/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Despachos;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.NotaDeCredito;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntrada;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntradaxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;

/**
 * FXML Controller class
 *
 * @author Joel
 */
public class NotaDeCreditoController extends Controller {

    @FXML
    private TextField cod_nota_buscar;
    @FXML
    private TableView<?> tabla_notas_cred;
    @FXML
    private TableColumn<?, ?> columna_cliente;
    @FXML
    private TableColumn<?, ?> columna_envio;
    @FXML
    private TableColumn<?, ?> columna_pedido;
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
    private Spinner<?> cantidad_producto;
    @FXML
    private TextField VerProducto;
    @FXML
    private Button buscarProducto;
    @FXML
    private TableView<?> tabla_productos;
    @FXML
    private TableColumn<?, ?> columna_prod_cod;
    @FXML
    private TableColumn<?, ?> columna_prod_nombre;
    @FXML
    private TableColumn<?, ?> columna_prod_desc;
    @FXML
    private TableColumn<?, ?> columna_prod_cant;
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
    private ComboBox<?> ordenes_compra_combobox;
    
        
    
    //LOGICA
        //--------------------------------------------------//
    
    private InformationAlertController infoController;  
    private ConfirmationAlertController confirmatonController;
    private final ObservableList<NotaDeCredito> masterData = FXCollections.observableArrayList();
    private List<NotaDeCredito> notas_credito;
    private Boolean crearNuevo = false;    
    private Cliente cliente_seleccionado = null;

    //AUTOCOMPLETAD
        //--------------------------------------------------//

    private List<Cliente> auto_completado_list_cliente;
    ArrayList<String> posibles_clientes = new ArrayList<>();
    AutoCompletionBinding<String> autoCompletionBinding;   
    
    //MODALES FLUJO
        //--------------------------------------------------//
    Stage modal_stage = new Stage();
    private List<OrdenEntradaxProducto> productos_a_agregar;
    private List<OrdenEntradaxProducto> productos_disponibles;
    private OrdenEntradaxProducto producto_devuelto;
    private OrdenEntrada orden_compra_seleccionada;
    private Boolean orden_compra_nueva;
    private NotaDeCredito nota_seleccionada;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.NotasdeCredito;
    }
    
    @FXML
    private void buscar_nota_credito(ActionEvent event) {
    }

    @FXML
    private void visualizar_nota_credito(ActionEvent event) {
    }

    @FXML
    private void handleModalProducto(ActionEvent event) {
    }

    @FXML
    private void agregar_producto(ActionEvent event) {
    }

    @FXML
    private void eliminar_producto(ActionEvent event) {
    }

    @FXML
    private void agregarCliente(ActionEvent event) {
    }
    
}
