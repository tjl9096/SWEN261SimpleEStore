package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class CodeTest {

    @Test
    public void testSetters() {
        String code_s = "test";
        int percent = 15;

        Code code = new Code(code_s, percent);

        String expected_code_s = "test2";
        int expected_percent = 42;

        code.setCode(expected_code_s);
        code.setPercent(expected_percent);

        assertEquals(expected_code_s, code.getCode());
        assertEquals(expected_percent, code.getPercent());
    }    
}
