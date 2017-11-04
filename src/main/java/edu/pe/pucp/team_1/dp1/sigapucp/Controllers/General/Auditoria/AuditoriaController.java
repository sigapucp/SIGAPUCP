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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
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
    private TextField HoraDos;

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
    private CheckBox EntreHora;

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

    @FXML
    private TextField HoraUno;   
    
    private List<AccionLog> acciones;
    
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
    @FXML
    private void handleCheckBoxDos(ActionEvent event){
        if (EntreHora.isSelected()){
            HoraDos.setDisable(false);
        }
        
        if (!EntreHora.isSelected()){
            HoraDos.setDisable(true);
        }     
    }    
    
    public AuditoriaController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
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
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
        ColumnaEmpleado.setCellValueFactory(cellData -> cellData.getValue().EmpleadoProperty());
        ColumnaHora.setCellValueFactory(cellData -> cellData.getValue().HoraProperty());
        ColumnaFecha.setCellValueFactory(cellData -> cellData.getValue().FechaProperty());
        ColumnaAccion.setCellValueFactory(cellData -> cellData.getValue().AccionProperty());
        ColumnaModulo.setCellValueFactory(cellData -> cellData.getValue().ModuloProperty());
        ColumnaDescripcion.setCellValueFactory(cellData -> cellData.getValue().DescripcionProperty());
        ColumnaRol.setCellValueFactory(cellData -> cellData.getValue().RolProperty());
        FilteredList<Auditoria> filteredData = new FilteredList<>(masterData, p -> true);
        
        /*ObjectProperty<Predicate<Auditoria>> empleadoFilter = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<Auditoria>> descripcionFilter = new SimpleObjectProperty<>();
        
        empleadoFilter.bind(Bindings.createObjectBinding(() ->
                auditoria -> auditoria.getEmpleado().toLowerCase().contains(EmpleadoAuditoria.getText().toLowerCase()),
                EmpleadoAuditoria.textProperty()));
        
        descripcionFilter.bind(Bindings.createObjectBinding(() ->
                auditoria -> auditoria.getDescripcion().toLowerCase().contains(DescripcionAuditoria.getText().toLowerCase()),
                DescripcionAuditoria.textProperty()));*/
        
        TablaAuditoria.setItems(filteredData);
        
        //filteredData.predicateProperty().bind(Bindings.createObjectBinding(
        //() -> empleadoFilter.get().and(descripcionFilter.get()),empleadoFilter,descripcionFilter));
        
        
               
    }
    
    
        /*public boolean cumple_condicion_busqueda(Auditoria registro_auditoria, String empleado, String descripcion){//, String accion, String modulo, LocalDate fecha1, LocalDate fecha2, String hora1, String hora2){
        boolean match = true;        
        if ( empleado.equals("") && descripcion.equals("")){ //&& accion==null && modulo==null && fecha1==null && fecha2==null && hora1==null && hora2==null){
            match = true;
        }else {
            /*Date fechaDate1 = new Date();
            Date fechaDate2 = new Date();
            if (!(fecha1==null)){
                fechaDate1 = Date.from(fecha1.atStartOfDay(ZoneId.systemDefault()).toInstant());
                fechaDate2 = Date.from(fecha2.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }*/
            //match = (!empleado.equals("")) ? (match && (registro_auditoria.getEmpleado()).equals(empleado)) : match;
            //match = (!descripcion.equals("")) ? (match && (registro_auditoria.getDescripcion()).equals(descripcion)) : match;
            /*match = (!(tipo==null)) ? (match && (promocion.get("tipo")).equals(tipo)) : match;
            Date fechaIni = promocion.getDate("fecha_inicio");            
            Date fechaFin = promocion.getDate("fecha_fin");            
            match = (!(fecha==null)) ? (match && fechaIni.before(fechaDate) && fechaFin.after(fechaDate)) : match;*/
        //}
        //return match;
    //}
    //@FXML
/*    public void buscar_auditoria(ActionEvent event) throws IOException{
        
        LocalDate fecha1 = AuditoriaFechaUno.getValue();
        LocalDate fecha2 = AuditoriaFechaDos.getValue();
        String hora1 = HoraUno.getText();
        String hora2 = HoraDos.getText();
        ObservableList<Auditoria> masterDataAux = masterData;
        int aux = 0;
        System.out.println(masterData.size());
        try{
            for (int i = 0; i < masterData.size(); i++){
                
                if (cumple_condicion_busqueda(masterData.get(i),EmpleadoAuditoria.getText(),DescripcionAuditoria.getText())) {//,AccionC.getSelectionModel().getSelectedItem(),Modulo.getSelectionModel().getSelectedItem(),fecha1,fecha2,hora1,hora2)){
                    System.out.println("cumplio condicion");
                    masterDataAux.add(masterData.get(i));
                    aux++;
                }else {
                    System.out.println("no cumplio condicion");
                }
                if (aux>0){
                    masterData = masterDataAux;
                }
                
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }*/
    @Override
    public Menu.MENU getMenu(){
        return Menu.MENU.Auditoria;
    }
   
}
