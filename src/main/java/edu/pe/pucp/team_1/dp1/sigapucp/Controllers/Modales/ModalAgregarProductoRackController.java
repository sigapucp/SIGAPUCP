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
import java.time.format.DateTimeFormatter;
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
    @FXML private ComboBox<String> producto_tipo;
    @FXML private DatePicker producto_fecha;
    @FXML private ComboBox<String> producto_tipo_posicion;
    @FXML private ComboBox<String> producto_rack_cord_x;
    @FXML private ComboBox<String> producto_rack_cord_y;
    @FXML private ComboBox<String> producto_rack_cord_z;

    public ModalAgregarProductoRackController(Rack rack, Almacen almacen, List<AlmacenAreaZ> almacenesZ_existentes, ObservableList<Producto> productos_rack_existentes) {
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
                                .filter(x-> (fila > 0 && x.equals(AlmacenAreaXY.POSICION.ADELANTE.name())) || (fila < numFilas && x.equals(AlmacenAreaXY.POSICION.ATRAS.name())) )
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
                                .filter(x-> (columna > 0 && x.equals(AlmacenAreaXY.POSICION.IZQUIERDA.name())) || (columna < numColumnas && x.equals(AlmacenAreaXY.POSICION.DERECHA.name())) )
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
        double capacidadRestante = almacenZ.getDouble("capacidad_restante");
        double pesoProducto = TipoProducto.where("tipo_id = ?", producto.getInteger("tipo_id")).get(0).getDouble("peso");

        if(capacidadRestante > 0 && capacidadRestante >= pesoProducto) {
            almacenZ.setDouble("capacidad_restante", capacidadRestante - pesoProducto);
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
            producto_seleccionado.set("posicion_rack",rack_activo.getString("tipo").equals(Rack.TIPO.HORIZONTAL.name()) ? 
                    producto_rack_cord_x.getSelectionModel().selectedItemProperty().getValue() : producto_rack_cord_y.getSelectionModel().getSelectedItem());
            args.setProducto(producto_seleccionado);
            args.setAlmacenZ(almacenZ_seleccionado);

           agregarProductoRackEvent.fire(this, args);
           infoController.show("Se agrego correctamente un producto en el rack");
           getCurrentStage().close();
        } else {
            if(producto_seleccionado!= null&&  validator.validate(validationRules, producto_tipo_posicion, producto_rack_cord_x, producto_rack_cord_y, producto_rack_cord_z)&&!revisarCapacidadRack(producto_seleccionado))
            {
                warningController.show("No hay espacio suficiente para ingresar el producto seleccionado","");
                
            }else{
                
            }
            
        }
    }

    @FXML
    public void buscarProducto(ActionEvent event) {
        try {
            String productoCod = producto_cod.getText();
            String productoTipo = String.valueOf(producto_tipo.getSelectionModel().getSelectedItem() == null ? "" : producto_tipo.getSelectionModel().getSelectedItem());
            String productoFechaAdquisicion = producto_fecha.getValue() == null ? "" : producto_fecha.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            List<Producto> productos_filtrados = new ArrayList<>();
            LazyList<Producto> productos_existentes = Producto.findAll();

            productos_filtrados = productos_existentes.stream().filter(producto -> {
                boolean cond = true;
                TipoProducto tipo = TipoProducto.findFirst("tipod_cod = ?", producto.getString("tipo_cod"));

                // Validacion que el producto no ha sido colocado en un rack
                cond = cond && producto.getString("ubicado").equals("N");
                // Validacion de Codigo de Producto
                cond = cond && producto.getString("producto_cod").contains(productoCod);
                // Validacion de Fecha de Adquisicion del Producto
                cond = cond && producto.getDate("fecha_adquisicion").toString().equals(productoFechaAdquisicion);
                // Validacion de Nombre de Producto
                cond = cond && tipo.getString("nombre").contains(productoTipo);
     
                return cond;
            }).collect(Collectors.toList());

            productos.clear();
            productos_filtrados.forEach(productos::add);
            tabla_productos.setItems(productos);
        } catch(Exception e) {
            Logger.getLogger(ModalAgregarProductoRackController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    public void cerrarModal(ActionEvent event) {
        getCurrentStage().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Campos de Busqueda
            ObservableList<String> tipos_productos = FXCollections.observableArrayList();
            tipos_productos.addAll(
                TipoProducto.findAll().stream().map(tipo -> tipo.getString("nombre")).collect(Collectors.toList())
            );

            producto_tipo.setItems(tipos_productos);
            // Tabla de Productos
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