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
@Path("ProductList")
public class ProductListResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ProductListResource
     */
    public ProductListResource() {
    }

    /**
     * Retrieves representation of an instance of services.ProductListResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        return "[{\"id\":110,\"name\":\"Frisby\",\"onMarket\":\"2002-0411\",\"onStock\":200},{\"id\":120,\"name\":\"Frisby\",\"onMarket\":\"2012-1108\",\"onStock\":200},{\"id\":130,\"name\":\"Ball\",\"onMarket\":\"2009-0215\",\"onStock\":190},{\"id\":140,\"name\":\"Ball\",\"onMarket\":\"1992-0111\",\"onStock\":2},{\"id\":150,\"name\":\"Fidschi Gogerl\",\"onMarket\":\"2013-0701\",\"onStock\":1000},{\"id\":160,\"name\":\"Fidschi Gogerl\",\"onMarket\":\"2013-0811\",\"onStock\":10},{\"id\":170,\"name\":\"Fidschi Gogerl\",\"onMarket\":\"2002-0405\",\"onStock\":22},{\"id\":180,\"name\":\"Murmel\",\"onMarket\":\"2010-1113\",\"onStock\":802},{\"id\":190,\"name\":\"Waveboard\",\"onMarket\":\"2011-1113\",\"onStock\":402}]";
    }

    /**
     * PUT method for updating or creating an instance of ProductListResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
