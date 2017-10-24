/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

/**
 *
 * @author Joel
 */

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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ProveedorController implements Initializable{

    @FXML
    private AnchorPane proveedor_container;

    @FXML
    private TextField dniBusq;

    @FXML
    private Button buscafBttn;

    @FXML
    private ComboBox<?> estadoBusq;

    @FXML
    private TextField rucBusq;

    @FXML
    private TextField razSocial;

    @FXML
    private TextField nombreBusq;

    @FXML
    void abrirEditarFormulario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Proveedor/View.fxml"));
            System.out.println(proveedor_container);
            proveedor_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void abrirNuevoFormulario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Proveedor/Form.fxml"));
            System.out.println(proveedor_container);
            proveedor_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        @FXML
    void regresarFormulario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Proveedor/Index.fxml"));
            System.out.println(proveedor_container);
            proveedor_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(ProveedorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { //To change body of generated methods, choose Tools | Templates.
    }
}

