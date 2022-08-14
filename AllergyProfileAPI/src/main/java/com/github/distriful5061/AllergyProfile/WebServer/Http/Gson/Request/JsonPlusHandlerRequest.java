package com.github.distriful5061.AllergyProfile.WebServer.Http.Gson.Request;

public class JsonPlusHandlerRequest {
    public int integer1;
    public int integer2;

    public JsonPlusHandlerRequest(int int1, int int2) {
        this.integer1 = int1;
        this.integer2 = int2;
    }

    public int getPlus() {
        return integer1 + integer2;
    }
}
