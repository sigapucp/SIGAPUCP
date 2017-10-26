/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.RecursosHumanos.Usuarios;


import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Utils.GUIUtils;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Hugo
 */

public class UsuariosController extends Controller{
    @FXML
    private TableColumn<Usuario,String> ColumnaNombre;
    
    @FXML
    private TableColumn<Usuario,String> ColumnaCorreo;

    @FXML
    private TableColumn<Usuario,String> ColumnaApellido;
    @FXML
    private TableColumn<Usuario,String> ColumnaCodigo;
    
    
    @FXML
    private TableView<Usuario> TablaUsuarios;

    @FXML
    private TextField CorreoUsuario;

    @FXML
    private TextField ApellidoUsuario;

    @FXML
    private TextField NombreUsuario;
       
    @FXML
    private AnchorPane usuario_container;
    
    private final ObservableList<Usuario> masterData = FXCollections.observableArrayList();    
    private List<Usuario> usuarios;
    private Usuario usuarioObservado;
    private Boolean crearNuevo;
    
    public UsuariosController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        usuarios = Usuario.findAll();       
        
        for (Usuario usuario : usuarios) {
            masterData.add(usuario);
        }                                 
        
        usuarioObservado = null;
        crearNuevo = false;
    }
    
    @FXML
    public void abrirDetalleUsuario(ActionEvent event) {
        
        
    }
    
    @FXML
    public void regresarIndex(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/RecursosHumanos/Usuario/Index.fxml"));
            usuario_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }           
    
    private void setDetalle(Usuario usuario)
    {
        
    }
    
    @FXML
    public void initialize(URL location, ResourceBundle resources) {        
        TablaUsuarios.setEditable(false);           
        //ColumnaNombre.prefWidthProperty().bind(ColumnaNombre.widthProperty().divide(4)); // w * 1/4
        //ColumnaNombre.setCellValueFactory((CellDataFeatures<Usuario, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
        ColumnaNombre.setCellValueFactory((CellDataFeatures<Usuario, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("usuario_cod")));
        //ColumnaApellido.setCellValueFactory((CellDataFeatures<Usuario, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("apellido")));                        
        TablaUsuarios.setItems(masterData);
    }
    
    

}

