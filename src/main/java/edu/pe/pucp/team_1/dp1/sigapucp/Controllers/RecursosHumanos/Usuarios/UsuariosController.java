/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pe.pucp.team_1.dp1.sigapucp.Controllers.RecursosHumanos.Usuarios;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Hugo
 */

public class UsuariosController implements Initializable{
    @FXML
    private TableColumn<?, ?> ColumnaNombre;

    @FXML
    private TextField CorreoUsuario;

    @FXML
    private TableColumn<?, ?> ColumnaCorreo;

    @FXML
    private TableColumn<?, ?> ColumnaApellido;

    @FXML
    private TextField ApellidoUsuario;

    @FXML
    private TextField NombreUsuario;

    @FXML
    private TableView<?> TablaUsuarios;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

