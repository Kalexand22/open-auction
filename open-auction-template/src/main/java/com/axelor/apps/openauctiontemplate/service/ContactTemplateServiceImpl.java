package com.axelor.apps.openauctiontemplate.service;

import com.axelor.apps.base.db.Partner;
import com.axelor.apps.openauction.db.ContactTemplate;
import java.lang.invoke.MethodHandles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactTemplateServiceImpl implements ContactTemplateService {
  private final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  public Partner createContactFromTemplate(ContactTemplate contactTemplate) {
    log.debug("Creation d'un contact depuis un modèle");
    return null;
  }
}
