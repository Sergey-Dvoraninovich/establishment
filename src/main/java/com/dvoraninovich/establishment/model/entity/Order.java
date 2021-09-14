package com.dvoraninovich.establishment.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private long id;
    private OrderState orderState;
    private LocalDateTime orderTime;
    private LocalDateTime finishTime;
    private PaymentType paymentType;
    private String cardNumber;
    private long userId;
    private BigDecimal bonusesInPayment;
    private BigDecimal finalPrice;

    private Order() {}

    public static Order.OrderBuilder builder() {
        return new Order().new OrderBuilder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getBonusesInPayment() {
        return bonusesInPayment;
    }

    public void setBonusesInPayment(BigDecimal bonusesInPayment) {
        this.bonusesInPayment = bonusesInPayment;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
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

        Order order = (Order) obj;
        return id == order.id
                && Objects.equals(orderState, order.orderState)
                && Objects.equals(orderTime, order.orderTime)
                && Objects.equals(finishTime, order.finishTime)
                && Objects.equals(paymentType, order.paymentType)
                && Objects.equals(cardNumber, order.cardNumber)
                && Objects.equals(userId, order.userId)
                && Objects.equals(bonusesInPayment, order.bonusesInPayment)
                && Objects.equals(finalPrice, order.finalPrice);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = result * 31 + Long.hashCode(id);
        result = result * 31 + orderState.hashCode();
        result = result * 31 + finishTime.hashCode();
        result = result * 31 + (orderTime != null ? orderTime.hashCode() : 0);
        result = result * 31 + paymentType.hashCode();
        result = result * 31 + cardNumber.hashCode();
        result = result * 31 + Long.hashCode(userId);
        result = result * 31 + bonusesInPayment.hashCode();
        result = result * 31 + finalPrice.hashCode();

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("Order {");
        result.append(" id = ").append(id);
        result.append(", orderState = '").append(orderState);
        result.append(", orderTime = '").append(orderTime);
        result.append(", finishTime = '").append(finishTime);
        result.append(", paymentType = '").append(paymentType);
        result.append(", cardNumber = '").append(cardNumber);
        result.append(", userId = '").append(userId);
        result.append(", bonusesInPayment = '").append(bonusesInPayment);
        result.append(", finalPrice = '").append(finalPrice);
        result.append(" }");
        return result.toString();
    }

    public class OrderBuilder {

        private OrderBuilder() {

        }

        public OrderBuilder setId(long id) {
            Order.this.id = id;
            return this;
        }

        public OrderBuilder setOrderState(OrderState orderState) {
            Order.this.orderState = orderState;
            return this;
        }

        public OrderBuilder setOrderTime(LocalDateTime orderTime) {
            Order.this.orderTime = orderTime;
            return this;
        }

        public OrderBuilder setFinishTime(LocalDateTime finishTime) {
            Order.this.finishTime = finishTime;
            return this;
        }

        public OrderBuilder setPaymentType(PaymentType paymentType) {
            Order.this.paymentType = paymentType;
            return this;
        }

        public OrderBuilder setCardNumber(String cardNumber) {
            Order.this.cardNumber = cardNumber;
            return this;
        }

        public OrderBuilder setUserId(long userId) {
            Order.this.userId = userId;
            return this;
        }

        public OrderBuilder setBonusesInPayment(BigDecimal bonusesInPayment) {
            Order.this.bonusesInPayment = bonusesInPayment;
            return this;
        }

        public OrderBuilder setFinalPrice(BigDecimal finalPrice) {
            Order.this.finalPrice = finalPrice;
            return this;
        }

        public Order build() {
            return Order.this;
        }
    }
}
