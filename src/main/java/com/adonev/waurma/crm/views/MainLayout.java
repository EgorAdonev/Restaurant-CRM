package com.adonev.waurma.crm.views;


import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

import javax.annotation.security.PermitAll;

public class MainLayout extends AppLayout {
//    private final SecurityService securityService;
    public MainLayout() {
//        securityService = security;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Restaurant Управление");
        H2 logotip = new H2("by EgorAdonev");
        logo.addClassNames("text-l", "m-m");
//        Button logout = new Button("Log out", e -> securityService.logout());

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo,logotip);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Orders List", OrderView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink customersListLink = new RouterLink("Customers List", CustomerView.class);
        customersListLink.setHighlightCondition(HighlightConditions.sameLocation());
        RouterLink menuListLink = new RouterLink("Menu", FoodItemView.class);
        customersListLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                listLink,
                customersListLink,
                menuListLink
        ));

    }
}