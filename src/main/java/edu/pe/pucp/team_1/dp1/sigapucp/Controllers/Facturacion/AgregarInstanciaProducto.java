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
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarInstanciaProductoArgs;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private TableColumn<Producto, String> columna_almacen;
    @FXML
    private TableColumn<Producto, String> columna_rack;
    @FXML
    private Button agregarProductoButtom;

    private InformationAlertController infoController;
    private List<Producto> instancias_productos;
    private final ObservableList<Producto> masterDataProducto = FXCollections.observableArrayList();
    private Producto producto_busqueda;
 
    public void llenar_productos_tabla(){
        try{            
            masterDataProducto.addAll(instancias_productos);
            
            columna_codigo.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("producto_cod")));
            columna_nombre.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findFirst("tipo_id = ?",p.getValue().getInteger("tipo_id")).getString("nombre")));
            columna_fecha_entrada.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("fecha_entrada")));
            columna_almacen.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("almacen_cod")));
            columna_rack.setCellValueFactory((TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("rack_cod")));
            
            tabla_producto.setItems(masterDataProducto);
        }catch(Exception e){            
            System.out.println(e);
        }
    }    
    
    @FXML
    private void agregarProducto(ActionEvent event) {
        producto_busqueda = tabla_producto.getSelectionModel().getSelectedItem();
        if(producto_busqueda==null)
        {
            infoController.show("No ha seleccionado ningun producto");
            return;
        }

        devolver_instancia_producto.fire(this, new agregarInstanciaProductoArgs(producto_busqueda));
        Stage stage = (Stage) agregarProductoButtom.getScene().getWindow();    
        stage.close();
    }    
    
    public AgregarInstanciaProducto(List<Producto> instancias_productos)
    {
        try{
            if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");  
            infoController = new InformationAlertController();
            this.instancias_productos = instancias_productos;
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        llenar_productos_tabla();
    }   
    public Event<agregarInstanciaProductoArgs> devolver_instancia_producto = new Event<>();          

}
