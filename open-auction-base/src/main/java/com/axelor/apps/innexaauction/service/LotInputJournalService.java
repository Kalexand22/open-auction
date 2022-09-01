package com.axelor.apps.openauction.service;

import com.axelor.apps.openauction.db.Lot;
import com.axelor.apps.openauction.db.MissionHeader;

public interface LotInputJournalService {

  public void createMissionLineFromLot(Lot lot, long currentMissionId);

  public void onNew(Lot lot, MissionHeader currentMission);

  public void onSave(Lot lot);

  public void createAuctionMissionHeader(MissionHeader currentMission);
}
