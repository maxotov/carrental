package kz.project.carrental.action.impl;

import kz.project.carrental.action.ACTION_CONST;
import kz.project.carrental.action.Action;
import kz.project.carrental.action.wrapper.RequestWrapper;
import kz.project.carrental.manager.ConfigurationManager;

public class ChangeLanguageAction implements Action,ACTION_CONST {

    @Override
    public String execute(RequestWrapper requestWrapper) {
        String language = requestWrapper.getSingleRequestParameter(LANGUAGE);
        if (language != null) {
            requestWrapper.getSessionAttributes().put("javax.servlet.jsp.jstl.fmt.locale.session", language);
        }
        return ConfigurationManager.getInstance().getProperty(PATH_PAGE_MAIN);
    }
}
