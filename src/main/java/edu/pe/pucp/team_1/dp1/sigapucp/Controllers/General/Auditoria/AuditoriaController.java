/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.General.Auditoria;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad.AccionLog;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Rol;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Promocion;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.javalite.activejdbc.Base;


/**
 *
 * @author Hugo
 */
public class AuditoriaController extends Controller {

    @FXML
    private DatePicker AuditoriaFechaDos;

    @FXML
    private ComboBox<String> Modulo;

    @FXML
    private ComboBox<String> AccionC;

    @FXML
    private DatePicker AuditoriaFechaUno;

    @FXML
    private TextField DescripcionAuditoria;

    @FXML
    private TableColumn<Auditoria,String> ColumnaModulo;

    @FXML
    private TableColumn<Auditoria,String> ColumnaHora;

    @FXML
    private TextField EmpleadoAuditoria;

    @FXML
    private TableView<Auditoria> TablaAuditoria;

    @FXML
    private TableColumn<Auditoria,String> ColumnaDescripcion;

    @FXML
    private TableColumn<Auditoria,String> ColumnaFecha;

    @FXML
    private TableColumn<Auditoria,String> ColumnaEmpleado;

    @FXML
    private TableColumn<Auditoria,String> ColumnaAccion;
    
    @FXML
    private TableColumn<Auditoria,String> ColumnaRol;

    @FXML
    private CheckBox EntreFecha; 
    
    private List<AccionLog> acciones;
    
    //private List<String> modulos;
    
    private List<Auditoria> auditorias;
    
    private ObservableList<Auditoria> masterData = FXCollections.observableArrayList();
    
    @FXML
    private void handleCheckBoxUno(ActionEvent event){
        if (EntreFecha.isSelected()){
            AuditoriaFechaDos.setDisable(false);
        }
        
        if (!EntreFecha.isSelected()){
            AuditoriaFechaDos.setDisable(true);
        }      
    }  
    
    public void limpiar_tabla_index(){
        TablaAuditoria.getItems().clear();
    }
    
    public void cargar_tabla_index(){
        limpiar_tabla_index();
        acciones = AccionLog.findAll();
        for (AccionLog accion : acciones){
            Timestamp tiempo = accion.getTimestamp("tiempo");
            Usuario usuario = Usuario.findById(accion.getInteger("usuario_id"));
            String empleado = usuario.getString("nombre");
            Menu menu = Menu.findById(accion.getInteger("menu_id"));
            String menus = menu.getString("nombre");
            Rol rol = Rol.findById(accion.getInteger("rol_id"));
            String roles = rol.getString("nombre");
            Accion acciond = Accion.findById(accion.getInteger("accion_id"));
            String nombreAccion = acciond.getString("nombre");
            String Descripcion = acciond.getString("descripcion");
            String fecha = new SimpleDateFormat("yyyy-MM-dd").format(tiempo);
            String hora = new SimpleDateFormat("HH:mm:ss").format(tiempo);
            masterData.add(new Auditoria(fecha,hora,empleado,nombreAccion,menus,Descripcion,roles));
            
        }
        ColumnaEmpleado.setCellValueFactory(cellData -> cellData.getValue().EmpleadoProperty());
        ColumnaHora.setCellValueFactory(cellData -> cellData.getValue().HoraProperty());
        ColumnaFecha.setCellValueFactory(cellData -> cellData.getValue().FechaProperty());
        ColumnaAccion.setCellValueFactory(cellData -> cellData.getValue().AccionProperty());
        ColumnaModulo.setCellValueFactory(cellData -> cellData.getValue().ModuloProperty());
        ColumnaDescripcion.setCellValueFactory(cellData -> cellData.getValue().DescripcionProperty());
        ColumnaRol.setCellValueFactory(cellData -> cellData.getValue().RolProperty());
        FilteredList<Auditoria> filteredData = new FilteredList<>(masterData, p -> true);
        TablaAuditoria.setItems(filteredData);
    }
    
    public AuditoriaController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");

        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
        cargar_tabla_index();
        Modulo.getItems().add("");
        List<Menu> modulos = Menu.findAll();
        List<String> listaModulos = new ArrayList<String>();
        for (int i=0; i< modulos.size(); i++){
            listaModulos.add(modulos.get(i).getString("nombre"));                      
        }
        ObservableList<String> listamoduloscombo = FXCollections.observableArrayList(listaModulos);
        Modulo.getItems().addAll(listamoduloscombo);
        
        AccionC.getItems().add("");
        List<Accion> accionesFiltro = Accion.findAll();
        List<String> listaAcciones = new ArrayList<String>();
        for (int i=0; i < accionesFiltro.size(); i++){
            listaAcciones.add(accionesFiltro.get(i).getString("nombre"));
            
        }
        ObservableList<String> listaaccionescombo = FXCollections.observableArrayList(listaAcciones);
        AccionC.getItems().addAll(listaaccionescombo);
        
               
    }
    
    
        public boolean cumple_condicion_busqueda(Auditoria registro_auditoria, String empleado, String descripcion, String accion, String modulo, LocalDate fecha1, LocalDate fecha2) throws ParseException {
        boolean match = true;   
        
        //System.out.println(registro_auditoria.getEmpleado());
        
        String empleado_nombre = registro_auditoria.getEmpleado();
        
        if (empleado_nombre == null){
            empleado_nombre = "";
        }
        
        if ( empleado.equals("") && descripcion.equals("")&& accion.equals("") && modulo.equals("") && fecha1==null && fecha2==null){
            match = true;
        }else {
            match = (!empleado.equals("")) ? (match && (empleado_nombre).equals(empleado)) : match;
            match = (!descripcion.equals("")) ? (match && (registro_auditoria.getDescripcion()).equals(descripcion)) : match;
            match = (!(accion.equals(""))) ? (match && (registro_auditoria.getAccion()).equals(accion)) : match;
            match = (!(modulo.equals(""))) ? (match && (registro_auditoria.getModulo()).equals(modulo)) : match;
            Date fechaDate1 = new Date();
            Date fechaDate2 = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = format.parse(registro_auditoria.getFecha());
            if (!(fecha1==null) && (fecha2==null)){
                fechaDate1 = Date.from(fecha1.atStartOfDay(ZoneId.systemDefault()).toInstant());                
                match = (!(fecha==null)) ? (match && fechaDate1.equals(fecha)) : match;
            }
            if (!(fecha1==null) && !(fecha2==null)){
                fechaDate1 = Date.from(fecha1.atStartOfDay(ZoneId.systemDefault()).toInstant());
                fechaDate2 = Date.from(fecha2.atStartOfDay(ZoneId.systemDefault()).toInstant());
                match = (!(fecha==null)) ? (match && fechaDate1.before(fecha) && fechaDate2.after(fecha)) : match;
            }
            
            
        }
        return match;
    }
    @FXML
    public void buscar_auditoria(ActionEvent event) throws IOException, ParseException{
        
        LocalDate fecha1 = AuditoriaFechaUno.getValue();
        LocalDate fecha2 = AuditoriaFechaDos.getValue();
        ObservableList<Auditoria> masterDataAux = FXCollections.observableArrayList();
        
        String empleado = EmpleadoAuditoria.getText();
                
        String descripcion = DescripcionAuditoria.getText();
        
        String accion = AccionC.getSelectionModel().getSelectedItem();
        if (accion==null){
            accion = "";
        }
        
        String modulo = Modulo.getSelectionModel().getSelectedItem();
        
        if (modulo==null){
            modulo = "";
        }

        
        int cant_elementos = masterData.size();
        
        for (int i = 0; i < cant_elementos; i++){
            if (cumple_condicion_busqueda(masterData.get(i),empleado,descripcion,accion,modulo,fecha1,fecha2)){
                masterDataAux.add(masterData.get(i));
            }                                 
        }

        FilteredList<Auditoria> filteredData = new FilteredList<>(masterDataAux, p -> true);
        TablaAuditoria.setItems(filteredData);
    }
    
    @Override
    public Menu.MENU getMenu(){
        return Menu.MENU.Auditoria;
    }
   
}
