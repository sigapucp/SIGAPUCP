/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Joel
 */
public class AgregarProformasController implements Initializable {

    @FXML
    private TextField codProducto;
    @FXML
    private TextField tipoProducto;
    @FXML
    private ComboBox<?> catProducto;
    @FXML
    private DatePicker fAdqui;
    @FXML
    private Button buscarProd;
    @FXML
    private TextField cantProd;
    @FXML
    private Button agregarYregresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void volverParent(ActionEvent event) {
        ProformasController.modal_stage.close();
    }
    
}
