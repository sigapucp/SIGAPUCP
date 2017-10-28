/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Promocion;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
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
    @FXML
    private TableView<Promocion> tabla_promociones;
    @FXML
    private TableColumn<Promocion, String> columna_codigo;
    @FXML
    private TableColumn<Promocion, String> columna_tipo;
    
    
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
    public void cargar_tabla_index(){
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
    }
    
    
}
