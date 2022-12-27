package org.acme.service;

import io.quarkus.runtime.util.StringUtil;
import org.acme.Message;
import org.acme.UserValidationException;
import org.acme.entity.LoginUser;
import org.acme.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Transactional(Transactional.TxType.REQUIRED)
    public LoginUser saveUser(LoginUser loginUser) throws Exception {

        validate(loginUser);
        loginUser.persist();

        return loginUser;

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public LoginUser login(LoginUser loginUser) throws Throwable {

        LoginUser logedUser = userRepository.findUser(loginUser);

        return loginUser;

    }

    @Transactional(Transactional.TxType.REQUIRED)
    private void validate(LoginUser loginUser) throws Exception {
        List<String> errors = new ArrayList<>();

        if (loginUser != null) {

            if (StringUtil.isNullOrEmpty(loginUser.getName())) {
                errors.add("Field 'name' need to be filled");
            }

            validatePassword(loginUser.getPassword(), errors);

        } else {
            errors.add("Fill all the user fields");
        }

        if (!errors.isEmpty()) {

            String errorString = "";

            for (String error : errors) {
                errorString += " * " + error + "; ";
            }

            throw new UserValidationException(new Message(errorString));

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

        } else {
            errors.add("Fill password");

        }

    }

}
