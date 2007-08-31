/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.chat.client.rooms.ui;

import java.util.HashMap;

import org.ourproject.kune.chat.client.rooms.MultiRoomPresenter;
import org.ourproject.kune.chat.client.rooms.MultiRoomView;
import org.ourproject.kune.chat.client.rooms.Room;
import org.ourproject.kune.chat.client.rooms.RoomPresenter;
import org.ourproject.kune.chat.client.rooms.RoomUserListView;
import org.ourproject.kune.platf.client.View;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.DialogListener;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.TextArea;
import com.gwtext.client.widgets.form.TextAreaConfig;
import com.gwtext.client.widgets.form.event.FieldListener;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.ContentPanelConfig;
import com.gwtext.client.widgets.layout.LayoutRegion;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;
import com.gwtext.client.widgets.layout.event.LayoutRegionListener;

public class MultiRoomPanel implements MultiRoomView, View {
    protected static final String INPUT_FIELD = "input-area";
    private LayoutDialog dialog;
    private Button sendBtn;
    private final MultiRoomPresenter presenter;
    private LayoutRegion centralLayout;
    private Label subject;
    private DeckPanel usersDeckPanel;
    private TextArea input;
    private Form inputForm;
    private final HashMap userListToIndex;
    private final HashMap panelIdToRoom;

    public MultiRoomPanel(final MultiRoomPresenter presenter) {
	this.presenter = presenter;
	this.userListToIndex = new HashMap();
	panelIdToRoom = new HashMap();
	createLayout();
    }

    public void addRoom(final Room room) {
	final BorderLayout layout = dialog.getLayout();
	layout.beginUpdate();

	ContentPanel chatRoomPanel = (ContentPanel) room.getView();
	layout.add(LayoutRegionConfig.CENTER, chatRoomPanel);

	String panelId = chatRoomPanel.getId();
	panelIdToRoom.put(panelId, room);
	layout.endUpdate();

	// FIXME: Returns Exception, affected maybe by:
	// http://code.google.com/p/gwt-ext/issues/detail?id=81
	// layout.showPanel(contentId);
    }

    public void show() {
	dialog.show();
    }

    public void hideRooms() {
	dialog.hide();
    }

    public void setSendEnabled(final boolean enabled) {
	if (enabled) {
	    sendBtn.enable();
	} else {
	    sendBtn.disable();
	}
    }

    public void setSubject(final String text) {
	subject.setText(text);
    }

    public void showUserList(final RoomUserListView view) {
	Integer index = (Integer) userListToIndex.get(view);
	usersDeckPanel.showWidget(index.intValue());
    }

    public void addRoomUsersPanel(final RoomUserListView view) {
	usersDeckPanel.add((Widget) view);
	userListToIndex.put(view, new Integer(usersDeckPanel.getWidgetIndex((Widget) view)));
    }

    public void clearInputText() {
	input.reset();
    }

    public void setInputText(final String text) {
	input.setRawValue(text);
    }

    public String getInputText() {
	return input.getValueAsString();
    }

    private void createLayout() {
	// create layout regions for layout dialog

	LayoutRegionConfig north = new LayoutRegionConfig() {
	    {
		setTitlebar(false);
		setInitialSize(30);
	    }
	};

	LayoutRegionConfig east = new LayoutRegionConfig() {
	    {
		setSplit(true);
		setInitialSize(150);
		setMinSize(100);
		setMaxSize(250);
		setCollapsible(true);
		setAnimate(true);
		setTitlebar(true);
		setAlwaysShowTabs(false);
	    }
	};

	LayoutRegionConfig center = new LayoutRegionConfig() {
	    {
		setTitlebar(false);
		setAutoScroll(true);
		setTabPosition("top");
		setCloseOnTab(true);
		setAlwaysShowTabs(true);
		setMargins(5, 5, 5, 5);
	    }
	};

	LayoutRegionConfig south = new LayoutRegionConfig() {
	    {
		setSplit(true);
		setHideWhenEmpty(false);
		setInitialSize(50);
	    }
	};

	dialog = new LayoutDialog(new LayoutDialogConfig() {
	    {
		setModal(false);
		setWidth(600);
		setHeight(400);
		setShadow(true);
		setMinHeight(300);
		setMinHeight(300);
		setProxyDrag(true);
		// i18n
		setTitle("Chat rooms");
	    }
	}, north, south, null, east, center);

	sendBtn = dialog.addButton("Send");
	sendBtn.addButtonListener(new ButtonListenerAdapter() {
	    public void onClick(final Button button, final EventObject e) {
		presenter.onSend();
	    }
	});

	final BorderLayout layout = dialog.getLayout();

	layout.beginUpdate();

	layout.setMonitorWindowResize(true);

	ContentPanel eastPanel = createUsersPanel();

	ContentPanel southPanel = createInputPanel();

	ContentPanel northPanel = createSubjectPanel();

	layout.add(LayoutRegionConfig.NORTH, northPanel);

	layout.add(LayoutRegionConfig.EAST, eastPanel);

	layout.add(LayoutRegionConfig.SOUTH, southPanel);

	layout.endUpdate();

	createListeners();

    }

    private void createListeners() {
	dialog.addDialogListener(new DialogListener() {
	    public boolean doBeforeHide(final LayoutDialog dialog) {
		if (centralLayout.getNumPanels() > 0) {
		    return Window.confirm("Sure?");
		}
		return true;
	    }

	    public boolean doBeforeShow(final LayoutDialog dialog) {
		return true;
	    }

	    public void onHide(final LayoutDialog dialog) {
	    }

	    public void onKeyDown(final LayoutDialog dialog, final EventObject e) {
	    }

	    public void onMove(final LayoutDialog dialog, final int x, final int y) {
	    }

	    public void onResize(final LayoutDialog dialog, final int width, final int height) {
	    }

	    public void onShow(final LayoutDialog dialog) {
	    }
	});

	centralLayout = dialog.getLayout().getRegion(LayoutRegionConfig.CENTER);

	centralLayout.addLayoutRegionListener(new LayoutRegionListener() {

	    public boolean doBeforeRemove(final LayoutRegion region, final ContentPanel panel) {
		if (Window.confirm("Are you sure?")) {
		    RoomPresenter roomPresenter = (RoomPresenter) panelIdToRoom.get(panel.getId());
		    presenter.closeRoom(roomPresenter);
		    return true;
		}
		return false;
	    }

	    public void onCollapsed(final LayoutRegion region) {
	    }

	    public void onExpanded(final LayoutRegion region) {
	    }

	    public void onInvalidated(final LayoutRegion region) {
	    }

	    public void onPanelActivated(final LayoutRegion region, final ContentPanel panel) {
		RoomPresenter roomPresenter = (RoomPresenter) panelIdToRoom.get(panel.getId());
		presenter.activateRoom(roomPresenter);
	    }

	    public void onPanelAdded(final LayoutRegion region, final ContentPanel panel) {
	    }

	    public void onPanelRemoved(final LayoutRegion region, final ContentPanel panel) {
		if (centralLayout.getNumPanels() == 0) {
		    presenter.onNoRooms();
		}
	    }

	    public void onResized(final LayoutRegion region, final int newSize) {
	    }

	    public void onSlideHide(final LayoutRegion region) {
	    }

	    public void onSlideShow(final LayoutRegion region) {
	    }

	    public void onVisibilityChange(final LayoutRegion region, final boolean visibility) {
	    }
	});

    }

    private ContentPanel createUsersPanel() {
	// i18n
	ContentPanel eastPanel = new ContentPanel(Ext.generateId(), "Users");
	usersDeckPanel = new DeckPanel();
	usersDeckPanel.addStyleName("kune-MultiRoomPanel-User");
	eastPanel.add(usersDeckPanel);
	return eastPanel;
    }

    private ContentPanel createSubjectPanel() {
	subject = new Label();
	ContentPanel northPanel = new ContentPanel(subject, "", new ContentPanelConfig() {
	    {
		setBackground(false);
	    }
	});
	northPanel.addStyleName("kune-MultiRoomPanel-Subject");
	return northPanel;
    }

    private ContentPanel createInputPanel() {

	inputForm = new Form(new FormConfig() {
	    {
		setHideLabels(true);
		setWidth("100%");
	    }
	});
	input = new TextArea(new TextAreaConfig() {
	    {
		setFieldListener(new FieldListener() {
		    public void onBlur(final Field field) {
		    }

		    public void onChange(final Field field, final Object newVal, final Object oldVal) {
			GWT.log("Change MRP", null);
		    }

		    public void onFocus(final Field field) {
			GWT.log("OnFocus MRP", null);
		    }

		    public void onInvalid(final Field field, final String msg) {
		    }

		    public void onSpecialKey(final Field field, final EventObject e) {
			if (e.getKey() == 13) {
			    if (e.isCtrlKey()) {
				// Do nothing, ctrl+enter = enter
			    } else {
				presenter.onSend();
				e.stopEvent();
			    }
			}
		    }

		    public void onValid(final Field field) {
		    }
		});
	    }
	});
	inputForm.add(input);

	inputForm.end();
	inputForm.render();

	ContentPanel southPanel = new ContentPanel(input, "", new ContentPanelConfig() {
	    {
		setBackground(true);
		setFitToFrame(true);
	    }
	});

	input.setWidth("100%");
	input.setHeight("100%");

	return southPanel;
    }
}