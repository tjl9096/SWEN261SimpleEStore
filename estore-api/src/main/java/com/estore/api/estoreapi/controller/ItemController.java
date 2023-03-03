package com.estore.api.estoreapi.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Item;
import com.estore.api.estoreapi.persistence.ItemDao;

@RestController
@RequestMapping("items")
public class ItemController {
    private static final Logger LOG = Logger.getLogger(ItemController.class.getName());
    private ItemDao itemDao;

    public ItemController(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable int id) {
        LOG.info("GET /items/" + id);
        try {
            Item item = itemDao.getItem(id);
            if (item != null)
                return new ResponseEntity<Item>(item, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<Item[]> getItems() {
        LOG.info("GET /items");

        try{
            return new ResponseEntity<>(this.itemDao.getItems(), HttpStatus.OK);
        } catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Item[]> searchItems(@RequestParam String name) {
        LOG.info("GET /items/?name=" + name);

        try{
            return new ResponseEntity<>(this.itemDao.findItems(name), HttpStatus.OK);
        } catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        LOG.info("POST /items " + item);

        try{
            Item newItem = this.itemDao.createItem(item);
            if (newItem != null) {
                return new ResponseEntity<Item>(newItem, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
     
    }

    @PutMapping("")
    public ResponseEntity<Item> updateItem(@RequestBody Item item) {
        LOG.info("PUT /items " + item);

        try {
            Item newitem = itemDao.updateItem(item);
            if (newitem != null)
                return new ResponseEntity<Item>(newitem, HttpStatus.OK);
            else
                return new ResponseEntity<Item>(newitem, HttpStatus.NOT_FOUND);       
        }
        catch (IOException e ) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable int id) {
        LOG.info("DELETE /items/" + id);

        try {
            boolean deleted = itemDao.deleteItem(id);
            if (deleted != false)
                return new ResponseEntity<Item>(HttpStatus.OK);
            else
                return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
        }
        catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
}
