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
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.shared.Registration;


import java.util.List;

public class CustomerForm extends FormLayout {
    private Customer customer;
//    TextField customerId = new TextField("Customer ID");
    TextField name = new TextField("Name");
    TextArea email = new TextArea("Email");
    TextField foodName = new TextField("Food Name");

//    TextArea customerOrders = new TextArea("Customer's Orders Details");
//    Panel infoPanel = new Panel();
    ComboBox<Customer> customerComboBox = new ComboBox<>("Previous Customers Names");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    CrmService service;
    Binder<Customer> customerBinder = new BeanValidationBinder<>(Customer.class);
    Binder<FoodItem> foodItemBinder = new BeanValidationBinder<>(FoodItem.class);

    public CustomerForm(List<FoodItem> foodItems, List<Customer> customers){
        addClassName("customer-form");
//        customerBinder.bind(customerOrders,"customerOrders");
//        customerBinder.forField(customerOrders)
//                .bind(Customer::getCustomerOrders,Customer::setCustomerOrders);

//        customerBinder.forField(customerId)
//                .withConverter(new StringToIntegerConverter("Must be integer!"))
//                  .withNullRepresentation(Integer.valueOf("0"))
//                .bind(Customer::getCustomerId, Customer::setCustomerId);

        customerBinder.forField(email)
                .withValidator(new EmailValidator("Not a Correct Email!"))
                .bind(Customer::getEmail, Customer::setEmail);
        customerBinder.forField(name)
                .bind(Customer::getName, Customer::setName);
        foodItemBinder.forField(foodName).bindReadOnly(FoodItem::getFoodName);

//        customerBinder.bindInstanceFields(this);
//        foodItemBinder.bindInstanceFields(this);
        customerComboBox.setItems(customers);
        customerComboBox.setItemLabelGenerator(Customer::getName);

        add(
                customerComboBox,
                name,
                email,
                createButtonsLayout());
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
