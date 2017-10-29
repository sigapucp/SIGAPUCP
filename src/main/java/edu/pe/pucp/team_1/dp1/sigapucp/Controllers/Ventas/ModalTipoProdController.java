/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Alberto Chang Lopez
 */
public class ModalTipoProdController extends Controller {
    
    @FXML
    private TextField busqCodTip;
    @FXML
    private TextField busqNomTip;
    @FXML
    private ComboBox<?> busqPerTip;
    @FXML
    private TableView<TipoProducto> tabla_tipPro;
    @FXML
    private TableColumn<TipoProducto, String> columna_codTip;
    @FXML
    private TableColumn<TipoProducto, String> columna_nomTip;
    @FXML
    private TableColumn<TipoProducto, String> columna_desTip;

    
    private InformationAlertController infoController;
    private TipoProducto tipo_seleccionado;
    private List<TipoProducto> tipos;
    private final ObservableList<TipoProducto> masterData = FXCollections.observableArrayList();
    
    
    public void limpiar_tabla_index(){
        tabla_tipPro.getItems().clear();
    }
    
    public void cargar_tabla_index(){
        limpiar_tabla_index();
        for( TipoProducto categoria : tipos){
            masterData.add(categoria);
        }
        tabla_tipPro.setEditable(false);
        columna_codTip.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        columna_nomTip.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
        columna_desTip.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("descripcion")));
        tabla_tipPro.setItems(masterData);
    }
    
    public boolean cumple_condicion_busqueda(TipoProducto tipo, String codigo, String nombre){
        boolean match = true;
        if ( codigo.equals("") && nombre.equals("") ){
            match = false;
        }
        else {
            match = (!codigo.equals("")) ? (match && (tipo.get("tipo_cod")).equals(codigo)) : true;
            match = (!nombre.equals("")) ? (match && (tipo.get("nombre")).equals(nombre)) : true;
        }
        return match;
    }
    @FXML
    public void buscar_tipo(ActionEvent event) throws IOException{
        tipos = TipoProducto.findAll();
        masterData.clear();
        try{
            for(TipoProducto tipo : tipos){
                if (cumple_condicion_busqueda(tipo, busqCodTip.getText(), busqNomTip.getText())){
                    masterData.add(tipo);
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public ModalTipoProdController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        
        infoController = new InformationAlertController();
        tipo_seleccionado = null;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tipos = null;
        tipos = TipoProducto.findAll();
        cargar_tabla_index();
    }    
    
}
