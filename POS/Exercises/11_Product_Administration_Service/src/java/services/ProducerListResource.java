/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Manuel Sammer
 */
@Path("ProducerList")
public class ProducerListResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ProducerListResource
     */
    public ProducerListResource() {
    }

    /**
     * Retrieves representation of an instance of services.ProducerListResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        return "[{\"id\":22,\"name\":\"Scheiben AG\"},{\"id\":33,\"name\":\"CeDe AG\",\"sales\":10000.11},{\"id\":44,\"name\":\"ÖFBB\",\"sales\":5000.55},{\"i d\":55,\"name\":\"DFBB\",\"sales\":1000.1},{\"id\":66,\"name\":\"Haitek\",\"sale s\":909.9},{\"id\":77,\"name\":\"Kornblum\"}]";
    }

    /**
     * PUT method for updating or creating an instance of ProducerListResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
