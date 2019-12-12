package mog.epam.java_course.service;

import java.io.IOException;

public class ClientRequestException extends IOException {
    public ClientRequestException(String str, Exception e) {
        super(str, e);
    }
}
