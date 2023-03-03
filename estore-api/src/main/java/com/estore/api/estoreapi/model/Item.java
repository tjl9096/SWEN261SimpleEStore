package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    private static final Logger LOG = Logger.getLogger(Item.class.getName());
    
    static final String STRING_FORMAT = "Item [id=%d, name=%s, price=%.2f, quantity=%d]";
    
    @JsonProperty("id") private int id;
    @JsonProperty("name") private String name;
    @JsonProperty("price") private Double price;
    @JsonProperty("quantity") private int quantity;

    public Item(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("price") Double price, @JsonProperty("quantity") int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {return id;}

    public String getName() {return name;}
    
    public Double getPrice() {return price;}

    public int getQuantity() {return quantity;}
    
    public void setName(String name) {this.name = name;}
    
    public void setPrice(Double price) {this.price = price;}

    public void setQuantity(int quantity) {this.quantity = quantity;}
    
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, price, quantity);
    }
}
