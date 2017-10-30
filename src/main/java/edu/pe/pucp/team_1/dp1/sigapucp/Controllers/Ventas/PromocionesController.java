/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Promocion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.PromocionBonificacion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.PromocionCantidad;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.PromocionPorcentaje;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

/*
 * FXML Controller class
 *
 * @author Alberto
 */
public class PromocionesController extends Controller{

    @FXML
    private AnchorPane promo_formulario;   
    //Index
    @FXML
    private TextField busqCodigoPromo;
    @FXML
    private TextField busqTipoPromo;    
    @FXML
    private DatePicker busqFecha;
    @FXML
    private TableView<Promocion> tabla_promociones;
    @FXML
    private TableColumn<Promocion, String> columna_codigo;
    @FXML
    private TableColumn<Promocion, String> columna_tipo;
    
    //Crear, editar
    @FXML
    private Label labelNombrePromocion;
    @FXML
    private TextField txtFieCodigoPromo;
    @FXML
    private DatePicker dpFecha1;
    @FXML
    private DatePicker dpFecha2;
    @FXML
    private ComboBox<String> comboBoxTipoPromo;  
    @FXML
    private ComboBox<String> comboxPrioridad;    
    @FXML
    private RadioButton rbPorTipo;
    @FXML
    private RadioButton rbPorCategoria;
    @FXML
    private Spinner<?> spCompro;
    @FXML
    private TextField txtFieCodigoProducto1;
    @FXML
    private Button botonTipo1;
    @FXML
    private Button botonCategoria1;
    @FXML
    private Spinner<?> spLlevo;
    @FXML
    private TextField txtFieCodigoProducto2;
    @FXML
    private Button botonTipo2;
    @FXML
    private Button botonCategoria2;
    @FXML
    private Spinner<?> spPorc;    
    @FXML
    private TextField txtFieCodigoProducto3;
    @FXML
    private Button botonTipo3;
    @FXML
    private Button botonCategoria3;
    @FXML
    private TextField txtFiePrecioUnitario;    
    @FXML
    private TextField desc_concepto;
      

    private Boolean crear_nuevo;
    private List<Promocion> promociones;   
    private Promocion promocion_seleccionada;
    private final ObservableList<Promocion> masterData = FXCollections.observableArrayList();
    private InformationAlertController infoController;
    private Boolean es_categoria_obtener;
    public TipoProducto tipo;
    private Controller activeController;
    
    @FXML private ModalTipoProdController ModalTipoProdController;
    @FXML private ModalCatProdController ModalCatProdController;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        //infoController = new InformationAlertController();
        inhabilitar_formulario();
        promocion_seleccionada = null;
        promociones = null;
        es_categoria_obtener = false;
        promociones = Promocion.findAll();
        cargar_tabla_index();
        //Formulario
        rbPorTipo.setOnAction(e -> manejarrbPorTipo());
        rbPorCategoria.setOnAction(e -> manejarrbPorCategoria());
        comboBoxTipoPromo.getItems().addAll("Por cantidad", "Por bonificación","Por porcentaje");
        comboBoxTipoPromo.setOnAction(e -> manejarcomboBoxTipoPromo());
        comboxPrioridad.getItems().addAll("1", "2","3");
        ModalTipoProdController.abrirModal.addHandler((sender, args) -> {
            try {
                FXMLLoader loaderContenido = new FXMLLoader(getClass().getResource("../fxml/Ventas/Promociones/ModalTipoProd.fxml"));
                System.out.println("Hola");
                System.out.println(loaderContenido);
                Controller controller = loaderContenido.<Controller>getController();
                loaderContenido.setController(controller);
                
            } catch (Exception ex) {
                System.out.println("Error");
            }
        });    
        ModalCatProdController.abrirModal.addHandler((sender, args) -> {
            try {
                FXMLLoader loaderContenido = new FXMLLoader(getClass().getResource("../fxml/Ventas/Promociones/ModalCatProd.fxml"));
                Controller controller = loaderContenido.<Controller>getController();
                loaderContenido.setController(controller);
                
            } catch (Exception ex) {
                System.out.println("Error");
            }
        }); 
    }    
    
    //Tabla de promociones
    public void limpiar_tabla_index(){
        tabla_promociones.getItems().clear();
    }
    
    public void cargar_tabla_index(){
        limpiar_tabla_index();
        for( Promocion promocion : promociones){
            masterData.add(promocion);
        }
        System.out.println(masterData.size());
        tabla_promociones.setEditable(false);
        columna_codigo.setCellValueFactory((TableColumn.CellDataFeatures<Promocion, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("promocion_cod")));
        columna_tipo.setCellValueFactory((TableColumn.CellDataFeatures<Promocion, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo")));
        tabla_promociones.setItems(masterData);
    }
     
    public void inhabilitar_formulario (){
        promo_formulario.setDisable(true);
    }
    
    public static Calendar stringToCalendar(String dateString, String format) {
 
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        try {
            if (dateString != null && !dateString.equals("")) {
                calendar.setTime(sdf.parse(dateString));
            }
        } catch (ParseException e) {
            System.out.println(e);
        }
        return calendar;
    }
    
    public boolean cumple_condicion_busqueda(Promocion promocion, String codigo, String tipo, Calendar fecha){
        boolean match = true;
        if ( codigo.equals("") && tipo.equals("") && fecha.equals("")){
            match = false;
        }
        else {
            match = (!codigo.equals("")) ? (match && (promocion.get("promocion_cod")).equals(codigo)) : true;
            match = (!tipo.equals("")) ? (match && (promocion.get("tipo")).equals(tipo)) : true;
            Calendar fechaIni = stringToCalendar(promocion.get("fecha_inicio").toString(),"dd/MM/yyyy");
            Calendar fechaFin = stringToCalendar(promocion.get("fecha_fin").toString(),"dd/MM/yyyy");
            match = (!fecha.equals("")) ? (match && fechaIni.before(fecha) && fechaFin.after(fecha)) : true;
        }
        return match;
    }
    @FXML
    public void buscar_promocion(ActionEvent event) throws IOException{
        promociones = Promocion.findAll();
        masterData.clear();
        Calendar fecha = stringToCalendar(busqFecha.getValue().toString(), "dd/MM/yyyy");
        try{
            for(Promocion promocion : promociones){
                if (cumple_condicion_busqueda(promocion, busqCodigoPromo.getText(), busqTipoPromo.getText(), fecha)){
                    masterData.add(promocion);
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    //Botón editar
    @FXML
    private void visualizar_promocion(ActionEvent event) {                   
//        if(!PreCambiarAccion()) return;
        crear_nuevo = false;
        promocion_seleccionada = tabla_promociones.getSelectionModel().getSelectedItem();
        if(promocion_seleccionada == null) return;        
        //setPromocionVisible(promocion_seleccionada);                        
    }
    
    
    
    //Botón guardar
    @Override
    public void guardar() {
        if (crear_nuevo){
            crear_promocion();
        }else{
            if (promocion_seleccionada == null) return;
            //editar_promocion(promocion_seleccionada);
        }
        promociones = Promocion.findAll();
        cargar_tabla_index();
    }   
        
    // Botón nuevo
    public void nuevo(){
       crear_nuevo = true;
       habilitar_formulario();
       limpiar_formulario();       
    }
    
    public void crear_promocion(){
        try{
            Base.openTransaction();  
            promocion_seleccionada = null;
            //Agregando las promociones a las tablas hijas por tipo de promoción
            String codigoPromo = txtFieCodigoPromo.getText();
            String tipoPromo = obtener_tipo_promo();
            String es_categoria = obtener_clasificacion();
            String flag_categoria = (es_categoria_obtener) ? "S":"N";
            if (tipoPromo.equals("Por cantidad")){
                PromocionCantidad promocionC = new PromocionCantidad();                
                promocionC.asignar_atributos(codigoPromo, (Integer)spCompro.getValue(), (Integer)spLlevo.getValue(), flag_categoria, txtFieCodigoProducto2.getText());
                promocionC.saveIt();
            } else if (tipoPromo.equals("Por bonificación")){
                PromocionBonificacion promocionB = new PromocionBonificacion();
                promocionB.asignar_atributos(codigoPromo, (Integer)spCompro.getValue(), (Integer)spLlevo.getValue());
                promocionB.saveIt();
            } else {
                PromocionPorcentaje promocionP = new PromocionPorcentaje();
                String concepto = (desc_concepto.getText() == null) ? "":desc_concepto.getText();
                promocionP.asignar_atributos(codigoPromo, (Integer)spPorc.getValue(), concepto);
                promocionP.saveIt();
            }
            
            //Obteniendo valores para Objeto Promoción            
            String fechaIni = dpFecha1.getValue().toString();
            String fechaFin = dpFecha2.getValue().toString();
            String prioridad = obtener_prioridad();
            String estado = "ACTIVO"; //obtener_estado();
            promocion_seleccionada.asignar_atributos(codigoPromo, fechaIni, fechaFin, prioridad, es_categoria, estado, tipoPromo, txtFieCodigoProducto1.getText());
            promocion_seleccionada.saveIt();
            Base.commitTransaction();
            infoController.show("La promoción ha sido creado satisfactoriamente"); 
        }
        catch(Exception e){
            System.out.println(e);
            Base.rollbackTransaction();
        }        
    }
    
    public void habilitar_formulario(){
        promo_formulario.setDisable(false);
    }
    
    public void limpiar_formulario(){
        txtFieCodigoPromo.clear();
        rbPorTipo.setSelected(false);
        rbPorCategoria.setSelected(false);
        txtFieCodigoProducto2.clear();
        txtFieCodigoProducto3.clear();
        txtFiePrecioUnitario.clear();
        SpinnerValueFactory spComproValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0,1);
        spCompro.setValueFactory(spComproValues);
        SpinnerValueFactory spLlevoValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0,1);
        spLlevo.setValueFactory(spLlevoValues);
        SpinnerValueFactory spPorcValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0,1);
        spPorc.setValueFactory(spPorcValues);
    }
    
    private void manejarrbPorTipo(){
        String tipoPromo = obtener_tipo_promo();
        rbPorCategoria.setSelected(false);
        if (tipoPromo.equals("Por porcentaje")){
            botonCategoria3.setDisable(true);
            botonTipo3.setDisable(false);
        } else {
            botonCategoria1.setDisable(true);
            botonCategoria2.setDisable(true);
            botonTipo1.setDisable(false);
            botonTipo2.setDisable(false);
        }
    }
    
    private void manejarrbPorCategoria(){
        String tipoPromo = obtener_tipo_promo();
        rbPorTipo.setSelected(false);
        if (tipoPromo.equals("Por porcentaje")){
            botonTipo3.setDisable(true);        
            botonCategoria3.setDisable(false);
        } else {
            botonTipo1.setDisable(true);
            botonTipo2.setDisable(true);
            botonCategoria1.setDisable(false);
            botonCategoria2.setDisable(false);
        } 
    }
    
    private String obtener_tipo_promo(){
        String tipoPromo = ( comboBoxTipoPromo.getSelectionModel().getSelectedItem() == null ) ? "" : comboBoxTipoPromo.getSelectionModel().getSelectedItem(); 
        return tipoPromo;
    }
    
    private String obtener_prioridad(){
        String prioridad = ( comboxPrioridad.getSelectionModel().getSelectedItem() == null ) ? "" : comboxPrioridad.getSelectionModel().getSelectedItem(); 
        return prioridad;
    }
    
    private String obtener_clasificacion(){
        String clasificacion = ( rbPorCategoria.isSelected() ) ? "S" : "N"; 
        return clasificacion;
    }
    
    private void deshabilitar_Porcentaje(){
        SpinnerValueFactory spPorcValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0,1);
        spPorc.setValueFactory(spPorcValues);
        spPorc.setDisable(true);
        
        txtFieCodigoProducto3.clear();
        txtFieCodigoProducto3.setDisable(true);
        
        botonTipo3.setDisable(true);
        botonCategoria3.setDisable(true);
    }
    
    private void habilitar_Porcentaje(){
        spPorc.setDisable(false);
        txtFieCodigoProducto3.setDisable(false);        
        botonTipo3.setDisable(false);
        botonCategoria3.setDisable(false);
    }
    
    //función para limpiar lo necesario para el tipo de promoción: Por Cantidad
    private void limpiarPorCantidad(){
        rbPorTipo.setSelected(false);
        rbPorTipo.setDisable(true);
        
        rbPorCategoria.setSelected(false);
        rbPorCategoria.setDisable(true);
        
        botonTipo1.setDisable(false);
        botonTipo2.setDisable(false);
        botonCategoria1.setDisable(false);
        botonCategoria2.setDisable(false);
    }
    
    private void habilitar_CantBoni(){
        spCompro.setDisable(false);        
        txtFieCodigoProducto1.setDisable(false);        
        botonTipo1.setDisable(false);
        botonCategoria1.setDisable(false);        
        spLlevo.setDisable(false);        
        txtFieCodigoProducto2.setDisable(false);        
        botonTipo2.setDisable(false);
        botonCategoria2.setDisable(false);
    }
    
    private void deshabilitar_CantBoni(){
        SpinnerValueFactory spComproValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0,1);
        spCompro.setValueFactory(spComproValues);
        spCompro.setDisable(true);
        
        txtFieCodigoProducto1.clear();
        txtFieCodigoProducto1.setDisable(true);
        
        botonTipo1.setDisable(true);
        botonCategoria1.setDisable(true);
        
        SpinnerValueFactory spLlevoValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0,1);
        spLlevo.setValueFactory(spLlevoValues);
        spLlevo.setDisable(true);
        
        txtFieCodigoProducto2.clear();
        txtFieCodigoProducto2.setDisable(true);
        
        botonTipo2.setDisable(true);
        botonCategoria2.setDisable(true);
    }
    
    private void manejarcomboBoxTipoPromo(){
        String tipoPromo = obtener_tipo_promo();
        if (tipoPromo.equals("Por cantidad")){
            habilitar_CantBoni();
            deshabilitar_Porcentaje();   
            limpiarPorCantidad();
        } else if (tipoPromo.equals("Por bonificación")){
            habilitar_CantBoni();
            deshabilitar_Porcentaje();
            rbPorTipo.setDisable(false);
            rbPorCategoria.setDisable(false);
        } else if (tipoPromo.equals("Por porcentaje")){
            habilitar_Porcentaje();
            deshabilitar_CantBoni();
        }
    }
}
