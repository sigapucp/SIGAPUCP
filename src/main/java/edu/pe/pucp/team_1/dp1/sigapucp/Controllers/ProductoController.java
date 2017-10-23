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
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author herbert
 */
public class ProductoController extends Controller {
    
    @FXML
    private AnchorPane producton_container;
    
    @FXML
    public void abrirNuevoFormulario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Materiales/Producto/Form.fxml"));
            producton_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void regresarIndex(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Materiales/Producto/Index.fxml"));
            producton_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void crear() {
        System.out.println("================================================================");
        System.out.println("Estoy creando un Producto");
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        System.out.println("Humo del humo");
    }
    
}
