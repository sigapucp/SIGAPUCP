package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Modales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.WarningAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Stock;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.abrirModalPromoArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoRackArgs;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

public class ModalAgregarProductoRackController extends ModalController {
    private ObservableList<Producto> productos;
    private Producto producto_seleccionado;
    private IEvent<agregarProductoRackArgs> agregarProductoRackEvent;
    private WarningAlertController warningController;
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmatonController;
    @FXML private TableView<Producto> tabla_productos;
    @FXML private TableColumn<Producto, String> tabla_producto_cod;
    @FXML private TableColumn<Producto, String> tabla_producto_nomb;
    @FXML private TableColumn<Producto, String> tabla_producto_fecha_adquisicion;
    @FXML private TableColumn<Producto, String> tabla_producto_fecha_vencimiento;
    @FXML private TableColumn<Producto, String> tabla_producto_cantidad;
    @FXML private TextField producto_cod;
    @FXML private TextField producto_nomb;
    @FXML private ComboBox<String> producto_tipo;
    @FXML private DatePicker producto_fecha;
    @FXML private TextField producto_cantidad_agregar;
    @FXML private ComboBox<String> producto_tipo_ingreso;

    public ModalAgregarProductoRackController() {
        agregarProductoRackEvent = new Event<>();
        productos = FXCollections.observableArrayList();
        producto_seleccionado = new Producto();
        warningController = new WarningAlertController();
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
    }

    private void actualizar_tabla(LazyList<Producto> productosEncontrados) {
        productos.clear();
        productosEncontrados.forEach(productos::add);
        tabla_productos.setItems(productos);
    }

    public IEvent<agregarProductoRackArgs> agregarProductoRackEvent() {
        return agregarProductoRackEvent;
    }

    @FXML
    void agregarProducto(ActionEvent event) {
        String productoCod = producto_cod.getText();
        String productoNomb = producto_nomb.getText();
        String productoTipo = producto_tipo.getSelectionModel().getSelectedItem();
        String productoFechaAdquisicion = producto_fecha.getPromptText();
        String productoCantidadAgregar = producto_cantidad_agregar.getText();
        String productoTipoIngreso = producto_tipo_ingreso.getSelectionModel().getSelectedItem();
        
        // if(validarCamposObligatorios(productoCod, productoNomb, productoTipo, productoFechaAdquisicion, productoCantidadAgregar, productoTipoIngreso)) {
        //     agregarProductoRackArgs args = new agregarProductoRackArgs();
            
        //     args.setProducto(producto_seleccionado);
        //     args.setCantidadIngresada(Integer.valueOf(productoCantidadAgregar));
        //     args.setTipoIngreso(productoTipoIngreso);

        //     agregarProductoRackEvent.fire(this, args);
        // } else {
        //     warningController.show("Error al agregar un rack", "Es necesario que ingrese valores validos en los campos obligatorios");
        // }
    }

    @FXML
    void buscarProducto(ActionEvent event) {
        String productoCod = producto_cod.getText();
        String productoNomb = producto_nomb.getText();
        String productoTipo = producto_tipo.getSelectionModel().getSelectedItem();
        String productoFechaAdquisicion = producto_fecha.getPromptText();
    }

    @FXML
    void cerrarModal(ActionEvent event) {
        getCurrentStage().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            tabla_producto_cod.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("producto_cod")) );
            tabla_producto_fecha_adquisicion.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("fecha_adquirida")) );
            tabla_producto_fecha_vencimiento.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("fecha_caducidad")) );
            tabla_producto_nomb.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) ->
                new ReadOnlyObjectWrapper(
                    TipoProducto.findById(p.getValue().getId()).getString("nombre")
                )
            );
            tabla_producto_cantidad.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) ->
                new ReadOnlyObjectWrapper(
                    Stock.findByCompositeKeys(p.getValue().getId(), p.getValue().get("tipo_cod")).getInteger("stock_fisico")
                )
            );
            LazyList<Producto> productoEncontrados = Producto.findAll();
            actualizar_tabla(productoEncontrados);
        } catch(Exception e) {
            Logger.getLogger(ModalAgregarProductoRackController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}