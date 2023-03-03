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
import org.springframework.web.bind.annotation.RestController;

import com.estore.api.estoreapi.model.Item;
import com.estore.api.estoreapi.persistence.ShopDAO;

@RestController
@RequestMapping("shop")
public class ShopController {
    private static final Logger LOG = Logger.getLogger(ItemController.class.getName());
    private ShopDAO shopDAO;

    public ShopController(ShopDAO shopDAO){
        this.shopDAO = shopDAO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable int id) {
        LOG.info("GET /shop/" + id);
        try {
            Item item = shopDAO.getItem(id);
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
        LOG.info("GET /shop");

        try{
            return new ResponseEntity<>(this.shopDAO.getItems(), HttpStatus.OK);
        } catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/total")
    public ResponseEntity<Number> getTotal() {
        LOG.info("GET /total");

        try{
            return new ResponseEntity<>(this.shopDAO.getTotal(), HttpStatus.OK);
        } catch(IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        LOG.info("POST /shop " + item);

        try{
            item = this.shopDAO.addItem(item);
            if (item != null) {
                return new ResponseEntity<Item>(item, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
     
    }

    @PutMapping("")
    public ResponseEntity<String> changeUser(@RequestBody String user) {
        LOG.info("PUT /shop " + user);

        try{
            this.shopDAO.changeUser(user);
            return new ResponseEntity<String>(user, HttpStatus.OK);   
        } catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable int id) {
        LOG.info("DELETE /items/" + id);

        try{
            Item item = this.shopDAO.getItem(id);
            if (item != null){
                boolean deleted = this.shopDAO.removeItem(item);
                if (deleted != false) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
}
