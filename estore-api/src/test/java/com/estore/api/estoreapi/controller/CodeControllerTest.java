package com.estore.api.estoreapi.controller;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Io;
import org.springframework.boot.test.context.assertj.AssertableWebApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperationsExtensionsKt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

import com.estore.api.estoreapi.model.Code;
import com.estore.api.estoreapi.persistence.CodeDao;

import net.bytebuddy.agent.VirtualMachine.ForHotSpot.Connection.Response;

@Tag("Controller-tier")
public class CodeControllerTest {
    private CodeController codeController;
    private CodeDao mockCodeDao;

    @BeforeEach
    public void setupCodeController() {
        mockCodeDao = mock(CodeDao.class);
        codeController = new CodeController(mockCodeDao);
    }

    @Test
    public void  testGetCodes() throws IOException {
        Code[] codes = new Code[2];
        codes[0] = new Code("secret", 10);
        codes[1] = new Code("wheelsRcool", 25);
        when(mockCodeDao.getCodes()).thenReturn(codes);

        ResponseEntity<Code[]> response = codeController.getCodes();

        assertEquals(codes, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void testGetCodesHandleExcpetion() throws IOException {
        doThrow(new IOException()).when(mockCodeDao).getCodes();

        ResponseEntity<Code[]> response = codeController.getCodes();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void testCreateCode() throws IOException {
        Code code = new Code("test", 15);

        when(mockCodeDao.createCode(code)).thenReturn(code);

        ResponseEntity<Code> response = codeController.createCode(code);

        assertEquals(code, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    @Test
    public void testCreateCodeFailed() throws IOException {
        Code code = new Code("test", 15);

        when(mockCodeDao.createCode(code)).thenReturn(null);

        ResponseEntity<Code> response = codeController.createCode(code);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
    @Test
    public void testCreateCodeHandleException() throws IOException {
        Code code = new Code("test", 15);

        doThrow(new IOException()).when(mockCodeDao).createCode(code);

        ResponseEntity<Code> response = codeController.createCode(code);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void testDeleteCode() throws IOException {
        String code = "test";

        when(mockCodeDao.deleteCode(code)).thenReturn(true);

        ResponseEntity<Code> response = codeController.deleteCode(code);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    @Test
    public void testDeleteCodeFailed() throws IOException {
        String code = "test";

        when(mockCodeDao.deleteCode(code)).thenReturn(false);

        ResponseEntity<Code> response = codeController.deleteCode(code);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    public void testDeleteCodeHandleException() throws IOException {
        String code = "test";

        doThrow(new IOException()).when(mockCodeDao).deleteCode(code);

        ResponseEntity<Code> response = codeController.deleteCode(code);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
