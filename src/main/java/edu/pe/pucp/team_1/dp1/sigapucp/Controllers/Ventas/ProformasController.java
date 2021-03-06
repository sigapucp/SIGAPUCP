/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.AgregarProductosController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cotizacion;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaxTipo;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import static edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu.MENU.Promociones;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad.AccionLoggerSingleton;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Sistema.ParametroSistema;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.CambioMoneda;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.CotizacionxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Flete;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Moneda;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Promocion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.PromocionBonificacion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.PromocionCantidad;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.PromocionPorcentaje;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.abrirDetallesArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.cambiarMenuArgs;
import java.net.URL;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
public class ProformasController extends Controller {

    @FXML
    private TextField clienteBusq;
    @FXML
    private ComboBox<String> estadoBusq;
    @FXML
    private TextField pedidoBusq;
    @FXML
    private TableView<Cotizacion> tablaPedidos;
    @FXML
    private TableColumn<Cotizacion, String> proformaColumn;
    @FXML
    private TableColumn<Cotizacion, String> clienteColumn;    
    @FXML
    public DatePicker fechaProfSh;
    @FXML
    public TextField clienteSh;    ;
    @FXML
    private TextField telfSh;
    @FXML
    private TextField direccionSh;       
    @FXML
    private Spinner<Integer> cantProd;
    @FXML 
    private TextField producto;
    @FXML
    private Button agregarProdProf;
    @FXML
    private AnchorPane proforma_tabla;
    @FXML
    private AnchorPane proforma_formulario;   
    
    private SpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 1);
    @FXML
    private TableColumn<CotizacionxProducto, String> cantProdColumn;    
    @FXML
    private Label proformaSub;      
    @FXML
    private ComboBox<String> VerMoneda;
    @FXML
    private TableView<CotizacionxProducto> TablaProductos;
    @FXML
    private TableColumn<CotizacionxProducto, String> nombreProdColumn;
    @FXML
    private TableColumn<CotizacionxProducto, String> descProdColumna;
    @FXML
    private TableColumn<CotizacionxProducto, String> fleteProdColumn;
    @FXML
    private TableColumn<CotizacionxProducto, String> subTotalProdColumna;
    @FXML
    private TableColumn<CotizacionxProducto, String> codProdColumn;
    @FXML
    private TableColumn<CotizacionxProducto, String> precioUnitarioColumn;
    @FXML
    private TableColumn<Cotizacion, String> ColumnaEstado;    
    @FXML
    private TextField subTotalFinal;
    @FXML
    private TextField igvTotal;
    @FXML
    private TextField total;
    @FXML
    private Label dniLabel;
    @FXML
    private TextField VerDocCliente;    
    
    Stage modal_stage = new Stage();
    
    public IEvent<abrirDetallesArgs> abrirDetalle;
    
    private final ObservableList<Cotizacion> cotizaciones = FXCollections.observableArrayList(); 
    private final ObservableList<CotizacionxProducto> productos = FXCollections.observableArrayList(); 
    
    private Cotizacion proformaSelecionado;
    private CotizacionxProducto productoSeleccionado; 
    private Cliente clienteSeleccionado; 
    private TipoProducto productoDevuelto;
    
    private Boolean crearNuevo = false;        
    private InformationAlertController infoController;    
    private List<Cliente> autoCompletadoList;    
    ArrayList<String> possiblewords = new ArrayList<>();    
    AutoCompletionBinding<String> autoCompletionBinding;
    ArrayList<String> possiblewordsProducto = new ArrayList<>();   
    private List<TipoProducto> autoCompletadoProductoList;
    AutoCompletionBinding<String> autoCompletionBindingProducto;
    private Double IGV;    
    private Moneda monedaSeleccionada = null;
  
    /**
     * Initializes the controller class.
     */
                
    public ProformasController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        List<Cotizacion> tempCotizaciones = Cotizacion.findAll();       
        IGV = ParametroSistema.findFirst("nombre = ?", "IGV").getDouble("valor");
        
        for (Cotizacion coti : tempCotizaciones) {
            cotizaciones.add(coti);
        }                               
        
        infoController = new InformationAlertController();
                
        proformaSelecionado = null;
        crearNuevo = false;
    }
    
    private void llenar_tabla_index() throws Exception{
        List<Cotizacion> tempCotizaciones = Cotizacion.findAll();
        cotizaciones.clear();
        producto.clear();
        
        proformaColumn.setCellValueFactory((CellDataFeatures<Cotizacion, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cotizacion_cod")));   
        clienteColumn.setCellValueFactory((CellDataFeatures<Cotizacion, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        ColumnaEstado.setCellValueFactory((CellDataFeatures<Cotizacion, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("estado")));
        
        DecimalFormat df = new DecimalFormat("#.00");
        DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
        sym.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(sym);
        
        codProdColumn.setCellValueFactory((CellDataFeatures<CotizacionxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        nombreProdColumn.setCellValueFactory((CellDataFeatures<CotizacionxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
        cantProdColumn.setCellValueFactory((CellDataFeatures<CotizacionxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cantidad")));
        precioUnitarioColumn.setCellValueFactory((CellDataFeatures<CotizacionxProducto, String> p) -> new ReadOnlyObjectWrapper(df.format(p.getValue().get("precio_unitario"))));
        descProdColumna.setCellValueFactory((CellDataFeatures<CotizacionxProducto, String> p) -> new ReadOnlyObjectWrapper(df.format(p.getValue().get("descuento"))));
        fleteProdColumn.setCellValueFactory((CellDataFeatures<CotizacionxProducto, String> p) -> new ReadOnlyObjectWrapper(df.format(p.getValue().get("flete"))));
        subTotalProdColumna.setCellValueFactory((CellDataFeatures<CotizacionxProducto, String> p) -> new ReadOnlyObjectWrapper(df.format(p.getValue().get("subtotal_final"))));        
        
        cotizaciones.addAll(tempCotizaciones);
        tablaPedidos.setItems(cotizaciones);
        TablaProductos.setItems(productos);
    }
    
    private void llenar_combobox() throws Exception
    {
        ObservableList<String> monedas = FXCollections.observableArrayList();            
        monedas.addAll(Moneda.findAll().stream().map(x -> x.getString("nombre")).collect(Collectors.toList()));
        VerMoneda.setItems(monedas);

        ObservableList<String> estados = FXCollections.observableArrayList();       
        estados.add("");
        estados.addAll(Arrays.asList(Cotizacion.ESTADO.values()).stream().map(x->x.name()).collect(Collectors.toList()));   
        estadoBusq.setItems(estados);   
        cantProd.setValueFactory(valueFactory);  
    }
    
    private void llenar_autocompletado() throws Exception
    {
        autoCompletadoList = Cliente.findAll();
        autoCompletadoProductoList = TipoProducto.findAll();

        clienteToString();
        autoCompletionBinding = TextFields.bindAutoCompletion(clienteSh, possiblewords);
        autoCompletionBinding.addEventHandler(EventType.ROOT, (event) -> {
            handleAutoCompletar();
        });

        productoToString();
        autoCompletionBindingProducto = TextFields.bindAutoCompletion(producto, possiblewordsProducto);

        autoCompletionBindingProducto.addEventHandler(EventType.ROOT, (event) -> {
        handleAutoCompletarProducto();
        });        
    }
    
    
    private void setAgregarProductos() throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarProductos.fxml"));
        AgregarProductosController controller = new AgregarProductosController();
        loader.setController(controller);                      
        Scene modal_content_scene = new Scene((Parent)loader.load());
        modal_stage.setScene(modal_content_scene);
        if(modal_stage.getModality() != Modality.APPLICATION_MODAL) modal_stage.initModality(Modality.APPLICATION_MODAL);    

        controller.devolverProductoEvent.addHandler((Object sender, agregarProductoArgs args) -> {
            productoDevuelto = args.producto;
        });
             
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //llenar tabla:

        try {            
            llenar_tabla_index();               
            llenar_combobox();
            llenar_autocompletado();
            inhabilitar_formulario();
            setAgregarProductos();   
            
            VerMoneda.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if(newValue == null) return;
                monedaSeleccionada = Moneda.findFirst("nombre = ?", newValue);
                cambiarMoneda();
            });
            
        } catch (Exception e) {
            infoController.show("Error al cargar las proformas " + e.getMessage());
        }                                      
    }      
   
    @Override
    public void nuevo(){
       crearNuevo = true;
       habilitar_formulario();
       limpiar_formulario();
    }
    
    @Override 
    public void guardar() {        
        if(crearNuevo)
        {
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Proformas, Accion.ACCION.CRE)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                crearNuevo = false;
                return;
            }
            crearProforma();
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.CRE, Menu.MENU.Proformas ,this.usuarioActual);
            inhabilitar_formulario();
            limpiar_formulario();
        }else
        {
            if(proformaSelecionado == null){
                infoController.show("No ha seleccionado un cliente");
                return;
            }
            if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Proformas, Accion.ACCION.MOD)){
                infoController.show("No tiene los permisos suficientes para realizar esta acción");
                return;
            }
            
            String estado = proformaSelecionado.getString("estado");            
            if(estado.equals(Cotizacion.ESTADO.CONPEDIDO.name()))
            {
                infoController.show("La proforma no puede ser modifcada ya que esta anexada a un Pedido");
                return;
            }
            
            editarProforma(proformaSelecionado);
            AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.MOD, Menu.MENU.Proformas ,this.usuarioActual);
        }        
        RefrescarTabla(Cotizacion.findAll());
    }
    
    private void editarProforma(Cotizacion proforma) {        
        try {
            Base.openTransaction();
            
            if(clienteSeleccionado == null)
            {
                infoController.show("No hay un cliente seleccionado");
                return;
            }

            if(total.getText().isEmpty())
            {
                infoController.show("Debe agregar algun producto a la proforma");
                return;
            }
            
            LocalDate fechaLocal = fechaProfSh.getValue();
            Date fecha = Date.valueOf(fechaLocal);                
            Double igvValue = Double.valueOf(igvTotal.getText());
            Double totalValue = Double.valueOf(subTotalFinal.getText());
            String monedaNombre = VerMoneda.getSelectionModel().getSelectedItem();
            Moneda moneda = Moneda.findFirst("nombre = ?", monedaNombre);
            
            asignar_data(proforma,clienteSeleccionado.getInteger("client_id"), fecha, igvValue,totalValue,Cotizacion.ESTADO.SINPEDIDO.name(),
                moneda.getInteger("moneda_id")); 
            
            proforma.saveIt();
            setProductos(proforma);
            infoController.show("Se ha editado satisfactoriamente la proforma");        
            Base.commitTransaction();            
        } catch (Exception e) {
            Base.rollbackTransaction();
            infoController.show("Error al editar proforma: " + e.getMessage());            
        }
    }
    
    private void crearProforma() {       
        try{        
            
        if(clienteSeleccionado == null)
        {
            infoController.show("No hay un cliente seleccionado");
            return;
        }
        
        if(total.getText().isEmpty())
        {
            infoController.show("Debe agregar algun producto a la proforma");
            return;
        }
        
        Base.openTransaction();
        LocalDate fechaLocal = fechaProfSh.getValue();
        Date fecha = Date.valueOf(fechaLocal);                    
        Double igvValue = Double.valueOf(igvTotal.getText());
        Double totalValue = Double.valueOf(subTotalFinal.getText());
        String monedaNombre = VerMoneda.getSelectionModel().getSelectedItem();
        Moneda moneda = Moneda.findFirst("nombre = ?", monedaNombre);
        
        Cotizacion proforma = new Cotizacion();
        asignar_data(proforma,clienteSeleccionado.getInteger("client_id"), fecha, igvValue,totalValue,Cotizacion.ESTADO.SINPEDIDO.name(),
                moneda.getInteger("moneda_id"));  
        String cod = "PROF" + String.valueOf(Integer.valueOf(String.valueOf((Base.firstCell("select last_value from cotizaciones_cotizacion_id_seq")))) + 1);
        proforma.set("cotizacion_cod",cod);
        proforma.saveIt();
        
        setProductos(proforma);
        Base.commitTransaction();       
        infoController.show("Se ha creado la proforma: PROF" + String.valueOf(cod));        
        }
        catch(Exception e){
           infoController.show("No se pudo crear la Proforma: " + e.getMessage());
           Base.rollbackTransaction();
        }    
    }
    
    private void asignar_data(Cotizacion proforma,int cliente_id, Date fechaEmision,Double igv ,Double total, String estado, int monedaId) throws Exception{      
        proforma.set("client_id",cliente_id);        
        proforma.setDate("fecha_emision", fechaEmision);
        proforma.set("igv",igv);
        proforma.set("total",total);        
        proforma.set("estado", estado);
        proforma.set("last_user_change",usuarioActual.getString("usuario_cod"));                        
        proforma.set("moneda_id", monedaId);                      
    }
    
    private void setProductos(Cotizacion cotizacion)
    {
        List<CotizacionxProducto> cotizacionesGuardadas = CotizacionxProducto.where("cotizacion_id = ?", cotizacion.getId());
        
        for(CotizacionxProducto cotizacionxproducto:productos)
        {
            if(cotizacionxproducto.isNew())
            {
                cotizacionxproducto.set("cotizacion_id",cotizacion.getId());
                cotizacionxproducto.set("client_id",cotizacion.get("client_id"));
                cotizacionxproducto.set("cotizacion_cod",cotizacion.get("cotizacion_cod"));
            }
            cotizacionxproducto.saveIt();
        }             
               
        if(cotizacionesGuardadas != null)
        {
            List<CotizacionxProducto> cotizacionesProductosDelete = cotizacionesGuardadas.stream().filter(x -> productos.stream().noneMatch(y -> !y.isNew() && 
                y.getInteger("cotizacion_id").equals(x.getInteger("cotizacion_id")) && 
                y.getInteger("tipo_id").equals(x.getInteger("tipo_id")))).collect(Collectors.toList());
        
            if(cotizacionesProductosDelete == null) return;

            for(CotizacionxProducto cotizacionxProducto:cotizacionesProductosDelete)
            {
                CotizacionxProducto.delete("cotizacion_id = ? AND tipo_id = ?",cotizacionxProducto.get("cotizacion_id"),cotizacionxProducto.get("tipo_id"));
            }                           
        }
                
        for(CotizacionxProducto cotizacionxproducto:productos)
        {
            cotizacionxproducto.saveIt();
        }             
    }
    
    @FXML
    private void buscarPedido(ActionEvent event) {        
        String proformaId = pedidoBusq.getText();
        String cliente = clienteBusq.getText();      
        String estado = ( estadoBusq.getSelectionModel().getSelectedItem() == null ) ? "" : estadoBusq.getSelectionModel().getSelectedItem();
        Integer clienteId = Cliente.first("nombre = ?", cliente).getInteger("client_id");
        List<Cotizacion> tempCotizaciones = Cotizacion.findAll();
        
        if(proformaId!=null&&!proformaId.isEmpty()) {            
            tempCotizaciones = tempCotizaciones.stream().filter(p -> p.getString("cotizacion_cod").equals(proformaId)).collect(Collectors.toList());
        }
        if (clienteId!=null) {
            tempCotizaciones = tempCotizaciones.stream().filter(p -> p.getString("client_id").equals(clienteId)).collect(Collectors.toList());
        }
        if(estado!=null&&!estado.isEmpty()) {
            tempCotizaciones = tempCotizaciones.stream().filter(p -> p.get("estado").equals(estado)).collect(Collectors.toList());
        }

        RefrescarTabla(tempCotizaciones);
        try {                        
        } catch (Exception e) { 
            infoController.show("Eror al buscar Proforma " + e.getMessage());
        }
    }      
    
    @FXML
    private void visualizarProforma(ActionEvent event){
        crearNuevo = false;
        habilitar_formulario();
        try {
            proformaSelecionado = tablaPedidos.getSelectionModel().getSelectedItem();
            if (proformaSelecionado == null) 
            {
                infoController.show("No ha seleccionado ninguna proforma");
                return;
            }
          setProformaVisible(proformaSelecionado);                            
        } catch (Exception e) {
            infoController.show("Error al mostrar Cotizacion: " + e.getMessage());
        }        
    }    
    
    private void setProformaVisible(Cotizacion cotizacion) throws Exception
    {
        Cliente cliente = Cliente.findFirst("client_id = ?", proformaSelecionado.get("client_id"));
        setInformacionCliente(cliente);  
        clienteSh.setText(cliente.getString("nombre"));
        LocalDate date = proformaSelecionado.getDate("fecha_emision").toLocalDate();
        fechaProfSh.setValue(date);
        VerMoneda.getSelectionModel().select(Moneda.findById(cotizacion.get("moneda_id")).getString("nombre"));       
        setValorTotal(cotizacion.getDouble("total"));
        
        List<CotizacionxProducto> cotizacionesxProductos = CotizacionxProducto.where("cotizacion_id = ?", proformaSelecionado.getId());        
        productos.clear();
        productos.addAll(cotizacionesxProductos);                   
    }
    
    private void RefrescarTabla(List<Cotizacion> tempCotizaciones)
    {
        try {
            if(tempCotizaciones == null) return;
            cotizaciones.removeAll(cotizaciones);                        
            for (Cotizacion coti : tempCotizaciones) {
                cotizaciones.add(coti);
            }               
            tablaPedidos.getColumns().get(0).setVisible(false);
            tablaPedidos.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("Error al refrescar Tabla: " + e.getMessage());
        }                                  
    }
    
    public void inhabilitar_formulario (){
        proforma_formulario.setDisable(true);
        proforma_tabla.setDisable(true);
    }
    
    public void habilitar_formulario (){
        proforma_formulario.setDisable(false);
        proforma_tabla.setDisable(false);
    }
    
    public void limpiar_formulario(){
        clienteSh.clear();
        telfSh.clear();
        direccionSh.clear();
        fechaProfSh.setValue(null);
        producto.clear();
        productos.clear();
        VerMoneda.getSelectionModel().clearSelection();
        subTotalFinal.clear();
        igvTotal.clear();
        total.clear();                
        SpinnerValueFactory newvalueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 1);
        cantProd.setValueFactory(newvalueFactory);
        proformaSelecionado = null;
    }      
        
    @FXML
    private void eliminarProducto(ActionEvent event) {        
        try {            
            CotizacionxProducto cotizacionxProducto = TablaProductos.getSelectionModel().getSelectedItem();
            if(cotizacionxProducto==null)
            {
                infoController.show("No ha seleccionado ninguna Cotizacion");
                return;
            }         

            if(!cotizacionxProducto.isNew())
            {
                Cotizacion cotizacion = Cotizacion.findById(cotizacionxProducto.get("cotizacion_id"));
                String estado = cotizacion.getString("estado");
                if(estado.equals(Cotizacion.ESTADO.CONPEDIDO.name()))
                {
                    infoController.show("No puede eliminar producto ya que este ya se encuentra en un Pedido");
                    return;                
                }            
            }  
            
            productos.remove(cotizacionxProducto);          
            for(CotizacionxProducto cotizacionxproducto:productos)
            {
                Double descuento = calcularDescuento(cotizacionxproducto);
                Double flete = calcularFlete(cotizacionxproducto);
                
                cotizacionxproducto.set("descuento",descuento);
                cotizacionxproducto.set("flete",flete);
                cotizacionxproducto.set("subtotal_final",cotizacionxproducto.getDouble("subtotal_previo") + descuento - flete);
            }
            calcularFinal(); 
             
            TablaProductos.getColumns().get(0).setVisible(false);
            TablaProductos.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("Error al eliminar el producto");
        }        
    }

    @FXML
    private void agregarProducto(ActionEvent event) {                
        if(productoDevuelto == null)
        {
            infoController.show("No ha seleccionado ningun producto"); 
           return;
        }
        
        if(clienteSeleccionado == null)
        {
            infoController.show("Debe seleccionar un cliente antes para el calculo del flete"); 
           return;            
        }
        
        if(monedaSeleccionada == null)
        {
            infoController.show("Debe seleccionar una moneda utilizada primero");
            return;
        }        
        Boolean isNew = false;             
        try {                        
                if(!productos.stream().anyMatch(x -> x.getInteger("tipo_id").equals(productoDevuelto.getInteger("tipo_id"))))
                {
                    CotizacionxProducto cotizacionxproducto = new CotizacionxProducto();                                           
                    Integer cantidad = cantProd.getValue();
                    Double precio = productoDevuelto.getPrecioActual(monedaSeleccionada);
                    cotizacionxproducto.set("tipo_id",productoDevuelto.getId());
                    cotizacionxproducto.set("tipo_cod",productoDevuelto.get("tipo_cod"));                                
                    cotizacionxproducto.set("cantidad",cantidad);                       
                    cotizacionxproducto.set("cantidad_descuento_disponible",cantidad);                                   
                    cotizacionxproducto.set("precio_unitario",precio);    
                    cotizacionxproducto.set("subtotal_previo",cantidad*precio); 
                    cotizacionxproducto.set("descuento",0);
                    cotizacionxproducto.set("flete",0);                    
                    cotizacionxproducto.set("subtotal_final",cantidad*precio);    
                    productos.add(cotizacionxproducto);
                    isNew = true;                    
                }                          
               RecalcularTabla(isNew);
        } catch (Exception e) {
            infoController.show("No se ha podido agregar ese Producto: " + e.getMessage());
        }                
    }
    
    private void RecalcularTabla(Boolean isNew) throws Exception
    {
        for(CotizacionxProducto productoCot:productos)
        {
            Integer extraCant = 0;
            if(!isNew && productoCot.getInteger("tipo_id").equals(productoDevuelto.getInteger("tipo_id")))
            {
                extraCant += cantProd.getValue();
            }
       
            Double precioUnitario = productoCot.getDouble("precio_unitario");  
            Integer cantidad = productoCot.getInteger("cantidad") + extraCant;
            productoCot.set("cantidad",cantidad);
            productoCot.set("cantidad_descuento_disponible",productoCot.getInteger("cantidad_descuento_disponible") + extraCant);     
            Double descuento = calcularDescuento(productoCot) + productoCot.getDouble("descuento");
            Double flete = calcularFlete(productoCot) + productoCot.getDouble("flete");
            Double subtotal = cantidad*precioUnitario - descuento + flete;

            productoCot.set("descuento",descuento);
            productoCot.set("flete",flete);

            productoCot.set("subtotal_previo", precioUnitario*cantidad);
            productoCot.set("subtotal_final",subtotal);                                                                                               
        }    
        
        TablaProductos.getColumns().get(0).setVisible(false);
        TablaProductos.getColumns().get(0).setVisible(true);    
        calcularFinal();        
    }
    
    private void cambiarMoneda()
    {
        if(monedaSeleccionada == null) return;
        try {
            for(CotizacionxProducto productoCot:productos)
            {                
                TipoProducto producto = TipoProducto.findById(productoCot.get("tipo_id"));               
                
                Double nuevoPrecio = producto.getPrecioActual(monedaSeleccionada);
                Double factor = nuevoPrecio/productoCot.getDouble("precio_unitario");
                Integer cantidad = productoCot.getInteger("cantidad");
                
                productoCot.set("precio_unitario",nuevoPrecio);    
                productoCot.set("subtotal_previo",cantidad*nuevoPrecio); 
                Double nuevoDescuento = productoCot.getDouble("descuento")*factor;
                productoCot.set("descuento",nuevoDescuento);
                Double nuevoFlete = calcularFlete(productoCot);
                productoCot.set("flete",productoCot.getDouble("flete")*factor);                    
                productoCot.set("subtotal_final",cantidad*nuevoPrecio - nuevoDescuento + nuevoFlete);                               
            }         
            
            TablaProductos.getColumns().get(0).setVisible(false);
            TablaProductos.getColumns().get(0).setVisible(true);    
            calcularFinal();        
            
        } catch (Exception e) {
            infoController.show("Error al cambiar de moneda: " + e.getMessage());
        }             
    }       
    
    private void setValorTotal(Double valor)
    {        
         DecimalFormat df = new DecimalFormat("#.00");
        DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
        sym.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(sym);
        
        subTotalFinal.setText(df.format(valor));
        Double valorIgv = IGV*valor;            
        igvTotal.setText(df.format(valorIgv));
        total.setText(df.format((valor+valorIgv)));                
    }
    
    private Double calcularDescuento(CotizacionxProducto producto) throws Exception
    {
        // Magic. Se que es el id 1,2,3. Puesto asi en la Bd
        Double prioridadBonficacion = Double.valueOf(ParametroSistema.findById(1).getInteger("valor"));
        Double prioridadCantidad = Double.valueOf(ParametroSistema.findById(2).getInteger("valor")) - 0.25;
        Double prioridadPorcentaje =  Double.valueOf(ParametroSistema.findById(3).getInteger("valor")) - 0.5;
        
        TipoProducto productoReferenciado = TipoProducto.findById(producto.get("tipo_id"));
        List<Promocion> promociones = Promocion.where("tipo_id = ? AND estado = ?",producto.get("tipo_id"),Promocion.ESTADO.ACTIVO.name());        
        List<CategoriaProducto> categorias = productoReferenciado.getAll(CategoriaProducto.class).stream().filter(x -> 
                x.getString("estado").equals(CategoriaProducto.ESTADO.ACTIVO.name())).collect(Collectors.toList());
        
        for(CategoriaProducto categoria:categorias)
        {
            promociones.addAll(Promocion.where("categoria_id = ?", categoria.get("categoria_id")));
        }                   
        
        Date today = Date.valueOf(LocalDate.now());        
        List<Promocion> promocionesActual = promociones.stream().filter(x -> today.after(x.getDate("fecha_inicio"))&& today.before(x.getDate("fecha_fin"))).collect(Collectors.toList());
        
        List<Promocion> promocionesCantidad = promocionesActual.stream().filter(x -> x.getString("tipo").equals(Promocion.TIPO.CANTIDAD.name())).collect(Collectors.toList());
        List<Promocion> promocionesProcentaje = promocionesActual.stream().filter(x -> x.getString("tipo").equals(Promocion.TIPO.PORCENTAJE.name())).collect(Collectors.toList());                        
        List<Promocion> promocionesBonificacion = promocionesActual.stream().filter(x -> x.getString("tipo").equals(Promocion.TIPO.BONIFICACIÓN.name())).collect(Collectors.toList());
        
        if(promocionesProcentaje.isEmpty()) prioridadPorcentaje +=3;
        if(promocionesCantidad.isEmpty()) prioridadCantidad +=3;
        if(promocionesBonificacion.isEmpty()) prioridadPorcentaje +=3;
        
        if(!promocionesProcentaje.isEmpty()&&prioridadPorcentaje<prioridadCantidad&&prioridadPorcentaje<prioridadBonficacion)
        {
            return aplicarPromocionPorcentaje(promocionesProcentaje, producto);
        }
        
        if(!promocionesCantidad.isEmpty()&&prioridadCantidad<prioridadPorcentaje&&prioridadCantidad<prioridadBonficacion)
        {
            return aplicarPromocionCantidad(promocionesCantidad, producto);            
        }
        
        if(!promocionesBonificacion.isEmpty()&&prioridadBonficacion<prioridadCantidad&&prioridadBonficacion<prioridadPorcentaje)
        {
            
            return aplicarPromocionBonificacion(promocionesBonificacion, producto);
        }        
        return 0.0;        
    }
    
    private Double aplicarPromocionBonificacion(List<Promocion> promocionesBonificacion,CotizacionxProducto producto) throws Exception
    {                       
        if(promocionesBonificacion.isEmpty()) return 0.0;                       
        promocionesBonificacion.sort((Promocion o1, Promocion o2) -> o1.getInteger("prioridad") - o2.getInteger("prioridad"));
        
        if(promocionesBonificacion.isEmpty()) return 0.0;
        Double valorPromocion = 0.0;
                
        Promocion promocionAplicada = promocionesBonificacion.stream().findFirst().get();        
        PromocionBonificacion promocionBonificacion = PromocionBonificacion.findFirst("promocion_id = ?", promocionAplicada.getId());              
        Boolean es_categoria = promocionBonificacion.getString("es_categoria_comprar").equals("S");
                 
        Double precioActual = producto.getDouble("precio_unitario");
        Double ganciaPorPromocion = (promocionBonificacion.getInteger("nr_obtener"))*precioActual;
        Integer cantidadComprando = 0;
        Integer cantidadObteniendo = 0;
        
        if(!es_categoria)
        {               
            List<CotizacionxProducto> cotizacionxproductos = productos.stream().filter(x -> Objects.equals(x.getInteger("tipo_id"), promocionBonificacion.getInteger("tipo_id"))).collect(Collectors.toList());
            if(cotizacionxproductos.isEmpty()) return 0.0;
            
            CotizacionxProducto cotizacionxproducto = cotizacionxproductos.stream().findFirst().get();
            cantidadComprando = cotizacionxproducto.getInteger("cantidad_descuento_disponible");     
            cantidadObteniendo = producto.getInteger("cantidad_descuento_disponible"); 
            
            Integer nr_obtener =  promocionBonificacion.getInteger("nr_obtener");                      
            Integer nr_comprar = promocionBonificacion.getInteger("nr_comprar");                      
            
            Integer nrPromociones = Integer.min(cantidadComprando/nr_comprar, cantidadObteniendo/nr_obtener);
            if(nrPromociones <= 0) return 0.0;
            
            producto.set("cantidad_descuento_disponible",cantidadObteniendo - nr_obtener*nrPromociones);
            cotizacionxproducto.set("cantidad_descuento_disponible",cantidadComprando - nr_comprar*nrPromociones);
            
            valorPromocion = ganciaPorPromocion*nrPromociones;   
        }else
        {
            List<CotizacionxProducto> cotizacionesxproductos = productos.stream().filter(x -> Objects.equals(x.getInteger("categoria_id"), promocionBonificacion.getInteger("categoria_id"))).collect(Collectors.toList());
            if(cotizacionesxproductos.isEmpty()) return 0.0;
            
            for(CotizacionxProducto cotizacionxProducto:cotizacionesxproductos)
            {
                cantidadComprando += cotizacionxProducto.getInteger("cantidad_descuento_disponible");                
            }
              
            Integer nrPromociones = 0;
            Integer nr_obtener = promocionBonificacion.getInteger("nr_obtener");
            
            nrPromociones = cantidadComprando / nr_obtener;
            Integer nrUtilizados = nrPromociones * nr_obtener;
            
            for(CotizacionxProducto cotizacionxProducto:cotizacionesxproductos)
            {
                Integer cantidadDisponible = cotizacionxProducto.getInteger("cantidad_descuento_disponible");                
                if(nrUtilizados<=0) break;
                if(nrUtilizados<=cantidadDisponible)
                {
                    cotizacionxProducto.set("cantidad_descuento_disponible",cantidadDisponible - nrUtilizados);
                }else
                {
                    cotizacionxProducto.set("cantidad_descuento_disponible",0);
                    nrUtilizados -= cantidadDisponible;
                }                
            }
            valorPromocion = ganciaPorPromocion*nrPromociones;  
        }
                          
        return valorPromocion;
    }
    
    private Double aplicarPromocionCantidad(List<Promocion> promocionesCantidad,CotizacionxProducto producto) throws Exception
    {        
        if(promocionesCantidad.isEmpty()) return 0.0;                       
        promocionesCantidad.sort((Promocion o1, Promocion o2) -> o1.getInteger("prioridad") - o2.getInteger("prioridad"));
        
        if(promocionesCantidad.isEmpty()) return 0.0;
    
        Promocion promocionAplicada = promocionesCantidad.stream().findFirst().get();    
        Boolean es_categoria = promocionAplicada.getString("es_categoria").equals("S");
        Integer id = (es_categoria) ?  promocionAplicada.getInteger("categoria_id") : promocionAplicada.getInteger("tipo_id");
        
        PromocionCantidad promocionCantidad = PromocionCantidad.findFirst("promocion_id = ?", promocionAplicada.getId());        
        Integer cantidadComprando = 0;
        
        Double valorDescuento = 0.0;
        Integer cantidad = producto.getInteger("cantidad_descuento_disponible");
        Double precioActual = producto.getDouble("precio_unitario");
        Double ganciaPorPromocion = (promocionCantidad.getInteger("nr_obtener")  - promocionCantidad.getInteger("nr_comprar"))*precioActual;
        
        if(!es_categoria)
        {            
            cantidadComprando = cantidad;                               
            Integer nrPromociones = cantidadComprando / promocionCantidad.getInteger("nr_obtener");        
            if(nrPromociones <= 0) return 0.0;
            
            producto.set("cantidad_descuento_disponible",cantidad - nrPromociones*promocionCantidad.getInteger("nr_obtener"));
            valorDescuento = ganciaPorPromocion*nrPromociones;            
        }else
        {            
            List<CotizacionxProducto> cotizacionesxproducto = productos.stream().filter(x -> 
                    TipoProducto.findById(x.get("tipo_id")).getAll(CategoriaProducto.class).stream().anyMatch(y -> 
                            Objects.equals(y.getInteger("categoria_id"), id))).collect(Collectors.toList());
            Integer nrPromociones = 0;
            Integer nr_obtener = promocionCantidad.getInteger("nr_obtener");
            
            for(CotizacionxProducto cotizacionxProducto:cotizacionesxproducto)
            {
                cantidadComprando += cotizacionxProducto.getInteger("cantidad_descuento_disponible");                
            }
            
            nrPromociones = cantidadComprando / nr_obtener;
            Integer nrUtilizados = nrPromociones * nr_obtener;
            
            for(CotizacionxProducto cotizacionxProducto:cotizacionesxproducto)
            {
                Integer cantidadDisponible = cotizacionxProducto.getInteger("cantidad_descuento_disponible");                
                if(nrUtilizados<=0) break;
                if(nrUtilizados<=cantidadDisponible)
                {
                    cotizacionxProducto.set("cantidad_descuento_disponible",cantidadDisponible - nrUtilizados);
                }else
                {
                    cotizacionxProducto.set("cantidad_descuento_disponible",0);
                    nrUtilizados -= cantidadDisponible;
                }                
            }
            valorDescuento = ganciaPorPromocion*nrPromociones;                            
        }                              
        return  valorDescuento;                
    }
    
    public Double aplicarPromocionPorcentaje(List<Promocion> promocionesProcentaje,CotizacionxProducto producto)
    {        
        if(promocionesProcentaje.isEmpty()) return 0.0;                       
        
        Double valorPromocion = 0.0;
        Integer cantidad = producto.getInteger("cantidad_descuento_disponible");
        Double precioActual = producto.getDouble("precio_unitario");
        
                
        Double cantidadPorcentaje = 0.0;
        for(Promocion promocion:promocionesProcentaje)
        {            
            PromocionPorcentaje promocionPorcentaje = PromocionPorcentaje.findFirst("promocion_id = ?", promocion.getId());
            cantidadPorcentaje += promocionPorcentaje.getDouble("valor_desc");
        }
        if(cantidadPorcentaje == 0) return 0.0;
        return  cantidad*precioActual*(cantidadPorcentaje/100);         
    }        
    
    private Double calcularFlete(CotizacionxProducto producto) throws Exception
    {         
        TipoProducto productoReferenciado = TipoProducto.findById(producto.get("tipo_id"));
        List<Flete> fletes = Flete.where("tipo_id = ?",producto.get("tipo_id"));        
        List<CategoriaProducto> categorias = productoReferenciado.getAll(CategoriaProducto.class);
        
        for(CategoriaProducto categoria:categorias)
        {
            fletes.addAll(Flete.where("categoria_id = ?", categoria.get("categoria_id")));
        }                   
        
        Date today = Date.valueOf(LocalDate.now());        
        List<Flete> fletesActual = fletes.stream().filter(x -> today.after(x.getDate("fecha_inicio"))&& today.before(x.getDate("fecha_fin"))).collect(Collectors.toList());
        Double distancia = ParametroSistema.findFirst("nombre = ?",clienteSeleccionado.getString("departamento")).getDouble("valor");
        Integer cantidad = producto.getInteger("cantidad");
        Double precio_unitario = producto.getDouble("precio_unitario");
     
        Double fleteVolumen = aplicarFleteVolumen(fletesActual, productoReferenciado, distancia, cantidad);
        Double fletePeso = aplicarFletePeso(fletesActual, productoReferenciado, distancia, cantidad);
        Double fletePorcentaje = aplicarFletePorcentaje(fletesActual, precio_unitario, cantidad);                                
        
        return fleteVolumen + fletePeso + fletePorcentaje;
    }
    
    private Double aplicarFleteVolumen(List<Flete> fletes,TipoProducto productoReferenciado, Double distancia,Integer cantidad) throws Exception
    {
        List<Flete> fletesVolumen = fletes.stream().filter(x -> x.getString("tipo").equals(Flete.TIPO.VOLUMEN.name())).collect(Collectors.toList());        
        Double fleteTotal = 0.0;              
        
        Double alto = productoReferenciado.getDouble("alto");
        Double ancho = productoReferenciado.getDouble("ancho");
        Double largo = productoReferenciado.getDouble("longitud");
        
        if(alto == null || ancho == null || largo == null) return 0.0;        
        Double volumen = alto*ancho*largo;
        
        for(Flete flete:fletesVolumen)
        {
            Double factor = CambioMoneda.findFirst("moneda1_id = ? and moneda2_id = ?", monedaSeleccionada.getInteger("moneda_id"),flete.getInteger("moneda_id")).getDouble("factor");
            fleteTotal += volumen*flete.getDouble("valor")*distancia*factor;                        
        }
        
        return fleteTotal*cantidad/1000;
    }
    
    private Double aplicarFletePeso(List<Flete> fletes,TipoProducto productoReferenciado, Double distancia,Integer cantidad) throws Exception
    {
        List<Flete> fletesVolumen = fletes.stream().filter(x -> x.getString("tipo").equals(Flete.TIPO.PESO.name())).collect(Collectors.toList());        
        Double fleteTotal = 0.0;              
        
        Double peso = productoReferenciado.getDouble("peso");
        
        for(Flete flete:fletesVolumen)
        {
            Double factor = CambioMoneda.findFirst("moneda1_id = ? and moneda2_id = ?", monedaSeleccionada.getInteger("moneda_id"),flete.getInteger("moneda_id")).getDouble("factor");
            fleteTotal += peso*flete.getDouble("valor")*distancia*factor;                        
        }
        return fleteTotal*cantidad/1000;   
    }
    
    private Double aplicarFletePorcentaje(List<Flete> fletes,Double precio,Integer cantidad) throws Exception
    {
        List<Flete> fletesVolumen = fletes.stream().filter(x -> x.getString("tipo").equals(Flete.TIPO.PORCENTAJE.name())).collect(Collectors.toList());        
        if(fletesVolumen.isEmpty()) return 0.0;
        Double fleteTotal = 0.0;              
        
        for(Flete flete:fletesVolumen)
        {
            fleteTotal += flete.getDouble("valor");
        }
        return precio*cantidad*(fleteTotal/100);
    }        
    
    private void calcularFinal()
    {
        Double total = 0.0;
        for(CotizacionxProducto cotizacionxproducto:productos)
        {
            total += cotizacionxproducto.getDouble("subtotal_final");
        }
        setValorTotal(total);
    }

    @FXML
    private void buscarProducto(ActionEvent event) {
        modal_stage.showAndWait();
        if(productoDevuelto==null) return;        
        producto.setText(productoDevuelto.getString("nombre"));
    }
    
    @FXML //Aun falta que de la proforma pueda generar un pedido. tanto en navegabilidad como comunicacion
    //de controllers
    private void handleGenerarPedido(ActionEvent event) {   
        if(proformaSelecionado==null)
        {
            infoController.show("No ha seleccionado ninguna proforma");
        }
        cambiarMenuEvent.fire(this, new cambiarMenuArgs(Menu.MENU.Pedidos, "Ventas", proformaSelecionado.getInteger("cotizacion_id")));
    }      

    private void clienteToString() {
        ArrayList<String> words = new ArrayList<>();
        for (Cliente cliente : autoCompletadoList){
            words.add(cliente.getString("nombre"));
        }
        possiblewords = words;
    }
    
    
    private void handleAutoCompletar() {
        try {
            for (Cliente cliente : autoCompletadoList){
            if (cliente.getString("nombre").equals(clienteSh.getText()))
            {                
                setInformacionCliente(cliente);  
                for(CotizacionxProducto cotizacionxProducto:productos)
                {
                    cotizacionxProducto.set("flete",calcularFlete(cotizacionxProducto));
                }
                TablaProductos.getColumns().get(0).setVisible(false);
                TablaProductos.getColumns().get(0).setVisible(true);
            }
        }            
        } catch (Exception e) {
            infoController.show("No se pudo cargar a este cliente: " + e.getMessage());
        }       
    }
    
    private void productoToString() {
        ArrayList<String> words = new ArrayList<>();
        for (TipoProducto producto : autoCompletadoProductoList){
            words.add(producto.getString("nombre"));
        }               
        possiblewordsProducto = words;
    }          
    
    private void handleAutoCompletarProducto() {      
        for (TipoProducto tipoProducto : autoCompletadoProductoList){
            if (tipoProducto.getString("nombre").equals(producto.getText())){           
                productoDevuelto = tipoProducto;             
            }
        }
    }

    private void setInformacionCliente(Cliente cliente)
    {
        telfSh.setText(cliente.getString("telef_contacto")); 
        direccionSh.setText(cliente.getString("direccion_facturacion"));
        String tipoCliente = cliente.getString("tipo_cliente");
        if(tipoCliente.isEmpty())
        {
            dniLabel.setVisible(false);
            VerDocCliente.setVisible(false);
        }else
        {
            if(tipoCliente.equals(Cliente.TIPO.PersonaNatural.name()))
            {
                dniLabel.setText("DNI");
                VerDocCliente.setText(cliente.getString("dni"));
            }else
            {
                dniLabel.setText("RUC");
                VerDocCliente.setText(cliente.getString("ruc"));                        
            }
        }
        clienteSeleccionado = cliente;  
    }

    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Proformas;
    }
}

