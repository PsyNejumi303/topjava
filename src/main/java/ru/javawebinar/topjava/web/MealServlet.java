package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("MealList");

        req.setAttribute("meals", UserMealsUtil.getFilteredMealsWithExceeded(UserMealsUtil.mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        req.getRequestDispatcher("/mealList.jsp").forward(req, resp);
    }
}
