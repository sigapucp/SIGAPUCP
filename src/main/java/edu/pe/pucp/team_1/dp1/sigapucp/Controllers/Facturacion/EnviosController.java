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
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenCompraProductoArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoArgs;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    
    //BUSQUEDA
    //-----------------------------------------------------//
        //PEDIDOS
    @FXML
    private TextField cod_pedido_buscar;
    @FXML
    private TextField cliente_pedido_buscar;
    @FXML
    private TableView<OrdenCompra> tabla_pedido;
    @FXML
    private TableColumn<OrdenCompra, String> columna_cliente;
    @FXML
    private TableColumn<OrdenCompra, String> columna_codigo_pedido;
        //PRODUCTOS
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
    //LOGICA
    //--------------------------------------------------//
    private InformationAlertController infoController;    
    private final ObservableList<OrdenCompra> pedidos = FXCollections.observableArrayList();
    private final ObservableList<OrdenCompraxProducto> productos = FXCollections.observableArrayList(); 
    private Boolean crearNuevo = false;    
    private OrdenCompra pedidoSeleccionado;
    private OrdenCompraxProducto producto_devuelto;
    private List<OrdenCompraxProducto> productos_orden_de_compra_a_enviar;
    private List<OrdenCompraxProducto> productos_seleccionados_a_enviar;
    Stage modal_stage = new Stage();
    
    //--------------------------------------------------//

    

    
    public void llenar_pedidos_tabla_index(){
        List<OrdenCompra> tem_pedido = OrdenCompra.findAll();
        pedidos.clear();
        columna_codigo_pedido.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompra, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("orden_compra_cod")));   
        columna_cliente.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompra, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        pedidos.addAll(tem_pedido);
        tabla_pedido.setItems(pedidos);        
    }
    public void inhabilitar_formulario(){
        envio_formulario.setDisable(true);
    }
    
    public void completar_cliente(Cliente cliente){
        String tipo_cliente = cliente.getString("tipo_cliente");
        String dni = cliente.getString("dni");
        String ruc = cliente.getString("ruc");   
        String nombre = cliente.getString("nombre");
        if(tipo_cliente.equals(Cliente.TIPO.PersonaNatural.name()))
        {
            dni_cliente.setText(dni);
            ruc_cliente.setDisable(true);
        }else
        {
            ruc_cliente.setText(ruc);
            dni_cliente.setDisable(true);
        }
        nombre_cliente.setText(nombre);        
    }
    
    public void llenar_productos_tabla(){
        productos.clear();
        productos.addAll(productos_seleccionados_a_enviar);

        columna_cod_producto.setCellValueFactory((CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        columna_nombre_producto.setCellValueFactory((CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
        columna_desc_producto.setCellValueFactory((CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("descripcion")));
        columna_cant_producto.setCellValueFactory((CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cantidad")));

        tabla_productos.setItems(productos);
    }
    
    public void mostrar_pedido(OrdenCompra pedido_seleccionado){
        Cliente cliente = Cliente.findById(pedido_seleccionado.get("client_id"));
        completar_cliente(cliente);
        productos_orden_de_compra_a_enviar = OrdenCompraxProducto.where("orden_compra_id = ?", pedido_seleccionado.getId());
        llenar_productos_tabla();
    }
    
    @FXML
    public void visualizar_pedido(ActionEvent event){
        crearNuevo = false;
        try {
            pedidoSeleccionado = tabla_pedido.getSelectionModel().getSelectedItem();
            if (pedidoSeleccionado == null) 
            {
                infoController.show("No ha seleccionado ningun Pedido");
            }

          mostrar_pedido(pedidoSeleccionado);                            
        } catch (Exception e) {
            infoController.show("Error al mostrar el pedido: " + e.getMessage());
        }           
    }    
    
    private void setAgregarProductos() throws Exception
    {
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarProductosEnvios.fxml"));
        AgregarProductosEnviosController controller = new AgregarProductosEnviosController(productos_orden_de_compra_a_enviar);
        loader.setController(controller);
        Scene modal_content_scene = new Scene((Parent)loader.load());
        modal_stage.setScene(modal_content_scene);       
        controller.devolverProductoEvent.addHandler((Object sender, agregarOrdenCompraProductoArgs args) -> {
            producto_devuelto = args.orden_compra_producto;
        });    
        }catch(Exception e){
            //System.out.println(e);
        }
    }
            
    @FXML
    private void handleAgregarProducto(ActionEvent event) throws IOException{
        modal_stage.showAndWait();
    }    
    
    public void EnviosController(){
        System.out.println("--------------------- 1");
        infoController = new InformationAlertController();
        pedidoSeleccionado = null;
        crearNuevo = false;
    }    
    
    public void initialize(URL location, ResourceBundle resources) {
        try{
            if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
            //llenar_pedidos_tabla_index();
            //inhabilitar_formulario();
            setAgregarProductos();
        }catch(Exception e)    {
            infoController.show("No se pudo inicializar el menu de Ordenes de Compra: " + e.getMessage());
        }
    }    
}
