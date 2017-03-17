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
import java.sql.Date;

public class DeliveryBean implements Serializable {
    public DeliveryBean() {
    }

    public DeliveryBean(String username, Date detdate, int deltotalprice) {
        this.username = username;
        this.detdate = detdate;
        this.deltotalprice = deltotalprice;
    }

    private String username;
    private Date detdate;
    private int deltotalprice;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDetdate() {
        return detdate;
    }

    public void setDetdate(Date detdate) {
        this.detdate = detdate;
    }

    public int getDeltotalprice() {
        return deltotalprice;
    }

    public void setDeltotalprice(int deltotalprice) {
        this.deltotalprice = deltotalprice;
    }
}
