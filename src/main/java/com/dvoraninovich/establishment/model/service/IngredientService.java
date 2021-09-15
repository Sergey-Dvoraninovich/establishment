package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    List<Ingredient> findAll() throws ServiceException;

    Optional<Ingredient> findIngredientById(long id) throws ServiceException;

    boolean addIngredient(Ingredient ingredient) throws ServiceException;
}
