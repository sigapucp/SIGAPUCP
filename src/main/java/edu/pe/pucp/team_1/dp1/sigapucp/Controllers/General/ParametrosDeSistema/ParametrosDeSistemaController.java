/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.General.ParametrosDeSistema;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ErrorAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Sistema.ParametroSistema;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Jauma
 */
public class ParametrosDeSistemaController extends Controller{

    @FXML
    private TextArea ParametroDescripcion;
    @FXML
    private ComboBox<String> ParametroSeleccion;
    @FXML
    private TextField ParametroValor;
    private List<ParametroSistema> parametros;
    private ErrorAlertController errorController;

    /**
     * Initializes the controller class.
     */
    
    public  ParametrosDeSistemaController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        errorController = new ErrorAlertController();
    }
    
    @Override
    public void guardar()
    {
        try {
            String nombre = ParametroSeleccion.getSelectionModel().getSelectedItem();
            ParametroSistema parametro = parametros.stream().filter(x->x.getString("nombre").equals(nombre)).collect(Collectors.toList()).get(0); 
            Base.openTransaction();
            String valor = ParametroValor.getText();
            parametro.set("valor",valor);
            parametro.set("last_user_change",usuarioActual.getString("usuario_cod"));
            parametro.saveIt();
            Base.commitTransaction();
        } catch (Exception e) {
            Base.rollbackTransaction();
        }        
    }
    
    private void setVisibleParametro(String nombre)
    {
        try {
            ParametroSistema parametro = parametros.stream().filter(x->x.getString("nombre").equals(nombre)).collect(Collectors.toList()).get(0);
                        
            ParametroSeleccion.getSelectionModel().select(parametro.getString("nombre"));
            ParametroValor.setText(parametro.getString("valor"));
            ParametroDescripcion.setText(parametro.getString("descripcion"));                        
        } catch (Exception e) {
        }                    
    }
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.ParametrosdeSistema;        
    }        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            parametros = ParametroSistema.findAll();
            
            ObservableList<String> nombreParametro = FXCollections.observableArrayList();   
            
            for(ParametroSistema parametro:parametros)
            {
                nombreParametro.add(parametro.getString("nombre"));
            }
            ParametroSeleccion.setItems(nombreParametro);
            
            
            ParametroSeleccion.valueProperty().addListener(new ChangeListener<String>() {           
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    setVisibleParametro(newValue);
                }
            });
        } catch (Exception e) {
        }
    }    
    
}
