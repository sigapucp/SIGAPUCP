/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Moneda;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenCompraProductoArgs;
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
public class AgregarProductosEnviosController implements Initializable {
    //TABLA
    //----------------------------------------------------------//
    @FXML
    private TableView<OrdenCompraxProducto> tabla_productos;
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
    

    
    
    public void llenar_productos_tabla(OrdenCompra pedido_seleccionado){
        productos.clear();
        productos.addAll(productos_orden_de_compra_a_enviar);

        columna_codigo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        columna_nombre.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
        columna_descripcion.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("descripcion")));
        columna_cantidad.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cantidad")));

        tabla_productos.setItems(productos);
    }
    
    @FXML
    private void agregarProducto(ActionEvent event) {
        
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {  
        } catch (Exception e) {
            infoController.show("Problemas en la inicializaciond de busqueda de Producto");
        }       
    }       
    public Event<agregarOrdenCompraProductoArgs> devolverProductoEvent = new Event<>();                   
}
