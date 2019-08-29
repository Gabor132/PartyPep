package com.gabor.partypeps.enums;

public enum AuthorityEnum {
    USER("USER"),
    ADMIN("ADMIN");
    private String value;
    AuthorityEnum(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
