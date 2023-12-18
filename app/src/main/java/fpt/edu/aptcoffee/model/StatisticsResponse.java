package fpt.edu.aptcoffee.model;

import java.util.List;

public class StatisticsResponse {
    private String date;
    private int totalRevenue;
    private int numberOfInvoices;

    private List<InvoiceData> invoiceData;

    public StatisticsResponse() {
    }

    public StatisticsResponse(String date, int totalRevenue, int numberOfInvoices, List<InvoiceData> invoiceData) {
        this.date = date;
        this.totalRevenue = totalRevenue;
        this.numberOfInvoices = numberOfInvoices;
        this.invoiceData = invoiceData;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(int totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getNumberOfInvoices() {
        return numberOfInvoices;
    }

    public void setNumberOfInvoices(int numberOfInvoices) {
        this.numberOfInvoices = numberOfInvoices;
    }

    public List<InvoiceData> getInvoiceData() {
        return invoiceData;
    }

    public void setInvoiceData(List<InvoiceData> invoiceData) {
        this.invoiceData = invoiceData;
    }
}
