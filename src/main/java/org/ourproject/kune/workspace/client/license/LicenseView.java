package org.ourproject.kune.workspace.client.license;

import org.ourproject.kune.platf.client.View;

public interface LicenseView extends View {

    void showImage(String imageUrl);

    void showName(String groupName, String licenseName);

}