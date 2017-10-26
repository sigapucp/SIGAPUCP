/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

/**
 *
 * @author herbert
 */
public class ProductosController extends Controller {
    
    @FXML
    private TableView<TipoProducto> tablaProductos;
    @FXML
    private TableColumn<TipoProducto, String> columna_codigo;
    @FXML
    private TableColumn<TipoProducto, String> columna_nombre;
    @FXML
    private TableColumn<TipoProducto, String> columna_descripcion;
    @FXML
    private TextField codgo_producto_buscar;
    @FXML
    private TextField nombre_producto_buscar;
    @FXML
    private TextField peso_producto_buscar;
    @FXML
    private CheckBox perecible_busqueda;
    @FXML
    private AnchorPane tipo_producto_formulario;
    
    private List<TipoProducto> tipo_productos;
    private final ObservableList<TipoProducto> masterData = FXCollections.observableArrayList();
            
    
    public void inhabilitar_formulario(){
        tipo_producto_formulario.setDisable(true);
    }
    
    public void habilitar_formulario(){
        tipo_producto_formulario.setDisable(false);
    }
        
    public void cargar_tabla_index(){
        for( TipoProducto tipo_producto : tipo_productos){
            masterData.add(tipo_producto);
        }
        tablaProductos.setEditable(false);
        columna_codigo.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        columna_nombre.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
        columna_descripcion.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("descripcion")));
        tablaProductos.setItems(masterData);        
    }

    @FXML
    public void buscar_tipo_producto(ActionEvent event) throws IOException{
        try{
            /*
            String estado = ( estadoBusq.getSelectionModel().getSelectedItem() == null ) ? "" : estadoBusq.getSelectionModel().getSelectedItem().toString();
            clientes = null;
            clientes = Cliente.where("ruc = ? AND dni = ? AND nombre = ? AND estado = ? ", rucBusq.getText(), dniBusq.getText(), nombreBusq.getText(), estado);
            cargar_tabla_index();
            */
        }
        catch( Exception e){
            System.out.println(e);
        }
    }    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!Base.hasConnection()) Base.open();
        inhabilitar_formulario();
        tipo_productos = null;
        tipo_productos = TipoProducto.findAll();
        cargar_tabla_index();
    }
    
/*
    @FXML
    private void abrirIngresarProductoARack(ActionEvent event) throws IOException{
        Parent main_content_parent = FXMLLoader.load(getClass().getResource("/fxml/Materiales/Producto/IngresarARack.fxml"));
        Scene main_content_scene = new Scene(main_content_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(main_content_scene);
        app_stage.show();
    }
*/
}
