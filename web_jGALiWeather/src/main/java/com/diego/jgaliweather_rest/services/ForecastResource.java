/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.diego.jgaliweather_rest.services;

import com.diego.DAOs.DatabaseConnector;
import com.diego.VOs.MeteorologicalDataDay;
import com.diego.jgaliweather_rest.deploy.MyDeployListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Difma
 */
@Path("")
public class ForecastResource {
    
    @GET
    @Path("/meteorologicalData/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveMeteorologicalData(@PathParam("id") int id){
        
        MeteorologicalDataDay data = null;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        try{
            
            data = DatabaseConnector.getInstance().retrieveVariableDataForLocation(id, sdf.parse("2015-07-25"));
            data.setComment(DatabaseConnector.getInstance().getComment(id, new Date()));
            
        } catch (Exception e) {

            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(e.getMessage()).build();

        }
        
        if(data != null){
            return Response.status(Response.Status.OK).entity(data).build();
        }
        else{
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
