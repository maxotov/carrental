package kz.project.carrental.filter;

import kz.project.carrental.entity.User;
import kz.project.carrental.manager.ConfigurationManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class UserFilter implements Filter {

    private static final String ACTION_NAMES = "actionNames";
    private List<String> actionNames;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        actionNames = new ArrayList<String>();
        String initNames = filterConfig.getInitParameter(ACTION_NAMES);
        if (initNames != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(initNames, ",");
            while (stringTokenizer.hasMoreTokens()) {
                actionNames.add(stringTokenizer.nextToken());
            }
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String action = req.getParameter("action");

        if (needFiltering(action)) {
            HttpSession session = req.getSession(true);
            User user = (User) session.getAttribute("user");
            if (user == null) {
                String page = ConfigurationManager.getInstance().getProperty("path.page.login");
                RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher(page);
                dispatcher.forward(req, resp);
            }else{
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }


    private boolean needFiltering(String actonName) {
        if (actonName == null) {
            return false;
        }

        if (actionNames != null) {
            return actionNames.contains(actonName);
        } else {
            return false;
        }
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
