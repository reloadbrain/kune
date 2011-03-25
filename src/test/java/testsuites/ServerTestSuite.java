/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package testsuites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cc.kune.core.server.access.AccessRightsServiceTest;
import cc.kune.core.server.access.FinderTest;
import cc.kune.core.server.auth.AuthenticatedMethodInterceptorTest;
import cc.kune.core.server.auth.AuthorizatedMethodInterceptorTest;
import cc.kune.core.server.domain.GroupListTest;
import cc.kune.core.server.finders.GroupFinderTest;
import cc.kune.core.server.finders.LicenseFinderTest;
import cc.kune.core.server.finders.RateFinderTest;
import cc.kune.core.server.finders.UserFinderTest;
import cc.kune.core.server.mapper.MapperTest;
import cc.kune.core.server.properties.KunePropertiesTest;

/**
 * Rescan with :
 * 
 * <pre>
 * find  src/test/java/org/ourproject/kune/platf/server/ -name '*.java' -exec basename \{} .java \;| paste -s - - | sed 's/     /.class, /g'
 * </pre>
 * 
 */
@RunWith(Suite.class)
@SuiteClasses({ GroupListTest.class, AccessRightsServiceTest.class, FinderTest.class, KunePropertiesTest.class,
        LicenseFinderTest.class, UserFinderTest.class, GroupFinderTest.class, RateFinderTest.class,
        AuthorizatedMethodInterceptorTest.class, AuthenticatedMethodInterceptorTest.class, MapperTest.class })
public class ServerTestSuite {

}
