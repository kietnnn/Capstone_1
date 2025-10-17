package com.pluralsigh;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class transaction {
    static LocalDate date;
    private static LocalTime time;
    private  String description;
    private  String vendor;
    private  Double amount;

    public transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
    }

    public static LocalDate getDate() {
        return date;
    }

    public static void setDate(LocalDate date) {
        transaction.date = date;
    }

    public static LocalTime getTime() {
        return time;
    }

    public static void setTime(LocalTime time) {
        transaction.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void display() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH mm ss");

        StringBuilder builder = new StringBuilder();
        builder.append(dateFormatter)
                .append(" ")
                .append(timeFormatter)
                .append("\t")
                .append(description)
                .append(" minutes")
                .append("\n")
                .append(vendor)
                .append("\n")
                .append(amount)
                .append("__________________________________________________________________________");

    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy MM dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH mm ss");
        String transactionAsString = String.format("%s","%s","%s","%s","%.2f",dateFormatter,timeFormatter,description,vendor,amount);
        return "transaction";
    }

}

