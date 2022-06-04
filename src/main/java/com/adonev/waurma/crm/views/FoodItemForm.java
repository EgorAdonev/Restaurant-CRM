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
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class FoodItemForm extends VerticalLayout {
    private FoodItem foodItem;
    TextField name = new TextField("Name");
    TextField foodItemId = new TextField("Food ID");
    TextField foodName = new TextField("Food Name");
    TextField foodPrice = new TextField("Price");

    ComboBox<FoodItem> foodItemComboBox = new ComboBox<>("Menu");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    CrmService service;
    Binder<Customer> customerBinder = new BeanValidationBinder<>(Customer.class);
    Binder<FoodItem> foodItemBinder = new BeanValidationBinder<>(FoodItem.class);

    public FoodItemForm(List<FoodItem> foodItems, List<Customer> customers){
        addClassName("customer-form");
//        customerBinder.bind(customerOrders,"customerOrders");
//        customerBinder.forField(customerOrders)
//                .bind(Customer::getCustomerOrders,Customer::setCustomerOrders);

//        customerBinder.forField(customerId)
//                .withConverter(new StringToIntegerConverter("Must be integer!"))
//                  .withNullRepresentation(Integer.valueOf("0"))
//                .bind(Customer::getCustomerId, Customer::setCustomerId);

//        customerBinder.forField(email)
//                .withValidator(new EmailValidator("Not a Correct Email!"))
//                .bind(Customer::getEmail, Customer::setEmail);
        customerBinder.forField(name)
                .bind(Customer::getName, Customer::setName);

        foodItemBinder.forField(foodItemId)
                .withConverter(new StringToIntegerConverter("Must be integer!"))
                .withNullRepresentation(Integer.valueOf("0"))
                .bindReadOnly(FoodItem::getFoodItemId);
        foodItemBinder.forField(foodPrice)
                .withConverter(new StringToDoubleConverter("Must be double!"))
                .withNullRepresentation(Double.valueOf("0.01"))
                .bind(FoodItem::getfoodPrice, FoodItem::setFoodPrice);
        foodItemBinder.forField(foodName).bind(FoodItem::getFoodName, FoodItem::setFoodName);

//        customerBinder.bindInstanceFields(this);
//        foodItemBinder.bindInstanceFields(this);
        foodItemComboBox.setReadOnly(true);
        foodItemComboBox.setItems(foodItems);
        foodItemComboBox.setItemLabelGenerator(FoodItem::getFoodName);

        add(
                name,
                foodItemComboBox,
                foodName,
                foodPrice,
                createButtonsLayout());
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(e -> validateAndSave());
        delete.addClickListener(e -> fireEvent(new FoodItemForm.DeleteEvent(this, foodItem)));
        close.addClickListener(e -> fireEvent(new FoodItemForm.CloseEvent(this)));

        foodItemBinder.addStatusChangeListener(e -> save.setEnabled(foodItemBinder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }
    private void validateAndSave(){
        try {
            foodItemBinder.writeBean(foodItem);
            fireEvent(new FoodItemForm.SaveEvent(this, foodItem));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }
    public void setFoodItem(FoodItem foodItem){
        this.foodItem = foodItem;
        foodItemBinder.readBean(foodItem);
    }


    public static abstract class FoodItemsFormEvent extends ComponentEvent<FoodItemForm> {
        private final FoodItem foodItem;
        protected FoodItemsFormEvent(FoodItemForm source, FoodItem foodItem) {
            super(source, false);
            this.foodItem = foodItem;
        }

        public FoodItem getFoodItem() {
            return foodItem;
        }
    }
    public static class SaveEvent extends FoodItemForm.FoodItemsFormEvent {
        SaveEvent(FoodItemForm source, FoodItem foodItem) {
            super(source, foodItem);
        }
    }

    public static class DeleteEvent extends FoodItemForm.FoodItemsFormEvent {
        DeleteEvent(FoodItemForm source, FoodItem foodItem) {
            super(source, foodItem);
        }
    }

    public static class CloseEvent extends FoodItemForm.FoodItemsFormEvent {
        CloseEvent(FoodItemForm source) {
            super(source,null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
