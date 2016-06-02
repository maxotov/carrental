package kz.project.carrental.action.impl;

import kz.project.carrental.action.ACTION_CONST;
import kz.project.carrental.action.Action;
import kz.project.carrental.action.exception.ActionException;
import kz.project.carrental.action.wrapper.RequestWrapper;
import kz.project.carrental.entity.Access;
import kz.project.carrental.entity.User;
import kz.project.carrental.logic.UserLogic;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.logic.wrapper.LogicResult;
import kz.project.carrental.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import java.util.List;

public class UserViewAction implements Action, ACTION_CONST {

    private static final Logger LOGGER = Logger.getLogger(UserViewAction.class);

    @Override
    public String execute(RequestWrapper requestWrapper) throws ActionException {
        UserLogic userLogic = new UserLogic();
        User user = (User) requestWrapper.getSessionAttributes().get(USER);

        try {
            LogicResult<List<User>> logicResult = userLogic.getAllUsers(user);
            List<Access> accesses = userLogic.getAllAccess();
            if (logicResult.noError()) {
                requestWrapper.getRequestAttributes().put(USERS, logicResult.getResult());
                requestWrapper.getRequestAttributes().put("accesses", accesses);
            } else {
                requestWrapper.getRequestAttributes().put(TIME_MESSAGE_ERROR, logicResult.getErrorMessage());
            }
            return ConfigurationManager.getInstance().getProperty(PATH_PAGE_USERS);
        } catch (LogicException e) {
            throw new ActionException(e);
        }


    }
}
