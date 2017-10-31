/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Hugo
 */
public class CategoriasController extends Controller{
        
    @FXML
    private TableView<CategoriaProducto> tablaCategorias;
    
    @FXML
    private TableColumn<CategoriaProducto,String> ColumnaNombre;
    
    @FXML
    private TableColumn<CategoriaProducto,String> ColumnaCodigo;
    
    @FXML
    private AnchorPane DetalleCategoria;
    
    @FXML
    private TextField codigo_categoria;

    @FXML
    private TextField descripcion_categoria;

    @FXML
    private TextField nombreBuscar;    

    @FXML
    private TextField codigoBuscar;

    @FXML
    private TextField nombre_categoria;
    
    private CategoriaProducto categoria_seleccionada;
    
    private InformationAlertController infoController;
    
    private Boolean crear_nuevo;
    
    private List<CategoriaProducto> categorias;
    
    private final ObservableList<CategoriaProducto> masterData = FXCollections.observableArrayList();
    private final ObservableList<CategoriaProducto> masterData_filtro = FXCollections.observableArrayList();
    public CategoriasController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        infoController = new InformationAlertController();
                
        categoria_seleccionada = null;
        crear_nuevo = false;
    }
    
    public boolean cumple_condicion_busqueda(CategoriaProducto categoria, String codigo, String nombre){
        boolean match = false;
        if ( codigo.equals("") && nombre.equals("")){
            match = false;
        }
        else {
            match = (!codigo.equals("")) ? (match && (categoria.get("categoria_code")).equals(codigo)) : true;
            match = (!nombre.equals("")) ? (match && (categoria.get("nombre")).equals(nombre)) : true;
        }
        return match;
    }    
    
    @FXML
    public void buscar_categoria(ActionEvent event) throws IOException{
        //System.out.println("============================================");
        //categorias = null;
        categorias = CategoriaProducto.findAll();
        masterData.clear();
        
        try{
            for(CategoriaProducto categoria : categorias){
                if (cumple_condicion_busqueda(categoria, nombreBuscar.getText(), codigoBuscar.getText())){
                    masterData.add(categoria);
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }        
        
    }
       
    public void crear_categoria(){
        try{
            Base.openTransaction();
            CategoriaProducto nueva_categoria = new CategoriaProducto();
            nueva_categoria.asignar_atributos(usuarioActual.getString(("usuario_cod")),codigo_categoria.getText(), nombre_categoria.getText(), descripcion_categoria.getText());
            nueva_categoria.saveIt();
            Base.commitTransaction();
            infoController.show("La categoria ha sido creado satisfactoriamente");             
        }
        catch(Exception e){
            System.out.println("La categoria contiene errores");      
            Base.rollbackTransaction();
        }
    }
    
    public void nuevo(){
        crear_nuevo = true;
        DetalleCategoria.setDisable(false);
        limpiar_formulario();
    }
  
    public void editar_categoria(CategoriaProducto categoria){
        try{
            categoria.asignar_atributos(usuarioActual.getString(("usuario_cod")),codigo_categoria.getText(), nombre_categoria.getText(), descripcion_categoria.getText());
            categoria.saveIt();
            infoController.show("la categoria ha sido editada");
        }
        catch(Exception e){
            
        }
    }
    
    public void limpiar_formulario(){
        nombre_categoria.clear();
        codigo_categoria.clear();
        descripcion_categoria.clear();
    }
    
    @Override
    public void guardar(){
        if (crear_nuevo){
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Usuarios, Accion.ACCION.CRE)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                crear_nuevo = false;
                return;
            }
            crear_categoria();
            limpiar_formulario();
        }else{
            if (categoria_seleccionada == null) return;
            editar_categoria(categoria_seleccionada);
        }
        categorias = CategoriaProducto.findAll();
        cargar_tabla_index();
    }
    
    
    public void cargar_tabla_index(){
        tablaCategorias.getItems().clear();
        masterData.clear();
        for (CategoriaProducto categoria : categorias){
            masterData.add(categoria);
        }
        tablaCategorias.setEditable(false);
        ColumnaCodigo.setCellValueFactory((TableColumn.CellDataFeatures<CategoriaProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("categoria_code")));
        ColumnaNombre.setCellValueFactory((TableColumn.CellDataFeatures<CategoriaProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
        tablaCategorias.getItems().addAll(masterData);
    }
    @FXML
    public void mostrar_detalle_categoria(ActionEvent event) throws IOException{
        try{
            CategoriaProducto registro_seleccionado = tablaCategorias.getSelectionModel().getSelectedItem();
            DetalleCategoria.setDisable(false);
            CategoriaProducto cate = CategoriaProducto.findFirst("nombre = ?", registro_seleccionado.getString("nombre"));
            nombre_categoria.setText(registro_seleccionado.getString("nombre"));
            codigo_categoria.setText(registro_seleccionado.getString("categoria_code"));
            descripcion_categoria.setText(registro_seleccionado.getString("descripcion"));
            
        }
        catch( Exception e){
            System.out.println(e);
        }
    }     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        DetalleCategoria.setDisable(true);
        categorias = null;
        categorias = CategoriaProducto.findAll();
        cargar_tabla_index();
        tablaCategorias.getSelectionModel().selectedIndexProperty().addListener((obs,oldSelection,newSelection) -> {
            if (newSelection != null){
                categoria_seleccionada = tablaCategorias.getSelectionModel().getSelectedItem();                
                tablaCategorias.getSelectionModel().clearSelection();        
            }
        });
       
    }
    
    @Override
    public Menu.MENU getMenu(){
        return Menu.MENU.Categorias;
    }
    
        
}
