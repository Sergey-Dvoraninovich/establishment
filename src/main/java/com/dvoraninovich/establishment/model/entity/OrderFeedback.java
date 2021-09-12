package com.dvoraninovich.establishment.model.entity;

import java.util.Date;
import java.util.Objects;

public class OrderFeedback {
    private long id;
    private long userId;
    private long orderId;
    private String text;
    private Date time;
    private int mark;

    private OrderFeedback() {

    }

    public static OrderFeedback.OrderFeedbackBuilder builder() {
        return new OrderFeedback().new OrderFeedbackBuilder();
    }

    //TODO remove constructor
    /*
    public OrderFeedback(long id,
            long userId,
            long orderId,
            String text,
            Date time,
            int mark) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.text = text;
        this.time = time;
        this.mark = mark;
    }
    */


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        final StringBuilder result = new StringBuilder("OrderFeedback {");
        result.append(" id = ").append(id);
        result.append(", user = '").append(userId);
        result.append(", order = '").append(orderId);
        result.append(", text = '").append(text);
        result.append(", time = '").append(time);
        result.append(", mark = '").append(mark);
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

        OrderFeedback orderFeedback = (OrderFeedback) obj;
        return id == orderFeedback.id
                && Objects.equals(userId, orderFeedback.userId)
                && Objects.equals(orderId, orderFeedback.orderId)
                && Objects.equals(text, orderFeedback.text)
                && Objects.equals(time, orderFeedback.time)
                && Objects.equals(mark, orderFeedback.mark);
    }

    @Override
    public int hashCode() {
        int result = 1;

        result = result * 31 + Long.hashCode(id);
        result = result * 31 + Long.hashCode(userId);
        result = result * 31 + Long.hashCode(orderId);
        result = result * 31 + (text != null ? text.hashCode() : 0);
        result = result * 31 + (time != null ? time.hashCode(): 0);
        result = result * 31 + mark;

        return result;
    }

    public class OrderFeedbackBuilder {

        private OrderFeedbackBuilder() {

        }

        public OrderFeedbackBuilder setId(long id) {
            OrderFeedback.this.id = id;
            return this;
        }

        public OrderFeedbackBuilder setUserId(long userId) {
            OrderFeedback.this.userId = userId;
            return this;
        }

        public OrderFeedbackBuilder setOrderId(long orderId) {
            OrderFeedback.this.orderId = orderId;
            return this;
        }

        public OrderFeedbackBuilder setText(String text) {
            OrderFeedback.this.text = text;
            return this;
        }

        public OrderFeedbackBuilder setTime(Date time) {
            OrderFeedback.this.time = time;
            return this;
        }

        public OrderFeedbackBuilder setMark(int mark) {
            OrderFeedback.this.mark = mark;
            return this;
        }

        public OrderFeedback build() {
            return OrderFeedback.this;
        }
    }
}
