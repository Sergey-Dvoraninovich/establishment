package com.dvoraninovich.establishment.model.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class DishListItem {
    private long id;
    private long orderId;
    private long dishId;
    private int dishAmount;
    private BigDecimal finalDishPrice;

    private DishListItem() {

    }

    //TODO remove constructor
    /*
    public DishListItem(long id,
            long orderId,
            long dishId,
            int dishAmount,
            BigDecimal finalDishPrice) {
        this.id = id;
        this.orderId = orderId;
        this.dishId = dishId;
        this.dishAmount = dishAmount;
        this.finalDishPrice = finalDishPrice;
    }
    */

    public static DishListItem.DishListItemBuilder builder() {
        return new DishListItem().new DishListItemBuilder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = id;
    }

    public long getDishId() {
        return dishId;
    }

    public void setDishId(long dishId) {
        this.dishId = dishId;
    }

    public int getDishAmount() {
        return dishAmount;
    }

    public void setDishAmount(int dishAmount) {
        this.dishAmount = dishAmount;
    }

    public BigDecimal getDishFinalPrice() {
        return finalDishPrice;
    }

    public void setDishFinalPrice(BigDecimal finalDishPrice) {
        this.finalDishPrice = finalDishPrice;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("DishListItem {");
        result.append(" id = ").append(id);
        result.append(", id_order = '").append(orderId);
        result.append(", id_dish = '").append(dishId);
        result.append(", dishAmount = '").append(dishAmount);
        result.append(", finalDihPrice = '").append(finalDishPrice);
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

        DishListItem dishListItem = (DishListItem) obj;
        return id == dishListItem.id
                && Objects.equals(orderId, dishListItem.orderId)
                && Objects.equals(dishId, dishListItem.dishId)
                && Objects.equals(dishAmount, dishListItem.dishAmount)
                && Objects.equals(finalDishPrice, dishListItem.finalDishPrice);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = result * 31 + Long.hashCode(id);
        result = result * 31 + Long.hashCode(orderId);
        result = result * 31 + Long.hashCode(dishId);
        result = result * 31 + dishAmount;
        result = result * 31 + (finalDishPrice != null ? finalDishPrice.hashCode() : 0);

        return result;
    }

    public class DishListItemBuilder {

        private DishListItemBuilder() {

        }

        public DishListItemBuilder setId(long id) {
            DishListItem.this.id = id;
            return this;
        }

        public DishListItemBuilder setOrderId(long orderId) {
            DishListItem.this.orderId = orderId;
            return this;
        }

        public DishListItemBuilder setDishId(long dishId) {
            DishListItem.this.dishId = dishId;
            return this;
        }

        public DishListItemBuilder setDishAmount(int dishAmount) {
            DishListItem.this.dishAmount = dishAmount;
            return this;
        }

        public DishListItemBuilder setDishFinalPrice(BigDecimal finalDishPrice) {
            DishListItem.this.finalDishPrice = finalDishPrice;
            return this;
        }

        public DishListItem build() {
            return DishListItem.this;
        }
    }
}
