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

import java.io.Serializable;

public class CustomerBean implements Serializable {

    public CustomerBean() {
    }

    public CustomerBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
