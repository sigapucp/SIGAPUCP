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
import edu.pe.pucp.team_1.dp1.sigapucp.CustomComponents.SelectableGrid;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.createRackArgs;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Jauma
 */
public class AlmacenesController extends Controller{

    private SelectableGrid grid;
    private ObservableList<Rack> racks;
    private Rack temp_rack;
    private WarningAlertController warningController;
    private InformationAlertController infoController;
    private ConfirmationAlertController confirmatonController;
    @FXML private AnchorPane contenedor_grilla;
    @FXML private TitledPane create_racks_container;
    @FXML private TextField rack_altura_field;
    
    public AlmacenesController() {
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        
        grid = new SelectableGrid(10, 10, 400, 400);
        temp_rack = new Rack();
        warningController = new WarningAlertController();
        infoController = new InformationAlertController();
        confirmatonController = new ConfirmationAlertController();
        racks = FXCollections.observableArrayList();
        
        grid.getCreateRackEvent().addHandler((sender, args) -> {
            create_racks_container.setDisable(false);
            contenedor_grilla.setDisable(true);
            setTempRack(temp_rack, args);
        });
    }
    
    private void setTempRack(Rack rack, createRackArgs args) {
        temp_rack.set("x_ancla1",args.getX_ancla1());
        temp_rack.set("y_ancla1",args.getY_ancla1());
        temp_rack.set("x_ancla2",args.getX_ancla2());
        temp_rack.set("y_ancla2",args.getY_ancla2());
        temp_rack.set("longitud",args.getLongitud());
        temp_rack.set("es_uniforme",args.getIs_uniforme());
        temp_rack.set("estado", "ACTIVO");
    }
    
    private void afterCreateOrCancelRack() {
        rack_altura_field.clear();
        create_racks_container.setDisable(true);
        contenedor_grilla.setDisable(false);
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
        contenedor_grilla.getChildren().add(grid);
    }    
    
}
