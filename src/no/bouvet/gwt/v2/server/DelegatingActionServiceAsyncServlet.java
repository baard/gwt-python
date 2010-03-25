package no.bouvet.gwt.v2.server;

import no.bouvet.gwt.v2.shared.ConvertTemperature;
import no.rehn.gwt.remoting.server.DispatchingActionService;
import no.rehn.gwt.remoting.shared.Action;
import no.rehn.gwt.remoting.shared.ActionService;
import no.rehn.gwt.remoting.shared.Result;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class DelegatingActionServiceAsyncServlet extends RemoteServiceServlet implements ActionService {
    final DispatchingActionService delegate = new DispatchingActionService();
    
    public DelegatingActionServiceAsyncServlet() {
        delegate.addHandler(ConvertTemperature.class, new ConvertTemperatureHandler());
    }

    @Override
    public Result execute(Action<Result> action) throws Exception {
        return delegate.execute(action);
    }
}
