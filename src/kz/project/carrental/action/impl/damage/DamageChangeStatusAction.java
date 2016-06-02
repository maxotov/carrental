package kz.project.carrental.action.impl.damage;

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
import kz.project.carrental.logic.DamageLogic;

public class DamageChangeStatusAction implements Action, ACTION_CONST {


    @Override
    public String execute(RequestWrapper requestWrapper) throws ActionException {
        User user = (User) requestWrapper.getSessionAttributes().get(USER);

        if (user == null) {
            requestWrapper.getRequestAttributes().put(TIME_MESSAGE_INFO, "message.info.login.first");
            return ConfigurationManager.getInstance().getProperty(PATH_PAGE_LOGIN);
        }else{
            Boolean newStatus = InputUtil.strToBoolean(requestWrapper.getSingleRequestParameter(DAMAGE_STATUS));
            Integer damage_id = InputUtil.strToInt(requestWrapper.getSingleRequestParameter(DAMAGE_ID));
            if (newStatus == null || damage_id == null){
                return ConfigurationManager.getInstance().getProperty(PATH_PAGE_MAIN);
            }

            try {
                DamageLogic damageLogic = new DamageLogic();
                LogicResult<Order> logicResult = damageLogic.changeDamageStatus(user, damage_id, newStatus);
                if (logicResult.noError()){
                    requestWrapper.getRequestAttributes().put(TIME_MESSAGE_INFO, "message.info.damage.status.changed");
                }else{
                    requestWrapper.getRequestAttributes().put(TIME_MESSAGE_ERROR, logicResult.getErrorMessage());
                }
                requestWrapper.getRequestAttributes().put(ORDER, logicResult.getResult());
                return  ConfigurationManager.getInstance().getProperty(PATH_PAGE_ORDER_VIEW);
            } catch (LogicException e) {
                throw new ActionException(e);
            }
        }
    }
}
