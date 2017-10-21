/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
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
    private DatePicker AuditoriaFechaUno;
    @FXML
    private DatePicker AuditoriaFechaDos;
    @FXML
    private CheckBox EntreFecha;
    @FXML
    private TextField EmpleadoAuditoria;
    @FXML
    private TextField DescripcionAuditoria;
    @FXML
    private CheckBox EntreHora;
    @FXML
    private TextField HoraUno;
    @FXML
    private TextField HoraDos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO      
        EntreFecha.setOnAction(e -> handleCheckBoxUno(AuditoriaFechaDos,EntreFecha));
        EntreHora.setOnAction(e -> handleCheckBoxDos(HoraDos,EntreHora));
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
