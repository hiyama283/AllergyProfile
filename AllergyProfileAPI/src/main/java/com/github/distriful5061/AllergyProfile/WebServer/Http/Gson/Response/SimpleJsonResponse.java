package com.github.distriful5061.AllergyProfile.WebServer.Http.Gson.Response;

import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpStatusCode;

public class SimpleJsonResponse {
    public int statusCode;
    public String message;

    public SimpleJsonResponse(HttpStatusCode httpStatusCode, String message) {
        this.statusCode = httpStatusCode.getStatusCode();

        if (message == null) this.message = httpStatusCode.getExtension();
        else this.message = message;
    }
}
