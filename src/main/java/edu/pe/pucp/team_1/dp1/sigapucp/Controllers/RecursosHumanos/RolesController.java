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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static java.util.Locale.filter;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.layout.AnchorPane;
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
    
    
    @FXML
    private void visualizarRol(ActionEvent event) {
        crearNuevo = false;
        rolSelecionado = TablaRoles.getSelectionModel().getSelectedItem();
        if(rolSelecionado == null) return;        
        setRolVisible(rolSelecionado);  
    }

    @FXML
    private void buscarRoles(ActionEvent event) {
    }
    
     private void limpiarVerRol()
    {
        VerNombre.clear();
        VerDescripcion.clear();
        ArbolPrivilegiosTabla.setRoot(null);          
    }    
    
    @Override
    public void nuevo()
    {
        crearNuevo = true;
        limpiarVerRol();
    }
    
    @Override 
    public void guardar()
    {        
        if(crearNuevo)
        {
//            crearUsuario();            
        }else
        {
            if(rolSelecionado == null) return;
            editarRol(rolSelecionado);
        }                
          RefrescarTabla();
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
        
        AccionxRol.delete("rol_id = ?", rol.getId());
        
        for(PrivilegioEntrada privilegio:privilegiosEntrada)
        {
            if(privilegio.getTipo() == PrivilegioEntrada.TIPOPRIVILEGIO.ACCION&&privilegio.getEstadoPrivilegio().getValue())
            {
                AccionxRol accionxRol = new AccionxRol();
                accionxRol.set("menu_id",privilegio.getIdMenu());
                accionxRol.set("rol_id",rol.getId());
                accionxRol.set("rol_cod",cod);
                accionxRol.set("accion_id",privilegio.getId());
                accionxRol.set("accion_cod",privilegio.getCod());
                accionxRol.set("solo_admin","F");
                accionxRol.saveIt();
            }
        }                
        rol.saveIt();
        Base.commitTransaction();
        
        infoController.show("El usuario ha sido editado satisfactoriamente");        
        }
        catch(Exception e){
           Base.rollbackTransaction();
        }                
    }
    
    private void RefrescarTabla()
    {
        try {
            roles.removeAll(roles);
            tempRoles = Usuario.findAll();               
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
            int a = 5;            
        }                        
    }
    
    public void modificarArbolPrivilegios(Rol rol)
    {
        List<AccionxRol> accionesxroles = AccionxRol.where("rol_id = ?", rol.getId());
        for(PrivilegioEntrada privilegio:privilegiosEntrada)
        {
            if(privilegio.getTipo() == PrivilegioEntrada.TIPOPRIVILEGIO.ACCION)
            {
                AccionxRol accionxrol = AccionxRol.findFirst("menu_id = ? AND accion_id = ? AND rol_id = ?",privilegio.getIdMenu(),privilegio.getId(),rol.getId());
                if(accionxrol!=null)
                {
                    privilegio.setEstadoPrivilegio(true);
                }else
                {
                    privilegio.setEstadoPrivilegio(false);
                }
            }else
            {
                AccionxRol accionxrol = AccionxRol.findFirst("menu_id = ?  AND rol_id = ?",privilegio.getId(),rol.getId());
                if(accionxrol!=null)
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
            privilegiosEntrada.add(menuEntrada);
            CheckBoxTreeItem<PrivilegioEntrada> menuNode = new CheckBoxTreeItem<>(menuEntrada);                
            rootNode.getChildren().add(menuNode);                               

            for(Accion accion:acciones)
            {
                PrivilegioEntrada accionEntrada = new PrivilegioEntrada((int)accion.getId(),accion.getString("accion_cod"), accion.getString("nombre"), false, PrivilegioEntrada.TIPOPRIVILEGIO.ACCION);
                accionEntrada.setIdMenu((int)menu.getId());
                privilegiosEntrada.add(accionEntrada);
                CheckBoxTreeItem<PrivilegioEntrada> accionNode = new CheckBoxTreeItem<>(accionEntrada);
                menuNode.getChildren().add(accionNode);                    
            }                
        }        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
            
            ColumnaCodigo.setCellValueFactory((TableColumn.CellDataFeatures<Rol, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("rol_cod")));
            ColumnaNombre.setCellValueFactory((TableColumn.CellDataFeatures<Rol, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));            
            ColumnaEstado.setCellValueFactory((TableColumn.CellDataFeatures<Rol, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("estado")));             
            ColumnaDescipcion.setCellValueFactory((TableColumn.CellDataFeatures<Rol, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("descripcion")));             
            
            ArbolAccionesColumna.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PrivilegioEntrada, String>, ObservableValue<String>>() {     
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PrivilegioEntrada, String> param) {
                return param.getValue().getValue().getNombrePrivilegio();
            }                       
            });

            ArbolPrivilegiosColumna.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PrivilegioEntrada, Boolean>, ObservableValue<Boolean>>() {     
            @Override
            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<PrivilegioEntrada, Boolean> param) {
                TreeItem<PrivilegioEntrada> treeItem = param.getValue();
                SimpleBooleanProperty booleanProp = treeItem.getValue().getEstadoPrivilegio();
                booleanProp.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    
                if(treeItem.getValue().getTipo() == PrivilegioEntrada.TIPOPRIVILEGIO.MENU)
                {
                    ObservableList<TreeItem<PrivilegioEntrada>> children = treeItem.getChildren(); 
                    if(newValue) return;
                    for(TreeItem<PrivilegioEntrada> child:children)
                    {                      
                       child.getValue().setEstadoPrivilegio(false);
                    }                          
                }else
                {                    
                    TreeItem<PrivilegioEntrada> parent = treeItem.getParent();
                    if(!newValue) return;  
                    parent.getValue().setEstadoPrivilegio(true);
                }
            });                                             
                return booleanProp;
            }                       
            });
                                            
            ArbolPrivilegiosColumna.setCellFactory((TreeTableColumn<PrivilegioEntrada, Boolean> param) -> {
                CheckBoxTreeTableCell<PrivilegioEntrada,Boolean> cell = new CheckBoxTreeTableCell<>();
                cell.setAlignment(Pos.CENTER);                                
                return cell;
            });

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
                                   
            TablaRoles.setItems(roles);
            ArbolPrivilegiosTabla.setShowRoot(false);
            initializarArbolPrivilegios();
         
           
        } catch (Exception e) {
        }       
    }         

}
