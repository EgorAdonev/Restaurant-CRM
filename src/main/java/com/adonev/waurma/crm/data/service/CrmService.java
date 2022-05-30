package com.adonev.waurma.crm.data.service;

import com.adonev.waurma.crm.data.repository.CustomerRepository;
import com.adonev.waurma.crm.data.repository.FoodItemRepository;
import com.adonev.waurma.crm.data.entity.Customer;
import com.adonev.waurma.crm.data.entity.FoodItem;
import com.adonev.waurma.crm.data.entity.Order;
import com.adonev.waurma.crm.data.entity.OrderDetail;
import com.adonev.waurma.crm.data.repository.OrderDetailRepository;
import com.adonev.waurma.crm.data.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final FoodItemRepository foodItemRepository;
    private final OrderDetailRepository orderDetailRepository;

    public CrmService(OrderRepository orderRepository,
                      CustomerRepository customerRepository,
                      FoodItemRepository foodItemRepository,
                      OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.foodItemRepository = foodItemRepository;
        this.orderDetailRepository = orderDetailRepository;

    }

    public List<Order> findAllOrders(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return orderRepository.findAll();
        } else {
            return orderRepository.search(stringFilter);
        }
    }

    public long countOrders() {
        return orderRepository.count();
    }

    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    public void saveOrder(Order order) {
        if (order == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        orderRepository.save(order);
    }
    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }
    public List<FoodItem> findAllFoodItems(){
        return foodItemRepository.findAll();
    }
    public List<Order> findAllOrders(){
        return orderRepository.findAll();
    }
    public List<OrderDetail> findAllOrderDetails() { return orderDetailRepository.findAll(); }
}
