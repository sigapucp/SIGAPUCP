/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.AgregarClientesController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.GuiaRemision;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.DocVenta;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Moneda;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarClienteArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarGuiaRemisionArgs;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class DocumentosDeVentaController extends Controller{

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField dni_cliente;
    @FXML
    private TextField ruc_cliente;
    @FXML
    private TextField nombre_cliente;
    @FXML
    private ComboBox<String> ver_moneda;
    @FXML
    private TextField codigo_venta;
    @FXML
    public DatePicker fecha_emision;    
    
    //------------------------------------------
    @FXML
    private TableView<OrdenCompraxProducto> tabla_detalle;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_cantidad;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_codigo;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_descripcion;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_pu;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_descuento;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_flete;
    @FXML
    private TableColumn<OrdenCompraxProducto, String> columna_subtotal;
    
    Stage modal_stage = new Stage();

    private Cliente cliente_seleccionado = null;
    private List<GuiaRemision> guias_remision = new ArrayList<GuiaRemision>();
    private InformationAlertController infoController;  
    private GuiaRemision guia_devuelta;
    private boolean crear_nuevo;
    private GuiaRemision guia_seleccionada;
    private final ObservableList<OrdenCompraxProducto> productos = FXCollections.observableArrayList();
    private List<OrdenCompraxProducto> productos_temp = new ArrayList<OrdenCompraxProducto>();
    
    public void crear_doc_venta(){
        try{
            Base.openTransaction();
            DocVenta venta = new DocVenta();
            venta.set("client_id", cliente_seleccionado.getId());
            venta.set("doc_venta_cod", codigo_venta.getText());
            venta.set("estado", DocVenta.ESTADO.PENDIENTE.name());
            venta.set("last_user_change", usuarioActual.getString("usuario_cod"));
            venta.set("guia_id", guia_seleccionada.getId());
            venta.set("guia_cod", guia_seleccionada.getString("guia_cod"));
            LocalDate fechaLocal = fecha_emision.getValue();
            Date fecha = Date.valueOf(fechaLocal);                    
            venta.set("fecha_emision", fecha);
            venta.saveIt();
            Base.commitTransaction();
        }catch ( Exception e){
            infoController.show("Se ha creado el documento de venta: ");        
        }
    }
    
    public void editar_doc_venta(){
        
    }
    
    public void guardar(){
        if (crear_nuevo){
            crear_doc_venta();
        }
        else{
            if(guia_seleccionada == null){
                infoController.show("No ha seleccionado una guia");
                return;                
            }
            editar_doc_venta();
        }
        crear_nuevo = false;
    }
    
    public void llenar_tabla_productos(){
        productos.addAll(productos_temp);
        columna_codigo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        columna_cantidad.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cantidad")));
        columna_pu.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("precio_unitario")));
        columna_descripcion.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("descuento")));
        columna_flete.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("flete")));
        columna_subtotal.setCellValueFactory((TableColumn.CellDataFeatures<OrdenCompraxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("subtotal_final")));        
        tabla_detalle.setItems(productos);
    }
    
    public void set_agregar_guia_remision(){
        try{
            //limpiar todo, se entiende como uno nuevo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarGuiasRemision.fxml"));
            AgregarGuiasRemisionController controller = new AgregarGuiasRemisionController(guias_remision);
            loader.setController(controller);
            Scene modal_content_scene = new Scene((Parent)loader.load());
            modal_stage.setScene(modal_content_scene);
            if(modal_stage.getModality() != Modality.APPLICATION_MODAL) modal_stage.initModality(Modality.APPLICATION_MODAL);    
            controller.devolver_guia_remision.addHandler((Object sender, agregarGuiaRemisionArgs args) -> {
                guia_devuelta = args.guia_remision;
                productos_temp = OrdenCompraxProducto.where("orden_compra_cod = ?", guia_devuelta.get("orden_compra_cod"));
                llenar_tabla_productos();
            });
            modal_stage.showAndWait();
        }catch (Exception e){
            System.out.println(e);
        }
       
    }
    
    @FXML
    public void agregar_guia_remision(ActionEvent event) throws IOException{
        try{
            guias_remision = GuiaRemision.where("client_id = ?", cliente_seleccionado.getId());
            if(cliente_seleccionado == null) {
                infoController.show("Necesita seleccionar un cliente");
                return;
            }
            set_agregar_guia_remision();
        }catch(Exception e){
            System.out.println(e);
        }
    }
    private List<Cliente> auto_completado_list_cliente;
    ArrayList<String> posibles_clientes = new ArrayList<>();
    AutoCompletionBinding<String> autoCompletionBinding;   
    
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
    private void handleAutoCompletar() {
        int i = 0;
        for (Cliente cliente : auto_completado_list_cliente){
            if (cliente.getString("nombre").equals(nombre_cliente.getText())){
                cliente_seleccionado = cliente;                               
                mostrar_informacion_cliente(cliente);
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
    
    public void llenar_moneda_combobox(){
        ObservableList<String> monedas = FXCollections.observableArrayList();  
        monedas.addAll(Moneda.findAll().stream().map(x -> x.getString("nombre")).collect(Collectors.toList()));
        ver_moneda.setItems(monedas);        
    }
    
    public DocumentosDeVentaController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            llenar_autocompletado();
            llenar_moneda_combobox();
        }catch(Exception e){
            
        }
    }
    
    @Override
    public Menu.MENU getMenu(){
        return Menu.MENU.DocumentosdeVenta;
    }
}
