package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.MEAL_LIST.forEach(um -> save(um, 0));
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        Objects.requireNonNull(userMeal); //throws NullPointerException

        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        } else if (get(counter.get(), userId) == null) {
            return null; //если userMeal не принадлежит user
        }
        Map<Integer, UserMeal> userMeals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        userMeals.put(userMeal.getId(), userMeal);

        repository.put(userId, userMeals);

        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, UserMeal> userMeals = repository.get(userId);
        return userMeals != null && userMeals.remove(id) != null;
    }

    @Override
    public UserMeal get(int id, int userId) {
        Map<Integer, UserMeal> userMeals = repository.get(userId);
        return userMeals == null ? null : userMeals.get(id);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        Map<Integer, UserMeal> userMeals = repository.get(userId);
        Comparator<UserMeal> comparator = (um1, um2) -> um1.getDateTime().compareTo(um2.getDateTime());
        return userMeals == null ?
                Collections.emptyList() :
                userMeals.values().stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public Collection<UserMeal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Objects.requireNonNull(startDateTime);
        Objects.requireNonNull(endDateTime);
        return getAll(userId).stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime(), startDateTime, endDateTime))
                .collect(Collectors.toList());
    }
}

