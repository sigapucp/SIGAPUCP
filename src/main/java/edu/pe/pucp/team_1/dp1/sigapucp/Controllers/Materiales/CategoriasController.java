/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad.AccionLoggerSingleton;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
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
    
    private ConfirmationAlertController confirmatonController;
    
    private Boolean crear_nuevo;
    
    private List<CategoriaProducto> categorias;
    
    private final ObservableList<CategoriaProducto> masterData = FXCollections.observableArrayList();
    
    public CategoriasController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();        
        categoria_seleccionada = null;
        crear_nuevo = false;
    }
    
    public boolean cumple_condicion_busqueda(CategoriaProducto categoria, String nombre, String codigo){
        boolean match = true;
        if (codigo.equals("") && nombre.equals("")){
            match = true;
        }
        else {
            match = (!codigo.equals("")) ? (match && (categoria.get("categoria_code")).equals(codigo)) : match;
            match = (!nombre.equals("")) ? (match && (categoria.get("nombre")).equals(nombre)) : match;
        }
        return match;
    }    
    
    @FXML
    public void buscar_categoria(ActionEvent event) throws IOException{
        categorias = CategoriaProducto.where("estado = ?", CategoriaProducto.ESTADO.ACTIVO.name());
        masterData.clear();
        try{
            for(CategoriaProducto categoria : categorias){
                if (cumple_condicion_busqueda(categoria, nombreBuscar.getText(), codigoBuscar.getText())){
                    masterData.add(categoria);
                }
            }
        }catch(Exception e){
            infoController.show("Error al filtrar categorias");
            System.out.println(e);
        }
        tablaCategorias.getItems().clear();
        tablaCategorias.setEditable(false);
        ColumnaCodigo.setCellValueFactory((TableColumn.CellDataFeatures<CategoriaProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("categoria_code")));
        ColumnaNombre.setCellValueFactory((TableColumn.CellDataFeatures<CategoriaProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
        tablaCategorias.getItems().addAll(masterData);
    }
       
    public void crear_categoria(){
        try{
            Base.openTransaction();
            CategoriaProducto nueva_categoria = new CategoriaProducto();
            if(!confirmatonController.show("Se creará la categoria con código: " + codigo_categoria.getText(), "¿Desea continuar?")) return;
            nueva_categoria.asignar_atributos(usuarioActual.getString(("usuario_cod")),codigo_categoria.getText(), nombre_categoria.getText(), descripcion_categoria.getText());
            nueva_categoria.saveIt();
            Base.commitTransaction();
            infoController.show("La categoria ha sido creado satisfactoriamente");       
            limpiar_formulario();
            deshabilitar_formulario();
            crear_nuevo = false;
        }
        catch(Exception e){    
            Base.rollbackTransaction();
            infoController.show("Error al crear la categoria");
            System.out.println(e);
            crear_nuevo = true;
        }
    }
    
    public void nuevo(){
        crear_nuevo = true;
        DetalleCategoria.setDisable(false);
        limpiar_formulario();
        habilitar_formulario();
    }
  
    public void editar_categoria(CategoriaProducto categoria){
        try{
            if(!confirmatonController.show("Se editará la categoria con código: " + codigo_categoria.getText(), "¿Desea continuar?")) return;
            categoria.asignar_atributos(usuarioActual.getString(("usuario_cod")),codigo_categoria.getText(), nombre_categoria.getText(), descripcion_categoria.getText());
            categoria.saveIt();
            infoController.show("La categoria ha sido editada");
        }
        catch(Exception e){
            infoController.show("Error al editar la categoria");
            System.out.println(e);
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
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Categorias, Accion.ACCION.CRE)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                crear_nuevo = false;
                return;
            }
            crear_categoria();
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.CRE, Menu.MENU.Categorias ,this.usuarioActual);
            limpiar_formulario();
        }else{
            if (categoria_seleccionada == null){
                infoController.show("No he seleccionado un categoria");
                return;
            }
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Categorias, Accion.ACCION.MOD)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                return;
            }
            editar_categoria(categoria_seleccionada);
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.MOD, Menu.MENU.Categorias ,this.usuarioActual);
        }
        categorias = CategoriaProducto.findAll();
        cargar_tabla_index();
    }
    
    @Override
    public void desactivar(){
        categoria_seleccionada = tablaCategorias.getSelectionModel().getSelectedItem();
        if (categoria_seleccionada==null){
            infoController.show("No se selecciono una categoria");
            return;
        }
        try{
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Categorias, Accion.ACCION.DES)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                return;
            }
            if(!confirmatonController.show("Se deshabilitara la categoria con código: " + categoria_seleccionada.getString("categoria_code"), "¿Desea continuar?")) return;
            Base.openTransaction();
            categoria_seleccionada.set("estado",CategoriaProducto.ESTADO.INACTIVO.name());
            categoria_seleccionada.saveIt();
            Base.commitTransaction();
            infoController.show("La categoria ha sido deshabilitada");
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.DES, Menu.MENU.Categorias ,this.usuarioActual);
            limpiar_formulario();
            cargar_tabla_index();
        }catch(Exception e){
            Base.rollbackTransaction();
            infoController.show("Error al desactivar la categoria");
            System.out.println(e);           
        }
    }
        
    public void cargar_tabla_index(){
        tablaCategorias.getItems().clear();
        masterData.clear();
        for (CategoriaProducto categoria : categorias){
            if (categoria.getString("estado").equals(CategoriaProducto.ESTADO.ACTIVO.name())){
                masterData.add(categoria);
            }           
        }
        tablaCategorias.setEditable(false);
        ColumnaCodigo.setCellValueFactory((TableColumn.CellDataFeatures<CategoriaProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("categoria_code")));
        ColumnaNombre.setCellValueFactory((TableColumn.CellDataFeatures<CategoriaProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
        tablaCategorias.getItems().addAll(masterData);
    }
    
    @FXML
    public void mostrar_detalle_categoria(ActionEvent event) throws IOException{
        try{
            categoria_seleccionada = tablaCategorias.getSelectionModel().getSelectedItem();
            DetalleCategoria.setDisable(false);
            CategoriaProducto cate = CategoriaProducto.findFirst("nombre = ?", categoria_seleccionada.getString("nombre"));
            nombre_categoria.setText(categoria_seleccionada.getString("nombre"));
            codigo_categoria.setText(categoria_seleccionada.getString("categoria_code"));
            descripcion_categoria.setText(categoria_seleccionada.getString("descripcion"));           
        }
        catch( Exception e){
            infoController.show("Error al mostrar detalle de la categoria");
            System.out.println(e);
        }
    }
    
    public void habilitar_formulario(){
        DetalleCategoria.setDisable(false);
    }
    
    public void deshabilitar_formulario(){
        DetalleCategoria.setDisable(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        deshabilitar_formulario();
        categorias = null;
        categorias = CategoriaProducto.findAll();
        cargar_tabla_index();
    }
    
    @Override
    public Menu.MENU getMenu(){
        return Menu.MENU.Categorias;
    }
}
