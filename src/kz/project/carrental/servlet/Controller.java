package kz.project.carrental.servlet;

import kz.project.carrental.action.exception.ActionException;
import kz.project.carrental.action.Action;
import kz.project.carrental.action.factory.ActionFactory;
import kz.project.carrental.base.impl.ConnectionPool;
import kz.project.carrental.manager.ConfigurationManager;
import kz.project.carrental.action.wrapper.RequestWrapper;
import kz.project.carrental.manager.Log4jManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Controller extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(Controller.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        handleRequest(req, resp);
    }

    /**
     * Handles user requests and generates a response to the user.
     *
     * @param req user request.
     * @param resp response to the user.
     * @throws ServletException
     * @throws IOException
     */
    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = null;
        req.setCharacterEncoding (ConfigurationManager.getInstance().getProperty("encoding"));
        resp.setCharacterEncoding(ConfigurationManager.getInstance().getProperty("encoding"));
        RequestWrapper requestWrapper = new RequestWrapper(req);

        Action action = ActionFactory.defineAction(requestWrapper);
        try {
            page = action.execute(requestWrapper);
        } catch (ActionException e) {
            LOGGER.error(null, e);
            page = ConfigurationManager.getInstance().getProperty("path.page.sorry");
        }
        requestWrapper.unwrap(req);

        if (page == null) {
            page = ConfigurationManager.getInstance().getProperty("path.page.sorry");
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(req, resp);

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Log4jManager.init(config);
        ConfigurationManager.getInstance().init(config);
        ConnectionPool.init(ConfigurationManager.getInstance());
    }

    @Override
    public void destroy() {
        ConnectionPool.getInstance().dispose();
        super.destroy();
    }
}
