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

import java.util.List;

public class DeleteAccessCommand implements Action, ACTION_CONST {

    @Override
    public String execute(RequestWrapper requestWrapper) throws ActionException {
        String id_user = requestWrapper.getSingleRequestParameter("id_user");
        String id_access = requestWrapper.getSingleRequestParameter("id_access");
        User user = (User) requestWrapper.getSessionAttributes().get(USER);
        UserLogic userLogic = new UserLogic();
        try {
            if(userLogic.deleteAccessUser(Integer.parseInt(id_user), Integer.parseInt(id_access))){
                requestWrapper.getRequestAttributes().put(TIME_MESSAGE_INFO, "message.glad.delete.access");
            } else{
                requestWrapper.getRequestAttributes().put(TIME_MESSAGE_ERROR, "message.error.delete.access");
            }
            LogicResult<List<User>> logicResult = userLogic.getAllUsers(user);
            List<Access> accesses = userLogic.getAllAccess();
            requestWrapper.getRequestAttributes().put(USERS, logicResult.getResult());
            requestWrapper.getRequestAttributes().put("accesses", accesses);
            return ConfigurationManager.getInstance().getProperty(PATH_PAGE_USERS);
        } catch (LogicException e) {
            throw new ActionException(e);
        }
    }
}
