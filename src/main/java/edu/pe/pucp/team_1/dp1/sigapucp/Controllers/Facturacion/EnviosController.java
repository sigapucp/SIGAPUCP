/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.AgregarProductosController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.Envio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenesCompraxProductosxenvio;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenCompraProductoArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoArgs;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.print.DocFlavor;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Gustavo
 */
public class EnviosController extends Controller{
    //FORMULARIO
        //PEDIDOS
    @FXML
    private AnchorPane envio_formulario;
    @FXML
    private TextField nombre_cliente;
    @FXML
    private TextField dni_cliente;
    @FXML
    private TextField ruc_cliente;
    @FXML
    private ComboBox ordenes_compra_combobox;
    @FXML
    private Spinner<?> cantidad_producto;
    @FXML
    private TextField VerProducto;
    
    //BUSQUEDA
    //-----------------------------------------------------//
        //ENVIOS
    @FXML
    private TextField envio_buscar;
    @FXML
    private TextField cliente_buscar;
    @FXML
    private TextField pedido_buscar;
    @FXML
    private TableView<Envio> tabla_envios;
    @FXML
    private TableColumn<Envio, String> columna_cliente;
    @FXML
    private TableColumn<Envio, String> columna_envio;
    @FXML
    private TableColumn<Envio, String> columna_pedido;
        //PRODUCTOS ENVIAR
        //--------------------------------------------------//
    @FXML
    private TableView<OrdenCompraxProducto> tabla_productos;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_prod_cod;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_prod_nombre;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_prod_desc;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_prod_cant;
        
    
    //LOGICA
        //--------------------------------------------------//
    
    private InformationAlertController infoController;  
    private ConfirmationAlertController confirmatonController;
    private final ObservableList<Envio> masterData = FXCollections.observableArrayList();
    private List<Envio> envios;
    private Boolean crearNuevo = false;    
    private Cliente cliente_seleccionado = null;

    //AUTOCOMPLETAD
        //--------------------------------------------------//

    private List<Cliente> auto_completado_list_cliente;
    ArrayList<String> posibles_clientes = new ArrayList<>();
    AutoCompletionBinding<String> autoCompletionBinding;   
    
    //MODALES FLUJO
        //--------------------------------------------------//
    Stage modal_stage = new Stage();
    private List<OrdenCompraxProducto> productos_a_agregar;
    private List<OrdenCompraxProducto> productos_disponibles;
    private OrdenCompraxProducto producto_devuelto;
    private OrdenCompra orden_compra_seleccionada;
 
    //----------------------------------------------------------------------------//

    private void insertar_ordencompraxproductoxenvios(Envio envio_padre){
        for(OrdenCompraxProducto producto : productos_a_agregar){
            OrdenesCompraxProductosxenvio envio = new OrdenesCompraxProductosxenvio();
            envio.set("orden_compra_cod", orden_compra_seleccionada.getString("orden_compra_cod"));
            envio.set("orden_compra_id", orden_compra_seleccionada.getInteger("orden_compra_id"));
            envio.set("client_id", cliente_seleccionado.getInteger("client_id"));
            envio.set("tipo_cod", producto.getString("tipo_cod"));
            envio.set("tipo_id", producto.getInteger("tipo_id"));            
            envio.set("envio_cod", envio_padre.getString("envio_cod"));
            envio.set("envio_id", envio_padre.getInteger("envio_id"));
            envio.saveIt();
        }
    }
    
    public void actualizar_ordencompraxproductos(){
        for(OrdenCompraxProducto producto : productos_disponibles){
            producto.saveIt();
        }
    }
    private void crear_envio(){
        try{
            Base.openTransaction();  
            Envio envio = new Envio();
            String envio_cod = "ENV" + orden_compra_seleccionada.getString("orden_compra_cod");
            if(!confirmatonController.show("Se creará el envio con código: " + envio_cod, "¿Desea continuar?")) return;
            
            envio.set("envio_cod", envio_cod);
            envio.set("orden_compra_cod", orden_compra_seleccionada.get("orden_compra_cod"));
            envio.set("orden_compra_id", orden_compra_seleccionada.getInteger("orden_compra_id"));
            envio.set("client_id", cliente_seleccionado.getInteger("client_id"));
            envio.set("last_user_change", usuarioActual.getString("usuario_cod"));
            envio.set("estado", Envio.ESTADO.PENDIENTE.name());
            envio.saveIt();
            actualizar_ordencompraxproductos();
            insertar_ordencompraxproductoxenvios(envio);
            Base.commitTransaction();
        } catch (Exception e){
            infoController.show("No se pudo crear el envio : " + e.getMessage()); 
        }
    }
    
    @Override
    public void guardar(){
        if (crearNuevo){
            crear_envio();
        } else {
            /*
            if ( == null){ 
                infoController.show("No ha seleccionado ninguna Orden de Compra");            
                return;
            }
            editarPedido(pedidoSeleccionado);
            */
        }
        crearNuevo = false;
        envios = Envio.findAll();
        llenar_tabla_envios();
    }
    
    public void llenar_tabla_productos_a_enviar(){
        try{
            ObservableList<OrdenCompraxProducto> productos = FXCollections.observableArrayList(); 
            productos.clear();
            productos.addAll(productos_a_agregar);
            columna_prod_cod.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
            columna_prod_nombre.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
            columna_prod_desc.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("descripcion")));
            columna_prod_cant.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cantidad")));
            tabla_productos.setItems(productos);
        }catch(Exception e){
            System.out.println("---------------------------");
            System.out.println(e);
        }

    }
    private void actualizar_lista_producto_a_agregar(OrdenCompraxProducto producto_disponible){
        if(!productos_a_agregar.stream().anyMatch(x -> x.getInteger("tipo_id").equals(producto_disponible.getInteger("tipo_id")))){
            OrdenCompraxProducto productoxenvio = new OrdenCompraxProducto();
            productoxenvio.set("tipo_id",producto_disponible.get("tipo_id"));
            productoxenvio.set("tipo_cod",producto_disponible.get("tipo_cod"));  
            productoxenvio.set("cantidad", (Integer)cantidad_producto.getValue());       
            productoxenvio.set("precio_unitario",producto_disponible.get("precio_unitario"));    
            productoxenvio.set("subtotal_previo",producto_disponible.get("subtotal_previo")); 
            productoxenvio.set("descuento",0);
            productoxenvio.set("flete",0);                    
            productoxenvio.set("subtotal_final",producto_disponible.get("subtotal_final"));             
            productos_a_agregar.add(productoxenvio);
        }
    }
    
    @FXML
    private void agregar_producto(ActionEvent event){
        if(producto_devuelto == null)
        {
            infoController.show("No ha seleccionado ningun producto"); 
           return;
        }        
        try{
            for(OrdenCompraxProducto producto_disponible : productos_disponibles){
                if (producto_disponible.getInteger("tipo_id") == producto_devuelto.getInteger("tipo_id")){
                    Integer cantidad = producto_disponible.getInteger("cantidad_descuento_disponible") - (Integer)cantidad_producto.getValue();
                    if (cantidad > 0){
                        if ((Integer)cantidad_producto.getValue() != 0){
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
     
    private void abrirModalProductos() throws Exception
    {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarProductosEnvios.fxml"));
            AgregarProductosEnviosController controller = new AgregarProductosEnviosController(productos_disponibles);
            loader.setController(controller);
            Scene modal_content_scene = new Scene((Parent)loader.load());
            modal_stage.setScene(modal_content_scene);       
            if(modal_stage.getModality() != Modality.APPLICATION_MODAL) modal_stage.initModality(Modality.APPLICATION_MODAL); 
            controller.devolverProductoEvent.addHandler((Object sender, agregarOrdenCompraProductoArgs args) -> {
                producto_devuelto = args.orden_compra_producto;
            });                 
            modal_stage.showAndWait();
        }catch(Exception e){
            infoController.show("No se pudo agregar los productos : " + e.getMessage());
        }
    }
    
    private void obtener_productos_disponibles_orden_compra(){
        productos_disponibles = OrdenCompraxProducto.where("orden_compra_id = ?", orden_compra_seleccionada.getId());
    }

    @FXML
    private void handleModalProducto(ActionEvent event) throws IOException{
        try{
            String temp_orden_compra = ordenes_compra_combobox.getSelectionModel().getSelectedItem().toString();
            if (!temp_orden_compra.equals(null)){
                orden_compra_seleccionada = OrdenCompra.findFirst("orden_compra_cod = ?", temp_orden_compra);
                obtener_productos_disponibles_orden_compra();
            }
            abrirModalProductos();
            if(producto_devuelto==null) return; 
            VerProducto.setText(TipoProducto.findFirst("tipo_cod = ? AND tipo_id = ?", producto_devuelto.get("tipo_cod"), producto_devuelto.get("tipo_id")).getString("nombre"));
        }catch(Exception e){
            infoController.show("Necesita seleccionar una orden de compra");
        }

    }    
    
    public void cliente_to_string() throws Exception{
        ArrayList<String> words = new ArrayList<>();
        for (Cliente cliente : auto_completado_list_cliente){
            words.add(cliente.getString("nombre"));
        }
        posibles_clientes = words;        
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

    public void llenar_ordenes_compra_cliente(){        
        try{
            ObservableList<String> ordenes_compra = FXCollections.observableArrayList();
            ordenes_compra.clear();
            ordenes_compra.addAll(OrdenCompra.where("client_id = ?", cliente_seleccionado.getId()).stream().map( x -> x.getString("orden_compra_cod")).collect(Collectors.toList()) );
            if (ordenes_compra.isEmpty()){
                infoController.show("El cliente no cuenta con pedidos pendientes : ");
                limpiar_formulario();
            }
            else{
                ordenes_compra_combobox.setItems(ordenes_compra);            
            }
        }catch(Exception e){
            infoController.show("Ha ocurrido un problema durante la seleccion de orden de compra : " + e.getMessage());
        }
    }
    
    private void handleAutoCompletar() {
        int i = 0;
        for (Cliente cliente : auto_completado_list_cliente){
            if (cliente.getString("nombre").equals(nombre_cliente.getText())){
                cliente_seleccionado = cliente;                               
                mostrar_informacion_cliente(cliente);
                llenar_ordenes_compra_cliente();
            }
        }
    }
    public void llenar_autocompletado() throws Exception{
        auto_completado_list_cliente = Cliente.findAll();
        cliente_to_string();
        autoCompletionBinding = TextFields.bindAutoCompletion(nombre_cliente, posibles_clientes);
        autoCompletionBinding.addEventHandler(EventType.ROOT, (event) -> {
            handleAutoCompletar();
        });        
    }
    
    public void limpiar_formulario(){
        nombre_cliente.clear();
        dni_cliente.clear();
        ruc_cliente.clear();
    }
    
    public void inhabilitar_formulario(){
        envio_formulario.setDisable(true);
    }
    
    public void habilitar_formulario(){
        envio_formulario.setDisable(false);
    }  
    
    @Override
    public void nuevo(){
       crearNuevo = true;
       habilitar_formulario();
       SpinnerValueFactory cantidad_productoValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0,1);
       cantidad_producto.setValueFactory(cantidad_productoValues);
    }
    
    public void llenar_tabla_envios(){
        masterData.clear();
        for( Envio envio : envios){
            masterData.add(envio);
        }
        columna_cliente.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("client_id")));
        columna_envio.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        columna_pedido.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("orden_compra_cod")));
        tabla_envios.setItems(masterData);   
    }
    
    public boolean cumple_condicion_busqueda(Envio envio, String codigo, String cliente, String codPedido){
        boolean match = true;        
        if ( codigo.equals("") && cliente.equals("") && codPedido.equals("")){
            match = true;
        }else {
            match = (!codigo.equals("")) ? (match && (envio.get("promocion_cod")).equals(codigo)) : match;
            Integer idCliente = (cliente_seleccionado!=null) ? (Integer)cliente_seleccionado.getId():0;
            match = (!cliente.equals("")) ? (match && (envio.get("cliente_id")==idCliente)) : match;
            match = (!codPedido.equals("")) ? (match && (envio.get("orden_compra_cod")).equals(codPedido)) : match;
        }
        return match;
    }

    @FXML
    public void buscar_envio(ActionEvent event) throws IOException{
        envios = Envio.findAll();
        masterData.clear();
        try{
            for(Envio envio : envios){
                if (cumple_condicion_busqueda(envio, envio_buscar.getText(), cliente_buscar.getText(), pedido_buscar.getText())){
                    masterData.add(envio);
                }
            }
        }catch(Exception e){
            infoController.show("No se pudo buscar el envio : " + e.getMessage());
        }
    }

    public EnviosController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
        crearNuevo = false;
        cliente_seleccionado = null;
        ordenes_compra_combobox = new ComboBox();
        productos_a_agregar = new ArrayList<OrdenCompraxProducto>();
        
    }    
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {  
        try {
            envios = Envio.findAll();
            llenar_tabla_envios();
            llenar_autocompletado();
            inhabilitar_formulario();            
        } catch (Exception ex) {
            infoController.show("No se pudo cargar la ventana envios : " + ex.getMessage());
        }
    }    
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Envios;
    }
}