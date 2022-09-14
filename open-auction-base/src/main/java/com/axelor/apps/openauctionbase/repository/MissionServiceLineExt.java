package com.axelor.apps.openauctionbase.repository;

import com.axelor.apps.openauction.db.MissionServiceLine;
import java.math.BigDecimal;

public class MissionServiceLineExt extends MissionServiceLine {

  public MissionServiceLineExt() {
    super();
  }

  public void setChargeable(boolean chargeable) {
    if (this.getAcceptToInvoice() != chargeable) {
      if (this.getOutstandingAmount().compareTo(BigDecimal.ZERO) != 0) {
        this.setAcceptToInvoice(chargeable);
      } else {
        this.setAcceptToInvoice(false);
      }
    }
    super.setChargeable(chargeable);
  }

  public void setAcceptToInvoice(boolean acceptToInvoice) {
    if (acceptToInvoice) {
        //TODO TESTFIELD Chargeable
        this.setQtytoInvoice(this.getOutstandingQuantity());
    } else {
        this.setQtytoInvoice(BigDecimal.ZERO);
    }
    super.setAcceptToInvoice(acceptToInvoice);
  }
}
