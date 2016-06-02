package kz.project.carrental.action.impl.order;

import kz.project.carrental.action.ACTION_CONST;
import kz.project.carrental.action.Action;
import kz.project.carrental.action.exception.ActionException;
import kz.project.carrental.action.wrapper.RequestWrapper;
import kz.project.carrental.entity.User;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.logic.wrapper.LogicResult;
import kz.project.carrental.manager.ConfigurationManager;
import kz.project.carrental.util.InputUtil;
import kz.project.carrental.entity.Order;
import kz.project.carrental.logic.OrderLogic;

public class OrderChangeStatusAction implements Action, ACTION_CONST {

    @Override
    public String execute(RequestWrapper requestWrapper) throws ActionException {
        User user = (User) requestWrapper.getSessionAttributes().get(USER);
        String newStatus = requestWrapper.getSingleRequestParameter(ORDER_STATUS);
        Integer order_id = InputUtil.strToInt(requestWrapper.getSingleRequestParameter(ORDER_ID));


        OrderLogic orderLogic = new OrderLogic();
        try {
            LogicResult<Order> logicResult = orderLogic.changeOrderStatus(user, order_id, newStatus);
            if (logicResult.noError()) {
                requestWrapper.getRequestAttributes().put(TIME_MESSAGE_INFO, "message.info.order.status.changed");
            } else {
                requestWrapper.getRequestAttributes().put(TIME_MESSAGE_ERROR, logicResult.getErrorMessage());
            }
            requestWrapper.getRequestAttributes().put(ORDER, logicResult.getResult());
            return ConfigurationManager.getInstance().getProperty(PATH_PAGE_ORDER_VIEW);
        } catch (LogicException e) {
            throw new ActionException(e);
        }

    }
}
