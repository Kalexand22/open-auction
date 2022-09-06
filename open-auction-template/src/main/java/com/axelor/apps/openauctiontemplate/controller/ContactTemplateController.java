package com.axelor.apps.openauctiontemplate.controller;

import com.axelor.apps.openauction.db.ContactTemplate;
import com.axelor.apps.openauction.db.repo.ContactTemplateRepository;
import com.axelor.apps.openauctiontemplate.service.ContactTemplateService;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.axelor.rpc.Context;
import java.util.HashMap;

public class ContactTemplateController {
  public void createContactFromTemplate(ActionRequest request, ActionResponse response) {

    Context context = request.getContext();

    HashMap<String, Object> contactTemplateMap =
        (HashMap<String, Object>) context.get("contactTemplate");
    ContactTemplate contactTemplate =
        Beans.get(ContactTemplateRepository.class)
            .find(Long.parseLong(contactTemplateMap.get("id").toString()));

    ContactTemplateService contactTemplateService = Beans.get(ContactTemplateService.class);
    contactTemplateService.createContactFromTemplate(contactTemplate);
  }
}
