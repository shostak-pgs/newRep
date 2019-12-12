package mog.epam.java_course.service.impl;

import mog.epam.java_course.service.Client;
import mog.epam.java_course.service.ClientRequestException;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Designed to perform GET and POST requests with java.net.URL and java.net.HttpURLConnection classes
 */
public class MyURLClient implements Client {
    private static final Logger logger = LoggerFactory.getLogger(MyHttpClient.class);
    static{
        PropertyConfigurator.configure("src/logger.properties");
    }

    /**
     * Designed to receive publications from the site by the specified publication URL
     * @param url an absolute URL given the base location of the article
     * @return String representation of response in JSON format
     * @throws ClientRequestException thrown if it is unable to connect to the server or such publication does not exist
     */
    @Override
    public String doGet(URL url) throws ClientRequestException {
        PrintStream consoleStream = System.out;
        OutputStream response = new ByteArrayOutputStream();
        HttpURLConnection urlCon = null;
        try {
            urlCon = (HttpURLConnection)url.openConnection();
            logger.info(urlCon.getClass().getName() + " was opened successfully");
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(urlCon.getInputStream()))) {
                System.setOut(new PrintStream(response));
                String str;
                while ((str = reader.readLine()) != null) {
                    System.out.println(str);
                }
            }
        } catch (IOException e) {
            logger.warn("Server connection Exception!", e);
            throw new ClientRequestException("File Not Found!", e);
        } finally {
            if(urlCon != null) {
                urlCon.disconnect();
                logger.info(urlCon.getClass().getName() + " was closed successfully");
            }
        }
        System.setOut(consoleStream);
        logger.info("Response was received with MyURLClient");
        return response.toString().trim();
    }

    /**
     * Designed to store information about a new publication by the specified publication id. If the request
     * is successful, returns the response in JSON format
     * @param url in which need to save a new publication
     * @param articleId id under which you want to post an article
     * @param params settings for saving information about a new publication in JSON format
     * @return String representation of response in JSON format
     * @throws ClientRequestException thrown if it is unable to connect to the server or unable to store publication
     */
    @Override
    public String doPost(URL url, String articleId, String params) throws ClientRequestException {
        StringBuilder response = new StringBuilder();
        HttpURLConnection urlCon = null;
        try {
            urlCon = (HttpURLConnection)url.openConnection();
            logger.info(urlCon.getClass().getName() + " was opened successfully");
            urlCon.setRequestMethod("POST");
            urlCon.setRequestProperty("Content-Type", "application/json");
            urlCon.setRequestProperty("Accept", "application/json");
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            try(OutputStream out = urlCon.getOutputStream()) {
                out.write(params.getBytes(StandardCharsets.UTF_8));
            }
            if (urlCon.getResponseCode() == 201) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(urlCon.getInputStream()))) {
                    String str;
                    while ((str = br.readLine()) != null) {
                        response.append(str.trim());
                    }
                }
            }
        } catch (IOException e) {
            logger.warn("Server connection Exception!", e);
            throw new ClientRequestException("File Not Found!", e);
        } finally {
            if(urlCon != null) {
                urlCon.disconnect();
                logger.info(urlCon.getClass().getName() + " was closed successfully");
            }
        }
        String responseWithSubstitutedId = substituteID(response.toString(), articleId);
        logger.info("Response was received with MyURLClient");
        return responseWithSubstitutedId;
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
