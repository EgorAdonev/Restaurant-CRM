package com.adonev.waurma.crm.views;

import com.adonev.waurma.crm.data.service.CrmService;
import com.adonev.waurma.crm.data.entity.Order;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value = "",layout = MainLayout.class)
@PageTitle("Orders")
public class OrderView extends VerticalLayout {
    private  Grid<Order> grid = new Grid<>(Order.class);
    TextField filterText = new TextField();
    OrderForm form;
    CrmService service;

    public OrderView(CrmService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(1, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new OrderForm(
//                service.findAllCustomers(),
                service.findAllOrderDetails());
        form.setWidth("25em");
        form.addListener(OrderForm.SaveEvent.class, this::saveOrder);
        form.addListener(OrderForm.DeleteEvent.class, this::deleteOrder);
        form.addListener(OrderForm.CloseEvent.class, e -> closeEditor());

    }

    private void configureGrid() {
        grid.addClassName("order-grid");
        grid.setSizeFull();
//        grid.setColumns("date");
//        grid.addColumn(order -> order.getOrderDate()).setHeader("Order's info");
//        grid.addColumn(order -> order.getOrderDetails().stream().map(OrderDetail::toString)).setHeader("Order Details");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateOrderList());

        Button addOrderButton = new Button("Add order");
        addOrderButton.addClickListener(click -> addOrder());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addOrderButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }
    private void closeEditor() {
        form.setOrder(null);
        form.setVisible(false);
        removeClassName("editing-o");
    }
    public void editOrder(Order order) {
        if (order == null) {
            closeEditor();
        } else {
            form.setOrder(order);
            form.setVisible(true);
            addClassName("editing-o");
        }
    }

    private void addOrder() {
        grid.asSingleSelect().clear();
        editOrder(new Order());
    }
    private void saveOrder(OrderForm.SaveEvent event) {
        service.saveOrder(event.getOrder());
        updateOrderList();
        closeEditor();
    }

    private void deleteOrder(OrderForm.DeleteEvent event) {
        service.deleteOrder(event.getOrder());
        updateOrderList();
        closeEditor();
    }
    private void updateOrderList() {
        grid.setItems(service.findAllOrders(filterText.getValue()));
    }

}