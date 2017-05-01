/*Created by IntelliJ IDEA.
  Author: sammerm
  Copyright © 2017 by sammerm
  All rights reserved. 
  No part of this publication may be reproduced, distributed, or transmitted in any form or by any means, 
  including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of the publisher, 
  except in the case of brief quotations embodied in critical reviews and certain other noncommercial uses permitted by copyright law.
  For permission requests, write to the publisher.
*/
package pkgControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import pkgData.Order;
import pkgData.Product;

public class mainController {

    @FXML
    private Button btnRefresh;

    @FXML
    private ListView<Product> lvProducts;

    @FXML
    private ListView<Order> lvOrders;

    @FXML
    private Label lblMsg;
}


