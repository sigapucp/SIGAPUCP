/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 *
 * @author Joel
 */
public class AgregarProductosController extends Controller{
    
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

    @FXML
    void volverParent(ActionEvent event) {
        PedidosController.modal_stage.close();
    }
    
    public void initialize(URL location, ResourceBundle resources){
    
    } 
    
    
    
}
