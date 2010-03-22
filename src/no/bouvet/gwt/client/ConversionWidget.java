package no.bouvet.gwt.client;

import no.bouvet.gwt.shared.TemperatureServiceAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class ConversionWidget extends Composite {
    final Button button = new Button("Convert!");
    final TextBox input = new TextBox();
    final Label output = new Label();

    final TemperatureServiceAsync service;

    public ConversionWidget(TemperatureServiceAsync service) {
        this.service = service;
        setupWidgets();
        initWidget(createWidget());
    }

    private void setupWidgets() {
        input.setText("10.0");
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onButtonClicked();
            }
        });
    }

    void onButtonClicked() {
        double degrees = Double.parseDouble(input.getText());
        service.fahrToCelc(degrees, new AsyncCallback<Double>() {
            @Override
            public void onSuccess(Double result) {
                output.setText(Double.toString(result) + " celsius");
            }
            
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Server failure: " + caught.getMessage());
            }
        });
    }

    private Widget createWidget() {
        FlowPanel container = new FlowPanel();
        container.add(new Label("Fahrenheits:"));
        container.add(input);
        container.add(button);
        container.add(output);
        return container;
    }
}
