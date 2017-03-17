<%--
  Created by IntelliJ IDEA.
  Author: Manuel Sammer
  Copyright © 2017 by Manuel Sammer
  All rights reserved. 
  No part of this publication may be reproduced, distributed, or transmitted in any form or by any means, 
  including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the publisher, 
  except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
  For permission requests, write to the publisher.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>List Order Table</title>
    <Link rel="stylesheet" type="text/css" href="css/listbooks.css">
</head>
<body>
<c:if test="${empty sessionID}">
    <c:redirect url="login.jsp"></c:redirect>
</c:if>
<h2>List of orders</h2>
<form action="OrderListServlet" method="get">
    <div id="main">
           user:<input type="text" name="username" size="25">
            <br/>date-of-delivery:<input type="date" name="deldate" value="${sessionDelivery.deldate}" size="25">
        <p/>
        <table>
            <c:forEach var="order" items="${listOrders}">
                <tr><td><input type="checkbox" name="ckorder" value="${order.bookid}"></td>
                    <td><c:out value ="${order.username}"/></td>
                    <td><c:out value ="${order.bookid}"/></td>
                    <td><c:out value ="${order.title}"/></td></tr>
            </c:forEach>
        </table>
        <p/>
    </div>
    <p/>
    <input type="submit" name="btnList" value="List">
    <input type="submit" name="btnNewDelivery" value="New Delivery">
    <input type="submit" name="btnBack" value="Back">
</form>
<div id="message">
    <input type="text" class="classmessage" name="message" readonly size="70" value="${sessionMessage}">
</div>
</body>
</html>
