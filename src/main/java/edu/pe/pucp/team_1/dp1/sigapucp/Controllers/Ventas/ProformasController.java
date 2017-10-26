/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.ContenidoPrincipalController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.abrirDetallesArgs;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import static javafx.scene.input.KeyCode.T;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

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
    public DatePicker fechaProfSh;
    @FXML
    public TextField clienteSh;
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
    @FXML
    private AnchorPane proforma_tabla;
    @FXML
    private AnchorPane proforma_formulario;
    @FXML
    private SpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0,1);
    
    public IEvent<abrirDetallesArgs> abrirDetalle;
    
    @FXML private ContenidoPrincipalController contenidoPrincipalController = new ContenidoPrincipalController();
    
    @FXML static Stage modal_stage = new Stage();

    /**
     * Initializes the controller class.
     */
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dolProf.setOnAction(e -> manejarRadBttnDol());
        solesProf.setOnAction(e -> manejarRadBttnSol());
        abrirDetalle = new Event<>();
        cantProd.setValueFactory(valueFactory);
        if (!Base.hasConnection()) Base.open();
        inhabilitar_formulario();
        Parent modal_content;
        try {
            modal_content = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Proformas/AgregarProformas.fxml"));
            Scene modal_content_scene = new Scene(modal_content);
            modal_stage.setScene(modal_content_scene);
            if (modal_stage.getModality() == null) modal_stage.initModality(Modality.APPLICATION_MODAL);
            //modal_stage.initOwner((Stage) pedidoContainer.getScene().getWindow());
            modal_stage.setScene(modal_content_scene);
        } catch (IOException ex) {
            Logger.getLogger(PedidosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
   
    @Override
    public void nuevo(){
       habilitar_formulario();
       limpiar_formulario();
    }
    
    public void inhabilitar_formulario (){
        proforma_formulario.setDisable(true);
        proforma_tabla.setDisable(true);
    }
    
    public void habilitar_formulario (){
        proforma_formulario.setDisable(false);
        proforma_tabla.setDisable(false);
    }
    
    public void limpiar_formulario(){
        clienteSh.clear();
        telfSh.clear();
        correoSh.clear();
        fechaProfSh.setValue(null);
        producto.clear();
        productosProf.getItems().clear();
        subTotalDol.clear();
        igvDol.clear();
        totalDol.clear();
        subTotalSol.clear();
        igvSol.clear();
        totalSol.clear();
        solesProf.setSelected(false);
        dolProf.setSelected(false);
        SpinnerValueFactory newvalueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0,1);
        cantProd.setValueFactory(newvalueFactory);
    }
    

    private void manejarRadBttnDol(){
        solesProf.setSelected(false);
    }
//    
    @FXML
    private void handleAgregarProducto(ActionEvent event) {
        modal_stage.showAndWait();
    }
    
    @FXML //Aun falta que de la proforma pueda generar un pedido. tanto en navegabilidad como comunicacion
    //de controllers
    private void handleGenerarPedido(ActionEvent event) {
        abrirDetallesArgs args = new abrirDetallesArgs();
        System.out.println("estoy aqui debuggeando");
        args.setNombreController("Pedidos");
        args.setNombreModulo("Ventas");
        
        abrirDetalle.fire(this, args);
    }
    
    private void manejarRadBttnSol(){
        dolProf.setSelected(false);
    }
    
}
