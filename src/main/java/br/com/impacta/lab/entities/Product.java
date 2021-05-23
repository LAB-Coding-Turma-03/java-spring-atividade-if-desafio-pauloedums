package br.com.impacta.lab.entities;

public class Product {
    
    private int code;
    private String description;
    private double value;

    public Product(int code, String description, double value){
        this.code = code;
        this.description = description;
        this.value = value;
    }


    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }

    
    public String resume(String product, String payment, double productValueAfterDiscount){
        return product + " sendo pago " + payment + " custará " + productValueAfterDiscount + " reais";
    }
}