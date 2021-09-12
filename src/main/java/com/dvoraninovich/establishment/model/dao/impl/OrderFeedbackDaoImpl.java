package com.dvoraninovich.establishment.model.dao.impl;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.DatabaseException;
import com.dvoraninovich.establishment.model.dao.OrderFeedbackDao;
import com.dvoraninovich.establishment.model.entity.OrderFeedback;
import com.dvoraninovich.establishment.model.pool.DatabaseConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.model.dao.DatabaseTableColumn.*;

public class OrderFeedbackDaoImpl implements OrderFeedbackDao {
    private static final OrderFeedbackDaoImpl instance = new OrderFeedbackDaoImpl();
    private static final DatabaseConnectionPool connectionPool = DatabaseConnectionPool.getInstance();

    private static final String SELECT_ALL_ORDER_FEEDBACKS
            = "SELECT id, id_user, id_order, text, time, mark "
            + "FROM orders_feedbacks;";
    private static final String SELECT_ORDER_FEEDBACK_BY_ID
            = "SELECT id, id_user, id_order, text, time, mark "
            + "FROM orders_feedbacks "
            + "WHERE id = ?;";
    private static final String INSERT_ORDER_FEEDBACK
            = "INSERT orders_feedbacks(id_user, id_order, text, time, mark) "
            + "VALUES (?, ?, ?, ?, ?);";
    private static final String UPDATE_ORDER_FEEDBACK
            = "UPDATE orders_feedbacks "
            + "SET id_user = ?, id_order = ?, text = ?, time = ?, mark = ? "
            + "WHERE id = ?;";

    public static OrderFeedbackDao getInstance(){
        return instance;
    }

    @Override
    public List<OrderFeedback> findAll() throws DaoException {
        List<OrderFeedback> orderFeedbackList = new ArrayList<>();
        try (Connection connection = connectionPool.acquireConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ORDER_FEEDBACKS);
            while (resultSet.next()) {
                OrderFeedback orderFeedback = createOrderFeedbackFromResultSet(resultSet);
                orderFeedbackList.add(orderFeedback);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle OrderFeedbackDao.findAll request", e);
        } catch (DatabaseException e) {
            throw new DaoException(e);
        }
        return orderFeedbackList;
    }

    @Override
    public Optional<OrderFeedback> findById(Long id) throws DaoException {
        Optional<OrderFeedback> orderFeedback = Optional.empty();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_FEEDBACK_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                orderFeedback = Optional.of(createOrderFeedbackFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle finding orderFeedback with id: " + id, e);
        }
        return orderFeedback;
    }

    @Override
    public boolean insert(OrderFeedback orderFeedback) throws DaoException {
        boolean successfulOperation = false;
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(INSERT_ORDER_FEEDBACK);
            statement.setLong(1, orderFeedback.getUserId());
            statement.setLong(2, orderFeedback.getOrderId());
            statement.setString(3, orderFeedback.getText());
            statement.setDate(4, (java.sql.Date) orderFeedback.getTime());
            statement.setInt(5, orderFeedback.getMark());
            Integer rowsNum = statement.executeUpdate();
            successfulOperation = rowsNum != 0;
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle inserting orderFeedback with id: " + orderFeedback.getId(), e);
        }
        return successfulOperation;
    }

    @Override
    public boolean update(OrderFeedback orderFeedback) throws DaoException {
        boolean successfulOperation = false;
        if (!Optional.empty().equals(findById(orderFeedback.getId()))) {
            try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
            ) {
                PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_FEEDBACK);
                statement.setLong(1, orderFeedback.getUserId());
                statement.setLong(2, orderFeedback.getOrderId());
                statement.setString(3, orderFeedback.getText());
                statement.setDate(4, (java.sql.Date) orderFeedback.getTime());
                statement.setInt(5, orderFeedback.getMark());
                statement.setLong(6, orderFeedback.getId());
                Integer rowsNum = statement.executeUpdate();
                successfulOperation = rowsNum != 0;
            } catch (DatabaseException | SQLException e) {
                throw new DaoException("Can't handle updating orderFeedback with id: " + orderFeedback.getId(), e);
            }
        }
        return successfulOperation;
    }

    private OrderFeedback createOrderFeedbackFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ORDER_FEEDBACK_ID);
        long userId = resultSet.getLong(ORDER_FEEDBACK_USER_ID);
        long orderId =  resultSet.getLong(ORDER_FEEDBACK_ORDER_ID);
        String text = resultSet.getString(ORDER_FEEDBACK_TEXT);
        Date time = resultSet.getTime(ORDER_FEEDBACK_TIME);
        int mark = resultSet.getInt(ORDER_FEEDBACK_MARK);

        OrderFeedback orderFeedback = OrderFeedback.builder()
        .setId(id)
        .setUserId(userId)
        .setOrderId(orderId)
        .setText(text)
        .setTime(time)
        .setMark(mark)
        .build();

        return orderFeedback;
    }
}
