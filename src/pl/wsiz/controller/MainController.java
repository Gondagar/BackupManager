package pl.wsiz.controller;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.wsiz.io.impl.DataFS;
import pl.wsiz.model.Item;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class MainController {


    static DataFS dataSource;

    @FXML
    TableView tableView;

    @FXML
    Stage stage;

    @FXML
    Label label;

    int ok = 0;
    int error = 0;


    public MainController() {
        dataSource = new DataFS();
    }


    @FXML
    private void addElement() throws IOException {


        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.initModality(Modality.APPLICATION_MODAL);

        System.out.println(System.getProperty("user.dir"));
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/pl/wsiz/fxml/add.fxml")));
        scene.setUserData(tableView);

        dialog.setScene(scene);
        dialog.showAndWait();
        DataFS.readDataFile();
        tableView.setItems(DataFS.getItemsList());
        tableView.refresh();

    }


    @FXML
    private void removeItem() throws IOException {


        Item item = (Item) tableView.getSelectionModel().getSelectedItem();

        dataSource.delete(item);

        dataSource.writeDataFile(tableView.getItems());

    }


    public MainController(String[] args) {
        dataSource = new DataFS();

    }

    @FXML
    private void initialize() {


        ObservableList columns = tableView.getColumns();
        TableColumn o = (TableColumn) columns.get(0);
        o.setCellValueFactory(new PropertyValueFactory<>("id"));


        o = (TableColumn) columns.get(1);
        o.setCellValueFactory(new PropertyValueFactory<>("name"));


        o = (TableColumn) columns.get(2);
        o.setCellValueFactory(new PropertyValueFactory<>("source"));


        o = (TableColumn) columns.get(3);
        o.setCellValueFactory(new PropertyValueFactory<>("destination"));

        o = (TableColumn) columns.get(4);
        o.setCellValueFactory(new PropertyValueFactory<>("data"));


        ObservableList columns3 = tableView.getColumns();
        o = (TableColumn) columns.get(5);
        o.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView.setItems(dataSource.getItemList());


    }


    @FXML
    public void debug() {

        dataSource.getItemList().forEach(System.out::println);
        copyFile();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Result");
        alert.setHeaderText(null);
        if (error == 0) {
            alert.setContentText("All files were successfully saved to the specified paths");
        } else {
            alert.setContentText(ok + " files were successfully saved to the specified paths\n" + error + " files were not backed up.");

        }
        alert.showAndWait();
        ok = 0;
        error = 0;


    }


    public void copyFile() {

        for (Item item : dataSource.getItemList()) {
            try {
                System.out.println("*************************Start***************************");

                File source = new File(item.getSource());
                String sourceName = source.getName();
                File sourseZIP = creatZip(source, item);

                if (sourseZIP != null) {
                    System.out.println("Sourse zip path " + sourseZIP.getPath());
                    item.setData(new Date().toString());
                    item.setStatus("ok");
                    String path = item.getDestination() + "" + sourceName + "[" + getDate() + "].zip";
                    System.out.println("Destination path" + path);
                    File dest = new File(path);
                    System.out.print("Copy file " + sourseZIP.getName());


                    Files.copy(sourseZIP.toPath(), dest.toPath());
                    Files.delete(sourseZIP.toPath());

                    System.out.println(" ...........ОК\n");
                    ok++;
                } else {
                    error++;
                }
            } catch (IOException e) {
                item.setStatus("error");
                item.setData("");
                e.printStackTrace();
                System.out.println("    ----------------    File not exists.");
                error++;
            }
        }
        DataFS.writeDataFile(DataFS.getItemsList());
        DataFS.readDataFile();
    }


    private static String getDate() {


        Date d = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy:hh.mm.ss");

        String formatDate = format1.format(d);//25.02.2013 09:03


        return formatDate;

    }


    public static File creatZip(File file, Item item) {

        final String FILENAME = file.getName();
        final int BUFFER_SIZE = 1024;

        byte[] buffer = new byte[BUFFER_SIZE];

        try {
            System.out.print("Compressing file: " + FILENAME);
            String name = file.getName();


            String zipPath = item.getDestination() + "/";


            String zipFile = zipPath + name.substring(0, name.length() - 4) + ".zip";
            final FileOutputStream fos = new FileOutputStream(zipFile);
            try (ZipOutputStream zos = new ZipOutputStream(fos)) {
                final ZipEntry ze = new ZipEntry(FILENAME);
                zos.putNextEntry(ze);
                try (FileInputStream fis = new FileInputStream(file)) {
                    int length;
                    while ((length = fis.read(buffer)) != -1) {
                        zos.write(buffer, 0, length);
                    }
                }
                zos.closeEntry();
            }
            System.out.println("          ......... OK");
            return new File(zipFile);
        } catch (FileNotFoundException ex) {
            System.out.println(" ---------------- File not found.");
        } catch (IOException ex) {
            ex.printStackTrace();

        }


        return null;
    }


    public void close(ActionEvent actionEvent) {
        System.exit(0);
    }
}
