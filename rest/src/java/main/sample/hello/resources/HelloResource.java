package sample.hello.resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.xml.bind.JAXBElement;

import sample.hello.bean.Contact;
import sample.hello.bean.People;

@Path("hello")
public class HelloResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sayHello() throws Exception {
		System.out.println(213);
		return "sdf";

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String post(JAXBElement<People> jaxbContact) {
		System.out.println("post");
		People p = jaxbContact.getValue();
		try {
			File f = new File("D:\\my.png");
			if (!f.exists()) {
				f.createNewFile();
				f.setWritable(true);
			}
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(p.getImage());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "sdf";

	}

	@GET
	@Path("basket")
	public String get(@Context SecurityContext sc) {
		if (sc.isUserInRole("PreferredCustomer")) {
			return "erer";
		} else {
			return "11";
		}
	}
}