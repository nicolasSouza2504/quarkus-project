package org.acme.resource;

import org.acme.entity.LoginUser;
import org.acme.repository.UserRepository;
import org.acme.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    UserRepository userRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        return Response.ok(new GsonBuilder().create().toJson(LoginUser.listAll())).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(String json) throws Exception {

        Gson gson = new GsonBuilder().create();

        LoginUser loginUser = gson.fromJson(json, LoginUser.class);

        return Response.ok(gson.toJson(userService.saveUser(loginUser))).build();
    }

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String json) throws Throwable {

        Gson gson = new GsonBuilder().create();

        LoginUser loginUser = gson.fromJson(json, LoginUser.class);

        return Response.ok(gson.toJson(userService.login(loginUser))).build();
    }

    @GET
    @Path("find-by-name")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByName(@QueryParam("name") String name) {
        return Response.ok(new GsonBuilder().create().toJson(userRepository.findByName(name))).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(String json) throws Exception {

        Gson gson = new GsonBuilder().create();

        LoginUser loginUser = gson.fromJson(json, LoginUser.class);

        return Response.ok(gson.toJson(userService.updateUser(loginUser))).build();
    }

}
