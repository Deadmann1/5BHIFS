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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Menu for Car Booking</title>
    <Link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
<c:if test="${empty sessionID && isLoggedIn}">
    <c:redirect url="login.jsp"></c:redirect>
</c:if>
<h2>Login</h2>
<form action="MenuServlet" method="get">
    <div id="main">
        <c:if test="${isLoggedIn}">
            <input type="submit" name="btnOamtc" value="Öamtc">
            <input type="submit" name="btnBuy" value="Buy">
            <input type="submit" name="btnBought" value ="Bought">
            <input type="submit" name="btnBack" value="Back">
        </c:if>
    </div>
    <p/>

</form>
<div id="message">
    <input type="text" class="classmessage" name="message" readonly size="70" value="${sessionMessage}">
</div>
</body>
</html>
