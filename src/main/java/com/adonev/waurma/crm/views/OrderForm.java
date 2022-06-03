package com.adonev.waurma.crm.views;

import com.adonev.waurma.crm.data.entity.FoodItem;
import com.adonev.waurma.crm.data.entity.OrderDetail;
import com.adonev.waurma.crm.data.entity.Customer;
import com.adonev.waurma.crm.data.entity.Order;
import com.adonev.waurma.crm.data.repository.OrderRepository;
import com.adonev.waurma.crm.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.converter.*;
import com.vaadin.flow.data.validator.DateTimeRangeValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

public class OrderForm extends FormLayout {
    private Order order;
    TextField ready = new TextField("Ready");
    DateTimePicker date = new DateTimePicker("Order Date");
    TextArea orderDetails = new TextArea("Details","Comment");

    TextField name = new TextField("Name","Your name");

//    ComboBox<FoodItem> foodItemComboBox = new ComboBox<>("Food Items");
    ComboBox<OrderDetail> ordersComboBox = new ComboBox<>("Order's Detail");
//    ComboBox<Customer> customerComboBox = new ComboBox<>("Customers");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    CrmService service;
    Binder<Order> binder = new BeanValidationBinder<>(Order.class);
    Binder<Customer> customerBinder = new BeanValidationBinder<>(Customer.class);
    public OrderForm(List<OrderDetail> customersOrders){
        addClassName("order-form");
//        binder.forField(orderId)
//                .withConverter(new StringToIntegerConverter("Must be integer!"))
//                .withNullRepresentation(Integer.valueOf("0"))
//                .bind(Order::getOrderId, Order::setOrderId);
        customerBinder.forField(name)
                .bind(Customer::getName, Customer::setName);

        binder.forField(date)
                .withValidator(
                        new DateTimeRangeValidator("Time machine is broken :( Cannot place the order in past",
                         LocalDateTime.now(), LocalDateTime.of(2100, Month.JANUARY,1,0,0)))
                .bind(Order::getOrderDate, Order::setOrderDate);
        binder.forField(orderDetails)
                .bind(Order::getOrderDetails,Order::setOrderDetails);
        binder.forField(ready)
                .withConverter(new StringToBooleanConverter("Must be Boolean!"))
                .withNullRepresentation(Boolean.FALSE)
                .bind(Order::isReady,Order::setReady);


//        ordersComboBox.setItems(customersOrders);
//        ordersComboBox.setItemLabelGenerator(OrderDetail::toString);

        add(
                ordersComboBox,
                ready,
                date,
                orderDetails,
                createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(e -> validateAndSave());
        delete.addClickListener(e -> fireEvent(new DeleteEvent(this, order)));
        close.addClickListener(e -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }
    private void validateAndSave(){
        try {
            binder.writeBean(order);
            fireEvent(new SaveEvent(this, order));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
    public void setOrder(Order order){
        this.order = order;
        binder.readBean(order);
    }


    public static abstract class OrderFormEvent extends ComponentEvent<OrderForm> {
        private final Order order;
        protected OrderFormEvent(OrderForm source, Order order) {
            super(source, false);
            this.order = order;
        }

        public Order getOrder() {
            return order;
        }
    }
    public static class SaveEvent extends OrderFormEvent {
        SaveEvent(OrderForm source, Order order) {
            super(source, order);
        }
    }

    public static class DeleteEvent extends OrderFormEvent {
        DeleteEvent(OrderForm source, Order order) {
            super(source, order);
        }

    }

    public static class CloseEvent extends OrderFormEvent {
        CloseEvent(OrderForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
