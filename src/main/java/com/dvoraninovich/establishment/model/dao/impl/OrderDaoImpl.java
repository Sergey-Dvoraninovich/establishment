package com.dvoraninovich.establishment.model.dao.impl;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.DatabaseException;
import com.dvoraninovich.establishment.model.dao.OrderDao;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.OrderState;
import com.dvoraninovich.establishment.model.entity.PaymentType;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.pool.DatabaseConnectionPool;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.model.dao.DatabaseTableColumn.*;

public class OrderDaoImpl implements OrderDao {
    private static final OrderDaoImpl instance = new OrderDaoImpl();
    private static final DatabaseConnectionPool connectionPool = DatabaseConnectionPool.getInstance();

    private static final String SELECT_ALL_ORDERS
            = "SELECT orders.id, orders.id_user, orders_statuses.order_status, orders.order_time, orders.finish_time, "
            + " orders.card_number, payment_types.payment_type, orders.bonuses_in_payment, orders.final_price "
            + "FROM orders "
            + "INNER JOIN orders_statuses "
            + "ON orders.id_order_status = orders_statuses.id "
            + "INNER JOIN payment_types "
            + "ON orders.id_payment_type = payment_types.id; ";

    private static final String SELECT_ALL_ORDERS_WITH_USER_INFO
            = "SELECT orders.id, orders.id_user, orders_statuses.order_status, orders.order_time, orders.finish_time, "
            + " orders.card_number, payment_types.payment_type, orders.bonuses_in_payment, orders.final_price, "
            + "users.login, users.mail, users.photo, users.phone_number "
            + "FROM orders "
            + "INNER JOIN orders_statuses "
            + "ON orders.id_order_status = orders_statuses.id "
            + "INNER JOIN users "
            + "ON orders.id_user = users.id "
            + "INNER JOIN payment_types "
            + "ON orders.id_payment_type = payment_types.id "
            + "WHERE orders_statuses.order_status != 'IN_CREATION' "
            + "LIMIT ?, ?;";

    private static final String COUNT_ORDERS
            = "SELECT COUNT(orders.id) "
            + "FROM orders "
            + "INNER JOIN orders_statuses "
            + "ON orders.id_order_status = orders_statuses.id "
            + "INNER JOIN payment_types "
            + "ON orders.id_payment_type = payment_types.id "
            + "WHERE orders_statuses.order_status != 'IN_CREATION';";

    private static final String FIND_ORDER_BY_ID
            = "SELECT orders.id, orders.id_user, orders_statuses.order_status, orders.order_time, orders.finish_time, "
            + " orders.card_number, payment_types.payment_type, orders.bonuses_in_payment, orders.final_price "
            + "FROM orders "
            + "INNER JOIN orders_statuses "
            + "ON orders.id_order_status = orders_statuses.id "
            + "INNER JOIN payment_types "
            + "ON orders.id_payment_type = payment_types.id "
            + "WHERE orders.id = ?;";

    private static final String COUNT_ORDER_DISHES_AMOUNT
            = "SELECT SUM(dishes_lists_items.dish_amount) "
            + "FROM dishes_lists_items "
            + "INNER JOIN orders "
            + "ON orders.id = dishes_lists_items.id_order "
            + "WHERE orders.id = ? ;";

    private static final String COUNT_ORDER_FINAL_PRICE
            = "SELECT ROUND(SUM(dishes_lists_items.dish_amount*dishes.price) - orders.bonuses_in_payment/100, 2)"
            + "FROM dishes_lists_items "
            + "INNER JOIN orders "
            + "ON orders.id = dishes_lists_items.id_order "
            + "INNER JOIN dishes "
            + "ON dishes.id = dishes_lists_items.id_dish "
            + "WHERE orders.id = ? ;";
    private static final String UPDATE_ORDER_FINAL_PRICE
            = "UPDATE orders "
            + "SET final_price = ? "
            + "WHERE id = ?;";

    private static final String FIND_USER_ORDER_IN_CREATION
            = "SELECT orders.id, orders.id_user, orders_statuses.order_status, orders.order_time, orders.finish_time, "
            + " orders.card_number, payment_types.payment_type, orders.bonuses_in_payment, orders.final_price "
            + "FROM orders "
            + "INNER JOIN orders_statuses "
            + "ON orders.id_order_status = orders_statuses.id "
            + "INNER JOIN payment_types "
            + "ON orders.id_payment_type = payment_types.id "
            + "WHERE orders.id_user = ? AND orders_statuses.order_status = 'IN_CREATION';";

    private static final String FIND_USER_ORDERS
            = "SELECT orders.id, orders.id_user, orders_statuses.order_status, orders.order_time, orders.finish_time, "
            + " orders.card_number, payment_types.payment_type, orders.bonuses_in_payment, orders.final_price "
            + "FROM orders "
            + "INNER JOIN orders_statuses "
            + "ON orders.id_order_status = orders_statuses.id "
            + "INNER JOIN payment_types "
            + "ON orders.id_payment_type = payment_types.id "
            + "WHERE orders.id_user = ? AND orders_statuses.order_status != 'IN_CREATION' "
            + "ORDER BY orders.order_time DESC "
            + "LIMIT ?, ?; ";
    private static final String FIND_USER_ORDERS_AMOUNT
            = "SELECT COUNT(orders.id) "
            + "FROM orders "
            + "INNER JOIN orders_statuses "
            + "ON orders.id_order_status = orders_statuses.id "
            + "INNER JOIN payment_types "
            + "ON orders.id_payment_type = payment_types.id "
            + "WHERE orders.id_user = ? AND orders_statuses.order_status != 'IN_CREATION' ;";

    private static final String INSERT_ORDER
            = "INSERT orders(id_user, id_order_status, order_time, "
            + "finish_time, card_number, id_payment_type, bonuses_in_payment, final_price) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_ORDER
            = "UPDATE orders "
            + "SET id_user = ?, id_order_status = ?, order_time = ?, "
            + "finish_time = ?, card_number = ?, id_payment_type = ?, bonuses_in_payment = ?, final_price = ? "
            + "WHERE id = ?;";
    private static final String WHERE_LINE = "WHERE ";

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
    public Long countOrderDishesAmount(long id) throws DaoException {
        Long dishesAmount = Long.valueOf(0);
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(COUNT_ORDER_DISHES_AMOUNT);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                dishesAmount = resultSet.getLong(1);
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle counting dishes amount for order with id: " + id, e);
        }
        return dishesAmount;
    }

    @Override
    public BigDecimal countOrderFinalPrice(long id) throws DaoException {
        BigDecimal finalPrice = new BigDecimal("0.00");
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(COUNT_ORDER_FINAL_PRICE);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                finalPrice = resultSet.getBigDecimal(1);
                if (finalPrice != null) {
                    finalPrice.setScale(2);
                }
                else {
                    finalPrice = new BigDecimal("0.00");
                }
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle counting final price for order with id: " + id, e);
        }
        return finalPrice;
    }

    @Override
    public List<Order> findUserOrders(long userId, long minPos, long maxPos) throws DaoException {
        List<Order> orders = new ArrayList<>();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_ORDERS);
            statement.setLong(1, userId);
            statement.setLong(2, minPos-1);
            statement.setLong(3, maxPos);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                orders.add(createOrderFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle finding orders for user with id: " + userId, e);
        }
        return orders;
    }

    @Override
    public Long countUserOrders(long userId) throws DaoException {
        Long amount = Long.valueOf(0);
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_ORDERS_AMOUNT);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                amount = resultSet.getLong(1);
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle counting final price for order with id: " + userId, e);
        }
        return amount;
    }

    @Override
    public Boolean updateOrderFinalPrice(long id) throws DaoException {
        BigDecimal finalPrice = new BigDecimal("0.00");
        Boolean successfulOperation = false;
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(COUNT_ORDER_FINAL_PRICE);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                finalPrice = resultSet.getBigDecimal(1);
                finalPrice = finalPrice == null ? new BigDecimal("0.00") : finalPrice;
                statement = connection.prepareStatement(UPDATE_ORDER_FINAL_PRICE);
                statement.setBigDecimal(1, finalPrice);
                statement.setLong(2, id);
                Integer rowsNum = statement.executeUpdate();
                successfulOperation = rowsNum != 0;
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle reinstalling final price for order with id: " + id, e);
        }
        return successfulOperation;
    }

    @Override
    public Long countOrders() throws DaoException {
        Long amount = Long.valueOf(0);
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(COUNT_ORDERS);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                amount = resultSet.getLong(1);
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle counting order amount", e);
        }
        return amount;
    }

    @Override
    public Long countOrders(String userIdLine, String minPriceLine, String maxPriceLine,
                            String[] orderStates, String[] paymentTypes) throws DaoException {
        Long amount = Long.valueOf(0);
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            String requestLine = addFilterParameters(COUNT_ORDERS, userIdLine, minPriceLine, maxPriceLine,
                    orderStates, paymentTypes);
            PreparedStatement statement = connection.prepareStatement(requestLine);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                amount = resultSet.getLong(1);
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle counting order amount", e);
        }
        return amount;
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
            statement.setTimestamp(4, Timestamp.valueOf(order.getFinishTime()));
            statement.setString(5, order.getCardNumber());
            statement.setInt(6,order.getPaymentType().ordinal()+1);
            statement.setBigDecimal(7, order.getBonusesInPayment());
            statement.setBigDecimal(8, order.getFinalPrice());
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
                statement.setTimestamp(4, Timestamp.valueOf(order.getFinishTime()));
                statement.setString(5, order.getCardNumber());
                statement.setInt(6,order.getPaymentType().ordinal()+1);
                statement.setBigDecimal(7, order.getBonusesInPayment());
                statement.setBigDecimal(8, order.getFinalPrice());
                statement.setLong(9, order.getId());
                Integer rowsNum = statement.executeUpdate();
                successfulOperation = rowsNum != 0;
            } catch (DatabaseException | SQLException e) {
                throw new DaoException("Can't handle updating order with id: " + order.getId(), e);
            }
        }
        return successfulOperation;
    }

    @Override
    public Optional<Order> findOrderInCreation(long userId) throws DaoException {
        Optional<Order> order = Optional.empty();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_ORDER_IN_CREATION);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                order = Optional.of(createOrderFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle finding order in creation for user with id: " + userId, e);
        }
        return order;
    }

    @Override
    public Long insertAndGetId(Order order) throws DaoException {
        Long id = Long.valueOf(0);
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUserId());
            statement.setInt(2, order.getOrderState().ordinal()+1);
            statement.setTimestamp(3, Timestamp.valueOf(order.getOrderTime()));
            statement.setTimestamp(4, Timestamp.valueOf(order.getFinishTime()));
            statement.setString(5, order.getCardNumber());
            statement.setInt(6,order.getPaymentType().ordinal()+1);
            statement.setBigDecimal(7, order.getBonusesInPayment());
            statement.setBigDecimal(8, order.getFinalPrice());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            id = generatedKeys.getLong(1);
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Error while inserting order", e);
        }
        return id;
    }

    @Override
    public HashMap<Order, User> findOrdersWithUsersLimit(long minPos, long maxPos) throws DaoException {
        HashMap<Order, User> userHashMap = new HashMap<>();
        try (Connection connection = connectionPool.acquireConnection()) {

            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ORDERS_WITH_USER_INFO);
            statement.setLong(1, minPos-1);
            statement.setLong(2, maxPos);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = createOrderFromResultSet(resultSet);


                String login = resultSet.getString(USER_LOGIN);
                String mail = resultSet.getString(USER_MAIL);
                String phone_number = resultSet.getString(USER_PHONE_NUMBER);
                String photo = resultSet.getString(USER_PHOTO);

                User user = User.builder()
                        .setLogin(login)
                        .setMail(mail)
                        .setPhoto(photo)
                        .setPhoneNumber(phone_number)
                        .build();

                userHashMap.put(order, user);
            }
        } catch (SQLException | DatabaseException e) {
            throw new DaoException("Can't handle OrderDaoImpl.findAllOrdersWithUserinfo request", e);
        }
        return userHashMap;
    }

    @Override
    public HashMap<Order, User> findOrdersWithUsersLimit(String userIdLine, String minPriceLine, String maxPriceLine, long minPos, long maxPos,
                                                         String[] orderStates, String[] paymentTypes) throws DaoException {
        HashMap<Order, User> userHashMap = new HashMap<>();
        try (Connection connection = connectionPool.acquireConnection()) {

            String requestLine = addFilterParameters(SELECT_ALL_ORDERS_WITH_USER_INFO, userIdLine, minPriceLine, maxPriceLine,
                                                     orderStates, paymentTypes);
            PreparedStatement statement = connection.prepareStatement(requestLine);
            statement.setLong(1, minPos-1);
            statement.setLong(2, maxPos);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Order order = createOrderFromResultSet(resultSet);

                long id = resultSet.getLong(ORDER_USER_ID);
                String login = resultSet.getString(USER_LOGIN);
                String mail = resultSet.getString(USER_MAIL);
                String phone_number = resultSet.getString(USER_PHONE_NUMBER);
                String photo = resultSet.getString(USER_PHOTO);

                User user = User.builder()
                        .setId(id)
                        .setLogin(login)
                        .setMail(mail)
                        .setPhoto(photo)
                        .setPhoneNumber(phone_number)
                        .build();

                userHashMap.put(order, user);
            }
        } catch (SQLException | DatabaseException e) {
            throw new DaoException("Can't handle OrderDaoImpl.findAllOrdersWithUserinfo request", e);
        }
        return userHashMap;
    }

    @Override
    public List<Order> findAllUserOrders(long userId) throws DaoException {
        List<Order> orders = new ArrayList<>();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_ORDER_IN_CREATION);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                orders.add(createOrderFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Can't handle finding orders for user with id: " + userId, e);
        }
        return orders;
    }

    private String addFilterParameters(String requestLine, String userIdLine, String minPriceLine, String maxPriceLine,
                                       String[] orderStates, String[] paymentTypes){
        StringBuilder filterString = new StringBuilder("");
        if (!userIdLine.equals("")){
            filterString.append(ORDER_USER_ID).append(" = ")
                    .append(userIdLine).append(" AND ");
        }
        if (!minPriceLine.equals("")){
            filterString.append(ORDER_FINAL_PRICE).append(" >= ")
                    .append(minPriceLine).append(" AND ");
        }
        if (!maxPriceLine.equals("")){
            filterString.append(ORDER_FINAL_PRICE).append(" <= ")
                    .append(maxPriceLine).append(" AND ");
        }
        if (orderStates.length != 0){
            filterString.append(makeFilterGroup(ORDER_ORDER_STATUS, orderStates)).append(" AND ");
        }
        if (paymentTypes.length != 0){
            filterString.append(makeFilterGroup(ORDER_PAYMENT_TYPE, paymentTypes)).append(" AND ");
        }
        Integer wherePos = requestLine.indexOf(WHERE_LINE);
        StringBuilder resultString = new StringBuilder(requestLine);
        resultString.insert(wherePos + WHERE_LINE.length(), filterString);
        System.out.println(resultString.toString());
        return resultString.toString();
    }

    private String makeFilterGroup(String DBLine, String[] parameters) {
        StringBuilder itemsString = new StringBuilder(" (");
        for (String line : parameters) {
            itemsString.append(" ")
                    .append(DBLine)
                    .append(" = '")
                    .append(line)
                    .append("' OR");
        }
        itemsString.replace(itemsString.lastIndexOf("OR"), itemsString.length(), "");
        itemsString.append(") ");
        return itemsString.toString();
    }

    private Order createOrderFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(ORDER_ID);
        long userId = resultSet.getLong(ORDER_USER_ID);
        String orderStatusLine = resultSet.getString(ORDER_ORDER_STATUS);
        Timestamp orderTimeTimestamp = resultSet.getTimestamp(ORDER_TIME);
        LocalDateTime orderTime = orderTimeTimestamp.toLocalDateTime();
        Timestamp finishTimeTimestamp = resultSet.getTimestamp(ORDER_FINISH_TIME);
        LocalDateTime finishTime = finishTimeTimestamp.toLocalDateTime();
        String cardNumber = resultSet.getString(ORDER_CARD_NUMBER);
        String paymentTypeLine = resultSet.getString(ORDER_PAYMENT_TYPE);
        BigDecimal bonusesInPayment = resultSet.getBigDecimal(ORDER_BONUSES_IN_PAYMENT);
        BigDecimal finalPrice = resultSet.getBigDecimal(ORDER_FINAL_PRICE);
        OrderState orderState = OrderState.valueOf(orderStatusLine);
        PaymentType paymentType = PaymentType.valueOf(paymentTypeLine);

        Order order = Order.builder()
                .setId(id)
                .setUserId(userId)
                .setOrderState(orderState)
                .setOrderTime(orderTime)
                .setFinishTime(finishTime)
                .setCardNumber(cardNumber)
                .setPaymentType(paymentType)
                .setBonusesInPayment(bonusesInPayment)
                .setFinalPrice(finalPrice)
                .build();

        return order;
    }
}
