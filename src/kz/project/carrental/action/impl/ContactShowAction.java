package kz.project.carrental.action.impl;

import kz.project.carrental.action.exception.ActionException;
import kz.project.carrental.action.Action;
import kz.project.carrental.action.wrapper.RequestWrapper;
import kz.project.carrental.manager.ConfigurationManager;

public class ContactShowAction implements Action {

    @Override
    public String execute(RequestWrapper requestWrapper) throws ActionException {
        String page = "";
        page = ConfigurationManager.getInstance().getProperty("path.page.contacts");
        return page;
    }
}
