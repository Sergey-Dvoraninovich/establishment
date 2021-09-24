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
    private static final String FIND_USER_BY_EMAIL
            = "SELECT users.id, users.login, users.mail, "
            + "statuses.status, roles.role, "
            + "users.card_number, users.phone_number, users.bonuses_amount, "
            + "users.photo "
            + "FROM users "
            + "INNER JOIN roles "
            + "ON users.id_role = roles.id "
            + "INNER JOIN statuses "
            + "ON users.id_status = statuses.id "
            + "WHERE users.mail = ?; ";
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
    private static final String FIND_USER_BY_PHONE_NUM
            = "SELECT users.id, users.login, users.mail, "
            + "statuses.status, roles.role, "
            + "users.card_number, users.phone_number, users.bonuses_amount,"
            + "users.photo "
            + "FROM users "
            + "INNER JOIN roles "
            + "ON users.id_role = roles.id "
            + "INNER JOIN statuses "
            + "ON users.id_status = statuses.id "
            + "WHERE users.phone_number = ?; ";
    private static final String SET_PASSWORD_BY_ID
            = "UPDATE users "
            + "SET password_hash = ? "
            + "WHERE id = ?;";
    private static final String SET_SALT_BY_ID
            = "UPDATE users "
            + "SET salt = ? "
            + "WHERE id = ?;";
    private static final String INSERT_USER
            = "INSERT users(login, mail, password_hash, salt, id_status, id_role, "
            + "card_number, phone_number, bonuses_amount, photo, code, code_expiration_time) "
            + "VALUES (?, ?, ?,  ?, ?, ?,  ?, ?, ?,  ?, ?, "
            + "TIMESTAMPADD(MINUTE, 5, CURRENT_TIMESTAMP()));";
    private static final String UPDATE_USER
            = "UPDATE users "
            + "SET mail = ?, id_status = ?, id_role = ?, "
            + "card_number = ?, phone_number = ?, bonuses_amount = ?, "
            + "photo = ? "
            + "WHERE id = ?;";
    private static final String DELETE_USER
            = "UPDATE users " +
            "SET id_status = 4 " +
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
    private static final String SET_VERIFICATION_CODE_BY_ID
            = "UPDATE users " +
            "SET id_status = ?, code_expiration_time = TIMESTAMPADD(MINUTE, 5, CURRENT_TIMESTAMP())) " +
            "WHERE id = ?;";

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
            throw new DaoException("Error while finding users", e);
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
            throw new DaoException("Error while finding user with id: " + id, e);
        }
        return user;
    }

    @Override
    public boolean insert(User user) throws DaoException {
        throw new UnsupportedOperationException();
    }

    public Long insertUser(User user, String passwordHash, String salt, String code) throws DaoException {
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
                statement.setString(11, code);
                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                generatedKeys.next();
                id = generatedKeys.getLong(1);
            } catch (DatabaseException | SQLException e) {
                throw new DaoException("Error while inserting user with login: " + user.getLogin(), e);
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
            throw new DaoException("Error while updating user with login: " + user.getLogin(), e);
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
            throw new DaoException("Error while deleting user with id: " + id, e);
        }
        return successfulOperation;
    }

    @Override
    public Long countUsers() throws DaoException {
        Long usersAmount = Long.valueOf(0);
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(COUNT_FILTERED_USERS);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                usersAmount = resultSet.getLong(1);
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Error while counting users", e);
        }
        return usersAmount;
    }

    @Override
    public List<User> findFilteredUsers(long minPos, long maxPos) throws DaoException {
        List<User> users = new ArrayList<>();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(SELECT_FILTERED_USERS);
            statement.setLong(1, minPos);
            statement.setLong(2, maxPos);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(createUserFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Error while selecting users", e);
        }
        return users;
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        Optional<User> user = Optional.empty();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = Optional.of(createUserFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Error while finding user with email: " + email, e);
        }
        return user;
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
            throw new DaoException("Error while finding user with login: " + login, e);
        }
        return user;
    }

    @Override
    public Optional<User> findUserByPhoneNum(String phoneNum) throws DaoException {
        Optional<User> user = Optional.empty();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_PHONE_NUM);
            statement.setString(1, phoneNum);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = Optional.of(createUserFromResultSet(resultSet));
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Error while finding user with phone number: " + phoneNum, e);
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
            successfulOperation = statement.execute();
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Error while inserting password for user with id: " + id, e);
        }
        return successfulOperation;
    }

    @Override
    public boolean setSaltById(Long id, String salt) throws DaoException {
        boolean successfulOperation;
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(SET_SALT_BY_ID);
            statement.setString(1, salt);
            statement.setLong(2, id);
            successfulOperation = statement.execute();
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Error while inserting salt for user with id: " + id, e);
        }
        return successfulOperation;
    }

    @Override
    public boolean setCodeById(Long id, String code) throws DaoException {
        boolean successfulOperation;
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(SET_VERIFICATION_CODE_BY_ID);
            statement.setString(1, code);
            statement.setLong(2, id);
            successfulOperation = statement.execute();
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Error while setting code for user with id: " + id, e);
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
            throw new DaoException("Error while finding password of user with id: " + id, e);
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
            throw new DaoException("Error while finding salt of user with id: " + id, e);
        }
        return salt;
    }

    @Override
    public Optional<String> getCodeById(Long id) throws DaoException {
        Optional<String> code = Optional.empty();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(GET_CODE_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                code = Optional.of(resultSet.getString(USER_CODE));
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Error while finding code of user with id: " + id, e);
        }
        return code;
    }

    @Override
    public Optional<LocalDateTime> getCodeExpirationTimeById(Long id) throws DaoException {
        Optional<LocalDateTime> codeExpirationTime = Optional.empty();
        try(Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
            PreparedStatement statement = connection.prepareStatement(GET_CODE_EXPIRATION_TIME_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Timestamp codeExpirationTimestamp = resultSet.getTimestamp(USER_CODE_EXPIRATION_TIME);
                codeExpirationTime = Optional.of(codeExpirationTimestamp.toLocalDateTime());
            }
        } catch (DatabaseException | SQLException e) {
            throw new DaoException("Error while finding codeExpirationTime of user with id: " + id, e);
        }
        return codeExpirationTime;
    }

    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong(USER_ID);
        String login = resultSet.getString(USER_LOGIN);
        String mail = resultSet.getString(USER_MAIL);
//        String passwordHash = resultSet.getString(USER_PASSWORD_HASH);
//        String salt = resultSet.getString(USER_SALT);
        String status = resultSet.getString(USER_STATUS);
        String role = resultSet.getString(USER_ROLE);
        String card_number = resultSet.getString(USER_CARD_NUMBER);
        String phone_number = resultSet.getString(USER_PHONE_NUMBER);
        BigDecimal bonuses_amount = resultSet.getBigDecimal(USER_BONUSES_AMOUNT);
        String photo = resultSet.getString(USER_PHOTO);
//        String code = resultSet.getString(USER_CODE);
//        Timestamp expirationCodeTimeTimestamp = resultSet.getTimestamp(USER_CODE_EXPIRATION_TIME);
//        LocalDateTime expirationCodeTime = expirationCodeTimeTimestamp.toLocalDateTime();

        User user = User.builder()
                        .setId(userId)
        .setLogin(login)
        .setMail(mail)
//        .setPasswordHash(passwordHash)
//        .setSalt(salt)
        .setStatus(UserStatus.valueOf(status))
        .setRole(Role.valueOf(role))
        .setCardNumber(card_number)
        .setPhoneNumber(phone_number)
        .setBonusesAmount(bonuses_amount)
        .setPhoto(photo)
//        .setCode(code)
//        .setExpirationCodeTime(expirationCodeTime)
                 .build();

        return user;
    }
}
