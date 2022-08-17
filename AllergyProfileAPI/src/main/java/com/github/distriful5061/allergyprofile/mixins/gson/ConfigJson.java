package com.github.distriful5061.AllergyProfile.mixins.gson;

public class ConfigJson {
    public ApiConfig httpApiConfig;
    public TokenConfig tokenAuthConfig;

    public ConfigJson(ApiConfig apiConfig, TokenConfig tokenConfig) {
        this.httpApiConfig = apiConfig;
        this.tokenAuthConfig = tokenConfig;
    }

    public ApiConfig getHttpApiConfig() {
        return this.httpApiConfig;
    }

    public TokenConfig getTokenAuthConfig() {
        return this.tokenAuthConfig;
    }
}
