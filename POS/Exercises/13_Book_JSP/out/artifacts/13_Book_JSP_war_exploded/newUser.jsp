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
    <title>New User</title>
    <Link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>
<h2>New  User</h2>
<form action="NewUserServlet" method="get">
    <div id="main">
        name:<input type="text" name="username" size="25"><br/>
        password:<input type="password" name="password" size="25">
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
