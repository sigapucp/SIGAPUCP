package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class ContenidoPrincipalController implements Initializable {

    @FXML private AnchorPane contenedor_modulos_contenido;
    @FXML private AnchorPane contenedor_botones_acciones;
    @FXML private NavegacionLateralController NavegacionLateralController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        NavegacionLateralController.abrirDetalle.addHandler((sender, args) -> {
            try {
                System.out.println(args.getPathContenido());
                AnchorPane contenido = FXMLLoader.load(getClass().getResource(args.getPathContenido()));
                contenedor_modulos_contenido.getChildren().setAll(contenido);
            } catch (IOException ex) {
                Logger.getLogger(ContenidoPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
}
