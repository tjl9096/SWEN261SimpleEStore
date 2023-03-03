package com.estore.api.estoreapi.controller;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
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

import com.estore.api.estoreapi.controller.ShopController;
import com.estore.api.estoreapi.model.Item;
import com.estore.api.estoreapi.persistence.ItemDao;
import com.estore.api.estoreapi.persistence.ShopDAO;

import ch.qos.logback.core.joran.conditional.ThenOrElseActionBase;
import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response;

@Tag("Controller-tier")
public class ShopControllerTest {
    private ShopController shopController;
    private ShopDAO mockShopDao;

    @BeforeEach
    public void setupShopController() {
        mockShopDao = mock(ShopDAO.class);
        shopController = new ShopController(mockShopDao);
    }

    @Test
    public void testGetItem() throws IOException {
        Item item = new Item(10, "Tire", 60.00, 2);
        when(mockShopDao.getItem(item.getId())).thenReturn(item);
        
        ResponseEntity<Item> response = shopController.getItem(item.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(item, response.getBody());
    }
    @Test
    public void testGetItemNotFound() throws IOException {
        int itemId = 1000;
        when(mockShopDao.getItem(itemId)).thenReturn(null);
        
        ResponseEntity<Item> response = shopController.getItem(itemId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    public void testGetItemHandleException() throws IOException{
        int itemId = 1000;
        doThrow(new IOException()).when(mockShopDao).getItem(itemId);

        ResponseEntity<Item> response = shopController.getItem(itemId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void testGetItems() throws IOException{
        Item[] items = new Item[2];
        items[0] = new Item(10, "Tire", 60.00, 2);
        items[1] = new Item(11, "Hamster Wheel", 100.00, 1);
        when(mockShopDao.getItems()).thenReturn(items);

        ResponseEntity<Item[]> response = shopController.getItems();

        assertEquals(items, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test       
    public void testGetItemsHandleException() throws IOException {
        doThrow(new IOException()).when(mockShopDao).getItems();

        ResponseEntity<Item[]> response = shopController.getItems();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void testAddItem() throws IOException {
        Item item = new Item(10, "Tire", 60.00, 2);
        //doThrow(new IOException()).when(mockShopDao).addItem(item);
        when(mockShopDao.addItem(item)).thenReturn(item);

        ResponseEntity<Item> response  = shopController.addItem(item);

        assertEquals(item, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    @Test
    public void testAddItemFailed() throws IOException {
        Item item = new Item(10, "Tire", 60.00, 2);

        when(mockShopDao.addItem(item)).thenReturn(null);

        ResponseEntity<Item> response = shopController.addItem(item);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
    @Test
    public void testAddItemHandleException() throws IOException {
        Item item = new Item(10, "Tire", 60.00, 2);

        doThrow(new IOException()).when(mockShopDao).addItem(item);

        ResponseEntity<Item> response = shopController.addItem(item);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void testChangeUser() throws IOException {
        String user = "test";

        when(mockShopDao.changeUser(user)).thenReturn(user);

        ResponseEntity<String> response = shopController.changeUser(user);

        assertEquals(user, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void testChangeUserHandleException() throws IOException {
        String user = "test";

        doThrow(new IOException()).when(mockShopDao).changeUser(user);

        ResponseEntity<String> response = shopController.changeUser(user);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void testDeleteItem() throws IOException {
        int id = 10;
        Item item = new Item(10, "Tire", 60.00, 2);

        when(mockShopDao.getItem(id)).thenReturn(item);
        when(mockShopDao.removeItem(item)).thenReturn(true);

        ResponseEntity<Item> response = shopController.deleteItem(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void testDeleteItemNotDeleted() throws IOException {
        int id = 10;
        Item item = new Item(10, "Tire", 60.00, 2);

        when(mockShopDao.getItem(id)).thenReturn(item);
        when(mockShopDao.removeItem(item)).thenReturn(false);

        ResponseEntity<Item> response = shopController.deleteItem(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    public void testDeleteItemGetFailed() throws IOException {
        int id = 10;

        when(mockShopDao.getItem(id)).thenReturn(null);

        ResponseEntity<Item> response = shopController.deleteItem(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    public void testDeleteItemHandleException() throws IOException {
        int id = 10;

        doThrow(new IOException()).when(mockShopDao).getItem(id);

        ResponseEntity<Item> response = shopController.deleteItem(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void testGetTotal() throws IOException {
        Double totalCost = 5.97;

        when(mockShopDao.getTotal()).thenReturn(totalCost);

        ResponseEntity<Number> response = shopController.getTotal();

        assertEquals(totalCost, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void testGetTotalHandleException() throws IOException {
        doThrow(new IOException()).when(mockShopDao).getTotal();

        ResponseEntity<Number> response = shopController.getTotal();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}