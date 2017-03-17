/*
  Created by IntelliJ IDEA.
  Author: Manuel Sammer
  Copyright © 2017 by Manuel Sammer
  All rights reserved.
  No part of this publication may be reproduced, distributed, or transmitted in any form or by any means,
  including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the publisher,
  except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
  For permission requests, write to the publisher.
*/
package pkgData;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class Database {
    private static String connStr="jdbc:oracle:thin:@192.168.128.152:1521:ora11g";//Externe ip jdbc:oracle:thin:@212.152.179.117:1521:ora11g192.168.128.152
    private static String user = "d5b12";
    private static String passwd = "d5b";

    //PLF SQL
    private static String selectCustomer = "SELECT COUNT(*) FROM CUSTOMERS WHERE (NAME = ? AND PASSWD = ?)";
    private static String selectCustomerId = "SELECT ID FROM CUSTOMERS WHERE NAME = ?";

    private static String selectCarsWithoutOwner = "SELECT ID, NAME, PRICE FROM CARS WHERE NVL(ID_CUSTOMER,0) = 0 ";
    private static String selectCarsWithOwner = "SELECT ID, NAME, PRICE FROM CARS WHERE NVL(ID_CUSTOMER,0) = ?";
    private static String updateCarsAsBought = "UPDATE CARS SET ID_CUSTOMER = ? WHERE ID = ?";


    //OLD SQL
    private static String selectBooks = "SELECT * FROM BOOKS WHERE BOOKS.ID NOT IN (SELECT BOOKID FROM ORDERS WHERE USERNAME = ?) AND AUTHOR LIKE ?";
    private static String insertBook = "INSERT INTO BOOKS VALUES(?,?,?,?)";
    private static String insertUser = "INSERT INTO USERS VALUES(?,?)";
    private static String selectDeliveries = "SELECT * FROM DELIVERIES";
    private static String insertOrder = "INSERT INTO ORDERS VALUES(?,?,?)";
    private static String selectOpenOrders = "SELECT BOOKID, USERNAME, TITLE FROM ORDERS o JOIN BOOKS b on o.BOOKID = b.ID WHERE USERNAME = ? AND DELIVERED != 1";
    private static String selectPriceFromBooks = "SELECT SUM(PRICE) as deltotalprice FROM BOOKS WHERE ID IN ( ";
    private static String updateOrdersDelivered = "UPDATE ORDERS SET DELIVERED = 1 WHERE ID IN ( )";
    private static String insertDelivery = "INSERT INTO DELIVERIES VALUES (?,?,?)";


    private Connection conn = null;
    private static Database ourInstance = new Database();

    public static Database getInstance() {
        return ourInstance;
    }

    private Database() {
    }

    private void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.OracleDriver");
        conn = DriverManager.getConnection(connStr,user,passwd);
    }

    public boolean isUserValid(CustomerBean user) throws Exception {
        createConnection();
        boolean ret = false;
        PreparedStatement state = conn.prepareStatement(selectCustomer, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        state.setString(1,user.getUsername());
        state.setString(2,user.getPassword());
        ResultSet rs = state.executeQuery();

        while(rs.next())   {
            int nmbOfUsers = rs.getInt(1);
            if(nmbOfUsers == 1) {
                ret = true;
            }
        }
        conn.close();
        return ret;
    }

    public Vector<CarBean> getCarsWithoutOwner() throws Exception {
        createConnection();
        Vector<CarBean> vecCars = new Vector<CarBean>();
        PreparedStatement state = conn.prepareStatement(selectCarsWithoutOwner, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = state.executeQuery();
        while(rs.next())   {
            vecCars.add(new CarBean(rs.getInt("id"),rs.getString("name"),rs.getInt("price")));
        }
        conn.close();
        return vecCars;
    }

    public void buyCar(String[] carIds, CustomerBean user) throws SQLException, ClassNotFoundException {
        int id = getCustomerIdWithUsername(user);
        PreparedStatement state;
        createConnection();
        for(int i = 0; i < carIds.length; i++)
        {
            state = conn.prepareStatement(updateCarsAsBought, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            state.setInt(1, id);
            state.setInt(2, Integer.parseInt(carIds[i]));
            state.executeUpdate();
        }
        conn.close();
    }

    public Vector<CarBean> getCarsWithOwner(CustomerBean user) throws SQLException, ClassNotFoundException {
        createConnection();
        Vector<CarBean> vecCars = new Vector<CarBean>();
        PreparedStatement state = conn.prepareStatement(selectCarsWithOwner, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        state.setInt(1,getCustomerIdWithUsername(user));
        ResultSet rs = state.executeQuery();
        while(rs.next())   {
            vecCars.add(new CarBean(rs.getInt("id"),rs.getString("name"),rs.getInt("price")));
        }
        conn.close();
        return vecCars;
    }

    private int getCustomerIdWithUsername(CustomerBean cust) throws SQLException, ClassNotFoundException {
        int id = -1;
        createConnection();
        PreparedStatement state = conn.prepareStatement(selectCustomerId, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        state.setString(1,cust.getUsername());
        ResultSet rs = state.executeQuery();
        while(rs.next())   {
            id = rs.getInt("id");
        }
        conn.close();
        return id;
    }
}
