package no.bouvet.gwt.v2.client;

import no.bouvet.gwt.v2.client.lib.ActionLogger;
import no.bouvet.gwt.v2.shared.ConvertTemperature;
import no.bouvet.gwt.v2.shared.ConvertTemperatureResult;
import no.bouvet.gwt.v2.shared.lib.ActionHandlerAsync;
import no.bouvet.gwt.v2.shared.lib.ActionService;
import no.bouvet.gwt.v2.shared.lib.ActionServiceAsync;
import no.bouvet.gwt.v2.shared.lib.DelegatingActionHandler;
import no.bouvet.gwt.v2.shared.lib.DispatchingActionServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * The {@link EntryPoint} for the application.
 */
public class SampleApp implements EntryPoint {
    public void onModuleLoad() {
        HandlerManager eventBus = new HandlerManager(null);
        DispatchingActionServiceAsync service = new DispatchingActionServiceAsync(eventBus);
        service.addHandler(ConvertTemperature.class, createCherryPyService());
        RootPanel.get().add(createConversionWidget(service));
        RootPanel.get().add(createActionLogger(eventBus));
    }

    ActionLogger createActionLogger(HandlerManager eventBus) {
        return new ActionLogger(eventBus);
    }
    
    Widget createConversionWidget(ActionServiceAsync service) {
        ConversionMessages messages = GWT.create(ConversionMessages.class);
        ConversionPresenter presenter = new ConversionPresenter(messages, service);
        ConversionDisplay.Images images = GWT.create(ConversionDisplay.Images.class);
        ConversionDisplay.Binder uiBinder = GWT.create(ConversionDisplay.Binder.class);
        ConversionDisplay display = new ConversionDisplay(messages, images, uiBinder);
        presenter.bind(display);
        return display;
    }

    ActionHandlerAsync<ConvertTemperature, ConvertTemperatureResult> createGwtRpcService() {
        ActionServiceAsync actionService = GWT.create(ActionService.class);
        ((ServiceDefTarget) actionService).setServiceEntryPoint(GWT.getModuleBaseURL() + "actionService");
        return new DelegatingActionHandler<ConvertTemperature, ConvertTemperatureResult>(actionService);
    }

    ActionHandlerAsync<ConvertTemperature, ConvertTemperatureResult> createCherryPyService() {
        return new CherryPyConvertTemperatureHandler(GWT.getModuleBaseURL());
    }
}
