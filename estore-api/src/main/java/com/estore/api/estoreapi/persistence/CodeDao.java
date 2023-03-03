package com.estore.api.estoreapi.persistence;

import java.io.IOException;

import com.estore.api.estoreapi.model.Code;

//TODO THE REST OF THESE I KNOW
public interface CodeDao {
    Code createCode(Code code) throws IOException;
    Code[] getCodes() throws IOException;
    boolean deleteCode(String code) throws IOException;
}
