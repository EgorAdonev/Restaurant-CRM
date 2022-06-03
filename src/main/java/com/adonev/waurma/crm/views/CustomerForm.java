package com.adonev.waurma.crm.views;

import com.adonev.waurma.crm.data.entity.Customer;
import com.adonev.waurma.crm.data.entity.FoodItem;
import com.adonev.waurma.crm.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

public class CustomerForm extends FormLayout {
    private Customer customer;
    TextField customerId = new TextField("Customer ID");
    TextField name = new TextField("Name");
    TextArea email = new TextArea("Email");
    TextField foodItemId = new TextField("Food ID");
    TextField foodName = new TextField("Food Name");
    TextField foodPrice = new TextField("Price");
    TextArea customerOrders = new TextArea("Customer's Orders Details");
//    Panel infoPanel = new Panel();

    ComboBox<FoodItem> foodItemComboBox = new ComboBox<>("Food Items");
    ComboBox<Customer> customerComboBox = new ComboBox<>("Customers");
//
//    ComboBox<Order> ordersComboBox = new ComboBox<>("Orders");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    CrmService service;
    Binder<Customer> customerBinder = new BeanValidationBinder<>(Customer.class);
    Binder<FoodItem> foodItemBinder = new BeanValidationBinder<>(FoodItem.class);
    @Autowired
    public CustomerForm(List<FoodItem> foodItems, List<Customer> customersOrders){
        addClassName("customer-form");
        customerBinder.bind(customerOrders,
                customerOrdersData -> customerOrdersData.getCustomerOrders().toString(),
                (customerOrdersData, title) -> customerOrdersData.setCustomerOrders(customerOrdersData.getCustomerOrders())
        );
        customerBinder.forField(email)
                .bind(Customer::getEmail, Customer::setEmail);
        customerBinder.forField(name)
                .bind(Customer::getName, Customer::setName);
        customerBinder.forField(customerId)
                .withConverter(new StringToIntegerConverter("Must be integer!"))
                .bind(Customer::getCustomerId, Customer::setCustomerId);
        foodItemBinder.forField(foodItemId)
                .withConverter(new StringToIntegerConverter("Must be integer!"))
                .bind(FoodItem::getFoodItemId, FoodItem::setFoodItemId);
//        foodItemBinder.bind(FoodItem::getfoodName, FoodItem::setfoodName);

        customerBinder.bindInstanceFields(this);
        foodItemBinder.bindInstanceFields(this);

        foodItemComboBox.setItems(foodItems);
        foodItemComboBox.setItemLabelGenerator(FoodItem::getfoodName);
        customerComboBox.setItems(customersOrders);
        customerComboBox.setItemLabelGenerator(Customer::getName);

        add(foodItemComboBox,
                customerComboBox,
                customerId,
                name,
                email,
                createButtonsLayout());
    }
    public CustomerForm(List<Customer> customersOrders){

    }
    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(e -> validateAndSave());
        delete.addClickListener(e -> fireEvent(new CustomerForm.DeleteEvent(this, customer)));
        close.addClickListener(e -> fireEvent(new CustomerForm.CloseEvent(this)));

        customerBinder.addStatusChangeListener(e -> save.setEnabled(customerBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }
    private void validateAndSave(){
        try {
            customerBinder.writeBean(customer);
            fireEvent(new CustomerForm.SaveEvent(this, customer));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
    public void setCustomer(Customer customer){
        this.customer = customer;
        customerBinder.readBean(customer);
    }



    public static abstract class CustomerFormEvent extends ComponentEvent<CustomerForm> {
        private final Customer customer;
        protected CustomerFormEvent(CustomerForm source, Customer customer) {
            super(source, false);
            this.customer = customer;
        }

        public Customer getCustomer() {
            return customer;
        }
    }
    public static class SaveEvent extends CustomerForm.CustomerFormEvent {
        SaveEvent(CustomerForm source, Customer order) {
            super(source, order);
        }
    }

    public static class DeleteEvent extends CustomerForm.CustomerFormEvent {
        DeleteEvent(CustomerForm source, Customer order) {
            super(source, order);
        }
    }

    public static class CloseEvent extends CustomerForm.CustomerFormEvent {
        CloseEvent(CustomerForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
