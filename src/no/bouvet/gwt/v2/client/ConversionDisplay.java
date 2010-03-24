package no.bouvet.gwt.v2.client;

import no.bouvet.gwt.v2.client.ConversionPresenter.HasVisibility;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Testable implementation of the temperature conversion widget.
 * <p>
 * Uses the <em>Passive View</em> pattern where the presenter drives the
 * display. This allows the presenter to be easily tested.
 */
class ConversionDisplay extends Composite implements ConversionPresenter.Display {
    final Button button = new Button("Convert!");
    final TextBox input = new TextBox();
    final Label output = new Label();
    final PreferredImage loading;
    final WindowAlertAdapter alert = new WindowAlertAdapter();
    
    interface Images extends ClientBundle {
        ImageResource spinner();
    }

    ConversionDisplay() {
        Images images = GWT.create(Images.class);
        loading = new PreferredImage(images.spinner());
        initWidget(createWidget());
    }
    
    Widget createWidget() {
        FlowPanel container = new FlowPanel();
        container.add(new Label("Fahrenheits:"));
        container.add(input);
        container.add(loading);
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
    
    @Override
    public HasVisibility loading() {
        return loading;
    }

    // reusable library stuff below
    
    static class PreferredImage extends Image implements HasVisibility {
        PreferredImage(ImageResource resource) {
            super(resource);
        }
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