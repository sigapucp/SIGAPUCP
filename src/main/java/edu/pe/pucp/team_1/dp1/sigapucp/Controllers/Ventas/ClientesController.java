/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Joel
 */
public class ClientesController extends Controller{

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
    private Button visualizarBttn;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
