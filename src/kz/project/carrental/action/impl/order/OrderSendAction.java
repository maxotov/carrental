package kz.project.carrental.action.impl.order;

import kz.project.carrental.action.ACTION_CONST;
import kz.project.carrental.action.exception.ActionException;
import kz.project.carrental.entity.Order;
import kz.project.carrental.entity.User;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.logic.wrapper.LogicResult;
import kz.project.carrental.manager.ConfigurationManager;
import kz.project.carrental.util.InputUtil;
import kz.project.carrental.action.Action;
import kz.project.carrental.logic.OrderLogic;
import kz.project.carrental.action.wrapper.RequestWrapper;
import org.apache.log4j.Logger;

import java.sql.Timestamp;

public class OrderSendAction implements Action, ACTION_CONST {

    private static final Logger LOGGER = Logger.getLogger(OrderSendAction.class);

    private static final String DATETIME_FORMAT = "dd.MM.yyyy HH:mm";

    @Override
    public String execute(RequestWrapper requestWrapper) throws ActionException {

        User user = (User) requestWrapper.getSessionAttributes().get(USER);
        Order order = (Order) requestWrapper.getSessionAttributes().get(ORDER);

        if (order != null) {

            String beginPeriodParameter = requestWrapper.getSingleRequestParameter("beginPeriod");
            String endPeriodParameter = requestWrapper.getSingleRequestParameter("endPeriod");
            Timestamp beginPeriod = InputUtil.strToTimestamp(beginPeriodParameter, DATETIME_FORMAT);
            Timestamp endPeriod = InputUtil.strToTimestamp(endPeriodParameter, DATETIME_FORMAT);

            if (beginPeriod == null || endPeriod == null) {
                requestWrapper.getRequestAttributes().put(TIME_MESSAGE_ERROR, "message.error.order.empty.period");
                return ConfigurationManager.getInstance().getProperty(PATH_PAGE_ORDER_PREPARE);
            }

            order.setBeginRent(beginPeriod);
            order.setEndRent(endPeriod);

            OrderLogic orderLogic = new OrderLogic();
            try {
                LogicResult<Order> logicResult = orderLogic.send(order, user);
                if (logicResult.noError()) {
                    requestWrapper.getRequestAttributes().put(ORDER, logicResult.getResult());
                    return ConfigurationManager.getInstance().getProperty(PATH_PAGE_ORDER_VIEW);
                } else {
                    requestWrapper.getRequestAttributes().put(TIME_MESSAGE_ERROR, logicResult.getErrorMessage());
                    return ConfigurationManager.getInstance().getProperty(PATH_PAGE_ORDER_PREPARE);
                }
            } catch (LogicException e) {
                throw new ActionException(e);
            }
        } else {
            return ConfigurationManager.getInstance().getProperty(PATH_PAGE_CARS);
        }
    }
}
