package edu.pe.pucp.team_1.dp1.sigapucp.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Controller_Usuario implements Initializable {

    private Controller_ContenidoPrincipal ventanaPrincipal;
    
    @Autowired
    private void setVentanaPrincipal(Controller_ContenidoPrincipal ventana) {
        this.ventanaPrincipal = ventana;
    }
    
    @FXML
    public void randomAction() {
        System.out.print("Humo");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}