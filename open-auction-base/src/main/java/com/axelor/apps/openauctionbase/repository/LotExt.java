package com.axelor.apps.openauctionbase.repository;

import javax.persistence.Entity;

import com.axelor.apps.openauction.db.Lot;
import com.axelor.apps.openauction.db.LotTemplate;

@Entity
public class LotExt extends Lot  {
    
    public void transferFields(LotTemplate pLotTemplate)
    {
        
    }
}
