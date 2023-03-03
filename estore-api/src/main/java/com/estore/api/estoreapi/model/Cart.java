package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cart {
    
    static final String STRING_FORMAT = "Cart [user=%s, items=%s]";

    @JsonProperty("user") private String user;
    @JsonProperty("items") private Item[] items;

    public Cart(@JsonProperty("user") String user, @JsonProperty("items") Item[] items) {
        this.user = user;
        this.items = items;
    }

    public String getUser(){
        return this.user;
    }

    public Item[] getItems(){
        return this.items;
    }

    public void setUser(String user){
        this.user = user;
    }

    public void setItems(Item[] items){
        this.items = items;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, user, items.toString());
    }

}
