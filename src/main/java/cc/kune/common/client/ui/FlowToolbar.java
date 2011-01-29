/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
 \*/
package cc.kune.common.client.ui;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class FlowToolbar extends Composite implements AbstractToolbar, HasWidgets {

    private final HorizontalPanel mainPanel;
    private final FlowPanel childPanel;

    public FlowToolbar() {
        super();
        mainPanel = new HorizontalPanel();
        childPanel = new FlowPanel();
        mainPanel.add(childPanel);
        initWidget(mainPanel);
    }

    public void add(final Widget widget) {
        childPanel.add(widget);
    }

    public void add(final Widget widget, final VerticalAlignmentConstant valign) {
        childPanel.add(widget);
    }

    public Widget addFill() {
        final Label emptyLabel = new Label("");
        emptyLabel.addStyleName("oc-floatright");
        // emptyLabel.setWidth("100%");
        this.add(emptyLabel);
        return emptyLabel;
    }

    public Widget addSeparator() {
        final Label emptyLabel = new Label("");
        emptyLabel.setStyleName("ytb-sep-FIXMEEE");
        emptyLabel.addStyleName("oc-tb-sep");
        emptyLabel.addStyleName("oc-floatleft");
        this.add(emptyLabel);
        return emptyLabel;
    }

    public Widget addSpacer() {
        final Label emptyLabel = new Label("");
        emptyLabel.setStyleName("oc-tb-spacer");
        emptyLabel.addStyleName("oc-floatleft");
        this.add(emptyLabel);
        return emptyLabel;
    }

    @Override
    public void clear() {
        childPanel.clear();
    }

    public void insert(final Widget widget, final int position) {
        childPanel.insert(widget, position);
    }

    @Override
    public Iterator<Widget> iterator() {
        return childPanel.iterator();
    }

    public boolean remove(final Widget widget) {
        return childPanel.remove(widget);
    }

    public void removeAll() {
        childPanel.clear();
    }

    /**
     * Set the blank style
     */
    public void setBlankStyle() {
        setBasicStyle();
        addStyleName("oc-blank-toolbar");
    }

    @Override
    public void setHeight(final String height) {
        mainPanel.setHeight(height);
        childPanel.setHeight(height);
    }

    /**
     * Set the normal grey style
     */
    public void setNormalStyle() {
        setBasicStyle();
        addStyleName("oc-tb-bottom-line");
    }

    /**
     * Set the transparent style
     */
    public void setTranspStyle() {
        setBasicStyle();
        addStyleName("oc-transp");
    }

    private void setBasicStyle() {
        setStyleName("x-toolbar-FIXME");
        addStyleName("x-panel-FIXME");
    }
}