package com.adonev.waurma.crm.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    public Order() {
    }

    public Order(LocalDateTime date, Boolean ready, Customer customer, List<OrderDetail> orderDetails) {
        this.date = String.valueOf(date);
        this.ready = ready;
        this.customer = customer;
        this.orderDetails = orderDetails;
    }

    @Id
//    @NotNull
//    @SequenceGenerator(name = "order_id_sequence",sequenceName = "order_id_sequence",allocationSize = 1)
//    @GeneratedValue(strategy = SEQUENCE,generator = "order_id_sequence")
    @Column(name = "order_id")
    private String orderId;

    @NotNull
    @Column(name = "order_date")
//            ,columnDefinition = "DATE")
    private String date;

    @NotNull
    @Column(name = "ready",columnDefinition = "bool")
    private Boolean ready;

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "customer_orders_fk"
            )
    )
    Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = String.valueOf(orderId);
    }
//    @OneToMany(
//            mappedBy = "order",
//            orphanRemoval = true,
//            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
//            fetch = FetchType.LAZY
//    )
//    List<FoodItem> foodItemsInOrder = new ArrayList<>();

    @OneToMany(mappedBy = "order",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.EAGER
    )
    List<OrderDetail> orderDetails = new ArrayList<>();

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", date=" + date +
                ", ready=" + ready +
                ", customer=" + customer +
                ", orderDetails=" + orderDetails +
                '}';
    }

    public @NotNull String getOrderDate() {
        return date;
    }
    public void setOrderDate(LocalDateTime date) {
        this.date = String.valueOf(date);
    }

    public Boolean isReady() {
        return ready;
    }

    public void setReady(Boolean ready) {
        this.ready = ready;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

//    public Customer getCompany() {
//        return customer;
//    }
//
//    public void setCompany(Customer customer) {
//        this.customer = customer;
//    }
//
//    public Status getStatus() {
//        return status;
//    }
//
//    public void setStatus(Status status) {
//        this.status = status;
//    }

}
