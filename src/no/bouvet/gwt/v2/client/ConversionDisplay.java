package no.bouvet.gwt.v2.client;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

class ConversionDisplay extends Composite implements ConversionPresenter.Display {
    interface Images extends ClientBundle {
        ImageResource spinner();
    }

    interface Binder extends UiBinder<Widget, ConversionDisplay> {
    }

    @UiField(provided = true)
    final ConversionDisplay.Images images;

    @UiField(provided = true)
    final ConversionMessages messages;

    @UiField
    Button button;

    @UiField
    TextBox input;

    @UiField
    Label output;
    
    ConversionDisplay(ConversionMessages messages, ConversionDisplay.Images images, Binder uiBinder) {
        this.messages = messages;
        this.images = images;
        initWidget(uiBinder.createAndBindUi(this));
    }
    
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