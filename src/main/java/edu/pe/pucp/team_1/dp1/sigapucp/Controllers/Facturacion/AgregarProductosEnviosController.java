/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion;


import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaxTipo;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenCompraProductoArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoArgs;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Jauma
 */
public class AgregarProductosEnviosController implements Initializable {
    //TABLA
    //----------------------------------------------------------//
    @FXML
    private TableView<OrdenCompraxProducto> tabla_productos;
    @FXML
    private TextField BuscarCodigo;
    @FXML
    private TextField BuscarNombre;
    //@FXML
    //private ComboBox<String> BuscarCategoria;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_codigo;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_nombre;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_descripcion;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_cantidad;
    @FXML
    private Button boton_agregar_producto;    
        
    //LOGICA
    //----------------------------------------------------------//
    private InformationAlertController infoController;
    private final ObservableList<OrdenCompraxProducto> productos = FXCollections.observableArrayList(); 
    private List<OrdenCompraxProducto> productos_orden_de_compra_a_enviar;
    private OrdenCompraxProducto producto_busqueda = null;
    private List<CategoriaProducto> categorias;

    
    
    public void llenar_productos_tabla(){
        tabla_productos.getItems().clear();
        productos.clear();
        productos.addAll(productos_orden_de_compra_a_enviar);
        columna_codigo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        columna_nombre.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
        columna_descripcion.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("descripcion")));
        columna_cantidad.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cantidad_descuento_disponible")));

        tabla_productos.setItems(productos);
    }
    
    public boolean cumple_condicion_busqueda(OrdenCompraxProducto producto, String codigo, String nombre
    //        , String categoria
    ){
        boolean match = true;        
        if ( codigo.equals("") && nombre.equals("") 
                //&& categoria.equals("")
                ){
            match = true;
        }else {
            match = (!codigo.equals("")) ? (match && (producto.get("tipo_cod")).equals(codigo)) : match;
            match = (!nombre.equals("")) ? (match && (TipoProducto.findById(producto.get("tipo_id")).getString("nombre").equals(nombre))) : match;
            CategoriaxTipo cruce = null;
            /*if (!categoria.equals("")){
               cruce = CategoriaxTipo.findFirst("tipo_id = ? AND tipo_cod = ? AND categoria_code = ? AND categoria_id = ?", (Integer)producto.get("tipo_id"),producto.get("tipo_cod"),(Integer)CategoriaProducto.findFirst("nombre = ?",categoria).get("categoria_id"),CategoriaProducto.findFirst("nombre = ?",categoria).get("categoria_code"));
                match = (!(cruce==null)) ? true : false;
            }*/
        }
        return match;
    }
    
    @FXML
    public void buscar_producto(ActionEvent event) throws IOException{
        productos.clear();
        //String categoria = (BuscarCategoria.getSelectionModel().getSelectedItem()==null) ? "" : BuscarCategoria.getSelectionModel().getSelectedItem().toString();
        try{
            for(OrdenCompraxProducto producto : productos_orden_de_compra_a_enviar){
                if (cumple_condicion_busqueda(producto, BuscarCodigo.getText(), BuscarNombre.getText()
                //        ,categoria 
                )){
                    productos.add(producto);
                }
            }
        }catch(Exception e){
            infoController.show("No se pudo buscar el producto : " + e.getMessage());
        }
    }
    
    @FXML
    private void agregarProducto(ActionEvent event) {
        categorias = null;
        producto_busqueda = tabla_productos.getSelectionModel().getSelectedItem();
        if(producto_busqueda==null)
        {
            infoController.show("No ha seleccionado ningun producto");
            return;
        }

        devolverProductoEvent.fire(this, new agregarOrdenCompraProductoArgs(producto_busqueda));
        Stage stage = (Stage) boton_agregar_producto.getScene().getWindow();    
        stage.close();
    }
    
    public AgregarProductosEnviosController(List<OrdenCompraxProducto> productos_enviar)
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");  
        infoController = new InformationAlertController();
        productos_orden_de_compra_a_enviar = productos_enviar;
    }
    
    /*private void llenar_comboBox_categoria(){
        List<CategoriaProducto> categorias = CategoriaProducto.findAll();
        BuscarCategoria.getItems().add("");
        for (CategoriaProducto categoria : categorias){
            BuscarCategoria.getItems().add(categoria.getString("nombre"));
        }        
    }*/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try { 
            llenar_productos_tabla();
            //llenar_comboBox_categoria();
        } catch (Exception e) {
            infoController.show("Problemas en la inicialización de búsqueda de los Productos");
        }       
    }       
    public Event<agregarOrdenCompraProductoArgs> devolverProductoEvent = new Event<>();                   
}
