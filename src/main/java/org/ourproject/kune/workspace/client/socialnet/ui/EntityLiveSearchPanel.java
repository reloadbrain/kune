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
package org.ourproject.kune.workspace.client.socialnet.ui;

import org.ourproject.kune.platf.client.AbstractPresenter;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.dialogs.BasicDialog;
import org.ourproject.kune.workspace.client.socialnet.EntityLiveSearchView;
import org.ourproject.kune.workspace.client.socialnet.GroupLiveSearchPresenter;

import com.gwtext.client.core.Connection;
import com.gwtext.client.core.Template;
import com.gwtext.client.core.UrlParam;
import com.gwtext.client.data.DataProxy;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.HttpProxy;
import com.gwtext.client.data.JsonReader;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.form.ComboBox;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.event.ComboBoxListenerAdapter;

public class EntityLiveSearchPanel implements EntityLiveSearchView {

    private static final int PAGINATION_SIZE = 10;
    private final AbstractPresenter presenter;
    private BasicDialog dialog;
    private final int searchType;
    private FormPanel searchForm;
    private final I18nTranslationService i18n;

    public EntityLiveSearchPanel(final AbstractPresenter initPresenter, final int searchType,
            final I18nTranslationService i18n) {
        this.presenter = initPresenter;
        this.searchType = searchType;
        this.i18n = i18n;
    }

    public void center() {
        dialog.center();
    }

    public void hide() {
        dialog.hide();
        searchForm.getForm().reset();
    }

    public void show() {
        if (dialog == null) {
            createGroupSearchDialog(searchType);
        }
        dialog.show();
        dialog.center();
        // DOM.setStyleAttribute(dialog.getElement(), "zIndex", "10000");
    }

    private void createGroupSearchDialog(final int searchType) {
        String title;
        if (searchType == EntityLiveSearchView.SEARCH_GROUPS) {
            title = i18n.t("Search existing users and groups");
        } else {
            title = i18n.t("Search existing users");
        }
        dialog = new BasicDialog(title, true, false, 285, 55);
        dialog.setClosable(true);
        dialog.setCollapsible(false);

        DataProxy dataProxy = null;
        switch (searchType) {
        case EntityLiveSearchView.SEARCH_GROUPS:
            dataProxy = new HttpProxy("/kune/json/GroupJSONService/search", Connection.POST);
            break;
        case EntityLiveSearchView.SEARCH_USERS:
            dataProxy = new HttpProxy("/kune/json/UserJSONService/search", Connection.POST);
            break;
        default:
            break;
        }

        JsonReader reader = new JsonReader(new RecordDef(new FieldDef[] { new StringFieldDef("shortName"),
                new StringFieldDef("longName"), new StringFieldDef("link"), new StringFieldDef("iconUrl") }));
        reader.setRoot("list");
        reader.setTotalProperty("size");
        reader.setId("shortName");

        final Store store = new Store(dataProxy, reader);

        store.load(new UrlParam[] { new UrlParam("query", "."), new UrlParam("first", 1),
                new UrlParam("max", PAGINATION_SIZE) });

        searchForm = new FormPanel();
        searchForm.setBorder(false);
        searchForm.setWidth(275);
        searchForm.setHideLabels(true);

        final Template resultTpl = new Template(
                "<div class=\"search-item\"><span class=\"kune-IconHyperlink\"><img alt=\"group logo\" src=\"images/group-def-icon.png\" style=\"height: 16px; width: 16px;\" />{shortName}:&nbsp;{longName}</span></div>");
        ComboBox cb = new ComboBox();
        cb.setStore(store);
        cb.setEmptyText(i18n.t("Write here to search"));
        cb.setDisplayField("longName");
        cb.setTypeAhead(true);
        cb.setLoadingText(i18n.t("Searching..."));
        cb.setWidth(268);
        cb.setPageSize(PAGINATION_SIZE);
        cb.setTpl(resultTpl);
        cb.setMode(ComboBox.REMOTE);
        cb.setMinChars(2);
        cb.setSelectOnFocus(false);
        cb.setHideTrigger(true);
        cb.setHideLabel(true);
        // setTitle(i18n.t("User or group"));
        cb.setItemSelector("div.search-item");

        cb.addListener(new ComboBoxListenerAdapter() {
            public void onSelect(final ComboBox comboBox, final Record record, final int index) {
                ((GroupLiveSearchPresenter) presenter).fireListener(record.getAsString("shortName"), record
                        .getAsString("longName"));
            }
        });

        searchForm.add(cb);
        dialog.add(searchForm);
    }
}
