/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
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
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    
    
    @FXML
    private TableColumn<Cliente, String> columna_ruc;
    @FXML
    private TableColumn<Cliente, String> columna_dni;
    @FXML
    private TableColumn<Cliente, String> columna_nombre;
    @FXML
    private TableView<Cliente> tabla_clientes;
    
    private Cliente cliente_seleccioando;
    private Boolean crear_nuevo;
    private InformationAlertController infoController;
    private List<Cliente> clientes;
    private final ObservableList<Cliente> masterData = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    
    public ClientesController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
                          
        
        infoController = new InformationAlertController();
                
        cliente_seleccioando = null;
        crear_nuevo = false;        
    }
    
    public String obtener_tipo_cliente(){
        String tipo_cliente = "";
        tipo_cliente = (persoNatu.isSelected()) ? "persona natural" : "";
        tipo_cliente = (persoJuri.isSelected()) ? "persona natural" : "";
        return tipo_cliente;
    }
    
    public void crear_cliente(){
        try{
            Base.openTransaction();  
            Cliente nuevo_cliente = new Cliente();
            nuevo_cliente.asignar_atributos(clienteSh.getText(), repLegal.getText(), telf.getText(), ruc.getText(), dni.getText(), obtener_tipo_cliente(), envioDir.getText(), factDir.getText());
            nuevo_cliente.saveIt();
            Base.commitTransaction();
            infoController.show("El cliente ha sido creado satisfactoriamente"); 
        }
        catch(Exception e){
            System.out.println(e);
            Base.rollbackTransaction();
        }        
    }
    
    public void editar_cliente(Cliente cliente){
        try{
            cliente.asignar_atributos(clienteSh.getText(), repLegal.getText(), telf.getText(), ruc.getText(), dni.getText(), obtener_tipo_cliente(), envioDir.getText(), factDir.getText());
            cliente.saveIt();
            infoController.show("El cliente ha sido editado creado satisfactoriamente"); 
        }
        catch(Exception e){
            //Base.rollbackTransaction();
        }
    }
    
    
    @Override
    public void guardar() {
        if (crear_nuevo){
            crear_cliente();
            limpiar_formulario();
        }else{
            if (cliente_seleccioando == null) return;
            editar_cliente(cliente_seleccioando);
        }
        clientes = Cliente.findAll();
        cargar_tabla_index();
    }    
    
    public void nuevo(){
       crear_nuevo = true;
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

    @FXML
    public void mostrar_detalle_cliente(ActionEvent event) throws IOException{
        try{
            Cliente registro_seleccionado = tabla_clientes.getSelectionModel().getSelectedItem();
            cliente_formulario.setDisable(false);
            //Cliente cliente = Cliente.findFirst("nombre = ?", registro_seleccionado.getString("nombre"));
            if (registro_seleccionado.getString("tipo_cliente").equals("persona natural")){
                dni.setText(registro_seleccionado.getString("dni"));
                ruc.setDisable(true);
                persoNatu.setSelected(true);
                persoJuri.setSelected(false);
            }else if (registro_seleccionado.getString("tipo_cliente").equals("persona juridica")){
                ruc.setText(registro_seleccionado.getString("ruc"));
                dni.setDisable(true);
                persoNatu.setSelected(false);
                persoJuri.setSelected(true);
            }
            clienteSh.setText(registro_seleccionado.getString("nombre"));
            telf.setText(registro_seleccionado.getString("telef_contacto"));
            repLegal.setText(registro_seleccionado.getString("nombre_contacto"));
            envioDir.setText(registro_seleccionado.getString("direccion_despacho"));
            factDir.setText(registro_seleccionado.getString("direccion_facturacion"));
            
            
        }
        catch( Exception e){
            System.out.println(e);
        }
    }  

    
    public void habilitar_formulario(){
        cliente_formulario.setDisable(false);
    }
    
    public void inhabilitar_formulario (){
        cliente_formulario.setDisable(true);
    }
    
    public void limpiar_tabla_index(){
        tabla_clientes.getItems().clear();
    }
    
    public boolean cumple_condicion_busqueda(Cliente cliente, String ruc, String dni, String nombres, String estado){
        boolean match = true;
        if ( ruc.equals("") && dni.equals("") && nombres.equals("") && estado.equals("")){
            match = false;
        }
        else {
            match = (!ruc.equals("")) ? (match && (cliente.get("ruc")).equals(ruc)) : true;
            match = (!dni.equals("")) ? (match && (cliente.get("dni")).equals(dni)) : true;
            match = (!nombres.equals("")) ? (match && (cliente.get("nombre")).equals(nombres)) : true;
            match = (!estado.equals("")) ? (match && (cliente.get("tipo_cliente")).equals(estado)) : true;
        }
        return match;
    }
    @FXML
    public void buscar_cliente(ActionEvent event) throws IOException{
        clientes = Cliente.findAll();
        masterData.clear();
        String estado = ( estadoBusq.getSelectionModel().getSelectedItem() == null ) ? "" : estadoBusq.getSelectionModel().getSelectedItem().toString();        
        try{
            for(Cliente cliente : clientes){
                if (cumple_condicion_busqueda(cliente, rucBusq.getText(), dniBusq.getText(), nombreBusq.getText(), estado)){
                    masterData.add(cliente);
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void llenar_estado_social_busqueda(){
        estadoBusq.getItems().addAll("persona natural", "persona juridica");
    }
    
    public void control_check_box(){
        persoNatu.setOnAction(e -> manejarAreaTexto(ruc,repLegal));
        persoJuri.setOnAction(e -> manejarAreaTexto(dni));        
    }
    
    public void cargar_tabla_index(){
        limpiar_tabla_index();
        for( Cliente cliente : clientes){
            masterData.add(cliente);
        }
        tabla_clientes.setEditable(false);
        columna_ruc.setCellValueFactory((TableColumn.CellDataFeatures<Cliente, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("ruc")));
        columna_dni.setCellValueFactory((TableColumn.CellDataFeatures<Cliente, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("dni")));
        columna_nombre.setCellValueFactory((TableColumn.CellDataFeatures<Cliente, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
        tabla_clientes.setItems(masterData);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inhabilitar_formulario();
        llenar_estado_social_busqueda();
        control_check_box();
        clientes = null;
        clientes = Cliente.findAll();
        cargar_tabla_index();
        tabla_clientes.getSelectionModel().selectedIndexProperty().addListener((obs,oldSelection,newSelection) -> {
            if (newSelection != null){
                cliente_seleccioando = tabla_clientes.getSelectionModel().getSelectedItem();                
                tabla_clientes.getSelectionModel().clearSelection();        
            }
        });
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
