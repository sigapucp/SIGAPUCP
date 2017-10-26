/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.General.ReporteDeErrores;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Hugo
 */
public class ReporteDeErroresController extends Controller {
    
    
    
    @FXML
    private Button BotonVisualizar;
    
    @FXML
    private AnchorPane DetalleErrorPane;
    
    @FXML
    private TextField CodigoError;

    @FXML
    private ComboBox<?> PrioridadError;
    
    @FXML
    private TableView<?> TablaReporteErrores;
    
    @FXML
    private TableColumn<?, ?> DescripcionTabla;

    @FXML
    private TableColumn<?, ?> CodigoTabla;

    @FXML
    private void VisualizarDetalleError(ActionEvent event){
        
        DetalleErrorPane.setVisible(true);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
        
    }
    
}
