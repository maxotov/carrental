package kz.project.carrental.action.factory;

import kz.project.carrental.action.Action;
import kz.project.carrental.action.exception.ActionException;
import kz.project.carrental.action.wrapper.RequestWrapper;
import org.apache.log4j.Logger;

public class ActionFactory {

    private static final Logger LOGGER = Logger.getLogger(ActionFactory.class);
    private static final String WRONG_ACTION = "Wrong action";

    /**
     * Defines action by request wrapper.
     *
     * @param requestWrapper witch contains name of action.
     * @return defined action, or else empty action.
     */
    public static Action defineAction(RequestWrapper requestWrapper) {
        Action action = ActionEnum.EMPTY.getAction();

        String actionName = requestWrapper.getActionName();
        if (actionName == null || actionName.isEmpty()) {
            return action;
        }

        try {
            ActionEnum actionEnum = ActionEnum.valueOf(actionName.toUpperCase());
            action = actionEnum.getAction();
        } catch (IllegalArgumentException e) {
            ActionException ex = new ActionException(WRONG_ACTION + " " + actionName, e);
            LOGGER.error("Can't create action", ex);
        }

        return action;
    }
}
