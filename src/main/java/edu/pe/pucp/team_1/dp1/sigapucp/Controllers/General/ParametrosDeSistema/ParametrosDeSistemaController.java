/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.General.ParametrosDeSistema;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ErrorAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Sistema.ParametroSistema;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private InformationAlertController infoController;

    /**
     * Initializes the controller class.
     */
    
    public  ParametrosDeSistemaController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        errorController = new ErrorAlertController();
        infoController = new InformationAlertController();
    }
    
    @Override
    public void guardar()
    {
        try {
            String nombre = ParametroSeleccion.getSelectionModel().getSelectedItem();
            ParametroSistema parametro = parametros.stream().filter(x->x.getString("nombre").equals(nombre)).collect(Collectors.toList()).get(0); 
            String valor = ParametroValor.getText();
            if(parametro.getInteger("parametro_id")<=3)
            {
                Integer prioridad = Integer.valueOf(valor);
                if(prioridad >3 || prioridad < 1)
                {
                    errorController.show("Error de Valor. La prioridades debe ser entre 1,2,3", valor);
                    return;
                }
            }
            Base.openTransaction();            
            parametro.set("valor",valor);
            parametro.set("last_user_change",usuarioActual.getString("usuario_cod"));
            parametro.saveIt();
            infoController.show("Se edito el parametro satisfactoriamente:");
            Base.commitTransaction();
            
        } catch (Exception e) {
            errorController.show("Error de Valor. La prioridades debe ser entre 1,2,3", e.getMessage());
            Base.rollbackTransaction();
        }        
    }
    
    @Override
    public void cargar(){
        Runtime r = Runtime.getRuntime();
        Process p;
        ProcessBuilder pb;
        r = Runtime.getRuntime();
        pb = new ProcessBuilder( 
            "C:\\Program Files\\PostgreSQL\\10\\bin\\pg_restore.exe",
            "--host=200.16.7.146",
            "--port=5432",
            "--username=sigapucp",
            "--dbname=sigapucp_db_admin",
            "--role=postgres",
            "--password=sigapucp",
            "--verbose",
           "D:\\sathish\\rawDatabase.backup");
        pb.redirectErrorStream(true);
        try {
            p = pb.start();
            InputStream is = p.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String ll;
            while ((ll = br.readLine()) != null) {
             System.out.println(ll);
            }
        } catch (IOException ex) {
            Logger.getLogger(ParametrosDeSistemaController.class.getName()).log(Level.SEVERE, null, ex);
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
            infoController.show("Error al mostrar parametro: " + e.getMessage());
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
            infoController.show("Error en cargar parametros: " + e.getMessage());
        }
    }    
    
}
