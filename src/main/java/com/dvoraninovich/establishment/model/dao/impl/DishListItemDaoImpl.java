package com.dvoraninovich.establishment.model.dao.impl;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.DatabaseException;
import com.dvoraninovich.establishment.model.dao.DishListItemDao;
import com.dvoraninovich.establishment.model.entity.DishListItem;
import com.dvoraninovich.establishment.model.pool.DatabaseConnectionPool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.model.dao.DatabaseTableColumn.*;

public class DishListItemDaoImpl implements DishListItemDao {
    private static final Logger logger = LogManager.getLogger(DishListItemDaoImpl.class);
    private static final DishListItemDaoImpl instance = new DishListItemDaoImpl();

    private static final String SELECT_ALL_DISH_LIST_ITEMS
            = "SELECT dishes_lists_items.id, dishes_lists_items.id_order, dishes_lists_items.id_dish, dishes_lists_items.dish_amount "
            + "FROM dishes_lists_items;";
    private static final String SELECT_DISH_LIST_ITEM_BY_ID
            = "SELECT dishes_lists_items.id, dishes_lists_items.id_order, dishes_lists_items.id_dish, dishes_lists_items.dish_amount "
            + "FROM dishes_lists_items "
            + "WHERE id = ?;";
    private static final String SELECT_DISH_LIST_ITEM_ORDER_AND_DISH_ID
            = "SELECT dishes_lists_items.id, dishes_lists_items.id_order, dishes_lists_items.id_dish, dishes_lists_items.dish_amount "
            + "FROM dishes_lists_items "
            + "WHERE dishes_lists_items.id_order = ? AND dishes_lists_items.id_dish = ?;";
    private static final String SELECT_ALL_DISH_LIST_ITEMS_BY_ORDER_ID
            = "SELECT dishes_lists_items.id, dishes_lists_items.id_order, dishes_lists_items.id_dish, dishes_lists_items.dish_amount "
            + "FROM dishes_lists_items "
            + "WHERE id_order = ?;";
    private static final String INSERT_DISH_LIST_ITEM
            = "INSERT dishes_lists_items(id_order, id_dish, dish_amount) "
            + "VALUES (?, ?, ?);";
    private static final String UPDATE_DISH_LIST_ITEM
            = "UPDATE dishes_lists_items "
            + "SET id_order = ?, id_dish = ?, dish_amount = ? "
            + "WHERE id = ?;";
    private static final String DELETE_DISH_LIST_ITEM
            = "DELETE FROM dishes_lists_items "
            + "WHERE id = ?;";

    public static DishListItemDaoImpl getInstance() {
        return instance;
    }

    private DishListItemDaoImpl() {
    }

    @Override
    public List<DishListItem> findAll() throws DaoException {
        List<DishListItem> dishListItems = new ArrayList<>();
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
             Statement statement = connection.createStatement()) {
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_DISH_LIST_ITEMS);
             while (resultSet.next()) {
                 DishListItem dishListItem = createDishListItemFromResultSet(resultSet);
                 dishListItems.add(dishListItem);
             }
        } catch (SQLException | DatabaseException e) {
            logger.error("Impossible to find all DishListItems", e);
            throw new DaoException("Impossible to find all DishListItems", e);
        }
        return dishListItems;
    }

    @Override
    public Optional<DishListItem> findById(Long id) throws DaoException {
        Optional<DishListItem> dishListItem = Optional.empty();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(SELECT_DISH_LIST_ITEM_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                dishListItem = Optional.of(createDishListItemFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to find DishListItem with id: " + id, e);
            throw new DaoException("Impossible to find DishListItem with id: " + id, e);
        }
        return dishListItem;
    }

    @Override
    public Optional<DishListItem> findByOrderAndDishId(long orderId, long dishId) throws DaoException {
        Optional<DishListItem> dishListItem = Optional.empty();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(SELECT_DISH_LIST_ITEM_ORDER_AND_DISH_ID);
            statement.setLong(1, orderId);
            statement.setLong(2, dishId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                dishListItem = Optional.of(createDishListItemFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to find DishListItem with orderId: "
                    + orderId + " and dishId: " + dishId, e);
            throw new DaoException("Impossible to find DishListItem with orderId: "
                    + orderId + " and dishId: " + dishId, e);
        }
        return dishListItem;
    }

    @Override
    public boolean insert(DishListItem dishListItem) throws DaoException {
        boolean successfulOperation = false;
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(INSERT_DISH_LIST_ITEM);
            statement.setLong(1, dishListItem.getOrderId());
            statement.setLong(2, dishListItem.getDishId());
            statement.setInt(3, dishListItem.getDishAmount());
            Integer rowsNum = statement.executeUpdate();
            successfulOperation = rowsNum != 0;
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to insert DishListItem with id: " + dishListItem.getId(), e);
            throw new DaoException("Impossible to insert DishListItem with id: " + dishListItem.getId(), e);
        }
        return successfulOperation;
    }

    @Override
    public boolean update(DishListItem dishListItem) throws DaoException {
        boolean successfulOperation = false;
        if (!Optional.empty().equals(findById(dishListItem.getId()))) {
            try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
            ) {
                PreparedStatement statement = connection.prepareStatement(UPDATE_DISH_LIST_ITEM);
                statement.setLong(1, dishListItem.getOrderId());
                statement.setLong(2, dishListItem.getDishId());
                statement.setInt(3, dishListItem.getDishAmount());
                statement.setLong(4, dishListItem.getId());
                Integer rowsNum = statement.executeUpdate();
                successfulOperation = rowsNum != 0;
            } catch (DatabaseException | SQLException e) {
                logger.error("Impossible to update DishListItem with id: " + dishListItem.getId(), e);
                throw new DaoException("Impossible to update DishListItem with id: " + dishListItem.getId(), e);
            }
        }
        return successfulOperation;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        boolean successfulOperation = false;
        if (!Optional.empty().equals(findById(id))) {
            try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
            ) {
                PreparedStatement statement = connection.prepareStatement(DELETE_DISH_LIST_ITEM);
                statement.setLong(1, id);
                Integer rowsNum = statement.executeUpdate();
                successfulOperation = rowsNum != 0;
            } catch (DatabaseException | SQLException e) {
                logger.error("Impossible to delete DishListItem with id: " + id, e);
                throw new DaoException("Impossible to delete DishListItem with id: " + id, e);
            }
        }
        return successfulOperation;
    }

    @Override
    public List<DishListItem> findAllByOrderId(long id) throws DaoException {
        List<DishListItem> dishListItems = new ArrayList<>();
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_DISH_LIST_ITEMS_BY_ORDER_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DishListItem dishListItem = createDishListItemFromResultSet(resultSet);
                dishListItems.add(dishListItem);
            }
        } catch (SQLException | DatabaseException e) {
            logger.error("Impossible to find all DishListItems by orderId: " + id, e);
            throw new DaoException("Impossible to find all DishListItems by orderId: " + id, e);
        }
        return dishListItems;
    }

    private DishListItem createDishListItemFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(DISH_LIST_ITEM_ID);
        long orderId = resultSet.getLong(DISH_LIST_ITEM_ID_ORDER);
        long dishId = resultSet.getLong(DISH_LIST_ITEM_ID_DISH);
        int dishAmount = resultSet.getInt(DISH_LIST_ITEM_DISH_AMOUNT);

        DishListItem dishListItem = DishListItem.builder()
                .setId(id)
                .setOrderId(orderId)
                .setDishId(dishId)
                .setDishAmount(dishAmount)
                .build();

        return dishListItem;
    }
}
