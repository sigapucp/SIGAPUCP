/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Modales.ModalAgregarProductoRackController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Accion;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Usuario;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad.AccionLoggerSingleton;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Rack;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Stock;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Almacen;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.AlmacenAreaXY;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.AlmacenAreaZ;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Modales.ModalController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.WarningAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarProductoRackArgs;
import java.net.URL;
import java.awt.Desktop.Action;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 * FXML Controller class
 *
 * @author Jauma
 */
public class RacksController extends Controller{

    private ObservableList<Rack> racks_busqueda;
    private ObservableList<Producto> productos_rack;
    // Todo lo que se modifica se agregar a Rack temporales para se guardado o actualizado
    private ObservableList<Producto> productos_tmp_racks;
    private List<AlmacenAreaZ> almacenesZ_racks;
    private WarningAlertController warningController;
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmatonController;
    private Rack rack_seleccionado;
    private Almacen almacen_relacionado;
    @FXML private AnchorPane rack_form_container;
    @FXML private TableView<Producto> rack_form_producto_tabla;
    @FXML private TableColumn<Producto, String> rack_form_producto_cod_column;
    @FXML private TableColumn<Producto, String> rack_form_producto_posicion_column;
    @FXML private TableColumn<Producto, String> rack_form_producto_nombre_column;
    @FXML private ComboBox<String> buscar_rack_almacen_nomb;
    @FXML private TextField rack_form_cod_field;
    @FXML private TextField rack_form_almacen_nombre_field;
    @FXML private TextField rack_form_cantidad_productos_field;
    @FXML private TextField rack_form_alto_field;
    @FXML private TextField rack_form_ancho_field;
    @FXML private TextField rack_form_largo_field;
    @FXML private TextField buscar_rack_cod;
    @FXML private TextField buscar_rack_altura;
    @FXML private TextField buscar_rack_ancho;
    @FXML private TextField buscar_rack_largo;
    @FXML private TextField buscar_rack_producto;
    @FXML private TableView<Rack> buscar_rack_tabla;
    @FXML private TableColumn<Rack, String> buscar_columna_codigo_rack;
    

    public RacksController() {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        racks_busqueda = FXCollections.observableArrayList();
        productos_rack = FXCollections.observableArrayList();
        productos_tmp_racks = FXCollections.observableArrayList();
        almacenesZ_racks = new ArrayList<>();
        warningController = new WarningAlertController();
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
    }    

    private void actualizarTablaBusqueda() {
        try {
            LazyList<Rack> rackActuales = Rack.findAll();
            racks_busqueda.clear();
            rackActuales.forEach(racks_busqueda::add);
            buscar_rack_tabla.setItems(racks_busqueda);
        } catch(Exception e) {
            Logger.getLogger(RacksController.class.getName()).log(Level.SEVERE, null, e);
        }
        
    }
    
    private void limpiarRackForm() {
        rack_form_container.setDisable(true);
        rack_form_cod_field.clear();
        rack_form_almacen_nombre_field.clear();
        rack_form_cantidad_productos_field.clear();
        rack_form_alto_field.clear();
        rack_form_largo_field.clear();
        rack_form_ancho_field.clear();
    }
    
    private void actualizarAlmacenZ(AlmacenAreaZ almacenZ) {
        AtomicInteger indice = new AtomicInteger(0);
        AtomicInteger currentIndice = new AtomicInteger(0);
        
        List<AlmacenAreaZ> almacenesFiltrados = almacenesZ_racks.stream().filter(almacen -> {
            boolean cond = almacen.getInteger("almacen_z_id") == almacenZ.getInteger("almacen_z_id");

            if(cond) indice.set(currentIndice.get());
            currentIndice.set(currentIndice.get() + 1);

            return cond;
        }).collect(Collectors.toList());

        if(almacenesFiltrados.size() == 0) {
            almacenesZ_racks.add(almacenZ); // Almacenes que son stasheados para evitar hacer muchas querys al guardar un producto, no es necesario de hacer pero me parece mas eficiente
        } else {
            almacenesZ_racks.set(indice.get(), almacenZ);
        }
    }

    private void agregarProductoARack(agregarProductoRackArgs args) {
        Producto producto = args.getProducto();
        AlmacenAreaZ almacenZ = args.getAlmacenZ();

        actualizarAlmacenZ(almacenZ);
        productos_rack.add(producto); // Racks que se muestran en la tabla de racks
        productos_tmp_racks.add(producto);
        rack_form_producto_tabla.setItems(productos_rack);
        rack_form_cantidad_productos_field.setText(String.valueOf(productos_rack.size()));
    }
    
    private boolean hasNoEmptyRequiredFields(String codigo, String almacen, String cantProd, String alto, String largo, String ancho) {
        return !(codigo.equals("") ||
                almacen.equals("") ||
                cantProd.equals("") ||
                largo.equals("") ||
                alto.equals("") ||
                ancho.equals(""));
    }

    private void openModal(Stage currentStage, AnchorPane contenido) {
        Scene modal_content_scene = new Scene(contenido);
        currentStage.setScene(modal_content_scene);
        currentStage.initModality(Modality.APPLICATION_MODAL);
        currentStage.setScene(modal_content_scene);
        currentStage.showAndWait();
    }

    private void loadModalContent(String path, ModalController controller) {
        try {
            Stage currentStage = new Stage();
            FXMLLoader loaderContenido = new FXMLLoader(getClass().getResource(path));
            loaderContenido.setController(controller);
            AnchorPane contenido = (AnchorPane) loaderContenido.load();
            controller.setCurrentStage(currentStage);
            controller.getCloseModalEvent().addHandler((sender, args) -> {
                agregarProductoARack((agregarProductoRackArgs) args);
            });
            openModal(currentStage, contenido);
        } catch (IOException ex) {
            Logger.getLogger(RacksController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean existeEnProductosTmpOProducto(Producto productoSeleccionado) {
        Boolean cond = false;
        try {
            List<Producto> productos_finales = new ArrayList<>();
            LazyList<Producto> productosDB = Producto.find("rack_id = ? and rack_cod = ?", rack_seleccionado.getInteger("rack_id"), rack_seleccionado.get("rack_cod"));
            // Reviso en la Lista de productos en BD
            for(Producto productoDB : productosDB)
                cond = cond || productoDB.getInteger("producto_id") == productoSeleccionado.getInteger("producto_id");
            // Reviso en la Lista a ser modificada
                for(Producto productoTemp : productos_tmp_racks)
                cond = cond || productoTemp.getInteger("producto_id") == productoSeleccionado.getInteger("producto_id");

        } catch(Exception e) {
            Logger.getLogger(RacksController.class.getName()).log(Level.SEVERE, null, e);
        }
        return cond;
    }

    private void agregarAProductosTemporales(Producto productoSeleccionado) {
        List<Producto> productos_filtrado = productos_tmp_racks.stream().filter(producto -> {
            return !(Objects.equals(producto.getInteger("producto_id"), productoSeleccionado.getInteger("producto_id")));
        }).collect(Collectors.toList());

        productos_filtrado.add(productoSeleccionado);

        productos_tmp_racks.clear();
        productos_filtrado.forEach(productos_tmp_racks::add);
    }

    private void removerProductosMostrar(Producto productoSeleccionado) {
        List<Producto> productos_filtrado = productos_rack.stream().filter(producto -> {
            return !(Objects.equals(producto.getInteger("producto_id"), productoSeleccionado.getInteger("producto_id")));
        }).collect(Collectors.toList());

        productos_rack.clear();
        productos_filtrado.forEach(productos_rack::add);
        rack_form_producto_tabla.setItems(productos_rack);
        rack_form_cantidad_productos_field.setText(String.valueOf(productos_rack.size()));
    }

    @FXML
    public void abrirModalAgregarProducto(ActionEvent event) {
        ModalController controller = new ModalAgregarProductoRackController(rack_seleccionado, almacen_relacionado, almacenesZ_racks, productos_tmp_racks);
        loadModalContent("/fxml/Materiales/Racks/AgregarProductosARack.fxml", controller);
    }

    @FXML
    public void visualizarRack(ActionEvent event) {
        rack_seleccionado = buscar_rack_tabla.getSelectionModel().getSelectedItem();
        if(rack_seleccionado == null)
        {
            infoController.show("No ha seleccionado ningun rack");
            return;
        }
        productos_rack.clear();
        productos_tmp_racks.clear();
        almacenesZ_racks.clear();

        almacen_relacionado = rack_seleccionado.parent(Almacen.class);
        int tileSize = almacen_relacionado.getInteger("longitud_area");
        int rackX1 = rack_seleccionado.getInteger("x_ancla1");
        int rackX2 = rack_seleccionado.getInteger("x_ancla2");
        int rackY1 = rack_seleccionado.getInteger("y_ancla1");
        int rackY2 = rack_seleccionado.getInteger("y_ancla2");
        String rackLargo = "";
        String rackAncho = "";
        LazyList<Producto> productos = Producto.find("rack_id = ? and rack_cod = ?", rack_seleccionado.getId(), rack_seleccionado.get("rack_cod"));

        productos.forEach(productos_rack::add);

        if (rackX1 == rackX2) {
            rackLargo = String.valueOf(tileSize);
            rackAncho = String.valueOf(rack_seleccionado.getInteger("longitud"));
        }
        else if (rackY1 == rackY2) {
            rackLargo = String.valueOf(rack_seleccionado.getInteger("longitud"));
            rackAncho = String.valueOf(tileSize);
        }
        
        String rackCodigo = rack_seleccionado.getString("rack_cod");
        String rackAlmacen = almacen_relacionado.getString("nombre");
        String rackAlto = String.valueOf(rack_seleccionado.getInteger("altura"));
        
        rack_form_cod_field.setText(rackCodigo);
        rack_form_almacen_nombre_field.setText(rackAlmacen);
        rack_form_cantidad_productos_field.setText(String.valueOf(productos_rack.size()));
        rack_form_producto_tabla.setItems(productos_rack);
        rack_form_alto_field.setText(rackAlto);
        rack_form_largo_field.setText(rackLargo);
        rack_form_ancho_field.setText(rackAncho);
        rack_form_container.setDisable(false);
    }
    
    @FXML
    public void buscarRacks(ActionEvent event) {
        try {
            String rackAncho = buscar_rack_ancho.getText();
            String rackAlto = buscar_rack_altura.getText();
            String rackLargo = buscar_rack_largo.getText();

            if( (validator.isEmptyString(rackAncho) || validator.isNumeric(rackAncho)) && 
                (validator.isEmptyString(rackAlto) || validator.isNumeric(rackAlto)) && 
                (validator.isEmptyString(rackLargo) || validator.isNumeric(rackLargo)) )
            {
                LazyList<Rack> racks_existentes = Rack.findAll();
                String almacenNomb = String.valueOf(buscar_rack_almacen_nomb.getSelectionModel().getSelectedItem() == null ? "" : buscar_rack_almacen_nomb.getSelectionModel().getSelectedItem());
                String rackCod = buscar_rack_cod.getText();
                String productoNomb = buscar_rack_producto.getText();
                Almacen almacen_seleccionado = Almacen.findFirst("nombre = ?", almacenNomb);
                List<Rack> racks_filtrados = new ArrayList<>();

                racks_filtrados = racks_existentes.stream().filter((rack) -> {
                    Almacen almacenRack = rack.parent(Almacen.class);
                    boolean cond = true;
                    // Validacion nombre Almacen
                    cond = cond && (almacen_seleccionado == null || almacen_seleccionado.getString("es_central").equals("T") ? true :almacenRack.getString("nombre").contains(almacenNomb));
                    // Validacion codigo Rack
                    cond = cond && rack.getString("rack_cod").contains(rackCod);
                    // Validacion Ancho
                    cond = cond && (validator.isEmptyString(rackAncho) ? true : almacenRack.getDouble("longitud_area") < Double.valueOf(rackAncho));
                    // Validacion Largo
                    cond = cond && (validator.isEmptyString(rackLargo) ? true : rack.getInteger("longitud") < Integer.valueOf(rackLargo));
                    // Validacion Alto
                    cond = cond && (validator.isEmptyString(rackAlto) ? true : rack.getInteger("altura ") < Integer.valueOf(rackAlto));
                    // Validacion Productos
                    cond = cond && (validator.isEmptyString(productoNomb) ? true : Producto.findBySQL("SELECT productos.* FROM productos LEFT JOIN tiposproducto ON productos.tipo_cod = tiposproducto.tipo_cod WHERE tiposproducto.nombre = ? AND rack_cod = ?", productoNomb, rack.getString("rack_cod")).size() > 0);
    
                    return cond;
                }).collect(Collectors.toList());

                racks_busqueda.clear();
                racks_filtrados.forEach(racks_busqueda::add);
                buscar_rack_tabla.setItems(racks_busqueda);
            } else {
                infoController.show("Solo se permiten valores numericos en los campos de Alto maximo, Ancho maximo, Largo, maximo");
            }
        } catch (Exception e) {
            Logger.getLogger(RacksController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void guardar() {
        if (!Usuario.tienePermiso(permisosActual, Menu.MENU.Racks, Accion.ACCION.MOD)){
            infoController.show("No tiene los permisos suficientes para realizar esta acción");
            return;
        }
        Rack rack = rack_seleccionado;
        String rackCodigoStr = rack_form_cod_field.getText();
        String rackAlmacen = rack_form_almacen_nombre_field.getText();
        String rackCantProd = rack_form_cantidad_productos_field.getText();
        String rackAltoStr = rack_form_alto_field.getText(); 
        String rackAnchoStr = rack_form_ancho_field.getText();
        String rackLargoStr = rack_form_largo_field.getText();
        
        
        if(hasNoEmptyRequiredFields(rackCodigoStr, rackAlmacen, rackCantProd, rackAltoStr, rackLargoStr, rackAnchoStr)) {
            try {
                Base.openTransaction();
                productos_tmp_racks.forEach((producto) -> {
                    producto.saveIt();
                });
                almacenesZ_racks.forEach((almacen) -> {
                    System.out.println(almacen);
                    almacen.saveIt();
                });
                Base.commitTransaction();
                productos_tmp_racks.forEach(t -> {
                    AccionLoggerSingleton.getInstance().logAccion(Accion.ACCION.MOV, Menu.MENU.Racks,this.usuarioActual);
                });
                infoController.show("Se ha guardado correctamente el rack");
                limpiarRackForm();
                actualizarTablaBusqueda();
            } catch (Exception e) {
                Base.rollbackTransaction();
                warningController.show("Error al guardar el rack", "Sucedio un error al guardar el rack, vuelva a intentarlo");
                Logger.getLogger(RacksController.class.getName()).log(Level.SEVERE, null, e);
            }
        } else {
            warningController.show("Error al guardar el rack", "Es necesario que complete todos los campos del rack");
        }
    }
    
    @FXML
    private void retirarProducto(ActionEvent event) {
        try {
            Producto producto_seleccionado = rack_form_producto_tabla.getSelectionModel().getSelectedItem();
            System.out.println(producto_seleccionado.getString("producto_cod"));

            if (producto_seleccionado != null) {
                if(existeEnProductosTmpOProducto(producto_seleccionado)) {
                    // Actualizamos la capacidad restante del AlmacenAreaZ
                    AlmacenAreaZ almacenZ = AlmacenAreaZ.findFirst("almacen_z_id = ?", producto_seleccionado.getInteger("almacen_z_id"));
                    List<AlmacenAreaZ> almacenesFiltrados = almacenesZ_racks.stream().filter(almacen -> {
                        return almacen.getInteger("almacen_z_id") == almacenZ.getInteger("almacen_z_id");
                    }).collect(Collectors.toList());
                    AlmacenAreaZ almacenFiltrado;
                    
                    if(almacenesFiltrados.size() > 0) {
                        almacenFiltrado = almacenesFiltrados.get(0);
                    } else {
                        almacenFiltrado = almacenZ;
                    }
                    
                    double capacidadRestante = almacenFiltrado.getDouble("capacidad_restante");
                    double pesoProducto = TipoProducto.findFirst("tipo_id = ?", producto_seleccionado.getInteger("tipo_id")).getDouble("peso");
                    almacenFiltrado.set("capacidad_restante", capacidadRestante + pesoProducto);

                    // Seteamos el producto para actualizar
                    producto_seleccionado.set("tipo_posicion", null);
                    producto_seleccionado.set("ubicado", "N");
                    producto_seleccionado.set("almacen_z_id", null);
                    producto_seleccionado.set("almacen_xy_id", null);
                    producto_seleccionado.set("rack_id", null);
                    producto_seleccionado.set("rack_cod", null);
                    producto_seleccionado.set("almacen_id", null);
                    producto_seleccionado.set("almacen_cod", null);
                    producto_seleccionado.set("posicion_rack", null);
                    
                    actualizarAlmacenZ(almacenFiltrado);
                    removerProductosMostrar(producto_seleccionado);
                    agregarAProductosTemporales(producto_seleccionado);
                } else {
                    infoController.show("El producto ya fue removido por otro usuario");
                }
            } else {
                warningController.show("Error al Retirar un Producto", "Es necesario que seleccione un elemento de la tabla para ser retirado");
            }
        } catch(Exception e) {
            Logger.getLogger(RacksController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Racks;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Menu de Busqueda
            ObservableList<String> almacenes = FXCollections.observableArrayList();
            almacenes.addAll(Almacen.findAll().stream()
                    .map(almacen -> almacen.getString("nombre")).collect(Collectors.toList())
            );

            buscar_rack_almacen_nomb.setItems(almacenes);
            buscar_columna_codigo_rack.setCellValueFactory( (TableColumn.CellDataFeatures<Rack, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("rack_cod") ));

            // Tabla de Formulario
            rack_form_producto_cod_column.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("producto_cod")) );
            rack_form_producto_posicion_column.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("posicion_rack")) );
            rack_form_producto_nombre_column.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) ->
                new ReadOnlyObjectWrapper(
                    TipoProducto.findById(p.getValue().getInteger("tipo_id")).getString("nombre")
                )
            );
            actualizarTablaBusqueda();
        } catch (Exception e) {
            Logger.getLogger(RacksController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
