package no.bouvet.gwt.v2.client;

import no.bouvet.gwt.v2.shared.TemperatureServiceAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;

/**
 * Testable implementation of the temperature conversion widget.
 * <p>
 * Uses the <em>Passive View</em> pattern where the presenter drives the
 * display. This allows the presenter to be easily tested.
 */
class ConversionPresenter {
    interface Display {
        HasClickHandlers button();
        HasText input();
        HasText output();
    }
    
    final ConversionMessages messages;
    final TemperatureServiceAsync service;
    Display display;
    
    public ConversionPresenter(ConversionMessages messages, TemperatureServiceAsync service) {
        this.messages = messages;
        this.service = service;
    }

    void bind(Display display) {
        this.display = display;
        display.input().setText("10.0");
        display.button().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onButtonClicked();
            }
        });
    }
    
    void onButtonClicked() {
        final double degrees = Double.parseDouble(display.input().getText());
        display.output().setText(messages.converting());
        service.fahrToCelc(degrees, new AsyncCallback<Double>() {
            @Override
            public void onSuccess(Double result) {
                display.output().setText(messages.output(degrees, result));
            }
            
            @Override
            public void onFailure(Throwable caught) {
                display.output().setText(messages.serverFailure(caught.getMessage()));
            }
        });
    }
}