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
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.OrdenSalida;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.OrdenesSalidaxEnvio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Gustavo
 */
public class AgregarEnviosController implements  Initializable{
    //BUSQUEDA
        //----------------------------------------------------------//
    @FXML
    private TextField buscar_cliente;
    @FXML
    private TextField buscar_envio;
    @FXML
    private TextField buscar_pedido;
    //TABLA
        //----------------------------------------------------------//
    @FXML
    private TableView<Envio> tabla_envios;
    @FXML
    private TableColumn<Envio, String> columna_cod_envio;
    @FXML
    private TableColumn<Envio, String> columna_cliente_envio;
    @FXML
    private TableColumn<Envio, String> columna_pedido_envio;
    @FXML
    private TableColumn<Envio, String> columna_estado_envio;    
    @FXML
    private Button boton_agregar_envio;
    //LOGICA
        //----------------------------------------------------------//
    private InformationAlertController infoController;
    private final ObservableList<Envio> masterData = FXCollections.observableArrayList();
    private Envio envio_busqueda;
    private List<Envio> lista_envios;
    
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
    
    public void limpiar_tabla_envios(){
        tabla_envios.getItems().clear();
    }
    
    public void llenar_tabla_envios(){
        limpiar_tabla_envios();
        List<Envio> envios = Envio.where("estado = ?",Envio.ESTADO.ENPROCESO.name());
        List<OrdenesSalidaxEnvio> envios_tomados = OrdenesSalidaxEnvio.findAll();
        masterData.addAll(envios);
        for (OrdenesSalidaxEnvio envio_tomado : envios_tomados){
            for (Envio envio : envios){
                if (envio.getInteger("envio_id")==envio_tomado.getInteger("envio_id"))
                    masterData.remove(envio);
            }
        }
        
        columna_cod_envio.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("envio_cod")));
        columna_cliente_envio.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        columna_pedido_envio.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("orden_compra_cod")));
        columna_estado_envio.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("estado")));
        tabla_envios.setItems(masterData);   
            
    }
    
    public AgregarEnviosController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");  
        infoController = new InformationAlertController();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {  
            llenar_tabla_envios();
        } catch (Exception e) {
            infoController.show("Problemas en la inicializaciond de busqueda de Producto");
        }       
    }
    
    public Event<agregarEnviosArgs> devolverEnvioEvent = new Event<>();
    
    
}
