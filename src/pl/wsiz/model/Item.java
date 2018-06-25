package pl.wsiz.model;

public class Item {

    private int id;
    private String name;
    private String source;
    private String destination;
    private String data;
    private String status;
    private boolean isDir;


    public Item(String name, String source, String destination, String data, String status, boolean isDir) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.data = data;
        this.status = status;
        this.isDir = isDir;
    }

    public Item() {
    }

    public Item(int id, String name, String source, String destination, String data, String status, boolean isDir) {
        this.id = id;
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.data = data;
        this.status = status;
        this.isDir = isDir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    public String toWrite() {
        return  name + "::" + source + "::" + destination + "::" + data + "::" + status + "::" + isDir;

    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", data='" + data + '\'' +
                ", status='" + status + '\'' +
                ", isDir=" + isDir +
                '}';
    }
}