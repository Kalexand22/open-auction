package com.axelor.apps.openauctionbase.repository;

import com.axelor.apps.openauction.db.Lot;
import com.axelor.apps.openauction.db.LotTemplate;
import javax.persistence.Entity;

@Entity
public class LotExt extends Lot {

  public void transferFields(LotTemplate pLotTemplate) {}
}
