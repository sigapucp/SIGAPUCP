/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaxTipo;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
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
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
/**
 *
 * @author herbert
 */
public class ProductosController extends Controller {
    
    @FXML
    private TableView<String[]> tablaProductos;
    @FXML
    private TableColumn<String[], String> ColumnaCategoria;
    @FXML
    private TableColumn<String[], String> ColumnaTipoProducto;
    @FXML
    private TableColumn<String[], String> ColumnaCodigoProducto;

    @FXML
    private TextField categoriaBuscar;    
    @FXML
    private TextField tipoProductoBuscar;  
    @FXML
    private TextField codigoProductoBuscar;      
    
    @FXML
    private AnchorPane tipo_producto_formulario;
    
    private List<String[]> master_data;
            
    
    public void inhabilitar_formulario(){
        tipo_producto_formulario.setDisable(true);
    }
    
    public void habilitar_formulario(){
        tipo_producto_formulario.setDisable(false);
    }
        
    public void cargar_tabla_index(){
        tablaProductos.setEditable(false);
        //master_data
        /*
        for(int i = 0; i < master_data.size(); i++){
            System.out.println(master_data.get(i)[0]);
            System.out.println(master_data.get(i)[1]);
            System.out.println(master_data.get(i)[2]);
            
        }
//        ColumnaCategoria.setCellValueFactory( (TableColumn.CellDataFeatures<String[], String> p) -> new ReadOnlyObjectWrapper(p.getValue()[0] ));
  //      ColumnaTipoProducto.setCellValueFactory( (TableColumn.CellDataFeatures<String[], String> p) -> new ReadOnlyObjectWrapper(p.getValue()[1]));
    //    ColumnaCodigoProducto.setCellValueFactory( (TableColumn.CellDataFeatures<String[], String> p) -> new ReadOnlyObjectWrapper(p.getValue()[2]));
        //tablaProductos.getItems().addAll(master_data);

        /*
        ColumnaCategoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                String[] x = p.getValue();
                if (x != null && x.length>0) {
                    return new SimpleStringProperty(x[0]);
                } else {
                    return new SimpleStringProperty("<no name>");
                }
            }
        });
        
        ColumnaTipoProducto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                String[] x = p.getValue();
                if (x != null && x.length>1) {
                    return new SimpleStringProperty(x[1]);
                } else {
                    return new SimpleStringProperty("<no value>");
                }
            }
        });
        
        ColumnaCodigoProducto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                String[] x = p.getValue();
                if (x != null && x.length>2) {
                    return new SimpleStringProperty(x[2]);
                } else {
                    return new SimpleStringProperty("<no value>");
                }
            }
        });
        

        */
        //tablaProductos.getItems().addAll(Arrays.asList(master_data));
    }

    @FXML
    public void buscar_tipo_producto(ActionEvent event) throws IOException{
        try{
            /*

            tipo_productos = null;
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
    
    public void crear_estructura_tabla(String nombre, String accion){
        master_data = null;
        try{
            if (accion == "busqueda"){
                CategoriaProducto categoria = CategoriaProducto.findFirst("nombre = ?", nombre);
                List<TipoProducto> tipos_producto_categoria = categoria.getAll(TipoProducto.class);
                for (TipoProducto tipo : tipos_producto_categoria){
                    String[] registro = {categoria.get("nombre").toString(), tipo.get("nombre").toString(), tipo.get("tipo_cod").toString()};
                    master_data.add(registro);
                }
            }
            if (accion == "index"){
                System.out.println("================================ 11");
                List<CategoriaProducto> categorias = null;
                System.out.println("================================ 12");
                for (CategoriaProducto categoria : categorias){
                    //List<TipoProducto> tipos_producto_categoria = categoria.getAll(TipoProducto.class);
                    categoria.getAll(TipoProducto.class).dump();
                    System.out.println("================================ 13");
                    /*
                    for (TipoProducto tipo : tipos_producto_categoria){
                        String[] registro = {categoria.get("nombre").toString(), tipo.get("nombre").toString(), tipo.get("tipo_cod").toString()};
                        master_data.add(registro);
                    } 
                    */
                }
            }            
        }
        catch(Exception e){
            System.out.println(e);
        }
       /* for(int i = 0; i < master_data.size(); i++){
            System.out.println(master_data.get(i)[0]);
            System.out.println(master_data.get(i)[1]);
            System.out.println(master_data.get(i)[2]);
            
        }*/
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inhabilitar_formulario();
        crear_estructura_tabla("", "index");
        cargar_tabla_index();
    }

    public ProductosController(){
        Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
    }
}
