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
        this.quantity = String.valueOf(quantity);
        this.totalPrice = String.valueOf(totalPrice);
    }

    @EmbeddedId
    OrderDetailKey id = new OrderDetailKey();

    @ManyToOne
    @MapsId(value = "orderId")
    @JoinColumn(name = "order_id")
    Order order;
    @ManyToOne
    @MapsId(value = "foodItemId")
    @JoinColumn(name = "food_item_id")
    FoodItem foodItem;

    @NotNull
    @Column(name = "quantity")
//            ,columnDefinition = "integer")
    private String quantity;
    @NotNull
    @Column(name = "total_price")
//            ,columnDefinition = "numeric")
    private String totalPrice;

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

    public @NotNull String getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = String.valueOf(quantity);
    }

    public @NotNull String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = String.valueOf(totalPrice);
    }

    public OrderDetailKey getId() {
        return id;
    }

    public void setId(OrderDetailKey id) {
        this.id = id;
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
