/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Joel
 */
public class PedidosController extends Controller {
    
    @FXML
    private AnchorPane pedidoContainer;
    @FXML
    private TextField subTotal;
    @FXML
    private TextField igvPedido;
    @FXML
    private TextField totalPedido;
    @FXML
    private TextField clienteSearch;
    @FXML
    private ComboBox<String> estadoSearch;
    @FXML
    private TextField pedidoSearch;
    @FXML
    private TableView<OrdenCompra> tablaPedidos;
    @FXML
    private AnchorPane pedidoForm;
    @FXML
    private RadioButton tipoDocBoleta;
    @FXML
    private RadioButton tipoDocFactura;
    @FXML
    private TextField clienteSh;
    @FXML
    private TextField vendedorSh;
    @FXML
    private TextField ruc;
    @FXML
    private TextField dni;
    @FXML
    private DatePicker fechaPed;
    @FXML
    private TextField tipoCambio;
    @FXML
    private TextField envioDir;
    @FXML
    private TextField factDir;
    @FXML
    private CheckBox mismaDir;
    @FXML
    private ComboBox<String> moneda;
    @FXML
    private AnchorPane pedidoTable;
    @FXML
    private Spinner<?> cantProd;
    @FXML
    private TextField producto;
    @FXML
    private Button agregarProducto;
    @FXML
    static Stage modal_stage = new Stage();
    
    boolean crearNuevo;
    
    private final ObservableList<OrdenCompra> pedidos = FXCollections.observableArrayList();
    
    private List<OrdenCompra> tempPedidos;
    
    private InformationAlertController infoController;
    
    private OrdenCompra pedidoSeleccionado;
    
    

    /**
     * Initializes the controller class.
     */
    
    public PedidosController(){
        if (!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        tempPedidos = OrdenCompra.findAll();
        for (OrdenCompra pedido : tempPedidos){
            pedidos.add(pedido);
        }
        infoController = new InformationAlertController();
        pedidoSeleccionado = null;
        crearNuevo = false;
    }
    
    @Override
    public void nuevo(){
        crearNuevo = true;
        habilitar_formulario();
    }
    
    @Override
    public void guardar(){
        if (crearNuevo){
            crearPedido();
        } else {
            if (pedidoSeleccionado == null) return;
            editarPedido(pedidoSeleccionado);
        }
        refrescarTabla();
    }
    
    @FXML
    void buscarPedido(ActionEvent event) {

    }

    @FXML
    void visualizarPedido(ActionEvent event) {

    }    
    
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mismaDir.setOnAction(e -> manejoTextoChckBox(factDir,mismaDir));
        tipoDocBoleta.setOnAction(e -> manejoTextoRadBttn1());
        tipoDocFactura.setOnAction(e -> manejoTextoRadBttn2());
        inhabilitar_formulario();
        //Seteo la modal de agregar producto
        Parent modal_content;
        try {
            modal_content = FXMLLoader.load(getClass().getResource("/fxml/Ventas/Pedidos/AgregarProductos.fxml"));
            Scene modal_content_scene = new Scene(modal_content);
            modal_stage.setScene(modal_content_scene);
            if (modal_stage.getModality() == null) modal_stage.initModality(Modality.APPLICATION_MODAL);
            //modal_stage.initOwner((Stage) pedidoContainer.getScene().getWindow());
            modal_stage.setScene(modal_content_scene);
        } catch (IOException ex) {
            Logger.getLogger(PedidosController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    

    
    private void manejoTextoChckBox(TextField texto, CheckBox seleccionado){
        if (seleccionado.isSelected()) {
            texto.setDisable(true);
        }else{
            texto.setDisable(false);
        }
    }
    
    private void manejoTextoRadBttn1(){
        tipoDocFactura.setSelected(false);
        ruc.setDisable(true);
        dni.setDisable(false);
    }
    
    private void manejoTextoRadBttn2(){
        tipoDocBoleta.setSelected(false);
        dni.setDisable(true);
        ruc.setDisable(false);
    }
    
    @FXML
    private void handleAgregarProducto(ActionEvent event) throws IOException{
        modal_stage.showAndWait();
    }

    private void habilitar_formulario() {
        pedidoForm.setDisable(false);
        pedidoTable.setDisable(false);
    }

    private void crearPedido() {
        //extraemos la informacion del formulario:
        String cliente = clienteSh.getText();
        //cliente id--- se busca por nombre
        Integer clienteId = 1;
        String vendedor = vendedorSh.getText();
        LocalDate fechaLocal = fechaPed.getValue();
        Date fecha = Date.from(fechaLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fechaEmision = dateFormat.format(fecha);
        Double subtotal = Double.valueOf(subTotal.getText());
        Integer cantProductos = (Integer) cantProd.getValue();
        String orden_compra_cod = "ORD";
        int monedaId;
        if (moneda.getValue().toString().equals("SOLES")){
             monedaId = 1;
        } else {
            monedaId = 2;
        }
        String usuario = "admin"; 
        char flag = '1';
        Integer usuario_id = 1;
        Integer proforma = 1;
        asignar_data(orden_compra_cod, clienteId, fechaEmision, subtotal, usuario, flag,usuario, usuario_id , monedaId, proforma);
    }

    
    private void asignar_data(String orden_compra_cod, Integer clienteId, String fechaEmision, Double subtotal, String usuario, char flag, String usuario_cod, Integer usuario_id, Integer monedaId, Integer cotizacion_id){
        try{      
            Base.openTransaction();  
            Integer cod = Integer.valueOf(String.valueOf((Base.firstCell("select last_value from ordenescompra_orden_compra_id_seq")))) + 1;        
            orden_compra_cod = orden_compra_cod + Integer.toString(cod);
            OrdenCompra pedido = new OrdenCompra();
            pedido.set("orden_compra_cod",orden_compra_cod);
            pedido.set("client_id",clienteId);
            pedido.setDate("fecha_emision", fechaEmision);
            pedido.set("subtotal", subtotal);
            //pedido.set("estado", estado);
            //proforma.set("last_user_change",usuarioActual.getString("cotizacion_cod"));
            pedido.set("last_user_change", usuario);
            pedido.set("flag_last_operation", flag);
            pedido.set("moneda_id", monedaId);
            pedido.saveIt();
            Base.commitTransaction();
            System.out.println("Todo Correcto BD");
            infoController.show("Se ha creado la orden de compra: ORD"+String.valueOf(cod));        
        }
        catch(Exception e){
           System.out.println(e);
           Base.rollbackTransaction();
        } 
    
    }
    
    private void editarPedido(OrdenCompra pedidoSeleccionado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void refrescarTabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void inhabilitar_formulario() {
        pedidoForm.setDisable(true);
        pedidoTable.setDisable(true);
    }
    
}
