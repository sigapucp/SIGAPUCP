/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Despachos.GuiaRemision;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.OrdenCompraxProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarGuiaRemisionArgs;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.agregarInstanciaProductoArgs;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 *
 * @author Gustavo
 */
public class AgregarGuiasRemisionController implements Initializable{
    @FXML
    private TableView<GuiaRemision> tabla_guias;
    @FXML
    private TableColumn<GuiaRemision, String> columna_codigo;
    @FXML
    private TableColumn<GuiaRemision, String> columna_pedido;
    
    @FXML
    private Button boton_agregar_guia;   
    
    private List<GuiaRemision> guias;
    private GuiaRemision guia_devuelta;
    private InformationAlertController infoController;
    
    
    public void llenar_tablas(){
        try{
            ObservableList<GuiaRemision> guias_remision =  FXCollections.observableArrayList();
            guias_remision.addAll(guias);
            for(int i =0; i < guias_remision.size(); i++){
                System.out.println(guias_remision.get(i));
            }
            columna_codigo.setCellValueFactory((TableColumn.CellDataFeatures<GuiaRemision, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("guia_cod")));
            columna_pedido.setCellValueFactory((TableColumn.CellDataFeatures<GuiaRemision, String> p) -> new ReadOnlyObjectWrapper(p.getValue().getString("orden_compra_cod")));
            tabla_guias.setItems(guias_remision);            
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    @FXML
    private void agregar_guia(ActionEvent event){
        guia_devuelta = tabla_guias.getSelectionModel().getSelectedItem();
        if (guia_devuelta == null){
            infoController.show("No ha seleccionado ningun producto"); 
            return;
    
        }
        try{
            devolver_guia_remision.fire(this, new agregarGuiaRemisionArgs(guia_devuelta));
            Stage stage = (Stage) boton_agregar_guia.getScene().getWindow();    
            stage.close();       
        }catch(Exception e){
            infoController.show("Ha ocurrido un error" + e.getMessage()); 
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            llenar_tablas();
        }catch (Exception e){
            infoController.show("Ha ocurrido un error durante el inicio de las guias de remision");
        }
    }

    public AgregarGuiasRemisionController(List<GuiaRemision> guias_remision){
        guias = guias_remision;
    }
    public Event<agregarGuiaRemisionArgs> devolver_guia_remision = new Event<>();          
    
}
