/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Modales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Ventas.PromocionesController;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.Event;
import edu.pe.pucp.team_1.dp1.sigapucp.CustomEvents.IEvent;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Navegacion.abrirModalPromoArgs;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

/**
 * FXML Controller class
 *
 * @author Alberto Chang Lopez
 */
public class ModalCatProdController extends ModalController {

    @FXML
    private TextField busqCodCat;
    @FXML
    private TextField busqNomCat;
    @FXML
    private TableView<CategoriaProducto> tabla_catProd;
    @FXML
    private TableColumn<CategoriaProducto, String> columna_codcat;
    @FXML
    private TableColumn<CategoriaProducto, String> columna_nomcat;
    @FXML
    private TableColumn<CategoriaProducto, String> columna_descat;

    
    private InformationAlertController infoController;
    private CategoriaProducto categoria_seleccionada;
    private List<CategoriaProducto> categorias;
    private final ObservableList<CategoriaProducto> masterData = FXCollections.observableArrayList();

    
    public void limpiar_tabla_index(){
        tabla_catProd.getItems().clear();
    }
    
    public void cargar_tabla_index(){
        limpiar_tabla_index();
        for( CategoriaProducto categoria : categorias){
            masterData.add(categoria);
        }
        tabla_catProd.setEditable(false);
        columna_codcat.setCellValueFactory((TableColumn.CellDataFeatures<CategoriaProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("categoria_code")));
        columna_nomcat.setCellValueFactory((TableColumn.CellDataFeatures<CategoriaProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
        columna_descat.setCellValueFactory((TableColumn.CellDataFeatures<CategoriaProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("descripcion")));
        tabla_catProd.setItems(masterData);
    }
    
    public boolean cumple_condicion_busqueda(CategoriaProducto categoria, String codigo, String nombre){
        boolean match = true;
        if ( codigo.equals("") && nombre.equals("") ){
            match = false;
        }
        else {
            match = (!codigo.equals("")) ? (match && (categoria.get("categoria_code")).equals(codigo)) : true;
            match = (!nombre.equals("")) ? (match && (categoria.get("nombre")).equals(nombre)) : true;
        }
        return match;
    }
    @FXML
    public void buscar_categoria(ActionEvent event) throws IOException{
        categorias = CategoriaProducto.findAll();
        masterData.clear();
        try{
            for(CategoriaProducto categoria : categorias){
                if (cumple_condicion_busqueda(categoria, busqCodCat.getText(), busqNomCat.getText())){
                    masterData.add(categoria);
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    @FXML
    private void agregarDatosCat(ActionEvent event) {   
        categoria_seleccionada = tabla_catProd.getSelectionModel().getSelectedItem();
        if(categoria_seleccionada == null) return; 
        
        String codPromo = categoria_seleccionada.getString("categoria_code");
        String idPromo = categoria_seleccionada.getString("categoria_id");
        
        abrirModalPromoArgs args = new abrirModalPromoArgs();
        args.setCodigo(codPromo);
        args.setId(idPromo);
        
        abrirModal.fire(this, args);
        PromocionesController.modal_stage_cat.close();
    }  
    
    public ModalCatProdController(){
        if(!Base.hasConnection()) Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        
        infoController = new InformationAlertController();
        categoria_seleccionada = null;
        
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        categorias = null;
        categorias = CategoriaProducto.findAll();
        cargar_tabla_index();        
    }        
}
