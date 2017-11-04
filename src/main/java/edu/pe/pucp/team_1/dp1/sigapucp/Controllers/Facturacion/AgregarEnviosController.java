/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.Envio;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarEnviosArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenCompraProductoArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Gustavo
 */
public class AgregarEnviosController implements  Initializable{
    //TABLA
        //----------------------------------------------------------//
    @FXML
    private TableView<Envio> tabla_envios;
    @FXML
    private Button boton_agregar_envio;
    //LOGICA
        //----------------------------------------------------------//
    private InformationAlertController infoController;
    private Envio envio_busqueda;
    
    @FXML
    private void agregar_envio(ActionEvent event){
        envio_busqueda = tabla_envios.getSelectionModel().getSelectedItem();
        if (envio_busqueda == null){
            infoController.show("No ha seleccionado algun envio");
            return;            
        }
        devolverEnvioEvent.fire(this, new agregarEnviosArgs(envio_busqueda));
        Stage stage = (Stage) boton_agregar_envio.getScene().getWindow();    
        stage.close();        
    }
    public AgregarEnviosController(List<Envio> envios){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");  
        infoController = new InformationAlertController();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {  
            //llenar_tabla_envios
        } catch (Exception e) {
            infoController.show("Problemas en la inicializaciond de busqueda de Producto");
        }       
    }
    
    public Event<agregarEnviosArgs> devolverEnvioEvent = new Event<>();
    
    
}
