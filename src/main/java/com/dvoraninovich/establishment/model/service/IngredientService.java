package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Ingredient;

import java.util.List;
import java.util.Optional;

/**
 * The interface Ingredient service.
 */
public interface IngredientService {
    /**
     * Find all.
     *
     * @return the list of ingredients
     * @throws ServiceException the service exception
     */
    List<Ingredient> findAll() throws ServiceException;

    /**
     * Find ingredient by id.
     *
     * @param id the id
     * @return the optional ingredient. Empty if there is no such ingredient
     * @throws ServiceException the service exception
     */
    Optional<Ingredient> findIngredientById(long id) throws ServiceException;

    /**
     * Add ingredient.
     *
     * @param ingredient the ingredient
     * @return the boolean result of adding ingredient
     * @throws ServiceException the service exception
     */
    boolean addIngredient(Ingredient ingredient) throws ServiceException;
}
