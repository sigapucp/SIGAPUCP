package edu.pe.pucp.team_1.dp1.sigapucp;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.Flete;
import edu.pe.pucp.team_1.dp1.sigapucp.Models.Ventas.FleteVolumen;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.javalite.activejdbc.Base;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Seguridad/Login.fxml"));       
        Scene scene = new Scene(root);
       
        stage.setScene(scene);
        stage.setTitle("SIGAPUCP");
        stage.setResizable(false);       
        stage.show();
    }
}