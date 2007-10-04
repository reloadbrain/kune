package org.ourproject.kune.workspace.client.workspace.ui;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.workspace.client.WorkspaceEvents;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class StackSubItemAction {
    private final static Images img = Images.App.getInstance();
    // i18n
    public final static StackSubItemAction DEFAULT_VISIT_GROUP = new StackSubItemAction(img.groupHome(),
	    "Visit this member homepage", WorkspaceEvents.GOTO_GROUP);

    private final AbstractImagePrototype icon;
    private final String text;
    private final String action;

    public StackSubItemAction(final AbstractImagePrototype icon, final String text, final String action) {
	this.icon = icon;
	this.text = text;
	this.action = action;
    }

    public AbstractImagePrototype getIcon() {
	return icon;
    }

    public String getText() {
	return text;
    }

    public String getAction() {
	return action;
    }
}