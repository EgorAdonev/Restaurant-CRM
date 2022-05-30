package com.adonev.waurma.crm.data.entity;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "food_item")
public class FoodItem {
    public FoodItem() {
    }

    public FoodItem(String foodName, Double foodPrice, List<OrderDetail> orderDetails) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.orderDetails = orderDetails;
    }

    @Id
    @SequenceGenerator(name = "food_item_id_sequence",sequenceName = "food_item_id_sequence",allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,generator = "food_item_id_sequence")
    @Column(name = "food_item_id")
    private Long foodItemId;

    @NotBlank
    @Column(name = "food_name")
    private String foodName;

    @NotNull
    @DecimalMin(value = "0.0001")
    @Column(name = "food_price")
    private Double foodPrice;

    @OneToMany(mappedBy = "foodItem")
    List<OrderDetail> orderDetails = new ArrayList<>();

    //    @ManyToOne
//    @JoinColumn(name="order",
//            nullable = false,
//            foreignKey = @ForeignKey(
//                    name = "food_item_in_order_fk"
//            ))
//    Order order;


    public Double getfoodPrice() {
        return foodPrice;
    }

    public void setfoodPrice(Double price_food) {
        this.foodPrice = price_food;
    }

    public String getfoodName() {
        return foodName;
    }

    public void setfoodName(String foodName) {
        this.foodName = foodName;
    }
    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Order getOrder() {
        return orderDetails.stream().findFirst().get().getOrder();
    }

//    public void setOrder(Order order) {
//        this.orderDetails.fin = order.getId().;
//    }
}