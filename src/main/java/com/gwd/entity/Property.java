package com.gwd.entity;

public class Property {

    private Integer id;
    private String typePaper;
    private boolean hascolor;
    private boolean hasdouble;
    private double price;

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", typePaper='" + typePaper + '\'' +
                ", hascolor=" + hascolor +
                ", hasdouble=" + hasdouble +
                ", price=" + price +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypePaper() {
        return typePaper;
    }

    public void setTypePaper(String typePaper) {
        this.typePaper = typePaper;
    }

    public boolean isHascolor() {
        return hascolor;
    }

    public void setHascolor(boolean hascolor) {
        this.hascolor = hascolor;
    }

    public boolean isHasdouble() {
        return hasdouble;
    }

    public void setHasdouble(boolean hasdouble) {
        this.hasdouble = hasdouble;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
