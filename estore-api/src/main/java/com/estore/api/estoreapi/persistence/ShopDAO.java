package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Item;

public interface ShopDAO {
    Item[] getItems() throws IOException;
    Item getItem(int id) throws IOException;
    Item addItem(Item item) throws IOException;
    String changeUser(String user) throws IOException;
    boolean clear() throws IOException;
    boolean removeItem(Item item) throws IOException;
    Double getTotal() throws IOException;
}
