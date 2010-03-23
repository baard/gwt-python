package no.bouvet.gwt.client;

import static org.junit.Assert.*;
import no.bouvet.gwt.shared.TemperatureServiceAsync;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;

public class TestableConversionWidgetTest {
    TestableConversionWidget.Presenter subject;
    FakeTemperatureServiceAsync fakeService;
    TestableConversionWidget.Presenter.Display fakeDisplay;

    @Before
    public void setUp() {
        fakeService = new FakeTemperatureServiceAsync();
        subject = new TestableConversionWidget.Presenter(fakeService);
        fakeDisplay = new FakeDisplay();
        subject.bind(fakeDisplay);
    }
    
    @Test
    public void defaultDisplayValues() {
        assertEquals("10.0", fakeDisplay.input().getText());
    }

    @Test
    public void convertTriggersService() {
        fakeDisplay.input().setText("12.0");
        fakeDisplay.convertButton().fireEvent(new FakeClickEvent());
        assertEquals(12.0, fakeService.lastDegreeParameter, 0.01);
    }
    
    @Test
    public void callbackSuccessUpdatesDisplay() {
        fakeDisplay.convertButton().fireEvent(new FakeClickEvent());
        fakeService.lastCallback.onSuccess(12.0);
        assertEquals("12.0 celsius", fakeDisplay.output().getText());
    }

    @Test
    public void callbackFailure() {
        fakeDisplay.convertButton().fireEvent(new FakeClickEvent());
        fakeService.lastCallback.onFailure(new Throwable("some error"));
        assertEquals("Server failure: some error", fakeDisplay.alert().getText());
    }
    
    static class FakeDisplay implements TestableConversionWidget.Presenter.Display {
        FakeHasClickHandlers convertButton = new FakeHasClickHandlers();
        FakeHasText input = new FakeHasText();
        FakeHasText output = new FakeHasText();
        FakeHasText alert = new FakeHasText();
        @Override
        public HasClickHandlers convertButton() {
            return convertButton;
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
    }
    
    static class FakeTemperatureServiceAsync implements TemperatureServiceAsync {
        double lastDegreeParameter;
        AsyncCallback<Double> lastCallback;
        @Override
        public void fahrToCelc(double degrees, AsyncCallback<Double> callback) {
            this.lastDegreeParameter = degrees;
            this.lastCallback = callback;
        }
    }

    // reusable library stuff below
    
    static class FakeClickEvent extends ClickEvent {
    }

    static class FakeHasClickHandlers implements HasClickHandlers {
        HandlerManager handlerManager = new HandlerManager(this);
        @Override
        public HandlerRegistration addClickHandler(ClickHandler handler) {
            return handlerManager.addHandler(ClickEvent.getType(), handler);
        }

        @Override
        public void fireEvent(GwtEvent<?> event) {
            handlerManager.fireEvent(event);
        }
    }
    
    static class FakeHasText implements HasText {
        String text;
        @Override
        public String getText() {
            return text;
        }

        @Override
        public void setText(String text) {
            this.text = text;
        }
    }
}
