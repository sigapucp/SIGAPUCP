/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.Envio;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenCompraProductoArgs;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.print.DocFlavor;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Gustavo
 */
public class OrdenesDeSalidaController  extends Controller{
    //LOGICA
        //--------------------------------------------------//
    private InformationAlertController infoController; 
    private List<Envio> envios_disponibles;
    //MODALES FLUJO
        //--------------------------------------------------//
    Stage modal_stage = new Stage();    
    

   private void setAgregarEnvios() throws Exception
    {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarEnviosOrdenSalida.fxml"));
            AgregarEnviosController controller = new AgregarEnviosController(envios_disponibles);
            loader.setController(controller);
            Scene modal_content_scene = new Scene((Parent)loader.load());
            modal_stage.setScene(modal_content_scene);       
                /*

            controller.devolverProductoEvent.addHandler((Object sender, agregarOrdenCompraProductoArgs args) -> {
                producto_devuelto = args.orden_compra_producto;
            });                 
*/
        }catch(Exception e){
            infoController.show("No se pudo agregar los productos : " + e.getMessage());
        }
    }    
    @FXML
    private void handleAgregarEnvio(ActionEvent event) throws IOException{
        try{
            modal_stage.showAndWait();
            setAgregarEnvios();            
        }catch(Exception e){
            infoController.show("No se pudo agregar los envios : " + e.getMessage());
        }

    }    
   
    public OrdenesDeSalidaController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        infoController = new InformationAlertController();
    }    
        
    public void initialize(DocFlavor.URL location, ResourceBundle resources) {  
        try {
        } catch (Exception ex) {
            infoController.show("No se pudo cargar la ventana envios : " + ex.getMessage());
        }
    }    
        
    
}
