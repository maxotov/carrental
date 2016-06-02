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

public class OrderDeleteAction implements Action, ACTION_CONST {

    @Override
    public String execute(RequestWrapper requestWrapper) throws ActionException {
        User user = (User) requestWrapper.getSessionAttributes().get(USER);
        Integer order_id = InputUtil.strToInt(requestWrapper.getSingleRequestParameter(ORDER_ID));

            OrderLogic orderLogic = new OrderLogic();
            try {
                LogicResult logicResult = orderLogic.deleteOrder(user, order_id);
                if (logicResult.noError()){
                    requestWrapper.getRequestAttributes().put(TIME_MESSAGE_INFO, "message.info.order.delete.successful");
                }else{
                    requestWrapper.getRequestAttributes().put(TIME_MESSAGE_ERROR, logicResult.getErrorMessage());
                }
                return  ConfigurationManager.getInstance().getProperty(PATH_PAGE_ORDER_VIEW_ALL);
            } catch (LogicException e) {
                throw new ActionException(e);
            }
    }
}
