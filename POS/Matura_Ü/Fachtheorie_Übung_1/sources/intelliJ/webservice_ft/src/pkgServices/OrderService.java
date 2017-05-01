package pkgServices;

import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.parser.JSONParser;
import pkgData.Database;
import pkgModels.Order;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.TreeSet;
import java.util.Vector;

/*Created by IntelliJ IDEA.
  Author: sammerm
  Copyright © 2017 by sammerm
  All rights reserved. 
  No part of this publication may be reproduced, distributed, or transmitted in any form or by any means, 
  including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the publisher, 
  except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
  For permission requests, write to the publisher.
*/
@WebServlet(name = "OrderService")
public class OrderService extends HttpServlet {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Vector<Order> ret;
        try {
            ret = Database.getInstance().getOrders();
        } catch (Exception ex) {
            throw new WebApplicationException("Internal server error was: " + ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
        return ret;
    }
}
