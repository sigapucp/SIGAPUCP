/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.ConfirmationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.WarningAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Rack;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.Almacen;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents.SelectableGrid;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
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
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Jauma
 */
public class AlmacenesController extends Controller{

    private SelectableGrid grid;
    private ObservableList<Rack> racks;
    private ObservableList<Almacen> almacen;
    private WarningAlertController warningController;
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmatonController;
    private Rack temp_rack;
    private AtomicBoolean skipValidationOnTabChange;
    @FXML private AnchorPane AnchorPane;
    @FXML private TextField peso_producto_buscar;
    @FXML private TableView<Almacen> tabla_almacenes;
    @FXML private TableColumn<Almacen, String> columna_nombre;
    @FXML private TableColumn<Almacen, String> columna_descripcion;
    @FXML private TextField codigo_producto_buscar;
    @FXML private TextField almacen_nombre_field;
    @FXML private TextField almacen_largo_field;
    @FXML private TextField almacen_ancho_field;
    @FXML private RadioButton radio_btn_almacen_logico;
    @FXML private ToggleGroup tipoAlmacen;
    @FXML private RadioButton radio_btn_almacen_fisico;
    @FXML private TableView<Rack> rack_table_view;
    @FXML private TableColumn<Rack, String> rack_column_codigo;
    @FXML private TableColumn<Rack, String> rack_column_largo;
    @FXML private TableColumn<Rack, String> rack_column_ancho;
    @FXML private TableColumn<Rack, String> rack_column_altura;
    @FXML private Button desactivar_rack;
    @FXML private Button buscar_rack_btn;
    @FXML private TextField rack_cod_input_search;
    @FXML private TitledPane create_racks_container;
    @FXML private TextField rack_altura_field;
    @FXML private AnchorPane contenedor_grilla;
    @FXML private TabPane almacen_form_pane;
    
    public AlmacenesController() {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        
        temp_rack = new Rack();
        warningController = new WarningAlertController();
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
        racks = FXCollections.observableArrayList();
        skipValidationOnTabChange = new AtomicBoolean(false);
        
        createNewSelectableGrid(10, 10, 400, 400);
    }
    
    private void setTempRack(Rack rack, createRackArgs args) {
        rack.set("x_ancla1",args.getX_ancla1());
        rack.set("y_ancla1",args.getY_ancla1());
        rack.set("x_ancla2",args.getX_ancla2());
        rack.set("y_ancla2",args.getY_ancla2());
        rack.set("longitud",args.getLongitud());
        rack.set("es_uniforme",args.getIs_uniforme());
        rack.set("estado", "ACTIVO");
    }
    
    private void afterCreateOrCancelRack() {
        rack_altura_field.clear();
        create_racks_container.setDisable(true);
        contenedor_grilla.setDisable(false);
    }
    
    private Boolean validationDrawTab() {
        return !(almacen_ancho_field.getText().equals("") || almacen_largo_field.getText().equals("") || tipoAlmacen.getSelectedToggle() == null);
    }
    
    private void createNewSelectableGrid(int columns, int rows, int width, int height) {
        grid = new SelectableGrid(rows, columns, width, height);

        grid.getCreateRackEvent().addHandler((sender, args) -> {
            create_racks_container.setDisable(false);
            contenedor_grilla.setDisable(true);
            temp_rack = new Rack();
            setTempRack(temp_rack, args);
        });

        if(contenedor_grilla != null) contenedor_grilla.getChildren().setAll(grid);
    }
    
    private void setTypeOfShow() {
        if (tipoAlmacen.getSelectedToggle() != null) {
            RadioButton activeRadio = (RadioButton) tipoAlmacen.getSelectedToggle();
            switch(activeRadio.getText()) {
                case "Almacen Logico":
                    int almacenAncho = Integer.parseInt(almacen_ancho_field.getText());
                    int almacenAlto = Integer.parseInt(almacen_largo_field.getText());
                    if (almacenAncho != grid.getGrid_width() || almacenAlto != grid.getGrid_heigth()) {
                        System.out.println("Crea una nueva grilla");
                        createNewSelectableGrid(10, 10, almacenAncho, almacenAlto);
                    }   
                    break;
                case "Almacen Fisico":
                    break;
                default:
                    break;
            }
            
        }
    }
    
    
    @FXML
    public void buscarAlmacen(ActionEvent event) {
        
    }
    
    @FXML
    public void crearRack(ActionEvent event) {
        if (!rack_altura_field.getText().equals("")) {
            int altura = Integer.parseInt(rack_altura_field.getText());
            temp_rack.set("altura", altura);
            grid.clearAndSaveTempTiles();
            racks.add(temp_rack);
            infoController.show("Se agrego el rack correctamente");
            rack_table_view.setItems(racks);
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
        contenedor_grilla.getChildren().setAll(grid);
        rack_column_codigo.setCellValueFactory( (TableColumn.CellDataFeatures<Rack, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("rack_cod") ));
        rack_column_largo.setCellValueFactory( (TableColumn.CellDataFeatures<Rack, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("longitud") ));
        rack_column_altura.setCellValueFactory( (TableColumn.CellDataFeatures<Rack, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("altura") ));

        almacen_form_pane.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (!skipValidationOnTabChange.get()) {
                boolean success = false;
                switch(newValue.intValue()) {
                    case 0:
                        success = true;
                        break;
                    case 1:
                        success = validationDrawTab();
                        setTypeOfShow();
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
    public Menu.MENU getMenu(){
        return Menu.MENU.Almacenes;
    }

}
