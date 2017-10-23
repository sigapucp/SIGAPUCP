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
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Joel
 */
public class PedidoController implements Initializable{
 
    @FXML
    private AnchorPane pedido_container;
    
    
    @FXML
    public void abrirNuevoFormulario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Pedido/Form.fxml"));
            System.out.println(pedido_container);
            pedido_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void abrirEditarFormulario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Pedido/View.fxml"));
            System.out.println(pedido_container);
            pedido_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void regresarFormulario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Pedido/Index.fxml"));
            System.out.println(pedido_container);
            pedido_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @FXML
//    public void abrirNuevoFormulario(ActionEvent event) {
//        try {
//            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Materiales/Producto/Form.fxml"));
//            System.out.println(pedido_container);
//            pedido_container.getChildren().setAll(contenido);
//        } catch (IOException ex) {
//            Logger.getLogger(ProductoController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
    }
    
}
