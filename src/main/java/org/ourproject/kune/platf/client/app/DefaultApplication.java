/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.platf.client.app;

import java.util.Iterator;
import java.util.Map;

import org.ourproject.kune.platf.client.dispatch.DefaultDispatcher;
import org.ourproject.kune.platf.client.dispatch.Dispatcher;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.extend.ExtensibleWidgetsManager;
import org.ourproject.kune.platf.client.services.ColorTheme;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.services.KuneErrorHandler;
import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.tool.ClientTool;
import org.ourproject.kune.workspace.client.WorkspaceEvents;
import org.ourproject.kune.workspace.client.WorkspaceFactory;
import org.ourproject.kune.workspace.client.sitebar.bar.SiteBarListener;
import org.ourproject.kune.workspace.client.workspace.Workspace;

public class DefaultApplication implements Application {
    private final Workspace workspace;
    private final Map<String, ClientTool> tools;
    private Dispatcher dispatcher;
    private StateManager stateManager;

    public DefaultApplication(final Map<String, ClientTool> tools, final Session session,
            final ExtensibleWidgetsManager extensionPointManager, final I18nTranslationService i18n,
            final ColorTheme colorTheme, final KuneErrorHandler errorHandler) {
        this.tools = tools;
        workspace = WorkspaceFactory.createWorkspace(session, extensionPointManager, i18n, colorTheme, errorHandler);
        workspace.attachTools(tools.values().iterator());

        DesktopView desktop = WorkspaceFactory.createDesktop(workspace, new SiteBarListener() {
            public void onChangeState(StateToken token) {
                stateManager.setState(token);
            }

            public void onUserLoggedOut() {
                dispatcher.fire(WorkspaceEvents.USER_LOGGED_OUT, null);
            }
        }, session);
        desktop.attach();

    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public ClientTool getTool(final String toolName) {
        return tools.get(toolName);
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void init(final DefaultDispatcher dispatcher, final StateManager stateManager) {
        this.dispatcher = dispatcher;
        this.stateManager = stateManager;
    }

    public void setGroupState(final String groupShortName) {
        Iterator<ClientTool> iterator = tools.values().iterator();
        while (iterator.hasNext()) {
            ClientTool tool = iterator.next();
            tool.setGroupState(groupShortName);
        }
    }

    public void start() {
        dispatcher.fireDeferred(WorkspaceEvents.START_APP, null);
    }

    public void stop() {
        dispatcher.fire(WorkspaceEvents.STOP_APP, null);
    }
}
