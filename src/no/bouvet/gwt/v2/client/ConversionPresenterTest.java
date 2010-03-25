package no.bouvet.gwt.v2.client;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import no.bouvet.gwt.v2.shared.ConvertTemperature;
import no.bouvet.gwt.v2.shared.ConvertTemperatureResult;
import no.bouvet.gwt.v2.shared.lib.Action;
import no.bouvet.gwt.v2.shared.lib.ActionServiceAsync;
import no.bouvet.gwt.v2.shared.lib.Result;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasText;

public class ConversionPresenterTest {
    ConversionPresenter subject;
    FakeActionServiceAsync fakeService;
    ConversionPresenter.Display fakeDisplay;
    ConversionMessages fakeMessages;

    @Before
    public void setUp() {
        fakeService = new FakeActionServiceAsync();
        fakeMessages = createFakeMessages(ConversionMessages.class);
        subject = new ConversionPresenter(fakeMessages, fakeService);
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
        fakeDisplay.button().fireEvent(new FakeClickEvent());
        ConvertTemperature lastAction = (ConvertTemperature) fakeService.lastAction;
        assertEquals(12.0, lastAction.getFahrenheits(), 0.01);
    }
    
    @Test
    public void callbackSuccessUpdatesDisplay() {
        fakeDisplay.button().fireEvent(new FakeClickEvent());
        fakeService.triggerLastCallback(new ConvertTemperatureResult(12.0, 14.0));
        assertEquals(fakeMessages.output(12.0, 14.0), fakeDisplay.output().getText());
    }

    @Test
    public void callbackFailure() {
        fakeDisplay.button().fireEvent(new FakeClickEvent());
        fakeService.lastCallback.onFailure(new Throwable("some error"));
        assertEquals(fakeMessages.serverFailure("some error"), fakeDisplay.output().getText());
    }
    
    @Test
    public void convertShowsLoadingIndicator() {
        fakeDisplay.button().fireEvent(new FakeClickEvent());
        assertEquals(fakeMessages.converting(), fakeDisplay.output().getText());
    }

    static class FakeDisplay implements ConversionPresenter.Display {
        FakeHasClickHandlers button = new FakeHasClickHandlers();
        FakeHasText input = new FakeHasText();
        FakeHasText output = new FakeHasText();
        FakeHasText alert = new FakeHasText();
        @Override
        public HasClickHandlers button() {
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
    }
    
    // reusable library stuff below

    static class FakeActionServiceAsync implements ActionServiceAsync {
        Action<?> lastAction;
        AsyncCallback<?> lastCallback;
        @Override
        public <T extends Result> void execute(Action<T> action, AsyncCallback<T> callback) {
            this.lastAction = action;
            this.lastCallback = callback;
        }
        
        @SuppressWarnings("unchecked")
        void triggerLastCallback(Result result) {
            ((AsyncCallback) lastCallback).onSuccess(result);
        }
    }
    
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
    
    static <T extends Messages> T createFakeMessages(Class<T> messageClass) {
        Object proxy = Proxy.newProxyInstance(messageClass.getClassLoader(), new Class<?>[] {messageClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // "format" with method and arguments to use in tests
                return method.getName() + Arrays.toString(args);
            }
        });
        return messageClass.cast(proxy);
    }
}
