package myprovider;

import javax.ws.rs.ApplicationPath;

import com.sun.jersey.api.core.ClassNamesResourceConfig;

@ApplicationPath("resources")
public class MyApplication extends ClassNamesResourceConfig {
	public MyApplication() {
		super("sample.hello.resources.HelloResource");
	}
}
