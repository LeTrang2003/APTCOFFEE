package fpt.edu.aptcoffee.model;

import java.io.Serializable;
import java.util.List;

public class HoaDonAPI implements Serializable {
    private String idTable;
    private List<String> idProduct;
    private String timeIn;
    private String timeOut;
    private int price;
    private String note;

    public HoaDonAPI(String idTable, List<String> idProduct, String timeIn, String timeOut, int price, String note) {
        this.idTable = idTable;
        this.idProduct = idProduct;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.price = price;
        this.note = note;
    }

    public String getIdTable() {
        return idTable;
    }

    public void setIdTable(String idTable) {
        this.idTable = idTable;
    }

    public List<String> getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(List<String> idProduct) {
        this.idProduct = idProduct;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
