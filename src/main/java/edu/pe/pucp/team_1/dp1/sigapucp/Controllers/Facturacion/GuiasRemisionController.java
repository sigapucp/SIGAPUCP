/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.Envio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.GuiaRemision;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import java.net.URL;
import java.time.LocalDate;
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
    //--------------------------------------------------//
        //DATOS A INGRESAR
    @FXML
    private TextField numero_remision;
    @FXML
    private TextField partida_remision;
    @FXML
    private TextField llegada_remision;
    @FXML
    private DatePicker fecha_remision;
    @FXML
    private TextField marca_vehiculo_remision;
    @FXML
    private TextField placa_vehiculo_remision;
    @FXML
    private TextField licencia_remision;
    @FXML
    private TextField codigo_remision;
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
    private final ObservableList<Envio> envios = FXCollections.observableArrayList();
    private Boolean crearNuevo = false;    
    private OrdenCompra pedidoSeleccionado;
    private Envio envio_seleccionado;
    private Cliente cliente_seleccionado;
    
    public void crear_remision(){
        try{
            String punto_partida = partida_remision.getText();
            String punto_llegada = llegada_remision.getText();
            String marca_vehiculo = marca_vehiculo_remision.getText();
            String placa_vehiculo = placa_vehiculo_remision.getText();
            String licencia = licencia_remision.getText();
            LocalDate fechaLocal = fecha_remision.getValue();
            GuiaRemision guia_remision = new GuiaRemision();

            Base.openTransaction();
            guia_remision.set("guia_cod", codigo_remision.getText());
            guia_remision.set("punto_partida", punto_partida);
            guia_remision.set("punto_llegada", punto_llegada);
            guia_remision.set("marca_vehiculo", marca_vehiculo);
            guia_remision.set("placa_vehiculo", placa_vehiculo);
            guia_remision.set("client_id", cliente_seleccionado.getId());
            guia_remision.set("envio_id", envio_seleccionado.getInteger("envio_id"));
            guia_remision.set("envio_cod", envio_seleccionado.getString("envio_cod"));
            guia_remision.set("orden_compra_cod", envio_seleccionado.getString("orden_compra_cod"));
            guia_remision.set("orden_compra_id", envio_seleccionado.getInteger("orden_compra_id"));
            guia_remision.set("estado", GuiaRemision.ESTADO.PENDIENTE.name());
            java.sql.Date fecha = java.sql.Date.valueOf(fechaLocal);        
            guia_remision.setDate("fecha_inicio_traslado", fecha);
            guia_remision.saveIt();
            Base.commitTransaction();            
        }catch(Exception e){
            infoController.show("Ocurrio un error durante la creacion de una guia de remision : " + e.getMessage());
        }

    }
    
    @Override
    public void guardar(){
        crear_remision();
    }
    
    public void inhabilitar_formulario(){
        pedido_form.setDisable(true);
    }
    
    public void setear_cliente(){
        Cliente cliente_temp = Cliente.findById(envio_seleccionado.getInteger("client_id"));
        String tipo_cliente = cliente_temp.getString("tipo_cliente");
        String dni = cliente_temp.getString("dni");
        String ruc = cliente_temp.getString("ruc");
        cliente_seleccionado= cliente_temp;
        
        if(tipo_cliente.equals(Cliente.TIPO.PersonaNatural.name()))
        {
            dni_cliente.setText(dni);
            ruc_cliente.setDisable(true);
        }else
        {
            ruc_cliente.setText(ruc);
            dni_cliente.setDisable(true);
        }
        nombre_cliente.setText(cliente_temp.getString("nombre"));
        nombre_cliente.setEditable(false);
        dni_cliente.setEditable(false);
        ruc_cliente.setEditable(false);
    }
    
    public void setear_productos(){
        try{
            ObservableList<OrdenCompraxProducto> productos = FXCollections.observableArrayList(); 
            productos.clear();
            List<OrdenCompraxProducto> productos_agregados = OrdenCompraxProducto.where("orden_compra_cod = ?", envio_seleccionado.get("orden_compra_cod"));
            productos.addAll(productos_agregados);
            columna_codigo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
            columna_nombre.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
            columna_descripcion.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("descripcion")));
            columna_cantidad.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cantidad")));
            columna_peso.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cantidad")));
            columna_unidad_medida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cantidad")));
            tabla_productos.setItems(productos);
        }catch(Exception e){
            infoController.show("Ocurrio un error durante la visualizacion de los productos del envio : " + e.getMessage());
        }           
    }
    
    @FXML
    public void visualizar_envio(ActionEvent event){
        envio_seleccionado = tabla_envios.getSelectionModel().getSelectedItem();
        if (envio_seleccionado == null){
            infoController.show("Salida no seleccionada");  
            return;            
        }
        try{
            setear_cliente();
            setear_productos();
        }catch (Exception e){
            infoController.show("Ocurrio un error durante la visualizacion del envio : " + e.getMessage());
        }
    }
    public void llenar_tabla_envios(){
        List<Envio> temp_envios = Envio.findAll();
        envios.addAll(temp_envios);
        columna_cliente.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("client_id")));
        columna_envio.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        columna_pedido.setCellValueFactory((TableColumn.CellDataFeatures<Envio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("orden_compra_cod")));
        tabla_envios.setItems(envios);
    }

    public GuiasRemisionController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        infoController = new InformationAlertController();
        pedidoSeleccionado = null;
        crearNuevo = true;
    }
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.GuiaDeRemision;
    }

    public void initialize(URL location, ResourceBundle resources) {
        try{
            llenar_tabla_envios();
            //inhabilitar_formulario();
        }catch(Exception e)    {
            infoController.show("No se pudo inicializar el menu de Ordenes de Compra: " + e.getMessage());
        }
    }
    
}
