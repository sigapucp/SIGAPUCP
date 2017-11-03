/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Rack;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Producto;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.WarningAlertController;
import java.net.URL;
import java.util.ResourceBundle;
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
    private WarningAlertController warningController;
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmatonController;
    @FXML private AnchorPane rack_form_container;
    @FXML private TableView<Producto> rack_form_tabla;
    @FXML private TableColumn<Producto, String> rack_form_cod_column;
    @FXML private TableColumn<Producto, String> rack_form_alm_column;
    @FXML private TableColumn<Producto, String> rack_form_alto_column;
    @FXML private TableColumn<Producto, String> rack_form_ancho_column;
    @FXML private TableColumn<Producto, String> rack_form_largo_column;
    @FXML private TableColumn<Producto, String> rack_form_cant_prod_column;
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
        warningController = new WarningAlertController();
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
    }    

    private void actualizarTablaBusqueda() {
        LazyList<Rack> rackActuales = Rack.findAll();

        rackActuales.forEach((rack) -> {
            racks_busqueda.add(rack);
        });
        buscar_rack_tabla.setItems(racks_busqueda);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Tabla de Busqueda
        buscar_columna_codigo_rack.setCellValueFactory( (TableColumn.CellDataFeatures<Rack, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("rack_cod") ));
        // Tabla de Formulario ; Nota camibar UI
        rack_form_cod_column.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("rack_cod")) );
        rack_form_alm_column.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("almacen_cod")) );
        rack_form_alto_column.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("altura")) );
        rack_form_largo_column.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("longitud")) );
        rack_form_ancho_column.setCellValueFactory( (TableColumn.CellDataFeatures<Producto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("ancho")) );

        actualizarTablaBusqueda();
    }
    
    @FXML
    void visualizarRack(ActionEvent event) {
        rack_form_container.setDisable(false);
    }
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Racks;
    }
}
