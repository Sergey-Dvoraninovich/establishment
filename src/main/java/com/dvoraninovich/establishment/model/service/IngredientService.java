package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findAll() throws ServiceException;

    boolean addIngredient(Ingredient ingredient) throws ServiceException;
}
