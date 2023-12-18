package fpt.edu.aptcoffee.model;

public class InvoiceData {
    private String idTable;
    private String idProduct;
    private String timeIn;
    private String timeOut;
    private double price;
    private String note;
    private String dateInvoice;

    public InvoiceData() {
    }

    public InvoiceData(String idTable, String idProduct, String timeIn, String timeOut, double price, String note, String dateInvoice) {
        this.idTable = idTable;
        this.idProduct = idProduct;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.price = price;
        this.note = note;
        this.dateInvoice = dateInvoice;
    }

    public String getIdTable() {
        return idTable;
    }

    public void setIdTable(String idTable) {
        this.idTable = idTable;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDateInvoice() {
        return dateInvoice;
    }

    public void setDateInvoice(String dateInvoice) {
        this.dateInvoice = dateInvoice;
    }
}
