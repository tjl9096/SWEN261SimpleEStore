package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Item;

public interface ItemDao {
    Item getItem(int id) throws IOException;
    Item[] getItems() throws IOException;
    Item[] findItems(String containsText) throws IOException;
    Item createItem(Item item) throws IOException;
    Item updateItem(Item item) throws IOException;
    boolean deleteItem(int id) throws IOException;
}
