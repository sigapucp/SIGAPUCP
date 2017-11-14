/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Facturacion;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.Envio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.GuiaRemision;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Unidad;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad.AccionLoggerSingleton;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenesCompraxProductosxenvio;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.print.DocFlavor;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
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
    private TableView<GuiaRemision> tabla_guias;
    @FXML
    private TableColumn<GuiaRemision, String> columna_cliente;
    @FXML
    private TableColumn<GuiaRemision, String> columna_guia;
    @FXML
    private TableColumn<GuiaRemision, String> columna_pedido;
    
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
    @FXML
    private ComboBox envios_combobox;    
    @FXML
    private TextField peso_total;
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
    private TableView<OrdenesCompraxProductosxenvio> tabla_productos;
    @FXML
    private TableColumn<OrdenesCompraxProductosxenvio, String> columna_codigo;
    @FXML
    private TableColumn<OrdenesCompraxProductosxenvio, String> columna_nombre;
    @FXML
    private TableColumn<OrdenesCompraxProductosxenvio, String> columna_descripcion;
    @FXML
    private TableColumn<OrdenesCompraxProductosxenvio, String> columna_cantidad;
    @FXML
    private TableColumn<OrdenesCompraxProductosxenvio, String> columna_peso;
    @FXML
    private TableColumn<OrdenesCompraxProductosxenvio, String> columna_unidad_medida;
    
    @FXML
    private Button boton_guardar;
    //LOGICA
    //--------------------------------------------------//
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmationController;
    
    private final ObservableList<GuiaRemision> guias = FXCollections.observableArrayList();
    private Boolean crearNuevo = false;    
    private OrdenCompra pedidoSeleccionado;
    private Envio envio_seleccionado;
    private GuiaRemision guia_seleccionada;
    private Cliente cliente_seleccionado;
    private List<Cliente> auto_completado_list_cliente;
    ArrayList<String> posibles_clientes = new ArrayList<>();
    AutoCompletionBinding<String> autoCompletionBinding;   
    private Boolean envio_nuevo;
    
    
    
    public void crear_remision(){
        try{
            
            if (cliente_seleccionado == null){
                infoController.show("No se ha podido crear la guia de remision : necesita de un cliente ");
                return;
            }
            if (envio_seleccionado == null){
                infoController.show("No se ha podido crear la guia de remision : necesita un envio ");
                return;
            }            
            String punto_partida = partida_remision.getText();
            String punto_llegada = llegada_remision.getText();
            String marca_vehiculo = marca_vehiculo_remision.getText();
            String placa_vehiculo = placa_vehiculo_remision.getText();
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
            guia_remision.set("estado", GuiaRemision.ESTADO.ENPROCESO.name());
            guia_remision.set("last_user_change", usuarioActual.getString("usuario_cod"));
            java.sql.Date fecha = java.sql.Date.valueOf(fechaLocal);        
            guia_remision.setDate("fecha_inicio_traslado", fecha);
            envio_seleccionado.set("estado",Envio.ESTADO.COMPLETA.name());
            guia_remision.saveIt();
            envio_seleccionado.saveIt();
            infoController.show("Se ha creado exitosamente ");
            Base.commitTransaction();            
        }catch(Exception e){
            infoController.show("Ocurrio un error durante la creacion de una guia de remision : " + e.getMessage());
            inhabilitar_formulario();
        }

    }
    
    @Override
    public void guardar(){
        try{
            if (crearNuevo){
                if (!Usuario.tienePermiso(permisosActual, Menu.MENU.GuiaDeRemision, Accion.ACCION.CRE)){
                    infoController.show("No tiene los permisos suficientes para realizar esta acción");
                    crearNuevo = false;
                    return;
                }
                crear_remision();
                AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.CRE, Menu.MENU.GuiaDeRemision ,this.usuarioActual);
                inhabilitar_formulario();
                llenar_tabla_guias();    
                crearNuevo = false;
            }
        }catch(Exception e){
            infoController.show("Ocurrio un error durante la creacion de una guia de remision : " + e.getMessage());
            inhabilitar_formulario();            
        }

    }
    
    @Override
    public void cambiarEstado(){
        if(guia_seleccionada == null)
        {
            infoController.show("No ha seleccionado ninguna Guia de Remision");
            return;
        }
        
        if(guia_seleccionada.getString("estado").equals(GuiaRemision.ESTADO.COMPLETA.name()))
        {
            infoController.show("La guia ya ha sido completada");
            return;
        }                        
        try{
            Base.openTransaction();
            guia_seleccionada.set("estado", GuiaRemision.ESTADO.COMPLETA.name());
            infoController.show("Se ha completado correctamente");
            Base.connection();
        }catch(Exception e){
            infoController.show("Ha ocurrido un error");
        }
    }
    
    @Override
    public void desactivar(){
        guia_seleccionada.set("estado", GuiaRemision.ESTADO.CANCELADA.name());
    }
    
    @Override
    public void nuevo(){
        habilitar_formulario();
        envio_nuevo = true;
        crearNuevo = true;
        limpiar_formulario();
        desbloquear_formulario();

        //boton_guardar.setDisable(false);
    }
    
    public void inhabilitar_formulario(){
        pedido_form.setDisable(true);
    }
    
    public void habilitar_formulario(){
        pedido_form.setDisable(false);
    }
    
    public void setear_cliente(){
        String tipo_cliente = cliente_seleccionado.getString("tipo_cliente");
        String dni = cliente_seleccionado.getString("dni");
        String ruc = cliente_seleccionado.getString("ruc");
        
        if(tipo_cliente.equals(Cliente.TIPO.PersonaNatural.name()))
        {
            dni_cliente.setText(dni);
            ruc_cliente.setDisable(true);
        }else
        {
            ruc_cliente.setText(ruc);
            dni_cliente.setDisable(true);
        }
        nombre_cliente.setText(cliente_seleccionado.getString("nombre"));
        nombre_cliente.setEditable(false);
        dni_cliente.setEditable(false);
        ruc_cliente.setEditable(false);
    }
    
    public void calcular_totales(List<OrdenesCompraxProductosxenvio> productos){
        Double peso = 0.0;
        peso = productos.stream().mapToDouble(p -> {
            return TipoProducto.findById(p.get("tipo_id")).getDouble("peso")*p.getInteger("cantidad");
        }).sum();
        peso_total.setText(peso.toString());        
    }
    
    public void setear_productos(){
        try{           
            String envio_temp = envios_combobox.getSelectionModel().getSelectedItem().toString();
            if ( envio_seleccionado != null){
                String orden_compra_actual = envio_seleccionado.getString("envio_cod");
                envio_nuevo = !envio_temp.equals(orden_compra_actual);
            }
            if (envio_nuevo){
                envio_seleccionado = Envio.findFirst("envio_cod = ?", envio_temp);
                envio_nuevo = false;
            }
            ObservableList<OrdenesCompraxProductosxenvio> productos = FXCollections.observableArrayList(); 
            productos.clear();
            List<OrdenesCompraxProductosxenvio> productos_agregados = OrdenesCompraxProductosxenvio.where("envio_id = ?", envio_seleccionado.getInteger("envio_id"));
            productos.addAll(productos_agregados);            

            columna_codigo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenesCompraxProductosxenvio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
            columna_nombre.setCellValueFactory((TableColumn.CellDataFeatures<OrdenesCompraxProductosxenvio, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
            columna_descripcion.setCellValueFactory((TableColumn.CellDataFeatures<OrdenesCompraxProductosxenvio, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("descripcion")));
            columna_cantidad.setCellValueFactory((TableColumn.CellDataFeatures<OrdenesCompraxProductosxenvio, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cantidad")));
            columna_peso.setCellValueFactory((TableColumn.CellDataFeatures<OrdenesCompraxProductosxenvio, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("peso")));
            columna_unidad_medida.setCellValueFactory((TableColumn.CellDataFeatures<OrdenesCompraxProductosxenvio, String> p) -> new ReadOnlyObjectWrapper(Unidad.findById(TipoProducto.findById(p.getValue().get("tipo_id")).getInteger("unidad_peso_id")).getString("nombre")));
            tabla_productos.setItems(productos);
            calcular_totales(productos_agregados);
        }catch(Exception e){
            infoController.show("Ocurrio un error durante la visualizacion de los productos del envio : " + e.getMessage());
        }           
    }
    
    public void combobox_accion(ActionEvent event){
        setear_productos();        
    }
    
    public void bloquear_formulario(){
        nombre_cliente.setEditable(false);
        ruc_cliente.setEditable(false);
        dni_cliente.setEditable(false);
        envios_combobox.setDisable(true);
        
        fecha_remision.setDisable(true);
        partida_remision.setEditable(false);
        codigo_remision.setEditable(false);
        llegada_remision.setEditable(false);
        
        marca_vehiculo_remision.setEditable(false);
        placa_vehiculo_remision.setEditable(false);
    }
    
    public void desbloquear_formulario(){
        nombre_cliente.setEditable(true);
        ruc_cliente.setEditable(true);
        dni_cliente.setEditable(true);        
        envios_combobox.setDisable(false);
        
        fecha_remision.setDisable(false);
        partida_remision.setEditable(true);
        codigo_remision.setEditable(true);
        llegada_remision.setEditable(true);
        
        marca_vehiculo_remision.setEditable(true);
        placa_vehiculo_remision.setEditable(true);
    }
    
    public void completar_datos(){
        LocalDate date = guia_seleccionada.getDate("fecha_inicio_traslado").toLocalDate();
        fecha_remision.setValue(date);
        partida_remision.setText(guia_seleccionada.getString("punto_partida"));
        codigo_remision.setText(guia_seleccionada.getString("guia_cod"));
        llegada_remision.setText(guia_seleccionada.getString("punto_llegada"));
        marca_vehiculo_remision.setText(guia_seleccionada.getString("marca_vehiculo"));
        placa_vehiculo_remision.setText(guia_seleccionada.getString("placa_vehiculo"));
    }
    
    @FXML
    public void visualizar_guia(ActionEvent event){
        //falta desactivar boton guardar
        //boton_guardar.setDisable(true);
        crearNuevo = false;
        habilitar_formulario();
        guia_seleccionada = tabla_guias.getSelectionModel().getSelectedItem();
        if (guia_seleccionada == null){
            infoController.show("Salida no seleccionada");  
            return;            
        }
        try{
            cliente_seleccionado = Cliente.findById(guia_seleccionada.getInteger("client_id"));
            envio_seleccionado = Envio.findById(guia_seleccionada.getInteger("envio_id"));
            envios_combobox.setValue(guia_seleccionada.getString("envio_cod"));
            setear_cliente();
            completar_datos();
            setear_productos();
            bloquear_formulario();
        }catch (Exception e){
            infoController.show("Ocurrio un error durante la visualizacion del envio : " + e.getMessage());
        }
    }
    
    public void llenar_tabla_guias(){
        List<GuiaRemision> temp_guias = GuiaRemision.findAll();
        tabla_guias.getItems().clear();
        guias.addAll(temp_guias);
        columna_guia.setCellValueFactory((TableColumn.CellDataFeatures<GuiaRemision, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("guia_cod")));
        columna_cliente.setCellValueFactory((TableColumn.CellDataFeatures<GuiaRemision, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        columna_pedido.setCellValueFactory((TableColumn.CellDataFeatures<GuiaRemision, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("orden_compra_cod")));
        tabla_guias.setItems(guias);
    }
    
    public void limpiar_formulario(){
        nombre_cliente.clear();
        dni_cliente.clear();
        ruc_cliente.clear();
        
        if(envios_combobox.getSelectionModel()!=null) envios_combobox.getSelectionModel().clearSelection();        
        envios_combobox.getItems().clear();
        
        fecha_remision.getEditor().clear();
        codigo_remision.clear();
        partida_remision.clear();
        llegada_remision.clear();
        
        marca_vehiculo_remision.clear();
        placa_vehiculo_remision.clear();
        
        tabla_productos.getItems().clear();
    }    
    
    public void llenar_envios_cliente(){        
        try{
            ObservableList<String> temp_guias = FXCollections.observableArrayList();
            temp_guias.addAll(Envio.where("client_id = ? and estado = ?", cliente_seleccionado.getId(), Envio.ESTADO.ENPROCESO.name()).stream().map( x -> x.getString("envio_cod")).collect(Collectors.toList()) );
            if (temp_guias.isEmpty()){
                infoController.show("El cliente no cuenta con Envios en despacho : ");
                limpiar_formulario();
            }
            else{
                envios_combobox.setItems(temp_guias);            
            }
        }catch(Exception e){
            infoController.show("Ha ocurrido un problema durante la seleccion de envios : " + e.getMessage());
            inhabilitar_formulario();
        }
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
                setear_cliente();
                llenar_envios_cliente();                
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
            llenar_tabla_guias();
            llenar_autocompletado();
            inhabilitar_formulario();
        }catch(Exception e)    {
            infoController.show("No se pudo inicializar el menu de Ordenes de Compra: " + e.getMessage());
        }
    }
    
}
