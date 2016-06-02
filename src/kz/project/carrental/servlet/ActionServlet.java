package kz.project.carrental.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import kz.project.carrental.entity.Model;
import kz.project.carrental.entity.Vendor;
import kz.project.carrental.logic.ModelLogic;
import kz.project.carrental.logic.VendorLogic;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.manager.ConfigurationManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Aibol
 * Date: 28.02.15
 * Time: 17:48
 * To change this template use File | Settings | File Templates.
 */

public class ActionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if(action.equals("car_create")){
            addCarCommand(request, response);
        }
        if(action.equals("get_models")){
           getModelsByVendor(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         doPost(request, response);
    }

    private void addCarCommand(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        VendorLogic modelLogic = new VendorLogic();
        List<Vendor> models = null;
        try {
            models = modelLogic.getAllVendors();
        } catch (LogicException e) {
            e.printStackTrace();
        }
        request.setAttribute("vendors", models);
        RequestDispatcher dis = getServletContext().getRequestDispatcher(ConfigurationManager.getInstance().getProperty("path.page.car.create"));
        dis.forward(request, response);


    }

    private void getModelsByVendor(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String idVendor = request.getParameter("id");

        ModelLogic modelLogic = new ModelLogic();
        List<Model> models = null;
        try {
            models = modelLogic.getModelsByVendor(Integer.parseInt(idVendor));
        } catch (LogicException e) {
            e.printStackTrace();
        }
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        JsonObject myObj = new JsonObject();
        JsonElement contactInfo = gson.toJsonTree(models);

        if(models.isEmpty()){
            myObj.addProperty("success", false);
        }
        else {
            myObj.addProperty("success", true);
        }
        myObj.add("models", contactInfo);
        out.println(myObj.toString());

        out.close();
    }
}
