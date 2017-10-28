/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Promocion;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.scene.layout.AnchorPane;
import org.javalite.activejdbc.Base;

/**
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
    private TextField txtFieCodigoProducto3;
    @FXML
    private Button botonTipo3;
    @FXML
    private Button botonCategoria3;
    @FXML
    private TextField txtFiePrecioUnitario;    
      

    private Boolean crear_nuevo;
    private List<Promocion> promociones;    
    private final ObservableList<Promocion> masterData = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        inhabilitar_formulario();
        promociones = null;
        promociones = Promocion.findAll();
        cargar_tabla_index();
    }    
    
    //Tabla de promociones
    public void limpiar_tabla_index(){
        tabla_promociones.getItems().clear();
    }
    
    public void cargar_tabla_index(){
        limpiar_tabla_index();
        for( Promocion promocion : promociones){
            masterData.add(promocion);
            //System.out.println(promocion);
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
    
    public boolean cumple_condicion_busqueda(Promocion promocion, String codigo, String tipo
            //, String fecha
            ){
        boolean match = true;
        if ( codigo.equals("") && tipo.equals("") 
                //&& fecha.equals("")
                ){
            match = false;
        }
        else {
            match = (!codigo.equals("")) ? (match && (promocion.get("promocion_cod")).equals(codigo)) : true;
            match = (!tipo.equals("")) ? (match && (promocion.get("tipo")).equals(tipo)) : true;
            //match = (!fecha.equals("")) ? (match && (promocion.get("tipo_cliente")).equals(fecha)) : true;
        }
        return match;
    }
    @FXML
    public void buscar_promocion(ActionEvent event) throws IOException{
        promociones = Promocion.findAll();
        masterData.clear();
        //LocalDate fecha = ( busqFecha.getValue() == null ) ? "" : busqFecha.getValue();
        try{
            for(Promocion promocion : promociones){
                if (cumple_condicion_busqueda(promocion, busqCodigoPromo.getText(), busqTipoPromo.getText()
                //        , fecha
                )){
                    masterData.add(promocion);
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
        
    // Botón nuevo
    public void nuevo(){
       crear_nuevo = true;
       habilitar_formulario();
       limpiar_formulario();
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
    }
    
    
}
