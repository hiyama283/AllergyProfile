package com.github.distriful5061.AllergyProfile.mixins.gson;

public class TokenConfig {
    public boolean regenRandomPassWord;
    public String useHMACPassWord;

    public TokenConfig(boolean regenRandomPassWord, String HMACPassWord) {
        this.regenRandomPassWord = regenRandomPassWord;
        this.useHMACPassWord = HMACPassWord;
    }
}
