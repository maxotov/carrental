package kz.project.carrental.tag;

import kz.project.carrental.entity.User;
import kz.project.carrental.util.AccessUtil;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Checks the user access. If he has access then include body of tag, else skip tag body.
 * Access checks by access's value.
 */
public class CheckAccess extends BodyTagSupport {

    private User user;
    private String access;

    @Override
    public int doStartTag() throws JspException {
        if (AccessUtil.hasAccess(user, access)) {
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
