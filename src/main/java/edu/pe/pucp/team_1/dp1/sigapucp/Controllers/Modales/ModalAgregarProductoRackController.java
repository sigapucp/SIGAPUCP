package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Modales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.WarningAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Almacen;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.AlmacenAreaXY;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.AlmacenAreaZ;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.OrdenEntrada;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Rack;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Stock;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.abrirModalPromoArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoRackArgs;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
    private Rack rack_activo;
    private Almacen almacen_relacionado;
    private int numFilas;
    private int numColumnas;
    private ObservableList<Producto> productos;
    private Producto producto_seleccionado;
    private AlmacenAreaXY almacenXY_seleccionado;
    private AlmacenAreaZ almacenZ_seleccionado;
    private IEvent<agregarProductoRackArgs> agregarProductoRackEvent;
    private WarningAlertController warningController;
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmatonController;
    private HashMap<String, String> validationRules;
    @FXML private TableView<Producto> tabla_productos;
    @FXML private TableColumn<Producto, String> tabla_producto_cod;
    @FXML private TableColumn<Producto, String> tabla_producto_nomb;
    @FXML private TableColumn<Producto, String> tabla_producto_fecha_adquisicion;
    @FXML private TableColumn<Producto, String> tabla_producto_fecha_vencimiento;
    @FXML private TextField producto_cod;
    @FXML private TextField producto_nomb;
    @FXML private ComboBox<String> producto_tipo;
    @FXML private DatePicker producto_fecha;
    @FXML private ComboBox<String> producto_tipo_posicion;
    @FXML private ComboBox<String> producto_rack_cord_x;
    @FXML private ComboBox<String> producto_rack_cord_y;
    @FXML private ComboBox<String> producto_rack_cord_z;

    public ModalAgregarProductoRackController(Rack rack, Almacen almacen) {
        agregarProductoRackEvent = new Event<>();
        productos = FXCollections.observableArrayList();
        producto_seleccionado = new Producto();
        warningController = new WarningAlertController();
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
        validationRules = new HashMap<>();
        rack_activo = rack;
        almacen_relacionado = almacen;
        numFilas = almacen.getInteger("ancho")/almacen.getDouble("longitud_area").intValue();
        numColumnas = almacen.getInteger("largo")/almacen.getDouble("longitud_area").intValue();
        System.out.println(String.format("El numero de filas y columnas son %d %d", numFilas, numColumnas));
        validationRules.put("producto_tipo_posicion", "notNull,notEmpty");
        validationRules.put("producto_rack_cord_x", "notNull,notEmpty,numeric");
        validationRules.put("producto_rack_cord_y", "notNull,notEmpty,numeric");
        validationRules.put("producto_rack_cord_z", "notNull,notEmpty,numeric");
    }

    private void actualizar_tabla(LazyList<Producto> productosEncontrados) {
        productos.clear();
        productosEncontrados.forEach(productos::add);
        tabla_productos.setItems(productos);
    }

    private void setComboBoxValue(ComboBox comboBox, int start, int end) {
        List<Integer> values = new ArrayList<>();
        for(int i = start; i <= end; i++){
            values.add(i);
        }
        comboBox.getItems().setAll(values);
    }

    private void inicializarComboBoxes() {
        String tipo = rack_activo.getString("tipo");
        ObservableList<String> tiposPos = FXCollections.observableArrayList();
        ObservableList<String> list = FXCollections.observableArrayList();

        setComboBoxValue(producto_rack_cord_z, 1, rack_activo.getInteger("altura"));

        switch(tipo) {
            case "HORIZONTAL":
                int fila = rack_activo.getInteger("y_ancla1");
                list.add(String.valueOf(fila));
                producto_rack_cord_y.setItems(list);
                producto_rack_cord_y.getSelectionModel().select(0);
                producto_rack_cord_y.setEditable(false);
                tiposPos.addAll(Arrays.asList(AlmacenAreaXY.POSICION.values()).stream()
                                .map(x->x.name())
                                .filter(x-> (fila > 0 && x.equals("ADELANTE")) || (fila < numFilas && x.equals("ATRAS")) )
                                .collect(Collectors.toList())
                                );
                producto_tipo_posicion.setItems(tiposPos);
                setComboBoxValue(producto_rack_cord_x, rack_activo.getInteger("x_ancla1"), rack_activo.getInteger("x_ancla2"));
                break;
            case "VERTICAL":
                int columna = rack_activo.getInteger("x_ancla1");
                list.add(String.valueOf(columna));
                producto_rack_cord_x.setItems(list);
                producto_rack_cord_y.getSelectionModel().select(0);
                producto_rack_cord_x.setEditable(false);
                tiposPos.addAll(Arrays.asList(AlmacenAreaXY.POSICION.values()).stream()
                                .map(x->x.name())
                                .filter(x-> (columna > 0 && x.equals("IZQUIERDA")) || (columna < numColumnas && x.equals("DERECHA")) )
                                .collect(Collectors.toList())
                                );
                producto_tipo_posicion.setItems(tiposPos);
                setComboBoxValue(producto_rack_cord_y, rack_activo.getInteger("y_ancla1"), rack_activo.getInteger("y_ancla2"));
                break;
            default:
                break;
        }
    }

    private boolean revisarCapacidadRack(Producto producto) {
        boolean condition = false;
        
        int producto_cod_x = Integer.valueOf(String.valueOf(producto_rack_cord_x.getSelectionModel().getSelectedItem()));
        int producto_cod_y = Integer.valueOf(String.valueOf(producto_rack_cord_y.getSelectionModel().getSelectedItem()));
        int producto_cod_z = Integer.valueOf(String.valueOf(producto_rack_cord_z.getSelectionModel().getSelectedItem())) - 1;
        AlmacenAreaXY almacenXY = (AlmacenAreaXY) AlmacenAreaXY.where("rack_cod = ? and rack_id = ? and x = ? and y = ?",
                                                            rack_activo.getString("rack_cod"), 
                                                            rack_activo.getId(), 
                                                            producto_cod_x, 
                                                            producto_cod_y).get(0);
        AlmacenAreaZ almacenZ = (AlmacenAreaZ) AlmacenAreaZ.where("almacen_xy_id = ? and level = ?", almacenXY.getId(), producto_cod_z).get(0);
        double capacidadRestante = almacenZ.getDouble("capacidadRestante");
        double pesoProducto = TipoProducto.where("tipo_id = ?", producto.getInteger("tipo_id")).get(0).getDouble("peso");

        if(capacidadRestante > 0 && capacidadRestante > pesoProducto) {
            almacenZ.setDouble("capacidadRestante", capacidadRestante - pesoProducto);
            almacenXY_seleccionado = almacenXY;
            almacenZ_seleccionado = almacenZ;
            condition = true;
        }

        return condition;
    }

    @Override
    public Event getCloseModalEvent() {
        return (Event) agregarProductoRackEvent;
    }

    @FXML
    public void agregarProducto(ActionEvent event) {
        producto_seleccionado = tabla_productos.getSelectionModel().getSelectedItem();

        if (producto_seleccionado != null && 
            validator.validate(validationRules, producto_tipo_posicion, producto_rack_cord_x, producto_rack_cord_y, producto_rack_cord_z) &&
            revisarCapacidadRack(producto_seleccionado))
        {
            agregarProductoRackArgs args = new agregarProductoRackArgs();
            producto_seleccionado.set("tipo_posicion", String.valueOf(producto_tipo_posicion.getSelectionModel().getSelectedItem()));
            producto_seleccionado.set("ubicado", "S");
            producto_seleccionado.set("almacen_z_id", almacenZ_seleccionado.getInteger("almacen_z_id"));
            producto_seleccionado.set("almacen_xy_id", almacenXY_seleccionado.getInteger("almacen_xy_id"));
            producto_seleccionado.set("rack_id", rack_activo.getInteger("rack_id"));
            producto_seleccionado.set("rack_cod", rack_activo.getString("rack_cod"));
            producto_seleccionado.set("almacen_id", almacen_relacionado.getInteger("almacen_id"));
            producto_seleccionado.set("almacen_cod", almacen_relacionado.getString("almacen_cod"));
            args.setProducto(producto_seleccionado);
            args.setAlmacenZ(almacenZ_seleccionado);

           agregarProductoRackEvent.fire(this, args);
           infoController.show("Se agrego correctamente un producto en el rack");
           getCurrentStage().close();
        } else {
            warningController.show("Error al agregar un rack", "Es necesario que ingrese valores validos en los campos obligatorios");
        }
    }

    @FXML
    public void buscarProducto(ActionEvent event) {
        String productoCod = producto_cod.getText();
        String productoNomb = producto_nomb.getText();
        String productoTipo = producto_tipo.getSelectionModel().getSelectedItem();
        String productoFechaAdquisicion = producto_fecha.getPromptText();
        HashMap<String, String> campos = new HashMap<>();
        // campos.put(String.valueOf(producto_cod.getId()), "producto_cod");
        // campos.put(String.valueOf(producto_cod.getId()), "producto_cod");
        // campos.put(String.valueOf(producto_cod.getId()), "producto_cod");
        // campos.put(String.valueOf(producto_cod.getId()), "producto_cod");
        // String query = generarQueryBusqueda(productoCod, productoNomb, productoTipo, productoFechaAdquisicion);
        // actualizar_tabla(Producto.find(campos, query));
    }

    @FXML
    public void cerrarModal(ActionEvent event) {
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
                    TipoProducto.findById(p.getValue().getInteger("tipo_id")).getString("nombre")
                )
            );

            LazyList<Producto> productoEncontrados = Producto.find("ubicado = ?", 'N');
            inicializarComboBoxes();
            actualizar_tabla(productoEncontrados);
        } catch(Exception e) {
            Logger.getLogger(ModalAgregarProductoRackController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}