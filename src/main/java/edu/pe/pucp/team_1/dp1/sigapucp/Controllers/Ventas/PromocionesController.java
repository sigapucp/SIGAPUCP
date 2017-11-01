/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;


import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Modales.ModalController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Promocion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.PromocionBonificacion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.PromocionCantidad;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.PromocionPorcentaje;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
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
    private ComboBox<String> busqTipoPromo;    
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
    private TextField desc_concepto;
      
    private Boolean crear_nuevo;
    private List<Promocion> promociones;   
    private Promocion promocion_seleccionada;
    private final ObservableList<Promocion> masterData = FXCollections.observableArrayList();
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmatonController;
    private Boolean es_categoria_comprar;
    
    //Variables para manejar los custom events de los modales
    private final String TIPO_MODAL_PATH = "/fxml/Ventas/Promociones/ModalTipoProd.fxml";
    private final String CATEGORIA_MODAL_PATH = "/fxml/Ventas/Promociones/ModalCatProd.fxml";        
    private String codigo_promo;
    private String id_promo;
    private boolean es_tipo; //es para saber si lo que se compra o en porcentaje es tipo o categoria
    
    private void manejoDeModales(){
        botonTipo1.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            es_tipo = true;
            openModalEventHandler(TIPO_MODAL_PATH, txtFieCodigoProducto1);
        });
        botonTipo2.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            es_categoria_comprar = false;
            openModalEventHandler(TIPO_MODAL_PATH, txtFieCodigoProducto2);
        });   
        botonTipo3.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            es_tipo = true;
            es_categoria_comprar = false;
            openModalEventHandler(TIPO_MODAL_PATH, txtFieCodigoProducto3);
        });   
        botonCategoria1.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            es_tipo = false;
            openModalEventHandler(CATEGORIA_MODAL_PATH, txtFieCodigoProducto1);
        }); 
        botonCategoria2.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            es_categoria_comprar = true;
            openModalEventHandler(CATEGORIA_MODAL_PATH, txtFieCodigoProducto2);
        }); 
        botonCategoria3.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            es_tipo = false;
            es_categoria_comprar = true;
            openModalEventHandler(CATEGORIA_MODAL_PATH, txtFieCodigoProducto3);
        }); 
    }
    
    private void openModalEventHandler(String modalPath, TextField txtField) {
        try {
            Stage currentStage = new Stage();
            FXMLLoader loaderContenido = new FXMLLoader(getClass().getResource(modalPath));
            AnchorPane contenido = (AnchorPane) loaderContenido.load();
            ModalController controller = loaderContenido.<ModalController>getController();
            controller.setCurrentStage(currentStage);
            controller.abrirModal.addHandler((sender,tipargs)->{
                codigo_promo = tipargs.getCodigo();
                id_promo = tipargs.getId();
                if(txtField.getId().equals("txtFieCodigoProducto1")&&comboBoxTipoPromo.getSelectionModel().getSelectedItem().equals(Promocion.TIPO.CANTIDAD.name()))
                {
                    txtFieCodigoProducto2.setText(codigo_promo);
                }
                txtField.setText(codigo_promo);                  
            });
            openModal(currentStage, contenido);
        } catch (IOException ex) {
            Logger.getLogger(PromocionesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void openModal(Stage currentStage, AnchorPane contenido) {
        Scene modal_content_scene = new Scene(contenido);                
        currentStage.setScene(modal_content_scene);
        currentStage.initModality(Modality.APPLICATION_MODAL);
        currentStage.setScene(modal_content_scene);
        currentStage.showAndWait();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
        inhabilitar_formulario();
        promocion_seleccionada = null;
        promociones = null;
        
        es_categoria_comprar = false;
        es_tipo = false;
        
        promociones = Promocion.findAll();
        cargar_tabla_index();
        //Formulario
        rbPorTipo.setOnAction(e -> manejarrbPorTipo());
        rbPorCategoria.setOnAction(e -> manejarrbPorCategoria());
        
        comboBoxTipoPromo.getItems().addAll(Promocion.TIPO.CANTIDAD.name()
                                        , Promocion.TIPO.BONIFICACIÓN.name()
                                        ,Promocion.TIPO.PORCENTAJE.name()
                                        );
        comboBoxTipoPromo.setOnAction(e -> manejarcomboBoxTipoPromo());
        comboxPrioridad.getItems().addAll("1", "2","3");
        
        busqTipoPromo.getItems().addAll("",Promocion.TIPO.CANTIDAD.name()
                                        , Promocion.TIPO.BONIFICACIÓN.name()
                                        ,Promocion.TIPO.PORCENTAJE.name()
                                        );
        manejoDeModales();        
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
        tabla_promociones.setEditable(false);
        columna_codigo.setCellValueFactory((TableColumn.CellDataFeatures<Promocion, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("promocion_cod")));
        columna_tipo.setCellValueFactory((TableColumn.CellDataFeatures<Promocion, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo")));
        tabla_promociones.setItems(masterData);
    }
     
    public void inhabilitar_formulario (){
        promo_formulario.setDisable(true);
    }
    
    public String formatoFecha(LocalDate fecha, String formato) {
        LocalDate fechaLocal = fecha;
        Date auxfecha = Date.from(fechaLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fechaEmision = dateFormat.format(auxfecha);        
        return fechaEmision;
    }
    
    public boolean cumple_condicion_busqueda(Promocion promocion, String codigo, String tipo, LocalDate fecha){
        boolean match = true;        
        if ( codigo.equals("") && tipo.equals("") && fecha==null){
            match = true;
        }else {
            Date fechaDate = new Date();
            if (!(fecha==null))
                fechaDate = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
            match = (!codigo.equals("")) ? (match && (promocion.get("promocion_cod")).equals(codigo)) : match;
            match = (!tipo.equals("")) ? (match && (promocion.get("tipo")).equals(tipo)) : match;
            Date fechaIni = promocion.getDate("fecha_inicio");            
            Date fechaFin = promocion.getDate("fecha_fin");            
            match = (!(fecha==null)) ? (match && fechaIni.before(fechaDate) && fechaFin.after(fechaDate)) : match;
        }
        return match;
    }
    @FXML
    public void buscar_promocion(ActionEvent event) throws IOException{
        promociones = Promocion.findAll();
        masterData.clear();
        LocalDate fecha = busqFecha.getValue();
        try{
            for(Promocion promocion : promociones){
                if (cumple_condicion_busqueda(promocion, busqCodigoPromo.getText(), busqTipoPromo.getSelectionModel().getSelectedItem(), fecha)){
                    masterData.add(promocion);
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    //Botón visualizar
    @FXML
    private void visualizar_promocion(ActionEvent event) {      
        crear_nuevo = false;
        promocion_seleccionada = tabla_promociones.getSelectionModel().getSelectedItem();
        if(promocion_seleccionada == null) return; 
        habilitar_formulario();
        limpiar_formulario();
        setPromocionVisible(promocion_seleccionada);                        
    }       
    
    private void mostrar_promo(String codigo, LocalDate fechaIni, LocalDate fechaFin, String prioridad, String tipo){
        txtFieCodigoPromo.setText(codigo);
        txtFieCodigoPromo.setDisable(true);
        //fechas
        dpFecha1.setValue(fechaIni);
        dpFecha2.setValue(fechaFin);
                
        comboxPrioridad.setValue(prioridad);
        comboBoxTipoPromo.setValue(tipo);
    }
    
    private void mostrar_promoCantidad(String codigoTipCat,Integer compro,Integer llevo){
        txtFieCodigoProducto1.setText(codigoTipCat);
        txtFieCodigoProducto2.setText(codigoTipCat);
        
        SpinnerValueFactory spComproValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, compro);
        spCompro.setValueFactory(spComproValues);
        
        SpinnerValueFactory spLlevoValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, llevo);
        spLlevo.setValueFactory(spLlevoValues);
    }
    
    private void mostrar_promoBonificacion(Integer compro,String codigoCompro,Integer llevo, String codigoLlevo){
        SpinnerValueFactory spComproValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, compro);
        spCompro.setValueFactory(spComproValues);
        
        txtFieCodigoProducto1.setText(codigoCompro);
        
        SpinnerValueFactory spLlevoValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, llevo);
        spLlevo.setValueFactory(spLlevoValues);
        
        txtFieCodigoProducto2.setText(codigoLlevo);
    }
    
    private void mostrar_promoPorcentaje(String codigoTipCat,Integer valor,String concepto){
        txtFieCodigoProducto3.setText(codigoTipCat);
        
        
        SpinnerValueFactory spPorcValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, valor);
        spPorc.setValueFactory(spPorcValues);
        
        desc_concepto.setText(concepto);
    }
    
    private void setPromocionVisible(Promocion promocion){
        try{
            String codigo = promocion.getString("promocion_cod");
            LocalDate fechaIni = promocion.getDate("fecha_inicio").toLocalDate();
            LocalDate fechaFin = promocion.getDate("fecha_fin").toLocalDate();
            Integer prioridadAux = promocion.getInteger("prioridad");
            String prioridad = prioridadAux.toString();
            String tipo = promocion.getString("tipo");
            String es_categoria_aux = promocion.getString("es_categoria");
            String codigoTipCat = "";
            
            if (es_categoria_aux.equals("S")){
                codigoTipCat = promocion.getString("categoria_code");
            } else if (es_categoria_aux.equals("N")){
                codigoTipCat = promocion.getString("tipo_cod");
            }
            
            labelNombrePromocion.setText(codigo);
            mostrar_promo(codigo,fechaIni,fechaFin,prioridad,tipo);
            
            if (tipo.equals(Promocion.TIPO.CANTIDAD.name())){
                if (es_categoria_aux.equals("S")){
                    rbPorCategoria.setSelected(true);
                    botonTipo1.setDisable(true);
                } else if (es_categoria_aux.equals("N")){
                    rbPorTipo.setSelected(true);
                    botonCategoria1.setDisable(true);
                }
                PromocionCantidad promocionC = PromocionCantidad.findFirst("promocion_cod = ? AND promocion_id = ?", codigo,promocion.getId());
                
                Integer compro = promocionC.getInteger("nr_comprar");
                Integer llevo = promocionC.getInteger("nr_obtener");                
                mostrar_promoCantidad(codigoTipCat, compro, llevo);
                

            } else if (tipo.equals(Promocion.TIPO.BONIFICACIÓN.name())){
                PromocionBonificacion promocionB = PromocionBonificacion.findFirst("promocion_cod = ? AND promocion_id = ?", codigo,promocion.getId());
                
                Integer compro = promocionB.getInteger("nr_comprar");
                Integer llevo = promocionB.getInteger("nr_obtener");

                String es_categoria_comprar_aux = promocionB.getString("es_categoria_comprar");
                String codigo_aux = "";
                
                if (es_categoria_comprar_aux.equals("S")){
                    codigo_aux = promocionB.getString("categoria_code");
                } else if (es_categoria_comprar_aux.equals("N")){
                    codigo_aux = promocionB.getString("tipo_code");
                }
                
                mostrar_promoBonificacion(compro,codigoTipCat,llevo,codigo_aux);
                
            } else if (tipo.equals(Promocion.TIPO.PORCENTAJE.name())) {

                if (es_categoria_aux.equals("S")){
                    rbPorCategoria.setSelected(true);
                    botonTipo3.setDisable(true);
                    botonCategoria3.setDisable(false);
                } else if (es_categoria_aux.equals("N")){
                    rbPorTipo.setSelected(true);
                    botonTipo3.setDisable(false);
                    botonCategoria3.setDisable(true);
                }
                PromocionPorcentaje promocionP = PromocionPorcentaje.findFirst("promocion_cod = ? AND promocion_id = ?", codigo,promocion.getId());
                
                Integer valor = promocionP.getInteger("valor_desc");
                String concepto = promocionP.getString("concepto_desc");
                
                mostrar_promoPorcentaje(codigoTipCat,valor,concepto);
            }
            
        } catch (Exception e){
            infoController.show("El Usuario contiene errores : " + e);
        }
    }
    
    //Botón guardar
    @Override
    public void guardar() {
        if (crear_nuevo){
            crear_promocion();
        }else{
            if (promocion_seleccionada == null) return;
            editar_promocion(promocion_seleccionada);
        }
        promociones = Promocion.findAll();
        cargar_tabla_index();
    }   
    
    private void editar_promocion(Promocion promocion_aux){
        try{
            Base.openTransaction();  
            //Obteniendo valores para Objeto Promoción         
            String codigoPromo = txtFieCodigoPromo.getText();
            String fechaIni = formatoFecha(dpFecha1.getValue(),"dd/MM/yyyy"); 
            String fechaFin = formatoFecha(dpFecha2.getValue(),"dd/MM/yyyy");
            String prioridad = obtener_prioridad();
            String es_categoria = obtener_clasificacion();
            String estado = Promocion.ESTADO.ACTIVO.name();
            String tipoPromo = obtener_tipo_promo();


            es_categoria_comprar =((tipoPromo.toUpperCase()).equals(Promocion.TIPO.CANTIDAD.name()) && rbPorCategoria.isSelected()) ? true : es_categoria_comprar;
            String flag_categoria = (es_categoria_comprar) ? "S":"N";

            String codigo = "";
            codigo = ((tipoPromo.toUpperCase()).equals(Promocion.TIPO.PORCENTAJE.name())) ? txtFieCodigoProducto3.getText():txtFieCodigoProducto2.getText();    
            System.err.println(codigo);

            String id = "";               
            if (es_tipo){
                TipoProducto tipo = new TipoProducto();
                tipo = TipoProducto.findFirst("tipo_cod = ?", codigo);
                id = tipo.getString("tipo_id");
            } else{
                CategoriaProducto categoria = new CategoriaProducto();
                categoria = CategoriaProducto.findFirst("categoria_code = ?", codigo);
                id = categoria.getString("categoria_id");
            }           

            if(!confirmatonController.show("Se editará la promoción con código: " + codigoPromo, "¿Desea continuar?")) return;

            promocion_seleccionada.asignar_atributos(codigoPromo, fechaIni, fechaFin, prioridad, es_categoria, estado, tipoPromo, codigo,id);                                  
            promocion_seleccionada.saveIt();      

            //llenando tablas hijas de promocion
            modificarTablasHijas(tipoPromo,flag_categoria);

            infoController = new InformationAlertController();
            infoController.show("La promoción ha sido modificada satisfactoriamente");

            Base.commitTransaction();
            limpiar_formulario();
        } catch (Exception e){
           infoController.show("La promoción contiene errores : " + e);        
           Base.rollbackTransaction();           
        }
    }
        
    // Botón nuevo
    @Override
    public void nuevo(){
       crear_nuevo = true;
       habilitar_formulario();       
       limpiar_formulario();       
    }
    
    public void crear_promocion(){
        try{
            Base.openTransaction();  
            //Obteniendo valores para Objeto Promoción
            promocion_seleccionada = new Promocion();            
            String codigoPromo = txtFieCodigoPromo.getText();
            String fechaIni = formatoFecha(dpFecha1.getValue(),"dd/MM/yyyy"); 
            String fechaFin = formatoFecha(dpFecha2.getValue(),"dd/MM/yyyy");
            String prioridad = obtener_prioridad();
            String es_categoria = obtener_clasificacion();
            String estado = Promocion.ESTADO.ACTIVO.name();
            String tipoPromo = obtener_tipo_promo();
            

            es_categoria_comprar =(tipoPromo.equals(Promocion.TIPO.CANTIDAD.name()) && !es_tipo) ? true : es_categoria_comprar;
            String flag_categoria = (es_categoria_comprar) ? "S":"N";
            
            String codigo = "";
            codigo = ((tipoPromo.toUpperCase()).equals(Promocion.TIPO.PORCENTAJE.name())) ? txtFieCodigoProducto3.getText():txtFieCodigoProducto1.getText();    
            System.out.println(codigo);
            
            String id = "";               
            if (es_tipo){
                TipoProducto tipo = new TipoProducto();
                tipo = TipoProducto.findFirst("tipo_cod = ?", codigo);
                id = tipo.getString("tipo_id");
            } else{
                CategoriaProducto categoria = new CategoriaProducto();
                categoria = CategoriaProducto.findFirst("categoria_code = ?", codigo);
                id = categoria.getString("categoria_id");
            }           
            
            if(!confirmatonController.show("Se creará la promoción con código: " + codigoPromo, "¿Desea continuar?")) return;
            
            promocion_seleccionada.asignar_atributos(codigoPromo, fechaIni, fechaFin, prioridad, es_categoria, estado, tipoPromo, codigo,id);                                  
            promocion_seleccionada.saveIt();      
                   
            //llenando tablas hijas de promocion
            llenarTablasHijas(tipoPromo,flag_categoria);
            
            infoController = new InformationAlertController();
            infoController.show("La promoción ha sido creado satisfactoriamente");
                        
            Base.commitTransaction();
            limpiar_formulario(); 
        }
        catch(Exception e){
           infoController.show("La promoción contiene errores : " + e);        
           Base.rollbackTransaction();           
        }    
    }
    
    private void modificarTablasHijas(String tipoPromo, String flag_categoria){
        if ((tipoPromo.toUpperCase()).equals(Promocion.TIPO.BONIFICACIÓN.name())){

            String id_comprar = "";
            if (es_categoria_comprar){
                CategoriaProducto categoria = new CategoriaProducto();
                categoria = CategoriaProducto.findFirst("categoria_code = ?", txtFieCodigoProducto2.getText());
                id_comprar = categoria.getString("categoria_id");
            } else{
                TipoProducto tipo = new TipoProducto();
                tipo = TipoProducto.findFirst("tipo_cod = ?", txtFieCodigoProducto2.getText());
                id_comprar = tipo.getString("tipo_id");
            }                               
            if (flag_categoria.equals("S")){
                PromocionBonificacion promoB = PromocionBonificacion.findFirst("promocion_id = ? AND  promocion_cod = ?",promocion_seleccionada.getId(),promocion_seleccionada.get("promocion_cod"));
                promoB.set("nr_comprar", (Integer)spCompro.getValue());
                promoB.set("nr_obtener",(Integer)spLlevo.getValue());
                promoB.set("es_categoria_comprar", flag_categoria);
                promoB.set("categoria_code", txtFieCodigoProducto1.getText());
                promoB.set("categoria_id" ,Integer.parseInt(id_comprar));
                promoB.saveIt();
            }
            else{
                PromocionBonificacion promoB = PromocionBonificacion.findFirst("promocion_id = ? AND  promocion_cod = ?",promocion_seleccionada.getId(),promocion_seleccionada.get("promocion_cod"));
                promoB.set("nr_comprar", (Integer)spCompro.getValue());
                promoB.set("nr_obtener",(Integer)spLlevo.getValue());
                promoB.set("es_categoria_comprar", flag_categoria);
                promoB.set("tipo_cod", txtFieCodigoProducto1.getText());
                promoB.set("tipo_id" ,Integer.parseInt(id_comprar));
                promoB.saveIt();
            }
        } else if ((tipoPromo.toUpperCase()).equals(Promocion.TIPO.CANTIDAD.name())){
            PromocionCantidad promoC = PromocionCantidad.findFirst("promocion_id = ? AND  promocion_cod = ?",promocion_seleccionada.getId(),promocion_seleccionada.get("promocion_cod"));
            promoC.set("nr_comprar", (Integer)spCompro.getValue());
            promoC.set("nr_obtener",(Integer)spLlevo.getValue());
        } else {
            String concepto = (desc_concepto.getText() == null) ? "":desc_concepto.getText();                                
            
            PromocionPorcentaje promoP = PromocionPorcentaje.findFirst("promocion_id = ? AND  promocion_cod = ?",promocion_seleccionada.getId(),promocion_seleccionada.get("promocion_cod"));
            promoP.set("valor_desc", (Integer)spPorc.getValue());
            promoP.set("concepto_desc",concepto);
        }
    }
    
    private void llenarTablasHijas(String tipoPromo, String flag_categoria){
        if ((tipoPromo.toUpperCase()).equals(Promocion.TIPO.BONIFICACIÓN.name())){
            String id_comprar = "";
            if (es_categoria_comprar){
                CategoriaProducto categoria = new CategoriaProducto();
                categoria = CategoriaProducto.findFirst("categoria_code = ?", txtFieCodigoProducto2.getText());
                id_comprar = categoria.getString("categoria_id");
            } else{
                TipoProducto tipo = new TipoProducto();
                tipo = TipoProducto.findFirst("tipo_cod = ?", txtFieCodigoProducto2.getText());
                id_comprar = tipo.getString("tipo_id");
            }                               
            if (flag_categoria.equals("S"))
                PromocionBonificacion.createIt("promocion_id", promocion_seleccionada.getId()
                                        , "promocion_cod", promocion_seleccionada.get("promocion_cod")
                                        , "nr_comprar", (Integer)spCompro.getValue()
                                        , "nr_obtener",(Integer)spLlevo.getValue()
                                        , "es_categoria_comprar", flag_categoria
                                        , "categoria_code", txtFieCodigoProducto1.getText()
                                        , "categoria_id" ,Integer.parseInt(id_comprar) 
                                        );
            else
                PromocionBonificacion.createIt("promocion_id", promocion_seleccionada.getId()
                                        , "promocion_cod", promocion_seleccionada.get("promocion_cod")
                                        , "nr_comprar", (Integer)spCompro.getValue()
                                        , "nr_obtener",(Integer)spLlevo.getValue()
                                        , "es_categoria_comprar", flag_categoria
                                        , "tipo_cod", txtFieCodigoProducto1.getText()
                                        , "tipo_id" ,Integer.parseInt(id_comprar) 
                                        );                
        } else if ((tipoPromo.toUpperCase()).equals(Promocion.TIPO.CANTIDAD.name())){
            PromocionCantidad.createIt("promocion_id", promocion_seleccionada.getId()
                                        , "promocion_cod", promocion_seleccionada.get("promocion_cod")
                                        , "nr_comprar", (Integer)spCompro.getValue()
                                        , "nr_obtener",(Integer)spLlevo.getValue()
                                        );
        } else {
            String concepto = (desc_concepto.getText() == null) ? "":desc_concepto.getText();                                
            PromocionPorcentaje.createIt("promocion_id", promocion_seleccionada.getId()
                                        , "promocion_cod", promocion_seleccionada.get("promocion_cod")
                                        , "valor_desc", (Integer)spPorc.getValue()
                                        , "concepto_desc",concepto
                                        );
        }
    }
    
    
    public void habilitar_formulario(){
        promo_formulario.setDisable(false);
    }
    
    public void limpiar_formulario(){
        txtFieCodigoPromo.clear();
        
        rbPorTipo.setSelected(false);        
        rbPorCategoria.setSelected(false);
        
        txtFieCodigoProducto1.clear();
        txtFieCodigoProducto1.setDisable(false);
        
        txtFieCodigoProducto2.clear();
        txtFieCodigoProducto2.setDisable(false);
        
        txtFieCodigoProducto3.clear();
        txtFieCodigoProducto3.setDisable(false);
                
        SpinnerValueFactory spComproValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0,1);
        spCompro.setValueFactory(spComproValues);
        spCompro.setDisable(true);
        
        SpinnerValueFactory spLlevoValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0,1);
        spLlevo.setValueFactory(spLlevoValues);
        spLlevo.setDisable(true);
        
        SpinnerValueFactory spPorcValues = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0,1);
        spPorc.setValueFactory(spPorcValues);
        spPorc.setDisable(true);
        
        comboBoxTipoPromo.getItems().clear();
        comboBoxTipoPromo.getItems().addAll(Promocion.TIPO.CANTIDAD.name()
                                        , Promocion.TIPO.BONIFICACIÓN.name()
                                        ,Promocion.TIPO.PORCENTAJE.name()
                                        );
        
        comboxPrioridad.getItems().clear();
        comboxPrioridad.getItems().addAll("1", "2","3");
        
        dpFecha1.getEditor().clear();
        dpFecha2.getEditor().clear();
        
        rbPorTipo.setDisable(true);
        rbPorCategoria.setDisable(true);
        
        txtFieCodigoProducto1.setDisable(true);
        txtFieCodigoProducto2.setDisable(true);
        txtFieCodigoProducto3.setDisable(true);
        
        botonTipo1.setDisable(true);
        botonTipo2.setDisable(true);
        botonTipo3.setDisable(true);
        
        botonCategoria1.setDisable(true);
        botonCategoria2.setDisable(true);
        botonCategoria3.setDisable(true);
        
        desc_concepto.setDisable(true);
    }
    
    private void manejarrbPorTipo(){
        String tipoPromo = obtener_tipo_promo();
        rbPorCategoria.setSelected(false);
        if ((tipoPromo.toUpperCase()).equals(Promocion.TIPO.PORCENTAJE.name())){
            botonCategoria3.setDisable(true);
            botonTipo3.setDisable(false);      
            txtFieCodigoProducto3.clear();
        } else {
            botonCategoria1.setDisable(true);
            botonTipo1.setDisable(false);
            if(!tipoPromo.equals(Promocion.TIPO.CANTIDAD.name()))
            {
                botonTipo2.setDisable(false);   
                txtFieCodigoProducto2.clear();
            }
            txtFieCodigoProducto1.clear();
        }
    }
    
    private void manejarrbPorCategoria(){
        String tipoPromo = obtener_tipo_promo();
        rbPorTipo.setSelected(false);
        if ((tipoPromo.toUpperCase()).equals(Promocion.TIPO.PORCENTAJE.name())){
            botonTipo3.setDisable(true);        
            botonCategoria3.setDisable(false);
            txtFieCodigoProducto3.clear();
        } else {
            botonTipo1.setDisable(true);
            botonCategoria1.setDisable(false);
            if(!tipoPromo.equals(Promocion.TIPO.CANTIDAD.name()))
            {
                botonCategoria2.setDisable(false);  
                txtFieCodigoProducto2.clear();                
            }
            txtFieCodigoProducto1.clear();
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
        
        desc_concepto.clear();
        desc_concepto.setDisable(true);
    }
    
    private void habilitar_Porcentaje(){
        spPorc.setDisable(false);
        txtFieCodigoProducto3.setDisable(false);   
        desc_concepto.setDisable(false);
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
        if ((tipoPromo.toUpperCase()).equals(Promocion.TIPO.CANTIDAD.name())){
            habilitar_CantBoni();
            deshabilitar_Porcentaje(); 
            rbPorTipo.setDisable(false);
            rbPorCategoria.setDisable(false);  
            botonTipo1.setDisable(true);
            botonCategoria1.setDisable(true);
            botonTipo2.setDisable(true);
            botonCategoria2.setDisable(true);
        } else if ((tipoPromo.toUpperCase()).equals(Promocion.TIPO.BONIFICACIÓN.name())){
            //rbPorTipo.setDisable(false);
            //rbPorCategoria.setDisable(false); 
            habilitar_CantBoni();
            deshabilitar_Porcentaje();
            limpiarPorCantidad();
        } else if ((tipoPromo.toUpperCase()).equals(Promocion.TIPO.PORCENTAJE.name())){
            rbPorTipo.setDisable(false);
            rbPorCategoria.setDisable(false);
            habilitar_Porcentaje();
            deshabilitar_CantBoni();
        }
    }
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Promociones;
    }
}