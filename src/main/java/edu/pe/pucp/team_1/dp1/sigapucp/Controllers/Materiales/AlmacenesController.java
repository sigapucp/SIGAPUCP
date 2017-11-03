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
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Seguridad.TipoError;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.createAlmacenArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.createRackArgs;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
    @FXML private AnchorPane AnchorPane;
    @FXML private TableView<Almacen> tabla_almacenes;
    @FXML private TableColumn<Almacen, String> almacen_nombre;
    @FXML private TableColumn<Almacen, String> almacen_codigo;
    @FXML private TextField nombre_almacen_buscar;
    @FXML private TextField codigo_almacen_buscar;
    @FXML private TextField almacen_nombre_field;
    @FXML private TextField almacen_logico_nombre_field;
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
    @FXML private TitledPane create_almacen_logico_container;
    @FXML private TextField rack_altura_field;
    @FXML private TitledPane contenedor_grilla;
    @FXML private AnchorPane pane_grilla;
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
        temp_almacen.asignarAtributosAlmacenLogico(
                args.getLargo(), 
                args.getAncho(), 
                args.getX_relativo(), 
                args.getY_relativo(), 
                args.getLongitud_area(),
                args.getNombre(),
                "ALMLGT",
                args.getEs_cental(),
                usuarioActual,
                racks);

        almacenes_logicos.add(temp_almacen);
        almacen_table_view.setItems(almacenes_logicos);
    }
    
    private void afterCreateOrCancelRack() {
        rack_altura_field.clear();
        create_racks_container.setDisable(true);
        contenedor_grilla.setDisable(false);
    }
    
    private void afterCreateOrCancelAlmacen() {
        almacen_logico_nombre_field.clear();
        create_almacen_logico_container.setDisable(true);
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
        
        linearBehavior.getDisableGridEvent().addHandler((sender, args) -> {
            create_racks_container.setDisable(false);
            contenedor_grilla.setDisable(true);
        });

        linearBehavior.getCreateRackEvent().addHandler((sender, args) -> {
            setTempRack((createRackArgs) args);
        });

        if(pane_grilla != null) pane_grilla.getChildren().setAll(grid);
    }
    
    private void createNewRectangularSelectableGrid(int width, int height, int size) {
        RectangularDrawing rectangularBehavior = new RectangularDrawing();
        grid = new SelectableGrid(width, height, size, rectangularBehavior);
        
        rectangularBehavior.getDisableGridEvent().addHandler((sender, args) -> {
            create_almacen_logico_container.setDisable(false);
            contenedor_grilla.setDisable(true);
        });
        
        rectangularBehavior.getCreateLogicalWarehouseEvent().addHandler((sender, args) -> {
            args.setNombre(almacen_logico_nombre_field.getText());
            args.setLongitud_area(grid.getTileSize());
            args.setAncho((args.getAncho()*grid.getTileSize()));
            args.setLargo((args.getLargo()*grid.getTileSize()));
            setTempAlmacen(args);
        });
        
        if(pane_grilla != null) pane_grilla.getChildren().setAll(grid);
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
        if(almacenActuales.size() < 1) {
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
    
    private String generarErrores(String almacenNombre, String almacenLargoStr, String almacenAnchoStr, char almacenEsCentral, ObservableList<Almacen> almacenes, ObservableList<Rack> racks) {
        String almacenNombreError = almacenNombre.equals("") ? "Es necesario que ingrese el nombre de un almacen" : "";
        String almacenLargoError = almacenLargoStr.equals("") ? "Es necesario que ingrese el largo de un almacen" : "";
        String almacenAnchoError = almacenAnchoStr.equals("") ? "Es necesario que ingrese el ancho de un almacen" : "";
        String almacenCentralError = almacenEsCentral == ' ' ? "Es necesario que seleccione un tipo de almacen" : "";
        String almacenesError = almacenes.size() < 1 ? "Es necesario que cree al menos un almacen logico" : "";
        String racksError = racks.size() < 1 ? "Es necesario que cree un al menos un rack" : "";
        StringBuilder errorMessage = new StringBuilder("");
        
        if(almacenNombreError.length() > 0) {
            errorMessage.append(almacenNombreError);
            errorMessage.append(System.lineSeparator());    
        }
        
        if(almacenLargoError.length() > 0) {
            errorMessage.append(almacenLargoError);
            errorMessage.append(System.lineSeparator());
        }
        
        if(almacenAnchoError.length() > 0) {
            errorMessage.append(almacenAnchoError);
            errorMessage.append(System.lineSeparator());    
        }
        
        if(almacenCentralError.length() > 0) {
            errorMessage.append(almacenCentralError);
            errorMessage.append(System.lineSeparator());
        }
        
        if(almacenEsCentral == 'T' && almacenesError.length() > 0) {
            errorMessage.append(almacenesError);
            errorMessage.append(System.lineSeparator());
        }
        
        if(almacenEsCentral == 'F' && racksError.length() > 0) {
            errorMessage.append(racksError);
            errorMessage.append(System.lineSeparator());
        }
        
        
        return errorMessage.toString();
    }
    
    private String generateAlmacenCode(char almacenEsCentral, String almacenCentralCod) {
        String cod = "ALMCT";
        try {
            int index = Integer.valueOf(String.valueOf(Base.firstCell("select last_value from almacenes_almacen_id_seq"))) + 1;
            
            if(almacenEsCentral == 'T') {
                cod = cod.concat(String.format("-%d", index));
            } else {
                cod = almacenCentralCod;
                cod = cod.concat(String.format("-almlog-%d", index));
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        
        return cod;
    }
    
    private void clearAlmacenForm() {
        almacen_nombre_field.clear();
        almacen_largo_field.clear();
        almacen_ancho_field.clear();
        almacen_lado_grilla.clear();
        tipoAlmacen.selectToggle(null);
        almacen_form_pane.setDisable(true);
        list_almacenes_tab.setDisable(true);
        list_racks_tab.setDisable(true);
        almacenes_logicos.clear();
        racks.clear();
    }
    
    @FXML
    public void buscarAlmacen(ActionEvent event) {
        
    }
    
    @FXML
    public void visualizarAlmacen(ActionEvent event) {
        Almacen almacen_seleccionado = tabla_almacenes.getSelectionModel().getSelectedItem();
        almacen_form_pane.setDisable(false);
        try {
            String nombreAlmacen = String.valueOf(almacen_seleccionado.get("nombre"));
            String largoAlmacenStr = String.valueOf(almacen_seleccionado.get("largo"));
            String anchoAlmacenStr = String.valueOf(almacen_seleccionado.get("ancho"));
            String ladoGrillaAlmacenStr = String.valueOf(almacen_seleccionado.get("longitud_area"));
            int ladoGrillaAlmacen = Double.valueOf(ladoGrillaAlmacenStr).intValue();
            almacen_nombre_field.setText(nombreAlmacen);
            almacen_largo_field.setText(largoAlmacenStr);
            almacen_ancho_field.setText(anchoAlmacenStr);
            almacen_lado_grilla.setText(String.valueOf(ladoGrillaAlmacen));
                
            if( String.valueOf(almacen_seleccionado.get("es_central")).equals("T") ) {
                tipoAlmacen.selectToggle(radio_btn_almacen_fisico);
                radio_btn_almacen_logico.setDisable(true);
                list_objects_pane.getSelectionModel().select(list_almacenes_tab);
                
                LazyList<Almacen> almacenesLogicos = Almacen.find("es_central = ?", 'F');
                almacenes_logicos.clear();
                almacenesLogicos.forEach(almacenes_logicos::add);
                almacen_table_view.setItems(almacenes_logicos);
                
                list_racks_tab.setDisable(true);
                list_almacenes_tab.setDisable(false);
                createNewRectangularSelectableGrid(400, 400, ladoGrillaAlmacen);
                grid.drawAlmacenes(almacenesLogicos, ladoGrillaAlmacen);
            } else {
                tipoAlmacen.selectToggle(radio_btn_almacen_logico);
                radio_btn_almacen_fisico.setDisable(true);
                list_objects_pane.getSelectionModel().select(list_racks_tab);
                
                LazyList<Rack> racks_temporales = almacen_seleccionado.getAll(Rack.class);
                racks.clear();
                racks_temporales.forEach(racks::add);
                rack_table_view.setItems(racks);
                
                list_racks_tab.setDisable(false);
                list_almacenes_tab.setDisable(true);
                createNewLinearSelectableGrid(400, 400, ladoGrillaAlmacen);
                grid.drawRacks(racks_temporales, ladoGrillaAlmacen);
                
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @FXML
    public void crearRack(ActionEvent event) {
        if (!rack_altura_field.getText().equals("")) {
            int altura = Integer.parseInt(String.valueOf(rack_altura_field.getText()));
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
    
    @FXML
    public void cancelarCreacionAlmacen(ActionEvent event) {
        grid.clearCurrentActiveTiles();
        afterCreateOrCancelAlmacen();
    }
    
    @FXML
    public void crearAlmacenLogico(ActionEvent event) {
        if(!almacen_logico_nombre_field.getText().equals("")) {
            temp_almacen.set("nombre", almacen_logico_nombre_field.getText());
            temp_almacen = new Almacen();
            grid.clearAndSaveTempTiles();
            infoController.show("Se creo correctamente el almacen");
            afterCreateOrCancelAlmacen();
        } else {
            warningController.show("Error de Creacion de Almacen", "Se debe ingresar un nombre");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    
    @Override
    public void guardar() {
        RadioButton tipoAlmacenRadio = (RadioButton) tipoAlmacen.getSelectedToggle();
        if(tipoAlmacenRadio != null) {
            String almacenNombre = almacen_nombre_field.getText();
            String almacenLargoStr = almacen_largo_field.getText();
            String almacenAnchoStr = almacen_ancho_field.getText();
            String almacenLongitudAreaStr = almacen_lado_grilla.getText();
            char almacenEsCentral = tipoAlmacenRadio.getText().equals("Almacen Logico") ? 'F' : 'T';
            Almacen almacen = new Almacen();
            
            if(almacen.esValido(almacenNombre,almacenLargoStr, almacenAnchoStr, almacenLongitudAreaStr, almacenEsCentral, almacenes_logicos, racks)) {
                if(almacenEsCentral == 'T') {
                    int almacenLargo = Integer.parseInt(almacenLargoStr);
                    int almacenAncho = Integer.parseInt(almacenAnchoStr);
                    double almacenLongitudArea = Double.parseDouble(almacenLongitudAreaStr);
                    
                    try {
                        String almacenCentralCod = generateAlmacenCode(almacenEsCentral, "");
                        Base.openTransaction();
                        almacen.asignarAtributosAlmacenCentral(almacenNombre,
                                almacenCentralCod,
                                almacenLargo,
                                almacenAncho,
                                almacenLongitudArea,
                                almacenEsCentral,
                                usuarioActual);

                        System.out.println("Almacen con datos");
                        System.out.println(almacen);
                        System.out.println(almacenes_logicos);
                        
                        almacen.saveIt();
                        
                        almacenes_logicos.forEach((almacenLogico) -> {
                            String almacenLogCod = generateAlmacenCode('F', almacenCentralCod);
                            almacenLogico.set("almacen_cod", almacenLogCod);
                            almacenLogico.saveIt();
                        });
                        Base.commitTransaction();
                        clearAlmacenForm();
                        
                        infoController.show("El almacen Central y los almacenes logicos se crearon satisfactoriamente");
                    } catch(Exception e) {
                        infoController.show("El almacen contiene errores : " + e);        
                        Base.rollbackTransaction();
                    }
                    
                } else {
                    System.out.println("No se que hago");
    //                almacen.asignarAtributosAlmacenLogico();                
                }
            } else {
                String errores = generarErrores(almacenNombre,almacenLargoStr, almacenAnchoStr, almacenEsCentral, almacenes_logicos, racks);
                if(errores.length() > 0) {
                    try {
                        LazyList<TipoError> errorList = TipoError.find("error_cod = ?", "VAL400");
                        TipoError error = errorList.get(0);
                        
                        warningController.show(error.getString("descripcion"), errores);    
                    } catch(Exception e) {
                        System.out.println(e);
                    }
                }
            }
        } else {
            warningController.show("Error al crear Almacen", "Es necesario que seleccione el tipo de Almacen");
        }
        
    }
}
