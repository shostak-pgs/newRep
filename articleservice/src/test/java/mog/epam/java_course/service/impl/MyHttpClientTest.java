package mog.epam.java_course.service.impl;

import mog.epam.java_course.service.ClientRequestException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.*;
import java.net.URL;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class MyHttpClientTest {
    private static final String URL_STRING = "https://jsonplaceholder.typicode.com/posts";

    @Mock
    private HttpEntity entityMock;

    @Mock
    private CloseableHttpClient closeableHttpClientMock;

    @Mock
    CloseableHttpResponse closeableHttpResponseMock;

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
        URL url = new URL(URL_STRING + "/92");
        when(entityMock.getContent()).thenReturn(new ByteArrayInputStream(request.getBytes()));
        when(closeableHttpClientMock.execute(any(HttpGet.class))).thenReturn(closeableHttpResponseMock);
        when(closeableHttpResponseMock.getEntity()).thenReturn(entityMock);
        String actual = new MyHttpClient(closeableHttpClientMock).doGet(url);
        assertEquals(actual, request);
    }

    @Test(expected = ClientRequestException.class)
    public void testDoGetRequestException() throws IOException {
        String request = "{" +
                "\"userId\" \"10\"," +
                "\"id\": \"92\"," +
                "\"title\": \"random title\"," +
                "\"body\": \"body\"" +
                "}";
        URL url = new URL(URL_STRING + "/92");
        when(entityMock.getContent()).thenReturn(new ByteArrayInputStream(request.getBytes()));
        when(closeableHttpClientMock.execute(any(HttpGet.class))).thenThrow(new IOException());
        when(closeableHttpResponseMock.getEntity()).thenReturn(entityMock);
        String actual = new MyHttpClient(closeableHttpClientMock).doGet(url);
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
        URL url = new URL(URL_STRING);
        when(entityMock.getContent()).thenReturn(new ByteArrayInputStream(expected.getBytes()));
        when(closeableHttpClientMock.execute(any(HttpPost.class))).thenReturn(closeableHttpResponseMock);
        when(closeableHttpResponseMock.getEntity()).thenReturn(entityMock);
        String actual = new MyHttpClient(closeableHttpClientMock).doPost(url, "92", request);
        assertEquals(actual, expected);
    }

    @Test(expected = ClientRequestException.class)
    public void testDoPostRequestException() throws IOException {
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
        URL url = new URL(URL_STRING);
        when(entityMock.getContent()).thenReturn(new ByteArrayInputStream(expected.getBytes()));
        when(closeableHttpClientMock.execute(any(HttpPost.class))).thenThrow(new IOException());
        String actual = new MyHttpClient(closeableHttpClientMock).doPost(url, "92", request);
    }

}