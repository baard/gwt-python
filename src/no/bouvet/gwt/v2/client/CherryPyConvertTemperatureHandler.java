package no.bouvet.gwt.v2.client;

import no.bouvet.gwt.v2.shared.ConvertTemperature;
import no.bouvet.gwt.v2.shared.ConvertTemperatureResult;
import no.rehn.gwt.remoting.client.ActionHandlerAsync;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Adapter for the CherryPy temperature conversion.
 */
public class CherryPyConvertTemperatureHandler implements ActionHandlerAsync<ConvertTemperature, ConvertTemperatureResult> {
    final String moduleBase;
    UrlEncoder urlEncoder = new DefaultURLEncoder();
    RequestBuilderFactory requestBuilderFactory = new DefaultRequestBuilderFactory();

    public CherryPyConvertTemperatureHandler(String moduleBase) {
        this.moduleBase = moduleBase.substring(moduleBase.lastIndexOf('/') + 1);
    }
    
    @Override
    public void handle(final ConvertTemperature action, final AsyncCallback<ConvertTemperatureResult> callback) {
        String url = urlEncoder.encode(moduleBase + "/fahr_to_celc?degrees=" + action.getFahrenheits());
        RequestBuilder rb = requestBuilderFactory.create(RequestBuilder.GET, url);
        rb.setCallback(new RequestCallback() {
            @Override
            public void onResponseReceived(Request request, Response response) {
                double result = Double.parseDouble(response.getText());
                callback.onSuccess(new ConvertTemperatureResult(action.getFahrenheits(), result));
            }

            @Override
            public void onError(Request request, Throwable exception) {
                callback.onFailure(exception);
            }
        });
        try {
            rb.send();
        } catch (RequestException e) {
            callback.onFailure(e);
        }
    }

    /**
     * Creates {@link RequestBuilder}s.
     * <p>
     * Convenient when test needs to hook into this creation.
     */
    interface RequestBuilderFactory {
        RequestBuilder create(RequestBuilder.Method method, String url);
    }

    /**
     * Default non-testable implementation.
     */
    static class DefaultRequestBuilderFactory implements RequestBuilderFactory {
        @Override
        public RequestBuilder create(Method method, String url) {
            return new RequestBuilder(method, url);
        }

    }

    /**
     * Used to encode URLs.
     */
    interface UrlEncoder {
        String encode(String url);
    }

    /**
     * Default non-testable implementation.
     */
    static class DefaultURLEncoder implements UrlEncoder {
        @Override
        public String encode(String url) {
            return URL.encode(url);
        }
    }
}
