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
import java.util.Vector;

public class Database {
    private static String connStr="jdbc:oracle:thin:@192.168.128.152:1521:ora11g";//Externe ip jdbc:oracle:thin:@212.152.179.117:1521:ora11g192.168.128.152
    private static String user = "d5b12";
    private static String passwd = "d5b";

    private static String selectBooks = "SELECT * FROM BOOKS WHERE BOOKS.ID NOT IN (SELECT BOOKID FROM ORDERS WHERE USERNAME = ?) AND AUTHOR LIKE ?";
    private static String selectUser = "SELECT COUNT(*) FROM USERS WHERE (USERNAME = ? AND PASSWORD = ?)";
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

    public Vector<BookBean> getBooksWithUser(UserBean user, BookBean book) throws Exception {
        createConnection();
        Vector<BookBean> vecBooks = new Vector<BookBean>();

        PreparedStatement state = conn.prepareStatement(selectBooks, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        state.setString(1,user.getUsername());
        state.setString(2,book.getAuthor());
        ResultSet rs = state.executeQuery();
        while(rs.next())   {
            vecBooks.add(new BookBean(rs.getInt("id"),rs.getString("title"),rs.getString("author"),rs.getInt("price")));
        }
        conn.close();
        return vecBooks;
    }

    public boolean isUserValid(UserBean user) throws Exception {
        createConnection();
        boolean ret = false;
        PreparedStatement state = conn.prepareStatement(selectUser, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
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

    public void addBook(BookBean book) throws SQLException, ClassNotFoundException {
        createConnection();
        PreparedStatement state = conn.prepareStatement(insertBook, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        state.setInt(1,book.getId());
        state.setString(2,book.getTitle());
        state.setString(3,book.getAuthor());
        state.setInt(4,book.getPrice());
        state.executeUpdate();
        conn.close();
    }

    public void addUser(UserBean user) throws SQLException, ClassNotFoundException {
        createConnection();
        PreparedStatement state = conn.prepareStatement(insertUser, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        state.setString(1,user.getUsername());
        state.setString(2,user.getPassword());
        state.executeUpdate();
        conn.close();
    }

    public Vector<DeliveryBean> getAllDeliveries() throws SQLException, ClassNotFoundException {
        createConnection();
        Vector<DeliveryBean> vecDeilveries = new Vector<DeliveryBean>();

        PreparedStatement state = conn.prepareStatement(selectDeliveries, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = state.executeQuery();
        while(rs.next())   {
            vecDeilveries.add(new DeliveryBean(rs.getString("username"),rs.getDate("deldate"),rs.getInt("deltotalprice")));
        }
        conn.close();
        return vecDeilveries;
    }

    public void addOrder(String[] bookIds, String username) throws SQLException, ClassNotFoundException {
        createConnection();
        for (String bId:bookIds) {
            int id =  Integer.parseInt(bId);
            PreparedStatement state = conn.prepareStatement(insertOrder, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            state.setString(1,username);
            state.setInt(2,id);
            state.setInt(3,0);
            state.executeUpdate();
        }
        conn.close();
    }

    public Vector<OrderBean> getOrderFromUser(String username) throws SQLException, ClassNotFoundException {
        createConnection();
        Vector<OrderBean> vecOrders = new Vector<OrderBean>();

        PreparedStatement state = conn.prepareStatement(selectOpenOrders, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        state.setString(1,username);
        ResultSet rs = state.executeQuery();
        while(rs.next())   {
            vecOrders.add(new OrderBean(rs.getInt("bookid"),rs.getString("username"),rs.getString("title")));
        }
        conn.close();
        return vecOrders;
    }

    public void addDelivery(String[] bookIds, String username, Date deldate) throws SQLException, ClassNotFoundException {
        createConnection();
        int deltotalprice = 0;
/*
        StringBuilder builder = new StringBuilder();

        for( int i = 0 ; i < bookIds.length; i++ ) {
            builder.append("?,");
        }

        String stmt = selectPriceFromBooks
                + builder.deleteCharAt( builder.length() -1 ).toString() + ")";
        PreparedStatement state = conn.prepareStatement(stmt, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        int index = 1;
        for( String s : bookIds ) {
            state.setInt(  index++, Integer.parseInt(s) ); // or whatever it applies
        }

        ResultSet rs = state.executeQuery();
        while(rs.next())   {
            deltotalprice = rs.getInt(1);
        }
        conn.close();

        createConnection();
        state = conn.prepareStatement(insertDelivery, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        state.setString(1,username);
        state.setDate(2,deldate);
        state.setInt(3,deltotalprice);
        state.executeUpdate();
        conn.close();



        createConnection();
        builder = new StringBuilder();

        for( int i = 0 ; i < bookIds.length; i++ ) {
            builder.append("?,");
        }

        stmt = updateOrdersDelivered
                + builder.deleteCharAt( builder.length() -1 ).toString() + ")";
        state = conn.prepareStatement(stmt, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        index = 1;
        for( String s : bookIds ) {
            state.setInt(  index++, Integer.parseInt(s) ); // or whatever it applies
        }

        state.executeUpdate();
        conn.close();
*/
    }
}
