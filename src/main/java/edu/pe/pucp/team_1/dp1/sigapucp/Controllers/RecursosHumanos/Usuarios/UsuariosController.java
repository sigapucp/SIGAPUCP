/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.RecursosHumanos.Usuarios;


import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Rol;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Utils.GUIUtils;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Hugo
 */

public class UsuariosController extends Controller{
    
    @FXML
    private TableView<Usuario> TablaUsuarios;
    
    @FXML
    private TableColumn<Usuario,String> ColumnaCodigo;
    @FXML
    private TableColumn<Usuario,String> ColumnaNombre;    
    @FXML
    private TableColumn<Usuario,String> ColumnaApellido;
    @FXML
    private TableColumn<Usuario,String> ColumnaEstado;
    @FXML
    private TableColumn<Usuario,String> ColumnaRol;
                   
    @FXML
    private AnchorPane usuario_container;    
    @FXML
    private TitledPane TitlePane;    
    @FXML
    private TextField BusquedaCodigo;
    @FXML
    private TextField BusquedaNombre;
    @FXML
    private TextField BusquedaApellido;
    @FXML
    private ComboBox<String> BusquedaEstado;
    @FXML
    private ComboBox<String> BusquedaRol;
        
    @FXML
    private TextField VerNombre;
    @FXML
    private TextField VerApellido;
    @FXML
    private TextField VerTelefono;
    @FXML
    private TextField VerCorreo;
    
    @FXML
    private ComboBox<String> VerEstado;
    @FXML
    private ComboBox<String> VerRol;
    
    @FXML
    private TreeTableColumn<?, ?> ArbolPrivilegios;

    
    private final ObservableList<Usuario> usuarios = FXCollections.observableArrayList();    
    private final ObservableList<Usuario> usuariosFiltrados = FXCollections.observableArrayList();    
    
    private List<Usuario> tempUsuarios;
    private Usuario usuarioSelecionado;
    private Boolean crearNuevo;
    private InformationAlertController infoController;
   
         
    public UsuariosController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        tempUsuarios = Usuario.findAll();       
        
        for (Usuario usuario : tempUsuarios) {
            usuarios.add(usuario);
        }                               
        
        infoController = new InformationAlertController();
                
        usuarioSelecionado = null;
        crearNuevo = false;
    }
    
    @FXML
    private void visualizarUsuario(ActionEvent event) {
        usuarioSelecionado = TablaUsuarios.getSelectionModel().getSelectedItem();
        if(usuarioSelecionado == null) return;        
        setUsuarioVisible(usuarioSelecionado);                        
    }        
    
    @FXML
    private void buscarUsuarios(ActionEvent event) {        
        String codigo = BusquedaCodigo.getText();
        String nombre = BusquedaNombre.getText();
        String apellido = BusquedaApellido.getText();
        String estado = BusquedaEstado.getValue();
        String rol = BusquedaRol.getValue();
        
        tempUsuarios = Usuario.findAll();
        
        if(codigo!=null&&!codigo.isEmpty())
        {            
            tempUsuarios = tempUsuarios.stream().filter(p -> p.getString("usuario_cod").equals(codigo)).collect(Collectors.toList());
        }
        
        if(nombre!=null&&!nombre.isEmpty())
        {
            tempUsuarios = tempUsuarios.stream().filter(p -> p.getString("nombre").equals(nombre)).collect(Collectors.toList());
        }
        
        if(apellido!=null&&!apellido.isEmpty())
        {
            tempUsuarios = tempUsuarios.stream().filter(p -> p.getString("apellido").equals(apellido)).collect(Collectors.toList());
        }
        
        if(estado!=null&&!estado.isEmpty())
        {
            tempUsuarios = tempUsuarios.stream().filter(p -> p.get("estado").equals(estado)).collect(Collectors.toList());
        }
        
        if(rol!=null&&!rol.isEmpty())
        {
            tempUsuarios = tempUsuarios.stream().filter(p -> p.getRol().get("nombre").equals(rol)).collect(Collectors.toList());
        }        
        usuarios.removeAll(usuarios);        
        for (Usuario usuario : tempUsuarios) {
            usuarios.add(usuario);
        }        
        try {                        
        } catch (Exception e) {
            
        }
    }
    
    
    private void setUsuarioVisible(Usuario usuario)
    {        
        try {
            String nombre = usuario.getString("nombre");
            String apellido = usuario.getString("apellido");
            String telefono = usuario.getString("telefono");
            String email = usuario.getString("email");
            String estado = usuario.getString("estado");
            String rol = usuario.getRol().getString("rol_cod");          
            
            VerNombre.setText(nombre);
            VerApellido.setText(apellido);
            VerTelefono.setText(telefono);
            VerCorreo.setText(email);
            VerEstado.setValue(estado);
            VerRol.setValue(rol);
            
        } catch (Exception e) {
            
        }                                
    }
    
    private void limpiarVerUsuario()
    {
        VerNombre.clear();
        VerApellido.clear();
        VerTelefono.clear();
        VerCorreo.clear();
        VerEstado.getSelectionModel().clearSelection();
        VerRol.getSelectionModel().clearSelection();
    }
    
    
    @Override
    public void nuevo()
    {
        crearNuevo = true;
        limpiarVerUsuario();
    }
    
    @Override 
    public void guardar()
    {        
        if(crearNuevo)
        {
            crearUsuario();            
        }else
        {
            if(usuarioSelecionado == null) return;
            editarUsuario(usuarioSelecionado);
        }        
        
        RefrescarTabla();
    }
        
    private void editarUsuario(Usuario usuario)
    {        
        String nombre = VerNombre.getText();
        String apellido = VerApellido.getText();
        String telefono = VerTelefono.getText();
        String email = VerCorreo.getText();
        String estado = VerEstado.getSelectionModel().getSelectedItem();
        String rol = VerRol.getSelectionModel().getSelectedItem();
        
        try{      
        Base.openTransaction();       
        usuario.set("nombre",nombre);
        usuario.set("apellido", apellido);
        usuario.set("telefono", telefono);
        usuario.set("email", email);
        usuario.set("estado", estado);
        
        Rol usuarioRol = Rol.findFirst("rol_cod = ?", rol);
        usuario.set("rol_id",usuarioRol.getId());
        usuario.set("rol_cod",rol);        
        usuario.saveIt();
        Base.commitTransaction();
        
        infoController.show("El usuario ha sido editado satisfactoriamente");        
        }
        catch(Exception e){
           Base.rollbackTransaction();
        }                
    }
    
    private void crearUsuario()
    {
        String nombre = VerNombre.getText();
        String apellido = VerApellido.getText();
        String telefono = VerTelefono.getText();
        String email = VerCorreo.getText();
        String estado = VerEstado.getSelectionModel().getSelectedItem();
        String rol = VerRol.getSelectionModel().getSelectedItem();
        
        try{      
        Base.openTransaction();    
        String cod = "USR" + String.valueOf((Base.firstCell("select last_value from usuarios_usuario_id_seq")));        
        
        Usuario usuario = new Usuario();
        usuario.set("usuario_cod",cod);        
        usuario.set("nombre",nombre);
        usuario.set("apellido", apellido);
        usuario.set("telefono", telefono);
        usuario.set("email", email);
        usuario.set("estado", estado);
        
        Rol usuarioRol = Rol.findFirst("rol_cod = ?", rol);
        usuario.set("rol_id",usuarioRol.getId());
        usuario.set("rol_cod",rol);        
        usuario.set("contrasena_encriptada","");
        usuario.saveIt();
        Base.commitTransaction();
        
        
        infoController.show("El usuario ha sido editado creado satisfactoriamente con el codigo: USR"+String.valueOf(cod));        
        }
        catch(Exception e){
           Base.rollbackTransaction();
        }                         
    }
    
    private void RefrescarTabla()
    {
        TablaUsuarios.getColumns().get(0).setVisible(false);
        TablaUsuarios.getColumns().get(0).setVisible(true);
    }
    
                                         
    public void initialize(URL location, ResourceBundle resources) {    
                
        try {
            
            ColumnaCodigo.setCellValueFactory((CellDataFeatures<Usuario, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("usuario_cod")));
            ColumnaNombre.setCellValueFactory((CellDataFeatures<Usuario, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
            ColumnaApellido.setCellValueFactory((CellDataFeatures<Usuario, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("apellido"))); 
            ColumnaEstado.setCellValueFactory((CellDataFeatures<Usuario, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("estado"))); 
            ColumnaRol.setCellValueFactory((CellDataFeatures<Usuario, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getRol().get("nombre"))); 

            ObservableList<String> estados = FXCollections.observableArrayList();    
            ObservableList<String> estadosNoPad = FXCollections.observableArrayList(); 
            
            estados.add("");
            estados.add("ACTIVO");
            estados.add("INACTIVO");
            estadosNoPad.add("ACTIVO");
            estadosNoPad.add("INACTIVO");
            
            
            ObservableList<String> rolesNames = FXCollections.observableArrayList();   
            ObservableList<String> rolesCods = FXCollections.observableArrayList();   
            
            List<Rol> rolesTemp = Rol.findAll();
            
            rolesNames.add("");
            for(Rol rol:rolesTemp)
            {
                rolesNames.add(rol.getString("nombre"));
                rolesCods.add(rol.getString("rol_cod"));
            }

            BusquedaEstado.setItems(estados);
            BusquedaRol.setItems(rolesNames);
                        
            VerRol.setItems(rolesCods);
            VerEstado.setItems(estadosNoPad);

            TablaUsuarios.setItems(usuarios);
            
        } catch (Exception e) {
        }       
    } 
}

