package com.axelor.apps.openauctionbase.service;

import com.axelor.apps.base.db.Partner;
import com.axelor.apps.openauction.db.Lot;
import com.axelor.apps.openauction.db.LotInputJournal;
import com.axelor.apps.openauction.db.LotQuickInputJournal;
import com.axelor.apps.openauction.db.LotTemplate;
import com.axelor.apps.openauction.db.LotUnitofMeasure;
import com.axelor.apps.openauction.db.LotValueEntry;
import com.axelor.apps.openauction.db.MissionHeader;
import com.axelor.apps.openauction.db.MissionLine;
import com.axelor.apps.openauction.db.repo.LotRepository;
import com.axelor.apps.openauctionbase.repository.LotExt;
import com.axelor.inject.Beans;
import com.google.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;

public class LotTemplateManagementImpl implements LotTemplateManagement {
  /*
  * MissionLineManagement@1000000008 : Codeunit 8011367;
   ActivityManagement@1000000011 : Codeunit 8011374;
   ContactLotManagement@1000000005 : Codeunit 8011385;
   StatusManagement@1000000010 : Codeunit 8011380;
  */
  ActivityManagement activityManagement;
  ContactLotManagement contactLotManagement;
  MissionStatusManagement statusManagement;
  LotRepository lotRepository;

  @Inject
  public LotTemplateManagementImpl(LotRepository lotRepository) {
    activityManagement = Beans.get(ActivityManagement.class);
    contactLotManagement = Beans.get(ContactLotManagement.class);
    statusManagement = Beans.get(MissionStatusManagement.class);
    this.lotRepository = lotRepository;
  }

  @Override
  public void CreateLot(
      LotQuickInputJournal pLotQuickInputJournal, Lot pNewLot, Partner pContactNo) {
    // lLot@1000000002 : Record 8011404;
    LotExt lLot;
    LotTemplate lLotTemplate;
    LotTemplate lLotTemplate2 = new LotTemplate();
    LotUnitofMeasure lLotUnitofMeasure = new LotUnitofMeasure();
    LotUnitofMeasure lLotUnitofMeasure2 = new LotUnitofMeasure();
    LotValueEntry lLotValueEntry = new LotValueEntry();
    Partner lContact = new Partner();
    lLotTemplate = pLotQuickInputJournal.getLotTemplateCode();
    if (lLotTemplate == null) {
      return;
    }
    lLot = new LotExt();
    lLot.setLotTemplateCode(pLotQuickInputJournal.getLotTemplateCode());
    // TODO lLotTemplate.CheckBeforeUsage;
    lLot.transferFields(lLotTemplate);
    lLot.setDescription(pLotQuickInputJournal.getDescription());
    lLot.setSearchDescription(lLot.getDescription());
    lLot.setNo("");
    if (pLotQuickInputJournal.getLotNo() != null) {
      lLot.setNo(pLotQuickInputJournal.getLotNo().getNo());
    }
    lLot.setQuantity(pLotQuickInputJournal.getQuantity());
    lLot.setLotCategorie1Code1(pLotQuickInputJournal.getLotCategorie1Code());
    lLot.setLotCategorie2Code1(pLotQuickInputJournal.getLotCategorie2Code());
    lLot.setLotCategorie3Code1(pLotQuickInputJournal.getLotCategorie3Code());
    lLot.setLotCategorie4Code1(pLotQuickInputJournal.getLotCategorie4Code());
    lLot.setValuationAtBest(pLotQuickInputJournal.getValuationAtBest());
    lLot.setOriginCountryCode(pLotQuickInputJournal.getOriginCountryCode());
    lLot.setAuctionProductFamily(pLotQuickInputJournal.getAuctionProductFamily());
    lLot.setResponsibilityCenter(pLotQuickInputJournal.getResponsibilityCenter());

    lLot.setLawyerBusNo(pLotQuickInputJournal.getLawyerBusNo());
    lLot.setInvLocation(pLotQuickInputJournal.getInvLocation());
    lLot.setInvSubLocation(pLotQuickInputJournal.getInvSubLocation());
    lLot.setInvClassification(pLotQuickInputJournal.getInvClassification());
    lLot.setInvSubClassification(pLotQuickInputJournal.getInvSubClassification());
    lLot.setInvMissionNo(pLotQuickInputJournal.getInvMissionNo());
    lLot.setInvMissionLineNo(pLotQuickInputJournal.getInvMissionLineNo());
    lLot.setLeasingContactNo(pLotQuickInputJournal.getLeasingContactNo());
    lLot.setLeasingContractNo(pLotQuickInputJournal.getLeasingContractNo());
    lLot.setLotConditionCode(pLotQuickInputJournal.getLotConditionCode());
    lLot.setExternalNo(pLotQuickInputJournal.getExternalNo());
    lLot.setInterlocutor(pLotQuickInputJournal.getInterlocutor());
    //   //>> AP14 isat.sf

    //   IF lLot.Vehicle THEN
    //     lLot.VALIDATE("Lot Inventory Status", lLot."Lot Inventory Status"::"To Pick");
    if (lLot.getVehicle()) {
      lLot.setLotInventoryStatus(LotRepository.LOTINVENTORYSTATUS_SELECT_TO_PICK);
    }

    //   lLot."Date To Auction From" := pLotQuickInputJournal."Date To Auction From";   //AP15
    // isat.zw
    lLot.setDateToAuctionFrom(pLotQuickInputJournal.getDateToAuctionFrom());
    //   //error('++'+format(lLot));

    //   lLot.INSERT(TRUE);
    lotRepository.save(lLot);
    //   LotNo := lLot."No.";

    //   lLotUnitofMeasure.SETRANGE("No.",lLotTemplate.Code);
    //   IF lLotUnitofMeasure.FINDSET THEN BEGIN
    //     REPEAT
    //       lLotUnitofMeasure2."No." := lLot."No.";
    //       lLotUnitofMeasure2.Code := lLotUnitofMeasure.Code;
    //       lLotUnitofMeasure2."Qty. per Unit of Measure" := lLotUnitofMeasure."Qty. per Unit of
    // Measure";
    //       IF lLotUnitofMeasure2.INSERT(TRUE) THEN;  // debug 290110 isat.sf
    //     UNTIL lLotUnitofMeasure.NEXT = 0;
    //   END;

    //   ContactLotManagement.InsertSellerContactbyLot(pContactNo,lLot."No.");
    contactLotManagement.insertSellerContactbyLot(pContactNo, lLot);
  }

  @Override
  public void CreateLotFromContact(LotQuickInputJournal pLotQuickInputJournal, Partner pContact) {
    // TODO Auto-generated method stub

  }

  @Override
  public Lot CreateLotFromMission(
      LotQuickInputJournal pLotQuickInputJournal, MissionHeader pMissionHeader) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void PostLotQuickInputFromMission(
      LotQuickInputJournal pLotQuickInputJournal, MissionHeader pMissionHeader) {
    // TODO Auto-generated method stub

  }

  @Override
  public String CreateLotFromAuction(LotQuickInputJournal pLotQuickInputJournal) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void CreateLotTemplate(Lot pLotTemplate, String pTypeOrigin) {
    // TODO Auto-generated method stub

  }

  @Override
  public void CreateLotValueEntry(
      LotQuickInputJournal pLotQuickInputJournal,
      Lot pLot,
      MissionHeader pMissionHeader,
      MissionLine pMissionLine) {
    // TODO Auto-generated method stub

  }

  @Override
  public void CreateLotValueEntryReserve(
      LotQuickInputJournal pLotQuickInputJournal,
      Lot pLot,
      MissionHeader pMissionHeader,
      MissionLine pMissionLine) {
    // TODO Auto-generated method stub

  }

  @Override
  public void CreateLotValueEntryEstimate(
      LotQuickInputJournal pLotQuickInputJournal,
      Lot pLot,
      MissionHeader pMissionHeader,
      MissionLine pMissionLine) {
    // TODO Auto-generated method stub

  }

  @Override
  public void CreateLotValueEntryAuctEstim(
      LotQuickInputJournal pLotQuickInputJournal,
      Lot pLot,
      MissionHeader pMissionHeader,
      MissionLine pMissionLine) {
    // TODO Auto-generated method stub

  }

  @Override
  public void CreateLotValueEntryAppraisal(
      LotQuickInputJournal pLotQuickInputJournal,
      Lot pLot,
      MissionHeader pMissionHeader,
      MissionLine pMissionLine) {
    // TODO Auto-generated method stub

  }

  @Override
  public void CreateLotValueEntryQuotation(
      LotQuickInputJournal pLotQuickInputJournal,
      Lot pLot,
      MissionHeader pMissionHeader,
      MissionLine pMissionLine) {
    // TODO Auto-generated method stub

  }

  @Override
  public void CreateLotInventoryEntry(LotQuickInputJournal pLotQuickInputJournal, Lot pLot) {
    // TODO Auto-generated method stub

  }

  @Override
  public void CalcGrossReserveByNetReserve(LotInputJournal pLotInputJournal) {
    // TODO Auto-generated method stub

  }

  @Override
  public void CalcNetReserveByGrossReserve(LotInputJournal pLotInputJournal) {
    // TODO Auto-generated method stub

  }

  @Override
  public BigDecimal CalcCommissionWithBaseAmount(
      LotInputJournal pLotInputJournal, BigDecimal pBaseAmount) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String CreateLotFromJudicialMission(
      MissionLine pOriginMissionLine, LotQuickInputJournal pLotQuickInputJournal) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void PostLotQuickInputFromJuMission(
      LotQuickInputJournal pLotQuickInputJournal, MissionLine pOriginMissionLine) {
    // TODO Auto-generated method stub

  }

  @Override
  public void CreateLotInvValueEntry(MissionLine pMissionLine) {
    // TODO Auto-generated method stub

  }

  @Override
  public void CreateLotValueEntryFromLot(
      String pLotNo,
      Date pPostingDate,
      String pEntryType,
      BigDecimal pAmount,
      BigDecimal pMinAmount,
      BigDecimal pMaxAmount,
      Partner pContactNo) {
    // TODO Auto-generated method stub

  }

  @Override
  public void SetToRegistIntegrationLot(
      Boolean pToRegistIntegrationLot, Integer pRegistIntegrationEntryNo) {
    // TODO Auto-generated method stub

  }

  @Override
  public String GetLotNoCreated() {
    // TODO Auto-generated method stub
    return null;
  }
}
