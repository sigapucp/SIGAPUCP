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
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.Envio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
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
        //PRODUCTOS
/*
    @FXML
    private TextField agregar_cod_producto;
    @FXML
    private TextField agregar_nombre_producto;
    @FXML
    private ComboBox agregar_cat_producto;
    @FXML
    private TableView<OrdenCompraxProducto> tabla_productos;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_cod_producto;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_nombre_producto;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_desc_producto;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_cant_producto;
    */
    //LOGICA
    //--------------------------------------------------//
    private InformationAlertController infoController;    
    private final ObservableList<Envio> envios = FXCollections.observableArrayList();
    private Boolean crearNuevo = false;    
    private Cliente cliente_seleccionado = null;
    //autocompletado
    private List<Cliente> auto_completado_list_cliente;
    ArrayList<String> posibles_clientes = new ArrayList<>();
    AutoCompletionBinding<String> autoCompletionBinding;    
    private List<Cliente> autoCompletadoList;    
    //modales
    Stage modal_stage = new Stage();
    private List<OrdenCompraxProducto> productos_a_agregar;
    private List<OrdenCompraxProducto> productos_disponibles;
    private OrdenCompraxProducto producto_devuelto;
    /*
    private OrdenCompra pedidoSeleccionado;
    */
/*      
    public void actualizar_lista_orden_de_compra_productos_a_enviar(){
        
    }
    public void agregar_producto_a_enviar(ActionEvent event){
        
        actualizar_lista_orden_de_compra_productos_a_enviar();
    }
    public void eliminar_producto_a_enviar(){
        actualizar_lista_orden_de_compra_productos_a_enviar();
    }
*/            
    private void actualizar_productos_en_listas(){
        //se debe enviar devuelta a envioscontroller
        //se le dice que actualize su lsita de compra a enviar con la resta del producto seleccionado
        //se le agregar el nuevo producto a la lista a mostar en la tabla
        //fin                
    }
    
    private void obtener_productos_disponibles_orden_compra(){
        
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
        actualizar_productos_en_listas();
        }catch(Exception e){
            System.out.println(e);
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
    
    private void mostar_informacion_cliente(Cliente cliente){
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
                mostar_informacion_cliente(cliente);
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
    
    public void llenar_tabla_pedidos(){
        List<Envio> temp_envios = Envio.findAll();
        envios.clear();
        columna_cliente.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("client_id")));
        columna_envio.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        columna_pedido.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("orden_compra_cod")));
        envios.addAll(temp_envios);
        tabla_envios.setItems(envios);   
    }    
    
    public void initialize(URL location, ResourceBundle resources) {
        try{
            if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
            llenar_tabla_pedidos();
            inhabilitar_formulario();
        }catch(Exception e)    {
            infoController.show("No se pudo inicializar el menu de Ordenes de Compra: " + e.getMessage());
        }
    }    
}
