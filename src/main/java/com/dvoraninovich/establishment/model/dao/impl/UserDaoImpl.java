package com.dvoraninovich.establishment.model.dao.impl;

import com.dvoraninovich.establishment.model.entity.Role;
import com.dvoraninovich.establishment.model.entity.User;
import com.dvoraninovich.establishment.model.entity.UserStatus;
import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.DatabaseException;
import com.dvoraninovich.establishment.model.dao.UserDao;
import com.dvoraninovich.establishment.model.pool.DatabaseConnectionPool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.model.dao.DatabaseTableColumn.*;

public class UserDaoImpl implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private static final UserDao instance = new UserDaoImpl();

    private static final String SELECT_ALL_USERS
            = "SELECT users.id, users.login, users.mail, "
            + "statuses.status, roles.role, "
            + "users.card_number, users.phone_number, users.bonuses_amount, "
            + "users.photo "
            + "FROM users "
            + "INNER JOIN roles "
            + "ON users.id_role = roles.id "
            + "INNER JOIN statuses "
            + "ON users.id_status = statuses.id; ";
    private static final String COUNT_FILTERED_USERS
            = "SELECT COUNT(users.id) "
            + "FROM users "
            + "INNER JOIN roles "
            + "ON users.id_role = roles.id "
            + "INNER JOIN statuses "
            + "ON users.id_status = statuses.id; ";
    private static final String SELECT_FILTERED_USERS
            = "SELECT users.id, users.login, users.mail, "
            + "statuses.status, roles.role, "
            + "users.card_number, users.phone_number, users.bonuses_amount, "
            + "users.photo "
            + "FROM users "
            + "INNER JOIN roles "
            + "ON users.id_role = roles.id "
            + "INNER JOIN statuses "
            + "ON users.id_status = statuses.id "
            + "LIMIT ?, ?;";
    private static final String FIND_USER_BY_LOGIN
            = "SELECT users.id, users.login, users.mail, "
            + "statuses.status, roles.role, "
            + "users.card_number, users.phone_number, users.bonuses_amount, "
            + "users.photo "
            + "FROM users "
            + "INNER JOIN roles "
            + "ON users.id_role = roles.id "
            + "INNER JOIN statuses "
            + "ON users.id_status = statuses.id "
            + "WHERE users.login = ?; ";
    private static final String FIND_USER_BY_ID
            = "SELECT users.id, users.login, users.mail, "
            + "statuses.status, roles.role, "
            + "users.card_number, users.phone_number, users.bonuses_amount, "
            + "users.photo "
            + "FROM users "
            + "INNER JOIN roles "
            + "ON users.id_role = roles.id "
            + "INNER JOIN statuses "
            + "ON users.id_status = statuses.id "
            + "WHERE users.id = ?; ";
    private static final String SET_PASSWORD_BY_ID
            = "UPDATE users "
            + "SET password_hash = ? "
            + "WHERE id = ?;";
    private static final String INSERT_USER
            = "INSERT users(login, mail, password_hash,  salt, id_status, id_role, "
            + "card_number, phone_number, bonuses_amount, photo) "
            + "VALUES (?, ?, ?,  ?, ?, ?,  ?, ?, ?, ? ;";
    private static final String UPDATE_USER
            = "UPDATE users "
            + "SET mail = ?, id_status = ?, id_role = ?, "
            + "card_number = ?, phone_number = ?, bonuses_amount = ?, "
            + "photo = ? "
            + "WHERE id = ?;";
    private static final String DELETE_USER
            = "UPDATE users " +
            "SET id_status = 3 " +
            "WHERE id = ?;";
    private static final String GET_PASSWORD_BY_ID
            = "SELECT users.password_hash "
            + "FROM users "
            + "WHERE id = ?;";
    private static final String GET_SALT_BY_ID
            = "SELECT users.salt "
            + "FROM users "
            + "WHERE id = ?;";
    private static final String GET_CODE_BY_ID
            = "SELECT users.code "
            + "FROM users "
            + "WHERE id = ?;";
    private static final String GET_CODE_EXPIRATION_TIME_BY_ID
            = "SELECT users.code_expiration_time "
            + "FROM users "
            + "WHERE id = ?;";
    private static final String LIMIT_LINE = "LIMIT";

    public static UserDao getInstance(){
        return instance;
    }

    @Override
    public List<User> findAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS);
            ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()){
                users.add(createUserFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to find users", e);
            throw new DaoException("Impossible to find users", e);
        }
        return users;
    }

    @Override
    public Optional<User> findById(Long id) throws DaoException {
        Optional<User> user = Optional.empty();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = Optional.of(createUserFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to find user with id: " + id, e);
            throw new DaoException("Impossible to find user with id: " + id, e);
        }
        return user;
    }

    @Override
    public boolean insert(User user) throws DaoException {
        throw new UnsupportedOperationException();
    }

    public Long insertUser(User user, String passwordHash, String salt) throws DaoException {
        Long id = Long.valueOf(0);
        if (Optional.empty().equals(findUserByLogin(user.getLogin()))) {
            try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
            ) {
                PreparedStatement statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getMail());
                statement.setString(3, passwordHash);
                statement.setString(4, salt);
                statement.setInt(5, user.getStatus().ordinal()+1);
                statement.setInt(6, user.getRole().ordinal()+1);
                statement.setString(7, user.getCardNumber());
                statement.setString(8, user.getPhoneNumber());
                statement.setBigDecimal(9, user.getBonusesAmount());
                statement.setString(10, user.getPhoto());
                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                id = generatedKeys.getLong(1);
            } catch (DatabaseException | SQLException e) {
                logger.error("Impossible to insert user with login: " + user.getLogin(), e);
                throw new DaoException("Impossible to insert user with login: " + user.getLogin(), e);
            }
        }
        return id;
    }

    @Override
    public boolean update(User user) throws DaoException {
        boolean successfulOperation;
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER);
            statement.setString(1, user.getMail());
            statement.setInt(2, user.getStatus().ordinal()+1);
            statement.setInt(3, user.getRole().ordinal()+1);
            statement.setString(4, user.getCardNumber());
            statement.setString(5, user.getPhoneNumber());
            statement.setBigDecimal(6, user.getBonusesAmount());
            statement.setString(7, user.getPhoto());
            statement.setLong(8, user.getId());
            Integer rowsNum = statement.executeUpdate();
            successfulOperation = rowsNum != 0;
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to update user with login: " + user.getLogin(), e);
            throw new DaoException("Impossible to update user with login: " + user.getLogin(), e);
        }
        return successfulOperation;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        boolean successfulOperation;
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(DELETE_USER);
            statement.setLong(1, id);
            Integer rowsNum = statement.executeUpdate();
            successfulOperation = rowsNum != 0;
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to delete user with id: " + id, e);
            throw new DaoException("Impossible to delete user with id: " + id, e);
        }
        return successfulOperation;
    }

    @Override
    public Long countUsers(String login, String mail, String phoneNumber, String cardNumber, String[] userStatuses, String[] userRoles) throws DaoException {
        Long usersAmount = Long.valueOf(0);
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            String requestLine = addFilterParameters(COUNT_FILTERED_USERS, login, mail,
                    phoneNumber, cardNumber, userStatuses, userRoles);
            PreparedStatement statement = connection.prepareStatement(requestLine);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                usersAmount = resultSet.getLong(1);
            }
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to count users", e);
            throw new DaoException("Impossible to count users", e);
        }
        return usersAmount;
    }

    @Override
    public List<User> findFilteredUsers(String login, String mail, String phoneNumber, String cardNumber, String[] userStatuses, String[] userRoles, long minPos, long maxPos) throws DaoException {
        List<User> users = new ArrayList<>();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            String requestLine = addFilterParameters(SELECT_FILTERED_USERS, login, mail,
                    phoneNumber, cardNumber, userStatuses, userRoles);
            PreparedStatement statement = connection.prepareStatement(requestLine);
            statement.setLong(1, minPos-1);
            statement.setLong(2, maxPos);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(createUserFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to select users", e);
            throw new DaoException("Impossible to select users", e);
        }
        return users;
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        Optional<User> user = Optional.empty();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
            ) {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = Optional.of(createUserFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to find user with login: " + login, e);
            throw new DaoException("Impossible to find user with login: " + login, e);
        }
        return user;
    }

    @Override
    public boolean setPasswordById(Long id, String password) throws DaoException {
        boolean successfulOperation;
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(SET_PASSWORD_BY_ID);
            statement.setString(1, password);
            statement.setLong(2, id);
            Integer rowsNum = statement.executeUpdate();
            successfulOperation = rowsNum != 0;
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to insert password for user with id: " + id, e);
            throw new DaoException("Impossible to insert password for user with id: " + id, e);
        }
        return successfulOperation;
    }

    @Override
    public Optional<String> getPasswordById(Long id) throws DaoException {
        Optional<String> passwordHash = Optional.empty();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(GET_PASSWORD_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                passwordHash = Optional.of(resultSet.getString(USER_PASSWORD_HASH));
            }
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to find password of user with id: " + id, e);
            throw new DaoException("Impossible to find password of user with id: " + id, e);
        }
        return passwordHash;
    }

    @Override
    public Optional<String> getSaltById(Long id) throws DaoException {
        Optional<String> salt = Optional.empty();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(GET_SALT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                salt = Optional.of(resultSet.getString(USER_SALT));
            }
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to find salt of user with id: " + id, e);
            throw new DaoException("Impossible to find salt of user with id: " + id, e);
        }
        return salt;
    }

    private String addFilterParameters(String requestLine, String login, String mail, String phoneNumber,
                                       String cardNumber, String[] userStatuses, String[] userRoles){
        StringBuilder filterString = new StringBuilder(" WHERE ");
        if (!login.equals("")){
            filterString.append(USER_LOGIN).append(" LIKE '%")
                    .append(login).append("%' AND ");
        }
        if (!mail.equals("")){
            filterString.append(USER_MAIL).append(" LIKE '%")
                    .append(mail).append("%' AND ");
        }
        if (!phoneNumber.equals("")){
            filterString.append(USER_PHONE_NUMBER).append(" LIKE '%")
                    .append(phoneNumber).append("%' AND ");
        }
        if (!cardNumber.equals("")){
            filterString.append(USER_CARD_NUMBER).append(" LIKE '%")
                    .append(cardNumber).append("%' AND ");
        }
        if (userStatuses.length != 0){
            filterString.append(makeFilterGroup(USER_STATUS, userStatuses)).append(" AND ");
        }
        if (userRoles.length != 0){
            filterString.append(makeFilterGroup(USER_ROLE, userRoles)).append(" AND ");
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
        resultString.insert(wherePos + lineToFind.length() - 1, filterString);
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

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong(USER_ID);
        String login = resultSet.getString(USER_LOGIN);
        String mail = resultSet.getString(USER_MAIL);
        String status = resultSet.getString(USER_STATUS);
        String role = resultSet.getString(USER_ROLE);
        String card_number = resultSet.getString(USER_CARD_NUMBER);
        String phone_number = resultSet.getString(USER_PHONE_NUMBER);
        BigDecimal bonuses_amount = resultSet.getBigDecimal(USER_BONUSES_AMOUNT);
        String photo = resultSet.getString(USER_PHOTO);

        User user = User.builder()
                        .setId(userId)
        .setLogin(login)
        .setMail(mail)
        .setStatus(UserStatus.valueOf(status))
        .setRole(Role.valueOf(role))
        .setCardNumber(card_number)
        .setPhoneNumber(phone_number)
        .setBonusesAmount(bonuses_amount)
        .setPhoto(photo)
        .build();

        return user;
    }
}
