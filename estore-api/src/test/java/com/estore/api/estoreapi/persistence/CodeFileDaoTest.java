package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Code;

import org.apache.logging.log4j.spi.Terminable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Persistence-tier")
public class CodeFileDaoTest {
    CodeFileDao codeFileDao;
    Code[] testCodes;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void setupCodeFileDao() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);

        testCodes = new Code[2];
        testCodes[0] = new Code("secret", 10);
        testCodes[1] = new Code("wheelsRcool", 25);

        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Code[].class))
                .thenReturn(testCodes);
        codeFileDao = new CodeFileDao("doesnt_matter.txt", mockObjectMapper);
    }   

    @Test
    public void testCreateCode() {
        Code code = new Code("newby", 42);

        Code result = assertDoesNotThrow(() -> codeFileDao.createCode(code),
            "Unexpected exception thrown");
        
        assertNotNull(result);

        assertEquals(code.getCode(), result.getCode());
        assertEquals(code.getPercent(), result.getPercent());
    }
    @Test
    public void testGetCodes() {
        Code[] codes = assertDoesNotThrow(() -> codeFileDao.getCodes(),
            "Unexpected excpetion thrown");

        assertEquals(testCodes.length, codes.length);
        for (int i = 0; i < testCodes.length; i++) {
            assertEquals(testCodes[i].getCode(), codes[i].getCode());
            assertEquals(testCodes[i].getPercent(), codes[i].getPercent());
        }
    }
    @Test
    public void testDeleteCode() {
        boolean removed = assertDoesNotThrow(() -> codeFileDao.deleteCode(testCodes[0].getCode()),
            "Unexpected exception thrown");

        assertEquals(true, removed);
        assertEquals(testCodes.length - 1, codeFileDao.codes.size());
    }
    @Test
    public void testDeleteCodeNotFound() {
        boolean removed = assertDoesNotThrow(() -> codeFileDao.deleteCode("Not a code"),
            "Unexpected exception thrown");

        assertEquals(false, removed);
        assertEquals(testCodes.length, codeFileDao.codes.size());
    }
}
