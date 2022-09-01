package com.axelor.apps.openauction.controller;

import com.axelor.apps.openauction.db.Lot;
import com.axelor.apps.openauction.db.MissionHeader;
import com.axelor.apps.openauction.service.LotInputJournalService;
import com.axelor.inject.Beans;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class LotInputJournalController {

  public void validate(ActionRequest request, ActionResponse response) {

    Integer missionHeaderId = (Integer) request.getContext().get("_missionRecordId");
    Lot lot = request.getContext().asType(Lot.class);
    LotInputJournalService lotInputJournalService = Beans.get(LotInputJournalService.class);

    // System.out.println("LotInputJournalController.validate");
    lotInputJournalService.createMissionLineFromLot(lot, missionHeaderId);
  }

  public void onNew(ActionRequest request, ActionResponse response) {

    Object missionHeader = request.getContext().get("_missionRecord");
    Lot lot = request.getContext().asType(Lot.class);
    LotInputJournalService lotInputJournalService = Beans.get(LotInputJournalService.class);

    System.out.println(missionHeader.toString());
    // lotInputJournalService.createLotFromJournalInput(lot);

  }

  public void onSave(ActionRequest request, ActionResponse response) {

    Lot lot = request.getContext().asType(Lot.class);
    LotInputJournalService lotInputJournalService = Beans.get(LotInputJournalService.class);
    lotInputJournalService.onSave(lot);
  }

  public void createAuctionMission(ActionRequest request, ActionResponse response) {

    MissionHeader missionHeader = request.getContext().asType(MissionHeader.class);
    LotInputJournalService lotInputJournalService = Beans.get(LotInputJournalService.class);

    System.out.println("LotInputJournalController.createAuctionMission");
    lotInputJournalService.createAuctionMissionHeader(missionHeader);
  }
}
