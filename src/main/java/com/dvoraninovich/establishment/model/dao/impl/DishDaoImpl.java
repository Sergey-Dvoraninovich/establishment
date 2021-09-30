package com.dvoraninovich.establishment.model.dao.impl;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.DatabaseException;
import com.dvoraninovich.establishment.model.dao.DishDao;
import com.dvoraninovich.establishment.model.entity.Dish;
import com.dvoraninovich.establishment.model.entity.Ingredient;
import com.dvoraninovich.establishment.model.pool.DatabaseConnectionPool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.model.dao.DatabaseTableColumn.*;

public class DishDaoImpl implements DishDao {
    private static final Logger logger = LogManager.getLogger(DishDaoImpl.class);
    private static final DishDaoImpl instance = new DishDaoImpl();

    private static final String SELECT_ALL_DISHES
            = "SELECT dishes.id, dishes.price, dishes.calories, dishes.amount_grams, dishes.name, dishes.is_available, dishes.photo "
            + "FROM dishes;";
    private static final String COUNT_DISHES
            = "SELECT COUNT(dishes.id)"
            + "FROM dishes;";
    private static final String SELECT_LIMITED_DISHES
            = "SELECT dishes.id, dishes.price, dishes.calories, dishes.amount_grams, dishes.name, dishes.is_available, dishes.photo "
            + "FROM dishes"
            + "LIMIT ?, ?;";
    private static final String SELECT_ORDER_DISHES
            = "SELECT dishes.id, dishes.price, dishes.calories, dishes.amount_grams, dishes.name, "
            + "dishes.is_available, dishes.photo  "
            + "FROM dishes "
            + "INNER JOIN dishes_lists_items "
            + "ON dishes.id = dishes_lists_items.id_dish "
            + "INNER JOIN orders "
            + "ON orders.id = dishes_lists_items.id_order "
            + "WHERE orders.id = ?;";
    private static final String FIND_DISH_BY_ID
            = "SELECT dishes.id, dishes.price, dishes.calories, dishes.amount_grams, dishes.name, dishes.is_available, dishes.photo "
            + "FROM dishes "
            + "WHERE id = ?; ";
    private static final String INSERT_DISH
            = "INSERT INTO dishes(price, calories, amount_grams, name, is_available, photo) "
            + "VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_DISH
            = "UPDATE dishes "
            + "SET price = ?, calories = ?, amount_grams = ?, name = ?, is_available = ?, photo = ? "
            + "WHERE id = ?;";
    private static final String DISABLE_DISH
            = "UPDATE dishes "
            + "SET is_available = false "
            + "WHERE id = ?;";
    private static final String MAKE_DISH_AVAILABLE
            = "UPDATE dishes "
            + "SET is_available = true "
            + "WHERE id = ?;";
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
    private static final String LIMIT_LINE = "LIMIT";

    public static DishDao getInstance(){
        return instance;
    }

    @Override
    public List<Dish> findAll() throws DaoException {
        List<Dish> dishList = new ArrayList<>();
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_DISHES);
            while (resultSet.next()) {
                Dish dish = createDishFromResultSet(resultSet);
                dishList.add(dish);
            }
        } catch (SQLException | DatabaseException e) {
            logger.error("Impossible to find all dishes", e);
            throw new DaoException("Impossible to find all dishes", e);
        }
        return dishList;
    }

    @Override
    public List<Dish> findOrderDishes(long orderId) throws DaoException {
        List<Dish> dishList = new ArrayList<>();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_DISHES);
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Dish dish = createDishFromResultSet(resultSet);
                dishList.add(dish);
            }
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to find dishes for order by orderId: " + orderId, e);
            throw new DaoException("Impossible to find dishes for order by orderId: " + orderId, e);
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
            logger.error("Impossible to find dish by id: " + id, e);
            throw new DaoException("Impossible to find dish by id: " + id, e);
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
            statement.setString(4, dish.getName());
            statement.setBoolean(5, dish.getIsAvailable());
            statement.setString(6, dish.getPhoto());
            Integer rowsNum = statement.executeUpdate();
            successfulOperation = rowsNum != 0;
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to insert dish " + dish.getName() +
                    " with id: " + dish.getId(), e);
            throw new DaoException("Impossible to insert dish " + dish.getName() +
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
                statement.setString(4, dish.getName());
                statement.setBoolean(5, dish.getIsAvailable());
                statement.setString(6, dish.getPhoto());
                statement.setLong(7, dish.getId());
                Integer rowsNum = statement.executeUpdate();
                successfulOperation = rowsNum != 0;
            } catch (DatabaseException | SQLException e) {
                logger.error("Impossible to update dish " + dish.getName() +
                        " with id: " + dish.getId(), e);
                throw new DaoException("Impossible to update dish " + dish.getName() +
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
            logger.error("Impossible to disable dish with id: " + id, e);
            throw new DaoException("Impossible to disable dish with id: " + id, e);
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
            logger.error("Impossible to make available dish with id: " + id, e);
            throw new DaoException("Impossible to make available dish with id: " + id, e);
        }
        return successfulOperation;
    }

    @Override
    public List<Ingredient> findDishIngredients(Long id) throws DaoException {
        List<Ingredient> ingredientList = new ArrayList<>();
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
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
        } catch (SQLException | DatabaseException e) {
            logger.error("Impossible to find dish ingredients", e);
            throw new DaoException("Impossible to find dish ingredients", e);
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
            logger.error("Impossible to add ingredient with id: " + ingredientId
                    + "to dish with id: " + dishId, e);
            throw new DaoException("Impossible to add ingredient with id: " + ingredientId
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
            logger.error("Impossible to remove ingredient with id: " + ingredientId
                    + "to dish with id: " + dishId, e);
            throw new DaoException("Impossible to remove ingredient with id: " + ingredientId
                    + "to dish with id: " + dishId, e);
        }
        return successfulOperation;
    }

    @Override
    public Long countFilteredDishes(String name, String minPriceLine, String maxPriceLine, String minCaloriesAmountLine, String maxCaloriesAmountLine, String minAmountGramsLine, String maxAmountGramsLine, Boolean[] dishStates) throws DaoException{
        Long dishesAmount = Long.valueOf(0);
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            String requestLine = addFilterParameters(COUNT_DISHES, name, minPriceLine, maxPriceLine,
                    minCaloriesAmountLine, maxCaloriesAmountLine, minAmountGramsLine, maxAmountGramsLine, dishStates);
            PreparedStatement statement = connection.prepareStatement(requestLine);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                dishesAmount = resultSet.getLong(1);
            }
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to count orders", e);
            throw new DaoException("Impossible to count orders", e);
        }
        return dishesAmount;
    }

    @Override
    public List<Dish> findFilteredDishes(String name, String minPriceLine, String maxPriceLine, String minCaloriesAmountLine, String maxCaloriesAmountLine, String minAmountGramsLine, String maxAmountGramsLine, Boolean[] dishStates, long minPos, long maxPos) throws DaoException {
        List<Dish> dishes = new ArrayList<>();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            String requestLine = addFilterParameters(SELECT_LIMITED_DISHES, name, minPriceLine, maxPriceLine,
                    minCaloriesAmountLine, maxCaloriesAmountLine, minAmountGramsLine, maxAmountGramsLine, dishStates);
            PreparedStatement statement = connection.prepareStatement(requestLine);
            statement.setLong(1, minPos-1);
            statement.setLong(2, maxPos);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                dishes.add(createDishFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to count orders", e);
            throw new DaoException("Impossible to count orders", e);
        }
        return dishes;
    }

    private String addFilterParameters(String requestLine, String name, String minPriceLine, String maxPriceLine,
                                       String minCaloriesAmountLine, String maxCaloriesAmountLine,
                                       String minAmountGramsLine, String maxAmountGramsLine, Boolean[] dishStates){
        StringBuilder filterString = new StringBuilder(" WHERE ");
        if (!name.equals("")){
            filterString.append(DISH_NAME).append(" LIKE '%")
                    .append(name).append("%' AND ");
        }
        if (!minPriceLine.equals("")){
            filterString.append(DISH_PRICE).append(" >= ")
                    .append(minPriceLine).append(" AND ");
        }
        if (!maxPriceLine.equals("")){
            filterString.append(DISH_PRICE).append(" <= ")
                    .append(maxPriceLine).append(" AND ");
        }
        if (!minCaloriesAmountLine.equals("")){
            filterString.append(DISH_CALORIES).append(" >= ")
                    .append(minCaloriesAmountLine).append(" AND ");
        }
        if (!maxCaloriesAmountLine.equals("")){
            filterString.append(DISH_CALORIES).append(" <= ")
                    .append(maxCaloriesAmountLine).append(" AND ");
        }
        if (!minAmountGramsLine.equals("")){
            filterString.append(DISH_AMOUNT_GRAMS).append(" >= ")
                    .append(minAmountGramsLine).append(" AND ");
        }
        if (!maxAmountGramsLine.equals("")){
            filterString.append(DISH_AMOUNT_GRAMS).append(" <= ")
                    .append(maxAmountGramsLine).append(" AND ");
        }
        if (dishStates.length != 0){
            filterString.append(makeFilterGroup(IS_DISH_AVAILABLE, dishStates)).append(" AND ");
        }
        if (!filterString.toString().equals(" WHERE ")) {
            Integer lastAndPos = filterString.lastIndexOf(" AND ");
            filterString.replace(lastAndPos, lastAndPos + 5, "");
        }
        else {
            filterString.replace(0, filterString.length(), "");
        }

        String lineToFind = !requestLine.contains(LIMIT_LINE) ? ";" : LIMIT_LINE;
        Integer wherePos = requestLine.indexOf(lineToFind);
        wherePos -= lineToFind.length() - 1;
        StringBuilder resultString = new StringBuilder(requestLine);
        resultString.insert(wherePos + lineToFind.length() - 1, filterString + " ");
        return resultString.toString();
    }

    private String makeFilterGroup(String DBLine, Boolean[] parameters) {
        StringBuilder itemsString = new StringBuilder(" (");
        for (Boolean value : parameters) {
            itemsString.append(" ")
                    .append(DBLine)
                    .append(" = ")
                    .append(value)
                    .append(" OR");
        }
        itemsString.replace(itemsString.lastIndexOf("OR"), itemsString.length(), "");
        itemsString.append(") ");
        return itemsString.toString();
    }

    private Dish createDishFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(DISH_ID);
        BigDecimal price = resultSet.getBigDecimal(DISH_PRICE);
        int calories = resultSet.getInt(DISH_CALORIES);
        int amountGrams = resultSet.getInt(DISH_AMOUNT_GRAMS);
        String name = resultSet.getString(DISH_NAME);
        boolean isAvailable = resultSet.getBoolean(IS_DISH_AVAILABLE);
        String photo = resultSet.getString(DISH_PHOTO);

        Dish dish = Dish.builder()
        .setId(id)
        .setPrice(price)
        .setCalories(calories)
        .setAmountGrams(amountGrams)
        .setName(name)
        .setIsAvailable(isAvailable)
        .setPhoto(photo)
                .build();

        return dish;
    }
}
