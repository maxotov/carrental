package kz.project.carrental.action.impl;

import kz.project.carrental.action.ACTION_CONST;
import kz.project.carrental.action.exception.ActionException;
import kz.project.carrental.logic.LoginLogic;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.logic.wrapper.LogicResult;
import kz.project.carrental.manager.ConfigurationManager;
import kz.project.carrental.action.Action;
import kz.project.carrental.entity.User;
import kz.project.carrental.action.wrapper.RequestWrapper;
import org.apache.log4j.Logger;

public class LoginAction implements Action, ACTION_CONST {

    private static final Logger LOGGER = Logger.getLogger(LoginAction.class);

    @Override
    public String execute(RequestWrapper requestWrapper) throws ActionException {

        String login = requestWrapper.getSingleRequestParameter(LOGIN);
        String password = requestWrapper.getSingleRequestParameter(PASSWORD);

        if (login != null && password != null) {
            if (login.isEmpty() || password.isEmpty()) {
                requestWrapper.getRequestAttributes().put(TIME_MESSAGE_INFO, "message.error.login.empty");
            } else {
                try {
                    LoginLogic loginLogic = new LoginLogic();
                    LogicResult<User> logicResult = loginLogic.verifyUser(login, password);
                    if (logicResult.noError()) {
                        requestWrapper.getSessionAttributes().put(USER, logicResult.getResult());
                        requestWrapper.getRequestAttributes().put(TIME_MESSAGE_INFO, "message.glad");
                        return ConfigurationManager.getInstance().getProperty(PATH_PAGE_MAIN);
                    } else {
                        requestWrapper.getRequestAttributes().put(TIME_MESSAGE_ERROR, logicResult.getErrorMessage());
                    }
                } catch (LogicException e) {
                    throw new ActionException(e);
                }
            }
        }
        return ConfigurationManager.getInstance().getProperty(PATH_PAGE_LOGIN);

    }
}
