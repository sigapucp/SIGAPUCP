/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaxTipo;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Unidad;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.javalite.activejdbc.LazyList;
/**
 *
 * @author herbert
 */
public class ProductosController extends Controller {
    
    private InformationAlertController infoController;
    // TABLA
    //------------------------------------------------------//
    @FXML
    private TableView<String[]> tablaProductos;
    @FXML
    private TableColumn<String[], String> ColumnaCategoria;
    @FXML
    private TableColumn<String[], String> ColumnaTipoProducto;
    @FXML
    private TableColumn<String[], String> ColumnaCodigoProducto;
    @FXML
    private AnchorPane tipo_producto_formulario;
    private List<String[]> master_data;
    // DATOS
    //--------------------------------------------------------//
    @FXML
    private TextField nombre_producto;
    @FXML
    private TextField codigo_producto;
    @FXML
    private TextField largo_producto;
    @FXML
    private TextField ancho_producto;
    @FXML
    private TextField peso_producto;
    @FXML
    private ComboBox unidades_producto;
    @FXML
    private TextArea descripcion_producto;
    @FXML
    private CheckBox perecible;
    //BUSCAR
    //---------------------------------------------------------//
    @FXML
    private TextField categoriaBuscar;    
    @FXML
    private TextField tipoProductoBuscar;  
    @FXML
    private TextField codigoProductoBuscar;      
    //CREACION - EDICION
    //---------------------------------------------------------//
    private boolean crear_nuevo = true;
    private TipoProducto producto_seleccionado ;
    
    public void inhabilitar_formulario(){
        tipo_producto_formulario.setDisable(true);
    }
    
    public void habilitar_formulario(){
        tipo_producto_formulario.setDisable(false);
    }

    public void limpiar_tabla_index(){
        tablaProductos.getItems().clear();
    }
    
    public void limpiar_formulario(){
        nombre_producto.clear();
        codigo_producto.clear();
        perecible.setDisable(false);
        largo_producto.clear();
        ancho_producto.clear();
        peso_producto.clear();
        unidades_producto.setDisable(false);
        descripcion_producto.clear();
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
        crear_estructura_tabla();
        try{
            for(int i = 0; i < master_data.size(); i++){
                if ( cumple_condicion_busqueda(master_data.get(i), categoriaBuscar.getText(),tipoProductoBuscar.getText(), codigoProductoBuscar.getText())){
                    System.out.println("-------------");
                    master_data_busqueda.add(master_data.get(i));
                }
            }
            master_data = master_data_busqueda;
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inhabilitar_formulario();
        crear_estructura_tabla();
        cargar_tabla_index();
        llenar_combobox_unidades();        
    }

    public void nuevo(){
        crear_nuevo = true;
        habilitar_formulario();
    }
    
    public void crear_tipo_producto(){
        try{
            Base.openTransaction();
            TipoProducto nuevo_tipo_producto = new TipoProducto();
            float peso = Float.parseFloat(peso_producto.getText());
            float longitud = Float.parseFloat(largo_producto.getText());
            float ancho = Float.parseFloat(ancho_producto.getText());
            char perecible = (this.perecible.isSelected() ? 'T' : 'F');
            Unidad unidad_tipo_producto = Unidad.first("nombre = ?", unidades_producto.getSelectionModel().getSelectedItem().toString());
            nuevo_tipo_producto.asignar_atributos("usuario", codigo_producto.getText(), peso, nombre_producto.getText(), perecible, descripcion_producto.getText(), longitud, ancho, unidad_tipo_producto.get("unidad_id").toString());
            nuevo_tipo_producto.saveIt();
            Base.commitTransaction();
            limpiar_formulario();
            infoController.show("El producto ha sido creado satisfactoriamente"); 
        }
        catch(Exception e){
            infoController.show("El producto contiene errores : " + e);        
            Base.rollbackTransaction();
        }
    }
    
    public void editar_producto(TipoProducto producto){
        limpiar_formulario();
    }
    
    public void llenar_combobox_unidades(){
        try{
            List<String> unidades_combo_box = new ArrayList<String>();
            LazyList<Unidad> lista_unidades = Unidad.findAll();
            for(Unidad unidad : lista_unidades){
                unidades_combo_box.add(unidad.get("nombre").toString());
            }            
            unidades_producto.getItems().addAll(unidades_combo_box);

        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    @Override
    public void guardar(){
        if (crear_nuevo){
            crear_tipo_producto();
        }
        else {
            if (producto_seleccionado == null) return;
            editar_producto(producto_seleccionado);
        }
        crear_estructura_tabla();
        cargar_tabla_index();        
    }
    
    public ProductosController(){
        Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        producto_seleccionado = null;
        infoController = new InformationAlertController();
    }
}
