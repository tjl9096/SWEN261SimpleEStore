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

import com.estore.api.estoreapi.model.Code;
import com.estore.api.estoreapi.persistence.CodeDao;

//doing this in segments, just like il dottore

@RestController
@RequestMapping("codes")
public class CodeController {
    private static final Logger LOG = Logger.getLogger(CodeController.class.getName());
    private CodeDao codeDao;

    //constructor
    public CodeController(CodeDao codeDAO){
        this.codeDao = codeDAO;
    }

    @GetMapping("")
    public ResponseEntity<Code[]> getCodes() {
        LOG.info("GET /codes");

        try{
            return new ResponseEntity<>(this.codeDao.getCodes(), HttpStatus.OK);
        } catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //alright this better work smile im gonna be so sad otherwise :))
    //now im sad :(
    @PostMapping("")
    public ResponseEntity<Code> createCode(@RequestBody Code code) {
        LOG.info("POST /codes/ " + code);
        try{
            Code newCode = this.codeDao.createCode(code);
            if (newCode != null) {
                return new ResponseEntity<Code>(newCode, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch(IOException e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //this works first try lets gooooo
    @DeleteMapping("/{code}")
    public ResponseEntity<Code> deleteCode(@PathVariable String code) {
        LOG.info("DELETE /codes/" + code);

        try {
            boolean deleted = codeDao.deleteCode(code);
            if (deleted != false)
                return new ResponseEntity<Code>(HttpStatus.OK);
            else
                return new ResponseEntity<Code>(HttpStatus.NOT_FOUND);
        }
        catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
