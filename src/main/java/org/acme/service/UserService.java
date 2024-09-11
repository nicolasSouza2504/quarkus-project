package org.acme.service;

import io.netty.util.internal.StringUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.acme.Message;
import org.acme.UserValidationException;
import org.acme.entity.LoginUser;
import org.acme.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    EntityManager entityManager;

    @Transactional(Transactional.TxType.REQUIRED)
    public LoginUser saveUser(LoginUser loginUser) throws Exception {

        validate(loginUser);

        entityManager.persist(loginUser);

        return loginUser;

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public LoginUser updateUser(LoginUser loginUser) throws Exception {

        LoginUser login = LoginUser.findById(loginUser.id);

        login.setName(loginUser.getName());
        login.setPassword(loginUser.getPassword());

        validate(login);

        entityManager.merge(login);

        return login;

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public LoginUser login(LoginUser loginUser) throws Throwable {

        throwErrors(validateLoginFields(loginUser, null));
        LoginUser registeredUser = userRepository.findUser(loginUser);

        if (registeredUser != null) {
            return loginUser;
        } else {
            throw new UserValidationException(new Message("The password or name is incorrect"));
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void validate(LoginUser loginUser) throws Exception {

        List<String> errors = new ArrayList<>();

        validateLoginFields(loginUser, errors);
        validatePassword(loginUser.getPassword(), errors);

        throwErrors(errors);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<String> validateLoginFields(LoginUser loginUser, List<String> errors) {

        if (errors == null) {
            errors = new ArrayList<>();
        }

        if (loginUser != null) {

            if (StringUtil.isNullOrEmpty(loginUser.getName())) {
                errors.add("Field 'name' need to be filled");
            }

            if (StringUtil.isNullOrEmpty(loginUser.getPassword())) {
                errors.add("Field 'password' need to be filled");
            }

        } else {
            errors.add("Fill all the user fields");
        }

        return errors;

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void validatePassword(String password, List<String> errors) throws Exception {

        if (!Pattern.compile("[*&%$#@!\"']").matcher(password).find()) {
            errors.add("Password doesnt contain especial characters");
        }

        if (!Pattern.compile("['0-9']").matcher(password).find()) {
            errors.add("Password doesnt contain numeric characters");
        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void throwErrors(List<String> errors) {

        if (!errors.isEmpty()) {

            String errorString = "";

            for (String error : errors) {
                errorString += " * " + error + "; ";
            }

            throw new UserValidationException(new Message(errorString));

        }

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public void removeUser(String id) {

        List<String> errors = new ArrayList<>();

        if (!StringUtil.isNullOrEmpty(id)) {

            LoginUser user = LoginUser.findById(new Long(id));

            if (user != null) {
                entityManager.remove(user);
            } else {
                errors.add("User not found!");
            }


        } else {
            errors.add("ID can't be null!");
        }

        throwErrors(errors);

    }

}
