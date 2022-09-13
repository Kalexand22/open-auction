package com.axelor.apps.openauctionbase.module;

import com.axelor.app.AxelorModule;
import com.axelor.apps.openauctionbase.service.MissionTemplateManagement;
import com.axelor.apps.openauctionbase.service.MissionTemplateManagementImpl;

public class OpenAuctionBaseModule extends AxelorModule {

  @Override
  protected void configure() {
    bind(MissionTemplateManagement.class).to(MissionTemplateManagementImpl.class);
  }
}
