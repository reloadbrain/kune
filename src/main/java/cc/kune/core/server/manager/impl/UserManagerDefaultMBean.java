/*
 *
 * Copyright (C) 2007-2013 The kune development team (see CREDITS for details)
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
package cc.kune.core.server.manager.impl;

import cc.kune.core.server.mbean.MBeanConstants;

/**
 * MBean interface for JMX management of {@link UserManagerDefault}
 *
 */
public interface UserManagerDefaultMBean {

  public static final String MBEAN_OBJECT_NAME = MBeanConstants.PREFIX + "UserManagerDefault";

  /**
   * Reloads configuration object, reading again property files.
   */
  void reIndex();

}
