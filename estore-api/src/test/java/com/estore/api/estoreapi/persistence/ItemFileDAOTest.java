package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.estore.api.estoreapi.model.Item;


@Tag("Persistence-tier")
public class ItemFileDAOTest {
    ItemFileDAO itemFileDAO;
    Item[] testItems;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupHeroFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        testItems = new Item[3];
        testItems[0] = new Item(42, "Pinwheel", 1.00, 100);
        testItems[1] = new Item(43, "Wheel of Fortune", 99.99, 1);
        testItems[2] = new Item(44, "Cheese Wheel", 24.50, 50);

        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Item[].class))
                .thenReturn(testItems);

        itemFileDAO = new ItemFileDAO("doesnt_matter.txt", mockObjectMapper);
    }

    @Test       //Tyler Lapiana
    public void testGetItem() {
        Item item = itemFileDAO.getItem(42);

        assertEquals(testItems[0], item);
    }

    @Test       //Tyler Lapiana
    public void testGetItemNotFound() {
        Item item = itemFileDAO.getItem(100);

        assertNull(item);
    }

    @Test       //Tyler Lapiana
    public void testGetItems() {
        Item[] items = itemFileDAO.getItems();

        assertEquals(items.length, testItems.length);
        for (int i = 0; i < testItems.length; i++) {
            assertEquals(items[i], testItems[i]);
        }
    }

    @Test       //Tyler Lapiana
    public void testFindItems() {
        Item[] items = itemFileDAO.findItems("Whe");

        assertEquals(2, items.length);
        assertEquals(testItems[1], items[0]);
        assertEquals(testItems[2], items[1]);
    }

    @Test
    public void testCreateItem() {
        Item item = new Item(42, "Pinwheel", 1.00, 100);

        Item result = assertDoesNotThrow(() -> itemFileDAO.createItem(item),
                    "Unexpected exception thrown");

        assertNotNull(result);
        Item actual = itemFileDAO.getItem(item.getId());
        assertEquals(actual.getId(), item.getId());
        assertEquals(actual.getName(), item.getName());
        assertEquals(actual.getPrice(), item.getPrice());
        assertEquals(actual.getQuantity(), item.getQuantity());
    }

    @Test
    public void testUpdateItem() {
        Item item = new Item(42, "Pinwheel", 1.00, 99);

        Item result = assertDoesNotThrow(() -> itemFileDAO.updateItem(item),
        "Unexpected exception thrown");

        assertNotNull(result);
        Item actual = itemFileDAO.getItem(item.getId());
        assertEquals(actual, item);
    }

    @Test
    public void testUpdateItemNotFound() {
        Item item = new Item(69, "Not a Wheel", 1.00, 99);

        Item result = assertDoesNotThrow(() -> itemFileDAO.updateItem(item),
        "Unexpected exception thrown");

        assertNull(result);
    }

    @Test
    public void testDeleteItem() {
        boolean result = assertDoesNotThrow(() -> itemFileDAO.deleteItem(42),
        "Unexpected exception thrown");

        assertEquals(result, true);
        assertEquals(itemFileDAO.items.size(), testItems.length-1);
    }

    @Test
    public void testDeleteItemNotFound() {
        boolean result = assertDoesNotThrow(() -> itemFileDAO.deleteItem(69),
        "Unexpected exception thrown");

        assertEquals(result, false);
        assertEquals(itemFileDAO.items.size(), testItems.length);
    }
}
