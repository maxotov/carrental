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
import kz.project.carrental.util.InputUtil;
import kz.project.carrental.entity.Order;

public class OrderViewAction implements Action, ACTION_CONST {

    @Override
    public String execute(RequestWrapper requestWrapper) throws ActionException {
        User user = (User) requestWrapper.getSessionAttributes().get(USER);
        int order_id = InputUtil.strToInt(requestWrapper.getSingleRequestParameter("order_id"));


        OrderLogic orderLogic = new OrderLogic();
        LogicResult<Order> logicResult = null;

        try {
            logicResult = orderLogic.show(user, order_id);
        } catch (LogicException e) {
            throw new ActionException(e);
        }

        if (logicResult.noError()) {
            requestWrapper.getRequestAttributes().put(ORDER, logicResult.getResult());
        } else {
            requestWrapper.getRequestAttributes().put(TIME_MESSAGE_ERROR, logicResult.getErrorMessage());
        }
        return ConfigurationManager.getInstance().getProperty(PATH_PAGE_ORDER_VIEW);

    }
}
