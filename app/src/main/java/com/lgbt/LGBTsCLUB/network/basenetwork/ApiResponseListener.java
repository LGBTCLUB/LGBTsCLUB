package com.lgbt.LGBTsCLUB.network.basenetwork;

public interface ApiResponseListener extends ServerResponseListener {

    //success response
    void onSuccess(String str, int requestId);

}
