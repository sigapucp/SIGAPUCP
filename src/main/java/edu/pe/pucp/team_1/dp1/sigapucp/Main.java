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
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Seguridad/Login.fxml"));
        Scene scene = new Scene(root);
        
        Base.open("org.postgresql.Driver", "jdbc:postgresql://200.16.7.146/sigapucp_db_admin", "sigapucp", "sigapucp");
        FleteVolumen f = new FleteVolumen();
//        Lote lot = new Lote();
//        try{    
//            Base.openTransaction();
//            Menu e = new Menu();
//            e.set("nombre", "Menu");
//            e.set("descripcion","MenuTest");          
//            e.saveIt();
//            Base.commitTransaction();
//        }
//        catch(Exception e){
//            Base.rollbackTransaction();
//         }finally{
//            Base.close();
//         }                 

//        List<Moneda> monedas = Moneda.findAll();
//        System.out.println(monedas); 
        stage.setScene(scene);
        stage.setTitle("SIGAPUCP");
        stage.setResizable(false);       
        stage.show();
    }
}
