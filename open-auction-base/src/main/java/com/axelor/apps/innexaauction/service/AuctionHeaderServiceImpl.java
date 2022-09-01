package com.axelor.apps.openauction.service;

import com.axelor.apps.openauction.db.AuctionHeader;
import com.axelor.apps.openauction.db.AuctionLine;
import com.axelor.apps.openauction.db.AuctionSiteUpdateHistory;
import com.axelor.apps.openauction.db.Lot;
import com.axelor.apps.openauction.db.LotGrouping;
import com.axelor.apps.openauction.db.LotGroupingLine;
import com.axelor.apps.openauction.db.PictureAttachement;
import com.axelor.apps.openauction.db.SiteAuction;
import com.axelor.apps.openauction.db.repo.AuctionHeaderRepository;
import com.axelor.apps.openauction.db.repo.AuctionLineRepository;
import com.axelor.apps.openauction.db.repo.AuctionSiteUpdateHistoryRepository;
import com.axelor.apps.openauction.db.repo.LotRepository;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuctionHeaderServiceImpl implements AuctionHeaderService {
  AuctionLineRepository auctionLineRepo;
  LotRepository lotRepo;
  AuctionSiteUpdateHistoryRepository auctionSiteUpdateHistoryRepo;
  AuctionHeaderRepository auctionHeaderRepo;

  @Inject
  public AuctionHeaderServiceImpl(
      LotRepository lotRepo,
      AuctionSiteUpdateHistoryRepository auctionSiteUpdateHistoryRepo,
      AuctionLineRepository auctionLineRepo,
      AuctionHeaderRepository auctionHeaderRepo) {

    this.auctionLineRepo = auctionLineRepo;
    this.lotRepo = lotRepo;
    this.auctionSiteUpdateHistoryRepo = auctionSiteUpdateHistoryRepo;
    this.auctionHeaderRepo = auctionHeaderRepo;
  }

  @Override
  @Transactional
  public void publish(SiteAuction siteAuction) {
    AuctionSiteUpdateHistory auctionSiteUpdateHistory = new AuctionSiteUpdateHistory();
    auctionSiteUpdateHistory.setAuctionNo(siteAuction.getAuctionNo());
    int nbLot = 0;
    int nbLotphare = 0;
    int nbPhoto = 0;
    for (AuctionLine line : siteAuction.getAuctionNo().getAuctionLineList()) {
      nbLot++;
      if (line.getSourceLotNo().getLighthouseLot()) {
        nbLotphare++;
      }
      for (PictureAttachement pict : line.getSourceLotNo().getPictureList()) {
        nbPhoto++;
      }
    }
    auctionSiteUpdateHistory.setComment1(siteAuction.getComment1());
    auctionSiteUpdateHistory.setCodeWebSite(siteAuction.getCodeWebSite());
    auctionSiteUpdateHistory.setDatePublication(LocalDateTime.now());
    auctionSiteUpdateHistory.setLotNumber(nbLot);
    auctionSiteUpdateHistory.setLighthouseLotNumber(nbLotphare);
    auctionSiteUpdateHistory.setPhotoNumber(nbPhoto);
    auctionSiteUpdateHistoryRepo.save(auctionSiteUpdateHistory);
  }

  @Override
  @Transactional
  public void initRegroupe(LotGrouping lotGroup, long auctionLineId) {

    AuctionLine auctionLine = auctionLineRepo.find(auctionLineId);
    Lot lot = lotRepo.copy(auctionLine.getSourceLotNo(), false);
    lotRepo.save(lot);
    lotGroup.setLotGroupNo(lot);
    lotGroup.setLotDescription(lot.getDescription());
    LotGroupingLine lotGroupLine = new LotGroupingLine();
    lotGroupLine.setSubLotNo(auctionLine.getSourceLotNo());
    List<LotGroupingLine> list = new ArrayList<>();
    list.add(lotGroupLine);
    lotGroup.setLotGroupLineList(list);
    // lotGroupRepo.save(lotGroup);
    // lotGroupLineRepo.save(lotGroupLine);
  }

  @Override
  @Transactional
  public void regroupe(LotGrouping lotGroup) {

    int passingOrder = 0;
    AuctionHeader auctionHeader = lotGroup.getAuctionHeader();
    AuctionLine auctionLine = new AuctionLine();

    AuctionLine lineAuctionLine = lotGroup.getOriginAuctionLine();

    passingOrder = lineAuctionLine.getPassingOrderNo();
    lineAuctionLine.setAuctionNo(null);
    auctionLineRepo.save(lineAuctionLine);

    for (LotGroupingLine lotGroupLine : lotGroup.getLotGroupLineList()) {
      AuctionLine lineAuctionLineb = lotGroupLine.getAuctionLine();
      lineAuctionLineb.setAuctionNo(null);
      auctionLineRepo.save(lineAuctionLineb);
    }

    Lot lot = new Lot();
    lot.setCurrentMissionNo(lotGroup.getOriginLot().getCurrentMissionNo());
    lot.setMissionNo(lotGroup.getOriginLot().getMissionNo());
    lot.setDescription(lotGroup.getLotDescription());
    lot.setCatalogDescription(lotGroup.getLotDescription());
    lot.setCurrentAuctionNo(lotGroup.getAuctionNo());
    lotRepo.save(lot);

    auctionLine.setSourceLotNo(lot);
    auctionLine.setAuctionNo(auctionHeader);
    auctionLine.setLotDescription(lotGroup.getLotDescription());
    auctionLine.setLineNo(passingOrder);
    auctionLine.setPassingOrderNo(passingOrder);
    auctionLine.setMissionLineNo(lotGroup.getOriginLot().getMissionLine());
    auctionLine.setMissionNo(lotGroup.getOriginLot().getMissionNo());
    auctionLine.setLotType("Lot2");
    auctionLine.setGroupingAuthorization(false);
    auctionLineRepo.save(auctionLine);
  }

  @Override
  @Transactional
  public void cancelRegroupe(LotGrouping lotGroup) {
    AuctionHeader auctionHeader = lotGroup.getAuctionHeader();

    for (AuctionLine lineAuctionLine : auctionLineRepo.all().fetch()) {
      if (lineAuctionLine.getGroupingAuthorization() == false
          && lineAuctionLine.getAuctionNo() == auctionHeader) {
        lineAuctionLine.setAuctionNo(null);
        auctionLineRepo.save(lineAuctionLine);
      }
    }

    for (LotGroupingLine lotGroupLine : lotGroup.getLotGroupLineList()) {
      AuctionLine lineAuctionLineb = lotGroupLine.getAuctionLine();
      lineAuctionLineb.setAuctionNo(auctionHeader);
      auctionLineRepo.save(lineAuctionLineb);
    }

    AuctionLine originAuctionLine = lotGroup.getOriginAuctionLine();
    originAuctionLine.setAuctionNo(auctionHeader);
    auctionLineRepo.save(originAuctionLine);
  }

  @Override
  @Transactional
  public AuctionHeader templateChange(AuctionHeader auctionTemplate, String title) {

    AuctionHeader auctionHeader = auctionHeaderRepo.copy(auctionTemplate, true);
    auctionHeader.setAuctionTemplate(auctionTemplate);
    auctionHeader.setDescription(title);
    auctionHeader.setTemplate(false);
    auctionHeaderRepo.save(auctionHeader);
    return auctionHeader;
  }
}
