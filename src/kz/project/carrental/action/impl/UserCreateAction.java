package kz.project.carrental.action.impl;

import kz.project.carrental.action.ACTION_CONST;
import kz.project.carrental.action.Action;
import kz.project.carrental.action.exception.ActionException;
import kz.project.carrental.action.wrapper.RequestWrapper;
import kz.project.carrental.entity.Access;
import kz.project.carrental.logic.UserLogic;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.manager.ConfigurationManager;
import kz.project.carrental.entity.User;
import kz.project.carrental.util.MD5Util;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UserCreateAction implements Action, ACTION_CONST {

    private static final Logger LOGGER = Logger.getLogger(UserCreateAction.class);

    @Override
    public String execute(RequestWrapper requestWrapper) throws ActionException {
        String login = requestWrapper.getSingleRequestParameter(LOGIN);
        String password = requestWrapper.getSingleRequestParameter(PASSWORD);
        String firstName = requestWrapper.getSingleRequestParameter(FIRST_NAME);
        String lastName = requestWrapper.getSingleRequestParameter(LAST_NAME);
        String middleName = requestWrapper.getSingleRequestParameter(MIDDLE_NAME);
        String email = requestWrapper.getSingleRequestParameter(EMAIL);
        String telePhone = requestWrapper.getSingleRequestParameter(TELEPHONE);
        String IIN = requestWrapper.getSingleRequestParameter("iin");
        String category = requestWrapper.getSingleRequestParameter("category");

        if(login != null && password != null && firstName != null && lastName != null && middleName != null
                && email != null && telePhone != null){
            if(login.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() ||
                    middleName.isEmpty() || email.isEmpty() || telePhone.isEmpty()){
                requestWrapper.getRequestAttributes().put(TIME_MESSAGE_INFO,"message.error.login.empty");
            } else {
                try{
                    UserLogic userLogic = new UserLogic();
                    boolean isNewUser = userLogic.checkLoginRegister(login);
                    if(isNewUser){
                        if(userLogic.validateParams(telePhone,email, IIN)){
                            User user = new User();
                            user.setLogin(login);
                            user.setPassword(MD5Util.getHashMD5(password));
                            user.setFirstName(firstName);
                            user.setLastName(lastName);
                            user.setMiddleName(middleName);
                            user.setEmail(email);
                            user.setTelephone(telePhone);
                            user.setIin(IIN);
                            user.setCategory(category);
                            List<Access> accesses = new ArrayList<>();
                            Access accessLogin = userLogic.getAccessById(1);
                            Access accessOrderMake = userLogic.getAccessById(3);
                            accesses.add(accessLogin);
                            accesses.add(accessOrderMake);
                            user.setAccesses(accesses);
                            userLogic.createUser(user);
                            requestWrapper.getRequestAttributes().put(TIME_MESSAGE_INFO, "message.glad.register");
                            return ConfigurationManager.getInstance().getProperty(PATH_PAGE_USER_CREATE);
                        }
                    } else{
                        requestWrapper.getRequestAttributes().put(TIME_MESSAGE_ERROR, "message.error.user.exist");
                    }

                } catch(LogicException e){

                }
            }
        }
        return ConfigurationManager.getInstance().getProperty(PATH_PAGE_USER_CREATE);
    }
}
