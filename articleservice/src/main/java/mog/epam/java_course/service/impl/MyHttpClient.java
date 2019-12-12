package mog.epam.java_course.service.impl;

import mog.epam.java_course.service.Client;
import mog.epam.java_course.service.ClientRequestException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.io.*;
import java.net.URL;

/**
 * Designed to perform GET and POST requests to transfered URL
 */
public class MyHttpClient implements Client {
    private static final Logger logger = LoggerFactory.getLogger(MyHttpClient.class);
    static{
        PropertyConfigurator.configure("src/logger.properties");
    }
    private CloseableHttpClient client;

    public MyHttpClient(CloseableHttpClient client){
        this.client = client;
    }

    /**
     * Designed to receive publications from the site by the specified publication id
     * @param url an absolute URL given the base location of the article
     * @return String representation of response in JSON format
     * @throws ClientRequestException thrown if it is unable to connect to the server or such publication does not exist
     */
    @Override
    public String doGet(URL url) throws IOException {
        String result;
        CloseableHttpResponse response = null;
        try {
            HttpGet getArticle = new HttpGet(String.valueOf(url));
            response = client.execute(getArticle);
            logger.info("Received Get response from " + url);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            logger.warn("Server connection Exception!", e);
            throw new ClientRequestException("Server connection Exception!", e);
        } finally {
            if(response != null) {
            response.close();
            logger.info(response.getClass().getName() + " was closed successfully");
        }
            client.close();
            logger.info(client.getClass().getName() + " was closed successfully");
        }
        logger.info("Response was received with MyHTTPClient");
        return result;
    }

    /**
     * Designed to store information about a new publication by the specified publication id
     * @param url in which need to save a new publication
     * @param articleId id under which you want to post an article
     * @param params for saving information about a new publication
     * @return String representation of response in JSON format
     * @throws ClientRequestException thrown if it is unable to connect to the server or unable to store publication
     */
    @Override
    public String doPost(URL url, String articleId, String params) throws IOException {
        String resultWithSubstituteId;
        CloseableHttpResponse response = null;
        try {
            HttpPost postArticle = new HttpPost(String.valueOf(url));
            postArticle.addHeader("content-type", "application/json");
            postArticle.setEntity(new StringEntity(params));
            response = client.execute(postArticle);
            logger.info("Received Get response from " + url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                result.append(str.trim());
            }
            resultWithSubstituteId = substituteID(result.toString(), articleId);
        } catch (IOException e) {
            logger.warn("Server connection Exception!", e);
            throw new ClientRequestException("Server connection Exception!", e);
        } finally {
            if(response != null) {
                response.close();
                logger.info(response.getClass().getName() + " was closed successfully");
            }
        client.close();
        logger.info(client.getClass().getName() + " was closed successfully");
        }
        logger.info("Response was received with MyHTTPClient");
        return resultWithSubstituteId;
    }

    /**
     * The method is intended to substitute publication id
     * @param articleId id under which you want to post an article
     * @param responseBeforeSubstitution response needed to substitute article id
     * @return string with substitute article Id
     */
    private String substituteID(String responseBeforeSubstitution, String articleId) {
        String lineWithRightId;
        int currentId = Integer.parseInt(articleId);
        if (currentId < 100) {
            currentId = 101;
            logger.info(articleId + " was substituted to " + currentId);
        }
        lineWithRightId = "\"id\": \"" + currentId + "\"";
        return responseBeforeSubstitution.replace("\"id\": 101", lineWithRightId);
    }
}

