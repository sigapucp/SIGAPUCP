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
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.PrivilegioEntrada;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Rol;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad.AccionLoggerSingleton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.Locale.filter;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Jauma
 */
public class RolesController extends Controller{

    @FXML
    private AnchorPane usuario_container;
    @FXML
    private TextField BusquedaNombre;
    @FXML
    private ComboBox<String> BusquedaEstado;
    @FXML
    private ComboBox<String> BusquedaRol;
    @FXML
    private TableView<Rol> TablaRoles;
    @FXML
    private TableColumn<Rol, String> ColumnaCodigo;
    @FXML
    private TableColumn<Rol, String> ColumnaNombre;
    @FXML
    private TableColumn<Rol, String> ColumnaEstado;
    @FXML
    private TableColumn<Rol, String> ColumnaDescipcion;
    @FXML
    private TitledPane TitlePane;
    @FXML
    private TextField VerNombre;
    @FXML
    private TextArea VerDescripcion;           
    @FXML
    private TreeTableView<PrivilegioEntrada> ArbolPrivilegiosTabla;
    @FXML
    private TreeTableColumn<PrivilegioEntrada, Boolean> ArbolPrivilegiosColumna;
    @FXML
    private TreeTableColumn<PrivilegioEntrada, String> ArbolAccionesColumna;
    @FXML
    private GridPane formulario_grid;
    
    private final ObservableList<Rol> roles = FXCollections.observableArrayList();         
    private List<Rol> tempRoles;
    private Rol rolSelecionado;
    private Boolean crearNuevo;
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmatonController;
    private List<PrivilegioEntrada> privilegiosEntrada;
    private TreeItem<PrivilegioEntrada> rootNode;
  

    /**
     * Initializes the controller class.
     */
    
    public RolesController()
    {
         if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");   
        
        tempRoles = Rol.findAll();               
        for (Rol usuario : tempRoles) {
            roles.add(usuario);
        }                               
        
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
        privilegiosEntrada = new ArrayList<>();
                
        rolSelecionado = null;
        crearNuevo = false;               
    }
    
    private void deshabilitar_formulario()
    {
        formulario_grid.setDisable(true);
    }
    
    private void habilitar_formulario()
    {
        formulario_grid.setDisable(false);
    }
    
    
    @FXML
    private void visualizarRol(ActionEvent event) {
        crearNuevo = false;
        rolSelecionado = null;
        rolSelecionado = TablaRoles.getSelectionModel().getSelectedItem();
        if(rolSelecionado == null) return;        
        setRolVisible(rolSelecionado);  
    }

    @FXML
    private void buscarRoles(ActionEvent event) {
        
        String codigo = BusquedaRol.getSelectionModel().getSelectedItem();
        String nombre = BusquedaNombre.getText();
        String estado = BusquedaEstado.getSelectionModel().getSelectedItem();        
        
        tempRoles = Rol.findAll();
        
        if(codigo!=null&&!codigo.isEmpty())
        {            
            tempRoles = tempRoles.stream().filter(p -> p.getString("rol_cod").equals(codigo)).collect(Collectors.toList());
        }
        
        if(nombre!=null&&!nombre.isEmpty())
        {
            tempRoles = tempRoles.stream().filter(p -> p.getString("nombre").equals(nombre)).collect(Collectors.toList());
        }
        
        if(estado!=null&&!estado.isEmpty())
        {
            tempRoles = tempRoles.stream().filter(p -> p.getString("estado").equals(estado)).collect(Collectors.toList());
        }                
        RefrescarTabla(tempRoles);
        try {                        
        } catch (Exception e) {
            
        }
    }
    
     private void limpiarVerRol()
    {
        VerNombre.clear();
        VerDescripcion.clear();
        for(PrivilegioEntrada privilegio:privilegiosEntrada)
        {
            privilegio.setEstadoPrivilegio(false);
        }
        if(rootNode!=null) rootNode.getValue().setEstadoPrivilegio(false);
    }    
     
     @Override
     public void desactivar()
     {
        if(rolSelecionado==null) 
        {
            infoController.show("No ha seleccionado un rol");            
            return;
        }
        try {
           if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Roles, Accion.ACCION.DES)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                return;
            }
            if(!confirmatonController.show("Se deshabilitara el rol con código: " + VerNombre.getText(), "¿Desea continuar?")) return;
            Base.openTransaction();
           
           rolSelecionado.set("estado",Rol.ESTADO.INACTIVO.name());
           rolSelecionado.saveIt();

           Base.commitTransaction();
           infoController.show("La categoria ha sido deshabilitada");
           AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.DES, Menu.MENU.Categorias ,this.usuarioActual);
           limpiarVerRol();
           RefrescarTabla(Rol.where("estado='ACTIVO'"));
        } catch (Exception e) {
            infoController.show("El rol contiene errores: " + e);
           Base.rollbackTransaction();
        }
     }
    
    @Override
    public void nuevo()
    {
        crearNuevo = true;
        if(ArbolPrivilegiosTabla.getRoot()==null)
        {
            ArbolPrivilegiosTabla.setRoot(rootNode);                                                          
        }
        habilitar_formulario();        
        limpiarVerRol();
    }


    
    @Override 
    public void guardar()
    {        
        if(crearNuevo)
        {
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Roles, Accion.ACCION.CRE)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                crearNuevo = false;
                return;
            }
            crearRol(); 
            limpiarVerRol();
            deshabilitar_formulario();
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.CRE, Menu.MENU.Roles ,this.usuarioActual);
        }else
        {
            if(rolSelecionado==null) 
            {
                infoController.show("No ha seleccionado un rol");
                return;
            }
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Roles, Accion.ACCION.MOD)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                return;
            }
            editarRol(rolSelecionado);
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.MOD, Menu.MENU.Roles ,this.usuarioActual);
        }                
        RefrescarTabla(Rol.where("estado='ACTIVO'"));
    }
    
     private void crearRol()
    {
        rolSelecionado = null;
        String nombre = VerNombre.getText();
        String descripcion = VerDescripcion.getText();        
        
        String cod = "ROL" + String.valueOf(Integer.valueOf(String.valueOf((Base.firstCell("select last_value from roles_rol_id_seq")))) + 1);  
        if(!confirmatonController.show("Se creara el Rol con codigo: " + cod, "¿Desea continuar?")) return;
        
        try{      
        Base.openTransaction();    
        
        Rol rol = new Rol();
        rol.set("rol_cod",cod);
        rol.set("nombre",nombre);
        rol.set("descripcion",descripcion);         
        rol.set("estado",Rol.ESTADO.ACTIVO.name());
        rol.set("last_user_change",usuarioActual.getString("usuario_cod"));
        
        rol.saveIt();
        
        for(PrivilegioEntrada privilegio:privilegiosEntrada)
        {
            if(privilegio.getEstadoPrivilegio().getValue())
            {
                AccionxRol accionxRol = new AccionxRol();
                accionxRol.set("menu_id",privilegio.getIdMenu());
                accionxRol.set("rol_id",rol.getId());
                accionxRol.set("rol_cod",cod);                
                accionxRol.set("solo_admin","F");
                
                if(privilegio.getTipo() == PrivilegioEntrada.TIPOPRIVILEGIO.ACCION)
                {
                    accionxRol.set("accion_id",privilegio.getId());
                    accionxRol.set("accion_cod",privilegio.getCod());                    
                }else
                {
                    accionxRol.set("accion_id",Accion.ACCION.getId(Accion.ACCION.VIW));
                    accionxRol.set("accion_cod",Accion.ACCION.VIW.name());                    
                }                
                accionxRol.saveIt();
            }
        }          
        
        Base.commitTransaction();
        infoController.show("El rol ha sido editado creado satisfactoriamente");        
        }
        catch(Exception e){
           Base.rollbackTransaction();           
        }finally{
           crearNuevo = false; 
        }        
    }
    
     private void editarRol(Rol rol)
    {        
        String cod = rol.getString("rol_cod");
        String nombre = VerNombre.getText();
        String descripcion = VerDescripcion.getText();              
        
        if(!confirmatonController.show("Se editara el rol con codigo: " + cod, "¿Desea continuar?")) return;
        
        try{      
        Base.openTransaction();       
        rol.set("nombre",nombre);     
        rol.set("descripcion", descripcion);        
        rol.set("last_user_change",usuarioActual.getString("usuario_cod"));
        
        AccionxRol.delete("rol_id = ?", rol.getId());
        
        for(PrivilegioEntrada privilegio:privilegiosEntrada)
        {
            if(privilegio.getEstadoPrivilegio().getValue())
            {
                AccionxRol accionxRol = new AccionxRol();
                accionxRol.set("menu_id",privilegio.getIdMenu());
                accionxRol.set("rol_id",rol.getId());
                accionxRol.set("rol_cod",cod);                
                accionxRol.set("solo_admin","F");
                
                if(privilegio.getTipo() == PrivilegioEntrada.TIPOPRIVILEGIO.ACCION)
                {
                    accionxRol.set("accion_id",privilegio.getId());
                    accionxRol.set("accion_cod",privilegio.getCod());                    
                }else
                {
                    accionxRol.set("accion_id",Accion.ACCION.getId(Accion.ACCION.VIW));
                    accionxRol.set("accion_cod",Accion.ACCION.VIW.name());                    
                }                
                accionxRol.saveIt();
            }
        }                
        rol.saveIt();
        Base.commitTransaction();        
        infoController.show("El Rol ha sido editado satisfactoriamente");        
        }
        catch(Exception e){
            infoController.show("No se ha podido guardar el rol: " + e.getMessage());
           Base.rollbackTransaction();
        }                
    }
    
    private void RefrescarTabla(List<Rol> tempRoles)
    {
        try {
            roles.removeAll(roles);
            if(tempRoles == null) return;
            for (Rol rol : tempRoles) {
                roles.add(rol);
            }               
            TablaRoles.getColumns().get(0).setVisible(false);
            TablaRoles.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
        }                                  
    }
      
    private void setRolVisible(Rol rol)
    {
        habilitar_formulario();
        try {            
            String nombre = rol.getString("nombre");
            String descripcion = rol.getString("descripcion");          
            
            VerNombre.setText(nombre);
            VerDescripcion.setText(descripcion);
            if(ArbolPrivilegiosTabla.getRoot()==null)
            {
                ArbolPrivilegiosTabla.setRoot(rootNode);                                                          
            }
            modificarArbolPrivilegios(rol);
            
        } catch (Exception e) {         
        }                        
    }
    
    public void modificarArbolPrivilegios(Rol rol)
    {
        List<AccionxRol> accionesxroles = AccionxRol.where("rol_id = ?", rol.getId());        
        
        for(PrivilegioEntrada privilegio:privilegiosEntrada)
        {
            if(privilegio.getTipo() == PrivilegioEntrada.TIPOPRIVILEGIO.ACCION)
            {
                if(accionesxroles.stream().anyMatch(o -> o.getInteger("accion_id") == privilegio.getId() && o.getInteger("menu_id") == privilegio.getIdMenu()))
                {
                    privilegio.setEstadoPrivilegio(true);
                }else
                {
                    privilegio.setEstadoPrivilegio(false);
                }
            }else
            {
                if(accionesxroles.stream().anyMatch(o -> o.getInteger("menu_id") == privilegio.getId()))
                {
                    privilegio.setEstadoPrivilegio(true);
                }else
                {
                    privilegio.setEstadoPrivilegio(false);
                }
            }
        }        
    }
    
    public void initializarArbolPrivilegios()
    {
        List<Menu> menus = Menu.findAll();
        menus.removeIf(new Predicate<Menu>() {
            @Override
            public boolean test(Menu t) {
                return (int)t.getId() == 1;
            }
        });

        rootNode = new TreeItem<>(new PrivilegioEntrada(-1,"","Menus",false, PrivilegioEntrada.TIPOPRIVILEGIO.MENU));              
        for(Menu menu:menus)
        {
            List<Accion> acciones = menu.getAll(Accion.class);
            PrivilegioEntrada menuEntrada = new PrivilegioEntrada((int)menu.getId(),"", menu.getString("nombre"), false, PrivilegioEntrada.TIPOPRIVILEGIO.MENU);
            menuEntrada.setIdMenu((int)menu.getId());
            privilegiosEntrada.add(menuEntrada);
            CheckBoxTreeItem<PrivilegioEntrada> menuNode = new CheckBoxTreeItem<>(menuEntrada);                
            rootNode.getChildren().add(menuNode);                               

            for(Accion accion:acciones)
            {
                if(accion.getInteger("accion_id")== Accion.ACCION.getId(Accion.ACCION.VIW)) continue;
                PrivilegioEntrada accionEntrada = new PrivilegioEntrada((int)accion.getId(),accion.getString("accion_cod"), accion.getString("nombre"), false, PrivilegioEntrada.TIPOPRIVILEGIO.ACCION);
                accionEntrada.setIdMenu((int)menu.getId());
                privilegiosEntrada.add(accionEntrada);
                CheckBoxTreeItem<PrivilegioEntrada> accionNode = new CheckBoxTreeItem<>(accionEntrada);
                menuNode.getChildren().add(accionNode);                    
            }                
        }        
    }
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Roles;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
            
            ColumnaCodigo.setCellValueFactory((TableColumn.CellDataFeatures<Rol, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("rol_cod")));
            ColumnaNombre.setCellValueFactory((TableColumn.CellDataFeatures<Rol, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));            
            ColumnaEstado.setCellValueFactory((TableColumn.CellDataFeatures<Rol, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("estado")));             
            ColumnaDescipcion.setCellValueFactory((TableColumn.CellDataFeatures<Rol, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("descripcion")));             
            
            ArbolAccionesColumna.setCellValueFactory((TreeTableColumn.CellDataFeatures<PrivilegioEntrada, String> param) -> param.getValue().getValue().getNombrePrivilegio());

            ArbolPrivilegiosColumna.setCellValueFactory((TreeTableColumn.CellDataFeatures<PrivilegioEntrada, Boolean> param) -> {
                TreeItem<PrivilegioEntrada> treeItem = param.getValue();
                SimpleBooleanProperty booleanProp = treeItem.getValue().getEstadoPrivilegio();
                booleanProp.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    
                  
                    
                    if(treeItem.getValue().getTipo() == PrivilegioEntrada.TIPOPRIVILEGIO.MENU)
                    {
                        if(treeItem.getValue().getId() == -1)
                        {
                            ObservableList<TreeItem<PrivilegioEntrada>> children = treeItem.getChildren();
                            for(TreeItem<PrivilegioEntrada> child:children)
                            {
                                child.getValue().setEstadoPrivilegio(true);
                                ObservableList<TreeItem<PrivilegioEntrada>> moreChildren = child.getChildren();
                                for(TreeItem<PrivilegioEntrada> morechild:moreChildren)
                                {
                                    morechild.getValue().setEstadoPrivilegio(true);
                                }                                
                            }                       
                        }else
                        {
                            ObservableList<TreeItem<PrivilegioEntrada>> children = treeItem.getChildren();
                            if(newValue) return;
                            for(TreeItem<PrivilegioEntrada> child:children)
                            {
                                child.getValue().setEstadoPrivilegio(false);
                            }                            
                        }                        
                    }else
                    {
                        TreeItem<PrivilegioEntrada> parent = treeItem.getParent();
                        if(!newValue) return;
                        parent.getValue().setEstadoPrivilegio(true);
                    }
                });                                             
                return booleanProp;
            });
                                            
            ArbolPrivilegiosColumna.setCellFactory((TreeTableColumn<PrivilegioEntrada, Boolean> param) -> {
                CheckBoxTreeTableCell<PrivilegioEntrada,Boolean> cell = new CheckBoxTreeTableCell<>();
                cell.setAlignment(Pos.CENTER);                                
                return cell;
            });

            ObservableList<String> estados = FXCollections.observableArrayList();    
            ObservableList<String> estadosNoPad = FXCollections.observableArrayList(); 
            
            estados.add("");
            estados.addAll(Arrays.asList(Rol.ESTADO.values()).stream().map(x->x.name()).collect(Collectors.toList()));            
            estadosNoPad.addAll(Arrays.asList(Rol.ESTADO.values()).stream().map(x->x.name()).collect(Collectors.toList()));                              
            
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
                                   
            TablaRoles.setItems(roles);           
            initializarArbolPrivilegios();
            deshabilitar_formulario();
           
        } catch (Exception e) {
            infoController.show("Error al inicializar los roles: " + e.getMessage());
        }       
    }         

}
