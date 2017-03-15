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
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Manuel Sammer
 */
@Path("ProductDetail")
public class ProductDetailResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ProductDetailResource
     */
    public ProductDetailResource() {
    }

    /**
     * Retrieves representation of an instance of services.ProductDetailResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("id") int _id) {
        //TODO return proper representation object
        return "{\"id\":120,\"name\":\"Frisby\",\"onMarket\":\"2012-11-08\",\"onStock\":200,\"producer\":{\"id\":22,\"name\":\"Scheiben AG\"}}";
    }

    /**
     * PUT method for updating or creating an instance of ProductDetailResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
