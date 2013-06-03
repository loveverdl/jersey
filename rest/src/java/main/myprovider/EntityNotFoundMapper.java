package myprovider;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundMapper implements
		ExceptionMapper<java.lang.Exception> {
	public Response toResponse(java.lang.Exception ex) {
		return Response.status(404).entity(ex.getMessage()).type("text/plain")
				.build();
	}
}