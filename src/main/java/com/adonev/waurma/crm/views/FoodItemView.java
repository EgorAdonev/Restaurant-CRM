package com.adonev.waurma.crm.views;

import com.adonev.waurma.crm.data.entity.FoodItem;
import com.adonev.waurma.crm.data.service.CrmService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "menu",layout = MainLayout.class)
@PageTitle("Menu")
public class FoodItemView extends VerticalLayout {
    private Grid<FoodItem> gridFoodItem = new Grid<>(FoodItem.class);
    TextField filterTextFoodItem = new TextField();
    FoodItemForm foodItemForm;
    CrmService service;

    public FoodItemView(CrmService service) {
        this.service = service;
        addClassName("food-item-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateMenuList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(gridFoodItem, foodItemForm);
        content.setFlexGrow(2, gridFoodItem);
        content.setFlexGrow(1, foodItemForm);
        content.addClassNames("content-food-item-view");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {

        foodItemForm = new FoodItemForm(service.findAllFoodItems(), service.findAllCustomers());
        foodItemForm.setWidth("25em");
        foodItemForm.addListener(FoodItemForm.SaveEvent.class, this::saveFoodItem);
        foodItemForm.addListener(FoodItemForm.DeleteEvent.class, this::deleteFoodItem);
        foodItemForm.addListener(FoodItemForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {

        gridFoodItem.addClassName("food-item-grid");
        gridFoodItem.setSizeFull();
        gridFoodItem.setColumns(
                "foodItemId",
                "foodName");
//                , "foodPrice");
//        gridFoodItem.addColumn(menuItem -> menuItem.getfoodPrice()).setHeader("Price");

        gridFoodItem.addColumn(foodItem -> {
            String ordDet = foodItem.getfoodPrice();
            return ordDet==null ? 0.0 : foodItem.getfoodPrice();
        }).setHeader("Price");
        gridFoodItem.getColumns().forEach(col -> col.setAutoWidth(true));

        gridFoodItem.asSingleSelect().addValueChangeListener(event ->
                editFoodItem(event.getValue()));

    }

    private HorizontalLayout getToolbar() {
        filterTextFoodItem.setPlaceholder("Filter...");
        filterTextFoodItem.setClearButtonVisible(true);
        filterTextFoodItem.setValueChangeMode(ValueChangeMode.LAZY);
        filterTextFoodItem.addValueChangeListener(e -> updateMenuList());

        Button addCustomerButton = new Button("Add to menu");
        addCustomerButton.addClickListener(click -> addFoodItem());

        HorizontalLayout toolbar = new HorizontalLayout(filterTextFoodItem, addCustomerButton);
        toolbar.addClassName("food-item-toolbar");
        return toolbar;
    }
    private void closeEditor() {
        foodItemForm.setFoodItem(null);
        foodItemForm.setVisible(false);
        removeClassName("editing");
    }
    public void editFoodItem(FoodItem foodItem) {
        if (foodItem == null) {
            closeEditor();
        } else {
            foodItemForm.setFoodItem(foodItem);
            foodItemForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void addFoodItem() {
        gridFoodItem.asSingleSelect().clear();
        editFoodItem(new FoodItem());
    }
    private void saveFoodItem(FoodItemForm.SaveEvent event) {
        service.saveFoodItem(event.getFoodItem());
        updateMenuList();
        closeEditor();
    }

    private void deleteFoodItem(FoodItemForm.DeleteEvent event) {
        service.deleteFoodItem(event.getFoodItem());
        updateMenuList();
        closeEditor();
    }


    private void updateMenuList() {
        gridFoodItem.setItems(service.findAllFoodItems(filterTextFoodItem.getValue()));
    }
}
