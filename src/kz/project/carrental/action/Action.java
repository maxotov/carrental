package kz.project.carrental.action;

import kz.project.carrental.action.exception.ActionException;
import kz.project.carrental.action.wrapper.RequestWrapper;

public interface Action {

    /**
     * Execute action.
     *
     * @param requestWrapper that contains all necessary data for execute action.
     * @return path to the page to which to forward after execute action.
     */
    public String execute(RequestWrapper requestWrapper) throws ActionException;
}
