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
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Moneda;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarClienteArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarOrdenEntradaProductoArgs;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

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
    
    @FXML
    private Button boton_agregar_cliente;     
        
    
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
    Stage modal_stage_prod = new Stage();
    private List<OrdenEntradaxProducto> productos_a_agregar;
    private List<OrdenEntradaxProducto> productos_disponibles;
    private OrdenEntradaxProducto producto_devuelto;
    private OrdenEntrada orden_entrada_seleccionada;
    private Boolean orden_entrada_nueva;
    private NotaDeCredito nota_seleccionada;
    private Double IGV;
    
    private ObservableList<OrdenEntradaxProducto> productos = FXCollections.observableArrayList(); 

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
            
            ordenes_entrada_combobox.valueProperty().addListener(new ChangeListener<String>() {           
               @Override
               public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                   if(newValue == null) return;
                   if(newValue.isEmpty()) return;
                    orden_entrada_seleccionada = OrdenEntrada.findFirst("orden_entrada_cod = ?", newValue);
                    obtener_productos_disponibles_orden_entrada();                      
                   setProductosEntrada(orden_entrada_seleccionada);    
                   llenar_tabla_productos_a_enviar();
                   calcularFinal();
            }});
        } catch (Exception ex){
            infoController.show("No se pudo cargar la ventana nota de credito : " + ex.getMessage());
        }
    }    
    
    public void setProductosEntrada(OrdenEntrada orden)
    {     
        limpiar_tabla_productos();
        List<OrdenEntradaxProducto> productosOrden = OrdenEntradaxProducto.where("orden_entrada_id = ?", orden.get("orden_entrada_id"));
        for(OrdenEntradaxProducto producto:productosOrden)
        {
            producto_devuelto = producto;
            agregar_producto(null);          
        }        
    }
    
    @Override
    public void nuevo(){
       crearNuevo = true;
       habilitar_formulario();
       limpiar_formulario();
       orden_entrada_nueva = true;
    }
    
    @Override
    public void guardar(){
        if (crearNuevo){
            crear_nota_credito();           
            limpiar_formulario();    
            inhabilitar_formulario();
        } else {
            if ( nota_seleccionada == null){ 
                infoController.show("No ha seleccionado ningun envio");
                return;
            }
            editar_entrada();
        }
        crearNuevo = false;
        RefrescarTabla(NotaDeCredito.findAll());
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
        columna_cliente.setCellValueFactory((TableColumn.CellDataFeatures<NotaDeCredito, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("credit_note_cod")));
        columna_envio.setCellValueFactory((TableColumn.CellDataFeatures<NotaDeCredito, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        columna_pedido.setCellValueFactory((TableColumn.CellDataFeatures<NotaDeCredito, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("orden_entrada_cod")));
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
        boton_agregar_cliente.setDisable(false);               
        ordenes_entrada_combobox.setDisable(false);
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
            ordenes_entrada.addAll(OrdenEntrada.where("client_id = ? and tipo = ? and estado = ?", cliente_seleccionado.getId(), OrdenEntrada.TIPO.Devolucion.name(),OrdenEntrada.ESTADO.Parcial.name()).stream().map(x -> x.getString("orden_entrada_cod")).collect(Collectors.toList()));
            if (ordenes_entrada.isEmpty()){
                infoController.show("El cliente no cuenta con pedidos devueltos :\n Asegurese de que se haya creado una Orden de entrada para los articulos devueltos. ");
                limpiar_formulario();
            }
            else{
                ordenes_entrada_combobox.setItems(ordenes_entrada);            
            }
        }catch(Exception e){
            infoController.show("Ha ocurrido un problema durante la seleccion de orden de entrada : " + e.getMessage());
        }
    }
        
    public void limpiar_formulario(){
        nombre_cliente.clear();
        dni_cliente.clear();
        ruc_cliente.clear();
        TablaProductos.getItems().clear();
        ordenes_entrada_combobox.setValue("");
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
    public void buscar_nota_credito(ActionEvent event) throws IOException{
        List<NotaDeCredito> notas = NotaDeCredito.findAll();
        masterData.clear();
        try{
            for(NotaDeCredito nc : notas){
                if (cumple_condicion_busqueda(nc, cod_nota_buscar.getText(), cliente_buscar.getText(), orden_entrada_buscar.getText())){
                    masterData.add(nc);
                }
            }
        }catch(Exception e){
            infoController.show("No se pudo buscar el envio : " + e.getMessage());
        }
    }

    @FXML
    private void visualizar_nota_credito(ActionEvent event) throws  IOException{
        crearNuevo = false;
        nota_seleccionada = tabla_notas_cred.getSelectionModel().getSelectedItem();
        if (nota_seleccionada == null){
            infoController.show("Nota de credito no seleccionada");  
            return;            
        }
        try{
            habilitar_formulario();
            boton_agregar_cliente.setDisable(true);                          
            dni_cliente.setEditable(false);
            nombre_cliente.setEditable(false);
            ruc_cliente.setEditable(false);
            ordenes_entrada_combobox.setDisable(true);
            limpiar_formulario();
            setear_datos_nota();
            setear_productos_nota();
            calcularFinal();
            
        }catch (Exception e){
            infoController.show("Error al cargar Envio: " + e.getMessage());
        }
    }

//    @FXML
//    private void handleModalProducto(ActionEvent event) throws IOException{
//        try{
//            String temp_orden_entrada = ordenes_entrada_combobox.getSelectionModel().getSelectedItem();
//            if ( orden_entrada_seleccionada != null){
//                String orden_entrada_actual = orden_entrada_seleccionada.getString("orden_compra_cod");
//                orden_entrada_nueva = !temp_orden_entrada.equals(orden_entrada_actual);
//            }
//            if (orden_entrada_nueva){
//                orden_entrada_seleccionada = OrdenEntrada.findFirst("orden_entrada_cod = ?", temp_orden_entrada);
//                obtener_productos_disponibles_orden_entrada();
//                limpiar_tabla_productos();
//                orden_entrada_nueva = false;
//            }
////            abrirModalProductos();
//            if(producto_devuelto==null) return; 
//            VerProducto.setText(TipoProducto.findFirst("tipo_cod = ? AND tipo_id = ?", producto_devuelto.get("tipo_cod"), producto_devuelto.get("tipo_id")).getString("nombre"));
//        }catch(Exception e){
//            infoController.show("Necesita seleccionar una orden de compra");
//        }
//
//    }
    
    public void limpiar_tabla_productos(){
        TablaProductos.getItems().clear();
    }
    
//    private void abrirModalProductos() throws Exception
//    {
//        try{
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarProductosEnvios.fxml"));
//            AgregarProductosEntradaController controller = new AgregarProductosEntradaController(productos_disponibles);
//            loader.setController(controller);
//            Scene modal_content_scene = new Scene((Parent)loader.load());
//            modal_stage_prod.setScene(modal_content_scene);       
//            if(modal_stage_prod.getModality() != Modality.APPLICATION_MODAL) modal_stage_prod.initModality(Modality.APPLICATION_MODAL); 
//            controller.devolverProductoEvent.addHandler((Object sender, agregarOrdenEntradaProductoArgs args) -> {
//                producto_devuelto = args.orden_entrada_producto;
//            });                 
//            modal_stage_prod.showAndWait();
//            if(producto_devuelto == null) return;
//            SpinnerValueFactory cantidad_productoValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(producto_devuelto.getInteger("cantidad"), producto_devuelto.getInteger("cantidad"), 0);
//            cantidad_producto.setValueFactory(cantidad_productoValues);
//        }catch(Exception e){
//            infoController.show("No se pudo agregar los productos : " + e.getMessage());
//        }
//    }
    
  
    
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
                if (Objects.equals(producto_disponible.getInteger("tipo_id"), producto_devuelto.getInteger("tipo_id"))){
                    actualizar_lista_producto_a_agregar(producto_disponible);
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
            cantProdColumn.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntradaxProducto, String> p) -> new ReadOnlyObjectWrapper((p.getValue().get("cantidad"))));
            precioUnitarioColumn.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntradaxProducto, String> p) -> {
                return new ReadOnlyObjectWrapper(
                        String.valueOf(((TipoProducto)(TipoProducto.findById(p.getValue().get("tipo_id")))).getPrecioActualNoEx(Moneda.findById(1))));
            });
            subTotalProdColumna.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntradaxProducto, String> p) -> {
                return new ReadOnlyObjectWrapper(
                        String.valueOf(((TipoProducto)(TipoProducto.findById(p.getValue().get("tipo_id")))).getPrecioActualNoEx(Moneda.findById(1))*p.getValue().getDouble("cantidad")));
            });
            TablaProductos.setItems(productos);
        }catch(Exception e){
            System.out.println(e);
        }

    }
//    
//    private void RecalcularTabla(Boolean isNew) throws Exception
//    {
//        for(OrdenEntradaxProducto productoxdevolucion : productos_a_agregar)
//        {
//            Integer extraCant = 0;
//            if(!isNew && productoxdevolucion.getInteger("tipo_id").equals(producto_devuelto.getInteger("tipo_id")))
//            {
//                extraCant += cantidad_producto.getValue();
//            }
//            productoxdevolucion.set("cantidad", productoxdevolucion.getInteger("cantidad") + extraCant);                                                                                                     
//            break;
//        }
//        //calcularFinal();
//        
//    }
//    
    private void calcularFinal()
    {
        Double total = 0.0;
        for(OrdenEntradaxProducto entradaxproducto:productos_a_agregar)
        {
            total += ((TipoProducto)(TipoProducto.findById(entradaxproducto.get("tipo_id")))).getPrecioActualNoEx(Moneda.findById(1))*entradaxproducto.getDouble("cantidad");
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
                productoxentrada.set("cantidad", producto_disponible.getInteger("cantidad"));       
                //productoxentrada.set("precio_unitario",producto_disponible.get("precio_unitario"));                  
                //productoxentrada.set("subtotal_final",producto_disponible.get("subtotal_final"));             
                productos_a_agregar.add(productoxentrada);
                isNew = true;
            }else{
//                RecalcularTabla(isNew);
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

    private void crear_nota_credito(){
        try{
            Base.openTransaction();  
            NotaDeCredito nueva_nota = new NotaDeCredito();
            String credit_note_cod = "NC" + orden_entrada_seleccionada.getString("orden_entrada_cod");
            if(!confirmatonController.show("Se creará el envio con código: " + credit_note_cod, "¿Desea continuar?")) return;
            
            nueva_nota.set("credit_note_cod", credit_note_cod);
            nueva_nota.set("orden_entrada_cod", orden_entrada_seleccionada.get("orden_entrada_cod"));
            nueva_nota.set("orden_entrada_id", orden_entrada_seleccionada.getInteger("orden_entrada_id"));
            nueva_nota.set("client_id", cliente_seleccionado.getInteger("client_id"));
            nueva_nota.set("last_user_change", usuarioActual.getString("usuario_cod"));
            nueva_nota.set("explicacion", orden_entrada_seleccionada.get("descripcion"));
            nueva_nota.set("fecha_emision", orden_entrada_seleccionada.get("fecha_emision"));
            nueva_nota.saveIt();       
            orden_entrada_seleccionada.set("estado",OrdenEntrada.ESTADO.Completa.name());
            orden_entrada_seleccionada.saveIt();
            Base.commitTransaction();
           
            infoController.show("La Nota de Credito se creo satisfactoriamente");
        } catch (Exception e){
            infoController.show("No se pudo crear la Nota de Credito : " + e.getMessage()); 
        }
    }

    private void editar_entrada() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

     private void RefrescarTabla(List<NotaDeCredito> tempNC)
    {
        try {
            if(tempNC == null) return;
            masterData.removeAll(masterData);                        
            for (NotaDeCredito nc : tempNC) {
                masterData.add(nc);
            }               
            tabla_notas_cred.getColumns().get(0).setVisible(false);
            tabla_notas_cred.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("Error al refrescar Tabla: " + e.getMessage());
        }                                  
    }

    private void setear_datos_nota() {
        Cliente cliente_temp = Cliente.findById(nota_seleccionada.getInteger("client_id"));
        String tipo_cliente = cliente_temp.getString("tipo_cliente");
        String dni = cliente_temp.getString("dni");
        String ruc = cliente_temp.getString("ruc");
   
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
        ordenes_entrada_combobox.setValue(nota_seleccionada.getString("orden_entrada_cod"));
        orden_entrada_seleccionada = OrdenEntrada.findById(nota_seleccionada.get("orden_entrada_id"));    
        //llenarHashDisponibles(orden_entrada_seleccionada);
        cliente_seleccionado = cliente_temp;
    }

    private void setear_productos_nota() {
        try{          
            productos.clear();
            List<OrdenEntradaxProducto> productos_agregados = OrdenEntradaxProducto.where("orden_entrada_id = ?",orden_entrada_seleccionada.getId());
            productos.addAll(productos_agregados); 
            productos_a_agregar = productos;
            llenar_tabla_productos_a_enviar();
        }catch(Exception e){
            System.out.println(e);
        }  
    }

    public boolean cumple_condicion_busqueda(NotaDeCredito nota, String codigo, String cliente, String ordEntrada){
        boolean match = true;        
        if ( codigo.equals("") && cliente.equals("") && ordEntrada.equals("")){
            match = true;
        }else {
            match = (!codigo.equals("")) ? (match && (nota.get("credit_note_cod")).equals(codigo)) : match;
            Integer idCliente = (cliente_seleccionado!=null) ? (Integer)cliente_seleccionado.getId():0;
            match = (!cliente.equals("")) ? (match && (nota.get("cliente_id")==idCliente)) : match;
            match = (!ordEntrada.equals("")) ? (match && (nota.get("orden_entrada_cod")).equals(ordEntrada)) : match;
        }
        return match;
    }
    
}
