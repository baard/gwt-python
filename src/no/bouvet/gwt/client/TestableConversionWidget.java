package no.bouvet.gwt.client;

import no.bouvet.gwt.shared.TemperatureServiceAsync;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TestableConversionWidget extends Composite {
    TestableConversionWidget(Presenter presenter) {
        DefaultDisplay display = new DefaultDisplay();
        presenter.bind(display);
        initWidget(display);
    }
    
    static class DefaultDisplay extends Composite implements Presenter.Display {
        final Button button = new Button("Convert!");
        final TextBox input = new TextBox();
        final Label output = new Label();
        final WindowAlertAdapter alert = new WindowAlertAdapter();
        
        DefaultDisplay() {
            initWidget(createWidget());
        }
        
        Widget createWidget() {
            FlowPanel container = new FlowPanel();
            container.add(new Label("Fahrenheits:"));
            container.add(input);
            container.add(button);
            container.add(output);
            return container;
        }
        
        @Override
        public HasClickHandlers convertButton() {
            return button;
        }

        @Override
        public HasText input() {
            return input;
        }

        @Override
        public HasText output() {
            return output;
        }
        
        @Override
        public HasText alert() {
            return alert;
        }
        
        static class WindowAlertAdapter implements HasText {
            String alert;
            @Override
            public String getText() {
                return alert;
            }

            @Override
            public void setText(String text) {
                alert = text;
                Window.alert(alert);
            }
        }
    }

    static class Presenter {
        public interface Display {
            HasClickHandlers convertButton();
            HasText input();
            HasText output();
            HasText alert();
        }
        
        final TemperatureServiceAsync service;
        Display display;
        
        public Presenter(TemperatureServiceAsync service) {
            this.service = service;
        }

        void bind(Display display) {
            this.display = display;
            display.input().setText("10.0");
            display.convertButton().addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    onButtonClicked();
                }
            });
        }
        
        void onButtonClicked() {
            double degrees = Double.parseDouble(display.input().getText());
            service.fahrToCelc(degrees, new AsyncCallback<Double>() {
                @Override
                public void onSuccess(Double result) {
                    display.output().setText(Double.toString(result) + " celsius");
                }
                
                @Override
                public void onFailure(Throwable caught) {
                    display.alert().setText("Server failure: " + caught.getMessage());
                }
            });
        }
    }
}
