package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class ItemTest {
    
    @Test
    public void testSetters() {
        int id = 42;
        String name = "Pinwheel";
        Double price = 5.00;
        int quantity = 42;

        Item item = new Item(id, name, price, quantity);

        String expected_name = "Pinwheel v2.0";
        Double expected_price = 9.50;
        int expected_quantity = 41;

        item.setName(expected_name);
        item.setPrice(expected_price);
        item.setQuantity(expected_quantity);

        assertEquals(expected_name, item.getName());
        assertEquals(expected_price, item.getPrice());
        assertEquals(expected_quantity, item.getQuantity());
    }

    @Test
    public void testToString() {
        int id = 42;
        String name = "Pinwheel";
        Double price = 5.00;
        int quantity = 42;
        
        String expected_string = String.format((Item.STRING_FORMAT), id, name, price, quantity);
        Item item = new Item(id, name, price, quantity);

        String actual_string = item.toString();

        assertEquals(expected_string, actual_string);
    }

    @Test
    public void testCtor(){
        int id = 10; 
        String name = "Hamster Wheel (it comes with a hamster)";
        double price = 29.99;
        int quantity = 1; 

        Item item = new Item(id, name, price, quantity);

        assertEquals(id, item.getId());
        assertEquals(name, item.getName());
        assertEquals(price, item.getPrice());
        assertEquals(quantity, item.getQuantity());
    }
}
