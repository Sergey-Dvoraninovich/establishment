package com.dvoraninovich.establishment.model.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Dish {
    private long id;
    private BigDecimal price;
    private int calories;
    private int amountGrams;
    private BigDecimal averageMark;
    private String name;
    private Boolean isAvailable;
    private String photo;

    private Dish(){

    }

    public static Dish.DishBuilder builder() {
        return new Dish().new DishBuilder();
    }

    //TODO remove constructor
    /*
    public Dish(long id, BigDecimal price,
            int calories,
            int amountGrams,
            BigDecimal averageMark,
            String name,
            Boolean isAvailable,
            String photo){
        this.id = id;
        this.price = price;
        this.calories = calories;
        this.amountGrams = amountGrams;
        this.averageMark = averageMark;
        this.name = name;
        this.isAvailable = isAvailable;
        this.photo = photo;
    }
    */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getAmountGrams() {
        return amountGrams;
    }

    public void setAmountGrams(int amountGrams) {
        this.amountGrams = amountGrams;
    }

    public BigDecimal getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(BigDecimal averageMark) {
        this.averageMark = averageMark;
    }

    public String getName() {return name;}

    public void setName(String ingredients) {this.name = ingredients;}

    public Boolean getIsAvailable() {return isAvailable;}

    public void setIsAvailable(Boolean isAvailable) {this.isAvailable = isAvailable;}

    public String getPhoto() {return photo;}

    public void setPhoto(String photo) {this.photo = photo;}

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

        Dish dish = (Dish) obj;
        return id == dish.id
                && Objects.equals(price, dish.price)
                && Objects.equals(calories, dish.calories)
                && Objects.equals(amountGrams, dish.amountGrams)
                && Objects.equals(averageMark, dish.averageMark)
                && Objects.equals(name, dish.name)
                && Objects.equals(isAvailable, dish.isAvailable)
                && Objects.equals(photo, dish.photo);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = result * 31 + Long.hashCode(id);
        result = result * 31 + (price != null ? price.hashCode() : 0);
        result = result * 31 + calories;
        result = result * 31 + amountGrams;
        result = result * 31 + (averageMark != null ? averageMark.hashCode() : 0);
        result = result * 31 + (name != null ? name.hashCode() : 0);
        result = result * 31 + (isAvailable != null ? isAvailable.hashCode() : 0);
        result = result * 31 + (photo != null ? photo.hashCode() : 0);

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("Dish {");
        result.append(" id = ").append(id);
        result.append(", price = '").append(price);
        result.append(", calories = '").append(calories);
        result.append(", amountGrams = '").append(amountGrams);
        result.append(", averageMark = '").append(averageMark);
        result.append(", name = '").append(name);
        result.append(", isAvailable = '").append(isAvailable);
        result.append(", photo = '").append(photo);
        result.append(" }");
        return result.toString();
    }

    public class DishBuilder{

        private DishBuilder() {

        }

        public DishBuilder setId(long id) {
            Dish.this.id = id;
            return this;
        }

        public DishBuilder setPrice(BigDecimal price) {
            Dish.this.price = price;
            return this;
        }

        public DishBuilder setCalories(int calories) {
            Dish.this.calories = calories;
            return this;
        }

        public DishBuilder setAmountGrams(int amountGrams) {
            Dish.this.amountGrams = amountGrams;
            return this;
        }

        public DishBuilder setAverageMark(BigDecimal averageMark) {
            Dish.this.averageMark = averageMark;
            return this;
        }

        public DishBuilder setName(String name) {
            Dish.this.name = name;
            return this;
        }

        public DishBuilder setIsAvailable(Boolean isAvailable) {
            Dish.this.isAvailable = isAvailable;
            return this;
        }

        public DishBuilder setPhoto(String photo) {
            Dish.this.photo = photo;
            return this;
        }

        public Dish build() {
            return Dish.this;
        }
    }
}
