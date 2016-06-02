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
import org.apache.log4j.Logger;

public class OrderPrepareAction implements Action, ACTION_CONST {

    private static final Logger LOGGER = Logger.getLogger(OrderPrepareAction.class);

    @Override
    public String execute(RequestWrapper requestWrapper) throws ActionException {
        String page = ConfigurationManager.getInstance().getProperty(PATH_PAGE_SORRY);

        User user = (User) requestWrapper.getSessionAttributes().get(USER);


        String carIdParam = requestWrapper.getSingleRequestParameter("car_id");
        Integer carId = InputUtil.strToInt(carIdParam);

        if (carId != null) {

            OrderLogic orderLogic = new OrderLogic();
            try {
                LogicResult<Order> logicResult = orderLogic.prepare(carId, user);
                if (logicResult.noError()) {
                    requestWrapper.getSessionAttributes().put(ORDER, logicResult.getResult());
                    page = ConfigurationManager.getInstance().getProperty(PATH_PAGE_ORDER_PREPARE);
                } else {
                    requestWrapper.getRequestAttributes().put(TIME_MESSAGE_ERROR, logicResult.getErrorMessage());
                    page = ConfigurationManager.getInstance().getProperty(PATH_PAGE_MAIN);
                }
            } catch (LogicException e) {
               throw new ActionException(e);
            }
        }
        return page;
    }
}
