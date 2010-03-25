package no.rehn.gwt.remoting.client.log;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;

//TODO clean up
public class ActionLogger extends Composite {
    private final StringBuilder buffer = new StringBuilder();
    private TextArea log = new TextArea();
    private Button clear = new Button("Clear Log");
    
    public ActionLogger(HandlerManager eventBus) {
        log.setSize("100%", "100px");
        clear.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                buffer.setLength(0);
                updateLogFromBuffer();
            }
        });
        FlowPanel container = new FlowPanel();
        container.add(new Label("Action Log"));
        container.add(clear);
        container.add(log);
        initWidget(container);
        bind(eventBus);
    }
    
    void bind(HandlerManager eventBus) {
        eventBus.addHandler(ActionStartedEvent.getType(), new ActionStartedHandler() {
            @Override
            public void onActionStarted(ActionStartedEvent event) {
                append("Starting: " + event.getAction());
            }
        });
        eventBus.addHandler(ActionEndedEvent.getType(), new ActionEndedHandler() {
            @Override
            public void onActionEnded(ActionEndedEvent event) {
                append("Ended: " + event.getAction() + "(server=" + event.serverDuration() + "ms, client=" + event.clientDuration() + "ms)");
            }
        });
    }

    void append(String message) {
        buffer.append(System.currentTimeMillis()).append(" - ");
        buffer.append(message).append("\n");
        updateLogFromBuffer();
    }

    void updateLogFromBuffer() {
        log.setText(buffer.toString());
    }
}
