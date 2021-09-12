package com.dvoraninovich.establishment.model.dao;

import com.dvoraninovich.establishment.model.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetParser {
    Order createOrder(ResultSet resultSet) throws SQLException;
    Dish createDish(ResultSet resultSet) throws SQLException;
    DishListItem createDishListItem(ResultSet resultSet) throws SQLException;
    Ingredient createIngredient(ResultSet resultSet) throws SQLException;
    User createUser(ResultSet resultSet) throws SQLException;
}
