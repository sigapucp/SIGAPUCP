/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Joel
 */


import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class PedidosVistaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private RadioButton tipoDocBoleta;

    @FXML
    private RadioButton tipoDocFactura;

    @FXML
    private TextField ruc;

    @FXML
    private TextField dni;

    @FXML
    private TextField envioDir;

    @FXML
    private TextField factDir;

    @FXML
    private CheckBox mismaDir;
    
    @FXML
    private Spinner<?> cantProd;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mismaDir.setOnAction(e -> manejoTextoChckBox(factDir,mismaDir));
        tipoDocBoleta.setOnAction(e -> manejoTextoRadBttn1());
        tipoDocFactura.setOnAction(e -> manejoTextoRadBttn2());
    }    
    
    private void manejoTextoChckBox(TextField texto, CheckBox seleccionado){
        if (seleccionado.isSelected()) {
            texto.setDisable(true);
        }else{
            texto.setDisable(false);
        }
    }
    
    private void manejoTextoRadBttn1(){
        tipoDocFactura.setSelected(false);
        ruc.setDisable(true);
        dni.setDisable(false);
    }
    
    private void manejoTextoRadBttn2(){
        tipoDocBoleta.setSelected(false);
        dni.setDisable(true);
        ruc.setDisable(false);
    }
    
}
