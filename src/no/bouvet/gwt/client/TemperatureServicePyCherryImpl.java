package no.bouvet.gwt.client;

import no.bouvet.gwt.shared.TemperatureServiceAsync;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class TemperatureServicePyCherryImpl implements TemperatureServiceAsync {
    final String moduleBase;
    UrlEncoder urlEncoder = new DefaultURLEncoder();
    RequestBuilderFactory requestBuilderFactory = new DefaultRequestBuilderFactory();

    public TemperatureServicePyCherryImpl(String moduleBase) {
        this.moduleBase = moduleBase.substring(moduleBase.lastIndexOf('/') + 1);
    }

    @Override
    public void fahrToCelc(double degrees, final AsyncCallback<Double> callback) {
        String url = urlEncoder.encode(moduleBase + "/fahr_to_celc?degrees=" + degrees);
        RequestBuilder rb = requestBuilderFactory.create(RequestBuilder.GET, url);
        try {
            rb.sendRequest(url, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    double result = Double.parseDouble(response.getText());
                    callback.onSuccess(result);
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    callback.onFailure(exception);
                }
            });
        } catch (RequestException e) {
            callback.onFailure(e);
        }
    }

    interface RequestBuilderFactory {
        RequestBuilder create(RequestBuilder.Method method, String url);
    }

    static class DefaultRequestBuilderFactory implements RequestBuilderFactory {
        @Override
        public RequestBuilder create(Method method, String url) {
            return new RequestBuilder(method, url);
        }

    }

    interface UrlEncoder {
        String encode(String url);
    }

    static class DefaultURLEncoder implements UrlEncoder {
        @Override
        public String encode(String url) {
            return URL.encode(url);
        }
    }
}
