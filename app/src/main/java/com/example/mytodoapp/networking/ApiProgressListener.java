package com.example.mytodoapp.networking;

public interface ApiProgressListener {
    void onSuccesOfAPI(Object data);
    void onFailureOfAPI(Object data);
}
