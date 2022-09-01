package com.axelor.apps.openauction.controller;

import com.axelor.apps.openauction.db.AuctionHeader;
import com.axelor.apps.openauction.db.LotGrouping;
import com.axelor.apps.openauction.db.SiteAuction;
import com.axelor.apps.openauction.db.repo.AuctionHeaderRepository;
import com.axelor.apps.openauction.service.AuctionHeaderService;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import java.util.LinkedHashMap;

public class AuctionHeaderController {

  public void publish(ActionRequest request, ActionResponse response) {

    SiteAuction siteAuction = request.getContext().asType(SiteAuction.class);
    AuctionHeaderService auctionHeaderService = Beans.get(AuctionHeaderService.class);

    auctionHeaderService.publish(siteAuction);
  }

  public void regroupe(ActionRequest request, ActionResponse response) {

    LotGrouping lotGroup = request.getContext().asType(LotGrouping.class);
    AuctionHeaderService auctionHeaderService = Beans.get(AuctionHeaderService.class);
    auctionHeaderService.regroupe(lotGroup);
  }

  public void initRegroupe(ActionRequest request, ActionResponse response) {

    Integer auctionLineRecordId = (Integer) request.getContext().get("_auctionLineRecordId");
    LotGrouping lotGroup = request.getContext().asType(LotGrouping.class);
    AuctionHeaderService auctionHeaderService = Beans.get(AuctionHeaderService.class);

    auctionHeaderService.initRegroupe(lotGroup, auctionLineRecordId);
  }

  public void cancelRegroupe(ActionRequest request, ActionResponse response) {

    LotGrouping lotGroup = request.getContext().asType(LotGrouping.class);
    AuctionHeaderService auctionHeaderService = Beans.get(AuctionHeaderService.class);

    auctionHeaderService.cancelRegroupe(lotGroup);
  }

  public void templateChange(ActionRequest request, ActionResponse response) {

    LinkedHashMap<String, Object> auctionHeaderMap =
        (LinkedHashMap<String, Object>) request.getContext().get("auctionHeader");
    String description = (String) request.getContext().get("description");
    AuctionHeader auctionHeader =
        Beans.get(AuctionHeaderRepository.class)
            .find(((Integer) auctionHeaderMap.get("id")).longValue());
    AuctionHeaderService auctionHeaderService = Beans.get(AuctionHeaderService.class);

    auctionHeaderService.templateChange(auctionHeader, description);
    response.setCanClose(true);
  }
}
