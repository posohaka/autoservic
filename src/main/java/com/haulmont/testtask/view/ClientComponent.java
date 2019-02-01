package com.haulmont.testtask.view;

import com.haulmont.testtask.data.entities.ClientEntity;
import com.haulmont.testtask.data.services.ClientService;
import com.haulmont.testtask.model.Client;
//import com.vaadin.data.Item;
import com.vaadin.server.ErrorMessage;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.components.grid.SingleSelectionModel;

import java.util.List;
import java.util.Set;

public class ClientComponent extends CustomComponent {

    //private Table table;
    private Grid<ClientEntity> grid = new Grid<>();
    public ClientComponent(UI mainui)
    {
        Panel panel = new Panel();
        VerticalLayout layout = new VerticalLayout();
        panel.setContent(layout);
        HorizontalLayout layotaddButton = new HorizontalLayout();
        Button buttonaddClient = new Button("Добавить клиента");
        Button buttonremoveClient = new Button("Удалить клиента");
        Button buttonchangeClient = new Button("Редактировать");
        layotaddButton.addComponent(buttonaddClient);
        layotaddButton.addComponent(buttonremoveClient);
        layotaddButton.addComponent(buttonchangeClient);
        //layotaddButton.setSizeFull();
        HorizontalLayout horizontalLayoutTable = new HorizontalLayout();
        grid.addColumn(ClientEntity::getFirstname).setCaption("Имя");
        grid.addColumn(ClientEntity::getSecondname).setCaption("Фамилия");
        grid.addColumn(ClientEntity::getThirdname).setCaption("Очтечство");
        grid.addColumn(ClientEntity::getPhonenumber).setCaption("Номер телефона");
        update();
        horizontalLayoutTable.addComponent(grid);
        layout.addComponent(horizontalLayoutTable);
        layout.addComponent(layotaddButton);
        horizontalLayoutTable.setSizeFull();
        grid.setSizeFull();
        buttonaddClient.addClickListener((Button.ClickListener) event -> addbut(mainui));
        buttonremoveClient.addClickListener((Button.ClickListener) clickEvent -> {
            Set<ClientEntity> clientEntities = grid.getSelectedItems();
            for(ClientEntity clientEntity : clientEntities)
            {
                Client client = new Client(clientEntity);
                deletebut(client, mainui);
            }
        });
        buttonchangeClient.addClickListener((Button.ClickListener) clickEvent -> {
            Set<ClientEntity> clientEntities = grid.getSelectedItems();
            for(ClientEntity clientEntity : clientEntities)
            {
                Client client = new Client(clientEntity);
                changebut(client, mainui);
            }
        });
        setCompositionRoot(panel);
        panel.setSizeFull();
        layout.setSizeFull();

        setSizeFull();
    }

    private void update()
    {
        List<ClientEntity> clientEntities = ClientService.allclient();
        grid.setItems(clientEntities);
    }

    private void deletebut(Client client, UI mainui)
    {
        try {
            ClientService.removeClient(client.getId());
            update();
        }
        catch (Exception e)
        {
            Window subWindow = new Window("Ошибка");
            VerticalLayout verticalLayout = new VerticalLayout();
            subWindow.setContent(verticalLayout);
            verticalLayout.addComponent(new Label("Этого клиента нельзя удалить. Видимо у него заказ."));
            subWindow.center();
            mainui.addWindow(subWindow);
        }
    }

    private void changebut(Client client, UI mainui)
    {
        Window subWindow = new Window("Редактировать");
        Label labelFN = new Label("Имя");
        Label labelSN = new Label("Фамилия");
        Label labelTN = new Label("Очтечство");
        Label labelPhN = new Label("Номер телефона");
        TextField first = new TextField();
        TextField second = new TextField();
        TextField third = new TextField();
        TextField phone = new TextField();
        first.setValue(client.getFirstname());
        second.setValue(client.getSecondname());
        third.setValue(client.getThirdname());
        phone.setValue(client.getPhonenumber());
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout subContent = new HorizontalLayout();
        subWindow.setContent(verticalLayout);
        VerticalLayout vF = new VerticalLayout();
        VerticalLayout vS = new VerticalLayout();
        VerticalLayout vT = new VerticalLayout();
        VerticalLayout vP = new VerticalLayout();
        vF.addComponent(labelFN);
        vF.addComponent(first);
        vS.addComponent(labelSN);
        vS.addComponent(second);
        vT.addComponent(labelTN);
        vT.addComponent(third);
        vP.addComponent(labelPhN);
        vP.addComponent(phone);
        subContent.addComponent(vF);
        subContent.addComponent(vS);
        subContent.addComponent(vT);
        subContent.addComponent(vP);
        verticalLayout.addComponent(subContent);
        HorizontalLayout butHorizont = new HorizontalLayout();
        // Put some components in it
        //subContent.addComponent(table1);
        Button buttonok = new Button("Ок");
        Button buttonno = new Button("Отменить");
        buttonok.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean flag = true;
                if (first.getValue() == null || first.getValue().equals(""))
                {
                    flag = false;
                    first.setComponentError(new UserError("Заполните поле"));
                    first.getComponentError();
                }
                if (second.getValue() == null || second.getValue().equals(""))
                {
                    flag = false;
                    second.setComponentError(new UserError("Заполните поле"));
                    second.getComponentError();
                }
                if (third.getValue() == null || third.getValue().equals(""))
                {
                    flag = false;
                    third.setComponentError(new UserError("Заполните поле"));
                    third.getComponentError();
                }
                if (phone.getValue() == null || phone.getValue().equals(""))
                {
                    flag = false;
                    phone.setComponentError(new UserError("Заполните поле"));
                    phone.getComponentError();
                }
                else
                {
                    try {
                        int p = Integer.valueOf(phone.getValue());
                    }
                    catch (Exception e)
                    {
                        flag = false;
                        phone.setComponentError(new UserError("Заполните поле"));
                        phone.getComponentError();
                    }
                }
                if (flag) {
                    client.setFirstname(first.getValue());
                    client.setSecondname(second.getValue());
                    client.setThirdname(third.getValue());
                    client.setPhonenumber(phone.getValue());
                    ClientService.changeClient(client);
                    update();
                    subWindow.close();
                }
            }
        });
        buttonno.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                subWindow.close();
            }
        });
        butHorizont.addComponent(buttonok);
        butHorizont.addComponent(buttonno);
        verticalLayout.addComponent(butHorizont);

        // Center it in the browser window
        subWindow.center();

        // Open it in the UI
        mainui.addWindow(subWindow);
    }

    private void addbut(UI mainui)
    {
        Window subWindow = new Window("Добавить клиетна");
        Label labelFN = new Label("Имя");
        Label labelSN = new Label("Фамилия");
        Label labelTN = new Label("Очтечство");
        Label labelPhN = new Label("Номер телефона");
        TextField first = new TextField();
        TextField second = new TextField();
        TextField third = new TextField();
        TextField phone = new TextField();
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout subContent = new HorizontalLayout();
        subWindow.setContent(verticalLayout);
        VerticalLayout vF = new VerticalLayout();
        VerticalLayout vS = new VerticalLayout();
        VerticalLayout vT = new VerticalLayout();
        VerticalLayout vP = new VerticalLayout();
        vF.addComponent(labelFN);
        vF.addComponent(first);
        vS.addComponent(labelSN);
        vS.addComponent(second);
        vT.addComponent(labelTN);
        vT.addComponent(third);
        vP.addComponent(labelPhN);
        vP.addComponent(phone);
        subContent.addComponent(vF);
        subContent.addComponent(vS);
        subContent.addComponent(vT);
        subContent.addComponent(vP);
        verticalLayout.addComponent(subContent);
        HorizontalLayout butHorizont = new HorizontalLayout();
        // Put some components in it
        //subContent.addComponent(table1);
        Button buttonok = new Button("Добавить клиента");
        Button buttonno = new Button("Отменить");
        buttonok.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean flag = true;
                if (first.getValue() == null || first.getValue().equals(""))
                {
                    flag = false;
                    first.setComponentError(new UserError("Заполните поле"));
                    first.getComponentError();
                }
                if (second.getValue() == null || second.getValue().equals(""))
                {
                    flag = false;
                    second.setComponentError(new UserError("Заполните поле"));
                    second.getComponentError();
                }
                if (third.getValue() == null || third.getValue().equals(""))
                {
                    flag = false;
                    third.setComponentError(new UserError("Заполните поле"));
                    third.getComponentError();
                }
                if (phone.getValue() == null || phone.getValue().equals(""))
                {
                    flag = false;
                    phone.setComponentError(new UserError("Заполните поле"));
                    phone.getComponentError();
                }
                else
                {
                    try {
                        int p = Integer.valueOf(phone.getValue());
                    }
                    catch (Exception e)
                    {
                        flag = false;
                        phone.setComponentError(new UserError("Заполните поле"));
                        phone.getComponentError();
                    }
                }
                if (flag) {
                    Client client = new Client();
                    client.setFirstname(first.getValue());
                    client.setSecondname(second.getValue());
                    client.setThirdname(third.getValue());
                    client.setPhonenumber(phone.getValue());
                    int ckient_id = ClientService.addClient(client);
                    client.setId(ckient_id);
                    update();
                    subWindow.close();
                }
            }
        });
        buttonno.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                subWindow.close();
            }
        });
        butHorizont.addComponent(buttonok);
        butHorizont.addComponent(buttonno);
        verticalLayout.addComponent(butHorizont);

        // Center it in the browser window
        subWindow.center();

        // Open it in the UI
        mainui.addWindow(subWindow);
    }
}