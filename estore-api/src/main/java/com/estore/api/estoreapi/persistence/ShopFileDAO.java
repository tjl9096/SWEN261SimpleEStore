package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.estore.api.estoreapi.model.Cart;
import com.estore.api.estoreapi.model.Item;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class ShopFileDAO implements ShopDAO{

    Map<Integer,Item> items;
    List<Cart> carts;
    private ObjectMapper objectMapper;
    private String user;
    private String filename;

    public ShopFileDAO(@Value("${cart.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.user = "";
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }
    
    @Override
    public Item[] getItems() {
        if(this.user.equals("")) return null;
        synchronized(items) {
            return getItemArray();
        }
    }

    @Override
    public Double getTotal() {
        Item[] items = this.getItemArray();
        Double val = 0.0;
        for(int i = 0; i < items.length; i++) {
            val = val + (items[i].getPrice() * items[i].getQuantity());
        }
        return val;
    }

    @Override
    public Item addItem(Item item) throws IOException {
        if(this.user.equals("")) return null;

        int max = item.getQuantity();
        synchronized(items) {
            boolean found = false;
            int key = -1;
            for(int i : items.keySet()) {
                if(i == item.getId()) {
                    found = true;
                    key = i;
                    break;
                }
            }

            if(found) {
                item.setQuantity(items.get(key).getQuantity() + 1);
                if(item.getQuantity() > max) {
                    item.setQuantity(max);
                }
            }
            else {
                item.setQuantity(1);
            }
}           
            items.put(item.getId(), item);
            save();
            return item;
    }

    @Override
    public String changeUser(String user) throws IOException {
        this.user = user;
        load();
        return user;
    }

    @Override
    public boolean clear() throws IOException {
        if(this.user.equals("")) return false;
        items = new TreeMap<>();
        save();
        return true;
    }

    @Override
    public boolean removeItem(Item item) throws IOException {
        if(this.user.equals("")) return false;
        synchronized(items) {
            if (items.containsKey(item.getId())) {
                items.remove(item.getId());
                save();
                return true;
            }
            return false;
        }
    }

    private boolean save() throws IOException {
        Item[] itemArray = getItemArray();
        Cart[] cartArray = getCartArray();
        
        boolean found = false;
        for(Cart i : cartArray){
            if(i.getUser().equals(this.user)){
                i.setItems(itemArray);
                found = true;
                break;
            }
        }
        if(!found) return false;

        //item array -> file
        objectMapper.writeValue(new File(filename), cartArray);
        return true;
    }

    private boolean load() throws IOException {
        items = new TreeMap<>();
        carts = new ArrayList<>();

        Cart[] cartArray = objectMapper.readValue(new File(filename),Cart[].class);
        Item[] itemArray = new Item[0];
        boolean found = false;
        for(Cart i : cartArray){
            if(!found && i.getUser().equals(this.user)){
                itemArray = i.getItems();
                found = true;
            }
            carts.add(i);
        }
        if(!found){
            carts.add(new Cart(this.user, new Item[0]));
        }

        for(Item i : itemArray) {
            items.put(i.getId(), i);
        }
        return true;
    }

    private Item[] getItemArray() {
        ArrayList<Item> itemList = new ArrayList<>();

        for (Item i : items.values()) {
            itemList.add(i);
        }

        Item[] itemArray = new Item[itemList.size()];
        itemList.toArray(itemArray);
        return itemArray;
    }

    private Cart[] getCartArray() {
        Cart[] cartArray = new Cart[this.carts.size()];
        this.carts.toArray(cartArray);
        return cartArray;
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
    
}
