package com.axelor.apps.openauction.service;

import com.axelor.apps.openauction.db.AuctionHeader;
import com.axelor.apps.openauction.db.LotGrouping;
import com.axelor.apps.openauction.db.SiteAuction;

public interface AuctionHeaderService {

  public void publish(SiteAuction siteAuction);

  public void regroupe(LotGrouping lotGroup);

  public void initRegroupe(LotGrouping lotGroup, long auctionLineId);

  public void cancelRegroupe(LotGrouping lotGroup);

  public AuctionHeader templateChange(AuctionHeader auctionHeader, String title);
}
