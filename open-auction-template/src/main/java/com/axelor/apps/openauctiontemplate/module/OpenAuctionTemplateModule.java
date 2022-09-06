package com.axelor.apps.openauctiontemplate.module;

import com.axelor.apps.openauction.service.ContactTemplateService;
import com.axelor.apps.openauction.service.ContactTemplateServiceImpl;

public class OpenAuctionTemplateModule extends AxelorModule {
    @Override
  protected void configure() {
    bind(ContactTemplateService.class).to(ContactTemplateServiceImpl.class);
  }
}
