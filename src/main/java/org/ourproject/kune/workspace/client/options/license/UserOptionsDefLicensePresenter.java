package org.ourproject.kune.workspace.client.options.license;

import org.ourproject.kune.workspace.client.licensewizard.LicenseChangeAction;
import org.ourproject.kune.workspace.client.licensewizard.LicenseWizard;
import org.ourproject.kune.workspace.client.options.EntityOptions;

import cc.kune.core.client.state.Session;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.core.shared.dto.UserInfoDTO;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;

public class UserOptionsDefLicensePresenter extends EntityOptionsDefLicensePresenter implements UserOptionsDefLicense {

    public UserOptionsDefLicensePresenter(final EntityOptions entityOptions, final Session session,
            final Provider<LicenseWizard> licenseWizard, final Provider<LicenseChangeAction> licChangeAction) {
        super(entityOptions, session, licenseWizard, licChangeAction);
        session.onUserSignIn(new Listener<UserInfoDTO>() {
            public void onEvent(final UserInfoDTO parameter) {
                setState();
            }
        });
    }

    @Override
    protected boolean applicable() {
        return session.isLogged();
    }

    @Override
    protected LicenseDTO getCurrentDefLicense() {
        return session.getCurrentState().getGroup().getDefaultLicense();
    }

    @Override
    protected StateToken getOperationToken() {
        return session.getCurrentUser().getStateToken();
    }
}