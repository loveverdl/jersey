/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010-2011 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.jersey.samples.bookmark;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import static junit.framework.Assert.*;

/**
 * 
 * @author pavel.bucek@sun.com
 */
public class BookmarkTest extends JerseyTest {

	public BookmarkTest() throws Exception {
		super(new WebAppDescriptor.Builder(
				"com.sun.jersey.samples.bookmark.resources").contextPath(
				"Bookmark").build());
	}

	@Test
	public void getUsers() {
		WebResource webResource = resource();
		JSONArray users = webResource.path("users").accept("application/json")
				.get(JSONArray.class);
		assertTrue(users != null);
	}

	@Test
	public void createUser() {
		boolean thrown = false;
		WebResource webResource = resource();
		JSONObject user = new JSONObject();

		try {
			user.put("userid", "testuid").put("password", "test")
					.put("email", "test@test.net").put("username", "Test User");
			webResource.path("users/testuid").type("application/json")
					.put(user);
		} catch (Exception e) {
			e.printStackTrace();
			thrown = true;
		}

		assertFalse(thrown);
	}

	@Test
	public void getUsers2() {
		WebResource webResource = resource();
		JSONArray users = webResource.path("users/").accept("application/json")
				.get(JSONArray.class);
		assertTrue(users != null);
		assertTrue(users.length() == 1);
	}

	@Test
	public void updateUser() {
		boolean thrown = false;
		WebResource webResource = resource();

		try {
			JSONObject user = webResource.path("users/testuid")
					.accept("application/json").get(JSONObject.class);

			user.put("password", "NEW PASSWORD").put("email", "NEW@EMAIL.NET")
					.put("username", "UPDATED TEST USER");
			webResource.path("users/testuid").type("application/json")
					.put(user);

			user = webResource.path("users/testuid").accept("application/json")
					.get(JSONObject.class);

			assertEquals(user.get("username"), "UPDATED TEST USER");
			assertEquals(user.get("email"), "NEW@EMAIL.NET");
			assertEquals(user.get("password"), "NEW PASSWORD");

		} catch (Exception e) {
			e.printStackTrace();
			thrown = true;
		}

		assertFalse(thrown);
	}

	// ugly.. but separation into separate test cases would be probably uglier
	@Test
	public void getUserBookmarkList() {
		boolean thrown = false;

		try {
			WebResource webResource = resource();
			JSONObject user = webResource.path("users/testuid")
					.accept("application/json").get(JSONObject.class);
			assertTrue(user != null);

			webResource = client().resource(user.getString("bookmarks"));

			JSONObject bookmark = new JSONObject();
			bookmark.put("uri", "http://java.sun.com")
					.put("sdesc", "test desc")
					.put("ldesc", "long test description");
			webResource.type("application/json").post(bookmark);

			JSONArray bookmarks = webResource.accept("application/json").get(
					JSONArray.class);
			assertTrue(bookmarks != null);
			int bookmarksSize = bookmarks.length();

			String testBookmarkUrl = bookmarks.getString(0);
			WebResource bookmarkResource = client().resource(testBookmarkUrl);

			bookmark = bookmarkResource.accept("application/json").get(
					JSONObject.class);
			assertTrue(bookmark != null);

			bookmarkResource.delete();

			bookmarks = resource().path("users/testuid/bookmarks")
					.accept("application/json").get(JSONArray.class);
			assertTrue(bookmarks != null);
			assertTrue(bookmarks.length() == (bookmarksSize - 1));

		} catch (Exception e) {
			e.printStackTrace();
			thrown = true;
		}

		assertFalse(thrown);
	}

	@Test
	public void deleteUser() {
		boolean thrown = false;
		WebResource webResource = resource();
		JSONObject user = new JSONObject();

		try {
			webResource.path("users/testuid").delete();
		} catch (Exception e) {
			e.printStackTrace();
			thrown = true;
		}

		assertFalse(thrown);
	}
}
