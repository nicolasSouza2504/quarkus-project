package org.acme;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {
    public String message;

    public Message(String message) {
        this.message = message;
    }

    public String toJson() {
        Gson gson = new GsonBuilder().create();

        return gson.toJson(this);
    }

}
