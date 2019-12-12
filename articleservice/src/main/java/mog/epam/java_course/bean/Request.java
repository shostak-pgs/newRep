package mog.epam.java_course.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import mog.epam.java_course.service.JSONMapperException;
import mog.epam.java_course.service.impl.MyHttpClient;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Сlass models the POST request to the site and has fields to hold three of the necessary
 * parameters to create a new publication
 */
public class Request {
    private String title;
    private String body;
    private String userId;

    /**
     * An empty constructor need for work Jackson ObjectMapper
     */
    public Request(){};

    public Request(String title, String body, String userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(userId, request.userId) &&
                Objects.equals(title, request.title) &&
                Objects.equals(body, request.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, title, body);
    }

    @Override
    public String toString() {
        return "Request{" +
                "userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public static class RequestBuilder{
        private static final Logger logger = LoggerFactory.getLogger(MyHttpClient.class);
        static{
            PropertyConfigurator.configure("src/logger.properties");
        }

        /**
         * Creates a request in JSON String format by creating request object and mapping it to JSON String.
         * Throws a JSONMapperException if the String have incorrect format
         * @param title title for request object
         * @param message message for request object
         * @param userId userId for request object
         * @return created response in JSON String format
         * @throws JSONMapperException it thrown if the request object can't be converted to JSON String
         */
        public static String buildRequest(String title, String message, String userId) throws JSONMapperException {
            OutputStream output = new ByteArrayOutputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            Request request = new Request(title, message, userId);
            try {
                objectMapper.writeValue(output, request);
            } catch (IOException e) {
                logger.warn("Wrong request to jsonplaceholder.typicode.com", e);
                throw new JSONMapperException("Wrong request to jsonplaceholder.typicode.com", e);
            }
            return output.toString();
        }
    }
}

