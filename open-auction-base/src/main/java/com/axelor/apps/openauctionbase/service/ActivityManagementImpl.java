package com.axelor.apps.openauctionbase.service;

import com.axelor.apps.base.db.Partner;
import com.axelor.apps.openauction.db.ActivityHeader;
import com.axelor.apps.openauction.db.ActivityLine;
import com.axelor.apps.openauction.db.AuctionHeader;
import com.axelor.apps.openauction.db.Lot;
import com.axelor.apps.openauction.db.LotTemplate;
import com.axelor.apps.openauction.db.MissionActivityLine;
import com.axelor.apps.openauction.db.MissionHeader;
import com.axelor.apps.openauction.db.MissionLine;
import com.axelor.apps.openauction.db.MissionServiceLine;
import com.axelor.apps.openauction.db.ServiceTemplate;
import com.axelor.apps.openauction.db.ServiceTemplateLine;
import com.axelor.apps.openauction.db.repo.MissionActivityLineRepository;
import com.axelor.apps.openauction.db.repo.MissionServiceLineRepository;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class ActivityManagementImpl implements ActivityManagement {
  MissionServiceLineRepository missionServiceLineRepository;
  MissionActivityLineRepository missionActivityLineRepository;

  @Inject
  public ActivityManagementImpl(
      MissionServiceLineRepository missionServiceLineRepository,
      MissionActivityLineRepository missionActivityLineRepository) {
    this.missionServiceLineRepository = missionServiceLineRepository;
    this.missionActivityLineRepository = missionActivityLineRepository;
  }

  @Override
  public void CreateActivityLines(
      ActivityHeader pActivityHeader,
      AuctionHeader pAuctionHeader,
      MissionHeader pMissionHeader,
      Lot pLotNo,
      LotTemplate pLotTemplate,
      Boolean pIsAuction,
      Boolean pIsActionOnly,
      Integer pTransactionLineNo) {

    MissionLine lMissionLine;
    Boolean lLineToTreat = false;
    Partner lcontact = null;
    for (ActivityLine line : pActivityHeader.getActivityLineList()) {
      if (!line.getTodoCondition().equals("")) {
        if (pActivityHeader.getApplicableOn() == "Header") lLineToTreat = true;
        if (pActivityHeader.getApplicableOn() == "Line")
          lLineToTreat =
              line.getLotTemplateFilter().equals(pLotTemplate)
                  || line.getLotTemplateFilter() == null
                  || line.getLotTemplateFilter().equals("");
        if (line.getToDoApplicableTo() == "Seller") lcontact = pMissionHeader.getMasterContactNo();
        else lcontact = null;
        if (lLineToTreat) {
          if (line.getServiceTemplateCode() != null)
            CreateMissionServiceLine(
                line,
                pAuctionHeader,
                pMissionHeader,
                pLotNo,
                lcontact,
                pIsAuction,
                pTransactionLineNo);
          // TODO gestion Interaction + Todo
        }
      }
    }
  }

  @Override
  @Transactional
  public void RemoveActivityLines(
      ActivityHeader pActivityHeader,
      AuctionHeader pAuctionHeader,
      MissionHeader pMissionHeader,
      Lot pLotNo,
      Integer pTransactionMineNo) {
    // TODO Auto-generated method stub
  }

  @Override
  @Transactional
  public void CreateTodo(
      ActivityHeader pActivityHeader,
      AuctionHeader pAuctionHeader,
      MissionHeader pMissionHeader,
      Lot pLotNo,
      Integer pTransactionMineNo,
      Partner pContact,
      Partner pSalesPerson) {
    // TODO Auto-generated method stub

  }

  @Override
  public void CreateActivityLineFromMission(
      ActivityHeader pActivityHeader,
      MissionHeader pMissionHeader,
      MissionLine pMissionLine,
      Boolean pActionOnly) {
    Lot lLot = pMissionLine.getLot();
    LotTemplate lLotTemplate = lLot == null ? null : lLot.getLotTemplateCode();
    CreateActivityLines(
        pActivityHeader,
        null,
        pMissionHeader,
        lLot,
        lLotTemplate,
        false,
        pActionOnly,
        pMissionLine.getLineNo());
  }

  @Transactional
  private void CreateMissionServiceLine(
      ActivityLine pActivityLine,
      AuctionHeader pAuctionHeader,
      MissionHeader pMissionHeader,
      Lot pLot,
      Partner pContact,
      Boolean pAuctionAct,
      Integer pTransactionLineNo) {
    ServiceTemplate missionServiceTemplate;
    MissionServiceLine lMissionServiceLine = new MissionServiceLine();
    MissionActivityLine lMissionActivityLine = new MissionActivityLine();
    if (pActivityLine.getServiceTemplateCode() == null) return;

    Boolean lValidateOK = pActivityLine.getTodoTemplateCode().equals("");
    missionServiceTemplate = pActivityLine.getServiceTemplateCode();
    for (ServiceTemplateLine missionServiceTemplateLine :
        missionServiceTemplate.getTemplateLineList()) {
      lMissionServiceLine.setEntryNo(0);
      lMissionServiceLine.setTransactionType(
          pAuctionAct
              ? MissionServiceLineRepository.TRANSACTIONTYPE_SELECT_VENTE
              : MissionServiceLineRepository.TRANSACTIONTYPE_SELECT_MISSION);
      lMissionServiceLine.setAuctionNo(pAuctionHeader);
      lMissionServiceLine.setMissionNo(pMissionHeader);
      lMissionServiceLine.setLotNo(pLot);
      lMissionServiceLine.setTransactionLineNo(pTransactionLineNo);
      lMissionServiceLine.setType(missionServiceTemplateLine.getType());
      // TODO setChargeableContactNo
      // lMissionServiceLine.setChargeableContactNo(pContact.getI);

      missionServiceLineRepository.save(lMissionServiceLine);
    }
  }
}
