package com.adonev.waurma.crm.data.generator;

import com.adonev.waurma.crm.data.entity.Customer;
import com.adonev.waurma.crm.data.entity.FoodItem;
import com.adonev.waurma.crm.data.entity.Order;
import com.adonev.waurma.crm.data.repository.CustomerRepository;
import com.adonev.waurma.crm.data.repository.FoodItemRepository;
import com.adonev.waurma.crm.data.repository.OrderDetailRepository;
import com.adonev.waurma.crm.data.repository.OrderRepository;
import com.vaadin.exampledata.DataType;
import com.vaadin.exampledata.ExampleDataGenerator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import java.time.LocalDateTime;
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
            ExampleDataGenerator<Customer> customersGenerator = new ExampleDataGenerator<>(Customer.class,
                    LocalDateTime.now());
            customersGenerator.setData(Customer::setName, DataType.FULL_NAME);
            customersGenerator.setData(Customer::setEmail, DataType.EMAIL);
            customersGenerator.setData(Customer::setCustomerId,DataType.ID);
            List<Customer> customers = customerRepository.saveAll(customersGenerator.create(5, seed));

//            List<FoodItem> foodItems = foodItemRepository
//                    .saveAll(Stream.of("CheeseWaurma", "ChickenWaurma", "PorkWaurma", "Tea", "Coffee")
//                            .map(FoodItem::new).collect(Collectors.toList()));
            ExampleDataGenerator<FoodItem> foodItemGenerator = new ExampleDataGenerator<>(FoodItem.class,
                    LocalDateTime.now());
            foodItemGenerator.setData(FoodItem::setFoodName, DataType.FOOD_PRODUCT_NAME);
            foodItemGenerator.setData(FoodItem::setFoodPrice, DataType.PRICE);
            foodItemGenerator.setData(FoodItem::setFoodItemId,DataType.ID);
            List<FoodItem> foodItems = foodItemRepository.saveAll(foodItemGenerator.create(5,seed));

            logger.info("... generating 50 order entities...");
            ExampleDataGenerator<Order> orderGenerator = new ExampleDataGenerator<>(Order.class,
                    LocalDateTime.now());
            orderGenerator.setData(Order::setOrderDate, DataType.DATETIME_LAST_7_DAYS);
            orderGenerator.setData(Order::setReady, DataType.BOOLEAN_10_90);
            orderGenerator.setData(Order::setOrderId,DataType.ID);

//            com.adonev.waurma.crm.data.entity.Customer customer = new Customer();
//            customerRepository.saveAll()
            orderGenerator.setData(Order::setCustomer,customers.get(0));
            List<Order> orderss = orderRepository.saveAll(orderGenerator.create(5,seed));
//            orderGenerator.setData(Order::setOrderDetails, new OrderDetail());
//            orderGenerator.setData(Order::setFoodItemsInOrder, DataType.EMAIL);

            logger.info("Generated demo data");
        };
    }

}
