package com.github.distriful5061.AllergyProfile.WebServer.Http.Gson.Response;

import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.Header.HttpMethod;
import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpStatusCode;

public class BasicError {
    public int statusCode;
    public String errorMessage;
    public String requestMethod;

    public BasicError(HttpStatusCode httpStatusCode, String message, HttpMethod httpMethod) {
        this.statusCode = httpStatusCode.getStatusCode();

        if (message == null) errorMessage = httpStatusCode.getExtension();
        else errorMessage = message;

        this.requestMethod = httpMethod.toString();
    }
}
