package com.estore.api.estoreapi.controller;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
import org.springframework.boot.test.context.assertj.AssertableWebApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperationsExtensionsKt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

import com.estore.api.estoreapi.model.Item;
import com.estore.api.estoreapi.persistence.ItemDao;

@Tag("Controller-tier")
public class ItemControllerTest {
    private ItemController itemController;
    private ItemDao mockItemDao;

    @BeforeEach
    public void setupItemController() {
        mockItemDao = mock(ItemDao.class);
        itemController = new ItemController(mockItemDao);
    }

    @Test       //Tyler Lapiana
    public void testGetItem() throws IOException {
        Item item = new Item(42, "Pinwheel", 5.00, 67);
        when(mockItemDao.getItem(item.getId())).thenReturn(item);

        ResponseEntity<Item> response = itemController.getItem(item.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(item, response.getBody());
    }

    @Test       //Tyler Lapiana
    public void testGetItemNotFound() throws IOException {
        int itemID = 100;
        when(mockItemDao.getItem(itemID)).thenReturn(null);

        ResponseEntity<Item> response = itemController.getItem(itemID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test       //Tyler Lapiana
    public void testGetItemHandleException() throws IOException {
        int itemID = 100;
        doThrow(new IOException()).when(mockItemDao).getItem(itemID);

        ResponseEntity<Item> response = itemController.getItem(itemID);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test       //Tyler Lapiana
    public void testGetItems() throws IOException {
        Item[] items = new Item[2];
        items[0] = new Item(42, "Pinwheel", 1.00, 100);
        items[1] = new Item(43, "Wheel of Fortune", 99.99, 1);
        when(mockItemDao.getItems()).thenReturn(items);

        ResponseEntity<Item[]> response = itemController.getItems();

        assertEquals(items, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test       //Tyler Lapiana
    public void testGetItemsHandleException() throws IOException {
        doThrow(new IOException()).when(mockItemDao).getItems();

        ResponseEntity<Item[]> response = itemController.getItems();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testSearchItems() throws IOException {
        String searchString = "ee";
        Item[] items = new Item[2];
        items[0] = new Item(42, "Pinwheel", 1.00, 100);
        items[1] = new Item(43, "Wheel of Fortune", 99.99, 1);
        when(mockItemDao.findItems(searchString)).thenReturn(items);

        ResponseEntity<Item[]> response = itemController.searchItems(searchString);

        assertEquals(items, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSearchItemsHandleException() throws IOException {
        String searchString = "za";

        doThrow(new IOException()).when(mockItemDao).findItems(searchString);

        ResponseEntity<Item[]> response = itemController.searchItems(searchString);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateItem() throws IOException {
        Item item = new Item(42, "Pinwheel", 1.00, 100);

        when(mockItemDao.createItem(item)).thenReturn(item);

        ResponseEntity<Item> response = itemController.createItem(item);

        assertEquals(item, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testCreateItemFailed() throws IOException {
        Item item = new Item(42, "Pinwheel", 1.00, 100);

        when(mockItemDao.createItem(item)).thenReturn(null);

        ResponseEntity<Item> response = itemController.createItem(item);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testCreateItemHandleException() throws IOException {
        Item item = new Item(42, "Pinwheel", 1.00, 100);

        doThrow(new IOException()).when(mockItemDao).createItem(item);

        ResponseEntity<Item> response = itemController.createItem(item);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testUpdateItem() throws IOException {
        Item item = new Item(42, "Pinwheel", 1.00, 100);

        when(mockItemDao.updateItem(item)).thenReturn(item);
        ResponseEntity<Item> response = itemController.updateItem(item);
        item.setQuantity(99);
        response = itemController.updateItem(item);

        assertEquals(item, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateItemFailed() throws IOException {
        Item item = new Item(42, "Pinwheel", 1.00, 100);

        when(mockItemDao.updateItem(item)).thenReturn(null);

        ResponseEntity<Item> response = itemController.updateItem(item);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());   
    }

    @Test
    public void testUpdateItemHandleException() throws IOException {
        Item item = new Item(42, "Pinwheel", 1.00, 100);

        doThrow(new IOException()).when(mockItemDao).updateItem(item);

        ResponseEntity<Item> response = itemController.updateItem(item);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testDeleteItem() throws IOException {
        int itemId = 42;

        when(mockItemDao.deleteItem(itemId)).thenReturn(true);

        ResponseEntity<Item> response = itemController.deleteItem(itemId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteItemFailed() throws IOException {
        int itemId = 69;

        when(mockItemDao.deleteItem(itemId)).thenReturn(false);

        ResponseEntity<Item> response = itemController.deleteItem(itemId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteItemHandleException() throws IOException {
        int itemId = 420;

        doThrow(new IOException()).when(mockItemDao).deleteItem(itemId);

        ResponseEntity<Item> response = itemController.deleteItem(itemId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
