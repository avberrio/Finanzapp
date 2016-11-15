package com.example.aldo.finanzapp.Models;

public class BillsClass {
    private String billName;
    private int value;
    private String finishDate;

    public BillsClass (String billName, int value, String finishDate) {
        this.billName = billName;
        this.value = value;
        this.finishDate = finishDate;
    }

    public String getBillName () {
        return this.billName;
    }
    public int getValue () {
        return this.value;
    }
    public String getFinishDate() {
        return this.finishDate;
    }
}