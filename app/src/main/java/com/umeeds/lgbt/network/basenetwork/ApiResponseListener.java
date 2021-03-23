package com.umeeds.lgbt.network.basenetwork;

public interface ApiResponseListener extends ServerResponseListener {

    //success response
    void onSuccess(String str, int requestId);

}
