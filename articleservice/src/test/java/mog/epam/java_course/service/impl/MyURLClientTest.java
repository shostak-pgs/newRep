package mog.epam.java_course.service.impl;

import mog.epam.java_course.service.ClientRequestException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class MyURLClientTest {
    @Mock
    private URL urlMock;

    @Mock
    private HttpURLConnection urlConMock;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDoGet() throws IOException {
        String request = "{" +
                "\"userId\" \"10\"," +
                "\"id\": \"92\"," +
                "\"title\": \"random title\"," +
                "\"body\": \"body\"" +
                "}";
        when(urlMock.openConnection()).thenReturn(urlConMock);
        when(urlConMock.getInputStream()).thenReturn(new ByteArrayInputStream(request.getBytes()));
        String actual = new MyURLClient().doGet(urlMock);
        assertEquals(actual, request);
    }

    @Test(expected = ClientRequestException.class)
    public void testDoGetClientException() throws IOException {
        String request = "{" +
                "\"userId\" \"10\"," +
                "\"id\": \"92\"," +
                "\"title\": \"random title\"," +
                "\"body\": \"body\"" +
                "}";
        when(urlMock.openConnection()).thenThrow(new IOException());
        String actual = new MyURLClient().doGet(urlMock);
    }

    @Test
    public void testDoPost() throws IOException {
        String request = "{" +
                "\"title\": \"random title\"," +
                "\"body\": \"body\"" +
                "\"userId\" \"10\"," +
                "}";
        String expected = "{" +
                "\"userId\" \"10\"," +
                "\"id\": \"101\"," +
                "\"title\": \"random title\"," +
                "\"body\": \"body\"" +
                "}";
        when(urlMock.openConnection()).thenReturn(urlConMock);
        when(urlConMock.getInputStream()).thenReturn(new ByteArrayInputStream(expected.getBytes()));
        when(urlConMock.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        when(urlConMock.getResponseCode()).thenReturn(201);
        String actual = new MyURLClient().doPost(urlMock, "92", request);
        assertEquals(actual, expected);
    }

    @Test(expected = ClientRequestException.class)
    public void testDoPostClientException() throws IOException {
        String request = "{" +
                "\"title\": \"random title\"," +
                "\"body\": \"body\"" +
                "\"userId\" \"10\"," +
                "}";
        String expected = "{" +
                "\"userId\" \"10\"," +
                "\"id\": \"101\"," +
                "\"title\": \"random title\"," +
                "\"body\": \"body\"" +
                "}";
        when(urlMock.openConnection()).thenThrow(new IOException());
        String actual = new MyURLClient().doPost(urlMock, "92", request);
        assertEquals(actual, expected);
    }
}