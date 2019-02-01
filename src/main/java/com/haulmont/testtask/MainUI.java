package com.haulmont.testtask;

import com.haulmont.testtask.data.entities.ClientEntity;
import com.haulmont.testtask.data.services.ClientService;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.view.ClientComponent;
import com.haulmont.testtask.view.MechanicComponent;
import com.haulmont.testtask.view.OrderComponent;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.List;

@Theme("valo")
public class MainUI extends UI {


    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        //layout.setSizeFull();
        layout.setMargin(true);

        Button buttonClient = new Button("Клиенты");
        Button buttonMechanic = new Button("Механики");
        Button buttonOrder = new Button("Заказы");

        HorizontalLayout horizontalButtonLayot = new HorizontalLayout();
        horizontalButtonLayot.setSizeFull();
        horizontalButtonLayot.addComponent(buttonClient);
        horizontalButtonLayot.addComponent(buttonMechanic);
        horizontalButtonLayot.addComponent(buttonOrder);
        horizontalButtonLayot.setSizeFull();
        layout.addComponent(horizontalButtonLayot);
        layout.addComponent(new ClientComponent(this));
        buttonClient.addClickListener((Button.ClickListener) event -> {
            client(layout);
        });
        buttonMechanic.addClickListener((Button.ClickListener) event -> {
            mechanic(layout);
        });
        buttonOrder.addClickListener((Button.ClickListener) event -> {
            order(layout);
        });

        setContent(layout);
    }

    private void client(VerticalLayout layout)
    {
        layout.removeComponent(layout.getComponent(layout.getComponentCount() - 1));
        layout.addComponent(new ClientComponent(this));
    }

    private void mechanic(VerticalLayout layout)
    {
        layout.removeComponent(layout.getComponent(layout.getComponentCount() - 1));
        layout.addComponent(new MechanicComponent(this));
    }

    private void order(VerticalLayout layout)
    {
        layout.removeComponent(layout.getComponent(layout.getComponentCount() - 1));
        layout.addComponent(new OrderComponent(this));
    }
}