package com.diego.jgaliweather_rest.filters;

import java.io.IOException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class DemoRequestFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext cr) throws IOException {
        // Si la URL solicitada contiene el string "/40", se enviará un error 401 UNAUTHORIZED
        if (cr.getUriInfo().getRequestUri().toString().toLowerCase().contains("/40")) {
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    }
}
