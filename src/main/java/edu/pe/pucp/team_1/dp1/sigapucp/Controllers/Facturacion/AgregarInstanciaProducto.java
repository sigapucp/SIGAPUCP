/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.OrdenSalidaxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarInstanciaProductoArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenCompraProductoArgs;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Gustavo
 */
public class AgregarInstanciaProducto implements Initializable {
    @FXML
    private TableView<Producto> tabla_producto;
    @FXML
    private TableColumn<Producto, String> columna_codigo;
    @FXML
    private TableColumn<Producto, String> columna_nombre;
    @FXML
    private TableColumn<Producto, String> columna_fecha_entrada;
    @FXML
    private TableColumn<Producto, String> columna_lote;
    @FXML
    private TableColumn<Producto, String> columna_almacen;
    @FXML
    private TableColumn<Producto, String> columna_rack;

    private InformationAlertController infoController;
    private List<Producto> instancias_productos;
    private final ObservableList<Producto> masterDataProducto = FXCollections.observableArrayList();
 
    public void llenar_productos_tabla(){
        try{
            System.out.println("------------------------- 4");
            for(Producto p : instancias_productos){
                System.out.println(p);
            }
            masterDataProducto.addAll(instancias_productos);
            for(Producto p : masterDataProducto){
                System.out.println(p);
            }
            columna_codigo.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper());
            /*
            columna_nombre.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
            columna_fecha_entrada.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("fecha_entrada")));
            columna_lote.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("lote_cod")));
            columna_almacen.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("almacen_cod")));
            columna_rack.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("rack_cod")));
            */
            //tabla_producto.setItems(masterDataProducto);
        }catch(Exception e){
            System.out.println(e);
        }

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
        System.out.println("---------------------- 2");
        if (!instancias_productos.isEmpty())
            llenar_productos_tabla();
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try { 
        System.out.println("---------------------- 1");
        } catch (Exception e) {
            infoController.show("Problemas en la inicialización de los Productos");
        }       
    }   
    public Event<agregarInstanciaProductoArgs> devolver_instancia_producto = new Event<>();          

}
