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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Joel
 */
public class PedidosController extends Controller {
    
    @FXML
    private AnchorPane pedidoContainer;
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
    @FXML
    private TextField clienteSh;
    @FXML
    private DatePicker fechaPed;
        
    static Stage modal_stage = new Stage();
    
    @FXML private ProformasController proformasController;

    /**
     * Initializes the controller class.
     */
@Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Base.close();
        Base.open();
        mismaDir.setOnAction(e -> manejoTextoChckBox(factDir,mismaDir));
        tipoDocBoleta.setOnAction(e -> manejoTextoRadBttn1());
        tipoDocFactura.setOnAction(e -> manejoTextoRadBttn2());
        if (proformasController != null){
            this.clienteSh.setText(this.proformasController.clienteSh.getText());
        }
        //Seteo la modal de agregar producto
        Parent modal_content;
        try {
            modal_content = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Pedidos/AgregarProductos.fxml"));
            Scene modal_content_scene = new Scene(modal_content);
            modal_stage.setScene(modal_content_scene);
            if (modal_stage.getModality() == null) modal_stage.initModality(Modality.APPLICATION_MODAL);
            //modal_stage.initOwner((Stage) pedidoContainer.getScene().getWindow());
            modal_stage.setScene(modal_content_scene);
        } catch (IOException ex) {
            Logger.getLogger(PedidosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    
    
    public void injectController(ProformasController controller){
        this.proformasController = controller;
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
    
    @FXML
    private void handleAgregarProducto(ActionEvent event) throws IOException{
        modal_stage.showAndWait();
    }
    
}
