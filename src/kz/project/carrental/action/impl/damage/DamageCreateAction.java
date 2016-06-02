package kz.project.carrental.action.impl.damage;

import kz.project.carrental.action.ACTION_CONST;
import kz.project.carrental.action.Action;
import kz.project.carrental.action.exception.ActionException;
import kz.project.carrental.action.wrapper.RequestWrapper;
import kz.project.carrental.entity.User;
import kz.project.carrental.logic.DamageLogic;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.logic.wrapper.LogicResult;
import kz.project.carrental.manager.ConfigurationManager;
import kz.project.carrental.util.InputUtil;
import kz.project.carrental.entity.Order;

public class DamageCreateAction implements Action, ACTION_CONST {

    @Override
    public String execute(RequestWrapper requestWrapper) throws ActionException {

        User user = (User) requestWrapper.getSessionAttributes().get(USER);

        if (user == null) {
            requestWrapper.getRequestAttributes().put(TIME_MESSAGE_INFO, "message.info.login.first");
            return ConfigurationManager.getInstance().getProperty(PATH_PAGE_LOGIN);
        }else{

            Integer order_id = InputUtil.strToInt(requestWrapper.getSingleRequestParameter("order_id"));
            String description = requestWrapper.getSingleRequestParameter("damage_description");
            Double price = InputUtil.strToDouble(requestWrapper.getSingleRequestParameter("damage_price"));

            //TODO
            if (order_id == null || description == null || price == null){
                return ConfigurationManager.getInstance().getProperty(PATH_PAGE_MAIN);
            }

            try {
                DamageLogic damageLogic = new DamageLogic();
                LogicResult<Order> logicResult = damageLogic.create(user, order_id, description, price);
                if (logicResult.noError()){
                    requestWrapper.getRequestAttributes().put(TIME_MESSAGE_INFO, "message.info.damage.created");
                }else{
                    requestWrapper.getRequestAttributes().put(TIME_MESSAGE_ERROR, logicResult.getErrorMessage());
                }
                requestWrapper.getRequestAttributes().put(ORDER, logicResult.getResult());
                return  ConfigurationManager.getInstance().getProperty(PATH_PAGE_ORDER_VIEW);
            } catch (LogicException e) {
                throw new ActionException(e);
            }
        }
        //TODO
    }
}
