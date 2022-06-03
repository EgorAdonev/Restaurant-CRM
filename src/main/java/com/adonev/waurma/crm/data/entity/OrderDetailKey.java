package com.adonev.waurma.crm.data.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderDetailKey implements Serializable  {

    @Column(name = "order_id")
    private Integer orderId;
    @Column(name = "food_item_id")
    private Integer foodItemId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(Integer foodItemId) {
        this.foodItemId = foodItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailKey that = (OrderDetailKey) o;
        return orderId.equals(that.orderId) && foodItemId.equals(that.foodItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, foodItemId);
    }
}