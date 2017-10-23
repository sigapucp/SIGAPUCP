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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
/*
** author: Joel
*/
public class ClienteController implements Initializable {

    @FXML
    private AnchorPane cliente_container;

    @FXML
    private TextField clienteSh;

    @FXML
    private TextField dni;

    @FXML
    private TextField envioDir;

    @FXML
    private TextField factDir;

    @FXML
    private CheckBox persoNatu;

    @FXML
    private CheckBox persoJuri;

    @FXML
    private TextField ruc;

    @FXML
    private TextField repLegal;

    @FXML
    private TextField rubro;

    @FXML
    private TextField telf;

    @FXML
    private TextArea anotaciones;

    @FXML
    private TextField web;

    @FXML
    private TextField correo;

    @FXML
    void regresarFormulario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Cliente/Index.fxml"));
            System.out.println(cliente_container);
            cliente_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(ProformaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
        @FXML
    public void abrirEditarFormulario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Cliente/View.fxml"));
            System.out.println(cliente_container);
            cliente_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void abrirNuevoFormulario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Cliente/Form.fxml"));
            System.out.println(cliente_container);
            cliente_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }

}
