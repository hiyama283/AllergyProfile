package com.github.distriful5061.AllergyProfile.Utils;

public enum HashEnum {
    MD2("MD2", "020x"),
    MD5("MD5", "020x"),
    SHA_1("SHA-1", "040x"),
    SHA_224("SHA-224", "040x"),
    SHA_256("SHA-256", "040x"),
    SHA_384("SHA-384", "040x"),
    SHA_512("SHA-512", "040x"),
    SHA3_224("SHA3-224", "040x"),
    SHA3_256("SHA3-256", "040x"),
    SHA3_384("SHA3-384", "040x"),
    SHA3_512("SHA3-512", "040x"),
    ;

    private final String hashName;
    private final String stringFormat;
    HashEnum(String stringTypeHashName, String useStringFormat) {
        this.hashName = stringTypeHashName;
        this.stringFormat = useStringFormat;
    }

    @Override
    public String toString() {
        return this.hashName;
    }

    public String getStringFormat() {
        return this.stringFormat;
    }
}
