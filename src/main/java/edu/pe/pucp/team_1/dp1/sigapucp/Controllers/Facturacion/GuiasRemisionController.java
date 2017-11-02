/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.print.DocFlavor;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Gustavo
 */
public class GuiasRemisionController extends Controller{
    
    //BUSQUEDA
    //-----------------------------------------------------//
    @FXML
    private TextField cod_pedido_buscar;
    @FXML
    private TextField cliente_pedido_buscar;
    @FXML
    private DatePicker fecha_inicio_buscar;
    @FXML
    private DatePicker fecha_fin_buscar;
    
    @FXML
    private TableView<OrdenCompra> tabla_pedido;
    @FXML
    private TableColumn<OrdenCompra, String> columna_cliente;
    @FXML
    private TableColumn<OrdenCompra, String> columna_codigo_pedido;
    @FXML
    private TableColumn<OrdenCompra, String> columna_pedido_fecha;
    
    //FORMULARIO
    //--------------------------------------------------//
        //Cliente
    @FXML
    private AnchorPane pedido_form;
    @FXML
    private TextField nombre_cliente;
    @FXML
    private TextField ruc_cliente;
    @FXML
    private TextField dni_cliente;
        //Producto
    @FXML
    private TableView<OrdenCompraxProducto> tabla_productos;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_codigo;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_nombre;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_descripcion;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_cantidad;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_peso;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_unidad_medida;
    
    //LOGICA
    //--------------------------------------------------//
    private InformationAlertController infoController;
    private final ObservableList<OrdenCompra> pedidos = FXCollections.observableArrayList();
    private Boolean crearNuevo = false;    
    private OrdenCompra pedidoSeleccionado;

    
    public GuiasRemisionController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        infoController = new InformationAlertController();
        pedidoSeleccionado = null;
        crearNuevo = false;
    }
    
    public void llenar_pedidos_tabla_index(){
        List<OrdenCompra> tem_pedido = OrdenCompra.findAll();
        pedidos.clear();
        columna_codigo_pedido.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompra, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("orden_compra_cod")));   
        columna_cliente.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompra, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        columna_pedido_fecha.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompra, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("fecha_emision")));
        pedidos.addAll(tem_pedido);
        tabla_pedido.setItems(pedidos);
    }
    public void inhabilitar_formulario(){
        pedido_form.setDisable(true);
    }
    
    public void completar_productos(){
        
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
    
    public void mostrar_pedido(OrdenCompra pedido_seleccionado){
        //completar_cliente(cliente);
        //completar_productos();
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
    public void initialize(DocFlavor.URL location, ResourceBundle resources) {
        try{
            llenar_pedidos_tabla_index();
            inhabilitar_formulario();
        }catch(Exception e)    {
            infoController.show("No se pudo inicializar el menu de Ordenes de Compra: " + e.getMessage());
        }
    }
    
}
