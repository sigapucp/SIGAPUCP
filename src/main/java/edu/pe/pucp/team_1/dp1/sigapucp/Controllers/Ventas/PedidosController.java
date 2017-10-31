/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Moneda;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

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
    private TableColumn<OrdenCompra, String> pedidoId;
    @FXML
    private TableColumn<OrdenCompra, String> clientePedido;
    @FXML
    private Button agregarProducto;
    @FXML
    static Stage modal_stage = new Stage();
    
    ArrayList<String> possiblewords = new ArrayList<>();    
    
    AutoCompletionBinding<String> autoCompletionBinding;
    
    private List<Cliente> autoCompletadoList;    
    
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
        String pedidoId = pedidoSearch.getText();
        String cliente = clienteSearch.getText();
        Integer clienteId = null;
        String estado = ( estadoSearch.getSelectionModel().getSelectedItem() == null ) ? "" : estadoSearch.getSelectionModel().getSelectedItem().toString();
        tempPedidos = OrdenCompra.findAll();
        if(pedidoId!=null&&!pedidoId.isEmpty()) {            
            tempPedidos = tempPedidos.stream().filter(p -> p.getString("cotizacion_cod").equals(pedidoId)).collect(Collectors.toList());
        }
        if (clienteId!=null) {
            tempPedidos = tempPedidos.stream().filter(p -> p.getString("client_id").equals(clienteId)).collect(Collectors.toList());
        }
        if(estado!=null&&!estado.isEmpty()) {
            tempPedidos = tempPedidos.stream().filter(p -> p.get("estado").equals(estado)).collect(Collectors.toList());
        }
        refrescarTabla();
        try {                        
        } catch (Exception e) { 
        }
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
        autoCompletadoList = Cliente.findAll();
        clienteToString();
        autoCompletionBinding = TextFields.bindAutoCompletion(clienteSh, possiblewords);
        autoCompletionBinding.addEventHandler(EventType.ROOT, (event) -> {
            handleAutoCompletar();
        });
        llenar_tabla();
        llenar_combobox();
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
    
    private void clienteToString() {
        ArrayList<String> words = new ArrayList<>();
        for (Cliente cliente : autoCompletadoList){
            words.add(cliente.getString("nombre"));
        }
        possiblewords = words;
    }
    
    private void llenar_combobox(){
        try{    
            //combobox estados
            estadoSearch.getItems().addAll("preparando pedido", "En almacen", "Listo para despachar");
            //combobox monedas
            List<String> monedas_combo_box = new ArrayList<String>();
            LazyList<Moneda> lista_monedas = Moneda.findAll();
            for(Moneda moneda : lista_monedas){
                monedas_combo_box.add(moneda.get("nombre").toString());
            }            
            moneda.getItems().addAll(monedas_combo_box);
        }catch(Exception e){
            infoController.show("El producto contiene errores : " + e);      
        }
    }
    
    private void llenar_tabla(){
        pedidoId.setCellValueFactory((CellDataFeatures<OrdenCompra, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("orden_compra_cod")));
        clientePedido.setCellValueFactory((CellDataFeatures<OrdenCompra, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("client_id")));
        tablaPedidos.setItems(pedidos);
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
        //Double subtotal = Double.valueOf(subTotal.getText());
        Double subtotal = 0.0;
        //Integer cantProductos = (Integer) cantProd.getValue();
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
            pedido.set("cotizacion_id", cotizacion_id);
            pedido.set("usuario_cod",usuarioActual.getString("usuario_cod"));
            pedido.set("usuario_id",usuarioActual.getInteger("usuario_id"));
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
        try {
            pedidos.removeAll(pedidos);
            for (OrdenCompra pedido : tempPedidos){
                pedidos.add(pedido);
            }
            tablaPedidos.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void inhabilitar_formulario() {
        pedidoForm.setDisable(true);
        pedidoTable.setDisable(true);
    }
    
    private void handleAutoCompletar() {
        int i = 0;
        for (Cliente cliente : autoCompletadoList){
            if (cliente.getString("nombre").equals(clienteSh.getText())){
                System.out.println(cliente.getString("direccion_despacho"));
                System.out.println(cliente.getString("direccion_facturacion"));
                envioDir.setText(cliente.getString("direccion_despacho"));
                factDir.setText(cliente.getString("direccion_facturacion"));
            }
        }
    }
    
    @Override
    public Menu.MENU getMenu(){
        return Menu.MENU.Pedidos;
    }
}
