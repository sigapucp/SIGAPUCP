/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.GuiaRemision;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad.AccionLoggerSingleton;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Simulacion.Envio;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Sistema.ParametroSistema;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.DocVenta;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.DocVentaxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Moneda;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompra;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenesCompraxProductosxenvio;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarGuiaRemisionArgs;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TextField ver_moneda;    
    @FXML
    public DatePicker fecha_emision;    
    @FXML
    private TextField subtotal;
    @FXML
    private TextField importe_total;
    @FXML
    private TextField igv_total;
    @FXML
    private TextField guia_remision_busqueda;
   @FXML
    private TextField tipo_doc;    
    
    //------------------------------------------
    @FXML
    private TableView<DocVentaxProducto> tabla_detalle;
    @FXML
    private TableColumn<DocVentaxProducto, String> columna_cantidad;
    @FXML
    private TableColumn<DocVentaxProducto, String> columna_codigo;
    @FXML
    private TableColumn<DocVentaxProducto, String> columna_descripcion;
    @FXML
    private TableColumn<DocVentaxProducto, String> columna_pu;
    @FXML
    private TableColumn<DocVentaxProducto, String> columna_descuento;
    @FXML
    private TableColumn<DocVentaxProducto, String> columna_flete;
    @FXML
    private TableColumn<DocVentaxProducto, String> columna_subtotal;
   //---------------------------------------------------------------
    @FXML
    private TableView<DocVenta> tabla_ventas;
    @FXML
    private TableColumn<DocVenta, String> ventas_codigo;
    @FXML
    private TableColumn<DocVenta, String> ventas_cliente;
    @FXML
    private TableColumn<DocVenta, String> ventas_fecha;
    
    @FXML
    private AnchorPane formulario;
    
    Stage modal_stage = new Stage();

    private Cliente cliente_seleccionado = null;
    private List<GuiaRemision> guias_remision = new ArrayList<GuiaRemision>();
    private InformationAlertController infoController;  
    private GuiaRemision guia_devuelta;
    private boolean crear_nuevo;
    private DocVenta documento_seleccionado;
    private final ObservableList<DocVentaxProducto> productos = FXCollections.observableArrayList();
    private List<DocVentaxProducto> productos_temp = new ArrayList<DocVentaxProducto>();
    
    private List<Cliente> auto_completado_list_cliente;
    ArrayList<String> posibles_clientes = new ArrayList<>();
    AutoCompletionBinding<String> autoCompletionBinding;   
    
    public void crear_doc_venta(){
        try{
            if (cliente_seleccionado == null){
                infoController.show("No se ha podido crear el documento de venta : necesita de un cliente ");
                return;
            }
            if (guia_devuelta == null){
                infoController.show("No se ha podido crear el documento de venta : necesita una guia de remision ");
                return;
            }
            Base.openTransaction();
            DocVenta venta = new DocVenta();
            venta.set("client_id", cliente_seleccionado.getId());
            String cod = "DOC" + String.valueOf(Integer.valueOf(String.valueOf((Base.firstCell("select last_value from docventas_doc_venta_id_seq")))) + 1);
            venta.set("doc_venta_cod", cod);
            venta.set("estado", DocVenta.ESTADO.PENDIENTE.name());
            venta.set("last_user_change", usuarioActual.getString("usuario_cod"));
            venta.set("guia_id", guia_devuelta.getId());
            venta.set("guia_cod", guia_devuelta.getString("guia_cod"));
            venta.set("tipo",tipo_doc.getText());
            LocalDate fechaLocal = fecha_emision.getValue();
            Date fecha = Date.valueOf(fechaLocal);  
            venta.set("fecha_emision", fecha);
            venta.saveIt();
            guia_devuelta.set("estado", GuiaRemision.ESTADO.COMPLETA.name());
            guia_devuelta.saveIt();
            Base.commitTransaction();
            infoController.show("Se creo satisfactoriamente");
        }catch ( Exception e){
            infoController.show("Ha sucedido un error en la creacion: " + e.getMessage());
        }
    }
    
    public void setPedidoVisible(DocVenta venta){
        cliente_seleccionado = Cliente.findById(venta.getInteger("client_id"));
        mostrar_informacion_cliente(cliente_seleccionado);       
        //ver_moneda.setValue(venta.getString("estado"));
        tipo_doc.setText(venta.getString("tipo"));
        guia_remision_busqueda.setText(venta.getString("guia_cod"));
        LocalDate fecha = venta.getDate("fecha_emision").toLocalDate();
        fecha_emision.setValue(fecha);
        guia_devuelta = GuiaRemision.findById(venta.getInteger("guia_id"));
        
        Envio envio = Envio.findById(guia_devuelta.getInteger("envio_id"));
        OrdenCompra ordenCompra = OrdenCompra.findById(envio.getInteger("orden_compra_id"));
        llenarInfoDocVenta(ordenCompra);
        productos_temp = getProductosGuia(guia_devuelta);               
        productos_temp.clear();        
        productos_temp.addAll(getProductosGuia(guia_devuelta));
        llenar_tabla_productos();        
        calcular_totales();
    }
    
    public void bloquear_datos(){
        nombre_cliente.setEditable(false);
        dni_cliente.setEditable(false);
        ruc_cliente.setEditable(false);
        ver_moneda.setEditable(false);
        fecha_emision.setEditable(false);        
        tipo_doc.setEditable(false);
        guia_remision_busqueda.setEditable(false);
        subtotal.setEditable(false);
        igv_total.setEditable(false);
        importe_total.setEditable(false);
    }
    
    public void desbloquear_datos(){
        nombre_cliente.setEditable(true);
        dni_cliente.setEditable(true);
        ruc_cliente.setEditable(true);
        ver_moneda.setEditable(true);
        fecha_emision.setEditable(true);        
        tipo_doc.setEditable(true);
        guia_remision_busqueda.setEditable(true);
        subtotal.setEditable(true);
        igv_total.setEditable(true);
        importe_total.setEditable(true);        
    }
    
    @FXML
    void visualizar_doc_venta(ActionEvent event) {
        productos.clear();
        habilitar_formulario();
        try {
            documento_seleccionado = tabla_ventas.getSelectionModel().getSelectedItem();
            if (documento_seleccionado == null) 
            {
                infoController.show("No ha seleccionado ningun Documento de Venta");
                return;
            }

          setPedidoVisible(documento_seleccionado);
          bloquear_datos();
        } catch (Exception e) {
            infoController.show("Error al mostrar el pedido: " + e.getMessage());
        }        

    }      
    
    public void limpiar_formulario(){
        nombre_cliente.clear();
        dni_cliente.clear();
        ruc_cliente.clear();
        dni_cliente.setDisable(false);
        ruc_cliente.setDisable(false);
        fecha_emision.getEditor().clear();        
        guia_remision_busqueda.clear();
        subtotal.clear();
        igv_total.clear();
        importe_total.clear();
        productos.clear();
    }
    
    @Override
    public void desactivar(){
        try{
            documento_seleccionado.set("estado", DocVenta.ESTADO.CANCELADA.name());
            GuiaRemision guia_temp = GuiaRemision.findById(documento_seleccionado.getInteger("guia_id"));
            guia_temp.set("estado", GuiaRemision.ESTADO.ENPROCESO.name());
            guia_temp.saveIt();            
        }catch(Exception e){
            infoController.show("Ha ocurrido un error durante la cancelacion del documento de venta : " + e.getMessage());
        }

    }
    @Override
    public void cambiarEstado(){
        if(documento_seleccionado == null)
        {
            infoController.show("No ha seleccionado ninguna Documento de Venta");
            return;
        }
        
        if(documento_seleccionado.getString("estado").equals(GuiaRemision.ESTADO.COMPLETA.name()))
        {
            infoController.show("El documento de venta ya ha sido completada");
            return;
        }                        
        try{
            Base.openTransaction();
            documento_seleccionado.set("estado", GuiaRemision.ESTADO.COMPLETA.name());
            infoController.show("Se ha completado correctamente");
            Base.connection();
        }catch(Exception e){
            infoController.show("Ha ocurrido un error");
        }
    }        
    
    @Override
    public void nuevo(){
        crear_nuevo = true;
        habilitar_formulario();
        limpiar_formulario();
        desbloquear_datos();
    }
    
    @Override
    public void guardar(){
        if (crear_nuevo){
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.DocumentosdeVenta, Accion.ACCION.CRE)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                crear_nuevo = false;
                return;
            }
            crear_doc_venta();
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.CRE, Menu.MENU.DocumentosdeVenta ,this.usuarioActual);
        }
        deshabilitar_formulario();
        limpiar_formulario();        
        llenar_tabla();
        crear_nuevo = true;
    }
    
    public void llenar_tabla_productos(){
        productos.removeAll(productos);
        productos.addAll(productos_temp);
        columna_codigo.setCellValueFactory((TableColumn.CellDataFeatures<DocVentaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getCodigo()));
        columna_descripcion.setCellValueFactory((TableColumn.CellDataFeatures<DocVentaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getDescripcion()));
        columna_cantidad.setCellValueFactory((TableColumn.CellDataFeatures<DocVentaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getCantidad()));
        columna_pu.setCellValueFactory((TableColumn.CellDataFeatures<DocVentaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getPrecioUnitario()));        
        columna_descuento.setCellValueFactory((TableColumn.CellDataFeatures<DocVentaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getDescuento()));        
        columna_flete.setCellValueFactory((TableColumn.CellDataFeatures<DocVentaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getFlete()));
        columna_subtotal.setCellValueFactory((TableColumn.CellDataFeatures<DocVentaxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getSubtotal()));        
        tabla_detalle.setItems(productos);
    }
    
    public void calcular_totales(){
        Double sub_total = 0.0;
        sub_total = productos_temp.stream().mapToDouble(p -> p.getSubTotalDouble()).sum();
        subtotal.setText(sub_total.toString());
        Double igv = sub_total * ParametroSistema.findFirst("nombre = ?","IGV").getDouble("valor");
        igv_total.setText(igv.toString());
        Double total = 0.0;
        total = sub_total + igv;
        importe_total.setText(total.toString());
    }
    
    public void set_agregar_guia_remision(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarGuiasRemision.fxml"));
            AgregarGuiasRemisionController controller = new AgregarGuiasRemisionController(guias_remision);
            loader.setController(controller);
            Scene modal_content_scene = new Scene((Parent)loader.load());
            modal_stage.setScene(modal_content_scene);
            if(modal_stage.getModality() != Modality.APPLICATION_MODAL) modal_stage.initModality(Modality.APPLICATION_MODAL);    
            controller.devolver_guia_remision.addHandler((Object sender, agregarGuiaRemisionArgs args) -> {
                guia_devuelta = args.guia_remision;
                guia_remision_busqueda.setText(guia_devuelta.getString("guia_cod"));
                
                Envio envio = Envio.findById(guia_devuelta.getInteger("envio_id"));
                OrdenCompra ordenCompra = OrdenCompra.findById(envio.getInteger("orden_compra_id"));
                
                productos_temp = getProductosGuia(guia_devuelta);
                llenarInfoDocVenta(ordenCompra);
                
                llenar_tabla_productos();
                calcular_totales();
            });
            modal_stage.showAndWait();
        }catch (Exception e){
            System.out.println(e);
        }
    }
    
    private void llenarInfoDocVenta(OrdenCompra ordenCompra)
    {              
        ver_moneda.setText(Moneda.findById(ordenCompra.getInteger("moneda_id")).getString("nombre"));
        
        String tipo = ordenCompra.getString("tipo");
        tipo_doc.setText(tipo);        
                
        String dni = cliente_seleccionado.getString("dni");
        String ruc = cliente_seleccionado.getString("ruc");
   
        if(tipo.equals(DocVenta.TIPO.BOLETA.name()))
        {
            dni_cliente.setText(dni);
            ruc_cliente.setDisable(true);
        }else
        {
            ruc_cliente.setText(ruc);
            dni_cliente.setDisable(true);
        }          
    }
    
    public List<DocVentaxProducto> getProductosGuia(GuiaRemision guia)
    {
        List<DocVentaxProducto> docVentaxProductos = new ArrayList<>();
        
        Envio envio = Envio.findById(guia.getInteger("envio_id"));
        List<OrdenesCompraxProductosxenvio> productosGuia = OrdenesCompraxProductosxenvio.where("envio_id = ?", envio.getInteger("envio_id"));
        
        for(OrdenesCompraxProductosxenvio producto:productosGuia)
        {
            OrdenCompraxProducto ordenCompraxProducto = OrdenCompraxProducto.findFirst("orden_compra_id = ? AND tipo_id = ?", 
                    producto.getInteger("orden_compra_id"),producto.getInteger("tipo_id"));
            TipoProducto tipo = TipoProducto.findById(ordenCompraxProducto.getInteger("tipo_id"));
            DocVentaxProducto docVentaxProducto = new DocVentaxProducto(producto.getInteger("cantidad"),tipo.getString("tipo_cod"),tipo.getString("descripcion"),
            ordenCompraxProducto.getDouble("precio_unitario"),ordenCompraxProducto.getDouble("descuento"),ordenCompraxProducto.getDouble("flete"),ordenCompraxProducto.getInteger("cantidad"));            
            docVentaxProductos.add(docVentaxProducto);
        }
        return docVentaxProductos;
    }
    
    @FXML
    public void agregar_guia_remision(ActionEvent event) throws IOException{
        try{
            if(cliente_seleccionado == null)
            {
                infoController.show("No ha seleccionado un cliente");
                return;
            }
            guias_remision = GuiaRemision.where("client_id = ? and estado = ?", cliente_seleccionado.getId(), GuiaRemision.ESTADO.ENPROCESO.name());
            if(cliente_seleccionado == null) {
                infoController.show("Necesita seleccionar un cliente");
                return;
            }
            set_agregar_guia_remision();
        }catch(Exception e){
            System.out.println(e);
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
        nombre_cliente.setText(cliente.getString("nombre"));
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
       
    public void llenar_tabla(){
        ObservableList<DocVenta> doc_ventas = FXCollections.observableArrayList();  
        List<DocVenta> ventas = DocVenta.findAll();
        doc_ventas.addAll(ventas);
        ventas_codigo.setCellValueFactory((TableColumn.CellDataFeatures<DocVenta, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("doc_venta_cod")));
        ventas_cliente.setCellValueFactory((TableColumn.CellDataFeatures<DocVenta, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).get("nombre")));
        ventas_fecha.setCellValueFactory((TableColumn.CellDataFeatures<DocVenta, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("fecha_emision")));
        tabla_ventas.setItems(doc_ventas);        
    }
    
    public void habilitar_formulario(){
        formulario.setDisable(false);
    }
    
    public void deshabilitar_formulario(){
        formulario.setDisable(true);
    }
    
    public DocumentosDeVentaController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        infoController = new InformationAlertController();
        crear_nuevo = false;
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            llenar_autocompletado();                     
            llenar_tabla();
            deshabilitar_formulario();
        }catch(Exception e){
            infoController.show("Error al inicializar los Documentos de Venta");            
        }
    }
    
    @Override
    public Menu.MENU getMenu(){
        return Menu.MENU.DocumentosdeVenta;
    }
}
