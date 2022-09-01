package com.axelor.apps.openauction.module;

import com.axelor.app.AxelorModule;
import com.axelor.apps.openauction.service.AuctionHeaderService;
import com.axelor.apps.openauction.service.AuctionHeaderServiceImpl;
import com.axelor.apps.openauction.service.LotInputJournalService;
import com.axelor.apps.openauction.service.LotInputJournalServiceImpl;

public class openauctionModule extends AxelorModule {

  @Override
  protected void configure() {
    bind(LotInputJournalService.class).to(LotInputJournalServiceImpl.class);
    bind(AuctionHeaderService.class).to(AuctionHeaderServiceImpl.class);
  }
}
