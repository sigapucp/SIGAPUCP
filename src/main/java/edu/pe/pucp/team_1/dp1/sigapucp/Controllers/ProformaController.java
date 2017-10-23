/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Joel
 */
public class ProformaController implements Initializable {
    
    @FXML
    private AnchorPane proforma_container;
    
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
    public void regresarFormulario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Proforma/Index.fxml"));
            System.out.println(proforma_container);
            proforma_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(ProformaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void abrirEditarFormulario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Proforma/View.fxml"));
            System.out.println(proforma_container);
            proforma_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(ProformaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void abrirNuevoFormulario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Proforma/Form.fxml"));
            System.out.println(proforma_container);
            proforma_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(ProformaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
    
}
