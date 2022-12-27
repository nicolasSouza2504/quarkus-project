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

        if (loginUser.getName() != null && !loginUser.getName().isEmpty()
                && loginUser.getPassword() != null && !loginUser.getPassword().isEmpty()) {

        } else {
            return null;
        }

//        PanacheQuery query = LoginUser.find("select us from LoginUser us where us.name = '" + loginUser + "'");
//        return query.list();
        return null;
    }

}
