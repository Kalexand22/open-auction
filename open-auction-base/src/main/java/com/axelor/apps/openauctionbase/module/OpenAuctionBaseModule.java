package com.axelor.apps.openauctionbase.module;

import com.axelor.app.AxelorModule;
import com.axelor.apps.openauction.db.MissionServiceLine;
import com.axelor.apps.openauction.db.repo.MissionServiceLineRepository;
import com.axelor.apps.openauctionbase.repository.MissionServiceLineExt;
import com.axelor.apps.openauctionbase.repository.MissionServiceLineRepositoryExt;
import com.axelor.apps.openauctionbase.service.ActivityManagement;
import com.axelor.apps.openauctionbase.service.ActivityManagementImpl;
import com.axelor.apps.openauctionbase.service.AuctionManagement;
import com.axelor.apps.openauctionbase.service.AuctionManagementImpl;
import com.axelor.apps.openauctionbase.service.MissionManagement;
import com.axelor.apps.openauctionbase.service.MissionManagementImpl;
import com.axelor.apps.openauctionbase.service.MissionTemplateManagement;
import com.axelor.apps.openauctionbase.service.MissionTemplateManagementImpl;

public class OpenAuctionBaseModule extends AxelorModule {

  @Override
  protected void configure() {

    bind(MissionServiceLineRepository.class).to(MissionServiceLineRepositoryExt.class);
    bind(MissionServiceLine.class).to(MissionServiceLineExt.class);
    bind(MissionTemplateManagement.class).to(MissionTemplateManagementImpl.class);
    bind(ActivityManagement.class).to(ActivityManagementImpl.class);
    bind(AuctionManagement.class).to(AuctionManagementImpl.class);
    bind(MissionManagement.class).to(MissionManagementImpl.class);
  }
}
