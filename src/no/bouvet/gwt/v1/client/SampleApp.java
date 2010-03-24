package no.bouvet.gwt.v1.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * The {@link EntryPoint} for the application.
 */
public class SampleApp implements EntryPoint {
    public void onModuleLoad() {
        RootPanel.get().add(new ConversionWidget());
    }
}
