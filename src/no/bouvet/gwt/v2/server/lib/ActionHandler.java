package no.bouvet.gwt.v2.server.lib;

import no.bouvet.gwt.v2.shared.lib.Action;
import no.bouvet.gwt.v2.shared.lib.Result;

public interface ActionHandler<A extends Action<R>, R extends Result> {
    R handle(A action) throws Exception;
}
