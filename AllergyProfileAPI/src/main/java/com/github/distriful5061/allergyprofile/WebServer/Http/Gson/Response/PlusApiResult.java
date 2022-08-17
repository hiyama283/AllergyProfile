package com.github.distriful5061.AllergyProfile.WebServer.Http.Gson.Response;

import com.github.distriful5061.AllergyProfile.WebServer.Http.HttpStatusCode;

public class PlusApiResult {
    public int statusCode;
    public int result;

    public PlusApiResult(HttpStatusCode httpStatusCode, int result) {
        this.statusCode = httpStatusCode.getStatusCode();
        this.result = result;
    }
}
