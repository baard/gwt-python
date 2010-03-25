package no.rehn.gwt.remoting.server;

import no.rehn.gwt.remoting.shared.Action;
import no.rehn.gwt.remoting.shared.Result;

public interface ActionHandler<A extends Action<R>, R extends Result> {
    R handle(A action) throws Exception;
}
