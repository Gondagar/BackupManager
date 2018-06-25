package pl.wsiz.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.wsiz.io.impl.DataFS;
import pl.wsiz.model.Item;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class EditDialogController implements Initializable {


    @FXML
    Button add;

    @FXML
    Button close;

    @FXML
    TextField name;

    @FXML
    TextField source;

    @FXML
    TextField destination;

    @FXML
    Label status;

    @FXML
    Stage stage;



    File file;

    private DataFS dataFS = new DataFS();

    private ResourceBundle resourceBundle;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceBundle = resources;
       /* Item item = (Item) add.getScene().getUserData();
        if(item != null) {
            source.setText(item.getSource());
            destination.setText(item.getDestination());
            name.setText(item.getName());
        }*/
    }


    public void selectFrom(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file o dir...");
        file = fileChooser.showOpenDialog(stage);
        name.setText(file.getName());
        source.setText(file.getPath());


    }

    public void selectTo(ActionEvent actionEvent) {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Set backup directory...");
        File file2 = directoryChooser.showDialog(stage);
        destination.setText(file2.getPath()+ "/");

    }

    public void addItem(ActionEvent actionEvent) {
    int size = DataFS.getItemsList().size() + 1;
    Item item = new Item(size, name.getText(), source.getText(), destination.getText(), "", "", file.isDirectory());
    DataFS.getItemsList().add(item);
    close(actionEvent);
    dataFS.writeDataFile(dataFS.getItemList());





    }

    @FXML
    public void close(ActionEvent actionEvent) {

        Stage stage = (Stage) close.getScene().getWindow();
        // do what you have to do
        stage.close();

    }
}
