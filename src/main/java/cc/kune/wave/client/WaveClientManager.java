package cc.kune.wave.client;

import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.UserSignInEvent;
import cc.kune.core.client.state.UserSignInEvent.UserSignInHandler;
import cc.kune.core.client.state.UserSignOutEvent;
import cc.kune.core.client.state.UserSignOutEvent.UserSignOutHandler;
import cc.kune.core.shared.dto.WaveClientParams;
import cc.kune.gspace.client.WsArmor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.inject.Inject;

public class WaveClientManager {
    private WebClient webClient;

    @Inject
    public WaveClientManager(final Session session, final StateManager stateManager,
            final UserServiceAsync userService, final WsArmor wsArmor) {
        session.onUserSignIn(true, new UserSignInHandler() {
            @Override
            public void onUserSignIn(final UserSignInEvent event) {
                userService.getWaveClientParameters(session.getUserHash(), new AsyncCallbackSimple<WaveClientParams>() {

                    @Override
                    public void onSuccess(final WaveClientParams result) {
                        // NotifyUser.info(result.getSessionJSON(), true);
                        setUseSocketIO(result.useSocketIO());
                        setSessionJSON(JsonUtils.safeEval(result.getSessionJSON()));
                        setClientFlags(JsonUtils.safeEval(result.getClientFlags()));
                        // Only for testing:
                        final ForIsWidget userSpace = wsArmor.getUserSpace();
                        if (webClient == null) {
                            if (userSpace.getWidgetCount() > 0) {
                                userSpace.remove(0);
                            }
                            webClient = new WebClient();
                            userSpace.add(webClient);
                        }
                    }
                });
            }
        });
        session.onUserSignOut(true, new UserSignOutHandler() {
            @Override
            public void onUserSignOut(final UserSignOutEvent event) {
                // While we don't find a way to logout in WebClient
                // Garbage collector
                // https://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/0e48c15839f9c9dc
                if (webClient != null) {
                    webClient.removeFromParent();
                    webClient = null;
                }
            }
        });
    }

    private native void setClientFlags(JavaScriptObject object) /*-{
		$wnd.__client_flags = object;
    }-*/;

    private native void setSessionJSON(JavaScriptObject object) /*-{
		$wnd.__session = object;
    }-*/;

    private native void setUseSocketIO(boolean use) /*-{
		$wnd.__useSocketIO = use;
    }-*/;
}