package com.axelor.apps.openauctiontemplate.controller;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import java.util.LinkedHashMap;
import com.axelor.apps.openauction.service.ContactTemplateService;

public class ContactTemplateController {
    public void createContactFromTemplate(ActionRequest request, ActionResponse response) {


        Context context = request.getContext();

        HashMap<String, Object> moveTemplateTypeMap =
            (HashMap<String, Object>) context.get("moveTemplateType");
        MoveTemplateType moveTemplateType =
            Beans.get(MoveTemplateTypeRepository.class)
                .find(Long.parseLong(moveTemplateTypeMap.get("id").toString()));

        LotGrouping lotGroup = request.getContext().asType(LotGrouping.class);
        AuctionHeaderService auctionHeaderService = Beans.get(AuctionHeaderService.class);
        auctionHeaderService.regroupe(lotGroup);

    }
}
