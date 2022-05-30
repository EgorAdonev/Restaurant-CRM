package com.adonev.waurma.crm.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "order_detail")
public class OrderDetail {
    public OrderDetail() {
    }

    public OrderDetail(Order order, FoodItem foodItem, Integer quantity, Double totalPrice) {
        this.order = order;
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    @EmbeddedId
    OrderDetailKey id;

    @ManyToOne
    @MapsId(value = "orderId")
    @JoinColumn(name = "order_id")
    Order order;
    @ManyToOne
    @MapsId(value = "foodItemId")
    @JoinColumn(name = "food_item_id")
    FoodItem foodItem;

    @NotNull
    @Column(name = "quantity",columnDefinition = "integer")
    private Integer quantity;
    @NotNull
    @Column(name = "total_price",columnDefinition = "numeric")
    private Double totalPrice;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", order=" + order +
                ", foodItem=" + foodItem +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
