package kz.project.carrental.action.factory;

import kz.project.carrental.action.Action;
import kz.project.carrental.action.impl.*;
import kz.project.carrental.action.impl.order.*;
import kz.project.carrental.action.impl.damage.DamageChangeStatusAction;
import kz.project.carrental.action.impl.damage.DamageCreateAction;
import kz.project.carrental.action.impl.damage.DamageDeleteAction;

public enum ActionEnum {

    EMPTY {
        {
            this.action = new EmptyAction();
        }
    },
    LOGIN {
        {
            this.action = new LoginAction();
        }
    },
    LOGOUT {
        {
            this.action = new LogoutAction();
        }
    },
    CHANGE_LANGUAGE {
        {
            this.action = new ChangeLanguageAction();
        }
    },
    CAR_VIEW {
        {
            this.action = new CarViewAction();
        }
    },
    CONTACT_VIEW {
        {
            this.action = new ContactShowAction();
        }
    },
    USER_VIEW {
        {
            this.action = new UserViewAction();
        }
    },
    ORDER_PREPARE {
        {
            this.action = new OrderPrepareAction();
        }
    },
    ORDER_VIEW {
        {
            this.action = new OrderViewAction();
        }

    },
    ORDER_VIEW_ALL{
        {
            this.action = new OrderViewAllAction();
        }
    },
    ORDER_SEND {
        {
            this.action = new OrderSendAction();
        }
    },
    ORDER_CHANGE_STATUS{
        {
            this.action = new OrderChangeStatusAction();
        }
    },
    ORDER_DELETE{
        {
            this.action = new OrderDeleteAction();
        }
    },
    DAMAGE_CHANGE_STATUS{
        {
            this.action = new DamageChangeStatusAction();
        }
    },
    DAMAGE_CREATE{
        {
            this.action = new DamageCreateAction();
        }
    },
    DAMAGE_DELETE{
        {
            this.action = new DamageDeleteAction();
        }
    },
    USER_CREATE{
        {
            this.action = new UserCreateAction();
        }
    },
    ADD_ACCESS{
        {
            this.action = new AddAccessCommand();
        }
    },
    DELETE_ACCESS{
        {
            this.action = new DeleteAccessCommand();
        }
    };

    protected Action action;

    public Action getAction() {
        return action;
    }
}
