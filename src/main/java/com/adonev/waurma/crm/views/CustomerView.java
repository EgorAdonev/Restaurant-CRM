package com.adonev.waurma.crm.views;

import com.adonev.waurma.crm.data.entity.Customer;
import com.adonev.waurma.crm.data.service.CrmService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "customers")
@PageTitle("Customers")
public class CustomerView extends VerticalLayout {
    private Grid<Customer> grid1 = new Grid<>(Customer.class);
    TextField filterTextCustomer = new TextField();
    CustomerForm customersForm;
    CrmService service;

    public CustomerView(CrmService service) {
        this.service = service;
        addClassName("customer-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateCustomerList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid1, customersForm);
        content.setFlexGrow(2, grid1);
        content.setFlexGrow(1, customersForm);
        content.addClassNames("content-customer-view");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {

        customersForm = new CustomerForm(service.findAllFoodItems(), service.findAllCustomers());
        customersForm.setWidth("25em");
        customersForm.addListener(CustomerForm.SaveEvent.class, this::saveCustomer);
        customersForm.addListener(CustomerForm.DeleteEvent.class, this::deleteCustomer);
        customersForm.addListener(CustomerForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {

        grid1.addClassName("customer-grid");
        grid1.setSizeFull();
        grid1.setColumns(
                "customerId",
                "name", "email");
        grid1.addColumn(customer -> customer.getName()).setHeader("Customer's info");
        grid1.addColumn(customer -> customer.getCustomerId()).setHeader("Customer ID");
        grid1.addColumn(customer -> customer.getEmail()).setHeader("Email");
        grid1.getColumns().forEach(col -> col.setAutoWidth(true));

        grid1.asSingleSelect().addValueChangeListener(event ->
                editCustomer(event.getValue()));

    }

    private HorizontalLayout getToolbar() {
        filterTextCustomer.setPlaceholder("Filter...");
        filterTextCustomer.setClearButtonVisible(true);
        filterTextCustomer.setValueChangeMode(ValueChangeMode.LAZY);
        filterTextCustomer.addValueChangeListener(e -> updateCustomerList());

        Button addCustomerButton = new Button("Add customers");
        addCustomerButton.addClickListener(click -> addCustomer());

        HorizontalLayout toolbar = new HorizontalLayout(filterTextCustomer, addCustomerButton);
        toolbar.addClassName("customer-toolbar");
        return toolbar;
    }
    private void closeEditor() {
        customersForm.setCustomer(null);
        customersForm.setVisible(false);
        removeClassName("editing");
    }
    public void editCustomer(Customer customer) {
        if (customer == null) {
            closeEditor();
        } else {
            customersForm.setCustomer(customer);
            customersForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void addCustomer() {
        grid1.asSingleSelect().clear();
        editCustomer(new Customer());
    }
    private void saveCustomer(CustomerForm.SaveEvent event) {
        service.saveCustomer(event.getCustomer());
        updateCustomerList();
        closeEditor();
    }

    private void deleteCustomer(CustomerForm.DeleteEvent event) {
        service.deleteCustomer(event.getCustomer());
        updateCustomerList();
        closeEditor();
    }

    private void updateCustomerList() {
        grid1.setItems(service.findAllCustomers(filterTextCustomer.getValue()));
    }
}