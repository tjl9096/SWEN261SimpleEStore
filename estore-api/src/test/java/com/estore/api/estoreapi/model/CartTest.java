package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class CartTest {
    
    @Test
    public void testToString() {
        String user = "tester";
        
        int id = 42;
        String name = "Pinwheel";
        Double price = 5.00;
        int quantity = 42;

        Item[] items = new Item[1];
        items[0] = new Item(id, name, price, quantity);

        String expectedString = String.format((Cart.STRING_FORMAT), user, items.toString());
        Cart cart = new Cart(user, items);

        String actual = cart.toString();

        assertEquals(expectedString, actual);
    }

    @Test
    public void testSetUser() {
        String user = "tester";
        
        Cart cart = new Cart(user, null);

        String expected_user = "tester2";

        cart.setUser(expected_user);

        assertEquals(expected_user, cart.getUser());
    }
}
