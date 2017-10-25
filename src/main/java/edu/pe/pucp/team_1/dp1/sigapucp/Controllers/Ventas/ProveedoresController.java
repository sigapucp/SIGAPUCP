/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Proveedor;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Joel
 */
public class ProveedoresController extends Controller{

    @FXML
    private TextField proveedor_nombre;
    @FXML 
    private TextField ruc;
    @FXML
    private TextField telf;
    @FXML
    private TextField repLegal;
    @FXML
    private TextArea comentarios;
    @FXML
    private TextField ruc_busqueda;
    @FXML
    private TextField nombre_busqueda;
    @FXML
    private AnchorPane proveedor_formulario;
    
    /**
     * Initializes the controller class.
     */
    

    @Override
    public void crear() {
        try{
            System.out.println("empezando a crear");
            Proveedor nuevo_proveedor = new Proveedor();
            nuevo_proveedor.asignar_atributos(proveedor_nombre.getText(), repLegal.getText(), telf.getText(), ruc.getText(), comentarios.getText());
            if ( nuevo_proveedor.saveIt()){
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
        this.proveedor_nombre.clear();
        this.ruc.clear();
        this.telf.clear();
        this.repLegal.clear();
        this.comentarios.clear();
    }
    
    public void habilitar_formulario(){
        proveedor_formulario.setDisable(false);
    }
    
    public  void inhabilitar_formulario (){
        proveedor_formulario.setDisable(true);
    }
    
    @FXML
    public void buscar_proveedor(ActionEvent event) throws IOException{
        try{
            List<Proveedor> proveedores = Proveedor.where("provuder_ruc = ?  or name = ? ", ruc_busqueda.getText(),nombre_busqueda.getText());
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Base.close();
        Base.open();
        inhabilitar_formulario();
        
    }    
    
}
