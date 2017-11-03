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
import java.net.URL;
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
    
    private final ObservableList<Auditoria> TablaAuditoriaData = FXCollections.observableArrayList();
    
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
        /*TablaAuditoriaData.add(new Auditoria("8:00","21/10/2017","Hugo","Atiende","Cato","Hugo esta atendiendo"));
        TablaAuditoriaData.add(new Auditoria("8:00","21/10/2017","Joel","Dormir","Casa","Joel esta durmiendo"));*/
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        acciones = AccionLog.findAll();
        for (AccionLog accion : acciones){
            String fecha = accion.getString("tiempo");
            //Date fechaDate = accion.getDate("tiempo");
            //LocalDate dateaux = fechaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
           
            Usuario usuario = Usuario.findById(accion.getInteger("usuario_id"));
            String empleado = usuario.getString("nombre");
            Menu menu = Menu.findById(accion.getInteger("menu_id"));
            String menus = menu.getString("nombre");
            Rol rol = Rol.findById(accion.getInteger("rol_id"));
            String roles = rol.getString("nombre");
            Accion acciond = Accion.findById(accion.getInteger("accion_id"));
            String nombreAccion = acciond.getString("nombre");
            String Descripcion = acciond.getString("descripcion");
            TablaAuditoriaData.add(new Auditoria(fecha,"12:00",empleado,nombreAccion,menus,Descripcion,roles));
            
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
        FilteredList<Auditoria> filteredData = new FilteredList<>(TablaAuditoriaData, p -> true);
          
        TablaAuditoria.setItems(filteredData);
               
    }
    
    @Override
    public Menu.MENU getMenu(){
        return Menu.MENU.Auditoria;
    }
   
}
