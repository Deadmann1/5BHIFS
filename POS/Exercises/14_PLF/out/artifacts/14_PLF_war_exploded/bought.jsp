<%--
  Created by IntelliJ IDEA.
  Author: sammerm
  Copyright © 2017 by sammerm
  All rights reserved. 
  No part of this publication may be reproduced, distributed, or transmitted in any form or by any means, 
  including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the publisher, 
  except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
  For permission requests, write to the publisher.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Form for Bought Cars of Car Booking</title>
    <Link rel="stylesheet" type="text/css" href="css/listbooks.css">
</head>
<body>
<c:if test="${empty sessionID || empty sessionCustomer}">
    <c:redirect url="login.jsp"></c:redirect>
</c:if>
<h2>Form for Bought Cars of Car Booking</h2>
<form action="BoughtServlet" method="get">
    <div id="main">
        customer:<input readonly type="text" name="username" value="${sessionCustomer.username}" size="25">
        <p/>
        <table>
            <c:forEach var="car" items="${boughtCarList}">
                    <tr><td><c:out value ="${car.name}"/></td>
                    <td><c:out value ="${car.price}"/></td></tr>
            </c:forEach>
        </table>
        <p/>
    </div>
    <p/>
    <input type="submit" name="btnBack" value="Back">
</form>
<div id="message">
    <input type="text" class="classmessage" name="message" readonly size="70" value="${sessionMessage}">
</div>
</body>
</html>
