/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.RecursosHumanos.Usuarios;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Hugo
 */

public class UsuariosController implements Initializable{
    @FXML
    private TableColumn<Usuarios,String> ColumnaNombre;

    @FXML
    private TextField CorreoUsuario;

    @FXML
    private TableColumn<Usuarios,String> ColumnaCorreo;

    @FXML
    private TableColumn<Usuarios,String> ColumnaApellido;

    @FXML
    private TextField ApellidoUsuario;

    @FXML
    private TextField NombreUsuario;

    @FXML
    private TableView<Usuarios> TablaUsuarios;
    
    @FXML
    private AnchorPane usuario_container;
    
    private final ObservableList<Usuarios> masterData = FXCollections.observableArrayList();
    
    @FXML
    public void abrirDetalleUsuario(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/RecursosHumanos/Usuario/Form.fxml"));
            usuario_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void regresarIndex(ActionEvent event) {
        try {
            AnchorPane contenido = FXMLLoader.load(getClass().getResource("/fxml/RecursosHumanos/Usuario/Index.fxml"));
            usuario_container.getChildren().setAll(contenido);
        } catch (IOException ex) {
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    public UsuariosController(){
        masterData.add(new Usuarios("hugo","villanueva","hugo@hugo"));
        masterData.add(new Usuarios("waldo","chavez","w@hugo"));
        masterData.add(new Usuarios("kevin","vega","k@hugo"));
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ColumnaNombre.setCellValueFactory(cellData -> cellData.getValue().NombreProperty());
        ColumnaApellido.setCellValueFactory(cellData -> cellData.getValue().ApellidoProperty());
        ColumnaCorreo.setCellValueFactory(cellData -> cellData.getValue().CorreoProperty());
        
        FilteredList<Usuarios> filteredData = new FilteredList<>(masterData, p-> true);
        
        NombreUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(usuarios -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (usuarios.getNombre().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                return false;
            });
        });
        
        ApellidoUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(usuarios -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (usuarios.getApellido().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                return false;
            });
        });
        
        CorreoUsuario.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(usuarios -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (usuarios.getCorreo().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }
                return false;
            });
        });
        
        SortedList<Usuarios> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(TablaUsuarios.comparatorProperty());
        TablaUsuarios.setItems(sortedData);
        
    }

}

