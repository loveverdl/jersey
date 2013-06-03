package sample.hello.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import sample.hello.bean.Address;
import sample.hello.bean.Contact;
import sample.hello.bean.People;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.ClientFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.representation.Form;

public class ContactClient {

	public static void main(String[] args) {
		File file = new File("C:\\Users\\¶°Áº\\Pictures\\home.png");
		FileInputStream fis;
		byte[] b = null;

		try {
			fis = new FileInputStream(file);
			if (fis != null) {
				int len = fis.available();
				b = new byte[len];
				fis.read(b);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		People p = new People();
		p.setName("wangdl");
		p.setImage(b);

		ClientFilter filter1 = new LoggingFilter();
		Client c = Client.create();
		c.addFilter(filter1);
		WebResource r = c.resource("http://localhost:8081/rest/rest/hello");
		String re = r.accept(MediaType.APPLICATION_JSON_TYPE)
				.type(MediaType.APPLICATION_JSON_TYPE).post(String.class, p);
		System.out.println(re);
		/*
		 * System.out.println("===== Get huangyim ====="); getOneContact(r,
		 * "huangyim");
		 * 
		 * System.out.println("===== Create foo ====="); postForm(r, "foo",
		 * "bar");
		 * 
		 * Address[] addrs = { new Address("Shanghai", "Ke Yuan Street") };
		 * Contact cnt = new Contact("guoqing", "Guo Qing",
		 * Arrays.asList(addrs));
		 * 
		 * System.out.println("===== Create guoqing ====="); putOneContact(r,
		 * cnt);
		 * 
		 * System.out.println("===== All Contacts ====="); getContacts(r);
		 * 
		 * System.out.println("===== Delete foo ====="); deleteOneContact(r,
		 * "foo");
		 * 
		 * System.out.println("===== All Contacts ====="); getContacts(r);
		 */
	}

	public static void getContacts(WebResource r) {

		// 1, get response as plain text
		String jsonRes = r.accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println(jsonRes);

		String xmlRes = r.accept(MediaType.APPLICATION_XML).get(String.class);
		System.out.println(xmlRes);

		// 2, get response and headers etc, wrapped in ClientResponse
		ClientResponse response = r.get(ClientResponse.class);
		System.out.println(response.getStatus());
		System.out.println(response.getHeaders().get("Content-Type"));
		String entity = response.getEntity(String.class);
		System.out.println(entity);

		// 3, get JAXB response
		GenericType<List<Contact>> genericType = new GenericType<List<Contact>>() {
		};
		List<Contact> contacts = r.accept(MediaType.APPLICATION_XML).get(
				genericType);
		System.out.println("No. of Contacts: " + contacts.size());
		Contact contact = contacts.get(0);
		System.out.println(contact.getId() + ": " + contact.getName());
	}

	public static void getOneContact(WebResource r, String id) {
		GenericType<JAXBElement<Contact>> generic = new GenericType<JAXBElement<Contact>>() {
		};
		JAXBElement<Contact> jaxbContact = r.path(id)
				.accept(MediaType.APPLICATION_XML).get(generic);
		Contact contact = jaxbContact.getValue();
		System.out.println(contact.getId() + ": " + contact.getName());
	}

	public static void postForm(WebResource r, String id, String name) {
		Form form = new Form();
		form.add("id", id);
		form.add("name", name);
		ClientResponse response = r.type(MediaType.APPLICATION_FORM_URLENCODED)
				.post(ClientResponse.class, form);
		System.out.println(response.getEntity(String.class));
	}

	public static void putOneContact(WebResource r, Contact c) {
		ClientResponse response = r.path(c.getId())
				.accept(MediaType.APPLICATION_XML).put(ClientResponse.class, c);
		System.out.println(response.getStatus());
	}

	public static void deleteOneContact(WebResource r, String id) {
		ClientResponse response = r.path(id).delete(ClientResponse.class);
		System.out.println(response.getStatus());
	}
}
