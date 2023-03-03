package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Item;

@Component
public class ItemFileDAO implements ItemDao {

    private static int nextId; //hidden id, iterated with each item :)
    Map<Integer,Item> items; //local cache of all items
    private ObjectMapper objectMapper; //json -> file and back (clown to clown communication)
    private String filename;    // read/write to
    
    public ItemFileDAO(@Value("${items.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    //makes id
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    private Item[] getItemArray() {
        return getItemArray(null);
    }

    //either a search for a string, or just returning all of them
    private Item[] getItemArray(String str) { //if str = null, return all items
        ArrayList<Item> itemList = new ArrayList<>();

        for (Item i : items.values()) {
            if (str == null || i.getName().contains(str)) {
                itemList.add(i);
            }
        }

        Item[] itemArray = new Item[itemList.size()];
        itemList.toArray(itemArray);
        return itemArray;
    }


    private boolean save() throws IOException {
        Item[] itemArray = getItemArray();

        //item array -> file
        objectMapper.writeValue(new File(filename), itemArray);
        return true;
    }

    private boolean load() throws IOException {
        items = new TreeMap<>();

        Item[] itemArray = objectMapper.readValue(new File(filename),Item[].class);

        for (Item i : itemArray) {
            items.put(i.getId(), i);
            if (i.getId() > nextId)
                nextId = i.getId();
        }

        ++nextId;
        return true;
    }
    
    //public methods
    @Override
    public Item[] getItems() {
        synchronized(items) {
            return getItemArray();
        }
    }

    @Override
        public Item[] findItems(String containsText) {
            synchronized(items) {
                return getItemArray(containsText);
            }
        }

    @Override
    public Item getItem(int id) {
        synchronized(items) {
            if (items.containsKey(id))
                return items.get(id);
            else
                return null;
        }
    }

    @Override
    public Item createItem(Item item) throws IOException {
        synchronized(items) {
            Item newItem = new Item(nextId(), item.getName(), item.getPrice(), item.getQuantity());
            items.put(newItem.getId(), newItem);
            save();
            return newItem;
        }
    }

    @Override
    public Item updateItem(Item item) throws IOException {
        synchronized(items) {
            if (items.containsKey(item.getId()) == false)
                return null;

            items.put(item.getId(), item);
            save();
            return item;
        }
    }

    @Override
    public boolean deleteItem(int id) throws IOException {
        synchronized(items) {
            if (items.containsKey(id)) {
                items.remove(id);
                return save();
            }
            else
                return false;
        }
    }
}
