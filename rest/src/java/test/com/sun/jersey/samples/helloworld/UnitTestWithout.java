package com.sun.jersey.samples.helloworld;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import sample.hello.resources.HelloResource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.client.apache4.ApacheHttpClient4;
import com.sun.jersey.samples.entityprovider.PropertiesProvider;

@RunWith(JUnit4.class)
public class UnitTestWithout {
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost/").port(9998).build();
	}

	static WebResource wr;

	@BeforeClass
	public static void setUpClass() {
		ResourceConfig rc = new ClassNamesResourceConfig(HelloResource.class);

		System.out.println("Starting grizzly...");
		try {
			GrizzlyServerFactory.createHttpServer(getBaseURI(), rc);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Client c = ApacheHttpClient4.create();
		wr = c.resource("http://localhost:9998");
	}

	@Test
	public void testHello() {
		WebResource webResource = wr.path("hello");
		String responseMsg = webResource.get(String.class);
		Assert.assertEquals("sdf", responseMsg);
	}
}
