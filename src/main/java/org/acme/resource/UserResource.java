package org.acme.resource;

import org.acme.entity.User;
import org.acme.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/user")
public class UserResource {

    @Inject
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return Response.ok(new GsonBuilder().create().toJson(User.listAll())).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(String json) throws Exception {

        Gson gson = new GsonBuilder().create();

        User user = gson.fromJson(json, User.class);

        return Response.ok(gson.toJson(userService.saveUser(user))).build();
    }

}