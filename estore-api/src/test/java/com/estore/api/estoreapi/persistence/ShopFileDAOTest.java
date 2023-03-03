package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Item;

import org.apache.naming.factory.ResourceLinkFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class ShopFileDAOTest {
    ShopFileDAO shopFileDAO;
    Item[] testItems;
    Cart[] testCarts;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupShopFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        testItems = new Item[3];
        testItems[0] = new Item(99,"Pinwheel", 0.99, 1);
        testItems[1] = new Item(100,"Wheel of Fortune", 1.99, 1);
        testItems[2] = new Item(101,"Cheese Wheel", 2.99, 1);

        testCarts = new Cart[1];
        testCarts[0] = new Cart("tester", testItems);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Cart[].class))
                .thenReturn(testCarts);
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Item[].class))
                .thenReturn(testItems);
        shopFileDAO = new ShopFileDAO("doesnt_matter.txt", mockObjectMapper);
        shopFileDAO.changeUser("tester");
    }

    @Test
    public void testGetItem() {
        Item item = shopFileDAO.getItem(99);

        assertEquals(testItems[0], item);
    }
    @Test
    public void testGetItemNotFound() {
        Item item = shopFileDAO.getItem(42);

        assertNull(item);
    }
    @Test
    public void testGetItems() {
        Item[] items = shopFileDAO.getItems();

        assertEquals(testItems.length, items.length);
        for (int i = 0; i < testItems.length; i++) {
            assertEquals(testItems[i],items[i]);
        }
    }
    @Test
    public void testGetItemsNoUser() {
        assertDoesNotThrow(() -> shopFileDAO.changeUser(""),
         "Unexpected exception thrown");

        Item[] items = shopFileDAO.getItems();

        assertNull(items);
    }
    @Test
    public void testGetTotal() {
        Double total = shopFileDAO.getTotal();

        total = total * Math.pow(10, 2);
        total = Math.floor(total);
        total = total / Math.pow(10, 2);

        assertEquals(5.97, total);
    }
    @Test
    public void testAddItem() {
        Item item = new Item(42,"Pinwheel v2.0", 0.99, 2);

        assertDoesNotThrow(() -> shopFileDAO.addItem(item),
        "Unexpected exception thrown");
        assertDoesNotThrow(() -> shopFileDAO.addItem(item),
        "Unexpected exception thrown");
        Item result = assertDoesNotThrow(() -> shopFileDAO.addItem(item),
        "Unexpected exception thrown");

        assertNotNull(result);
        Item actual = shopFileDAO.getItem(item.getId());
        assertEquals(actual.getId(), item.getId());
        assertEquals(actual.getName(), item.getName());
        assertEquals(actual.getPrice(), item.getPrice());
        assertEquals(actual.getQuantity(), item.getQuantity());
    }
    @Test
    public void testAddItemNoUser() {
        assertDoesNotThrow(() -> shopFileDAO.changeUser(""),
         "Unexpected exception thrown");

        Item item = new Item(42,"Pinwheel v2.0", 0.99, 2);

        Item result = assertDoesNotThrow(() -> shopFileDAO.addItem(item),
        "Unexpected exception thrown");

        assertNull(result);
    }
    @Test
    public void testClear() {
        boolean cleared = assertDoesNotThrow(() -> shopFileDAO.clear(),
        "Unexpected exception thrown");

        assertEquals(true, cleared);
        Item[] items = shopFileDAO.getItems();
        assertEquals(0, items.length);
    }
    @Test
    public void testClearNoUser() {
        assertDoesNotThrow(() -> shopFileDAO.changeUser(""),
         "Unexpected exception thrown");

        boolean cleared = assertDoesNotThrow(() -> shopFileDAO.clear(),
        "Unexpected exception thrown");

        assertEquals(false, cleared);
    }
    @Test
    public void testRemoveItem() {
        boolean removed = assertDoesNotThrow(() -> shopFileDAO.removeItem(testItems[0]),
        "Unexpected Exception thrown");

        assertEquals(true, removed);
        assertEquals(testItems.length-1, shopFileDAO.items.size());
    }
    @Test
    public void testRemoveItemNotFound() {
        Item item = new Item(42,"Pinwheel v2.0", 0.99, 2);

        boolean removed = assertDoesNotThrow(() -> shopFileDAO.removeItem(item),
        "Unexpected Exception thrown");

        assertEquals(false, removed);
        assertEquals(testItems.length, shopFileDAO.items.size());
    }
    @Test
    public void testRemoveItemNoUser() {
        assertDoesNotThrow(() -> shopFileDAO.changeUser(""),
         "Unexpected exception thrown");

        boolean removed = assertDoesNotThrow(() -> shopFileDAO.removeItem(testItems[0]),
        "Unexpected Exception thrown");

        assertEquals(false, removed);
    }
}
