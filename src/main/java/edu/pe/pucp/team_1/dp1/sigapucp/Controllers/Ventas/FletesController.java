/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Joel
 */
public class FletesController extends Controller {

    @FXML
    private TextField busqCodigoFlete;
    @FXML
    private ComboBox<?> busqTipoFlete;
    @FXML
    private DatePicker busqFlete;
    @FXML
    private TableView<?> tabla_fletes;
    @FXML
    private TableColumn<?, ?> columna_codigo;
    @FXML
    private TableColumn<?, ?> columna_tipo;
    @FXML
    private Label labelNombrePromocion;
    @FXML
    private TextField txtFieCodigoFlete;
    @FXML
    private DatePicker dpFecha1;
    @FXML
    private DatePicker dpFecha2;
    @FXML
    private ComboBox<?> comboBoxTipoFlete;
    @FXML
    private ComboBox<?> comboxPrioridad;
    @FXML
    private Spinner<?> spCompro;
    @FXML
    private TextField txtFieCodigoProducto1;
    @FXML
    private Button botonTipo1;
    @FXML
    private Button botonCategoria1;
    @FXML
    private Spinner<?> spLlevo;
    @FXML
    private TextField txtFieCodigoProducto2;
    @FXML
    private Button botonTipo2;
    @FXML
    private Button botonCategoria2;
    @FXML
    private Spinner<?> spPorc;
    @FXML
    private TextField txtFieCodigoProducto3;
    @FXML
    private Button botonTipo3;
    @FXML
    private Button botonCategoria3;
    @FXML
    private TextField desc_concepto;

    /**
     * Initializes the controller class.
     */
    
    private void FletesController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void buscar_promocion(ActionEvent event) {
    }
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Fletes;
    }
}
