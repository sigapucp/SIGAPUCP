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
import java.util.ArrayList;
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

    public void limpiar_tabla_index(){
        tablaProductos.getItems().clear();
    }
    
    public void cargar_tabla_index(){
        limpiar_tabla_index();
        tablaProductos.setEditable(false);
        ColumnaCategoria.setCellValueFactory( (TableColumn.CellDataFeatures<String[], String> p) -> new ReadOnlyObjectWrapper(p.getValue()[0] ));
        ColumnaTipoProducto.setCellValueFactory( (TableColumn.CellDataFeatures<String[], String> p) -> new ReadOnlyObjectWrapper(p.getValue()[1]));
        ColumnaCodigoProducto.setCellValueFactory( (TableColumn.CellDataFeatures<String[], String> p) -> new ReadOnlyObjectWrapper(p.getValue()[2]));
        tablaProductos.getItems().addAll(master_data);
    }

    public boolean cumple_condicion_busqueda(String[] registro, String categoria, String tipo, String codigo){
        boolean match = true;
        if ( categoria.equals("") && tipo.equals("") && codigo.equals("")){
            match = false;
        }
        else {
            match = (!categoria.equals("")) ? (match && registro[0].equals(categoria)) : true;
            match = (!tipo.equals("")) ? (match && registro[1].equals(tipo)) : true;
            match = (!codigo.equals("")) ? (match && registro[2].equals(codigo)) : true;
        }
        return match;
    }
    
    @FXML
    public void buscar_tipo_producto(ActionEvent event) throws IOException{
        List<String[]> master_data_busqueda = new ArrayList<String[]>();
        boolean hayDatos=false;
        crear_estructura_tabla();
        int aux  = 0;
        try{
            for(int i = 0; i < master_data.size(); i++){
                hayDatos = cumple_condicion_busqueda(master_data.get(i), categoriaBuscar.getText(),tipoProductoBuscar.getText(), codigoProductoBuscar.getText());
                if (hayDatos){
                    System.out.println("-------------");
                    master_data_busqueda.add(master_data.get(i));
                    aux++;
                }
            }
            if (aux > 0){
                master_data = master_data_busqueda;                
            }
            
            System.out.println(master_data.size());
            cargar_tabla_index();
        }
        catch( Exception e){
            System.out.println(e);
        }
    } 
    
    
    public void crear_estructura_tabla(){
        master_data = new ArrayList<String[]>();
        try{
            List<CategoriaProducto> lista_categorias = CategoriaProducto.findAll();
            for (CategoriaProducto categoria : lista_categorias){
                List<TipoProducto> tipos_producto_categoria = categoria.getAll(TipoProducto.class);
                for (TipoProducto tipo : tipos_producto_categoria){
                    String[] registro = {categoria.get("nombre").toString(), tipo.get("nombre").toString(), tipo.get("tipo_cod").toString()};
                    master_data.add(registro);
                } 
            }   
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
   @FXML
    public void mostrar_detalle_producto(ActionEvent event) throws IOException{
        //List<String[]> master_data_busqueda = new ArrayList<String[]>();
        crear_estructura_tabla();
        try{
            
        }
        catch( Exception e){
            System.out.println(e);
        }
    }    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inhabilitar_formulario();
        crear_estructura_tabla();
        cargar_tabla_index();
    }

    public ProductosController(){
        Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
    }
}
