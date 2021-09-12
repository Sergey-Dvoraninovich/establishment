package com.dvoraninovich.establishment.model.dao.impl;

import com.dvoraninovich.establishment.model.entity.*;
import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.DatabaseException;
import com.dvoraninovich.establishment.model.dao.DishDao;
import com.dvoraninovich.establishment.model.pool.DatabaseConnectionPool;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.model.dao.DatabaseTableColumn.*;

public class DishDaoImpl implements DishDao {
    private static final DishDaoImpl instance = new DishDaoImpl();
    private static final DatabaseConnectionPool connectionPool = DatabaseConnectionPool.getInstance();

    private static final String SELECT_ALL_DISHES
            = "SELECT id, price, calories, amount_grams, average_mark, name, is_available, photo "
            + "FROM dishes;";
    private static final String FIND_ALL_AVAILABLE_DISHES
            = "SELECT id, price, calories, amount_grams, average_mark, name, is_available, photo "
            + "FROM dishes "
            + "WHERE is_available = true;";
    private static final String FIND_DISH_BY_ID
            = "SELECT id, price, calories, amount_grams, average_mark, name, is_available, photo "
            + "FROM dishes "
            + "WHERE id = ?; ";
    private static final String INSERT_DISH
            = "INSERT INTO dishes(price, calories, amount_grams, average_mark, name, is_available, photo) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_DISH
            = "UPDATE dishes "
            + "SET price = ?, calories = ?, amount_grams = ?, average_mark = ?, name = ?, is_available = ?, photo = ? "
            + "WHERE id = ?;";
    private static final String DISABLE_DISH
            = "UPDATE dishes " +
            "SET is_available = false " +
            "WHERE id = ?;";
    private static final String MAKE_DISH_AVAILABLE
            = "UPDATE dishes " +
            "SET is_available = true " +
            "WHERE id = ?;";
    private static final String FIND_DISH_INGREDIENTS
            = "SELECT ingredients.id, ingredients.name "
            + "FROM ingredients "
            + "INNER JOIN dishes_ingredients "
            + "ON ingredients.id = dishes_ingredients.id_ingredient "
            + "WHERE dishes_ingredients.id_dish = ?";
    private static final String INSERT_DISH_INGREDIENT
            = "INSERT INTO dishes_ingredients(id_dish, id_ingredient) "
            + "VALUES (?, ?);";
    private static final String DELETE_DISH_INGREDIENT
            = "DELETE FROM dishes_ingredients "
            + "WHERE id_dish = ? AND id_ingredient = ?;";

    public static DishDao getInstance(){
        return instance;
    }

    @Override
    public List<Dish> findAll() throws DaoException {
        List<Dish> dishList = new ArrayList<>();
        try (Connection connection = connectionPool.acquireConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_DISHES);
            while (resultSet.next()) {
                Dish dish = createDishFromResultSet(resultSet);
                dishList.add(dish);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle DishDao.findAll request", e);
        } catch (DatabaseException e) {
            throw new DaoException(e);
        }
        return dishList;
    }

    @Override
    public List<Dish> findAllAvailable() throws DaoException {
        List<Dish> dishList = new ArrayList<>();
        try (Connection connection = connectionPool.acquireConnection();
             Statement statement = connection.createStatement()) {
             ResultSet resultSet = statement.executeQuery(FIND_ALL_AVAILABLE_DISHES);
             while (resultSet.next()) {
                 Dish dish = createDishFromResultSet(resultSet);
                 dishList.add(dish);
             }
        } catch (SQLException e) {
            throw new DaoException("Can't handle DishDao.findAllAvailable request", e);
        } catch (DatabaseException e) {
            throw new DaoException(e);
        }
        return dishList;
    }

    @Override
    public Optional<Dish> findById(Long id) throws DaoException {
        Optional<Dish> dish = Optional.empty();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(FIND_DISH_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                dish = Optional.of(createDishFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle finding dish with id: " + id, e);
        }
        return dish;
    }

    @Override
    public boolean insert(Dish dish) throws DaoException {
        boolean successfulOperation = false;
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(INSERT_DISH);
            statement.setBigDecimal(1, dish.getPrice());
            statement.setInt(2, dish.getCalories());
            statement.setInt(3, dish.getAmountGrams());
            statement.setBigDecimal(4, dish.getAverageMark());
            statement.setString(5, dish.getName());
            statement.setBoolean(6, dish.getIsAvailable());
            statement.setString(7, dish.getPhoto());
            Integer rowsNum = statement.executeUpdate();
            successfulOperation = rowsNum != 0;
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle inserting dish " + dish.getName() +
                    " with id: " + dish.getId(), e);
        }
        return successfulOperation;
    }

    @Override
    public boolean update(Dish dish) throws DaoException {
        boolean successfulOperation = false;
        if (!Optional.empty().equals(findById(dish.getId()))) {
            try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
            ) {
                PreparedStatement statement = connection.prepareStatement(UPDATE_DISH);
                statement.setBigDecimal(1, dish.getPrice());
                statement.setInt(2, dish.getCalories());
                statement.setInt(3, dish.getAmountGrams());
                statement.setBigDecimal(4, dish.getAverageMark());
                statement.setString(5, dish.getName());
                statement.setBoolean(6, dish.getIsAvailable());
                statement.setString(7, dish.getPhoto());
                statement.setLong(8, dish.getId());
                Integer rowsNum = statement.executeUpdate();
                successfulOperation = rowsNum != 0;
            } catch (DatabaseException | SQLException e) {
                throw new DaoException("Can't handle updating dish " + dish.getName() +
                        " with id: " + dish.getId(), e);
            }
        }
        return successfulOperation;
    }

    @Override
    public boolean disable(Long id) throws DaoException {
        boolean successfulOperation;
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(DISABLE_DISH);
            statement.setLong(1, id);
            Integer rowsNum = statement.executeUpdate();
            successfulOperation = rowsNum != 0;
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle disabling dish with id: " + id, e);
        }
        return successfulOperation;
    }

    @Override
    public boolean makeAvailable(Long id) throws DaoException {
        boolean successfulOperation = false;
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(MAKE_DISH_AVAILABLE);
            statement.setLong(1, id);
            Integer rowsNum = statement.executeUpdate();
            successfulOperation = rowsNum != 0;
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle making available dish with id: " + id, e);
        }
        return successfulOperation;
    }

    @Override
    public List<Ingredient> findDishIngredients(Long id) throws DaoException {
        List<Ingredient> ingredientList = new ArrayList<>();
        try (Connection connection = connectionPool.acquireConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_DISH_INGREDIENTS)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Ingredient ingredient = Ingredient.builder()
                        .setId(resultSet.getInt(INGREDIENT_ID))
                        .setName(resultSet.getString(INGREDIENT_NAME))
                        .build();
                ingredientList.add(ingredient);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle DishDao.findDishIngredients request", e);
        } catch (DatabaseException e) {
            throw new DaoException(e);
        }
        return ingredientList;
    }

    @Override
    public boolean addDishIngredient(Long dishId, Long ingredientId) throws DaoException {
        boolean successfulOperation;
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(INSERT_DISH_INGREDIENT);
            statement.setLong(1, dishId);
            statement.setLong(2, ingredientId);
            Integer rowsNum = statement.executeUpdate();
            successfulOperation = rowsNum != 0;
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle adding ingredient with id: " + ingredientId
                    + "to dish with id: " + dishId, e);
        }
        return successfulOperation;
    }

    @Override
    public boolean removeDishIngredient(Long dishId, Long ingredientId) throws DaoException {
        boolean successfulOperation;
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(DELETE_DISH_INGREDIENT);
            statement.setLong(1, dishId);
            statement.setLong(2, ingredientId);
            Integer rowsNum = statement.executeUpdate();
            successfulOperation = rowsNum != 0;
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle removing ingredient with id: " + ingredientId
                    + "to dish with id: " + dishId, e);
        }
        return successfulOperation;
    }

    private Dish createDishFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(DISH_ID);
        BigDecimal price = resultSet.getBigDecimal(DISH_PRICE);
        int calories = resultSet.getInt(DISH_CALORIES);
        int amountGrams = resultSet.getInt(DISH_AMOUNT_GRAMS);
        BigDecimal averageMark = resultSet.getBigDecimal(DISH_AVERAGE_MARK);
        String name = resultSet.getString(DISH_NAME);
        boolean isAvailable = resultSet.getBoolean(IS_DISH_AVAILABLE);
        String photo = resultSet.getString(DISH_PHOTO);

        Dish dish = Dish.builder()
        .setId(id)
        .setPrice(price)
        .setCalories(calories)
        .setAmountGrams(amountGrams)
        .setAverageMark(averageMark)
        .setName(name)
        .setIsAvailable(isAvailable)
        .setPhoto(photo)
                .build();

        return dish;
    }
}
