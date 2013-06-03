package client.test;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class JerseyClientGet {

	public static void main(String[] args) {
		JSONObject myObject = new JSONObject();
		try {
			myObject.put("name", "Agamemnon");
			myObject.put("age", 32);
			System.out.println(myObject.toString());
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}
}