package pl.wsiz.io.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.wsiz.model.Item;

import java.io.*;
import java.util.Scanner;

public class DataFS {


    static private ObservableList<Item> itemsList = FXCollections.observableArrayList();


    public DataFS() {
        readDataFile();
    }


    public void add(Item item) {

        itemsList.add(item);
    }


    public void update(Item item) {

    }


    public void delete(Item item) {


        itemsList.remove(item);

        File file = new File(System.getProperty("user.dir") + "/src/pl/wsiz/conf/files.cfg");

    }


    public ObservableList<Item> getItemList() {
        return itemsList;
    }

    static public ObservableList<Item> getItemsList() {
        return itemsList;
    }

    public void print() {
        int number = 0;
        System.out.println();
        for (Item item : itemsList) {
            number++;
            System.out.println(number + ") id = " + item.getId() + "; name = " + item.getName() + "; source = " + item.getName() + "; path = " + item.getDestination() + "; status = " + item.getStatus());
        }
    }


    public static void readDataFile() {
        itemsList.clear();
        int cout = 0;
        boolean isDir = false;

        Scanner reader;
        File file = new File(System.getProperty("user.dir") + "/src/pl/wsiz/conf/files.cfg");


        try (FileReader fileReader = new FileReader(file)) {

            reader = new Scanner(fileReader);


            while (reader.hasNextLine()) {

                String line = reader.nextLine();
                System.out.println(line);
                String[] split = line.split("::");

                String name = split[0];
                String source = split[1];
                String destination = split[2];
                String data = split[3];

                if ("true".equals(split[4])) {
                    isDir = true;
                }

                String status = "error";
                if (new File(source).canRead()) {
                    status = "ok";
                }
                itemsList.add(new Item(itemsList.size() + 1, name, source, destination, data, status, isDir));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void writeDataFile(ObservableList items) {

        Scanner reader;
        boolean isDir = false;
        File file = new File(System.getProperty("user.dir") + "/src/pl/wsiz/conf/files.cfg");
        file.delete();
        System.out.println(file.getPath());

        for (Item item : itemsList) {


            try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {

                if (new File(item.getDestination()).isDirectory()) {
                    item.setDir(true);
                }

                System.out.println(item.toWrite());

                writer.println(item.toWrite());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public static void setItemsList(ObservableList<Item> itemsList) {
        DataFS.itemsList = itemsList;
    }
}
