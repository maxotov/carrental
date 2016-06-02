package kz.project.carrental.action.impl;

import kz.project.carrental.action.ACTION_CONST;
import kz.project.carrental.action.Action;
import kz.project.carrental.action.exception.ActionException;
import kz.project.carrental.action.wrapper.RequestWrapper;
import kz.project.carrental.entity.Access;
import kz.project.carrental.entity.User;
import kz.project.carrental.entity.UserAccess;
import kz.project.carrental.logic.UserLogic;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.logic.wrapper.LogicResult;
import kz.project.carrental.manager.ConfigurationManager;
import java.util.ArrayList;
import java.util.List;

public class AddAccessCommand implements Action, ACTION_CONST {

    @Override
    public String execute(RequestWrapper requestWrapper) throws ActionException {
        String id_user = requestWrapper.getSingleRequestParameter("id_user");
        String id_access = requestWrapper.getSingleRequestParameter("id_access");
        User user = (User) requestWrapper.getSessionAttributes().get(USER);
        UserLogic userLogic = new UserLogic();
        int flag = 0;
        try {
            User chosenUser = userLogic.findUserById(Integer.parseInt(id_user));
            List<Access> userAccesses = new ArrayList<>();
            if(!chosenUser.getAccesses().isEmpty()){
                userAccesses.addAll(chosenUser.getAccesses());
                for(Access access: chosenUser.getAccesses()){
                    if(access.getId()==Integer.parseInt(id_access)){
                        flag++;
                    }
                }
            }
            if(flag==0){
                UserAccess userAccess = new UserAccess();
                Access access = userLogic.getAccessById(Integer.parseInt(id_access));
                userAccess.setAccess(access);
                userAccess.setUser(chosenUser);
                userLogic.createUserAccess(userAccess);
                requestWrapper.getRequestAttributes().put(TIME_MESSAGE_INFO, "message.glad.add.access");
            } else{
                requestWrapper.getRequestAttributes().put(TIME_MESSAGE_ERROR, "message.error.access.repeat");
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
