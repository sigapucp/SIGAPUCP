/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

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
import javafx.scene.layout.AnchorPane;

public class DocDeVentaController implements Initializable{

    @FXML
    private AnchorPane docventa_container;

    @FXML
    void regresarFormulario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Ventas/DocumentosDeVentas/Index.fxml"));
            System.out.println(docventa_container);
            docventa_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(DocDeVentaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    void abrirEditarFormulario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Ventas/DocumentosDeVentas/Form.fxml"));
            System.out.println(docventa_container);
            docventa_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(DocDeVentaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

}
