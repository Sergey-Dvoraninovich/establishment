package com.dvoraninovich.establishment.model.dao.impl;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.DatabaseException;
import com.dvoraninovich.establishment.model.dao.IngredientDao;
import com.dvoraninovich.establishment.model.entity.Ingredient;
import com.dvoraninovich.establishment.model.pool.DatabaseConnectionPool;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.dvoraninovich.establishment.model.dao.DatabaseTableColumn.INGREDIENT_ID;
import static com.dvoraninovich.establishment.model.dao.DatabaseTableColumn.INGREDIENT_NAME;

public class IngredientDaoImpl implements IngredientDao {
    private static final Logger logger = LogManager.getLogger(IngredientDaoImpl.class);
    private static final IngredientDaoImpl instance = new IngredientDaoImpl();

    private static final String SELECT_ALL_INGREDIENT
            = "SELECT ingredients.id, ingredients.name "
            + "FROM ingredients;";
    private static final String SELECT_INGREDIENT_BY_ID
            = "SELECT ingredients.name "
            + "FROM ingredients "
            + "WHERE (id = ?);";
    private static final String INSERT_INGREDIENT
            = "INSERT ingredients(name) "
            + "VALUES (?);";
    private static final String UPDATE_INGREDIENT
            = "UPDATE ingredeints "
            + "SET name = ? "
            + "WHERE id = ?;";

    public static IngredientDaoImpl getInstance() {
        return instance;
    }

    private IngredientDaoImpl() {
    }

    @Override
    public List<Ingredient> findAll() throws DaoException {
        List<Ingredient> ingredientList = new ArrayList<>();
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_INGREDIENT);
            while (resultSet.next()) {
                Ingredient ingredient = createIngredientFromResultSet(resultSet);
                ingredientList.add(ingredient);
            }
        } catch (SQLException | DatabaseException e) {
            logger.error("Impossible to find all ingredients", e);
            throw new DaoException("Impossible to find all ingredients", e);
        }
        return ingredientList;
    }

    @Override
    public boolean insert(Ingredient ingredient) throws DaoException {
        boolean successfulOperation = false;
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
        ) {
             PreparedStatement statement = connection.prepareStatement(INSERT_INGREDIENT);
             statement.setString(1, ingredient.getName());
             Integer rowsNum = statement.executeUpdate();
             successfulOperation = rowsNum != 0;
        } catch (DatabaseException | SQLException e) {
            logger.error("Impossible to insert ingredient " + ingredient.getName() +
                    "with id: " + ingredient.getId(), e);
            throw new DaoException("Impossible to insert ingredient " + ingredient.getName() +
                                 "with id: " + ingredient.getId(), e);
        }
        return successfulOperation;
    }

    @Override
    public boolean update(Ingredient ingredient) throws DaoException {
        boolean successfulOperation = false;
        if (!Optional.empty().equals(findById(ingredient.getId()))) {
            try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
            ) {
                PreparedStatement statement = connection.prepareStatement(UPDATE_INGREDIENT);
                statement.setString(1, ingredient.getName());
                statement.setLong(2, ingredient.getId());
                Integer rowsNum = statement.executeUpdate();
                successfulOperation = rowsNum != 0;
            } catch (DatabaseException | SQLException e) {
                logger.error("Impossible to update ingredient " + ingredient.getName() +
                        " with id: " + ingredient.getId(), e);
                throw new DaoException("Impossible to update ingredient " + ingredient.getName() +
                        " with id: " + ingredient.getId(), e);
            }
        }
        return successfulOperation;
    }

    @Override
    public Optional<Ingredient> findById(Long id) throws DaoException {
        Ingredient ingredient = Ingredient.builder().build();
        try (Connection connection = DatabaseConnectionPool.getInstance().acquireConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_INGREDIENT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ingredient.setId(resultSet.getInt(INGREDIENT_ID));
                ingredient.setName(resultSet.getString(INGREDIENT_NAME));
            }
        } catch (SQLException | DatabaseException e) {
            logger.error("Impossible to find dish by id", e);
            throw new DaoException("Impossible to find dish by id", e);
        }
        return Optional.of(ingredient);
    }

    private Ingredient createIngredientFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(INGREDIENT_ID);
        String name = resultSet.getString(INGREDIENT_NAME);

        Ingredient ingredient = Ingredient.builder()
                .setId(id)
                .setName(name)
                .build();

        return ingredient;
    }
}
