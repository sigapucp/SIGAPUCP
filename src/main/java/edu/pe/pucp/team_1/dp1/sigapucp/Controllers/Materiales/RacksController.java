/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Rack;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Stock;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Almacen;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.WarningAlertController;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    private WarningAlertController warningController;
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmatonController;
    private Rack rack_seleccionado;
    @FXML private AnchorPane rack_form_container;
    @FXML private TableView<Producto> rack_form_producto_tabla;
    @FXML private TableColumn<Producto, String> rack_form_producto_cod_column;
    @FXML private TableColumn<Producto, String> rack_form_cant_prod_column;
    @FXML private TableColumn<Producto, String> rack_form_producto_nombre_column;
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
        warningController = new WarningAlertController();
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
    }    

    private void actualizarTablaBusqueda() {
        try {
            LazyList<Rack> rackActuales = Rack.findAll();

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
    
    private void agregarProductoARack(Producto producto) {
        productos_rack.add(producto);
    }
    
    private boolean hasNoEmptyRequiredFields(String codigo, String almacen, String cantProd, String alto, String largo, String ancho) {
        return !(codigo.equals("") ||
                almacen.equals("") ||
                cantProd.equals("") ||
                largo.equals("") ||
                alto.equals("") ||
                ancho.equals(""));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Tabla de Busqueda
        buscar_columna_codigo_rack.setCellValueFactory( (TableColumn.CellDataFeatures<Rack, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("rack_cod") ));
        // Tabla de Formulario ; Nota camibar UI
        rack_form_producto_cod_column.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("producto_cod")) );
        rack_form_cant_prod_column.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) ->
            new ReadOnlyObjectWrapper(
                Stock.findByCompositeKeys(p.getValue().getId(), p.getValue().get("tipo_cod")).getInteger("stock_fisico")
            )
        );
        rack_form_producto_nombre_column.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) ->
            new ReadOnlyObjectWrapper(
                TipoProducto.findById(p.getValue().getId()).getString("nombre")
            )
        );

        actualizarTablaBusqueda();
    }

    @FXML
    void visualizarRack(ActionEvent event) {
        rack_seleccionado = buscar_rack_tabla.getSelectionModel().getSelectedItem();
        Almacen almacen_relacionado = rack_seleccionado.parent(Almacen.class);
        int tileSize = almacen_relacionado.getInteger("longitud_area");
        int rackX1 = rack_seleccionado.getInteger("x_ancla1");
        int rackX2 = rack_seleccionado.getInteger("x_ancla2");
        int rackY1 = rack_seleccionado.getInteger("y_ancla1");
        int rackY2 = rack_seleccionado.getInteger("y_ancla2");
        String rackLargo = "";
        String rackAncho = "";
        
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
        rack_form_cantidad_productos_field.setText("0");
        rack_form_alto_field.setText(rackAlto);
        rack_form_largo_field.setText(rackLargo);
        rack_form_ancho_field.setText(rackAncho);
        rack_form_container.setDisable(false);
    }
    
    @Override
    public void guardar() {
        Rack rack = rack_seleccionado;
        String rackCodigoStr = rack_form_cod_field.getText();
        String rackAlmacen = rack_form_almacen_nombre_field.getText();
        String rackCantProd = rack_form_cant_prod_column.getText();
        String rackAltoStr = rack_form_alto_field.getText(); 
        String rackAnchoStr = rack_form_ancho_field.getText();
        String rackLargoStr = rack_form_largo_field.getText();
        
        
        if(hasNoEmptyRequiredFields(rackCodigoStr, rackAlmacen, rackCantProd, rackAltoStr, rackLargoStr, rackAnchoStr)) {
            try {
                Base.openTransaction();
                rack.set("altura", Integer.valueOf(rackAltoStr));
                rack.saveIt();
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
}
