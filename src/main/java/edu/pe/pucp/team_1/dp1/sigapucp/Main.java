package edu.pe.pucp.team_1.dp1.sigapucp;

import edu.pe.pucp.team_1.dp1.sigapucp.Models.Humo;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main extends Application {

    private ConfigurableApplicationContext springContext;
    private Parent rootNode;

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() throws Exception {
        springContext = SpringApplication.run(Main.class);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        rootNode = fxmlLoader.load();
        Humo.prueba_humo();
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(rootNode));
        stage.setTitle("SIGAPUCP");
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
    }
}
