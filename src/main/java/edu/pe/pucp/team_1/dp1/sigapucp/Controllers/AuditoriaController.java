/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

/**
 * FXML Controller class
 *
 * @author Hugo
 */
@Component
public class AuditoriaController implements Initializable {

    @FXML
    private DatePicker AuditoriaFechaDos;

    @FXML
    private Button BtnBuscar;

    @FXML
    private TextField EmpleadoAuditoria;

    @FXML
    private CheckBox EntreHora;

    @FXML
    private TableView<Auditoria> TablaAuditoria;

    @FXML
    private TableColumn<Auditoria,String> ColumnaEmpleado;

    @FXML
    private CheckBox EntreFecha;

    @FXML
    private TextField HoraDos;

    @FXML
    private DatePicker AuditoriaFechaUno;

    @FXML
    private TextField DescripcionAuditoria;

    @FXML
    private TextField HoraUno;
    
    private final ObservableList<Auditoria> masterData = FXCollections.observableArrayList();

    public AuditoriaController(){
        masterData.add(new Auditoria("8:00","21/10/2017","Hugo","Atiende","Cato","Hugo esta atendiendo"));
        masterData.add(new Auditoria("8:00","21/10/2017","Joel","Dormir","Casa","Joel esta durmiendo"));
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO      
        EntreFecha.setOnAction(e -> handleCheckBoxUno(AuditoriaFechaDos,EntreFecha));
        EntreHora.setOnAction(e -> handleCheckBoxDos(HoraDos,EntreHora));
           
        ColumnaEmpleado.setCellValueFactory(cellData -> cellData.getValue().EmpleadoProperty());
        ColumnaEmpleado.setCellValueFactory(cellData -> cellData.getValue().HoraProperty());
        ColumnaEmpleado.setCellValueFactory(cellData -> cellData.getValue().FechaProperty());
        ColumnaEmpleado.setCellValueFactory(cellData -> cellData.getValue().AccionProperty());
        ColumnaEmpleado.setCellValueFactory(cellData -> cellData.getValue().ModuloProperty());
        ColumnaEmpleado.setCellValueFactory(cellData -> cellData.getValue().DescripcionProperty());
        
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
        
        
        SortedList<Auditoria> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(TablaAuditoria.comparatorProperty());
        
        TablaAuditoria.setItems(sortedData);
               
    } 
    
    private void handleCheckBoxUno(DatePicker AuditoriaFechaDos,CheckBox EntreFecha){
        if (EntreFecha.isSelected()){
            AuditoriaFechaDos.setDisable(false);
        }
        
        if (!EntreFecha.isSelected()){
            AuditoriaFechaDos.setDisable(true);
        }
        
        
    }
    
    private void handleCheckBoxDos(TextField HoraDos,CheckBox EntreHora){
        if (EntreHora.isSelected()){
            HoraDos.setDisable(false);
        }
        
        if (!EntreHora.isSelected()){
            HoraDos.setDisable(true);
        }
        
        
    }
    
}
