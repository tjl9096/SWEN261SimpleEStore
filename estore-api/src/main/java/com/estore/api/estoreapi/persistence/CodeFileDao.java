package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Code;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CodeFileDao implements CodeDao {

    private ObjectMapper objectMapper; //clown to clown communication, clown to clown conversation
    private String filename;    // read or write to

    Map<String, Code> codes; //local cache of all items

    //constructor
    public CodeFileDao(@Value("${codes.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load();
    }

    private Code[] getCodeArray() {
        return getCodeArray(null);
    }

    //get all the codes, potentially search for specific one?
    //then check returned array for length and if = 1 code exists already
    private Code[] getCodeArray(String str) { //if str = null, return all items
        ArrayList<Code> codeList = new ArrayList<>();

        for (Code c : codes.values()) {
            if (str == null || c.getCode().contains(str)) {
                codeList.add(c);
            }
        }

        Code[] codeArray = new Code[codeList.size()];
        codeList.toArray(codeArray);
        return codeArray;
    }

    //put everything into a file
    private boolean save() throws IOException {
        Code[] codeArray = getCodeArray(null);

        //item array -> file
        objectMapper.writeValue(new File(filename), codeArray);
        return true;
    }

    //map codes from file :)
    private boolean load() throws IOException {
        codes = new TreeMap<>();

        Code[] codeArray = objectMapper.readValue(new File(filename),Code[].class); //???? just kinda copied stuff

        for (Code c : codeArray) {
            codes.put(c.getCode(), c);
            //don't need the id part bc no ids
        }

        return true;
    }

    //the one public method, i swear there'll be more smile
    //creates new code
    //TODO: check if code exists already before making it
    //that's a problem for future sam
    @Override
    public Code createCode(Code code) throws IOException {
        synchronized(codes) {
            Code newCode = new Code(code.getCode(), code.getPercent());
            codes.put(newCode.getCode(), newCode);
            save(); //very important :blessed_kirby:
            return newCode; //the other ones return what was created so why not this one
        }
    }

    @Override
    public Code[] getCodes() throws IOException {
        synchronized(codes) {
            return getCodeArray();
        }
    }

    @Override
    public boolean deleteCode(String code) throws IOException {
        synchronized(codes) {
            if (codes.containsKey(code)) {
                codes.remove(code);
                return save();
            } else {
                return false;
            }
        }
    }
    
}
