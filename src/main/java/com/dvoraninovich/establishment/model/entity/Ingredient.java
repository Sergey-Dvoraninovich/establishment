package com.dvoraninovich.establishment.model.entity;

import java.util.Objects;

public class Ingredient {
    private long id;
    private String name;

    private Ingredient() {

    }

    //public Ingredient(long id, String name) {
    //    this.id = id;
    //    this.name = name;
    //}

    public static IngredientBuilder builder() {
        return new Ingredient().new IngredientBuilder();
    }

    public long getId() {
            return id;
        }

    public void setId(long id) {
            this.id = id;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
            this.name = name;
        }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("Ingredient {");
        result.append(" id = ").append(id);
        result.append(", name = '").append(name);
        result.append(" }");
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Ingredient ingredient = (Ingredient) obj;
        return id == ingredient.id
               && Objects.equals(name, ingredient.name);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = result * 31 + Long.hashCode(id);
        result = result * 31 + name.hashCode();

        return result;
    }

    public class IngredientBuilder{

        private IngredientBuilder() {

        }

        public IngredientBuilder setId(long id) {
            Ingredient.this.id = id;
            return this;
        }

        public IngredientBuilder setName(String name) {
            Ingredient.this.name = name;
            return this;
        }

        public Ingredient build() {
            return Ingredient.this;
        }
    }
}
