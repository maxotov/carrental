package kz.project.carrental.servlet;

import kz.project.carrental.entity.Car;
import kz.project.carrental.entity.Model;
import kz.project.carrental.entity.Vendor;
import kz.project.carrental.logic.CarLogic;
import kz.project.carrental.logic.ModelLogic;
import kz.project.carrental.logic.VendorLogic;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.manager.ConfigurationManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;

@MultipartConfig(maxFileSize = 16177215)
public class AddCarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if(action.equals("car_create")){
            addCarCommand(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void addCarCommand(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String regNumber = request.getParameter("regNumber");
        String modelId = request.getParameter("modelId");
        String carName = request.getParameter("carName");
        String price = request.getParameter("price");
        String path = "";
        Part filePart = request.getPart("filename");

        if(regNumber != null && modelId!=null && carName!=null && price!=null && filePart != null && filePart.getSize()!=0){
            if(regNumber.isEmpty() || modelId.isEmpty() || carName.isEmpty() || price.isEmpty()){
                request.setAttribute("timeMessageInfo", "message.error.login.empty");
            } else{
                String projectDir = getServletContext().getRealPath(File.separator);
                projectDir += "image\\car\\";
                path = projectDir + extractFileName(filePart);
                filePart.write(path);
                try{
                    CarLogic carLogic = new CarLogic();
                    ModelLogic modelLogic = new ModelLogic();
                    Car car = new Car();
                    car.setRegNumber(regNumber);
                    Model model = modelLogic.getModelById(Integer.parseInt(modelId));
                    car.setModel(model);
                    car.setName(carName);
                    car.setPhoto(extractFileName(filePart));
                    car.setPrice(Double.parseDouble(price));
                    carLogic.createCar(car);
                    request.setAttribute("timeMessageInfo", "message.glad.car.create");

                } catch(LogicException e){

                }
            }
        }

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

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
