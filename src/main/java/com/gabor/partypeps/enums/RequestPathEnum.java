package com.gabor.partypeps.enums;

public enum RequestPathEnum {

    GET_ALL_USERS("users/all"),
    GET_ALL_GROUPS("groups/all"),
    GET_ALL_MESSAGES("messages/all"),
    GET_USER_BY_ID("users/{id}"),
    GET_USER_BY_NAME("users/{name}"),
    GET_GROUP_BY_ID("groups/{id}"),
    GET_MESSAGE_BY_ID("messages/{id}"),
    GET_GROUPS_BY_USER_ID("groups/user/{id}"),
    ADD_USER("users/add"),
    ADD_GROUP("groups/add"),
    ADD_MESSAGE("messages/add"),
    REMOVE_USER_BY_ID("users/remove/myself/{id}"),
    REMOVE_GROUP_BY_ID("groups/remove/{id}"),
    REMOVE_MESSAGE_BY_ID("messages/remove/{id}"),
    GET_ACCESS_TOKEN("oauth/token"),
    REFRESH_ACCESS_TOKEN("oauth/token"),
    CHECK_ACCESS_TOKEN("oauth/check_token"),
    GET_CLIENT_ID("app-security/clientId");
    private String value;

    RequestPathEnum(String value) {
        this.value = value;
    }


    public String getUrl() {
        return this.value;
    }
}
