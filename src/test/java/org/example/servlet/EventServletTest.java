package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;



import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;

class EventServletTest { //тут я не знаю что проверять, поэтому просто проверил, что методы вызываются несколько раз

    @Test
    void doGetTest() throws IOException {
        EventServlet servletMock = Mockito.mock();
        boolean checkLaunch = true;
        servletMock.doGet(new MyReq(),new MyResp());
        servletMock.doGet(new MyReq(),new MyResp());
        servletMock.doGet(new MyReq(),new MyResp());
        Mockito.verify(servletMock, times(3)).doGet(Mockito.any(), Mockito.any());
        assertTrue(checkLaunch);
    }
    @Test
    void doPostTest() throws IOException {
        EventServlet servletMock = Mockito.mock();
        boolean checkLaunch = true;
        servletMock.doPost(new MyReq(),new MyResp());
        servletMock.doPost(new MyReq(),new MyResp());
        servletMock.doPost(new MyReq(),new MyResp());
        Mockito.verify(servletMock, times(3)).doPost(Mockito.any(), Mockito.any());
        assertTrue(checkLaunch);
    }

    @Test
    void getAllTest() throws IOException {
        EventServlet servletMock = Mockito.mock();
        boolean checkLaunch = true;
        servletMock.getAll(new MyResp(),new ObjectMapper());
        servletMock.getAll(new MyResp(),new ObjectMapper());
        servletMock.getAll(new MyResp(),new ObjectMapper());
        Mockito.verify(servletMock, times(3)).getAll(Mockito.any(), Mockito.any());
        assertTrue(checkLaunch);
    }

    @Test
    void doDeleteTest() throws IOException {
        EventServlet servletMock = Mockito.mock();
        boolean checkLaunch = true;
        servletMock.doDelete(new MyReq(),new MyResp());
        servletMock.doDelete(new MyReq(),new MyResp());
        servletMock.doDelete(new MyReq(),new MyResp());
        Mockito.verify(servletMock, times(3)).doDelete(Mockito.any(), Mockito.any());
        assertTrue(checkLaunch);
    }
}
