package com.axelor.apps.openauctionbase.module;

import com.axelor.app.AxelorModule;
import com.axelor.apps.openauction.db.MissionServiceLine;
import com.axelor.apps.openauction.db.repo.MissionServiceLineRepository;
import com.axelor.apps.openauctionbase.repository.MissionServiceLineExt;
import com.axelor.apps.openauctionbase.repository.MissionServiceLineRepositoryExt;
import com.axelor.apps.openauctionbase.service.MissionTemplateManagement;
import com.axelor.apps.openauctionbase.service.MissionTemplateManagementImpl;

public class OpenAuctionBaseModule extends AxelorModule {

  @Override
  protected void configure() {
    bind(MissionTemplateManagement.class).to(MissionTemplateManagementImpl.class);
    bind(MissionServiceLineRepository.class).to(MissionServiceLineRepositoryExt.class);
    bind(MissionServiceLine.class).to(MissionServiceLineExt.class);
  }
}
