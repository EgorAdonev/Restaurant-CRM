package com.adonev.waurma.crm.data.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderDetailKey implements Serializable {
    @Column(name = "order_id")
    Long orderId;
    @Column(name = "food_item_id")
    Long foodItemId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getFoodItemId() {
        return foodItemId;
    }

    public void setFoodItemId(Long foodItemId) {
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