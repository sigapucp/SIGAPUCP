/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Modales.ModalAgregarProductoRackController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
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
import java.util.stream.Collectors;
import java.util.ResourceBundle;
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
    private List<AlmacenAreaZ> almacenesZ_racks;
    private WarningAlertController warningController;
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmatonController;
    private Rack rack_seleccionado;
    private Almacen almacen_relacionado;
    @FXML private AnchorPane rack_form_container;
    @FXML private TableView<Producto> rack_form_producto_tabla;
    @FXML private TableColumn<Producto, String> rack_form_producto_cod_column;
    @FXML private TableColumn<Producto, String> rack_form_producto_fecha_venc_column;
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
    
    private void agregarProductoARack(agregarProductoRackArgs args) {
        Producto producto = args.getProducto();
        AlmacenAreaZ almacenZ = args.getAlmacenZ();

        almacenesZ_racks.add(almacenZ);
        productos_rack.add(producto);
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

    @FXML
    public void abrirModalAgregarProducto(ActionEvent event) {
        ModalController controller = new ModalAgregarProductoRackController(rack_seleccionado, almacen_relacionado);
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
        productos_rack.clear();;
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
                
                List<Rack> racks_filtrados = racks_existentes.stream().filter((rack) -> {
                    Almacen almacenRack = rack.parent(Almacen.class);
                    boolean rackProdutosCond = validator.isEmptyString(productoNomb) ? true : Producto.findBySQL("SELECT productos.* FROM productos LEFT JOIN tiposproducto ON productos.tipo_cod = tiposproducto.tipo_cod WHERE tiposproducto.nombre = ? AND rack_cod = ?", productoNomb, rack.getString("rack_cod")).size() > 0;
                    boolean rackAnchoCond = validator.isEmptyString(rackAncho) ? true : almacenRack.getDouble("longitud_area") < Double.valueOf(rackAncho);
                    boolean rackLargoCond = validator.isEmptyString(rackLargo) ? true : rack.getInteger("longitud") < Integer.valueOf(rackLargo);
                    boolean rackAltoCond = validator.isEmptyString(rackAlto) ? true : rack.getInteger("altura ") < Integer.valueOf(rackAlto);
    
                    return almacenRack.getString("nombre").contains(almacenNomb) &&
                    rack.getString("rack_cod").contains(rackCod) &&
                    rackAnchoCond &&
                    rackLargoCond &&
                    rackAltoCond &&
                    rackProdutosCond;
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
                productos_rack.forEach((producto) -> {
                    producto.saveIt();
                });
                almacenesZ_racks.forEach((almacen) -> {
                    almacen.saveIt();
                });
                Base.commitTransaction();
                infoController.show("Se ha guardado correctamente el rack");
                limpiarRackForm();
                actualizarTablaBusqueda();
            } catch (Exception e) {
                Base.rollbackTransaction();
                System.out.println(e);
                warningController.show("Error al guardar el rack", "Sucedio un error al guardar el rack, vuelva a intentarlo");
            }
        } else {
            warningController.show("Error al guardar el rack", "Es necesario que complete todos los campos del rack");
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
                    .filter(almacen -> almacen.getString("es_central").equals("F"))
                    .map(almacen -> almacen.getString("nombre")).collect(Collectors.toList())
            );

            buscar_rack_almacen_nomb.setItems(almacenes);
            buscar_columna_codigo_rack.setCellValueFactory( (TableColumn.CellDataFeatures<Rack, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("rack_cod") ));

            // Tabla de Formulario
            rack_form_producto_cod_column.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("producto_cod")) );
            rack_form_producto_fecha_venc_column.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("fecha_caducidad")) );
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
