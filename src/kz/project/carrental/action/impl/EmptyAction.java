package kz.project.carrental.action.impl;

import kz.project.carrental.action.ACTION_CONST;
import kz.project.carrental.action.Action;
import kz.project.carrental.action.wrapper.RequestWrapper;
import kz.project.carrental.manager.ConfigurationManager;

public class EmptyAction implements Action, ACTION_CONST {

    @Override
    public String execute(RequestWrapper requestWrapper) {
        return ConfigurationManager.getInstance().getProperty(PATH_PAGE_MAIN);
    }
}
