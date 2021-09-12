package com.dvoraninovich.establishment.model.dao.impl;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.DatabaseException;
import com.dvoraninovich.establishment.model.dao.OrderDao;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.OrderState;
import com.dvoraninovich.establishment.model.entity.PaymentType;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.pool.DatabaseConnectionPool;
import javafx.util.Pair;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.model.dao.DatabaseTableColumn.*;

public class OrderDaoImpl implements OrderDao {
    private static final OrderDaoImpl instance = new OrderDaoImpl();
    private static final DatabaseConnectionPool connectionPool = DatabaseConnectionPool.getInstance();

    private static final String SELECT_ALL_ORDERS
            = "SELECT orders.id, orders.id_user, orders_statuses.order_status, orders.order_time, orders.card_number, "
            + "payment_types.payment.type, orders.final_price, "
            + "users.login, users.phone_number, users.photo, users.mail "
            + "FROM orders "
            + "INNER JOIN orders_statuses "
            + "ON orders.id_order_status = orders_statuses.id "
            + "INNER JOIN payment_types "
            + "ON orders.id_payment_type = payment_types.id; ";

    private static final String FIND_ORDER_BY_ID
            = "SELECT orders.id, orders.id_user, orders_statuses.order_status, orders.order_time, orders.card_number, "
            + "payment_types.payment.type, orders.final_price, "
            + "users.login, users.phone_number, users.photo, users.mail "
            + "FROM orders "
            + "INNER JOIN orders_statuses "
            + "ON orders.id_order_status = orders_statuses.id "
            + "INNER JOIN payment_types "
            + "ON orders.id_payment_type = payment_types.id "
            + "WHERE id = ?;";

    private static final String INSERT_ORDER
            = "INSERT orders(id_user, id_order_status, "
            + "order_time, card_number, id_payment_type, final_price) "
            + "VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_ORDER
            = "UPDATE dishes_lists_items "
            + "SET id_user = ?, id_order_status = ?, "
            + "order_time = ?, card_number = ?, id_payment_type, final_price "
            + "WHERE id = ?;";

    public static OrderDaoImpl getInstance() {
        return instance;
    }

    private OrderDaoImpl() {
    }

    @Override
    public List<Order> findAll() throws DaoException {
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = connectionPool.acquireConnection();
             Statement statement = connection.createStatement()) {
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_ORDERS);
             while (resultSet.next()) {
                 Order order = createOrderFromResultSet(resultSet);
                 orderList.add(order);
             }
        } catch (SQLException e) {
            throw new DaoException("Can't handle OrderDaoImpl.findAll request", e);
        } catch (DatabaseException e) {
            throw new DaoException(e);
        }
        return orderList;
    }

    @Override
    public Optional<Order> findById(Long id) throws DaoException {
        Optional<Order> order = Optional.empty();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(FIND_ORDER_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                order = Optional.of(createOrderFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle finding order with id: " + id, e);
        }
        return order;
    }

    @Override
    public boolean insert(Order order) throws DaoException {
        boolean successfulOperation = false;
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(INSERT_ORDER);
            statement.setLong(1, order.getUserId());
            statement.setInt(2, order.getOrderState().ordinal()+1);
            statement.setTimestamp(3, Timestamp.valueOf(order.getOrderTime()));
            statement.setString(4, order.getCardNumber());
            statement.setInt(5,order.getPaymentType().ordinal()+1);
            statement.setBigDecimal(6, order.getFinalPrice());
            Integer rowsNum = statement.executeUpdate();
            successfulOperation = rowsNum != 0;
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle inserting order with id: " + order.getId(), e);
        }
        return successfulOperation;
    }

    @Override
    public boolean update(Order order) throws DaoException {
        boolean successfulOperation = false;
        if (!Optional.empty().equals(findById(order.getId()))) {
            try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
            ) {
                PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER);
                statement.setLong(1, order.getUserId());
                statement.setInt(2, order.getOrderState().ordinal()+1);
                statement.setTimestamp(3, Timestamp.valueOf(order.getOrderTime()));
                statement.setString(4, order.getCardNumber());
                statement.setInt(5,order.getPaymentType().ordinal()+1);
                statement.setBigDecimal(6, order.getFinalPrice());
                statement.setLong(7, order.getId());
                Integer rowsNum = statement.executeUpdate();
                successfulOperation = rowsNum != 0;
            } catch (DatabaseException | SQLException e) {
                throw new DaoException("Can't handle updating order with id: " + order.getId(), e);
            }
        }
        return successfulOperation;
    }

    @Override
    public List<Pair<Order, User>> findAllOrdersWithUserinfo() throws DaoException {
        List<Pair<Order, User>> pairList = new ArrayList<>();
        try (Connection connection = connectionPool.acquireConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ORDERS);
            while (resultSet.next()) {
                Pair<Order, User> pair = createOrderAndUserFromResultSet(resultSet);
                pairList.add(pair);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't handle OrderDaoImpl.findAllOrdersWithUserinfo request", e);
        } catch (DatabaseException e) {
            throw new DaoException(e);
        }
        return pairList;
    }

    private Order createOrderFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ORDER_ID);
        long userId = resultSet.getLong(ORDER_USER_ID);
        String orderStatusLine = resultSet.getString(ORDER_ORDER_STATUS);
        Timestamp orderTimeTimestamp = resultSet.getTimestamp(ORDER_TIME);
        LocalDateTime orderTime = orderTimeTimestamp.toLocalDateTime();
        String cardNumber = resultSet.getString(ORDER_CARD_NUMBER);
        String paymentTypeLine = resultSet.getString(ORDER_PAYMENT_TYPE);
        BigDecimal finalPrice = resultSet.getBigDecimal(ORDER_FINAL_PRICE);
        OrderState orderState = OrderState.valueOf(orderStatusLine);
        PaymentType paymentType = PaymentType.valueOf(paymentTypeLine);

        Order order = Order.builder()
        .setId(id)
        .setUserId(userId)
        .setOrderState(orderState)
        .setOrderTime(orderTime)
        .setCardNumber(cardNumber)
        .setPaymentType(paymentType)
        .setFinalPrice(finalPrice)
        .build();

        return order;
    }

    private Pair<Order, User> createOrderAndUserFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(FULL_ORDER_ID);
        long userId = resultSet.getLong(FULL_ORDER_USER_ID);
        String orderStatusLine = resultSet.getString(FULL_ORDER_ORDER_STATUS);
        Timestamp orderTimeTimestamp = resultSet.getTimestamp(FULL_ORDER_TIME);
        LocalDateTime orderTime = orderTimeTimestamp.toLocalDateTime();
        String cardNumber = resultSet.getString(FULL_ORDER_CARD_NUMBER);
        String paymentTypeLine = resultSet.getString(FULL_ORDER_PAYMENT_TYPE);
        BigDecimal finalPrice = resultSet.getBigDecimal(FULL_ORDER_FINAL_PRICE);
        OrderState orderState = OrderState.valueOf(orderStatusLine);
        PaymentType paymentType = PaymentType.valueOf(paymentTypeLine);

        String login = resultSet.getString(FULL_ORDER_USER_LOGIN);
        String mail = resultSet.getString(FULL_ORDER_USER_MAIL);
        String phone_number = resultSet.getString(FULL_ORDER_USER_PHONE_NUMBER);
        String photo = resultSet.getString(FULL_ORDER_USER_PHOTO);

        Order order = Order.builder()
                .setId(id)
                .setUserId(userId)
                .setOrderState(orderState)
                .setOrderTime(orderTime)
                .setCardNumber(cardNumber)
                .setPaymentType(paymentType)
                .setFinalPrice(finalPrice)
                .build();

        User user = User.builder()
                .setLogin(login)
                .setMail(mail)
                .setPhoto(photo)
                .setPhoneNumber(phone_number)
                .build();

        return new Pair<>(order, user);
    }

}
