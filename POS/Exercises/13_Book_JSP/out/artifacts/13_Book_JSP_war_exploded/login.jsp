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
    <title>Login</title>
    <Link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
<h2>Login</h2>
<form action="LoginServlet" method="get">
    <div id="main">
        username:<input type="text" name="user" size="25"><br/>
        password:<input type="password" name="password" size="25">
    </div>
    <p/>
    <input type="submit" name="btnLogin" value="Login">
    <c:if test="${isLoggedIn}">
        <input type="submit" name="btnListBooks" value="ListBooks">
    </c:if>
    <c:if test="${isAdmin}">
        <input type="submit" name="btnNewBook" value="NewBook">
        <input type="submit" name="btnNewUser" value ="NewUser">
        <input type="submit" name="btnNewDelivery" value="NewDelivery">
        <input type="submit" name="btnAllDeliveries" value="All Deliveries">
    </c:if>
</form>
<div id="message">
    <input type="text" class="classmessage" name="message" readonly size="70" value="${sessionMessage}">
</div>
</body>
</html>
