package com.adonev.waurma.crm.data.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "customer")
public class Customer {
    public Customer() {
    }

    public Customer(String name, String email, List<Order> customerOrders) {
        this.name = name;
        this.email = email;
        this.customerOrders = customerOrders;
    }

    @Id
    @NotNull
    @SequenceGenerator(name = "customer_id_sequence", sequenceName = "customer_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "customer_id_sequence")
    @Column(name = "customer_id")
    private Long customerId;

    @NotBlank
    @Column(name = "name",columnDefinition = "VARCHAR")
    private String name;

    @NotBlank
    @Column(name = "email",columnDefinition = "VARCHAR")
    private String email;
    @OneToMany(
            mappedBy = "customer",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    List<Order> customerOrders = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getEmployees() {
        return customerOrders;
    }

    public void setEmployees(List<Order> orders) {
        this.customerOrders = orders;
    }

}
