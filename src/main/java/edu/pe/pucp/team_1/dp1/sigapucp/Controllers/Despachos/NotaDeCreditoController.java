/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Despachos;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.AgregarClientesController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.NotaDeCredito;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntrada;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntradaxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Sistema.ParametroSistema;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarClienteArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenEntradaProductoArgs;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Joel
 */
public class NotaDeCreditoController extends Controller {

    @FXML
    private TextField cod_nota_buscar;

    @FXML
    private TableView<NotaDeCredito> tabla_notas_cred;

    @FXML
    private TableColumn<NotaDeCredito, String> columna_cliente;

    @FXML
    private TableColumn<NotaDeCredito, String> columna_envio;

    @FXML
    private TableColumn<NotaDeCredito, String> columna_pedido;

    @FXML
    private TextField cliente_buscar;

    @FXML
    private TextField orden_entrada_buscar;

    @FXML
    private AnchorPane envio_formulario;

    @FXML
    private Label LabelPedido;

    @FXML
    private AnchorPane pedidoTable;

    @FXML
    private Spinner<Integer> cantidad_producto;

    @FXML
    private TextField VerProducto;

    @FXML
    private Button buscarProducto;

    @FXML
    private TextField igvNC;

    @FXML
    private TextField subtotalNC;

    @FXML
    private TextField totalNC;

    @FXML
    private TableView<OrdenEntradaxProducto> TablaProductos;

    @FXML
    private TableColumn<OrdenEntradaxProducto, String> codProdColumn;

    @FXML
    private TableColumn<OrdenEntradaxProducto, String> nombreProdColumn;

    @FXML
    private TableColumn<OrdenEntradaxProducto, String> cantProdColumn;

    @FXML
    private TableColumn<OrdenEntradaxProducto, String> precioUnitarioColumn;

    @FXML
    private TableColumn<OrdenEntradaxProducto, String> subTotalProdColumna;

    @FXML
    private AnchorPane pedidoForm;

    @FXML
    private TextField nombre_cliente;

    @FXML
    private Label VerDocumentoLabel;

    @FXML
    private TextField dni_cliente;

    @FXML
    private Label VerDocumentoLabel1;

    @FXML
    private TextField ruc_cliente;

    @FXML
    private Label VerDocumentoLabel2;

    @FXML
    private ComboBox<String> ordenes_entrada_combobox;
    
        
    
    //LOGICA
        //--------------------------------------------------//
    
    private InformationAlertController infoController;  
    private ConfirmationAlertController confirmatonController;
    private final ObservableList<NotaDeCredito> masterData = FXCollections.observableArrayList();
    private List<NotaDeCredito> notas_credito;
    private Boolean crearNuevo = false;    
    private Cliente cliente_seleccionado = null;

    //AUTOCOMPLETADO
        //--------------------------------------------------//

    private List<Cliente> auto_completado_list_cliente;
    ArrayList<String> posibles_clientes = new ArrayList<>();
    AutoCompletionBinding<String> autoCompletionBinding;   
    
    //MODALES FLUJO
        //--------------------------------------------------//
    Stage modal_stage = new Stage();
    private List<OrdenEntradaxProducto> productos_a_agregar;
    private List<OrdenEntradaxProducto> productos_disponibles;
    private OrdenEntradaxProducto producto_devuelto;
    private OrdenEntrada orden_entrada_seleccionada;
    private Boolean orden_entrada_nueva;
    private NotaDeCredito nota_seleccionada;
    private Double IGV;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            notas_credito = NotaDeCredito.findAll();
            llenar_tabla_nota_cred();
            llenar_autocompletado();
            inhabilitar_formulario();            
            setAgregarClientes();
        } catch (Exception ex){
            infoController.show("No se pudo cargar la ventana envios : " + ex.getMessage());
        }
    }    
    
    @Override
    public void nuevo(){
       crearNuevo = true;
       habilitar_formulario();
       limpiar_formulario();
       orden_entrada_nueva = true;
    }
    
    public NotaDeCreditoController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
        crearNuevo = false;
        cliente_seleccionado = null;
        ordenes_entrada_combobox = new ComboBox();
        productos_a_agregar = new ArrayList<OrdenEntradaxProducto>();
        IGV = ParametroSistema.findFirst("nombre = ?", "IGV").getDouble("valor");
    }    
    
    public void llenar_tabla_nota_cred(){
        masterData.clear();
        for( NotaDeCredito nota : notas_credito){
            masterData.add(nota);
        }
        columna_cliente.setCellValueFactory((TableColumn.CellDataFeatures<NotaDeCredito, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("client_id")));
        columna_envio.setCellValueFactory((TableColumn.CellDataFeatures<NotaDeCredito, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        columna_pedido.setCellValueFactory((TableColumn.CellDataFeatures<NotaDeCredito, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("orden_compra_cod")));
        tabla_notas_cred.setItems(masterData);   
    }

    public void cliente_to_string() throws Exception{
        ArrayList<String> words = new ArrayList<>();
        for (Cliente cliente : auto_completado_list_cliente){
            words.add(cliente.getString("nombre"));
        }
        posibles_clientes = words;        
    }

    private void handleAutoCompletar() {
        int i = 0;
        for (Cliente cliente : auto_completado_list_cliente){
            if (cliente.getString("nombre").equals(nombre_cliente.getText())){
                cliente_seleccionado = cliente;                               
                mostrar_informacion_cliente(cliente);
                llenar_ordenes_entrada_cliente();
            }
        }
    }
    
    public void inhabilitar_formulario(){
        envio_formulario.setDisable(true);
    }
    
    public void habilitar_formulario(){
        envio_formulario.setDisable(false);
    }  
    
    private void mostrar_informacion_cliente(Cliente cliente){
        String tipo_cliente = cliente.getString("tipo_cliente");
        String dni = cliente.getString("dni");
        String ruc = cliente.getString("ruc");
   
        if(tipo_cliente.equals(Cliente.TIPO.PersonaNatural.name()))
        {
            dni_cliente.setText(dni);
            ruc_cliente.setDisable(true);
        }else
        {
            ruc_cliente.setText(ruc);
            dni_cliente.setDisable(true);
        }          
    }
   
    
        public void llenar_ordenes_entrada_cliente(){        
        try{
            ObservableList<String> ordenes_entrada = FXCollections.observableArrayList();
            ordenes_entrada.clear();
            ordenes_entrada.addAll(OrdenEntrada.where("client_id = ? and tipo = ?", cliente_seleccionado.getId(), OrdenEntrada.TIPO.Devolucion.name()).stream().map(x -> x.getString("orden_entrada_cod")).collect(Collectors.toList()));
            if (ordenes_entrada.isEmpty()){
                infoController.show("El cliente no cuenta con pedidos devueltos :\n Asegurese de que se haya creado una Orden de entrada para los articulos devueltos. ");
                limpiar_formulario();
            }
            else{
                ordenes_entrada_combobox.setItems(ordenes_entrada);            
            }
        }catch(Exception e){
            infoController.show("Ha ocurrido un problema durante la seleccion de orden de compra : " + e.getMessage());
        }
    }
        
    public void limpiar_formulario(){
        nombre_cliente.clear();
        dni_cliente.clear();
        ruc_cliente.clear();
        tabla_notas_cred.getItems().clear();
        ordenes_entrada_combobox.getSelectionModel().clearSelection();
    }        
     
    
    public void llenar_autocompletado() throws Exception{
        auto_completado_list_cliente = Cliente.findAll();
        cliente_to_string();
        autoCompletionBinding = TextFields.bindAutoCompletion(nombre_cliente, posibles_clientes);
        autoCompletionBinding.addEventHandler(EventType.ROOT, (event) -> {
            handleAutoCompletar();
        }); 
    }    
    
      public void setAgregarClientes() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarClientes.fxml"));
        AgregarClientesController controller = new AgregarClientesController();
        loader.setController(controller);                      
        Scene modal_content_scene = new Scene((Parent)loader.load());
        modal_stage.setScene(modal_content_scene);
        if(modal_stage.getModality() != Modality.APPLICATION_MODAL) modal_stage.initModality(Modality.APPLICATION_MODAL);    

        controller.devolverClienteEvent.addHandler((Object sender, agregarClienteArgs args) -> {
            cliente_seleccionado = args.cliente;
        });
    }
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.NotasdeCredito;
    }
    
    @FXML
    private void buscar_nota_credito(ActionEvent event) {
    }

    @FXML
    private void visualizar_nota_credito(ActionEvent event) {
    }

    @FXML
    private void handleModalProducto(ActionEvent event) throws IOException{
        try{
            String temp_orden_entrada = ordenes_entrada_combobox.getSelectionModel().getSelectedItem();
            if ( orden_entrada_seleccionada != null){
                String orden_entrada_actual = orden_entrada_seleccionada.getString("orden_compra_cod");
                orden_entrada_nueva = !temp_orden_entrada.equals(orden_entrada_actual);
            }
            if (orden_entrada_nueva){
                orden_entrada_seleccionada = OrdenEntrada.findFirst("orden_entrada_cod = ?", temp_orden_entrada);
                obtener_productos_disponibles_orden_entrada();
                limpiar_tabla_productos();
                orden_entrada_nueva = false;
            }
            abrirModalProductos();
            if(producto_devuelto==null) return; 
            VerProducto.setText(TipoProducto.findFirst("tipo_cod = ? AND tipo_id = ?", producto_devuelto.get("tipo_cod"), producto_devuelto.get("tipo_id")).getString("nombre"));
        }catch(Exception e){
            infoController.show("Necesita seleccionar una orden de compra");
        }

    }
    
    public void limpiar_tabla_productos(){
        TablaProductos.getItems().clear();
    }
    
    private void abrirModalProductos() throws Exception
    {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarProductosEnvios.fxml"));
            AgregarProductosEntradaController controller = new AgregarProductosEntradaController(productos_disponibles);
            loader.setController(controller);
            Scene modal_content_scene = new Scene((Parent)loader.load());
            modal_stage.setScene(modal_content_scene);       
            if(modal_stage.getModality() != Modality.APPLICATION_MODAL) modal_stage.initModality(Modality.APPLICATION_MODAL); 
            controller.devolverProductoEvent.addHandler((Object sender, agregarOrdenEntradaProductoArgs args) -> {
                producto_devuelto = args.orden_entrada_producto;
            });                 
            modal_stage.showAndWait();
            SpinnerValueFactory cantidad_productoValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, producto_devuelto.getInteger("cantidad_descuento_disponible"), 0);
            cantidad_producto.setValueFactory(cantidad_productoValues);
        }catch(Exception e){
            infoController.show("No se pudo agregar los productos : " + e.getMessage());
        }
    }
    
    private void obtener_productos_disponibles_orden_entrada(){
        productos_disponibles = OrdenEntradaxProducto.where("orden_entrada_id = ?", orden_entrada_seleccionada.getId());
    }

    @FXML
    private void agregar_producto(ActionEvent event){
        if(producto_devuelto == null)
        {
            infoController.show("No ha seleccionado ningun producto"); 
           return;
        }        
        try{
            for(OrdenEntradaxProducto producto_disponible : productos_disponibles){
                if (producto_disponible.getInteger("tipo_id") == producto_devuelto.getInteger("tipo_id")){
                    Integer cantidad = producto_disponible.getInteger("cantidad_descuento_disponible") - (Integer)cantidad_producto.getValue();
                    if (cantidad >= 0){
                        if (cantidad_producto.getValue() != 0){
                            producto_disponible.setInteger("cantidad_descuento_disponible", cantidad);
                            actualizar_lista_producto_a_agregar(producto_disponible);
                            llenar_tabla_productos_a_enviar();
                        }else{
                            infoController.show("Error: Debe seleccionar una cantidad mayor a 0 ");
                        }
                    }
                    else{
                        infoController.show("Error: no es posible seleccionar esa cantidad, no existen existencias suficientes ");
                    }
                    break;
                }
            }   
        }catch(Exception e){
            infoController.show("Error " + e.getMessage());
        }
    }
    
        public void llenar_tabla_productos_a_enviar(){
        try{
            ObservableList<OrdenEntradaxProducto> productos = FXCollections.observableArrayList(); 
            productos.clear();
            productos.addAll(productos_a_agregar);
            limpiar_tabla_productos();
            codProdColumn.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntradaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
            nombreProdColumn.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntradaxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
            cantProdColumn.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntradaxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("cantidad")));
            precioUnitarioColumn.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntradaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("precio_unitario")));
            TablaProductos.setItems(productos);
        }catch(Exception e){
            System.out.println(e);
        }

    }
    
    private void RecalcularTabla(Boolean isNew) throws Exception
    {
        for(OrdenEntradaxProducto productoxdevolucion : productos_a_agregar)
        {
            Integer extraCant = 0;
            if(!isNew && productoxdevolucion.getInteger("tipo_id").equals(producto_devuelto.getInteger("tipo_id")))
            {
                extraCant += cantidad_producto.getValue();
            }
            productoxdevolucion.set("cantidad", productoxdevolucion.getInteger("cantidad") + extraCant);                                                                                                     
            break;
        }
        calcularFinal();
        
    }
    
    private void calcularFinal()
    {
        Double total = 0.0;
        for(OrdenEntradaxProducto entradaxproducto:productos_a_agregar)
        {
            total += entradaxproducto.getDouble("subtotal_final");
        }
        setValorTotal(total);
    }    
    
    private void setValorTotal(Double valor)
    {        
        subtotalNC.setText(String.valueOf(valor));
        Double valorIgv = IGV*valor;            
        igvNC.setText(String.valueOf(valorIgv));
        totalNC.setText(String.valueOf(valor+valorIgv));                
    }
    
    
    private void actualizar_lista_producto_a_agregar(OrdenEntradaxProducto producto_disponible){
        Boolean isNew = false; 
        try{
            if(!productos_a_agregar.stream().anyMatch(x -> x.getInteger("tipo_id").equals(producto_disponible.getInteger("tipo_id")))){
                OrdenEntradaxProducto productoxentrada = new OrdenEntradaxProducto();
                productoxentrada.set("tipo_id",producto_disponible.get("tipo_id"));
                productoxentrada.set("tipo_cod",producto_disponible.get("tipo_cod"));  
                productoxentrada.set("cantidad", cantidad_producto.getValue());       
                productoxentrada.set("precio_unitario",producto_disponible.get("precio_unitario"));                  
                productoxentrada.set("subtotal_final",producto_disponible.get("subtotal_final"));             
                productos_a_agregar.add(productoxentrada);
                isNew = true;
            }else{
                RecalcularTabla(isNew);
            }
        } catch (Exception e) {
            infoController.show("No se ha podido agregar ese Producto: " + e.getMessage());
        } 
    }

    @FXML
    private void eliminar_producto(ActionEvent event) throws IOException{

    }

    @FXML
    private void agregarCliente(ActionEvent event) {
        modal_stage.showAndWait();
        if(cliente_seleccionado==null) return;        
        mostrar_informacion_cliente(cliente_seleccionado);
        nombre_cliente.setText(cliente_seleccionado.getString("nombre"));
        llenar_ordenes_entrada_cliente();        
    }
    
}
