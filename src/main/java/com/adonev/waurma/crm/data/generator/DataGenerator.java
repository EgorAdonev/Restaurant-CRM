package com.adonev.waurma.crm.data.generator;

import com.adonev.waurma.crm.data.entity.Customer;
import com.adonev.waurma.crm.data.entity.FoodItem;
import com.adonev.waurma.crm.data.entity.Order;
import com.adonev.waurma.crm.data.entity.OrderDetail;
import com.adonev.waurma.crm.data.repository.CustomerRepository;
import com.adonev.waurma.crm.data.repository.FoodItemRepository;
import com.adonev.waurma.crm.data.repository.OrderDetailRepository;
import com.adonev.waurma.crm.data.entity.*;
import com.adonev.waurma.crm.data.repository.OrderRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(OrderRepository orderRepository, CustomerRepository customerRepository,
                                      FoodItemRepository foodItemRepository, OrderDetailRepository orderDetailRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            if (orderRepository.count() != 0L) {
                logger.info("Using existing database");
                return;
            }
            int seed = 123;

            logger.info("Generating demo data");
            ExampleDataGenerator<Customer> companyGenerator = new ExampleDataGenerator<>(Customer.class,
                    LocalDateTime.now());
            companyGenerator.setData(Customer::setName, DataType.FULL_NAME);
            companyGenerator.setData(Customer::setEmail, DataType.EMAIL);
            List<Customer> companies = customerRepository.saveAll(companyGenerator.create(5, seed));

//            List<FoodItem> statuses = foodItemRepository
//                    .saveAll(Stream.of("Imported lead", "Not contacted", "Contacted", "Customer", "Closed (lost)")
//                            .map(Status::new).collect(Collectors.toList()));

            logger.info("... generating 50 order entities...");
            ExampleDataGenerator<Order> orderGenerator = new ExampleDataGenerator<>(Order.class,
                    LocalDateTime.now());
            orderGenerator.setData(Order::setOrderDate, DataType.DATETIME_LAST_7_DAYS);
//            orderGenerator.setData(Order::setOrderDetails, new OrderDetail());
//            orderGenerator.setData(Order::setFoodItemsInOrder, DataType.EMAIL);

            List<OrderDetail> orderDetailsTest = new ArrayList<>();
            FoodItem dish = new FoodItem("Chicken Wings", 31.23, orderDetailsTest);

            foodItemRepository.save(dish);
            Order order = new Order(LocalDateTime.now(), false, customerRepository.findById(1L).orElseThrow(), orderDetailsTest);
            orderRepository.save(order);
            orderDetailsTest.add(new OrderDetail(order, dish
                    , 2, 62.46));
            orderDetailRepository.save(orderDetailsTest.get(0));
//            Random r = new Random(seed);
//            List<Order> orders = orderGenerator.create(50, seed).stream().map(oneorder -> {
//                oneorder.setOrderDate(LocalDateTime.now());
//                oneorder.setReady(true);
//                List<OrderDetail> orderDetailsTest1 = new ArrayList<>();
//                OrderDetail od = new OrderDetail();
//                od.setQuantity(3);
//                od.setTotalPrice(33.09);
//                orderDetailsTest1.add(od);
//                FoodItem dish1 = new FoodItem("Milk Shake", 11.03, orderDetailsTest1);
//                od.setFoodItem(dish1);
//                oneorder.setOrderDetails(orderDetailsTest1);
//                return oneorder;
//            }).collect(Collectors.toList());
//
//            orderRepository.saveAll(orders);

            logger.info("Generated demo data");
        };
    }

}
