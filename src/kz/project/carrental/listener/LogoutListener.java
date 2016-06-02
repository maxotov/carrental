package kz.project.carrental.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Listener performs invalidation session. If in the session attributes added attribute by name "logout".
 */
public class LogoutListener implements HttpSessionAttributeListener {

    private static final String LOGOUT = "logout";

    @Override
    public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
        if (httpSessionBindingEvent.getName().equals(LOGOUT)) {
            httpSessionBindingEvent.getSession().invalidate();
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
    }
}
