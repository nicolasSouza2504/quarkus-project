package org.acme.service;

import io.quarkus.runtime.util.StringUtil;
import org.acme.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class UserService {

    @Transactional(Transactional.TxType.REQUIRED)
    public User saveUser(User user) throws Exception {

        validate(user);
        user.persist();

        return user;

    }

    @Transactional(Transactional.TxType.REQUIRED)
    private void validate(User user) throws Exception {
        List<String> errors = new ArrayList<>();

        if (user != null) {

            if (StringUtil.isNullOrEmpty(user.getName())) {
                errors.add("Field 'name' need to be filled");
            }

            validatePassword(user.getPassword(), errors);

        } else {
            errors.add("Fill all the user fields");
        }

        if (!errors.isEmpty()) {

            String errorString = "";

            for (String error : errors) {
                errorString += " * " + error + "; ";
            }

            throw new Exception(errorString);

        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    private void validatePassword(String password, List<String> errors) throws Exception {

        if (!StringUtil.isNullOrEmpty(password)) {

            if (!Pattern.compile("[*&%$#@!\"']").matcher(password).find()) {
                errors.add("Password doesnt contain especial characters");
            }

            if (!Pattern.compile("['0-9']").matcher(password).find()) {
                errors.add("Password doesnt contain numeric characters");
            }

        }

    }

}
