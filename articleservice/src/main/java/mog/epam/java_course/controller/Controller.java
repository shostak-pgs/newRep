package mog.epam.java_course.controller;

import mog.epam.java_course.bean.Request;
import mog.epam.java_course.bean.Response;
import mog.epam.java_course.service.JSONMapperException;
import mog.epam.java_course.service.ClientRequestException;
import mog.epam.java_course.service.impl.MyHttpClient;
import mog.epam.java_course.service.impl.MyURLClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URL;

/**
 * The program takes three command-line argument and, if the first argument valid,
 * selects the method GET or POST and sends a request to the website "https://jsonplaceholder.typicode.com"
 * with HttpClient or URLClient depends of third argument
 * Displays the result of the query
 */
public class Controller {
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String URL = "URL";
    private static final String URL_STRING = "https://jsonplaceholder.typicode.com/posts";
    private static final Logger logger = LoggerFactory.getLogger(MyHttpClient.class);
    static{
            PropertyConfigurator.configure("src/logger.properties");
        }

    /**
     * Contains main program logic
     * @param args array contains arguments. The first - a method type, could be GET or POST.
     * The second - id of publication, must be > 0.
     * The third - type of a client, HTTP or URL
     * @throws ClientRequestException if it is unable to communicate with the resource
     * @throws JSONMapperException is thrown if an error of parsing occurs
     * @throws ValidatorException thrown if the passed parameters do not correct
     */
    public static void main(String[] args) throws IOException, JSONMapperException, ValidatorException {
        ParameterValidator.validate(args);
        final String METHOD = args[0];
        final String ID = args[1];
        final String CLIENT_TYPE = args[2];

        switch (METHOD) {
            case (GET):
                logger.info("GET request was chosen");
                Response response = Response.ResponseBuilder.buildResponse(sendGetRequest(ID, CLIENT_TYPE));
                Presentation.showGetResponse(response);
                break;
            case (POST):
                logger.info("POST request was chosen");
                Response response2 = Response.ResponseBuilder.buildResponse(sendPostRequest(ID, CLIENT_TYPE));
                Presentation.showPostResponse(response2);
                break;
        }
    }

    /**
     * The method is intended to send a GET request using the client specified by the method argument
     * @param articleId id of the article to request
     * @param ClientType client type for request sending
     * @return the String representation of response
     * @throws IOException it thrown if it's failed server connection or resource closing
     * @throws JSONMapperException it thrown if the response String have incorrect format
     */
    private static String sendGetRequest(String articleId, String ClientType) throws IOException, JSONMapperException {
        String stringResponse;
        URL url = new URL(URL_STRING + "/" + articleId);
        if (ClientType.equals(URL)) {
            logger.info("URL client type was chosen");
            stringResponse = new MyURLClient().doGet(url);
        } else {
            logger.info("HTTP client type request was chosen");
            stringResponse = new MyHttpClient(HttpClientBuilder.create().build()).doGet(url);
        }
        return stringResponse;
    }

    /**
     * The method is intended to send a POST request using the client specified by the method argument
     * @param articleId id on which to store a new publication
     * @param ClientType client type for request sending
     * @return the String representation of response
     * @throws IOException it thrown if it's failed server connection or resource closing
     * @throws JSONMapperException it thrown if the response String have incorrect format
     */
    private static String sendPostRequest(String articleId, String ClientType) throws IOException, JSONMapperException {
        String requestParam = Request.RequestBuilder.buildRequest("ops", "mother clean the window", "2");
        URL url = new URL(URL_STRING);
        String stringResponse;
        if (ClientType.equals(URL)) {
            logger.info("URL client type was chosen");
            stringResponse = new MyURLClient().doPost(url, articleId, requestParam);
        } else {
            logger.info("HTTP client type request was chosen");
            stringResponse =  new MyHttpClient(HttpClientBuilder.create().build()).doPost(url, articleId, requestParam);
        }
        return stringResponse;
    }
}
