package org.ourproject.kune.docs.client.ui.ctx.folder;

import org.ourproject.kune.platf.client.View;

public interface FolderContentView extends View {
    void clear();

    void addItem(String name, String type, String event);

    void selectItem(int index);

    void setControlsVisible(boolean isVisible);

    void setCurrentName(String name);

    void setParentButtonEnabled(boolean isEnabled);

}
