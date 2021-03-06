package com.lgbt.LGBTsCLUB.network.basenetwork;

public class ServerResponseCode {

    interface httpCodes {
        int STATUS_OK = 200;
        int STATUS_CREATED = 201;
        int STATUS_NO_CONTENT = 204;
        int STATUS_BAD_REQUEST = 400;
        int STATUS_UNAUTHORIZED = 401;
        int STATUS_FORBITTEN = 403;
        int STATUS_NOT_FOUND = 404;
        int STATUS_SERVER_ERROR = 500;
    }
}
