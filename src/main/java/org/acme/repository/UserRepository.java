package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.acme.entity.LoginUser;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UserRepository {

    public List<LoginUser> findByName(String name) {
        PanacheQuery query = LoginUser.find("select us from LoginUser us where us.name = '" + name + "'");
        return query.list();
    }
    public LoginUser findUser(LoginUser loginUser) {

        PanacheQuery query = LoginUser.find("select us from LoginUser us where us.name = '" + loginUser.getName() + "' and us.password = '" +  loginUser.getPassword() + "'");

        return (LoginUser) query.firstResult();
    }

}
