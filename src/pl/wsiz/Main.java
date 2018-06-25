package pl.wsiz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.wsiz.controller.MainController;


public class Main extends Application {

    private static String[] arguments;


    @Override
    public void start(Stage primaryStage) throws Exception {


        Parent root = FXMLLoader.load(getClass().getResource("/pl/wsiz/fxml/main.fxml"));
        primaryStage.setTitle("Backup manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);
        primaryStage.show();


    }


    public static void main(String[] args) {
        if (args.length > 0) {

            if("service".equals(args[0].toLowerCase())) {
                new MainController(arguments).copyFile();
                System.exit(0);
            } else{
                launch(args);
            }
            return;
        } else {
            launch(args);
        }
    }


}
