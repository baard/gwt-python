package no.bouvet.gwt.client;

import no.bouvet.gwt.shared.TemperatureService;
import no.bouvet.gwt.shared.TemperatureServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class SampleApp implements EntryPoint {
    public void onModuleLoad() {
        FlowPanel content = new FlowPanel();
        TemperatureServiceAsync service = createCherryService();
        content.add(new ConversionWidget(service));
        content.add(new TestableConversionWidget(new TestableConversionWidget.Presenter(service)));
        RootPanel.get().add(content);
    }

    TemperatureServiceAsync createGwtRpcService() {
        TemperatureServiceAsync remoteService = GWT.create(TemperatureService.class);
        ((ServiceDefTarget) remoteService).setServiceEntryPoint(GWT.getModuleBaseURL() + "temperatureService");
        return remoteService;
    }

    TemperatureServiceAsync createCherryService() {
        return new TemperatureServicePyCherryImpl(GWT.getModuleBaseURL());
    }
}
