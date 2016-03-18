package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(UserMealRestController.class);

    @Autowired
    private UserMealService service;

    public UserMeal get(int id) {
        return service.get(id, LoggedUser.id());
    }

    public void delete(int id) {
        service.delete(id, LoggedUser.id());
    }

    public List<UserMealWithExceed> getAll() {
        return UserMealsUtil.getWithExceeded(service.getAll(LoggedUser.id()), LoggedUser.getCaloriesPerDay());
    }

    public void update(UserMeal meal) {
        service.update(meal, LoggedUser.id());
    }

    public void create(UserMeal meal) {
        service.update(meal, LoggedUser.id());
    }

    public List<UserMealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        LOG.info("get between your date");
        return UserMealsUtil.getFilteredWithExceeded(service.getBetweenDates(startDate, endDate, LoggedUser.id()),
                startTime, endTime, LoggedUser.getCaloriesPerDay());
    }
}
