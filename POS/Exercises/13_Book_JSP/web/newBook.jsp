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
<html>
<head>
    <title>New Book</title>
    <Link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
<h2>New  Book</h2>
<form action="NewBookServlet" method="get">
    <div id="main">
        id:<input type="text" name="id" size="5"><br/>
        author:<input type="text" name="author" size="25"><br/>
        title:<input type="text" name="title" size="25"><br/>
        price (10-200):<input type="number" name="price" size="25">
    </div>
    <p/>
    <input type="submit" name="btnInsert" value="Insert">
    <input type="submit" name="btnBack" value="Back">
</form>
<div id="message">
    <input type="text" class="classmessage" name="message" readonly size="70" value="${sessionMessage}">
</div>
</body>
</html>
