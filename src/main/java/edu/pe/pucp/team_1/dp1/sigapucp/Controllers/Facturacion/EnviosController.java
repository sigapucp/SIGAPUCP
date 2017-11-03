/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.AgregarProductosController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private ComboBox<String> ordenes_compra_combobox;
    @FXML
    private Spinner<Integer> cantidad_producto;
    
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
    
    //LOGICA
        //--------------------------------------------------//
    
    private InformationAlertController infoController;    
    private final ObservableList<Envio> masterData = FXCollections.observableArrayList();
    private List<Envio> envios;
    private Boolean crearNuevo = false;    
    private Cliente cliente_seleccionado = null;

    //AUTOCOMPLETAD
        //--------------------------------------------------//

    private List<Cliente> auto_completado_list_cliente;
    ArrayList<String> posibles_clientes = new ArrayList<>();
    AutoCompletionBinding<String> autoCompletionBinding;    
    private List<Cliente> autoCompletadoList;    
    
    //MODALES FLUJO
        //--------------------------------------------------//
    Stage modal_stage = new Stage();
    private List<OrdenCompraxProducto> productos_a_agregar;
    private List<OrdenCompraxProducto> productos_disponibles;
    private OrdenCompraxProducto producto_devuelto;
    private OrdenCompra orden_compra_seleccionada;
 
    //----------------------------------------------------------------------------//

    private void eliminar_producto_de_lista_enviar(){
    //SI SE ELIMINA
        //suma existencis
        //elimina en lista enviar
    }
    
    private void agregar_producto_a_lista_enviar(ActionEvent event){
        try{
            for(OrdenCompraxProducto producto_disponible : productos_disponibles){
                if (producto_disponible.getId() == producto_devuelto.getId()){
                    Integer cantidad = producto_disponible.getInteger("cantidad") - cantidad_producto.getValue();
                    if (cantidad > 0){
                        producto_disponible.setInteger("cantidad", cantidad);
                        productos_a_agregar.add(producto_disponible);
                    }
                    else{
                        //mensaje de error
                    }
                    break;
                }
            }            
        }catch(Exception e){
            infoController.show("Error " + e.getMessage());
        }
    }
    
    private void setAgregarProductos() throws Exception
    {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarProductosEnvios.fxml"));
            obtener_productos_disponibles_orden_compra();
            AgregarProductosEnviosController controller = new AgregarProductosEnviosController(productos_disponibles);
            loader.setController(controller);
            Scene modal_content_scene = new Scene((Parent)loader.load());
            modal_stage.setScene(modal_content_scene);       
            controller.devolverProductoEvent.addHandler((Object sender, agregarOrdenCompraProductoArgs args) -> {
                producto_devuelto = args.orden_compra_producto;
            });   
        }catch(Exception e){
            infoController.show("No se pudo agregar los productos : " + e.getMessage());
        }
    }
    
    private void obtener_productos_disponibles_orden_compra(){
        //falta setear la orden de compra
        List<OrdenCompraxProducto> productos_en_orden_compra = OrdenCompraxProducto.where("orden_compra_id = ?", orden_compra_seleccionada.getId());
        productos_disponibles.clear();
        for(OrdenCompraxProducto producto : productos_en_orden_compra){
          OrdenesCompraxProductosxenvio producto_seleccionado = OrdenesCompraxProductosxenvio.findFirst("orden_compra_id = ? and tipo_id = ?", orden_compra_seleccionada.getId(), producto.getId());
          Integer cantidad = producto.getInteger("cantidad") - producto_seleccionado.getInteger("cantidad");
          producto.set("cantidad", cantidad);
          productos_disponibles.add(producto);
        }
    }

    @FXML
    private void handleAgregarProducto(ActionEvent event) throws IOException{
        modal_stage.showAndWait();
    }    
    
    public void cliente_to_string() throws Exception{
        ArrayList<String> words = new ArrayList<>();
        for (Cliente cliente : autoCompletadoList){
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
        ObservableList<String> ordenes_compra = FXCollections.observableArrayList();
        ordenes_compra.addAll(OrdenCompra.where("client_id = ?", cliente_seleccionado.getId()).stream().map( x -> x.getString("orden_compra_id")).collect(Collectors.toList()) );
        ordenes_compra_combobox.setItems(ordenes_compra);
    }
    
    private void handleAutoCompletar() {
        
        int i = 0;
        for (Cliente cliente : autoCompletadoList){
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
    }
    
    public void EnviosController(){
        infoController = new InformationAlertController();
        crearNuevo = false;
        cliente_seleccionado = null;
    }    
    
    public void limpiar_tabla_index(){
        tabla_envios.getItems().clear();
    }
    
    public void llenar_tabla_envios(){
        limpiar_tabla_index();
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
            Integer idCliente = (Integer) cliente_seleccionado.getId();
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
    
    public void initialize(URL location, ResourceBundle resources) {        
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        envios = Envio.findAll();
        llenar_tabla_envios();
        inhabilitar_formulario();
    }    
    
    /*@Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Envios;
    }*/
}
