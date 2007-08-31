package org.ourproject.kune.platf.client.dto;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class InitDataDTO implements IsSerializable {
    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.LicenseDTO>
     */
    ArrayList ccLicenses;
    /**
     * @gwt.typeArgs <org.ourproject.kune.platf.client.dto.LicenseDTO>
     */
    ArrayList notCCLicenses;

    UserDTO currentUser;
    private String chatHttpBase;
    private String chatDomain;
    private String chatRoomHost;

    public ArrayList getCCLicenses() {
	return ccLicenses;
    }

    public void setCCLicenses(final ArrayList ccLicenses) {
	this.ccLicenses = ccLicenses;
    }

    public ArrayList getNotCCLicenses() {
	return notCCLicenses;
    }

    public void setNotCCLicenses(final ArrayList notLicenses) {
	this.notCCLicenses = notLicenses;
    }

    public UserDTO getCurrentUser() {
	return currentUser;
    }

    public void setCurrentUser(final UserDTO currentUser) {
	this.currentUser = currentUser;
    }

    public String getChatHttpBase() {
	return chatHttpBase;
    }

    public void setChatHttpBase(final String chatHttpBase) {
	this.chatHttpBase = chatHttpBase;
    }

    public String getChatDomain() {
	return chatDomain;
    }

    public void setChatDomain(final String chatDomain) {
	this.chatDomain = chatDomain;
    }

    public String getChatRoomHost() {
	return chatRoomHost;
    }

    public void setChatRoomHost(final String chatRoomHost) {
	this.chatRoomHost = chatRoomHost;
    }
}