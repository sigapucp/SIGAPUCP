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
import javafx.scene.layout.AnchorPane;
import org.javalite.activejdbc.Base;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import java.io.IOException;
import java.util.List;
import javafx.event.ActionEvent;

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
    private ComboBox estadoBusq;
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
    private TextField telf;
    @FXML
    private AnchorPane cliente_formulario;
    
    /**
     * Initializes the controller class.
     */
    
    public String obtener_tipo_cliente(){
        String tipo_cliente = "";
        tipo_cliente = (persoNatu.isSelected()) ? "persona natural" : "";
        tipo_cliente = (persoJuri.isSelected()) ? "persona natural" : "";
        return tipo_cliente;
    }
  
    @Override
    public void crear() {
        try{
            System.out.println("empezando a crear");
            Cliente nuevo_cliente = new Cliente();
            nuevo_cliente.asignar_atributos(clienteSh.getText(), repLegal.getText(), telf.getText(), ruc.getText(), dni.getText(), obtener_tipo_cliente(), envioDir.getText(), factDir.getText());
            if ( nuevo_cliente.saveIt()){
                System.out.println("todo Ok");
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }    
    
    public void nuevo(){
       habilitar_formulario();
       limpiar_formulario();
    }
    
    public void limpiar_formulario(){
        clienteSh.clear();
        dni.clear();
        envioDir.clear();
        factDir.clear();
        persoNatu.setSelected(false);
        persoJuri.setSelected(false);
        ruc.clear();
        repLegal.clear();
        telf.clear();
    }
    
    public void habilitar_formulario(){
        cliente_formulario.setDisable(false);
    }
    
    public void inhabilitar_formulario (){
        cliente_formulario.setDisable(true);
    }
    
    @FXML
    public void buscar_cliente(ActionEvent event) throws IOException{
        try{
            List<Cliente> clientes = Cliente.where("ruc = ? or dni = ? or nombre = ? or estado = ? ", rucBusq.getText(), dniBusq.getText(), nombreBusq.getText(), estadoBusq.getSelectionModel().getSelectedItem().toString());
        }
        catch( Exception e){
            System.out.println(e);
        }
    }
    
    public void llenar_estado_social_busqueda(){
        estadoBusq.getItems().addAll("persona natural", "persona juridica");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Base.open();
        inhabilitar_formulario();
        llenar_estado_social_busqueda();
        persoNatu.setOnAction(e -> manejarAreaTexto(ruc,repLegal));
        persoJuri.setOnAction(e -> manejarAreaTexto(dni));
    }    
    
    private void manejarAreaTexto(TextField texto, TextField texto2){    
        if (persoNatu.isSelected()){
            texto.setDisable(true);
            texto2.setDisable(true);
            dni.setDisable(false);
            persoJuri.setSelected(false);
        }
    }
    
    private void manejarAreaTexto(TextField texto){
        if (persoJuri.isSelected()){
            texto.setDisable(true);
            ruc.setDisable(false);
            repLegal.setDisable(false);
            persoNatu.setSelected(false);
        }
        
    } 
    
}
