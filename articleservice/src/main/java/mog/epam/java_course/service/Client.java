package mog.epam.java_course.service;

import java.io.IOException;
import java.net.URL;

/**
 * Designed to perform GET and POST requests to transfered URL
 */
public interface Client {

    /**
     * Designed to receive publications from the site by the specified publication URL
     * @param url URL of the required publication
     * @return String representation of response in JSON format
     * @throws ClientRequestException thrown if it is unable to connect to the server or such publication does not exist
     */
     String doGet(URL url) throws IOException;

    /**
     * Designed to store information about a new publication by the specified publication id
     * @param url in which need to save a new publication
     * @param articleId id under which you want to post an article
     * @param params for saving information about a new publication
     * @return String representation of response in JSON format
     * @throws ClientRequestException thrown if it is unable to connect to the server or unable to store publication
     */
     String doPost(URL url, String articleId, String params) throws IOException;
}
