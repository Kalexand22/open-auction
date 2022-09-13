package com.axelor.apps.openauctionbase.service;

import com.axelor.apps.base.db.Partner;
import com.axelor.apps.openauction.db.ActivityHeader;
import com.axelor.apps.openauction.db.AuctionHeader;
import com.axelor.apps.openauction.db.Lot;
import com.axelor.apps.openauction.db.LotTemplate;
import com.axelor.apps.openauction.db.MissionHeader;
import com.axelor.apps.openauction.db.MissionLine;
import com.google.inject.persist.Transactional;

public class ActivityManagementImpl implements ActivityManagement {
    
    @Override
    @Transactional
    public void CreateActivityLines(ActivityHeader pActivityHeader, AuctionHeader pAuctionHeader,
            MissionHeader pMissionHeader, Lot pLotNo, LotTemplate pLotTemplate, Boolean pIsAuction,
            Boolean pIsActionOnly, Integer pTransactionMineNo) {
        // TODO Auto-generated method stub
    }

    @Override
    @Transactional
    public void RemoveActivityLines(ActivityHeader pActivityHeader, AuctionHeader pAuctionHeader,
            MissionHeader pMissionHeader, Lot pLotNo, Integer pTransactionMineNo) {
        // TODO Auto-generated method stub
    }

    @Override
    @Transactional
    public void CreateTodo(ActivityHeader pActivityHeader, AuctionHeader pAuctionHeader, MissionHeader pMissionHeader,
            Lot pLotNo, Integer pTransactionMineNo, Partner pContact, Partner pSalesPerson) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void CreateActivityLineFromMission(ActivityHeader pActivityHeader, MissionHeader pMissionHeader,
            MissionLine pMissionLine, Boolean pActionOnly) {
        // TODO Auto-generated method stub
        
    }
}