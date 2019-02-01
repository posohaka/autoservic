package com.haulmont.testtask.view;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.data.entities.ClientEntity;
import com.haulmont.testtask.data.entities.MechanicEntity;
import com.haulmont.testtask.data.entities.OrdersEntity;
import com.haulmont.testtask.data.services.ClientService;
import com.haulmont.testtask.data.services.MechanicService;
import com.haulmont.testtask.data.services.OrdersService;
import com.haulmont.testtask.model.Client;
import com.haulmont.testtask.model.Mechanic;
import com.haulmont.testtask.model.Orders;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import org.jboss.marshalling.Pair;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class OrderComponent extends CustomComponent {

    private Grid<OrdersEntity> grid = new Grid<>();
    private TextField textFieldfilter = new TextField();
    private ComboBox<String> comboBoxfilter = new ComboBox();

    public OrderComponent(MainUI mainui)
    {
        Panel panel = new Panel();
        VerticalLayout layout = new VerticalLayout();
        panel.setContent(layout);
        HorizontalLayout horizontalLayoutfilter = new HorizontalLayout();
        //TextField textFieldfilter = new TextField();
        List<String> list = new ArrayList<>();
        list.add("По клиенту");
        list.add("По описанию");
        list.add("Запланирован");
        list.add("Выполнен");
        list.add("Принят клиентом");
        comboBoxfilter.setItems(list);
        comboBoxfilter.setSelectedItem(list.get(0));
        horizontalLayoutfilter.addComponent(textFieldfilter);
        //horizontalLayoutfilter.addComponent(comboBoxfilter);
        Button buttonfilter = new Button(VaadinIcons.CLOSE);
        horizontalLayoutfilter.addComponent(buttonfilter);
        horizontalLayoutfilter.addComponent(comboBoxfilter);
        layout.addComponent(horizontalLayoutfilter);
        buttonfilter.addClickListener((Button.ClickListener) clickEvent -> textFieldfilter.clear());
        HorizontalLayout layotaddButton = new HorizontalLayout();
        //layotaddButton.setSizeFull();
        Button buttonaddOrder = new Button("Добавить заказ");
        Button buttondeleteOrder = new Button("Удалить заказ");
        Button buttonchangeOrder = new Button("Редактировать");
        layotaddButton.addComponent(buttonaddOrder);
        layotaddButton.addComponent(buttondeleteOrder);
        layotaddButton.addComponent(buttonchangeOrder);
        HorizontalLayout horizontalLayoutTable = new HorizontalLayout();
        grid.setSizeFull();
        grid.addColumn(OrdersEntity::getDescription).setCaption("Описание");
        grid.addColumn(order -> {
            ClientEntity clientEntity = order.getClientEntity();
            return clientEntity.getFirstname() + " " + clientEntity.getSecondname();
        }
        ).setCaption("Клиент");
        grid.addColumn(order ->
        {
            MechanicEntity mechanicEntity = order.getMechanicEntity();
            return mechanicEntity.getFirstname() + " " + mechanicEntity.getSecondname();
        }).setCaption("Механик");
        grid.addColumn(OrdersEntity::getBegintime).setCaption("Дата создания");
        grid.addColumn(OrdersEntity::getEndtime).setCaption("Дата окончания работ");
        grid.addColumn(OrdersEntity::getPrice).setCaption("Стоимость");
        grid.addColumn(order -> getStatus(order.getStatus())).setCaption("Статус");
        update();
        horizontalLayoutTable.addComponent(grid);
        horizontalLayoutTable.setSizeFull();
        layout.addComponent(horizontalLayoutTable);
        layout.addComponent(layotaddButton);
        buttonaddOrder.addClickListener((Button.ClickListener) event -> addbut(mainui));
        buttondeleteOrder.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Set<OrdersEntity> ordersEntities = grid.getSelectedItems();
                for (OrdersEntity ordersEntity : ordersEntities)
                {
                    Orders orders = new Orders(ordersEntity);
                    deletebut(orders, mainui);
                }
            }
        });
        buttonchangeOrder.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Set<OrdersEntity> ordersEntities = grid.getSelectedItems();
                for (OrdersEntity ordersEntity : ordersEntities)
                {
                    Orders orders = new Orders(ordersEntity);
                    changebut(orders, mainui);
                }
            }
        });

        comboBoxfilter.addValueChangeListener(e ->
        {
            textFieldfilter.clear();
            update();
        });

        textFieldfilter.addValueChangeListener(e -> update());
        textFieldfilter.setValueChangeMode(ValueChangeMode.LAZY);
//        buttonfilter.addClickListener(new Button.ClickListener() {
//            @Override
//            public void buttonClick(Button.ClickEvent event) {
//                String valuecombobox = (String) comboBoxfilter.getValue();
//                switch (valuecombobox) {
//                    case ("По клиенту"):
//                        List<OrdersEntity> ordersEntities1 = OrdersService.getByClient(textFieldfilter.getValue());
//                        break;
//                    case ("По описанию"):
//                }
//            }
//        });
        setCompositionRoot(panel);
        layout.setSizeFull();
        panel.setSizeFull();


        setSizeFull();
    }

    private void update()
    {
        List<OrdersEntity> ordersEntities;
        if (comboBoxfilter.getValue() != null) {
            if (textFieldfilter.getValue() == null || textFieldfilter.getValue().equals("")) {
                String value = comboBoxfilter.getValue();
                //ordersEntities = OrdersService.allOrders();
                switch (value) {
                    case "Запланирован":
                        ordersEntities = OrdersService.getByStatusPlan();
                        break;
                    case "Выполнен":
                        ordersEntities = OrdersService.getByStatusDone();
                        break;
                    case "Принят клиентом":
                        ordersEntities = OrdersService.getByStatusOk();
                        break;
                    default:
                        ordersEntities = OrdersService.allOrders();
                        break;
                }

            } else {
                String value = comboBoxfilter.getValue();
                switch (value) {
                    case "По клиенту":
                        ordersEntities = OrdersService.getByClient(textFieldfilter.getValue());
                        break;
                    case "По описанию":
                        ordersEntities = OrdersService.getByDescription(textFieldfilter.getValue());
                        break;
                    default:
                        ordersEntities = OrdersService.allOrders();
                        break;
                }
            }
        }
        else {
            ordersEntities = OrdersService.allOrders();
        }
        grid.setItems(ordersEntities);
    }

    private void deletebut(Orders orders, UI mainui)
    {
        try {
            OrdersService.removeOrder(orders.getId());
            update();
        }
        catch (Exception e)
        {
//            Window subWindow = new Window("Ошибка");
//            VerticalLayout verticalLayout = new VerticalLayout();
//            subWindow.setContent(verticalLayout);
//            verticalLayout.addComponent(new Label("Этого клиента нельзя удалить. Видимо у него заказ."));
//            subWindow.center();
//            mainui.addWindow(subWindow);
        }
    }

    //поменяй этот метод чтобы работал на заказах и подумай над окном дат
    private void changebut(Orders orders, UI mainui)
    {
        Window subWindow = new Window("Редактировать");
        Label labelDescription = new Label("Описание");
        Label labelClient = new Label("Клиент");
        Label labelMechanic = new Label("Механик");
        Label labelDateBegin = new Label("Дата создания");
        Label labelDateEnd = new Label("Дата окончания");
        Label labelPrice = new Label("Стоимость");
        Label labelStatus = new Label("Статус");
        TextField textDescription = new TextField();
        ComboBox<ClientEntity> comboBoxClient = new ComboBox<>();
        ComboBox<MechanicEntity> comboBoxMechanic = new ComboBox<>();
        DateField begin = new DateField();
        Date beginDate = orders.getBegintime();
        if (beginDate == null) {
            begin.setValue(LocalDate.now());
        }
        else {
            begin.setValue(beginDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        DateField end = new DateField();
        Date endDate = orders.getEndtime();
        if (endDate == null)
        {
            end.setValue(LocalDate.now());
        }
        else {
            end.setValue(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        }
        TextField textFieldPrice = new TextField();
        ComboBox<Pair<Integer, String>> comboBoxStatus = new ComboBox<>();
        textDescription.setValue(orders.getDescription());
        List<ClientEntity> clientEntities = ClientService.allclient();
        comboBoxClient.setItems(clientEntities);
        comboBoxClient.setItemCaptionGenerator(client -> {
            return client.getFirstname() + " " + client.getSecondname();
        });
        OrdersEntity ordersEntity = OrdersService.getOrderById(orders.getId());
        comboBoxClient.setSelectedItem(ordersEntity.getClientEntity());
        List<MechanicEntity> mechanicEntities = MechanicService.allMechanic();
        comboBoxMechanic.setItems(mechanicEntities);
        comboBoxMechanic.setItemCaptionGenerator(mechanic -> {
            return mechanic.getFirstname() + " " + mechanic.getSecondname();
        } );
        comboBoxMechanic.setSelectedItem(ordersEntity.getMechanicEntity());
        textFieldPrice.setValue(String.valueOf(orders.getPrice()));
        List<Pair<Integer, String>> listStatus = new ArrayList<>();
        listStatus.add(new Pair<>(0, "Запланирован"));
        listStatus.add(new Pair<>(1, "Выполнен"));
        listStatus.add(new Pair<>(2, "Передан клиенту"));
        comboBoxStatus.setItems(listStatus);
        comboBoxStatus.setItemCaptionGenerator(par -> {
            return par.getB();
        });
        comboBoxStatus.setSelectedItem(new Pair<>(orders.getStatus(), getStatus(orders.getStatus())));
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout subContent = new HorizontalLayout();
        subWindow.setContent(verticalLayout);
        HorizontalLayout horizontalLayoutfirst = new HorizontalLayout();
        HorizontalLayout horizontalLayoutsecond = new HorizontalLayout();
        VerticalLayout layotDescript = new VerticalLayout();
        VerticalLayout layotClient = new VerticalLayout();
        VerticalLayout layotMechanic = new VerticalLayout();
        VerticalLayout layoutDateBegin = new VerticalLayout();
        VerticalLayout layoutDateEnd = new VerticalLayout();
        VerticalLayout layoutPrice = new VerticalLayout();
        VerticalLayout layoutStatus = new VerticalLayout();
        layotDescript.addComponent(labelDescription);
        layotDescript.addComponent(textDescription);
        layotClient.addComponent(labelClient);
        layotClient.addComponent(comboBoxClient);
        layotMechanic.addComponent(labelMechanic);
        layotMechanic.addComponent(comboBoxMechanic);
        layoutDateBegin.addComponent(labelDateBegin);
        layoutDateBegin.addComponent(begin);
        layoutDateEnd.addComponent(labelDateEnd);
        layoutDateEnd.addComponent(end);
        layoutPrice.addComponent(labelPrice);
        layoutPrice.addComponent(textFieldPrice);
        layoutStatus.addComponent(labelStatus);
        layoutStatus.addComponent(comboBoxStatus);
        horizontalLayoutfirst.addComponent(layotDescript);
        horizontalLayoutfirst.addComponent(layotClient);
        horizontalLayoutfirst.addComponent(layotMechanic);
        horizontalLayoutsecond.addComponent(layoutDateBegin);
        horizontalLayoutsecond.addComponent(layoutDateEnd);
        horizontalLayoutsecond.addComponent(layoutStatus);
        horizontalLayoutsecond.addComponent(layoutPrice);
        verticalLayout.addComponent(horizontalLayoutfirst);
        verticalLayout.addComponent(horizontalLayoutsecond);
        //verticalLayout.addComponent(subContent);
        HorizontalLayout butHorizont = new HorizontalLayout();
        // Put some components in it
        //subContent.addComponent(table1);
        Button buttonok = new Button("Ок");
        Button buttonno = new Button("Отменить");
        buttonok.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean flag = true;
                if (textDescription.getValue() == null || textDescription.getValue().equals("")) {
                    flag = false;
                    textDescription.setComponentError(new UserError("Заполните поле"));
                    textDescription.getComponentError();
                }
                if (comboBoxClient.getValue() == null) {
                    flag = false;
                    comboBoxClient.setComponentError(new UserError("Заполните поле"));
                    comboBoxClient.getComponentError();
                }
                if (comboBoxMechanic.getValue() == null) {
                    flag = false;
                    comboBoxMechanic.setComponentError(new UserError("Заполните поле"));
                    comboBoxMechanic.getComponentError();
                }
                if (comboBoxStatus.getValue() == null) {
                    flag = false;
                    comboBoxStatus.setComponentError(new UserError("Заполните поле"));
                    comboBoxStatus.getComponentError();
                }
                boolean flagDate = true;
                if (begin.getValue() == null) {
                    flag = false;
                    flagDate = false;
                    begin.setComponentError(new UserError("Заполните поле"));
                    begin.getComponentError();
                }
                if (end.getValue() == null) {
                    flag = false;
                    flagDate = false;
                    end.setComponentError(new UserError("Заполните поле"));
                    end.getComponentError();
                }
                if (flagDate)
                {
                    LocalDate localDateBegin = begin.getValue();
                    Date dateB = Date.from(localDateBegin.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    LocalDate localDateEnd = end.getValue();
                    Date dateE = Date.from(localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    if (dateB.after(dateE))
                    {
                        flag = false;
                        begin.setComponentError(new UserError("Даты должны быть в правильном порядке"));
                        begin.getComponentError();
                        end.setComponentError(new UserError("Даты должны быть в правильном порядке"));
                        end.getComponentError();
                    }
                }
                int ptice = 0;
                if (textFieldPrice.getValue() == null || textFieldPrice.getValue().equals("")) {
                    flag = false;
                    textFieldPrice.setComponentError(new UserError("Заполните поле"));
                    textFieldPrice.getComponentError();
                } else {
                    try {
                        ptice = Integer.valueOf(textFieldPrice.getValue());
                        if (ptice <= 0)
                        {
                            flag = false;
                            textFieldPrice.setComponentError(new UserError("Заполните поле"));
                            textFieldPrice.getComponentError();
                        }
                    } catch (Exception e) {
                        flag = false;
                        textFieldPrice.setComponentError(new UserError("Заполните поле"));
                        textFieldPrice.getComponentError();
                    }
                }
                if (flag) {
                    orders.setDescription(textDescription.getValue());
                    orders.setClient(new Client(comboBoxClient.getValue()));
                    orders.setMechanic(new Mechanic(comboBoxMechanic.getValue()));
                    // Date.from(begin.getValue().atZone(ZoneId.systemDefault()).toInstant());
                    LocalDate localDateBegin = begin.getValue();
                    orders.setBegintime(Date.from(localDateBegin.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    LocalDate localDateEnd = end.getValue();
                    orders.setEndtime(Date.from(localDateEnd.atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    orders.setPrice(Integer.parseInt(textFieldPrice.getValue()));
                    orders.setStatus(comboBoxStatus.getValue().getA());
                    OrdersService.changeOrder(orders);
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
        Window subWindow = new Window("Добавить заказ");
        Label labelDescription = new Label("Описание");
        Label labelClient = new Label("Клиент");
        Label labelMechanic = new Label("Механик");
        Label labelPrice = new Label("Стоимость");
        TextField textDescription = new TextField();
        ComboBox<ClientEntity> comboBoxClient = new ComboBox<>();
        ComboBox<MechanicEntity> comboBoxMechanic = new ComboBox<>();
        TextField textFieldPrice = new TextField();
        VerticalLayout verticalLayout = new VerticalLayout();
        HorizontalLayout subContent = new HorizontalLayout();
        subWindow.setContent(verticalLayout);
        List<ClientEntity> clientEntities = ClientService.allclient();
        comboBoxClient.setItems(clientEntities);
        comboBoxClient.setItemCaptionGenerator(client -> {
            return client.getFirstname() + " " + client.getSecondname();
        });
        List<MechanicEntity> mechanicEntities = MechanicService.allMechanic();
        comboBoxMechanic.setItems(mechanicEntities);
        comboBoxMechanic.setItemCaptionGenerator(mechanic -> {
            return mechanic.getFirstname() + " " + mechanic.getSecondname();
        } );
        VerticalLayout layotDescript = new VerticalLayout();
        VerticalLayout layotClient = new VerticalLayout();
        VerticalLayout layotMechanic = new VerticalLayout();
        VerticalLayout layoutPrice = new VerticalLayout();
        layotDescript.addComponent(labelDescription);
        layotDescript.addComponent(textDescription);
        layotClient.addComponent(labelClient);
        layotClient.addComponent(comboBoxClient);
        layotMechanic.addComponent(labelMechanic);
        layotMechanic.addComponent(comboBoxMechanic);
        layoutPrice.addComponent(labelPrice);
        layoutPrice.addComponent(textFieldPrice);
        subContent.addComponent(layotDescript);
        subContent.addComponent(layotClient);
        subContent.addComponent(layotMechanic);
        subContent.addComponent(layoutPrice);
        verticalLayout.addComponent(subContent);
        HorizontalLayout butHorizont = new HorizontalLayout();
        // Put some components in it
        //subContent.addComponent(table1);
        Button buttonok = new Button("Добавить заказ");
        Button buttonno = new Button("Отменить");
        buttonok.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean flag = true;
                if (textDescription.getValue() == null || textDescription.getValue().equals("")) {
                    flag = false;
                    textDescription.setComponentError(new UserError("Заполните поле"));
                    textDescription.getComponentError();
                }
                if (comboBoxClient.getValue() == null) {
                    flag = false;
                    comboBoxClient.setComponentError(new UserError("Заполните поле"));
                    comboBoxClient.getComponentError();
                }
                if (comboBoxMechanic.getValue() == null) {
                    flag = false;
                    comboBoxMechanic.setComponentError(new UserError("Заполните поле"));
                    comboBoxMechanic.getComponentError();
                }
                int ptice = 0;
                if (textFieldPrice.getValue() == null || textFieldPrice.getValue().equals("")) {
                    flag = false;
                    textFieldPrice.setComponentError(new UserError("Заполните поле"));
                    textFieldPrice.getComponentError();
                } else {
                    try {
                        ptice = Integer.valueOf(textFieldPrice.getValue());
                        if (ptice <= 0) {
                            flag = false;
                            textFieldPrice.setComponentError(new UserError("Заполните поле"));
                            textFieldPrice.getComponentError();
                        }
                    } catch (Exception e) {
                        flag = false;
                        textFieldPrice.setComponentError(new UserError("Заполните поле"));
                        textFieldPrice.getComponentError();
                    }
                }
                if (flag) {
                    Orders orders = new Orders();
                    orders.setDescription(textDescription.getValue());
                    orders.setClient(new Client(comboBoxClient.getValue()));
                    orders.setMechanic(new Mechanic(comboBoxMechanic.getValue()));
                    orders.setPrice(Integer.parseInt(textFieldPrice.getValue()));
                    int order_id = OrdersService.addOrder(orders);
                    orders.setId(order_id);
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

    private String getStatus(int st)
    {
        switch (st) {
            case (0):
                return "Запланирован";
            case (1):
                return "Выполнен";
            default:
                return "Передан клиенту";
        }
    }
}
