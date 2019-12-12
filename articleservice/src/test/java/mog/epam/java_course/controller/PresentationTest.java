package mog.epam.java_course.controller;

import mog.epam.java_course.bean.Response;
import org.junit.Test;
import java.io.*;
import static org.junit.Assert.*;

public class PresentationTest {

    @Test
    public void showGetResponse() {
        OutputStream actual = new ByteArrayOutputStream();
        String expected = "Article [5]: User [1] Title [\"some title\"] Message [\"some message\"]";
        Response response = new Response("1", "5", "some title", "some message");
        System.setOut(new PrintStream(actual));
        Presentation.showGetResponse(response);
        assertEquals(expected, actual.toString());
    }

        @Test
        public void showPostResponse() {
            OutputStream actual= new ByteArrayOutputStream();
            String expected = "Article [102] has been created: User [2] Title [\"some title\"] Message [\"some message\"]";
            Response response = new Response("2", "102", "some title", "some message");
            System.setOut(new PrintStream(actual));
            Presentation.showPostResponse(response);
            assertEquals(expected, actual.toString());
    }
}