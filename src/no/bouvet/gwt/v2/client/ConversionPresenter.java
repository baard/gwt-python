package no.bouvet.gwt.v2.client;

import no.bouvet.gwt.v2.shared.TemperatureServiceAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;

class ConversionPresenter {
    public interface Display {
        HasClickHandlers convertButton();
        HasText input();
        HasText output();
        HasText alert();
        HasVisibility loading();
    }
    
    final TemperatureServiceAsync service;
    Display display;
    
    public ConversionPresenter(TemperatureServiceAsync service) {
        this.service = service;
    }

    void bind(Display display) {
        this.display = display;
        display.input().setText("10.0");
        display.loading().setVisible(false);
        display.convertButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onButtonClicked();
            }
        });
    }
    
    void onButtonClicked() {
        double degrees = Double.parseDouble(display.input().getText());
        display.loading().setVisible(true);
        service.fahrToCelc(degrees, new AsyncCallback<Double>() {
            @Override
            public void onSuccess(Double result) {
                display.output().setText(Double.toString(result) + " celsius");
                display.loading().setVisible(false);
            }
            
            @Override
            public void onFailure(Throwable caught) {
                display.alert().setText("Server failure: " + caught.getMessage());
                display.loading().setVisible(false);
            }
        });
    }
    
    // reusable library stuff below
    
    interface HasVisibility {
        boolean isVisible();
        void setVisible(boolean visible);
    }
}