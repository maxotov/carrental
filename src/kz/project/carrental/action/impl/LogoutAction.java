package kz.project.carrental.action.impl;

import kz.project.carrental.action.ACTION_CONST;
import kz.project.carrental.action.Action;
import kz.project.carrental.action.wrapper.RequestWrapper;
import kz.project.carrental.manager.ConfigurationManager;

public class LogoutAction implements Action, ACTION_CONST {


    @Override
    public String execute(RequestWrapper requestWrapper) {
        requestWrapper.clear();
        requestWrapper.getSessionAttributes().put(LOGOUT, true);
        return ConfigurationManager.getInstance().getProperty(PATH_PAGE_MAIN);
    }
}
