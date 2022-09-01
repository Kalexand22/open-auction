package com.axelor.apps.openauction.service;

import com.axelor.apps.openauction.db.AuctionLine;
import com.axelor.apps.openauction.db.Lot;
import com.axelor.apps.openauction.db.MissionHeader;
import com.axelor.apps.openauction.db.MissionLine;
import com.axelor.apps.openauction.db.PictureAttachement;
import com.axelor.apps.openauction.db.repo.AuctionLineRepository;
import com.axelor.apps.openauction.db.repo.LotRepository;
import com.axelor.apps.openauction.db.repo.MissionHeaderRepository;
import com.axelor.apps.openauction.db.repo.MissionLineRepository;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import java.util.ArrayList;
import java.util.List;

public class LotInputJournalServiceImpl implements LotInputJournalService {
  LotRepository lotRepo;
  MissionLineRepository missionLineRepo;
  MissionHeaderRepository missionHeaderRepo;
  AuctionLineRepository auctionLineRepo;
  int cpt;

  @Inject
  public LotInputJournalServiceImpl(
      LotRepository lotRepo,
      MissionLineRepository missionLineRepo,
      MissionHeaderRepository missionHeaderRepo,
      AuctionLineRepository auctionLineRepo) {
    this.lotRepo = lotRepo;
    this.missionLineRepo = missionLineRepo;
    this.missionHeaderRepo = missionHeaderRepo;
    this.auctionLineRepo = auctionLineRepo;
  }

  @Override
  @Transactional
  public void createMissionLineFromLot(Lot lot, long currentMissionId) {

    System.out.println(currentMissionId);
    MissionHeader currentMission = missionHeaderRepo.find(currentMissionId);
    cpt = 0;
    for (Lot lotTmp : currentMission.getLotList()) {

      if (lotTmp.getMissionLine() == null) {
        cpt++;
        this.createMissionLine(lotTmp, currentMission);
      }
      /*
      System.out.println("lot");
      System.out.println(lotTmp.getId());
      System.out.println("missionLine");
      System.out.println(lotTmp.getMissionLine().getId());
      */
    }
  }

  @Transactional
  private void createMissionLine(Lot lot, MissionHeader currentMission) {

    MissionLine missionLine = new MissionLine();

    int lenght = currentMission.getMissionLineList().toArray().length;
    missionLine.setLineNo(lenght + cpt);
    missionLine.setLotNoMission(lenght + cpt);
    missionLine.setMissionNo(currentMission);
    missionLine.setAuctionMission(false);
    missionLine.setPriority("Normal2");
    missionLine.setLotStatus("Initial0");
    missionLine.setBidInvoicingStatus("NotInvoiced0");
    missionLine.setMissionLotDocumentStatus("Initial0");
    missionLine.setMissionLotOpStatus("Initial0");
    missionLine.setLot(lot);
    System.out.println("missionLine : " + missionLine.toString());
    missionLineRepo.save(missionLine);
    lot.setMissionLine(missionLine);
  }

  @Override
  public void onNew(Lot lot, MissionHeader currentMission) {
    lot.setCurrentMissionNo(currentMission);
  }

  @Override
  @Transactional
  public void createAuctionMissionHeader(MissionHeader currentMission) {

    MissionHeader auctionHeaderMission = missionHeaderRepo.copy(currentMission, true);
    auctionHeaderMission.setAuctionMission(true);

    List<MissionLine> missionLineList = auctionHeaderMission.getMissionLineList();
    List<MissionLine> toRemove = new ArrayList<>();
    List<Lot> lotList = new ArrayList<>();
    // List<Lot> toRemoveLot = new ArrayList<>();
    int index = 0;
    for (MissionLine line : missionLineList) {
      Lot lot = currentMission.getLotList().get(index);
      Lot newLot = lotRepo.copy(lot, true);
      newLot.setResponsibilityCenter(currentMission.getResponsibilityCenter());
      if (line.getCanceled()) {
        toRemove.add(line);
        // toRemoveLot.add(lot);
      } else {
        line.setAuctionMission(true);
        line.setLot(newLot);
        newLot.setCurrentMissionNo(auctionHeaderMission);
        lotList.add(newLot);
      }
      index++;
    }
    // lotList.removeAll(toRemoveLot);
    missionLineList.removeAll(toRemove);
    auctionHeaderMission.setLotList(lotList);
    auctionHeaderMission.setMissionLineList(missionLineList);

    missionHeaderRepo.save(auctionHeaderMission);
  }

  @Override
  @Transactional
  public void onSave(Lot lot) {

    if (lot.getCurrentAuctionNo() != null && lot.getAuctionLine() == null) {

      int noLot = lot.getCurrentAuctionNo().getAuctionLineList().toArray().length;
      AuctionLine auctionLine = new AuctionLine();
      auctionLine.setSourceLotNo(lot);
      auctionLine.setAuctionNo(lot.getCurrentAuctionNo());
      auctionLine.setLotDescription(lot.getDescription());
      auctionLine.setLineNo(noLot + 1);
      auctionLine.setPassingOrderNo(noLot + 1);
      auctionLine.setMissionLineNo(lot.getMissionLine());
      auctionLine.setMissionNo(lot.getCurrentMissionNo());
      auctionLine.setLotType("Lot2");
      auctionLine.setGroupingAuthorization(true);

      auctionLineRepo.save(auctionLine);
      lot.setAuctionLine(auctionLine);
    }
    for (PictureAttachement pictureAttchemenet : lot.getPictureList()) {
      if (pictureAttchemenet.getMain()) {
        lot.setMainPicture(pictureAttchemenet.getMainPicture());
        break;
      }
    }
  }
}
