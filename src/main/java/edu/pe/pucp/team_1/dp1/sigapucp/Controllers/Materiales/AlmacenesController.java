/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.WarningAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Rack;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Almacen;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents.SelectableGrid;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents.LinearDrawing;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents.RectangularDrawing;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.createAlmacenArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.createRackArgs;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tab;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;

/**
 * FXML Controller class
 *
 * @author Jauma
 */
public class AlmacenesController extends Controller{

    private SelectableGrid grid;
    private ObservableList<Rack> racks;
    private ObservableList<Almacen> almacenes;
    private ObservableList<Almacen> almacenes_logicos;
    private WarningAlertController warningController;
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmatonController;
    private Rack temp_rack;
    private Almacen temp_almacen;
    private AtomicBoolean skipValidationOnTabChange;
    private int numAlmacenes;
    private int numRacks;
    @FXML private AnchorPane AnchorPane;
    @FXML private TableView<Almacen> tabla_almacenes;
    @FXML private TableColumn<Almacen, String> almacen_nombre;
    @FXML private TableColumn<Almacen, String> almacen_codigo;
    @FXML private TextField nombre_almacen_buscar;
    @FXML private TextField codigo_almacen_buscar;
    @FXML private TextField almacen_nombre_field;
    @FXML private TextField almacen_largo_field;
    @FXML private TextField almacen_ancho_field;
    @FXML private TextField almacen_lado_grilla;
    @FXML private RadioButton radio_btn_almacen_logico;
    @FXML private ToggleGroup tipoAlmacen;
    @FXML private RadioButton radio_btn_almacen_fisico;
    @FXML private TableView<Rack> rack_table_view;
    @FXML private TableColumn<Rack, String> rack_column_codigo;
    @FXML private TableColumn<Rack, String> rack_column_largo;
    @FXML private TableColumn<Rack, String> rack_column_altura;
    @FXML private TableView<Almacen> almacen_table_view;
    @FXML private TableColumn<Almacen, String> almacen_column_cod;
    @FXML private TableColumn<Almacen, String> almacen_column_largo;
    @FXML private TableColumn<Almacen, String> almacen_column_ancho;
    @FXML private TitledPane create_racks_container;
    @FXML private TitledPane create_almacen_container;
    @FXML private TextField rack_altura_field;
    @FXML private AnchorPane contenedor_grilla;
    @FXML private TabPane almacen_form_pane;
    @FXML private TabPane list_objects_pane;
    @FXML private Tab almacen_form_tab;
    @FXML private Tab almacen_draw_tab;
    @FXML private Tab list_racks_tab;
    @FXML private Tab list_almacenes_tab;
    
    public AlmacenesController() {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        
        temp_rack = new Rack();
        temp_almacen = new Almacen();
        warningController = new WarningAlertController();
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
        racks = FXCollections.observableArrayList();
        almacenes = FXCollections.observableArrayList();
        almacenes_logicos = FXCollections.observableArrayList();
        skipValidationOnTabChange = new AtomicBoolean(false);
        
//        createNewLinearSelectableGrid(400, 400, 10);
    }
    
    private void setTempRack(createRackArgs args) {
        temp_rack.set("x_ancla1",args.getX_ancla1());
        temp_rack.set("y_ancla1",args.getY_ancla1());
        temp_rack.set("x_ancla2",args.getX_ancla2());
        temp_rack.set("y_ancla2",args.getY_ancla2());
        temp_rack.set("longitud",args.getLongitud());
        temp_rack.set("es_uniforme",args.getIs_uniforme());
        temp_rack.set("estado", "ACTIVO");

        racks.add(temp_rack);
        rack_table_view.setItems(racks);
    }
    
    private void setTempAlmacen(createAlmacenArgs args) {
        temp_almacen.set("nombre", args.getNombre());
        temp_almacen.set("es_central", args.getEs_cental());
        temp_almacen.set("largo", args.getLargo());
        temp_almacen.set("ancho", args.getAncho());
        temp_almacen.set("longitud_area", args.getLongitud_area());
        temp_almacen.set("x_relativo_central", args.getX_relativo());
        temp_almacen.set("y_relativo_central", args.getY_relativo());
    }
    
    private void afterCreateOrCancelRack() {
        rack_altura_field.clear();
        create_racks_container.setDisable(true);
        contenedor_grilla.setDisable(false);
    }
    
    private Boolean validationDrawTab() {
        return !(almacen_ancho_field.getText().equals("") ||
                almacen_largo_field.getText().equals("") ||
                almacen_lado_grilla.getText().equals("") ||
                tipoAlmacen.getSelectedToggle() == null);
    }
    
    private void createNewLinearSelectableGrid(int columns, int rows, int size) {
        LinearDrawing linearBehavior = new LinearDrawing();
        grid = new SelectableGrid(rows, columns, size, linearBehavior);

        linearBehavior.getCreateRackEvent().addHandler((sender, args) -> {
            create_racks_container.setDisable(false);
            contenedor_grilla.setDisable(true);
            setTempRack((createRackArgs) args);
        });

        if(contenedor_grilla != null) contenedor_grilla.getChildren().setAll(grid);
    }
    
    private void createNewRectangularSelectableGrid(int width, int height, int size) {
        RectangularDrawing rectangularBehavior = new RectangularDrawing();
        grid = new SelectableGrid(width, height, size, rectangularBehavior);
        
        rectangularBehavior.getCreateLogicalWarehouseEvent().addHandler((sender, args) -> {
            String almacenNombre = almacen_nombre_field.getText();
            temp_almacen = new Almacen();
            args.setLongitud_area(grid.getTileSize());
            args.setNombre(almacenNombre);
            setTempAlmacen(args);
        });
        
        if(contenedor_grilla != null) contenedor_grilla.getChildren().setAll(grid);
    }
    
    private void setTypeOfShow(boolean success) {
        if (success && tipoAlmacen.getSelectedToggle() != null) {
            int almacenAlto = Integer.parseInt(almacen_largo_field.getText());
            int almacenAncho = Integer.parseInt(almacen_ancho_field.getText());
            int tileSize = Integer.parseInt(almacen_lado_grilla.getText());
            Boolean hasAlmacenResize = grid == null || almacenAlto != grid.getGrid_heigth() || almacenAncho != grid.getGrid_width()|| tileSize != grid.getTileSize();
            RadioButton activeRadio = (RadioButton) tipoAlmacen.getSelectedToggle();
            switch(activeRadio.getText()) {
                case "Almacen Logico":        
                    if (hasAlmacenResize) {
                        System.out.println("Crea una nueva grilla para Almacen Logico");
                        createNewLinearSelectableGrid(almacenAncho, almacenAlto, tileSize);
                    }
                    break;
                case "Almacen Fisico":
                    if (hasAlmacenResize) {
                        System.out.println("Crea una nueva grilla para Almacen Fisico");
                        createNewRectangularSelectableGrid(almacenAncho, almacenAlto, tileSize);
                    }
                    break;
                default:
                    break;
            }
            
        }
    }
    
    private void actualizarTablaBusqueda() {
        LazyList<Almacen> almacenActuales = Almacen.findAll();
        numAlmacenes = almacenActuales.size();
        if(numAlmacenes < 1) {
            radio_btn_almacen_logico.setDisable(true);
            tipoAlmacen.selectToggle(radio_btn_almacen_fisico);
            almacen_form_pane.setDisable(false);
            list_almacenes_tab.setDisable(false);
            list_objects_pane.getSelectionModel().select(list_almacenes_tab);
            infoController.show("Es necesario que se cree un almacen fisico primero");
        } else {
            almacenActuales.forEach((almacen) -> {
                almacenes.add(almacen);
            });
            tabla_almacenes.setItems(almacenes);
        }
    }
    
    
    @FXML
    public void buscarAlmacen(ActionEvent event) {
        
    }
    
    @FXML
    public void crearRack(ActionEvent event) {
        if (!rack_altura_field.getText().equals("")) {
            int altura = Integer.parseInt(rack_altura_field.getText());
            temp_rack = new Rack();
            temp_rack.set("altura", altura);
            grid.clearAndSaveTempTiles();
            infoController.show("Se agrego el rack correctamente");
            afterCreateOrCancelRack();
        } else {
            warningController.show("Error de Creacion de Rack", "Se debe ingresar una altura");
        }
        
    }
    
    @FXML
    public void cancelarCreacionRack(ActionEvent event) {
        grid.clearCurrentActiveTiles();
        afterCreateOrCancelRack();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        contenedor_grilla.getChildren().setAll(grid);
        // Creacion de almacen logico, tabla de Racks creados hasta el momento
        rack_column_codigo.setCellValueFactory( (TableColumn.CellDataFeatures<Rack, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("rack_cod") ));
        rack_column_largo.setCellValueFactory( (TableColumn.CellDataFeatures<Rack, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("longitud") ));
        rack_column_altura.setCellValueFactory( (TableColumn.CellDataFeatures<Rack, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("altura") ));
        // Almacen Tabla de Busqueda
        almacen_nombre.setCellValueFactory( (TableColumn.CellDataFeatures<Almacen, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")) );
        almacen_codigo.setCellValueFactory( (TableColumn.CellDataFeatures<Almacen, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("almacen_cod")) );
        // Almacen Tabla de Almacenes Logicos
        almacen_column_cod.setCellValueFactory( (TableColumn.CellDataFeatures<Almacen, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("almacen_cod")) );
        almacen_column_largo.setCellValueFactory( (TableColumn.CellDataFeatures<Almacen, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("largo")) );
        almacen_column_ancho.setCellValueFactory( (TableColumn.CellDataFeatures<Almacen, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("ancho")) );
        actualizarTablaBusqueda();
        
        almacen_form_pane.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (!skipValidationOnTabChange.get()) {
                boolean success = false;
                switch(newValue.intValue()) {
                    case 0:
                        success = true;
                        break;
                    case 1:
                        success = validationDrawTab();
                        setTypeOfShow(success);
                        break;
                    default:
                        break;
                }
                if(!success) {
                    Platform.runLater(() -> {
                        skipValidationOnTabChange.set(true);
                        almacen_form_pane.getSelectionModel().select(oldValue.intValue());
                        skipValidationOnTabChange.set(false);
                        warningController.show("Error de sistema", "Se debe definir la altura, el ancho y el tipo de almacen");
                    });
                }
            }
        });
    }    
    
    @Override
    public Menu.MENU getMenu()
    {
        return Menu.MENU.Almacenes;
    }
}
