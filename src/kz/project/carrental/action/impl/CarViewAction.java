package kz.project.carrental.action.impl;

import kz.project.carrental.action.ACTION_CONST;
import kz.project.carrental.action.Action;
import kz.project.carrental.action.exception.ActionException;
import kz.project.carrental.action.wrapper.RequestWrapper;
import kz.project.carrental.entity.Car;
import kz.project.carrental.logic.CarLogic;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.manager.ConfigurationManager;
import org.apache.log4j.Logger;

import java.util.List;

public class CarViewAction implements Action, ACTION_CONST {

    private static final Logger LOGGER = Logger.getLogger(CarViewAction.class);

    @Override
    public String execute(RequestWrapper requestWrapper) throws ActionException {
        CarLogic carLogic = new CarLogic();

        List<Car> cars = null;
        try {
            cars = carLogic.getAllCars();
            requestWrapper.getRequestAttributes().put(CARS, cars);
        } catch (LogicException e) {
            throw new ActionException(e);
        }

        return ConfigurationManager.getInstance().getProperty(PATH_PAGE_CARS);
    }
}
