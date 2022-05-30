package com.adonev.waurma.crm.views;

import com.adonev.waurma.crm.data.entity.FoodItem;
import com.adonev.waurma.crm.data.entity.OrderDetail;
import com.adonev.waurma.crm.data.entity.Customer;
import com.adonev.waurma.crm.data.entity.Order;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class OrderForm extends FormLayout {
    private Order order;
    TextField firstName = new TextField("Name");
    TextField orders = new TextField("Orders");
    EmailField email = new EmailField("Email");
    ComboBox<FoodItem> foodItemComboBox = new ComboBox<>("Food Items");
    ComboBox<Customer> customerComboBox = new ComboBox<>("Company");
    ComboBox<OrderDetail> ordersComboBox = new ComboBox<>("Order's Detail");
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    Binder<Order> binder = new BeanValidationBinder<>(Order.class);
    public OrderForm(List<Customer> customers, List<FoodItem> foodItems, List<OrderDetail> customersOrders){
        addClassName("contact-form");
        binder.bindInstanceFields(this);

        customerComboBox.setItems(customers);
        customerComboBox.setItemLabelGenerator(Customer::getName);
        foodItemComboBox.setItems(foodItems);
        foodItemComboBox.setItemLabelGenerator(FoodItem::getfoodName);
        ordersComboBox.setItems(customersOrders);
        ordersComboBox.setItemLabelGenerator(OrderDetail::toString);

        add(firstName,
                orders,
                email,
                customerComboBox,
                foodItemComboBox,
                ordersComboBox,
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
        private Order order;
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
