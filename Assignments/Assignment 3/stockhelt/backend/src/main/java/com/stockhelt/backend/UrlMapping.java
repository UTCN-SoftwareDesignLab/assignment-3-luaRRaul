package com.stockhelt.backend;

public class UrlMapping {
    public static final String API_PATH = "/api";

    public static final String ENTITY = "/{id}";

    public static final String USERS = API_PATH + "/users";
    public static final String PATIENTS = API_PATH + "/patients";
    public static final String APPOINTMENTS = API_PATH + "/appointments";

    public static final String DOCTORS = USERS + "/doctors";

    public static final String TOPIC = API_PATH + "/topic";
    public static final String WS = API_PATH + "/ws";
    public static final String WEBSOCKET = API_PATH + "/my-websocket";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";
}
