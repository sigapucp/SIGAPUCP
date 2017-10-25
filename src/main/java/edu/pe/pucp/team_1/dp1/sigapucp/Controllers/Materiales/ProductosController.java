/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Materiales;

import edu.pe.pucp.team_1.dp1.sigapucp.Controllers.Controller;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Materiales.TipoProducto;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author herbert
 */
public class ProductosController extends Controller {
    
    @FXML
    private TableView<TipoProducto> tablaProductos;

    @FXML
    private TableColumn<TipoProducto, String> codigoColumna;

    @FXML
    private TableColumn<TipoProducto, String> TipoColumna;
    
    @FXML
    private TextField codProd;

    @FXML
    private TextField idProd;
 
    @FXML
    private TextField tipoProd;

    @FXML
    private TextField cateProd;        
    @Override
    
    public void initialize(URL location, ResourceBundle resources) {
//            List<TipoProducto> registros = TipoProducto.recuperarRegistros();
//            ObservableList<TipoProducto> TablaProductosData = FXCollections.observableArrayList(registros);
//            codigoColumna.setCellValueFactory(cellData -> cellData.getValue().getTipoCod()); 
//            TipoColumna.setCellValueFactory(cellData -> cellData.getValue().getTipoProd());
            
    }
    

    
    @FXML
    private void CargarProductos(ActionEvent event){
        //List<TipoProducto> lista = TipoProducto.inicializarProductos();
        //ObservableList<TipoProducto> listaproductos = FXCollections.observableArrayList(lista);
        //ObservableList<TipoProducto> sublist = FXCollections.observableArrayList( 
        /*             listaproductos.get(1), listaproductos.get(4) );
        System.out.println(sublist);*/
        //System.out.println(lista);
        //tipoProd.setText(lista.get(2).toString());
//        TipoProducto tipo = TipoProducto.findById(1);
//        String name = tipo.getTipCod();
//        String name2 = tipo.getTipoProd();
        String name = TipoProducto.hallarNombreTipoProducto();
        System.out.println(name);
        tipoProd.setText(name);
        String codigo = TipoProducto.hallarCodigoTipoProducto();
        codProd.setText(codigo);
    }
    @FXML
    private void abrirIngresarProductoARack(ActionEvent event) throws IOException{
        Parent main_content_parent = FXMLLoader.load(getClass().getResource("/fxml/Materiales/Producto/IngresarARack.fxml"));
        Scene main_content_scene = new Scene(main_content_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(main_content_scene);
        app_stage.show();
    }
}
