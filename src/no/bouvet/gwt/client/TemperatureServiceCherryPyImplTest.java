package no.bouvet.gwt.client;

import no.bouvet.gwt.client.TemperatureServiceCherryPyImpl.RequestBuilderFactory;
import no.bouvet.gwt.client.TemperatureServiceCherryPyImpl.UrlEncoder;

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

public class TemperatureServiceCherryPyImplTest {
    static final double SOME_DEGREE = 123.45;
    TemperatureServiceCherryPyImpl subject;
    FakeRequestBuilderFactory fakeRequestBuilderFactory;

    @Before
    public void setUp() {
        subject = new TemperatureServiceCherryPyImpl("");
        subject.urlEncoder = new FakeUrlEncoder();
        fakeRequestBuilderFactory = new FakeRequestBuilderFactory();
        subject.requestBuilderFactory = fakeRequestBuilderFactory;
    }

    @Test
    public void requestBuilt() throws Exception {
        subject.fahrToCelc(10.0, new FakeAsyncCallback<Double>());
        assertEquals("/fahr_to_celc?degrees=10.0", fakeRequestBuilderFactory.lastUrl);
    }

    @Test
    public void responseIsParsed() throws Exception {
        FakeAsyncCallback<Double> callback = new FakeAsyncCallback<Double>();
        subject.fahrToCelc(SOME_DEGREE, callback);
        fakeRequestBuilderFactory.lastRequestBuilder.getCallback().onResponseReceived(null, new ResponseStub("12.0"));
        assertEquals(12.0, callback.lastResult, 0.0);
    }

    @Test
    public void failure() throws Exception {
        FakeAsyncCallback<Double> callback = new FakeAsyncCallback<Double>();
        subject.fahrToCelc(SOME_DEGREE, callback);
        Throwable error = new Throwable();
        fakeRequestBuilderFactory.lastRequestBuilder.getCallback().onError(null, error);
        assertEquals(error, callback.lastCaught);
    }

    @Test
    public void requestFailure() throws Exception {
        FakeAsyncCallback<Double> callback = new FakeAsyncCallback<Double>();
        fakeRequestBuilderFactory.throwRequestException = true;
        subject.fahrToCelc(SOME_DEGREE, callback);
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
