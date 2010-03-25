package no.bouvet.gwt.v2.server.lib;


import no.bouvet.gwt.v2.server.ConvertTemperatureHandler;
import no.bouvet.gwt.v2.shared.ConvertTemperature;
import no.bouvet.gwt.v2.shared.lib.Action;
import no.bouvet.gwt.v2.shared.lib.ActionService;
import no.bouvet.gwt.v2.shared.lib.Result;

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
