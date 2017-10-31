/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.AgregarProductosController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import static edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas.ProformasController.modal_stage;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cotizacion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Moneda;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoArgs;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.control.Label;
import javafx.scene.control.SpinnerValueFactory;
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
    private TextField BuscarCliente;
    @FXML
    private ComboBox<String> BuscarEstado;
    @FXML
    private TextField BuscarCodigo;
    @FXML
    private TableView<OrdenCompra> TablaPedido;
    @FXML
    private TableColumn<OrdenCompra, String> CodigoPedidoColumna;
    @FXML
    private TableColumn<OrdenCompra, String> ClientePedidoColumna;
    @FXML
    private TableColumn<OrdenCompra, String> EstadoPedidoColumna;
    @FXML
    private AnchorPane pedidoTable;
    private SpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 1);
    @FXML
    private Spinner<Integer> cantProd;
    @FXML
    private TextField VerProducto;
    @FXML
    private Button buscarProducto;
    @FXML
    private TextField subTotal;
    @FXML
    private TextField igvPedido;
    @FXML
    private TextField totalPedido;
    @FXML
    private TableView<OrdenCompraxProducto> TablaProductos;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> codProdColumn;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> nombreProdColumn;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> cantProdColumn;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> precioUnitarioColumn;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> descProdColumna;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> fleteProdColumn;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> subTotalProdColumna;
    @FXML
    private AnchorPane pedidoForm;
    @FXML
    private RadioButton TipoDocBoleta;
    @FXML
    private RadioButton TipoDocFactura;
    @FXML
    private TextField VerCliente;
    @FXML
    private Label VerDocumentoLabel;
    @FXML
    private TextField VerDocumento;
    @FXML
    private DatePicker VerFecha;
    private TextField VerDireccionEnvio;
    private TextField factDir;
    @FXML
    private ComboBox<String> VerMoneda;
    @FXML
    private TextField VerVendedor;
    @FXML
    private Label LabelPedido;
  
    static Stage modal_stage = new Stage();
    
    ArrayList<String> possiblewords = new ArrayList<>();        
    AutoCompletionBinding<String> autoCompletionBinding;    
    private List<Cliente> autoCompletadoList;        
    
    private List<TipoProducto> autoCompletadoProductoList;      
    ArrayList<String> possiblewordsProducto = new ArrayList<>();      
    AutoCompletionBinding<String> autoCompletionBindingProducto;
    
    private List<Usuario> autoCompletadoUsuarioList;      
    ArrayList<String> possiblewordsUsuario = new ArrayList<>();      
    AutoCompletionBinding<String> autoCompletionBindingUsuario;
    
    private final ObservableList<OrdenCompra> pedidos = FXCollections.observableArrayList();
    private final ObservableList<OrdenCompraxProducto> productos = FXCollections.observableArrayList();
            
    private InformationAlertController infoController;
    
    private OrdenCompra pedidoSeleccionado;
    
    private Cliente clienteSeleccionado;
    
    private TipoProducto productoDevuelto;
    
    private Usuario vendedorSelecionado;            
     
    private Boolean crearNuevo = false;           
  
    private Double IGV;    
    @FXML
    private TextField VerDireccionFacturacion;
    @FXML
    private TextField VerDireccionDespacho;

           
    /**
     * Initializes the controller class.
     */
    
    public PedidosController(){
        if (!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        List<OrdenCompra> tempPedidos = OrdenCompra.findAll();
        for (OrdenCompra pedido : tempPedidos){
            pedidos.add(pedido);
        }
        infoController = new InformationAlertController();
        pedidoSeleccionado = null;
        vendedorSelecionado = null;
        productoDevuelto = null;
        crearNuevo = false;
    }
    
      
    private void llenar_combobox() throws Exception
    {
        ObservableList<String> monedas = FXCollections.observableArrayList();            
        monedas.addAll(Moneda.findAll().stream().map(x -> x.getString("nombre")).collect(Collectors.toList()));
        VerMoneda.setItems(monedas);

        ObservableList<String> estados = FXCollections.observableArrayList();       
        estados.add("");
        estados.addAll(Arrays.asList(OrdenCompra.ESTADO.values()).stream().map(x->x.name()).collect(Collectors.toList()));   
        BuscarEstado.setItems(estados);      
        cantProd.setValueFactory(valueFactory);   
    }
    
    private void llenar_tabla_index() throws Exception{
        List<OrdenCompra> tempPedido = OrdenCompra.findAll();
        pedidos.clear();
        productos.clear();
        
        CodigoPedidoColumna.setCellValueFactory((CellDataFeatures<OrdenCompra, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("orden_compra_cod")));   
        ClientePedidoColumna.setCellValueFactory((CellDataFeatures<OrdenCompra, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        EstadoPedidoColumna.setCellValueFactory((CellDataFeatures<OrdenCompra, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("estado")));
        
        codProdColumn.setCellValueFactory((CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        nombreProdColumn.setCellValueFactory((CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
        cantProdColumn.setCellValueFactory((CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cantidad")));
        precioUnitarioColumn.setCellValueFactory((CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("precio_unitario")));
        descProdColumna.setCellValueFactory((CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("descuento")));
        fleteProdColumn.setCellValueFactory((CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("flete")));
        subTotalProdColumna.setCellValueFactory((CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("subtotal_final")));        
        
        pedidos.addAll(tempPedido);
        TablaPedido.setItems(pedidos);
        TablaProductos.setItems(productos);
    }
    
      private void llenar_autocompletado() throws Exception
    {
        autoCompletadoList = Cliente.findAll();
        autoCompletadoProductoList = TipoProducto.findAll();
        autoCompletadoUsuarioList = Usuario.findAll();

        clienteToString();
        autoCompletionBinding = TextFields.bindAutoCompletion(VerCliente, possiblewords);
        autoCompletionBinding.addEventHandler(EventType.ROOT, (event) -> {
            handleAutoCompletar();
        });

        productoToString();
        autoCompletionBindingProducto = TextFields.bindAutoCompletion(VerProducto, possiblewordsProducto);
        autoCompletionBindingProducto.addEventHandler(EventType.ROOT, (event) -> {
        handleAutoCompletarProducto();
        });        
        
        usuarioToString();
        autoCompletionBindingUsuario = TextFields.bindAutoCompletion(VerVendedor, possiblewordsUsuario);
        autoCompletionBindingUsuario.addEventHandler(EventType.ROOT, (event) -> {
        handleAutoCompletarUsuario();
        });        
    }
      
    private void setAgregarProductos() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarProductos.fxml"));
        AgregarProductosController controller = new AgregarProductosController();
        loader.setController(controller);                      
        Scene modal_content_scene = new Scene((Parent)loader.load());
        modal_stage.setScene(modal_content_scene);
        if(modal_stage.getModality() != Modality.APPLICATION_MODAL) modal_stage.initModality(Modality.APPLICATION_MODAL);    

        controller.devolverProductoEvent.addHandler((Object sender, agregarProductoArgs args) -> {
            productoDevuelto = args.producto;
        });
        cantProd.setValueFactory(valueFactory);        
    }
         
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
//        mismaDir.setOnAction(e -> manejoTextoChckBox(factDir,mismaDir));
        try {
            
            TipoDocBoleta.setOnAction(e -> manejoTextoRadBttn1());
            TipoDocFactura.setOnAction(e -> manejoTextoRadBttn2());
            
            llenar_tabla_index();
            llenar_combobox();
            llenar_autocompletado();
            setAgregarProductos();
                    
        } catch (Exception e) {            
            infoController.show("No se pudo inicializar el menu de Ordenes de Compra: " + e.getMessage());
        }                     
        //inhabilitar_formulario();                      
    }   
    
    @Override
    public void nuevo(){
        crearNuevo = true;
        //habilitar_formulario();
        VerVendedor.setText(usuarioActual.getString("usuario_cod"));
        vendedorSelecionado = usuarioActual;
        LabelPedido.setText("Nuevo Pedido");
    }
    
    @Override
    public void guardar(){
        if (crearNuevo){
            crearPedido();
        } else {
            if (pedidoSeleccionado == null){ 
                infoController.show("No ha seleccionado ninguna Orden de Compra");            
                return;
            }
            editarPedido(pedidoSeleccionado);
        }
        crearNuevo = false;
        RefrescarTabla(OrdenCompra.findAll());
    }
    
    @FXML
    void buscarPedido(ActionEvent event) {        
        try {
            String pedidoId = BuscarCodigo.getText();
            String cliente = BuscarCliente.getText();
            String estado = BuscarEstado.getSelectionModel().getSelectedItem();
            Integer clienteId = Cliente.first("nombre = ?", cliente).getInteger("client_id");

            List <OrdenCompra> tempPedidos = OrdenCompra.findAll();
            if(pedidoId!=null&&!pedidoId.isEmpty()) {            
                tempPedidos = tempPedidos.stream().filter(p -> p.getString("orden_compra_cod").equals(pedidoId)).collect(Collectors.toList());
            }
            if (cliente!=null) {
                tempPedidos = tempPedidos.stream().filter(p -> p.getString("client_id").equals(clienteId)).collect(Collectors.toList());
            }
            if(estado!=null&&!estado.isEmpty()) {
                tempPedidos = tempPedidos.stream().filter(p -> p.get("estado").equals(estado)).collect(Collectors.toList());
            }     
            RefrescarTabla(tempPedidos);       
        } catch (Exception e) {
            infoController.show("Eror al buscar Orden de Compra " + e.getMessage());
        }             
    }

    @FXML
    void visualizarPedido(ActionEvent event) {

    }    
    
    
    private void clienteToString() throws Exception{
        ArrayList<String> words = new ArrayList<>();
        for (Cliente cliente : autoCompletadoList){
            words.add(cliente.getString("nombre"));
        }
        possiblewords = words;
    }
  
    
    private void manejoTextoChckBox(TextField texto, CheckBox seleccionado){
        if (seleccionado.isSelected()) {
            texto.setDisable(true);
        }else{
            texto.setDisable(false);
        }
    }
    
    private void manejoTextoRadBttn1(){
        TipoDocFactura.setSelected(false);
        VerDocumentoLabel.setText("DNI:");        
    }
    
    private void manejoTextoRadBttn2(){
        TipoDocBoleta.setSelected(false);
        VerDocumentoLabel.setText("RUC:");        
    }
    
    @FXML
    private void handleAgregarProducto(ActionEvent event) throws IOException{
        modal_stage.showAndWait();
    }

    private void habilitar_formulario() {
        pedidoForm.setDisable(false);
        pedidoTable.setDisable(false);
    }
    
    private void editarPedido(OrdenCompra orden)
    {
        
    }

    private void crearPedido() {
        //extraemos la informacion del formulario:
//        Integer clienteId = clienteAsociado.getInteger("client_id");
//        String vendedor = vendedorSh.getText();
//        LocalDate fechaLocal = fechaPed.getValue();
//        if (fechaLocal == null){ 
//            infoController.show("Debe seleccionar la fecha de emision");
//            return;
//        }
//        Date fecha = Date.from(fechaLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
//        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        String fechaEmision = dateFormat.format(fecha);
//        Double subtotal = Double.valueOf(subTotal.getText());
//        //Double subtotal = 0.0;
//        //Integer cantProductos = (Integer) cantProd.getValue();
//        String orden_compra_cod = "ORD";
//        Integer monedaId = 1;
//        if (moneda.getValue() == null) {
//            infoController.show("Debe seleccionar la moneda usada para la orden de compra");
//            return;
//        }
//        LazyList<Moneda> lista_monedas = Moneda.findAll();
//        for (Moneda monedita : lista_monedas){
//            if (monedita.get("nombre").equals(moneda.getValue())){
//                monedaId = monedita.getInteger("moneda_id");    
//            }
//        }
//        String usuario = usuarioActual.getString("usuario_cod"); 
//        char flag = '1';
//        Integer usuario_id = usuarioActual.getInteger("usuario_id");
//        Integer proforma = 1;
//        asignar_data(orden_compra_cod, clienteId, fechaEmision, subtotal, usuario, flag,usuario, usuario_id , monedaId, proforma);
    }
    
    @FXML
    private void agregaProducto(ActionEvent event) {
    }

    @FXML
    private void eliminarProducto(ActionEvent event) {
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
            pedido.set("usuario_cod",usuario);
            pedido.set("usuario_id",usuario_id);
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
   private void RefrescarTabla(List<OrdenCompra> tempPedidos)
    {
        try {
            if(tempPedidos == null) return;
            pedidos.removeAll(tempPedidos);                        
            for (OrdenCompra pedido : tempPedidos) {
                pedidos.add(pedido);
            }               
            TablaPedido.getColumns().get(0).setVisible(false);
            TablaPedido.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("Error al refrescar Tabla: " + e.getMessage());
        }                                  
    }

    private void inhabilitar_formulario() {
        pedidoForm.setDisable(true);
        pedidoTable.setDisable(true);
    }
    
    private void handleAutoCompletar() {
        
        int i = 0;
        for (Cliente cliente : autoCompletadoList){
            if (cliente.getString("nombre").equals(VerCliente.getText())){
                clienteSeleccionado = cliente;
                
                String direccionFacturacion = cliente.getString("direccion_facturacion");
                String direccionDespacho = cliente.getString("direccion_despacho");
                
                String tipo_cliente = cliente.getString("tipo_cliente");
                String dni = cliente.getString("dni");
                String ruc = cliente.getString("ruc");
                                
                VerDireccionDespacho.setText(direccionDespacho);
                VerDireccionFacturacion.setText(direccionFacturacion);
                
                if(tipo_cliente.equals(Cliente.TIPO.PersonaNatural.name()))
                {
                    VerDocumentoLabel.setText("DNI:");
                    VerDocumento.setText(dni);
                    TipoDocBoleta.setSelected(true);
                    TipoDocFactura.setDisable(false);  
                    
                }else
                {
                    VerDocumentoLabel.setText("RUC:");
                    VerDocumento.setText(ruc);
                    TipoDocFactura.setSelected(true);
                    TipoDocBoleta.setDisable(true);                    
                }                                                    
//                if (cliente.getString("direccion_despacho").equals(cliente.getString("direccion_facturacion"))){ 
//                    mismaDir.setSelected(true);
//                    factDir.setDisable(true);
//                }                
            }
        }
    }
    
     private void productoToString() {
        ArrayList<String> words = new ArrayList<>();
        for (TipoProducto producto : autoCompletadoProductoList){
            words.add(producto.getString("nombre"));
        }               
        possiblewordsProducto = words;
    }          
    
    private void handleAutoCompletarProducto() {      
        for (TipoProducto tipoProducto : autoCompletadoProductoList){
            if (tipoProducto.getString("nombre").equals(VerProducto.getText())){           
                productoDevuelto = tipoProducto;             
            }
        }
    }    
    
     private void usuarioToString() {
        ArrayList<String> words = new ArrayList<>();
        for (Usuario usuario : autoCompletadoUsuarioList){
            words.add(usuario.getString("nombre"));
        }               
        possiblewordsUsuario = words;
    }          
    
    private void handleAutoCompletarUsuario() {      
        for (Usuario usuario : autoCompletadoUsuarioList){
            if (usuario.getString("nombre").equals(VerVendedor.getText())){           
                vendedorSelecionado = usuario;             
            }
        }
    }    
}
