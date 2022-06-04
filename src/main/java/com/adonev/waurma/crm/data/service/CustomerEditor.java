//package com.adonev.waurma.crm.data.service;
//
//
//import com.adonev.waurma.crm.data.entity.Customer;
//import com.adonev.waurma.crm.data.repository.CustomerRepository;
//import com.vaadin.flow.component.Key;
//import com.vaadin.flow.component.KeyNotifier;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.textfield.TextField;
//import com.vaadin.flow.data.binder.Binder;
//import com.vaadin.flow.spring.annotation.SpringComponent;
//import com.vaadin.flow.spring.annotation.UIScope;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static com.vaadin.flow.component.icon.VaadinIcon.TRASH;
//
//@SpringComponent
//@UIScope
//public class CustomerEditor extends VerticalLayout implements KeyNotifier {
//
//	private final CustomerRepository repository;
//
//	/**
//	 * The currently edited customer
//	 */
//	private Customer customer;
//
//	/* Fields to edit properties in Customer entity */
//	TextField name = new TextField("First name");
//	TextField email = new TextField("Last name");
//
//	/* Action buttons */
//	// TODO why more code?
//	Button save = new Button("Save");
//	Button cancel = new Button("Cancel");
//	Button delete = new Button("Delete", TRASH.create());
//	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
//
//	Binder<Customer> binder = new Binder<>(Customer.class);
//	private ChangeHandler changeHandler;
//
//	@Autowired
//	public CustomerEditor(CustomerRepository repository) {
//		this.repository = repository;
//
//		add(name, email, actions);
//
//		// bind using naming convention
//		binder.bindInstanceFields(this);
//
//		// Configure and style components
//		setSpacing(true);
//
//		save.getElement().getThemeList().add("primary");
//		delete.getElement().getThemeList().add("error");
//
//		addKeyPressListener(Key.ENTER, e -> save());
//
//		// wire action buttons to save, delete and reset
//		save.addClickListener(e -> save());
//		delete.addClickListener(e -> delete());
//		cancel.addClickListener(e -> editFoodItem(customer));
//		setVisible(false);
//	}
//
//	void delete() {
//		repository.delete(customer);
//		changeHandler.onChange();
//	}
//
//	void save() {
//		repository.save(customer);
//		changeHandler.onChange();
//	}
//
//	public interface ChangeHandler {
//		void onChange();
//	}
//
//	public final void editFoodItem(Customer c) {
//		if (c == null) {
//			setVisible(false);
//			return;
//		}
//		final boolean persisted = c.getCustomerId() != null;
//		if (persisted) {
//			// Find fresh entity for editing
//			customer = repository.findById(Long.valueOf(c.getCustomerId())).get();
//		}
//		else {
//			customer = c;
//		}
//		cancel.setVisible(persisted);
//
//		// Bind customer properties to similarly named fields
//		// Could also use annotation or "manual binding" or programmatically
//		// moving values from fields to entities before saving
//		binder.setBean(customer);
//
//		setVisible(true);
//
//		// Focus first name initially
//		name.focus();
//	}
//
//	public void setChangeHandler(ChangeHandler h) {
//		// ChangeHandler is notified when either save or delete
//		// is clicked
//		changeHandler = h;
//	}
//
//}