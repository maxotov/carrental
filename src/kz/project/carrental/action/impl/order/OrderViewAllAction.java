package kz.project.carrental.action.impl.order;

import kz.project.carrental.action.ACTION_CONST;
import kz.project.carrental.action.Action;
import kz.project.carrental.action.exception.ActionException;
import kz.project.carrental.action.wrapper.RequestWrapper;
import kz.project.carrental.entity.User;
import kz.project.carrental.logic.OrderLogic;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.logic.wrapper.LogicResult;
import kz.project.carrental.manager.ConfigurationManager;
import kz.project.carrental.entity.Order;

import java.util.List;

public class OrderViewAllAction implements Action, ACTION_CONST {

    @Override
    public String execute(RequestWrapper requestWrapper) throws ActionException {
        User user = (User) requestWrapper.getSessionAttributes().get(USER);
        OrderLogic orderLogic = new OrderLogic();
        try {
            LogicResult<List<Order>> logicResult = orderLogic.showAll(user);
            if (logicResult.noError()) {
                requestWrapper.getRequestAttributes().put(ORDERS, logicResult.getResult());
            } else {
                requestWrapper.getRequestAttributes().put(TIME_MESSAGE_ERROR, logicResult.getErrorMessage());
            }
            return ConfigurationManager.getInstance().getProperty(PATH_PAGE_ORDER_VIEW_ALL);
        } catch (LogicException e) {
            throw new ActionException(e);
        }
    }
}
