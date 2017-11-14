/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Moneda;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoArgs;
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
public class AgregarProductosController implements Initializable {

    @FXML
    private TextField BuscarCodigo;
    @FXML
    private TextField BuscarNombre;
    @FXML
    private ComboBox<String> BuscarCategoria;
//    @FXML
//    private ComboBox<String> BuscarEstado;
    @FXML
    private Button buscarProductoButtom;    
    
    @FXML
    private Button agregarProductoButtom;    
    
    @FXML
    private TableView<TipoProducto> TablaProductos; 
    
    @FXML
    private TableColumn<TipoProducto, String> ColumnaNombre;

//    @FXML
//    private TableColumn<TipoProducto, String> ColumnaEstado;

    @FXML
    private TableColumn<TipoProducto, String> ColumnaCodigo;

    @FXML
    private TableColumn<TipoProducto, String> ColumnaDescripcion;
    
    private TipoProducto ProductoBusqueda = null;
    private InformationAlertController infoController;
    private final ObservableList<TipoProducto> productos = FXCollections.observableArrayList();     
    private List<TipoProducto> productosMostrar;
    public AgregarProductosController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");  
        infoController = new InformationAlertController();
        productosMostrar = TipoProducto.where("estado = ?", TipoProducto.ESTADO.ACTIVO.name());
    }

    /**
     * Initializes the controller class.
     */
    
    public void setProductosMostrar(List<TipoProducto> gProductosMostrar)
    {
        productosMostrar = gProductosMostrar;
        productos.removeAll(productos);
        productos.addAll(productosMostrar);
    }
    
    
    @FXML
    private void buscarProducto(ActionEvent event) {
        
        String codigo = BuscarCodigo.getText();
        String nombre = BuscarNombre.getText();
        String categoria = BuscarCategoria.getSelectionModel().getSelectedItem();
        //String estado = BuscarEstado.getSelectionModel().getSelectedItem();        
        
        List<TipoProducto> tempProductos = productosMostrar;
        try{
            
            if(codigo!=null&&!codigo.isEmpty())
            {            
                tempProductos = tempProductos.stream().filter(p -> p.getString("tipo_cod").equals(codigo)).collect(Collectors.toList());
            }
             
            if(nombre!=null&&!nombre.isEmpty())
            {            
                tempProductos = tempProductos.stream().filter(p -> p.getString("nombre").equals(nombre)).collect(Collectors.toList());
            }
           
////            if(estado!=null&&!estado.isEmpty())
////            {                
////                tempProductos = tempProductos.stream().filter(p -> p.get("estado").equals(estado)).collect(Collectors.toList());
////            }

            if(categoria!=null&&!categoria.isEmpty())
            {
                // WARNING PELIGRO NOMBRE !!!
                CategoriaProducto categoriaObj = CategoriaProducto.findFirst("nombre = ?", categoria);
                List<TipoProducto> tiposCategoria = categoriaObj.getAll(TipoProducto.class);
                tempProductos = tempProductos.stream().filter(p -> tiposCategoria.stream().anyMatch(x->Objects.equals(x.getInteger("tipo_id"), p.getInteger("tipo_id")))).collect(Collectors.toList());
            }        
            RefrescarTabla(tempProductos);             
        }catch(Exception e){
            infoController.show("Problemas en la busqueda de producto");            
        }                      
    }

    @FXML
    private void agregarProducto(ActionEvent event) {
        
        ProductoBusqueda = TablaProductos.getSelectionModel().getSelectedItem();
        if(ProductoBusqueda==null)
        {
            infoController.show("No ha seleccionado ningun producto");
            return;
        }
        devolverProductoEvent.fire(this, new agregarProductoArgs(ProductoBusqueda));
        Stage stage = (Stage) agregarProductoButtom.getScene().getWindow();    
        stage.close();
    }
    
     private void RefrescarTabla(List<TipoProducto> productoRefresh)
    {        
        try {
            productos.removeAll(productos);                 
            if(productoRefresh == null) return;
            for (TipoProducto producto : productoRefresh) {
                productos.add(producto);
            }               
            TablaProductos.getColumns().get(0).setVisible(false);
            TablaProductos.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("El producto contiene errores : " + e);      
        }                                  
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {            
            ColumnaCodigo.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
            ColumnaNombre.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
            ColumnaDescripcion.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("descripcion")));

            ObservableList<String> categoriasDrop = FXCollections.observableArrayList(); 
            
            categoriasDrop.add("");
            categoriasDrop.addAll(CategoriaProducto.findAll().stream().map(x -> x.getString("nombre")).collect(Collectors.toList()));


            //BuscarEstado.setItems(estados);
            BuscarCategoria.setItems(categoriasDrop);      
            productos.addAll(productosMostrar);
            TablaProductos.setItems(productos);            
        } catch (Exception e) {
            infoController.show("Problemas en la inicializacion de busqueda de Producto");
        }       
    }       
    public Event<agregarProductoArgs> devolverProductoEvent = new Event<>();                   
}
