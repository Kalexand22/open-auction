package com.axelor.apps.openauctionbase.service;

import com.axelor.apps.base.db.Partner;
import com.axelor.apps.openauction.db.MissionHeader;
import com.axelor.apps.openauction.db.MissionTemplate;
import com.google.inject.persist.Transactional;

public class MissionTemplateManagementImpl implements MissionTemplateManagement {

  MissionTemplate MissionTemplate;
  Boolean HideTemplate;
  Boolean AuctionFromInv;
  Boolean SkipActivity;

  @Override
  @Transactional
  public MissionHeader CreateMissionFromMission(
      MissionHeader pMissionHeader,
      MissionTemplate pMissionTemplate,
      Boolean pJudicialFilter,
      String pLawyerBusNo) {
    return null;
  }

  @Override
  @Transactional
  public Boolean CreateMissionFromContact(Partner pContact) {
    return null;
  }

  @Override
  public void GetCustomerNo() {}

  @Override
  public void SetAuctionFromInv(Boolean pTrue) {
    AuctionFromInv = pTrue;
  }

  @Override
  public void SetSkipActivityCreation(Boolean pSkipActivity) {
    SkipActivity = pSkipActivity;
  }

  @Override
  @Transactional
  public void CreateActivity(MissionHeader pMissionHeader, String pActivityCodeToHeader) {
    // TODO ActivityManagement
  }
}
