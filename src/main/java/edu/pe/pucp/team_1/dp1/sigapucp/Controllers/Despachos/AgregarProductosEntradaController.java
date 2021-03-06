/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Despachos;


import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion.*;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaxTipo;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntradaxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenCompraProductoArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenEntradaProductoArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoArgs;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Jauma
 */
public class AgregarProductosEntradaController implements Initializable {
    //TABLA
    //----------------------------------------------------------//
    @FXML
    private TableView<OrdenEntradaxProducto> tabla_productos;
    @FXML
    private TextField BuscarCodigo;
    @FXML
    private TextField BuscarNombre;
    //@FXML
    //private ComboBox<String> BuscarCategoria;
    @FXML
    private TableColumn<OrdenEntradaxProducto, String> columna_codigo;
    @FXML
    private TableColumn<OrdenEntradaxProducto, String> columna_nombre;
    @FXML
    private TableColumn<OrdenEntradaxProducto, String> columna_descripcion;
    @FXML
    private TableColumn<OrdenEntradaxProducto, String> columna_cantidad;
    @FXML
    private Button boton_agregar_producto;   
    private List<TipoProducto> autoCompletadoProductoList;  
    ArrayList<String> possiblewordsProducto = new ArrayList<>();        
    AutoCompletionBinding<String> autoCompletionBindingProducto; 
        
    //LOGICA
    //----------------------------------------------------------//
    private InformationAlertController infoController;
    private final ObservableList<OrdenEntradaxProducto> productos = FXCollections.observableArrayList(); 
    private List<OrdenEntradaxProducto> productos_orden_de_entrada_a_enviar;
    private OrdenEntradaxProducto producto_busqueda = null;
    private List<CategoriaProducto> categorias;

    
    
    public void llenar_productos_tabla(){
        tabla_productos.getItems().clear();
        productos.clear();
        productos.addAll(productos_orden_de_entrada_a_enviar);
        columna_codigo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntradaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        columna_nombre.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntradaxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
        columna_descripcion.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntradaxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("descripcion")));

        tabla_productos.setItems(productos);
    }
    
    public boolean cumple_condicion_busqueda(OrdenEntradaxProducto producto, String codigo, String nombre
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
            for(OrdenEntradaxProducto producto : productos_orden_de_entrada_a_enviar){
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

        devolverProductoEvent.fire(this, new agregarOrdenEntradaProductoArgs(producto_busqueda));
        Stage stage = (Stage) boton_agregar_producto.getScene().getWindow();    
        stage.close();
    }
    
    public AgregarProductosEntradaController(List<OrdenEntradaxProducto> productos_enviar)
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");  
        infoController = new InformationAlertController();
        productos_orden_de_entrada_a_enviar = productos_enviar;
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
            llenar_autocompletado();
            //llenar_comboBox_categoria();
        } catch (Exception e) {
            infoController.show("Problemas en la inicialización de búsqueda de los Productos");
        }       
    }       
    public Event<agregarOrdenEntradaProductoArgs> devolverProductoEvent = new Event<>();                   

    private void llenar_autocompletado() {
        autoCompletadoProductoList = TipoProducto.findAll();
        productoToString();
        autoCompletionBindingProducto = TextFields.bindAutoCompletion(BuscarCodigo, possiblewordsProducto);
        autoCompletionBindingProducto.addEventHandler(EventType.ROOT, (event) -> {
            handleAutoCompletarProducto();
        });
    }
    
    private void handleAutoCompletarProducto() {      
        for (TipoProducto tipoProducto : autoCompletadoProductoList){
             String nombre = tipoProducto.getString("producto_cod");
            if(nombre == null) continue;
            if (nombre.equals(BuscarCodigo.getText())){           
                //productoDevuelto = tipoProducto;             
            }
        }
    } 
    
     private void productoToString() {
        ArrayList<String> words = new ArrayList<>();
        for (TipoProducto producto : autoCompletadoProductoList){
            words.add(producto.getString("tipo_cod"));
        }               
        possiblewordsProducto = words;
    }       

}
