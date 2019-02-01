package com.haulmont.testtask.view;

import com.haulmont.testtask.data.entities.MechanicEntity;
import com.haulmont.testtask.data.services.MechanicService;
import com.haulmont.testtask.data.services.util.Statistic;
import com.haulmont.testtask.model.Mechanic;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.PointClickEvent;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.*;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;
import com.vaadin.ui.Label;

import java.util.List;
import java.util.Set;

public class MechanicComponent extends CustomComponent {

    private Grid<MechanicEntity> grid = new Grid<>();

    public MechanicComponent(UI mainui)
    {
        Panel panel = new Panel();
        VerticalLayout layout = new VerticalLayout();
        panel.setContent(layout);
        HorizontalLayout layotaddButton = new HorizontalLayout();

        Button buttonaddMechanic = new Button("Добавить механика");
        Button buttondeleteMechanic = new Button("Удалить механика");
        Button buttonchangeMechanic = new Button("Редактировать");
        Button buttonstatistic = new Button("Статистика");
        layotaddButton.addComponent(buttonaddMechanic);
        layotaddButton.addComponent(buttondeleteMechanic);
        layotaddButton.addComponent(buttonchangeMechanic);
        layotaddButton.addComponent(buttonstatistic);
        //layotaddButton.setSizeFull();
        HorizontalLayout horizontalLayoutTable = new HorizontalLayout();
        grid.addColumn(MechanicEntity::getFirstname).setCaption("Имя");
        grid.addColumn(MechanicEntity::getSecondname).setCaption("Фамилия");
        grid.addColumn(MechanicEntity::getThirdname).setCaption("Очтечство");
        grid.addColumn(MechanicEntity::getPayment).setCaption("Почасовая оплата");
        update();
        horizontalLayoutTable.addComponent(grid);
        grid.setSizeFull();
        horizontalLayoutTable.setSizeFull();
        layout.addComponent(horizontalLayoutTable);
        layout.addComponent(layotaddButton);
        buttonaddMechanic.addClickListener((Button.ClickListener) event -> addbut(mainui));
        buttondeleteMechanic.addClickListener((Button.ClickListener) clickEvent -> {
            Set<MechanicEntity> mechanicEntities = grid.getSelectedItems();
            for (MechanicEntity mechanicEntity : mechanicEntities)
            {
                Mechanic mechanic = new Mechanic(mechanicEntity);
                deletebut(mechanic, mainui);
            }
        });
        buttonchangeMechanic.addClickListener((Button.ClickListener) clickEvent -> {
            Set<MechanicEntity> mechanicEntities = grid.getSelectedItems();
            for (MechanicEntity mechanicEntity : mechanicEntities)
            {
                Mechanic mechanic = new Mechanic(mechanicEntity);
                changebut(mechanic, mainui);
            }
        });
        buttonstatistic.addClickListener((Button.ClickListener) clickEvent -> {

            Set<MechanicEntity> mechanicEntities = grid.getSelectedItems();
            for (MechanicEntity mechanicEntity : mechanicEntities)
            {
                Mechanic mechanic = new Mechanic(mechanicEntity);
                getstatistic(mainui, mechanic);
            }
        });
        setCompositionRoot(panel);
        panel.setSizeFull();
        layout.setSizeFull();

        setSizeFull();
    }

    private void update()
    {
        List<MechanicEntity> mechanicEntities = MechanicService.allMechanic();
        grid.setItems(mechanicEntities);
    }

    private void deletebut(Mechanic mechanic, UI mainui)
    {
        try {
            MechanicService.removeMechanic(mechanic.getId());
            update();
        }
        catch (Exception e)
        {
            Window subWindow = new Window("Ошибка");
            VerticalLayout verticalLayout = new VerticalLayout();
            subWindow.setContent(verticalLayout);
            verticalLayout.addComponent(new Label("Этого механика нельзя удалить. Видимо у него заказ."));
            subWindow.center();
            mainui.addWindow(subWindow);
        }
    }

    private void changebut(Mechanic mechanic, UI mainui)
    {
        Window subWindow = new Window("Редактировать");
        Label labelFN = new Label("Имя");
        Label labelSN = new Label("Фамилия");
        Label labelTN = new Label("Очтечство");
        Label labelPayment = new Label("Стоимость");
        TextField first = new TextField();
        TextField second = new TextField();
        TextField third = new TextField();
        TextField payment = new TextField();
        first.setValue(mechanic.getFirstname());
        second.setValue(mechanic.getSecondname());
        third.setValue(mechanic.getThirdname());
        payment.setValue(String.valueOf(mechanic.getPayment()));
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
        vP.addComponent(labelPayment);
        vP.addComponent(payment);
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
                if (first.getValue() == null || first.getValue().equals("")) {
                    flag = false;
                    first.setComponentError(new UserError("Заполните поле"));
                    first.getComponentError();
                }
                if (second.getValue() == null || second.getValue().equals("")) {
                    flag = false;
                    second.setComponentError(new UserError("Заполните поле"));
                    second.getComponentError();
                }
                if (third.getValue() == null || third.getValue().equals("")) {
                    flag = false;
                    third.setComponentError(new UserError("Заполните поле"));
                    third.getComponentError();
                }
                int paymentValue = 0;
                if (payment.getValue() == null || payment.getValue().equals("")) {
                    flag = false;
                    payment.setComponentError(new UserError("Заполните поле"));
                    payment.getComponentError();
                } else {
                    try {
                        paymentValue = Integer.valueOf(payment.getValue());
                        if (paymentValue <= 0)
                        {
                            flag = false;
                            payment.setComponentError(new UserError("Заполните поле"));
                            payment.getComponentError();
                        }
                    } catch (Exception e) {
                        flag = false;
                        payment.setComponentError(new UserError("Заполните поле"));
                        payment.getComponentError();
                    }
                }
                if (flag) {
                    mechanic.setFirstname(first.getValue());
                    mechanic.setSecondname(second.getValue());
                    mechanic.setThirdname(third.getValue());
                    mechanic.setPayment(paymentValue);
                    MechanicService.changeMechanic(mechanic);
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
        Window subWindow = new Window("Добавить механика");
        Label labelFN = new Label("Имя");
        Label labelSN = new Label("Фамилия");
        Label labelTN = new Label("Очтечство");
        Label labelPhN = new Label("Стоимость");
        TextField first = new TextField();
        TextField second = new TextField();
        TextField third = new TextField();
        TextField payment = new TextField();
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
        vP.addComponent(payment);
        subContent.addComponent(vF);
        subContent.addComponent(vS);
        subContent.addComponent(vT);
        subContent.addComponent(vP);
        verticalLayout.addComponent(subContent);
        HorizontalLayout butHorizont = new HorizontalLayout();
        // Put some components in it
        //subContent.addComponent(table1);
        Button buttonok = new Button("Добавить механика");
        Button buttonno = new Button("Отменить");
        buttonok.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                boolean flag = true;
                if (first.getValue() == null || first.getValue().equals("")) {
                    flag = false;
                    first.setComponentError(new UserError("Заполните поле"));
                    first.getComponentError();
                }
                if (second.getValue() == null || second.getValue().equals("")) {
                    flag = false;
                    second.setComponentError(new UserError("Заполните поле"));
                    second.getComponentError();
                }
                if (third.getValue() == null || third.getValue().equals("")) {
                    flag = false;
                    third.setComponentError(new UserError("Заполните поле"));
                    third.getComponentError();
                }
                int paymentValue = 0;
                if (payment.getValue() == null || payment.getValue().equals("")) {
                    flag = false;
                    payment.setComponentError(new UserError("Заполните поле"));
                    payment.getComponentError();
                } else {
                    try {
                        paymentValue = Integer.valueOf(payment.getValue());
                        if (paymentValue <= 0)
                        {
                            flag = false;
                            payment.setComponentError(new UserError("Заполните поле"));
                            payment.getComponentError();
                        }
                    } catch (Exception e) {
                        flag = false;
                        payment.setComponentError(new UserError("Заполните поле"));
                        payment.getComponentError();
                    }
                }
                if (flag) {
                    Mechanic mechanic = new Mechanic();
                    mechanic.setFirstname(first.getValue());
                    mechanic.setSecondname(second.getValue());
                    mechanic.setThirdname(third.getValue());
                    mechanic.setPayment(paymentValue);
                    int mechanicId = MechanicService.addMechanic(mechanic);
                    mechanic.setId(mechanicId);
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

    private void getstatistic(UI mainui, Mechanic mechanic)
    {
        Statistic statistic = MechanicService.getStatisticOrdersById(mechanic.getId());
        Window subWindow = new Window("Статистика");
        VerticalLayout verticalLayout = new VerticalLayout();
        subWindow.setContent(verticalLayout);
        HorizontalLayout horizontalLayoutMechanic = new HorizontalLayout();
        HorizontalLayout horizontalLayoutSt = new HorizontalLayout();
        VerticalLayout verticalLayout1Plan = new VerticalLayout();
        VerticalLayout verticalLayout1Done = new VerticalLayout();
        VerticalLayout verticalLayout1Ok = new VerticalLayout();
        horizontalLayoutMechanic.addComponent(new Label(mechanic.getFirstname() + " " + mechanic.getSecondname()));
        horizontalLayoutSt.addComponent(verticalLayout1Plan);
        horizontalLayoutSt.addComponent(verticalLayout1Done);
        horizontalLayoutSt.addComponent(verticalLayout1Ok);
        verticalLayout1Plan.addComponent(new Label("Количество апланированых заказов"));
        verticalLayout1Plan.addComponent(new Label(String.valueOf(statistic.getCountPlan())));
        verticalLayout1Done.addComponent(new Label("Количество выполненых заказов"));
        verticalLayout1Done.addComponent(new Label(String.valueOf(statistic.getCountDone())));
        verticalLayout1Ok.addComponent(new Label("Количество принятых клиентом заказов"));
        verticalLayout1Ok.addComponent(new Label(String.valueOf(statistic.getCountClientOk())));
        verticalLayout.addComponent(horizontalLayoutMechanic);
        verticalLayout.addComponent(horizontalLayoutSt);
        verticalLayout.setSizeUndefined();
        subWindow.center();
        mainui.addWindow(subWindow);
    }
}