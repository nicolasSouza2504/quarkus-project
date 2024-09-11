package org.acme;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.acme.entity.LoginUser;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class UserValidationException extends WebApplicationException {
    private Message message;

    public UserValidationException(Message message) {

        super(Response.status(Response.Status.BAD_REQUEST).entity(message.toJson()).build());
        this.message = message;
    }

}
