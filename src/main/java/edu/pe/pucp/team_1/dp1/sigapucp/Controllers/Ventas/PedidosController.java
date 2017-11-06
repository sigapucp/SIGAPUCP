/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.AgregarProductosController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Stock;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad.AccionLoggerSingleton;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Sistema.ParametroSistema;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.CambioMoneda;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cotizacion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.CotizacionxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Flete;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Moneda;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Promocion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.PromocionBonificacion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.PromocionCantidad;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.PromocionPorcentaje;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.cambiarMenuArgs;
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
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
import org.javalite.activejdbc.Model;

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
    @FXML
    private TextField VerDireccionFacturacion;
    @FXML
    private TextField VerDireccionDespacho;

        
    Stage modal_stage = new Stage();
    
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
    
    private ConfirmationAlertController confirmationController;
    
    private OrdenCompra pedidoSeleccionado;
    
    private Cliente clienteSeleccionado;
    
    private TipoProducto productoDevuelto;
    
    private Usuario vendedorSelecionado;            
     
    private Boolean crearNuevo = false;    
    
    private Cotizacion cotizacionAnexada = null;
  
    private Double IGV;    

    private Moneda monedaSeleccionada;
           
    /**
     * Initializes the controller class.
     */
    
    public PedidosController(){
        if (!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        List<OrdenCompra> tempPedidos = OrdenCompra.findAll();
        IGV = ParametroSistema.findFirst("nombre = ?", "IGV").getDouble("valor");
        for (OrdenCompra pedido : tempPedidos){
            pedidos.add(pedido);
        }
        infoController = new InformationAlertController();
        confirmationController = new ConfirmationAlertController();
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
        try {
            
            TipoDocBoleta.setOnAction(e -> manejoTextoRadBttn1());
            TipoDocFactura.setOnAction(e -> manejoTextoRadBttn2());
            
            llenar_tabla_index();
            llenar_combobox();
            llenar_autocompletado();
            setAgregarProductos();
            inhabilitar_formulario();
            
               
            VerMoneda.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if(newValue == null) return;
                monedaSeleccionada = Moneda.findFirst("nombre = ?", newValue);
                cambiarMoneda();
            });
                    
        } catch (Exception e) {            
            infoController.show("No se pudo inicializar el menu de Ordenes de Compra: " + e.getMessage());
        }                                                   
    }   
    
    @Override
    public void nuevo(){
        crearNuevo = true;
        habilitar_formulario();
        VerVendedor.setText(usuarioActual.getString("usuario_cod"));
        vendedorSelecionado = usuarioActual;
        LabelPedido.setText("Nuevo Pedido");
    }
    
    @Override
    public void guardar(){
        if (crearNuevo){
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Pedidos, Accion.ACCION.CRE)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                crearNuevo = false;
                return;
            }
            crearPedido();
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.CRE, Menu.MENU.Pedidos ,this.usuarioActual);
        } else {
            if (pedidoSeleccionado == null){
                infoController.show("No ha seleccionado un cliente");
                return;
            }
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Pedidos, Accion.ACCION.MOD)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                return;
            }
            editarPedido(pedidoSeleccionado);
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.MOD, Menu.MENU.Pedidos ,this.usuarioActual);
        }
        crearNuevo = false;
        RefrescarTabla(OrdenCompra.findAll());
    }
    
    @Override
    public void cambiarEstado()
    {
        if(pedidoSeleccionado == null)
        {
            infoController.show("No ha seleccionado ninguna Pedido");
            return;
        }
        
        if(pedidoSeleccionado.getString("estado").equals(OrdenCompra.ESTADO.COMPLETA.name()))
        {
            infoController.show("La orden ya ha sido completada");
            return;
        }                
        
        try {
            Base.openTransaction();
            
            String estadoAnterior = pedidoSeleccionado.getString("estado");
            
            OrdenCompra.ESTADO siguienteEstado = OrdenCompra.ESTADO.valueOf(estadoAnterior).next();
            
            if(!confirmationController.show("Esta accion cambiara la Orden del estado " + estadoAnterior + " a " + siguienteEstado.name(), "¿Desea continuar?")) return;
            
            pedidoSeleccionado.set("estado",siguienteEstado.name());                       
            pedidoSeleccionado.saveIt();            
                                    
            
            if(estadoAnterior.equals(OrdenCompra.ESTADO.ENDESPACHO.name())) 
            {
                infoController.show("El Pedido fue actualizada correctamente. Todos productos han sido despachados");
                Base.commitTransaction();
            
                TablaPedido.getColumns().get(0).setVisible(false);
                TablaPedido.getColumns().get(0).setVisible(true);           
                            
                return;
            }  
            
             
            Boolean stockError = false;
            String messageStock = "El pedido contiene entradas que superan el stock disponible actual. "                    
                    + "Los productos con error son:\n\nCodigo           Faltante\n";
            for(OrdenCompraxProducto pedidoxproducto:productos)
            {                
                Stock productoStock = Stock.first("tipo_id = ?", pedidoxproducto.get("tipo_id"));            
                Integer cantidadLlevada = pedidoxproducto.getInteger("cantidad");
                Integer stockLogico = productoStock.getInteger("stock_logico");

                if(cantidadLlevada > stockLogico)
                {
                    String cod = pedidoxproducto.getString("tipo_cod");
                    messageStock += cod + ":            " + String.valueOf(cantidadLlevada-stockLogico) + "\n";
                    stockError = true;
                }
            }             

            if(stockError)
            {
                infoController.show(messageStock);
                return;
            }
                        
            for(OrdenCompraxProducto producto:productos)
            {                
                producto.set("reservado","S");                
                producto.saveIt();
            }                        
            Base.commitTransaction();            
            TablaPedido.getColumns().get(0).setVisible(false);
            TablaPedido.getColumns().get(0).setVisible(true);            
            infoController.show("El pedido fue actualizada correctamente. Los productos han sido reservados");
        } catch (Exception e) {
            Base.rollbackTransaction();
            infoController.show("No se ha podido modificar el estado del Pedido");
        }
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
        
        crearNuevo = false;
        habilitar_formulario();
        try {
            pedidoSeleccionado = TablaPedido.getSelectionModel().getSelectedItem();
            if (pedidoSeleccionado == null) 
            {
                infoController.show("No ha seleccionado ningun Pedido");
            }

          setPedidoVisible(pedidoSeleccionado);                            
        } catch (Exception e) {
            infoController.show("Error al mostrar el pedido: " + e.getMessage());
        }        

    }    
                    
    private void setPedidoVisible(OrdenCompra pedidoSeleccionado) throws Exception
    {
        Cliente cliente = Cliente.findById(pedidoSeleccionado.get("client_id"));
        Usuario vendedor = Usuario.findById(pedidoSeleccionado.get("usuario_id"));
        vendedorSelecionado = vendedor;
        clienteSeleccionado = cliente;
        
        setInformacionCliente(cliente,false);     
        VerCliente.setText(cliente.getString("nombre"));
        VerVendedor.setText(vendedor.getString("nombre"));
        
        String direccionFacturacion = pedidoSeleccionado.getString("direccion_facturacion");        
        String direccionDespacho = pedidoSeleccionado.getString("direccion_despacho");
        
        VerDireccionDespacho.setText(direccionDespacho);
        VerDireccionFacturacion.setText(direccionFacturacion);
        
        LocalDate date = pedidoSeleccionado.getDate("fecha_emision").toLocalDate();
        VerFecha.setValue(date);
        
        VerMoneda.getSelectionModel().select(Moneda.findById(pedidoSeleccionado.get("moneda_id")).getString("nombre"));       
        setValorTotal(pedidoSeleccionado.getDouble("total"));
        
        List<OrdenCompraxProducto> pedidoxproductos = OrdenCompraxProducto.where("orden_compra_id = ?", pedidoSeleccionado.getId());        
        productos.clear();
        productos.addAll(pedidoxproductos);                   
    }
                 
    private void setValorTotal(Double valor)
    {        
        subTotal.setText(String.valueOf(valor));
        Double valorIgv = IGV*valor;            
        igvPedido.setText(String.valueOf(valorIgv));
        totalPedido.setText(String.valueOf(valor+valorIgv));                
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
      
    private void habilitar_formulario() {
        pedidoForm.setDisable(false);
        pedidoTable.setDisable(false);
    }
    
    private void editarPedido(OrdenCompra pedido)
    {
        try{            
        if(clienteSeleccionado == null)
        {
            infoController.show("No hay un cliente seleccionado");
            return;
        }
        
        if(vendedorSelecionado == null)
        {
            infoController.show("No hay un vendedor seleccionado");
            return;            
        }
        
        if(totalPedido.getText().isEmpty())
        {
            infoController.show("Debe agregar algun producto a la Orden");
            return;
        }
        
        Base.openTransaction();
        LocalDate fechaLocal = VerFecha.getValue();
        java.sql.Date fecha = java.sql.Date.valueOf(fechaLocal);                    
        
        Double igvValue = Double.valueOf(igvPedido.getText());
        Double totalValue = Double.valueOf(subTotal.getText());
        if(igvValue == null) igvValue = 0.0;
        if(totalValue == null) totalValue = 0.0;
        String monedaNombre = VerMoneda.getSelectionModel().getSelectedItem();
        Moneda moneda = Moneda.findFirst("nombre = ?", monedaNombre);
        String direccionFacturacion = VerDireccionFacturacion.getText();
        String direccionDespacho = VerDireccionDespacho.getText();
        String departamento = clienteSeleccionado.getString("departamento");
              
        Integer cotizacion_id = null;
        if(cotizacionAnexada != null)
        {
            cotizacion_id = cotizacionAnexada.getInteger("cotizacion_id");
        }
        asignar_data(pedido,usuarioActual.getString("usuario_cod"),clienteSeleccionado.getInteger("client_id"), fecha, igvValue,totalValue,
                OrdenCompra.ESTADO.PENDIENTE.name(),vendedorSelecionado,moneda.getInteger("moneda_id"),cotizacion_id,direccionDespacho,direccionFacturacion,departamento);          
        String cod = pedido.getString("orden_compra_cod");        
        pedido.saveIt();
      
        setProductos(pedido);
        Base.commitTransaction();       
        infoController.show("Se ha editado la Orden de Compra: " + String.valueOf(cod));        
        }
        catch(Exception e){
           infoController.show("No se pudo editar la Proforma: " + e.getMessage());
           Base.rollbackTransaction();
        }                    
    }

    private void crearPedido() {
        //extraemos la informacion del formulario:
    try{
        
        if(clienteSeleccionado == null)
        {
            infoController.show("No hay un cliente seleccionado");
            return;
        }
        
        if(vendedorSelecionado == null)
        {
            infoController.show("No hay un vendedor seleccionado");
            return;            
        }
        
        if(totalPedido.getText().isEmpty())
        {
            infoController.show("Debe agregar algun producto a la Orden");
            return;
        }
        
        Base.openTransaction();
        LocalDate fechaLocal = VerFecha.getValue();
        java.sql.Date fecha = java.sql.Date.valueOf(fechaLocal);                    
        
        Double igvValue = Double.valueOf(igvPedido.getText());
        Double totalValue = Double.valueOf(subTotal.getText());
        if(igvValue == null) igvValue = 0.0;
        if(totalValue == null) totalValue = 0.0;
        String monedaNombre = VerMoneda.getSelectionModel().getSelectedItem();
        Moneda moneda = Moneda.findFirst("nombre = ?", monedaNombre);
        String direccionFacturacion = VerDireccionFacturacion.getText();
        String direccionDespacho = VerDireccionDespacho.getText();
        String departamento = clienteSeleccionado.getString("departamento");
        
        OrdenCompra pedido = new OrdenCompra();
        Integer cotizacion_id = null;
        if(cotizacionAnexada != null)
        {
            cotizacion_id = cotizacionAnexada.getInteger("cotizacion_id");
        }
        asignar_data(pedido,usuarioActual.getString("usuario_cod"),clienteSeleccionado.getInteger("client_id"), fecha, igvValue,totalValue,
                OrdenCompra.ESTADO.PENDIENTE.name(),vendedorSelecionado,moneda.getInteger("moneda_id"),cotizacion_id,direccionDespacho,direccionFacturacion,departamento);  
        
        String cod = "PED" + String.valueOf(Integer.valueOf(String.valueOf((Base.firstCell("select last_value from ordenescompra_orden_compra_id_seq")))) + 1);
        pedido.set("orden_compra_cod",cod);        
        pedido.saveIt();
      
        setProductos(pedido);
        Base.commitTransaction();       
        infoController.show("Se ha creado la Orden de Compra: " + String.valueOf(cod));        
        }
        catch(Exception e){
           infoController.show("No se pudo crear la Proforma: " + e.getMessage());
           Base.rollbackTransaction();
        }    
    }
    
     private void asignar_data(OrdenCompra pedido,String usuarioAccion, Integer clienteId, Date fechaEmision, Double igvVale, Double total, 
             String estado,Usuario vendedor, Integer monedaId, Integer cotizacion_id,String direccionDespacho,String direccionFacturacion,String departamento) throws Exception{
                       
        pedido.set("client_id",clienteId);
        pedido.setDate("fecha_emision", fechaEmision);
        pedido.set("total", total);
        pedido.set("igv", total);
        pedido.set("estado", estado);
        pedido.set("last_user_change",usuarioAccion);        
        pedido.set("moneda_id", monedaId);        
        pedido.set("cotizacion_id", cotizacion_id);
        pedido.set("usuario_cod",vendedor.get("usuario_cod"));
        pedido.set("usuario_id",vendedor.getId());                                                        
        pedido.set("direccion_despacho",direccionDespacho);
        pedido.set("direccion_facturacion",direccionFacturacion);                
        pedido.set("departamento",departamento);                
    } 
     
    private void setProductos(OrdenCompra pedido) throws Exception
    {        
        List<OrdenCompraxProducto> pedidosGuardados = OrdenCompraxProducto.where("orden_compra_id = ?", pedido.getId());
        
        Boolean stockError = false;
        String messageStock = "El pedido contiene entradas que superan el stock disponible actual. "
                + "La orden se guardará pero no podrá ser completado por el momento.\n"
                + "Los productos con error son:\n\nCodigo           Faltante\n";
        for(OrdenCompraxProducto pedidoxproducto:productos)
        {
            if(pedidoxproducto.isNew())
            {
                pedidoxproducto.set("orden_compra_id",pedido.getId());
                pedidoxproducto.set("client_id",pedido.get("client_id"));
                pedidoxproducto.set("orden_compra_cod",pedido.get("orden_compra_cod"));
                pedidoxproducto.saveIt();
            }
            Stock productoStock = Stock.first("tipo_id = ?", pedidoxproducto.get("tipo_id"));            
            Integer cantidadLlevada = pedidoxproducto.getInteger("cantidad");
            Integer stockLogico = productoStock.getInteger("stock_logico");
            
            if(cantidadLlevada > stockLogico)
            {
                String cod = pedidoxproducto.getString("tipo_cod");
                messageStock += cod + ":            " + String.valueOf(cantidadLlevada-stockLogico) + "\n";
                stockError = true;
            }
        }             
        
        if(stockError)
        {
            infoController.show(messageStock);
        }
        
        if(pedidosGuardados == null) return;
        List<OrdenCompraxProducto> pedidosProductosDelete = pedidosGuardados.stream().filter(x -> productos.stream().noneMatch(y -> !y.isNew() && 
                y.getInteger("orden_compra_id").equals(x.getInteger("orden_compra_id")) && 
                y.getInteger("tipo_id").equals(x.getInteger("tipo_id")))).collect(Collectors.toList());
        
        if(pedidosProductosDelete == null) return;
        
        for(OrdenCompraxProducto pedidoxProducto:pedidosProductosDelete)
        {
            OrdenCompraxProducto.delete("orden_compra_id = ? AND tipo_id = ?",pedidoxProducto.get("orden_compra_id"),pedidoxProducto.get("tipo_id"));
        }
    }
    
    @FXML
    private void buscarProducto(ActionEvent event) {
        modal_stage.showAndWait();
        if(productoDevuelto==null) return;        
        VerProducto.setText(productoDevuelto.getString("nombre"));
    }
    
    
    @FXML
    private void eliminarProducto(ActionEvent event) {
         try {            
            OrdenCompraxProducto pedidoxProducto = TablaProductos.getSelectionModel().getSelectedItem();
            if(pedidoxProducto==null)
            {
                infoController.show("No ha seleccionado ningun Pedido");
                return;
            }         

            if(!pedidoxProducto.isNew())
            {
                OrdenCompra pedido = OrdenCompra.findById(pedidoxProducto.get("orden_compra_id"));
                String estado = pedido.getString("estado");
                if(estado.equals(OrdenCompra.ESTADO.ENDESPACHO.name())||estado.equals(OrdenCompra.ESTADO.COMPLETA.name()))
                {
                    infoController.show("No puede eliminar producto ya que este ya se encuentra con Envio");
                    return;                
                }            
            }  
            
            productos.remove(pedidoxProducto);          
            for(OrdenCompraxProducto pedidoxproducto:productos)
            {
                Double descuento = calcularDescuento(pedidoxproducto);
                Double flete = calcularFlete(pedidoxproducto);
                
                pedidoxproducto.set("descuento",descuento);
                pedidoxproducto.set("flete",flete);
                pedidoxproducto.set("subtotal_final",pedidoxproducto.getDouble("subtotal_previo") + descuento - flete);
            }
            calcularFinal(); 
             
            TablaProductos.getColumns().get(0).setVisible(false);
            TablaProductos.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("Error al eliminar el producto");
        }        
    }
    
    @FXML
    private void agregaProducto(ActionEvent event) {
         if(productoDevuelto == null)
        {
            infoController.show("No ha seleccionado ningun producto"); 
           return;
        }
        
        if(clienteSeleccionado == null)
        {
            infoController.show("Debe seleccionar un cliente antes para el calculo del flete"); 
           return;            
        }
        
        if(monedaSeleccionada == null)
        {
            infoController.show("Debe seleccionar una moneda utilizada primero");
            return;
        }        
        Boolean isNew = false;             
        try {                        
                if(!productos.stream().anyMatch(x -> x.getInteger("tipo_id").equals(productoDevuelto.getInteger("tipo_id"))))
                {
                    OrdenCompraxProducto pedidoxproducto = new OrdenCompraxProducto();                                           
                    Integer cantidad = cantProd.getValue();
                    Double precio = productoDevuelto.getPrecioActual(monedaSeleccionada);
                    pedidoxproducto.set("tipo_id",productoDevuelto.getId());
                    pedidoxproducto.set("tipo_cod",productoDevuelto.get("tipo_cod"));                                
                    pedidoxproducto.set("cantidad",cantidad);                       
                    pedidoxproducto.set("cantidad_descuento_disponible",cantidad);                                   
                    pedidoxproducto.set("precio_unitario",precio);    
                    pedidoxproducto.set("subtotal_previo",cantidad*precio); 
                    pedidoxproducto.set("descuento",0);
                    pedidoxproducto.set("flete",0);                    
                    pedidoxproducto.set("reservado","N");
                    pedidoxproducto.set("subtotal_final",cantidad*precio);    
                    productos.add(pedidoxproducto);
                    isNew = true;                    
                }                          
               RecalcularTabla(isNew);
        } catch (Exception e) {
            infoController.show("No se ha podido agregar ese Producto: " + e.getMessage());
        }                
    }
    
    private void RecalcularTabla(Boolean isNew) throws Exception
    {
        for(OrdenCompraxProducto productoPed:productos)
        {
            Integer extraCant = 0;
            if(!isNew && productoPed.getInteger("tipo_id").equals(productoDevuelto.getInteger("tipo_id")))
            {
                extraCant += cantProd.getValue();
            }

            Double subtotal_anterior = productoPed.getDouble("subtotal_final");            
            Double precioUnitario = productoPed.getDouble("precio_unitario");  
            Integer cantidad = productoPed.getInteger("cantidad") + extraCant;
            productoPed.set("cantidad",cantidad);
            productoPed.set("cantidad_descuento_disponible",productoPed.getInteger("cantidad_descuento_disponible") + extraCant);     
            Double descuento = calcularDescuento(productoPed) + productoPed.getDouble("descuento");
            Double flete = calcularFlete(productoPed) + productoPed.getDouble("flete");
            Double subtotal = cantidad*precioUnitario - descuento + flete;

            productoPed.set("descuento",descuento);
            productoPed.set("flete",flete);

            productoPed.set("subtotal_previo", precioUnitario*cantidad);
            productoPed.set("subtotal_final",subtotal);                                                                                               
        }    
        
        TablaProductos.getColumns().get(0).setVisible(false);
        TablaProductos.getColumns().get(0).setVisible(true);    
        calcularFinal();        
    }
    
    private void cambiarMoneda()
    {
        if(monedaSeleccionada == null) return;
        try {
            for(OrdenCompraxProducto productoPed:productos)
            {                
                TipoProducto producto = TipoProducto.findById(productoPed.get("tipo_id"));               
                
                Double nuevoPrecio = producto.getPrecioActual(monedaSeleccionada);
                Double factor = nuevoPrecio/productoPed.getDouble("precio_unitario");
                Integer cantidad = productoPed.getInteger("cantidad");
                
                productoPed.set("precio_unitario",nuevoPrecio);    
                productoPed.set("subtotal_previo",cantidad*nuevoPrecio); 
                Double nuevoDescuento = productoPed.getDouble("descuento")*factor;
                productoPed.set("descuento",nuevoDescuento);
                Double nuevoFlete = calcularFlete(productoPed);
                productoPed.set("flete",productoPed.getDouble("flete")*factor);                    
                productoPed.set("subtotal_final",cantidad*nuevoPrecio - nuevoDescuento + nuevoFlete);                               
            }         
            
            TablaProductos.getColumns().get(0).setVisible(false);
            TablaProductos.getColumns().get(0).setVisible(true);    
            calcularFinal();        
            
        } catch (Exception e) {
            infoController.show("Error al cambiar de moneda: " + e.getMessage());
        }             
    }    
    
     private Double calcularDescuento(OrdenCompraxProducto producto) throws Exception
    {
        // Magic. Se que es el id 1,2,3. Puesto asi en la Bd
        Double prioridadBonficacion = Double.valueOf(ParametroSistema.findById(1).getInteger("valor"));
        Double prioridadCantidad = Double.valueOf(ParametroSistema.findById(2).getInteger("valor")) - 0.25;
        Double prioridadPorcentaje =  Double.valueOf(ParametroSistema.findById(3).getInteger("valor")) - 0.5;
        
        TipoProducto productoReferenciado = TipoProducto.findById(producto.get("tipo_id"));
        List<Promocion> promociones = Promocion.where("tipo_id = ?",producto.get("tipo_id"));        
        List<CategoriaProducto> categorias = productoReferenciado.getAll(CategoriaProducto.class);
        
        for(CategoriaProducto categoria:categorias)
        {
            promociones.addAll(Promocion.where("categoria_id = ?", categoria.get("categoria_id")));
        }                   
        
        java.sql.Date today = java.sql.Date.valueOf(LocalDate.now());        
        List<Promocion> promocionesActual = promociones.stream().filter(x -> today.after(x.getDate("fecha_inicio"))&& today.before(x.getDate("fecha_fin"))).collect(Collectors.toList());
        
        if(prioridadPorcentaje<prioridadCantidad&&prioridadPorcentaje<prioridadBonficacion)
        {
            return aplicarPromocionPorcentaje(promocionesActual, producto);
        }
        
        if(prioridadCantidad<prioridadPorcentaje&&prioridadCantidad<prioridadBonficacion)
        {
            return aplicarPromocionCantidad(promocionesActual, producto);            
        }
        
        if(prioridadBonficacion<prioridadCantidad&&prioridadBonficacion<prioridadPorcentaje)
        {
            return aplicarPromocionBonificacion(promocionesActual, producto);
        }        
        return 0.0;        
    }
     
     private Double aplicarPromocionBonificacion(List<Promocion> promociones,OrdenCompraxProducto producto) throws Exception
    {               
        List<Promocion> promocionesBonificacion = promociones.stream().filter(x -> x.getString("tipo").equals(Promocion.TIPO.BONIFICACIÓN.name())).collect(Collectors.toList());
        if(promocionesBonificacion.isEmpty()) return 0.0;                       
        promocionesBonificacion.sort((Promocion o1, Promocion o2) -> o1.getInteger("prioridad") - o2.getInteger("prioridad"));
        
        if(promocionesBonificacion.isEmpty()) return 0.0;
        Double valorPromocion = 0.0;
                
        Promocion promocionAplicada = promocionesBonificacion.stream().findFirst().get();        
        PromocionBonificacion promocionBonificacion = PromocionBonificacion.findFirst("promocion_id = ?", promocionAplicada.getId());              
        Boolean es_categoria = promocionBonificacion.getString("es_categoria_comprar").equals("S");
                 
        Double precioActual = producto.getDouble("precio_unitario");
        Double ganciaPorPromocion = (promocionBonificacion.getInteger("nr_obtener"))*precioActual;
        Integer cantidadComprando = 0;
        Integer cantidadObteniendo = 0;
        
        if(!es_categoria)
        {               
            List<OrdenCompraxProducto> pedidoxproductos = productos.stream().filter(x -> Objects.equals(x.getInteger("tipo_id"), promocionBonificacion.getInteger("tipo_id"))).collect(Collectors.toList());
            if(pedidoxproductos.isEmpty()) return 0.0;
            
            OrdenCompraxProducto pedidoxproducto = pedidoxproductos.stream().findFirst().get();
            cantidadComprando = pedidoxproducto.getInteger("cantidad_descuento_disponible");     
            cantidadObteniendo = producto.getInteger("cantidad_descuento_disponible"); 
            
            Integer nr_obtener =  promocionBonificacion.getInteger("nr_obtener");                      
            Integer nr_comprar = promocionBonificacion.getInteger("nr_comprar");                      
            
            Integer nrPromociones = Integer.min(cantidadComprando/nr_comprar, cantidadObteniendo/nr_obtener);
            if(nrPromociones <= 0) return 0.0;
            
            producto.set("cantidad_descuento_disponible",cantidadObteniendo - nr_obtener*nrPromociones);
            pedidoxproducto.set("cantidad_descuento_disponible",cantidadComprando - nr_comprar*nrPromociones);
            
            valorPromocion = ganciaPorPromocion*nrPromociones;   
        }else
        {
            List<OrdenCompraxProducto> pedidosxproductos = productos.stream().filter(x -> Objects.equals(x.getInteger("categoria_id"), promocionBonificacion.getInteger("categoria_id"))).collect(Collectors.toList());
            if(pedidosxproductos.isEmpty()) return 0.0;
            
            for(OrdenCompraxProducto peedidoxproducto:pedidosxproductos)
            {
                cantidadComprando += peedidoxproducto.getInteger("cantidad_descuento_disponible");                
            }
              
            Integer nrPromociones = 0;
            Integer nr_obtener = promocionBonificacion.getInteger("nr_obtener");
            
            nrPromociones = cantidadComprando / nr_obtener;
            Integer nrUtilizados = nrPromociones * nr_obtener;
            
            for(OrdenCompraxProducto peedidoxproducto:pedidosxproductos)
            {
                Integer cantidadDisponible = peedidoxproducto.getInteger("cantidad_descuento_disponible");                
                if(nrUtilizados<=0) break;
                if(nrUtilizados<=cantidadDisponible)
                {
                    peedidoxproducto.set("cantidad_descuento_disponible",cantidadDisponible - nrUtilizados);
                }else
                {
                    peedidoxproducto.set("cantidad_descuento_disponible",0);
                    nrUtilizados -= cantidadDisponible;
                }                
            }
            valorPromocion = ganciaPorPromocion*nrPromociones;  
        }                          
        return valorPromocion;
    }
    
    private Double aplicarPromocionCantidad(List<Promocion> promociones,OrdenCompraxProducto producto) throws Exception
    {
        List<Promocion> promocionesCantidad = promociones.stream().filter(x -> x.getString("tipo").equals(Promocion.TIPO.CANTIDAD.name())).collect(Collectors.toList());
        if(promocionesCantidad.isEmpty()) return 0.0;                       
        promocionesCantidad.sort((Promocion o1, Promocion o2) -> o1.getInteger("prioridad") - o2.getInteger("prioridad"));
        
        if(promocionesCantidad.isEmpty()) return 0.0;
    
        Promocion promocionAplicada = promocionesCantidad.stream().findFirst().get();    
        Boolean es_categoria = promocionAplicada.getString("es_categoria").equals("S");
        Integer id = (es_categoria) ?  promocionAplicada.getInteger("categoria_id") : promocionAplicada.getInteger("tipo_id");
        
        PromocionCantidad promocionCantidad = PromocionCantidad.findFirst("promocion_id = ?", promocionAplicada.getId());        
        Integer cantidadComprando = 0;
        
        Double valorDescuento = 0.0;
        Integer cantidad = producto.getInteger("cantidad_descuento_disponible");
        Double precioActual = producto.getDouble("precio_unitario");
        Double ganciaPorPromocion = (promocionCantidad.getInteger("nr_obtener")  - promocionCantidad.getInteger("nr_comprar"))*precioActual;
        
        if(!es_categoria)
        {            
            cantidadComprando = cantidad;                               
            Integer nrPromociones = cantidadComprando / promocionCantidad.getInteger("nr_obtener");        
            if(nrPromociones <= 0) return 0.0;
            
            producto.set("cantidad_descuento_disponible",cantidad - nrPromociones*promocionCantidad.getInteger("nr_obtener"));
            valorDescuento = ganciaPorPromocion*nrPromociones;            
        }else
        {            
            List<OrdenCompraxProducto> pedidosxproductos = productos.stream().filter(x -> 
                    TipoProducto.findById(x.get("tipo_id")).getAll(CategoriaProducto.class).stream().anyMatch(y -> 
                            Objects.equals(y.getInteger("categoria_id"), id))).collect(Collectors.toList());
            Integer nrPromociones = 0;
            Integer nr_obtener = promocionCantidad.getInteger("nr_obtener");
            
            for(OrdenCompraxProducto pedidoxproducto:pedidosxproductos)
            {
                cantidadComprando += pedidoxproducto.getInteger("cantidad_descuento_disponible");                
            }
            
            nrPromociones = cantidadComprando / nr_obtener;
            Integer nrUtilizados = nrPromociones * nr_obtener;
            
            for(OrdenCompraxProducto pedidoxproducto:pedidosxproductos)
            {
                Integer cantidadDisponible = pedidoxproducto.getInteger("cantidad_descuento_disponible");                
                if(nrUtilizados<=0) break;
                if(nrUtilizados<=cantidadDisponible)
                {
                    pedidoxproducto.set("cantidad_descuento_disponible",cantidadDisponible - nrUtilizados);
                }else
                {
                    pedidoxproducto.set("cantidad_descuento_disponible",0);
                    nrUtilizados -= cantidadDisponible;
                }                
            }
            valorDescuento = ganciaPorPromocion*nrPromociones;                            
        }                              
        return  valorDescuento;                
    }
    
    public Double aplicarPromocionPorcentaje(List<Promocion> promociones,OrdenCompraxProducto producto)
    {
        List<Promocion> promocionesProcentaje = promociones.stream().filter(x -> x.getString("tipo").equals(Promocion.TIPO.PORCENTAJE.name())).collect(Collectors.toList());
        if(promocionesProcentaje.isEmpty()) return 0.0;                       
        
        Double valorPromocion = 0.0;
        Integer cantidad = producto.getInteger("cantidad_descuento_disponible");
        Double precioActual = producto.getDouble("precio_unitario");
        
                
        Double cantidadPorcentaje = 0.0;
        for(Promocion promocion:promocionesProcentaje)
        {            
            PromocionPorcentaje promocionPorcentaje = PromocionPorcentaje.findFirst("promocion_id = ?", promocion.getId());
            cantidadPorcentaje += promocionPorcentaje.getDouble("valor_desc");
        }
        if(cantidadPorcentaje == 0) return 0.0;
        return  cantidad*precioActual*(cantidadPorcentaje/100);         
    }     
    
    private Double calcularFlete(OrdenCompraxProducto producto) throws Exception
    {         
        TipoProducto productoReferenciado = TipoProducto.findById(producto.get("tipo_id"));
        List<Flete> fletes = Flete.where("tipo_id = ?",producto.get("tipo_id"));        
        List<CategoriaProducto> categorias = productoReferenciado.getAll(CategoriaProducto.class);
        
        for(CategoriaProducto categoria:categorias)
        {
            fletes.addAll(Flete.where("categoria_id = ?", categoria.get("categoria_id")));
        }                   
        
        java.sql.Date today = java.sql.Date.valueOf(LocalDate.now());        
        List<Flete> fletesActual = fletes.stream().filter(x -> today.after(x.getDate("fecha_inicio"))&& today.before(x.getDate("fecha_fin"))).collect(Collectors.toList());
        Double distancia = ParametroSistema.findFirst("nombre = ?",clienteSeleccionado.getString("departamento")).getDouble("valor");
        Integer cantidad = producto.getInteger("cantidad");
        Double precio_unitario = producto.getDouble("precio_unitario");
     
        Double fleteVolumen = aplicarFleteVolumen(fletesActual, productoReferenciado, distancia, cantidad);
        Double fletePeso = aplicarFletePeso(fletesActual, productoReferenciado, distancia, cantidad);
        Double fletePorcentaje = aplicarFletePorcentaje(fletesActual, precio_unitario, cantidad);                                
        
        return fleteVolumen + fletePeso + fletePorcentaje;
    }
    
    private Double aplicarFleteVolumen(List<Flete> fletes,TipoProducto productoReferenciado, Double distancia,Integer cantidad) throws Exception
    {
        List<Flete> fletesVolumen = fletes.stream().filter(x -> x.getString("tipo").equals(Flete.TIPO.VOLUMEN.name())).collect(Collectors.toList());        
        Double fleteTotal = 0.0;              
        
        Double alto = productoReferenciado.getDouble("alto");
        Double ancho = productoReferenciado.getDouble("ancho");
        Double largo = productoReferenciado.getDouble("longitud");
        
        if(alto == null || ancho == null || largo == null) return 0.0;        
        Double volumen = alto*ancho*largo;
        
        for(Flete flete:fletesVolumen)
        {
            Double factor = CambioMoneda.findFirst("moneda1_id = ? and moneda2_id = ?", monedaSeleccionada.getInteger("moneda_id"),flete.getInteger("moneda_id")).getDouble("factor");
            fleteTotal += volumen*flete.getDouble("valor")*distancia*factor;                        
        }
        
        return fleteTotal*cantidad/1000;
    }
    
    private Double aplicarFletePeso(List<Flete> fletes,TipoProducto productoReferenciado, Double distancia,Integer cantidad) throws Exception
    {
        List<Flete> fletesVolumen = fletes.stream().filter(x -> x.getString("tipo").equals(Flete.TIPO.PESO.name())).collect(Collectors.toList());        
        Double fleteTotal = 0.0;              
        
        Double peso = productoReferenciado.getDouble("peso");
        
        for(Flete flete:fletesVolumen)
        {
            Double factor = CambioMoneda.findFirst("moneda1_id = ? and moneda2_id = ?", monedaSeleccionada.getInteger("moneda_id"),flete.getInteger("moneda_id")).getDouble("factor");
            fleteTotal += peso*flete.getDouble("valor")*distancia*factor;                        
        }
        return fleteTotal*cantidad/1000;   
    }
    
    private Double aplicarFletePorcentaje(List<Flete> fletes,Double precio,Integer cantidad) throws Exception
    {
        List<Flete> fletesVolumen = fletes.stream().filter(x -> x.getString("tipo").equals(Flete.TIPO.PORCENTAJE.name())).collect(Collectors.toList());        
        if(fletesVolumen.isEmpty()) return 0.0;
        Double fleteTotal = 0.0;              
        
        for(Flete flete:fletesVolumen)
        {
            fleteTotal += flete.getDouble("valor");
        }
        return precio*cantidad*(fleteTotal/100);
    }        
    
    private void calcularFinal()
    {
        Double total = 0.0;
        for(OrdenCompraxProducto pedidoxproducto:productos)
        {
            total += pedidoxproducto.getDouble("subtotal_final");
        }
        setValorTotal(total);
    }
    
                      
   private void RefrescarTabla(List<OrdenCompra> tempPedidos)
    {
        try {
            if(tempPedidos == null) return;
            pedidos.removeAll(pedidos);                        
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
        
        try {            
             for (Cliente cliente : autoCompletadoList){
                if (cliente.getString("nombre").equals(VerCliente.getText())){
                    clienteSeleccionado = cliente;                               
                    setInformacionCliente(cliente,true);
                    for(OrdenCompraxProducto pedidoxproducto:productos)
                    {
                        pedidoxproducto.set("flete",calcularFlete(pedidoxproducto));
                    }
                    TablaProductos.getColumns().get(0).setVisible(false);
                    TablaProductos.getColumns().get(0).setVisible(true);
                }
            }            
        } catch (Exception e) {
            infoController.show("No se ha podido cargar la informacion del cliente: " + e.getMessage());
        }
       
    }
    
    private void setInformacionCliente(Cliente cliente,Boolean cambiarDireccion)
    {         
     
        String tipo_cliente = cliente.getString("tipo_cliente");
        String dni = cliente.getString("dni");
        String ruc = cliente.getString("ruc");

        if(cambiarDireccion)
        {
            String direccionFacturacion = cliente.getString("direccion_facturacion");
            String direccionDespacho = cliente.getString("direccion_despacho");

            VerDireccionDespacho.setText(direccionDespacho);
            VerDireccionFacturacion.setText(direccionFacturacion);            
        }        
        if(tipo_cliente.equals(Cliente.TIPO.PersonaNatural.name()))
        {
            VerDocumentoLabel.setText("DNI:");
            VerDocumento.setText(dni);
            TipoDocBoleta.setSelected(true);
            TipoDocFactura.setSelected(false);
            TipoDocFactura.setDisable(false);  
        }else
        {
            VerDocumentoLabel.setText("RUC:");
            VerDocumento.setText(ruc);
            TipoDocFactura.setSelected(true);
            TipoDocBoleta.setDisable(true);                    
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
    
    @Override
    public void menuCall(cambiarMenuArgs args)
    {
        try {
            Integer cotizacion_id = args.related_id;
        
            Cotizacion cotizacion = Cotizacion.findById(cotizacion_id);

            Cliente cliente = Cliente.findById(cotizacion.get("client_id"));             
            clienteSeleccionado = cliente;

            setInformacionCliente(cliente,false);     
            VerCliente.setText(cliente.getString("nombre"));      

            String direccionFacturacion = cliente.getString("direccion_facturacion");        
            String direccionDespacho = cliente.getString("direccion_despacho");

            VerDireccionDespacho.setText(direccionDespacho);
            VerDireccionFacturacion.setText(direccionFacturacion);

            LocalDate date = cotizacion.getDate("fecha_emision").toLocalDate();
            VerFecha.setValue(date);

            VerMoneda.getSelectionModel().select(Moneda.findById(cotizacion.get("moneda_id")).getString("nombre"));       
            setValorTotal(cotizacion.getDouble("total"));
          
            productos.clear();
            productos.addAll(generarProductos(cotizacion));  

            crearNuevo = true;
            habilitar_formulario();
            
        } catch (Exception e) {
            infoController.show("No se ha podido crear el Pedido a partir de la Proforma: " + e.getMessage());
        }
        
    }
    
    private List<OrdenCompraxProducto> generarProductos(Cotizacion cotizacion)
    {
        List<CotizacionxProducto> cotizacionxProductos = CotizacionxProducto.where("cotizacion_id = ?",cotizacion.getId());
        List<OrdenCompraxProducto> ordenCompraxProductos = new ArrayList<>();
        for(CotizacionxProducto cotizacionxProducto:cotizacionxProductos)
        {
            OrdenCompraxProducto pedidoxproducto = new OrdenCompraxProducto();
                                    
            pedidoxproducto.set("tipo_id",cotizacionxProducto.get("tipo_id"));
            pedidoxproducto.set("tipo_cod",cotizacionxProducto.get("tipo_cod"));                                
            pedidoxproducto.set("cantidad",cotizacionxProducto.get("cantidad"));                       
            pedidoxproducto.set("cantidad_descuento_disponible",cotizacionxProducto.get("cantidad_descuento_disponible"));                                   
            pedidoxproducto.set("precio_unitario",cotizacionxProducto.get("precio_unitario"));    
            pedidoxproducto.set("subtotal_previo",cotizacionxProducto.get("subtotal_previo")); 
            pedidoxproducto.set("descuento",cotizacionxProducto.get("descuento"));
            pedidoxproducto.set("flete",cotizacionxProducto.get("flete"));                    
            pedidoxproducto.set("subtotal_final",cotizacionxProducto.get("subtotal_final"));    
            ordenCompraxProductos.add(pedidoxproducto);
        }
        return ordenCompraxProductos;
    }        
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Pedidos;
    }

}
