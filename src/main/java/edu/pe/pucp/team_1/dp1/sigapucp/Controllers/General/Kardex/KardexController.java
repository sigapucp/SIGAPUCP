/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.General.Kardex;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Seguridad.InformationAlertController;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.CategoriaProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.RecursosHumanos.Menu;
import java.io.IOException;
import java.util.stream.Collectors;

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
import javafx.scene.layout.AnchorPane;
import org.javalite.activejdbc.Base;

/**
 *
 * @author Hugo
 */
public class KardexController extends Controller {
    
    @FXML
    private AnchorPane kardexProducto;
    
    @FXML
    private TableView<TipoProducto> tablaProductos; 
    
    @FXML
    private TableColumn<TipoProducto,String> ColumnaCodigoProducto;

    @FXML
    private TableColumn<TipoProducto,String> ColumnaTipoProducto;
     
    @FXML
    private TextField tipoProductoBuscar;  
    @FXML
    private TextField codigoProductoBuscar;    
    @FXML
    private TextField producto_nombre;
    @FXML
    private TextField producto_codigo;
    @FXML
    private TextField producto_categoria;
    
    
    private List<TipoProducto> tipos;
    private final ObservableList<TipoProducto> masterData = FXCollections.observableArrayList();
    private InformationAlertController infoController;
    private TipoProducto producto_seleccionado ;
    
    public void inhabilitar_formulario(){
        kardexProducto.setDisable(true);
    }
    
    public void habilitar_formulario(){
        kardexProducto.setDisable(false);
    }
    
    private void cargar_tabla_index(){
        tablaProductos.getItems().clear();
        masterData.clear();
        for (TipoProducto tipo : tipos){
            masterData.add(tipo);
        }
        tablaProductos.setEditable(false);
        ColumnaTipoProducto.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("tipo_cod")));
        ColumnaCodigoProducto.setCellValueFactory((TableColumn.CellDataFeatures<TipoProducto, String> p) -> new ReadOnlyObjectWrapper(p.getValue().get("nombre")));
        tablaProductos.getItems().addAll(masterData);
        
    }
    
    @FXML
    public void mostrar_detalle_tipo_producto(ActionEvent event) throws IOException{
        try{
            TipoProducto registro_seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
            kardexProducto.setDisable(false);
            producto_nombre.setText(registro_seleccionado.getString("nombre"));
            producto_codigo.setText(registro_seleccionado.getString("tipo_cod"));
            //TODO
        }
        catch( Exception e){
            System.out.println(e);
        }        
    }  
    
    @FXML
    public void buscar_producto(ActionEvent event)throws IOException{
        String codigo_producto = codigoProductoBuscar.getText();
        String nombre_producto = tipoProductoBuscar.getText();
        List<TipoProducto> temp_productos = TipoProducto.findAll();
        
        if(codigo_producto!=null&&!codigo_producto.isEmpty())
        {            
            temp_productos = temp_productos.stream().filter(p -> p.getString("tipo_cod").equals(codigo_producto)).collect(Collectors.toList());
        }

        if(nombre_producto!=null&&!nombre_producto.isEmpty())
        {            
            temp_productos = temp_productos.stream().filter(p -> p.getString("nombre").equals(nombre_producto)).collect(Collectors.toList());
        }
                   
        tipos = temp_productos;
        cargar_tabla_index();
        try {                        
        } catch (Exception e) {
            infoController.show("El producto contiene errores : " + e);                    
        }        
    }
    
    public KardexController(){
        Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");       
        producto_seleccionado = null;
        infoController = new InformationAlertController();
        
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inhabilitar_formulario();
        tipos = null;
        tipos = TipoProducto.findAll();
        cargar_tabla_index();
        tablaProductos.getSelectionModel().selectedIndexProperty().addListener((obs,oldSelection,newSelection) -> {
            if (newSelection != null){
                producto_seleccionado = tablaProductos.getSelectionModel().getSelectedItem();                
                tablaProductos.getSelectionModel().clearSelection();        
            }
        });        
    }

}
