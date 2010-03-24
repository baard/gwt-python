package no.bouvet.gwt.v1.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Implementation of the temperature conversion widget that is not simple to
 * test.
 */
public class ConversionWidget extends Composite {
    final TextBox input = new TextBox();
    final Button button = new Button("Convert!");
    final Label output = new Label();
    
    public ConversionWidget() {
        input.setText("10.0");
        button.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                onConvert();
            }
        });
        FlowPanel container = new FlowPanel();
        container.add(new Label("Fahrenheits:"));
        container.add(input);
        container.add(button);
        container.add(output);
        initWidget(container);
    }

    void onConvert() {
        UrlBuilder url = Window.Location.createUrlBuilder();
        url.removeParameter("gwt.codesvr");
        url.setPath("/fahr_to_celc");
        url.setParameter("degrees", input.getText());
        RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, url.buildString());
        rb.setCallback(new RequestCallback() {
            @Override
            public void onResponseReceived(Request request, Response response) {
                output.setText(response.getText() + " celsius");
            }
            
            @Override
            public void onError(Request request, Throwable exception) {
                Window.alert("Server failure: " + exception.getMessage());
            }
        });
        try {
            rb.send();
        } catch (RequestException e) {
            Window.alert("Send failed: " + e.getMessage());
        }
    }
}
