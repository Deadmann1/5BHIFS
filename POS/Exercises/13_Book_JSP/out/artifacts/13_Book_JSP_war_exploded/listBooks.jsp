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
    <title>List Books</title>
    <Link rel="stylesheet" type="text/css" href="css/listbooks.css">
</head>
<body>
<h2>Search for Book(s) for user '${user.name}'</h2>
<form action="LoginServlet" method="get">
    <div id="main">
        book-id:<input type="text" name="bookId" size="25"><br/>
        author:<input type="text" name="author" size="25">
    </div>
    <p/>
    <input type="submit" name="btnLogin" value="Search">
    <input type="submit" name="btnLogin" value="Back">
</form>
<div id="message">
    <input type="text" class="classmessage" name="message" readonly size="70" value="${sessionMessage}">
</div>
</body>
</html>
