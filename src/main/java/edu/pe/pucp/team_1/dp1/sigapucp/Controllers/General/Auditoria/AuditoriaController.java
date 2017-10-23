/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.General.Auditoria;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.RecursosHumanos.Usuarios.Usuarios;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


/**
 *
 * @author Hugo
 */
public class AuditoriaController implements Initializable {

    @FXML
    private DatePicker AuditoriaFechaDos;

    @FXML
    private ComboBox<String> Modulo;

    @FXML
    private ComboBox<String> Accion;

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
    private CheckBox EntreFecha;

    @FXML
    private TextField HoraUno;   
    
    private final ObservableList<Auditoria> masterData = FXCollections.observableArrayList();
    
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
        masterData.add(new Auditoria("8:00","21/10/2017","Hugo","Atiende","Cato","Hugo esta atendiendo"));
        masterData.add(new Auditoria("8:00","21/10/2017","Joel","Dormir","Casa","Joel esta durmiendo"));
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
        FilteredList<Auditoria> filteredData = new FilteredList<>(masterData, p -> true);
        
        EmpleadoAuditoria.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(auditoria -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (auditoria.getEmpleado().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }             
                return false;
            });
        });
        
        DescripcionAuditoria.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(auditoria -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (auditoria.getDescripcion().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }             
                return false;
            });
        });
        
        /*ColumnaHora.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(auditoria -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (auditoria.getHora().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }             
                return false;
            });
        });
        
        ColumnaFecha.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(auditoria -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (auditoria.getFecha().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }             
                return false;
            });
        });*/
        
        SortedList<Auditoria> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(TablaAuditoria.comparatorProperty());
        
        TablaAuditoria.setItems(sortedData);
               
    }
   
}
