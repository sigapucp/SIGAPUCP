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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}