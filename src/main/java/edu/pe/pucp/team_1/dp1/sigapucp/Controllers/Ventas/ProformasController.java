/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Joel
 */
public class ProformasController extends Controller {

    @FXML
    private TextField clienteBusq;
    @FXML
    private Button buscarPedido;
    @FXML
    private ComboBox<?> estadoBusq;
    @FXML
    private TextField pedidoBusq;
    @FXML
    private TableView<?> tablaPedidos;
    @FXML
    private Button visualizarPedido;
    @FXML
    private DatePicker fechaProfSh;
    @FXML
    private TextField clienteSh;
    @FXML
    private RadioButton solesProf;
    @FXML
    private RadioButton dolProf;
    @FXML
    private TextField telfSh;
    @FXML
    private TextField correoSh;
    @FXML
    private TableView<?> productosProf;
    @FXML
    private TextField subTotalSol;
    @FXML
    private TextField igvSol;
    @FXML
    private TextField totalSol;
    @FXML
    private TextField subTotalDol;
    @FXML
    private TextField igvDol;
    @FXML
    private TextField totalDol;
    @FXML
    private Button generarPed;
    @FXML
    private Button EliminarProf;
    @FXML
    private Spinner<?> cantProd;
    @FXML
    private TextField producto;
    @FXML
    private Button agregarProdProf;
    
    
    static Stage modal_stage = new Stage();

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dolProf.setOnAction(e -> manejarRadBttnDol());
        solesProf.setOnAction(e -> manejarRadBttnSol());
        Parent modal_content;
        try {
            modal_content = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Proformas/AgregarProformas.fxml"));
            Scene modal_content_scene = new Scene(modal_content);
            modal_stage.setScene(modal_content_scene);
            modal_stage.initModality(Modality.APPLICATION_MODAL);
            //modal_stage.initOwner((Stage) pedidoContainer.getScene().getWindow());
            modal_stage.setScene(modal_content_scene);
        } catch (IOException ex) {
            Logger.getLogger(PedidosController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }  
    private void manejarRadBttnDol(){
        solesProf.setSelected(false);
    }
//    
    @FXML
    private void handleAgregarProducto(ActionEvent event) {
        modal_stage.showAndWait();
    }
    
    private void manejarRadBttnSol(){
        dolProf.setSelected(false);
    }
    
}
