/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.AgregarProductosController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.ContenidoPrincipalController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cotizacion;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Sistema.ParametroSistema;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.CotizacionxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Moneda;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.abrirDetallesArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoArgs;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
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
    
    static Stage modal_stage = new Stage();
    
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
        producto.clear();;
        
        proformaColumn.setCellValueFactory((CellDataFeatures<Cotizacion, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cotizacion_cod")));   
        clienteColumn.setCellValueFactory((CellDataFeatures<Cotizacion, String> p) -> new ReadOnlyObjectWrapper(Cliente.findById(p.getValue().get("client_id")).getString("nombre")));
        ColumnaEstado.setCellValueFactory((CellDataFeatures<Cotizacion, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("estado")));
        
        codProdColumn.setCellValueFactory((CellDataFeatures<CotizacionxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        nombreProdColumn.setCellValueFactory((CellDataFeatures<CotizacionxProducto, String> p) -> new ReadOnlyObjectWrapper(TipoProducto.findById(p.getValue().get("tipo_id")).getString("nombre")));
        cantProdColumn.setCellValueFactory((CellDataFeatures<CotizacionxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cantidad")));
        precioUnitarioColumn.setCellValueFactory((CellDataFeatures<CotizacionxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("precio_unitario")));
        descProdColumna.setCellValueFactory((CellDataFeatures<CotizacionxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("descuento")));
        fleteProdColumn.setCellValueFactory((CellDataFeatures<CotizacionxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("flete")));
        subTotalProdColumna.setCellValueFactory((CellDataFeatures<CotizacionxProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("subtotal_final")));        
        
        cotizaciones.addAll(tempCotizaciones);
        tablaPedidos.setItems(cotizaciones);
        TablaProductos.setItems(productos);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //llenar tabla:
        try {
            llenar_tabla_index();   
            
            ObservableList<String> monedas = FXCollections.observableArrayList();            
            monedas.addAll(Moneda.findAll().stream().map(x -> x.getString("nombre")).collect(Collectors.toList()));
            VerMoneda.setItems(monedas);
            
            ObservableList<String> estados = FXCollections.observableArrayList();       
            estados.add("");
            estados.addAll(Arrays.asList(Cotizacion.ESTADO.values()).stream().map(x->x.name()).collect(Collectors.toList()));   
            estadoBusq.setItems(estados);
            
            
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
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarProductos.fxml"));
            AgregarProductosController controller = new AgregarProductosController();
            loader.setController(controller);                      
            Scene modal_content_scene = new Scene((Parent)loader.load());
            modal_stage.setScene(modal_content_scene);
            if(modal_stage.getModality() != Modality.APPLICATION_MODAL) modal_stage.initModality(Modality.APPLICATION_MODAL);    
            
            controller.devolverProductoEvent.addHandler((Object sender, agregarProductoArgs args) -> {
                productoDevuelto = args.producto;
            });
            
            cantProd.setValueFactory(valueFactory);
            //        inhabilitar_formulario();                                    
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
            crearProforma();            
        }else
        {
            if(proformaSelecionado == null)
            {
                infoController.show("No ha seleccionado ninguna Proforma");
                 return;
            }
            editarProforma(proformaSelecionado);
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
        
        if(cotizacionesGuardadas == null) return;
        List<CotizacionxProducto> cotizacionesProductosDelete = cotizacionesGuardadas.stream().filter(x -> productos.stream().noneMatch(y -> !y.isNew() && 
                y.getInteger("cotizacion_id").equals(x.getInteger("cotizacion_id")) && 
                y.getInteger("tipo_id").equals(x.getInteger("tipo_id")))).collect(Collectors.toList());
        
        if(cotizacionesProductosDelete == null) return;
        
        for(CotizacionxProducto cotizacionxProducto:cotizacionesProductosDelete)
        {
            CotizacionxProducto.delete("cotizacion_id = ? AND tipo_id = ?",cotizacionxProducto.get("cotizacion_id"),cotizacionxProducto.get("tipo_id"));
        }
    }
    
    @FXML
    private void buscarPedido(ActionEvent event) {        
        String proformaId = pedidoBusq.getText();
        String cliente = clienteBusq.getText();
        Integer clienteId = null;
        String estado = ( estadoBusq.getSelectionModel().getSelectedItem() == null ) ? "" : estadoBusq.getSelectionModel().getSelectedItem().toString();
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
        }
    }      
    
    @FXML
    private void visualizarProforma(ActionEvent event){
        crearNuevo = false;
        try {
            proformaSelecionado = tablaPedidos.getSelectionModel().getSelectedItem();
            if (proformaSelecionado == null) 
            {
                infoController.show("No ha seleccionado ninguna proforma");
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
    }      
        
    @FXML
    private void eliminarProducto(ActionEvent event) {
        
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
        Double totalEntrada = cotizacionxProducto.getDouble("subtotal_final");      
        recalcularTotal(-totalEntrada);               
        productos.remove(cotizacionxProducto);              
    }

    @FXML
    private void agregarProducto(ActionEvent event) {                
        if(productoDevuelto==null)
        {
            infoController.show("No ha seleccionado ningun producto"); 
           return;
        }
        
        try {
            for(CotizacionxProducto productoCot:productos)
            {
                if(productoCot.getInteger("tipo_id").equals(productoDevuelto.getInteger("tipo_id")))
                {
                    Integer cantidad = productoCot.getInteger("cantidad") + cantProd.getValue();
                    Double precioUnitario = productoCot.getDouble("precio_unitario");                    
                    recalcularTotal(cantProd.getValue()*precioUnitario);
                    
                    // Pueden varias las condiciones de descuento y fletes ...
                    productoCot.set("cantidad",cantidad);
                    productoCot.set("subtotal_final",precioUnitario*cantidad);
                    TablaProductos.getColumns().get(0).setVisible(false);
                    TablaProductos.getColumns().get(0).setVisible(true);
                    return;
                }
            }

            CotizacionxProducto cotizacionxproducto = new CotizacionxProducto();
            Integer cantidad = cantProd.getValue();
            Double precio = productoDevuelto.getPrecioActual();
            
          
            
            cotizacionxproducto.set("tipo_id",productoDevuelto.getId());
            cotizacionxproducto.set("tipo_cod",productoDevuelto.get("tipo_cod"));            
            cotizacionxproducto.set("cantidad",cantidad);                        
            cotizacionxproducto.set("precio_unitario",precio);    
            cotizacionxproducto.set("subtotal_previo",cantidad*precio); 
            
            Double descuento = calcularDescuento(productoDevuelto);
            Double flete = calcularFlete(productoDevuelto);
            
            cotizacionxproducto.set("descuento",descuento); 
            cotizacionxproducto.set("flete",flete);             
            Double totalEntrada = cantidad*precio - descuento + flete;
            cotizacionxproducto.set("subtotal_final",totalEntrada);
            recalcularTotal(totalEntrada);
                        
            productos.add(cotizacionxproducto);            
        } catch (Exception e) {
            infoController.show("No se ha podido agregar ese Producto: " + e.getMessage());
        }                
    }
    
    private void recalcularTotal(Double cambio)
    {
        String totalValue = (!subTotalFinal.getText().isEmpty()) ? subTotalFinal.getText() : "0.0";
        Double subTotalSinIgv = Double.valueOf(totalValue);                    
        subTotalSinIgv += cambio;
        subTotalFinal.setText(String.valueOf(subTotalSinIgv));
        Double valorIgv = IGV*subTotalSinIgv;            
        igvTotal.setText(String.valueOf(valorIgv));
        total.setText(String.valueOf(subTotalSinIgv+valorIgv));        
    }
    
    private void setValorTotal(Double valor)
    {        
        subTotalFinal.setText(String.valueOf(valor));
        Double valorIgv = IGV*valor;            
        igvTotal.setText(String.valueOf(valorIgv));
        total.setText(String.valueOf(valor+valorIgv));                
    }
    
    private Double calcularDescuento(TipoProducto producto)
    {
        return 0.0;        
    }
    
    private Double calcularFlete(TipoProducto producto)
    {
        return 0.0;
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
        abrirDetallesArgs args = new abrirDetallesArgs();
        System.out.println("estoy aqui debuggeando");
        args.setNombreController("Pedidos");
        args.setNombreModulo("Ventas");
        
        abrirDetalle.fire(this, args);
    }      

    private void clienteToString() {
        ArrayList<String> words = new ArrayList<>();
        for (Cliente cliente : autoCompletadoList){
            words.add(cliente.getString("nombre"));
        }
        possiblewords = words;
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

    private void handleAutoCompletar() {
        int i = 0;
        for (Cliente cliente : autoCompletadoList){
            if (cliente.getString("nombre").equals(clienteSh.getText())){                
                setInformacionCliente(cliente);                             
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

