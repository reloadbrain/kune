package org.ourproject.kune.chat.client;

import org.ourproject.kune.platf.client.tool.AbstractClientTool;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;
import org.ourproject.kune.workspace.client.dto.StateDTO;

public class ChatClientTool extends AbstractClientTool {
    private static final String NAME = "chats";
    private final ChatToolComponents components;

    public ChatClientTool() {
	// i18n
	super("salas de chat");
	components = new ChatToolComponents(this);
    }

    public WorkspaceComponent getContent() {
	return components.getContent();
    }

    public WorkspaceComponent getContext() {
	return components.getContext();
    }

    public String getName() {
	return NAME;
    }

    public void setContent(final StateDTO state) {
	components.getContent().setState(state);
	components.getContext().setState(state);
    }

}
