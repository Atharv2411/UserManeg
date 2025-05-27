package com.EMS.pojo;

public class Ammount {
    private String period;
    private double totalAmount;

    public Ammount() {}

    public Ammount(String period, double totalAmount) {
        this.period = period;
        this.totalAmount = totalAmount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
