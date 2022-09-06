package com.axelor.apps.openauctiontemplate.service;

import com.axelor.apps.openauction.db.ContactTemplate;
import com.axelor.apps.base.db.Partner;

public interface ContactTemplateService {
    
    public Partner createContactFromTemplate(ContactTemplate contactTemplate);
}
