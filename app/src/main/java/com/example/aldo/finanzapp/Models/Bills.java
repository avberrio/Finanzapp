package com.example.aldo.finanzapp.Models;

/**
 * Created by aldo on 13-11-16.
 */

public class Bills {

    private long id;
    private String billName;
    private String amount;
    private String finishDate;
    private String description;
    private String updateStatus;

    public Bills (String billName, String value, String finishDate, String description){
        this.billName = billName;
        this.amount = value;
        this.finishDate = finishDate;
        this.description = description;

    }

    /* colocar getters y setters */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBillName () {
        return billName;
    }

    public void setBillName (String billName) {
        this.billName = billName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFinishDate () {
        return finishDate;
    }

    public void setFinishDate () {
        this.finishDate = finishDate;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }





    public String getUpdateStatus() { return updateStatus; }
    public void setUpdateStatus(String updateStatus) {this.updateStatus = updateStatus; }


    public String toString(){
        return this.getBillName();
    }

}