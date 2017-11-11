/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarInstanciaProductoArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenCompraProductoArgs;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Gustavo
 */
public class AgregarInstanciaProducto implements Initializable {
    private InformationAlertController infoController;
    private List<Producto> instancias_productos;
 
    public void llenar_productos_tabla(){
        /*
        tabla_productos.getItems().clear();
        productos.clear();
        productos.addAll(productos_orden_de_compra_a_enviar);
        columna_codigo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        columna_nombre.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
        columna_descripcion.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("descripcion")));
        columna_cantidad.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cantidad_descuento_disponible")));

        tabla_productos.setItems(productos);
*/
    }    
    
    @FXML
    private void agregarProducto(ActionEvent event) {
        /*
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
*/
    }    
    
    public AgregarInstanciaProducto(List<Producto> instancias_productos)
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");  
        infoController = new InformationAlertController();
        this.instancias_productos = instancias_productos;
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try { 
        } catch (Exception e) {
            infoController.show("Problemas en la inicialización de búsqueda de los Productos");
        }       
    }   
    public Event<agregarInstanciaProductoArgs> devolver_instancia_producto = new Event<>();          

}
