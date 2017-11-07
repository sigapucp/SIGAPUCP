/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.RecursosHumanos;


import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.AccionxRol;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Rol;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad.AccionLoggerSingleton;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.InputMethodEvent;
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
    private ComboBox<String> VerRol;   
    
    @FXML
    private TreeTableView<String> ArbolPrivilegiosTabla;
    @FXML
    private TreeTableColumn<String, String> ArbolPrivilegiosColumna;
      
    private final ObservableList<Usuario> usuarios = FXCollections.observableArrayList();     
     
    private Usuario usuarioSelecionado;
    private Boolean crearNuevo;
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmatonController;
   
         
    public UsuariosController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        
        List<Usuario> tempUsuarios = Usuario.findAll();               
        for (Usuario usuario : tempUsuarios) {
            usuarios.add(usuario);
        }                               
        
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
                
        usuarioSelecionado = null;
        crearNuevo = false;       
    }
    
    @FXML
    private void visualizarUsuario(ActionEvent event) {                   
//        if(!PreCambiarAccion()) return;
        crearNuevo = false;
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
        
        List<Usuario> tempUsuarios = Usuario.findAll();
        
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
        RefrescarTabla(tempUsuarios);
        try {                        
        } catch (Exception e) {
            infoController.show("El Usuario contiene errores : " + e);                    
        }
    }          
    
    private void setUsuarioVisible(Usuario usuario)
    {        
        try {
            String nombre = usuario.getString("nombre");
            String apellido = usuario.getString("apellido");
            String telefono = usuario.getString("telefono");
            String email = usuario.getString("email");         
            Rol usuarioRol = usuario.getRol();
            String rol = usuarioRol.getString("rol_cod");          
            
            VerNombre.setText(nombre);
            VerApellido.setText(apellido);
            VerTelefono.setText(telefono);
            VerCorreo.setText(email);          
            VerRol.setValue(rol);
            
            mostrarUsuarioPrivilegios(usuario);                                                   
        } catch (Exception e) {
            infoController.show("El Usuario contiene errores : " + e);
        }                                
    }
    
    private void limpiarVerUsuario()
    {
        VerNombre.clear();
        VerApellido.clear();
        VerTelefono.clear();
        VerCorreo.clear();       
        VerRol.getSelectionModel().clearSelection();
        ArbolPrivilegiosTabla.setRoot(null);
    }
    
     @Override
     public void desactivar()
     {
        if(usuarioSelecionado==null) 
        {
            infoController.show("No ha seleccionado un usuario");            
            return;
        }
        try {
           if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Usuarios, Accion.ACCION.DES)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                return;
            } 
           if(!confirmatonController.show("Se deshabilitara la categoria con nombre: " + VerNombre.getText(), "¿Desea continuar?")) return;
           Base.openTransaction();
           
           usuarioSelecionado.set("estado",Usuario.ESTADO.INACTIVO.name());
           usuarioSelecionado.saveIt();

           Base.commitTransaction();
           infoController.show("El usuario ha sido deshabilitado");
           AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.DES, Menu.MENU.Usuarios ,this.usuarioActual);
           limpiarVerUsuario();
           RefrescarTabla(Usuario.where("estado = 'ACTIVO'"));
           //cargar
        } catch (Exception e) {
           infoController.show("El Usuario contiene errores : " + e);        
           Base.rollbackTransaction();
        }
     }
    
    
    @Override
    public void nuevo()
    {
//        if(!PreCambiarAccion()) return;
        crearNuevo = true;
        limpiarVerUsuario();
    }
    
    @Override 
    public void guardar()
    {        
        if(crearNuevo)
        {
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Usuarios, Accion.ACCION.CRE)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                crearNuevo = false;
                return;
            }
            crearUsuario();  
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.CRE, Menu.MENU.Usuarios ,this.usuarioActual);
            limpiarVerUsuario();
        }else
        {
            if(usuarioSelecionado==null) 
            {
                infoController.show("No ha seleccionado un usuario");            
                return;
            }
             if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Usuarios, Accion.ACCION.MOD)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                return;
            }
            editarUsuario(usuarioSelecionado);
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.MOD, Menu.MENU.Usuarios ,this.usuarioActual);
        }                
        RefrescarTabla(Usuario.where("estado = 'ACTIVO'"));
    }
        
    private void editarUsuario(Usuario usuario)
    {        
        String cod = "USR" + String.valueOf(usuario.getId());
        String nombre = VerNombre.getText();
        String apellido = VerApellido.getText();
        String telefono = VerTelefono.getText();
        String email = VerCorreo.getText();       
        String rol = VerRol.getSelectionModel().getSelectedItem();
        
        if(!confirmatonController.show("Se editara el usuario con codigo: " + cod, "¿Desea continuar?")) return;
        
        try{      
        Base.openTransaction();       
        usuario.set("nombre",nombre);
        usuario.set("apellido", apellido);
        usuario.set("telefono", telefono);
        usuario.set("email", email);       
        
        Rol usuarioRol = Rol.findFirst("rol_cod = ?", rol);
        usuario.set("rol_id",usuarioRol.getId());
        usuario.set("rol_cod",rol);        
        usuario.set("last_user_change",usuarioActual.getString("usuario_cod"));
        usuario.saveIt();
        Base.commitTransaction();
        
        infoController.show("El usuario ha sido editado satisfactoriamente");        
        }
        catch(Exception e){
            infoController.show("El Usuario contiene errores : " + e);        
           Base.rollbackTransaction();
        }                
    }
    
    private void crearUsuario()
    {
        usuarioSelecionado = null;
        String nombre = VerNombre.getText();
        String apellido = VerApellido.getText();
        String telefono = VerTelefono.getText();
        String email = VerCorreo.getText();     
        String rol = VerRol.getSelectionModel().getSelectedItem();
        
        
        String cod = "USR" + String.valueOf(Integer.valueOf(String.valueOf((Base.firstCell("select last_value from usuarios_usuario_id_seq")))) + 1);  
        if(!confirmatonController.show("Se creara el usuario con codigo: " + cod, "¿Desea continuar?")) return;
        
        try{      

        Base.openTransaction();           
        Usuario usuario = new Usuario();
        usuario.set("usuario_cod",cod);        
        usuario.set("nombre",nombre);
        usuario.set("apellido", apellido);
        usuario.set("telefono", telefono);
        usuario.set("email", email);       
        
        Rol usuarioRol = Rol.findFirst("rol_cod = ?", rol);
        usuario.set("rol_id",usuarioRol.getId());
        usuario.set("rol_cod",rol);        
        usuario.set("contrasena_encriptada","");  
        usuario.set("estado",Usuario.ESTADO.ACTIVO.name());
        usuario.set("last_user_change",usuarioActual.get("usuario_cod"));
        usuario.saveIt();        
        Base.commitTransaction();                
        infoController.show("El usuario ha sido creado satisfactoriamente");        
        }
        catch(Exception e){
           infoController.show("El Usuario contiene errores : " + e);        
           Base.rollbackTransaction();           
        }finally{
           crearNuevo = false; 
        }        
    }
    
    @Override
    public void cargar(){
        if(!confirmatonController.show("Verifique que el formato del archivo .csv sea: \n codigo_usuario,nombre,apellido,telefono,mail,rol,", "¿Desea continuar?")) return;
        String filename = "data_usuarios.csv";
        File file = new File(filename);
        Boolean primera_fila = true;
        try {
            Scanner inputStream = new Scanner(file);
            while (inputStream.hasNext()){
                String data = inputStream.nextLine();
                //manejo de la data aquí:
                String [] values = data.split(",");
                if (primera_fila) {
                    primera_fila = false;
                    if (values.length != 6) {
                        infoController.show("El archivo .csv no tiene el formato adecuado. Verifique que sea\ncodigo_usuario,nombre,apellido,telefono,mail,rol,"); 
                        return;
                    }
                    continue; //nos saltamos el encabezado
                }
                Base.openTransaction();
                Usuario nuevo_usuario = new Usuario();
                nuevo_usuario.set("usuario_cod",values[0]);        
                nuevo_usuario.set("nombre",values[1]);
                nuevo_usuario.set("apellido", values[2]);
                nuevo_usuario.set("telefono", values[3]);
                nuevo_usuario.set("email", values[4]);       
                Rol usuarioRol = Rol.findFirst("rol_cod = ?", values[5]);
                nuevo_usuario.set("rol_id",usuarioRol.getId());
                nuevo_usuario.set("rol_cod",values[5]);        
                nuevo_usuario.set("contrasena_encriptada","");  
                nuevo_usuario.set("estado",Usuario.ESTADO.ACTIVO.name());
                nuevo_usuario.set("last_user_change",usuarioActual.get("usuario_cod"));
                nuevo_usuario.saveIt();
                Base.commitTransaction();
                System.out.println("CORRECTO");
            }
            infoController.show("¡Carga masiva de datos de usuarios exitosa!");
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.CSV, Menu.MENU.Usuarios, this.usuarioActual);
            inputStream.close();
            RefrescarTabla(Usuario.where("estado = 'ACTIVO'"));
        } catch (FileNotFoundException ex) {
            System.out.println("INCORRECTO");
            Base.rollbackTransaction();
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void RefrescarTabla(List<Usuario> usuariosRefresh)
    {        
        try {
            usuarios.removeAll(usuarios);                 
            if(usuariosRefresh == null) return;
            for (Usuario usuario : usuariosRefresh) {
                usuarios.add(usuario);
            }               
            TablaUsuarios.getColumns().get(0).setVisible(false);
            TablaUsuarios.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("El Usuario contiene errores : " + e);        
        }                                  
    }
    
      public void mostrarUsuarioPrivilegios(Usuario usuario)
    {
        List<AccionxRol> accionesxrol = AccionxRol.where("rol_id = ?",usuario.getInteger("rol_id"));        
        Set<Integer> menuIds =  new HashSet<>(accionesxrol.stream().map(x->x.getInteger("menu_id")).collect(Collectors.toList()));
        TreeItem<String> rootNode = new TreeItem<>("Menu");          
        
        for(Integer menuId:menuIds)
        {
            List<AccionxRol> filteredMenu = accionesxrol.stream().filter(x->x.getInteger("menu_id").equals(menuId)).collect(Collectors.toList());
            CheckBoxTreeItem<String> menuNode = new CheckBoxTreeItem<>(Menu.MENU.getMenu(menuId).name());    
            rootNode.getChildren().add(menuNode);    
            for(AccionxRol accion : filteredMenu)
            {
                CheckBoxTreeItem<String> accionNode = new CheckBoxTreeItem<>(Accion.findById(accion.getInteger("accion_id")).getString("nombre"));
                menuNode.getChildren().add(accionNode);                                    
            }            
        }
        ArbolPrivilegiosTabla.setRoot(rootNode);           
    }
     
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Usuarios;
    }
    
    public void initialize(URL location, ResourceBundle resources) {                    
        try {
            
            ColumnaCodigo.setCellValueFactory((CellDataFeatures<Usuario, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("usuario_cod")));
            ColumnaNombre.setCellValueFactory((CellDataFeatures<Usuario, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
            ColumnaApellido.setCellValueFactory((CellDataFeatures<Usuario, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("apellido"))); 
            ColumnaEstado.setCellValueFactory((CellDataFeatures<Usuario, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("estado"))); 
            ColumnaRol.setCellValueFactory((CellDataFeatures<Usuario, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getRol().get("nombre"))); 
            
            ArbolPrivilegiosColumna.setCellValueFactory((TreeTableColumn.CellDataFeatures<String, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getValue())); 
                        
            ObservableList<String> estados = FXCollections.observableArrayList();                           
            estados.add("");
            estados.addAll(Arrays.asList(Usuario.ESTADO.values()).stream().map(x->x.name()).collect(Collectors.toList()));                        
            
            
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
            
            TablaUsuarios.setItems(usuarios);
            ArbolPrivilegiosTabla.setShowRoot(false);
            
        } catch (Exception e) {
            infoController.show("El Usuario contiene errores : " + e);        
        }       
    } 

   
}

