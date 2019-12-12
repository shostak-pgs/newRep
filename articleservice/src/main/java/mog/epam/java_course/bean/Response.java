package mog.epam.java_course.bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import mog.epam.java_course.service.JSONMapperException;
import mog.epam.java_course.service.impl.MyHttpClient;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

/**
 * Сlass models a response from the site "https://jsonplaceholder.typicode.com" and has fields
 * to hold four parameters describing an existing publication
 */
public class Response {
    private String userId;
    private String id;
    private String title;
    private String body;

    /**
     * An empty constructor need for work Jackson ObjectMapper
     */
    public Response(){};

    public Response(String userId, String id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        if (!(o instanceof Response)) return false;
        Response response = (Response) o;
        return userId.equals(response.userId) &&
                id.equals(response.id) &&
                title.equals(response.title) &&
                body.equals(response.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, id, title, body);
    }

    @Override
    public String toString() {
        return "Response{" +
                "userId='" + userId + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
     public static class ResponseBuilder{
         private static final Logger logger = LoggerFactory.getLogger(MyHttpClient.class);
         static{
             PropertyConfigurator.configure("src/logger.properties");
         }

         /**
          * Creates a response object by mapping passed JSON String. Throws a JSONMapperException
          * if the String have incorrect format
          * @param str String in JSON format for parsing
          * @return created response object
          * @throws JSONMapperException it thrown if the String have incorrect format
          */
         public static Response buildResponse(String str) throws JSONMapperException {
             Response response;
             ObjectMapper mapper = new ObjectMapper();
             try {
                 response = mapper.readValue(str, Response.class);
             } catch (IOException e) {
                 logger.warn("Wrong request to jsonplaceholder.typicode.com", e);
                 throw new JSONMapperException("Wrong response from jsonplaceholder.typicode.com", e);
             }
             return response;
         }
     }
}
