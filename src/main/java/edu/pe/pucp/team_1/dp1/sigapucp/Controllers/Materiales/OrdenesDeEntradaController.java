/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Materiales;

import com.sun.javafx.font.FontConstants;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.AgregarProductosController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas.PedidosController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEventHandler;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntrada;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntradaxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Cliente;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Proveedor;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoArgs;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Jauma
 */
public class OrdenesDeEntradaController extends Controller {

    @FXML
    private AnchorPane usuario_container;
    @FXML
    private TextField BusquedaCodigo;
    @FXML
    private ComboBox<String> BusquedaEstado;
    @FXML
    private ComboBox<String> BusquedaTipo;
    @FXML
    private TableView<OrdenEntrada> TablaOrdenes;
    @FXML
    private TableColumn<OrdenEntrada, String> ColumnaCodigo;
    @FXML
    private TableColumn<OrdenEntrada, String> ColumnaFecha;
    @FXML
    private TableColumn<OrdenEntrada, String> ColumnaTipo;
    @FXML
    private TableColumn<OrdenEntrada, String> ColumnaEstado;
    @FXML
    private TitledPane TitlePane;
    @FXML
    private Label ProveedorLabel;
    @FXML
    private TextField ClienteBuscar;
    @FXML
    private ComboBox<String> VerTipo;
    @FXML
    private DatePicker VerFecha;
    @FXML
    private Label ClienteLabel;
    @FXML
    private TextField ProveedorBuscar;
    @FXML
    private TextArea VerDescripcion;
    @FXML
    private TextField VerProducto;
    @FXML
    private Spinner<Integer> verCantidad;
    private SpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10000, 1);
    
    @FXML
    private TableView<OrdenEntradaxProducto> TablaProductos;
    @FXML
    private TableColumn<OrdenEntradaxProducto, String> ColumnaProductoCodigo;
    @FXML
    private TableColumn<OrdenEntradaxProducto, String> ColumnaProductoNombre;
    @FXML
    private TableColumn<OrdenEntradaxProducto, String> ColumnaProductoCantidad;
    @FXML
    private TableColumn<OrdenEntradaxProducto, String> ColumnaProductoDescripcion;
    @FXML
    private AnchorPane BusquedaFecha;
    @FXML 
    static Stage modal_stage = new Stage();
    
    
    private final ObservableList<OrdenEntrada> entradas = FXCollections.observableArrayList();
    private final ObservableList<OrdenEntradaxProducto> productos = FXCollections.observableArrayList();   
       
    private OrdenEntrada entradaSelecionada;
    private Boolean crearNuevo;
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmationController;
    
    private List<Cliente> autoCompletadoClienteList;    
    private List<Proveedor> autoCompletadoProveedorList;
    private List<TipoProducto> autoCompletadoProductoList;
    ArrayList<String> possiblewordsCliente = new ArrayList<>();    
    ArrayList<String> possiblewordsProveedor = new ArrayList<>();    
    ArrayList<String> possiblewordsProducto = new ArrayList<>();    
    AutoCompletionBinding<String> autoCompletionBindingCliente;
    AutoCompletionBinding<String> autoCompletionBindingProveedor;
    AutoCompletionBinding<String> autoCompletionBindingProducto;
    TipoProducto productoDevuelto = new TipoProducto();
    

    public OrdenesDeEntradaController()
    {
         if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        
        
        List<OrdenEntrada> tempEntradas = OrdenEntrada.findAll();        
        for (OrdenEntrada entrada : tempEntradas) {
            entradas.add(entrada);
        }                    
        
        infoController = new InformationAlertController();
        confirmationController = new ConfirmationAlertController();
        
        crearNuevo = false;
        entradaSelecionada = null;
    }
    
    @FXML
    private void visualizarOrden(ActionEvent event) {        
    }

    @FXML
    private void buscarOrden(ActionEvent event) {
    }

    @FXML
    private void agregarProducto(ActionEvent event) {
    }

    @FXML
    private void eliminarProducto(ActionEvent event) {
        modal_stage.showAndWait();
    }

    /**
     * Initializes the controller class.
     */
    
    private void limpiar_formulario()
    {
        entradas.clear();
        productos.clear();
        VerDescripcion.clear();
        VerProducto.clear();
        valueFactory.setValue(1);
        VerTipo.getSelectionModel().clearSelection();        
    }
         
    @Override
    public void guardar(){
        if (crearNuevo){
//            crearOrden();
            limpiar_formulario();
        }
        else {
            if(entradaSelecionada==null) 
            {
                infoController.show("No ha seleccionado una Entrada Seleccionada");            
                return;
            }
//            editarOrden(entradaSelecionada);
        }    
        RefrescarTabla(OrdenEntrada.findAll());
    }
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.OrdendeEntrada;
    }
    
      private void RefrescarTabla(List<OrdenEntrada> entradaRefresh)
    {        
        try {
            entradas.removeAll(entradas);                 
            if(entradaRefresh == null) return;
            for (OrdenEntrada entrada : entradaRefresh) {
                entradas.add(entrada);
            }               
            TablaOrdenes.getColumns().get(0).setVisible(false);
            TablaOrdenes.getColumns().get(0).setVisible(true);
        } catch (Exception e) {
            infoController.show("La orden de entradada contiene errores : " + e);      
        }                                  
    }
      
    private void handleAutoCompletarCliente() {
        int i = 0;
        for (Cliente cliente : autoCompletadoClienteList){
            if (cliente.getString("nombre").equals(ClienteBuscar.getText())){           
//                telfSh.setText(cliente.getString("telef_contacto"));                
            }
        }
    }
    
    private void handleAutoCompletarProveedor() {
        int i = 0;
        for (Proveedor proveedor : autoCompletadoProveedorList){
            if (proveedor.getString("nombre").equals(ProveedorBuscar.getText())){           
//                telfSh.setText(cliente.getString("telef_contacto"));                
            }
        }
    }
    
    private void handleAutoCompletarProducto() {
        int i = 0;
        for (TipoProducto tipoProducto : autoCompletadoProductoList){
            if (tipoProducto.getString("nombre").equals(VerProducto.getText())){           
//                telfSh.setText(cliente.getString("telef_contacto"));                
            }
        }
    }
    
    private void clienteToString() {
        ArrayList<String> words = new ArrayList<>();
        for (Proveedor cliente : autoCompletadoProveedorList){
            words.add(cliente.getString("nombre"));
        }        
        possiblewordsProveedor = words;
    }
    
    private void proveedorToString() {
        ArrayList<String> words = new ArrayList<>();
        for (Proveedor cliente : autoCompletadoProveedorList){
            words.add(cliente.getString("nombre"));
        }        
        possiblewordsProveedor = words;
    }
     
     private void productoToString() {
        ArrayList<String> words = new ArrayList<>();
        for (TipoProducto cliente : autoCompletadoProductoList){
            words.add(cliente.getString("nombre"));
        }               
        possiblewordsProducto = words;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            ColumnaCodigo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntrada, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("entrada_cod")));
            ColumnaFecha.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntrada, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getDate("fecha_emision").toString()));
            ColumnaEstado.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntrada, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("estado")));
            ColumnaTipo.setCellValueFactory((TableColumn.CellDataFeatures<OrdenEntrada, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("tipo")));
            
            ObservableList<String> estados = FXCollections.observableArrayList();                           
            ObservableList<String> tipos = FXCollections.observableArrayList();                        
            ObservableList<String> tiposNoPad = FXCollections.observableArrayList();            
            
            estados.add("");
            estados.addAll(Arrays.asList(OrdenEntrada.ESTADO.values()).stream().map(x->x.name()).collect(Collectors.toList()));  
            
            tipos.add("");
            tipos.addAll(Arrays.asList(OrdenEntrada.TIPO.values()).stream().map(x->x.name()).collect(Collectors.toList()));  
            
            tiposNoPad.addAll(Arrays.asList(OrdenEntrada.TIPO.values()).stream().map(x->x.name()).collect(Collectors.toList())); 
            
            BusquedaEstado.setItems(estados);
            BusquedaTipo.setItems(tipos);
            
            VerTipo.setItems(tiposNoPad);
            verCantidad.setValueFactory(valueFactory);
            
            VerTipo.valueProperty().addListener(new ChangeListener<String>() {           
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    
                    boolean enter = false;
                    if(newValue.equals(OrdenEntrada.TIPO.Compra.name()))
                    {
                        ProveedorLabel.setVisible(true);
                        ProveedorBuscar.setVisible(true);
                        ClienteLabel.setVisible(false);
                        ClienteBuscar.setVisible(false);
                        enter = true;
                    }                    
                    if(newValue.equals(OrdenEntrada.TIPO.Devolucion.name()))
                    {
                        ProveedorLabel.setVisible(false);
                        ProveedorBuscar.setVisible(false);
                        ClienteLabel.setVisible(true);
                        ClienteBuscar.setVisible(true);
                        enter = true;
                    }                
                    
                    if(!enter)
                    {
                        ProveedorLabel.setVisible(false);
                        ProveedorBuscar.setVisible(false);
                        ClienteLabel.setVisible(false);
                        ClienteBuscar.setVisible(false);                        
                    }
                }
            });
            
            ProveedorLabel.setVisible(false);
            ProveedorBuscar.setVisible(false);
            ClienteLabel.setVisible(false);
            ClienteBuscar.setVisible(false);
            
            autoCompletadoClienteList = Cliente.findAll();
            autoCompletadoProductoList = TipoProducto.findAll();
            autoCompletadoProveedorList = Proveedor.findAll();
            
            clienteToString();
            autoCompletionBindingCliente = TextFields.bindAutoCompletion(ClienteBuscar, possiblewordsCliente);
            
            autoCompletionBindingCliente.addEventHandler(EventType.ROOT, (event) -> {
            handleAutoCompletarCliente();
            });
            
            proveedorToString();
            autoCompletionBindingProveedor = TextFields.bindAutoCompletion(ProveedorBuscar, possiblewordsProveedor);
            
            autoCompletionBindingProveedor.addEventHandler(EventType.ROOT, (event) -> {
            handleAutoCompletarProveedor();
            });
            
            productoToString();
            autoCompletionBindingProducto = TextFields.bindAutoCompletion(VerProducto, possiblewordsProducto);
            
            autoCompletionBindingProducto.addEventHandler(EventType.ROOT, (event) -> {
            handleAutoCompletarProducto();
            });
            
        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AgregarProductos.fxml"));
            AgregarProductosController controller = new AgregarProductosController();
            loader.setController(controller);
                      
            Scene modal_content_scene = new Scene((Parent)loader.load());
            modal_stage.setScene(modal_content_scene);
            modal_stage.initModality(Modality.APPLICATION_MODAL);    
            
            TablaOrdenes.setItems(entradas);
            TablaProductos.setItems(productos);
            
            controller.devolverProductoEvent.addHandler((Object sender, agregarProductoArgs args) -> {
                productoDevuelto = args.producto;
            });
            
            modal_stage.showAndWait();
            
        } catch (Exception e) {
            infoController.show("La orden de entradada contiene errores : " + e);    
        }
    }         

    @FXML
    private void buscarProducto(ActionEvent event) {
    }
}
