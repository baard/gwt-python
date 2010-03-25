package no.bouvet.gwt.v2.client;

import no.bouvet.gwt.v2.client.CherryPyConvertTemperatureHandler.RequestBuilderFactory;
import no.bouvet.gwt.v2.client.CherryPyConvertTemperatureHandler.UrlEncoder;
import no.bouvet.gwt.v2.shared.ConvertTemperature;
import no.bouvet.gwt.v2.shared.ConvertTemperatureResult;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.http.client.Header;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.user.client.rpc.AsyncCallback;

import static org.junit.Assert.*;

public class CherryPyConvertTemperatureHandlerTest {
    static final double SOME_DEGREE = 123.45;
    CherryPyConvertTemperatureHandler subject;
    FakeRequestBuilderFactory fakeRequestBuilderFactory;
    FakeAsyncCallback<ConvertTemperatureResult> callback;

    @Before
    public void setUp() {
        subject = new CherryPyConvertTemperatureHandler("");
        subject.urlEncoder = new FakeUrlEncoder();
        fakeRequestBuilderFactory = new FakeRequestBuilderFactory();
        subject.requestBuilderFactory = fakeRequestBuilderFactory;
        callback = new FakeAsyncCallback<ConvertTemperatureResult>();
    }

    @Test
    public void requestBuilt() throws Exception {
        subject.handle(new ConvertTemperature(10.0), callback);
        assertEquals("/fahr_to_celc?degrees=10.0", fakeRequestBuilderFactory.lastUrl);
    }

    @Test
    public void responseIsParsed() throws Exception {
        subject.handle(new ConvertTemperature(SOME_DEGREE), callback);
        fakeRequestBuilderFactory.lastRequestBuilder.getCallback().onResponseReceived(null, new ResponseStub("12.0"));
        assertEquals(12.0, callback.lastResult.getCelsius(), 0.0);
    }

    @Test
    public void failure() throws Exception {
        subject.handle(new ConvertTemperature(SOME_DEGREE), callback);
        Throwable error = new Throwable();
        fakeRequestBuilderFactory.lastRequestBuilder.getCallback().onError(null, error);
        assertEquals(error, callback.lastCaught);
    }

    @Test
    public void requestFailure() throws Exception {
        fakeRequestBuilderFactory.throwRequestException = true;
        subject.handle(new ConvertTemperature(SOME_DEGREE), callback);
        assertNotNull(callback.lastCaught);
    }

    // reusable library stuff below
    
    static class FakeAsyncCallback<T> implements AsyncCallback<T> {
        Throwable lastCaught;
        T lastResult;

        @Override
        public void onFailure(Throwable caught) {
            this.lastCaught = caught;
        }

        @Override
        public void onSuccess(T result) {
            this.lastResult = result;
        }
    }

    static class FakeRequestBuilderFactory implements RequestBuilderFactory {
        Method lastMethod;
        String lastUrl;
        RequestBuilder lastRequestBuilder;
        boolean throwRequestException;

        @Override
        public RequestBuilder create(Method method, String url) {
            this.lastMethod = method;
            this.lastUrl = url;
            lastRequestBuilder = new RequestBuilder(method, url) {
                @Override
                public Request send() throws RequestException {
                    if (throwRequestException) {
                        throw new RequestException();
                    }
                    return new Request() {
                    };
                }

                @Override
                public Request sendRequest(String requestData, RequestCallback callback) throws RequestException {
                    setCallback(callback);
                    setRequestData(requestData);
                    return send();
                }
            };
            return lastRequestBuilder;
        }
    }

    static class ResponseStub extends Response {
        String text;

        public ResponseStub(String text) {
            this.text = text;
        }

        @Override
        public String getHeader(String header) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Header[] getHeaders() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getHeadersAsString() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getStatusCode() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getStatusText() {
            throw new UnsupportedOperationException();
        }

        @Override
        public String getText() {
            return text;
        }
    }

    static class FakeUrlEncoder implements UrlEncoder {
        @Override
        public String encode(String url) {
            return url;
        }
    }
}
