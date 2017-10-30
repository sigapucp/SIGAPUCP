/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.ContenidoPrincipalController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cotizacion;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.abrirDetallesArgs;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
    private Button buscarPedido;
    @FXML
    private ComboBox<?> estadoBusq;
    @FXML
    private TextField pedidoBusq;
    @FXML
    private TableView<Cotizacion> tablaPedidos;
    @FXML
    private TableColumn<Cotizacion, String> proformaColumn;
    @FXML
    private TableColumn<Cotizacion, String> clienteColumn;    
    @FXML
    private Button visualizarPedido;
    @FXML
    public DatePicker fechaProfSh;
    @FXML
    public TextField clienteSh;
    @FXML
    private RadioButton solesProf;
    @FXML
    private RadioButton dolProf;
    @FXML
    private TextField telfSh;
    @FXML
    private TextField correoSh;
    @FXML
    private TableView<?> productosProf;
    @FXML
    private TextField subTotalSol;
    @FXML
    private TextField igvSol;
    @FXML
    private TextField totalSol;
    @FXML
    private TextField subTotalDol;
    @FXML
    private TextField igvDol;
    @FXML
    private TextField totalDol;
    @FXML
    private Button generarPed;
    @FXML
    private Button EliminarProf;
    @FXML
    private Spinner<?> cantProd;
    @FXML 
    private TextField producto;
    @FXML
    private Button agregarProdProf;
    @FXML
    private AnchorPane proforma_tabla;
    @FXML
    private AnchorPane proforma_formulario;
    @FXML
    private SpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0,1);
    @FXML
    private TableColumn<Cotizacion, String> codProfColumn;
    @FXML
    private TableColumn<Cotizacion, String> descripProdColumn;
    @FXML
    private TableColumn<Cotizacion, String> cantProdColumn;
    @FXML
    private TableColumn<Cotizacion, String> puSolColumn;
    @FXML
    private TableColumn<Cotizacion, String> precioSolColumn;
    @FXML
    private TableColumn<Cotizacion, String> puDolColumn;
    @FXML
    private TableColumn<Cotizacion, String> precioDolColumn;    
    @FXML 
    static Stage modal_stage = new Stage();
    @FXML 
    private ContenidoPrincipalController contenidoPrincipalController = new ContenidoPrincipalController();
    @FXML
    private Label proformaSub;
    
    public IEvent<abrirDetallesArgs> abrirDetalle;
    
    private final ObservableList<Cotizacion> cotizaciones = FXCollections.observableArrayList(); 
    
    private Cotizacion proformaSelecionado;
    
    private Boolean crearNuevo;
    
    private List<Cotizacion> tempCotizaciones;

    private InformationAlertController infoController;
    
    private List<Cliente> autoCompletadoList;
    
    ArrayList<String> possiblewords = new ArrayList<>();
    
    AutoCompletionBinding<String> autoCompletionBinding;
    /**
     * Initializes the controller class.
     */
    
   
         
    public ProformasController()
    {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        tempCotizaciones = Cotizacion.findAll();       
        
        for (Cotizacion coti : tempCotizaciones) {
            cotizaciones.add(coti);
        }                               
        
        infoController = new InformationAlertController();
                
        proformaSelecionado = null;
        crearNuevo = false;
    }
    
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //llenar tabla:
        llenar_tabla_index();   
        autoCompletadoList = Cliente.findAll();
        clienteToString();
        autoCompletionBinding = TextFields.bindAutoCompletion(clienteSh, possiblewords);
        autoCompletionBinding.addEventHandler(EventType.ROOT, (event) -> {
            handleAutoCompletar();
        });
        dolProf.setOnAction(e -> manejarRadBttnDol());
        solesProf.setOnAction(e -> manejarRadBttnSol());
        abrirDetalle = new Event<>();
        cantProd.setValueFactory(valueFactory);
        inhabilitar_formulario();
        Parent modal_content;                                
        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Ventas/Proformas/AgregarProformas.fxml"));
            AgregarProductosController controller = new AgregarProductosController();
            loader.setController(controller);
                      
            Scene modal_content_scene = new Scene((Parent)loader.load());
            modal_stage.setScene(modal_content_scene);
            if (modal_stage.getModality() == null) modal_stage.initModality(Modality.APPLICATION_MODAL);   
            
            
        } catch (IOException ex) {
            Logger.getLogger(PedidosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    private void llenar_tabla_index(){
        //tempCotizaciones = Cotizacion.findAll();
        proformaColumn.setCellValueFactory((CellDataFeatures<Cotizacion, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("cotizacion_cod")));   
        clienteColumn.setCellValueFactory((CellDataFeatures<Cotizacion, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("client_id")));
        tablaPedidos.setItems(cotizaciones);
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
            if(proformaSelecionado == null) return;
            editarProforma(proformaSelecionado);
        }        
        RefrescarTabla();
    }
    
    private void editarProforma(Cotizacion proforma) {        
//        String nombre = VerNombre.getText();
//        String apellido = VerApellido.getText();
//        String telefono = VerTelefono.getText();
//        String email = VerCorreo.getText();
//        String estado = VerEstado.getSelectionModel().getSelectedItem();
//        String rol = VerRol.getSelectionModel().getSelectedItem();
//        
//        try{      
//        Base.openTransaction();       
//        usuario.set("nombre",nombre);
//        usuario.set("apellido", apellido);
//        usuario.set("telefono", telefono);
//        usuario.set("email", email);
//        usuario.set("estado", estado);
//        
//        Rol usuarioRol = Rol.findFirst("rol_cod = ?", rol);
//        usuario.set("rol_id",usuarioRol.getId());
//        usuario.set("rol_cod",rol);        
//        usuario.saveIt();
//        Base.commitTransaction();
//        
//        infoController.show("El usuario ha sido editado satisfactoriamente");        
//        }
//        catch(Exception e){
//           Base.rollbackTransaction();
//        }                
    }
    
    private void crearProforma() {
        String cliente = clienteSh.getText();
        String telefono = telfSh.getText();
        String email = correoSh.getText();
        LocalDate fechaLocal = fechaProfSh.getValue();
        Date fecha = Date.from(fechaLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fechaEmision = dateFormat.format(fecha);
        System.out.println(fechaEmision);
        String cotizacion_cod = "PROF";
        //String cotizacion_id = obtenerUltimoId() + 1;
        int monedaId;
        if (solesProf. isSelected()){
             monedaId = 1;
        } else {
            monedaId = 2;
        }      
        asignar_data(cotizacion_cod, 1, fechaEmision, 0.0, "ACTIVO","admin",'1',"admin",1, monedaId,'n');  
    }
    
    private void asignar_data(String cotizacion_cod, int cliente_id, String fechaEmision, Double total, String estado, String usuario, char flag, String usuario_cod, int usuario_id, int monedaId, char promoAut ){
        try{      
        Base.openTransaction();  
        Integer cod = Integer.valueOf(String.valueOf((Base.firstCell("select last_value from cotizaciones_cotizacion_id_seq")))) + 1;        
        cotizacion_cod = cotizacion_cod + Integer.toString(cod);
        Cotizacion proforma = new Cotizacion();
        proforma.set("cotizacion_cod",cotizacion_cod);
        proforma.set("client_id",cliente_id);
        //proforma.set("cotizacion_id", cod);
        proforma.setDate("fecha_emision", fechaEmision);
        proforma.set("total", 0.0);
        //proforma.set("telefono", telefono);
        //proforma.set("email", email);
        proforma.set("estado", estado);
        //proforma.set("last_user_change",usuarioActual.getString("cotizacion_cod"));
        proforma.set("last_user_change", usuario);
        proforma.set("flag_last_operation", flag);
        proforma.set("usuario_cod",usuario_cod);
        proforma.set("usuario_id", usuario_id);
        proforma.set("moneda_id", monedaId);
        proforma.set("promocion_automatica", promoAut);     
        //proforma.setDate("last_date_change",dateFormat.format(date));
        proforma.saveIt();
        Base.commitTransaction();
        System.out.println("Todo Correcto BD");
        infoController.show("Se ha creado la proforma: PROF"+String.valueOf(cod));        
        }
        catch(Exception e){
           System.out.println(e);
           Base.rollbackTransaction();
        }    
    }
    
    @FXML
    private void buscarPedido(ActionEvent event) {        
        String proformaId = pedidoBusq.getText();
        String cliente = clienteBusq.getText();
        Integer clienteId = null;
        String estado = ( estadoBusq.getSelectionModel().getSelectedItem() == null ) ? "" : estadoBusq.getSelectionModel().getSelectedItem().toString();
        tempCotizaciones = Cotizacion.findAll();
        if(proformaId!=null&&!proformaId.isEmpty()) {            
            tempCotizaciones = tempCotizaciones.stream().filter(p -> p.getString("cotizacion_cod").equals(proformaId)).collect(Collectors.toList());
        }
        if (clienteId!=null) {
            tempCotizaciones = tempCotizaciones.stream().filter(p -> p.getString("client_id").equals(clienteId)).collect(Collectors.toList());
        }
        if(estado!=null&&!estado.isEmpty()) {
            tempCotizaciones = tempCotizaciones.stream().filter(p -> p.get("estado").equals(estado)).collect(Collectors.toList());
        }
        RefrescarTabla();
        try {                        
        } catch (Exception e) { 
        }
    }      
    
    @FXML
    private void visualizarProforma(ActionEvent event){
        crearNuevo = false;
        proformaSelecionado = tablaPedidos.getSelectionModel().getSelectedItem();
        if (proformaSelecionado == null) return;
        setProformaVisible(proformaSelecionado);
    }
    
    @FXML
    private void setProformaVisible(Cotizacion selected){
        String proforma = selected.getString("cotizacion_cod");
        String fechaEmision = selected.get("fecha_emision").toString();
        //falta pasarle bien el dato de la fecha. 
        //tambien buscar al cliente
        proformaSub.setText("Cotizacion: " + proforma);
        //fechaProfSh.setValue(fechaEmision.);
    }
    
    private void RefrescarTabla()
    {
        try {
            cotizaciones.removeAll(cotizaciones);
            //tempCotizaciones = Cotizacion.findAll();               
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
        correoSh.clear();
        fechaProfSh.setValue(null);
        producto.clear();
        productosProf.getItems().clear();
        subTotalDol.clear();
        igvDol.clear();
        totalDol.clear();
        subTotalSol.clear();
        igvSol.clear();
        totalSol.clear();
        solesProf.setSelected(false);
        dolProf.setSelected(false);
        SpinnerValueFactory newvalueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0,1);
        cantProd.setValueFactory(newvalueFactory);
    }
    

    private void manejarRadBttnDol(){
        solesProf.setSelected(false);
    }
//    
    @FXML
    private void handleAgregarProducto(ActionEvent event) {
        modal_stage.showAndWait();
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
    
    private void manejarRadBttnSol(){
        dolProf.setSelected(false);
    }

    private void clienteToString() {
        ArrayList<String> words = new ArrayList<>();
        for (Cliente cliente : autoCompletadoList){
            words.add(cliente.getString("nombre"));
        }
        possiblewords = words;
    }

    private void handleAutoCompletar() {
        int i = 0;
        for (Cliente cliente : autoCompletadoList){
            if (cliente.getString("nombre").equals(clienteSh.getText())){
                System.out.println(cliente.getString("telef_contacto"));
                telfSh.setText(cliente.getString("telef_contacto"));
                
            }
        }
    }
    
}
