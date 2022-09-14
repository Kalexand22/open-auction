package com.axelor.apps.openauctionbase.repository;

import java.time.LocalDate;
import java.util.Calendar;

import org.apache.poi.ss.formula.functions.Today;

import com.axelor.apps.openauction.db.MissionServiceLine;
import com.axelor.apps.openauction.db.repo.MissionServiceLineRepository;

public class MissionServiceLineRepositoryExt extends MissionServiceLineRepository {
    public MissionServiceLineRepositoryExt() {
        super();
    }
    public MissionServiceLine save(MissionServiceLine entity) {
        MissionServiceLine lMissServLine = null;
        MissionServiceLine lMissServLine2;
        if(entity.getId()!=0)
        {
            if (!entity.getAuctionBid())
            {
                if(entity.getAuctionNo() != null )
                {
                    entity.setTransactionType(MissionServiceLineRepository.OPENAUCTION_TRANSACTIONTYPE_SELECT_VENTE);
                }
                else
                {
                    entity.setTransactionType(MissionServiceLineRepository.OPENAUCTION_TRANSACTIONTYPE_SELECT_MISSION);
                }
            }
            if(entity.getDocumentNo() == null  || entity.getDocumentNo() == 0 )
            {
                
                entity.setDocumentNo(entity.getTransactionType()==MissionServiceLineRepository.OPENAUCTION_TRANSACTIONTYPE_SELECT_VENTE
                    ?entity.getAuctionNo().getId()
                        :entity.getMissionNo().getId());
            }
            if(entity.getEntryNo() == null  || entity.getEntryNo() == 0 )
            {
                /*
                 * //gestion de n° sequence de la clé
                    IF "Entry No." = 0 THEN BEGIN
                        lMissServLine2.LOCKTABLE;
                        lMissServLine2.SETRANGE("Document No.", "Document No.");
                        IF lMissServLine2.FINDLAST THEN
                        "Entry No." := lMissServLine2."Entry No." + 1
                        ELSE
                        "Entry No." := 1;
                    END;
                    //>>ap46 isat.zw
                 */
                lMissServLine2 = all().filter("self.documentNo = ?1", entity.getDocumentNo()).order("-entryNo").fetchOne();
                entity.setEntryNo(lMissServLine2.getEntryNo()+1);
            }
            if(entity.getPriceDate() == null )
            {
                //TODO gestion de la workdate
                entity.setPriceDate(LocalDate.now());
            }
            if(entity.getBuyerFiscalPosition() == null || entity.getSellerFiscalPosition() == null)
                lMissServLine = FindBidLine(entity);
            if(lMissServLine != null )
            {
                if(entity.getBuyerFiscalPosition() == null )
                {
                    entity.setBuyerFiscalPosition(lMissServLine.getBuyerFiscalPosition());
                }
                if(entity.getSellerFiscalPosition() == null )
                {
                    entity.setSellerFiscalPosition(lMissServLine.getSellerFiscalPosition());
                }
            }

            entity.setOutstandingAmount(entity.getAmountInclVAT());
            
        }
        //TODO IsChargeable; TouchRecord(FALSE); LawyerAnalysisMgt.SetSynchroRecord(Rec."Mission No.",'',Rec."Entry No.",8011449,0); 
        
        
        return super.save(entity);
    }

    private MissionServiceLine FindBidLine(MissionServiceLine rec ) {
        MissionServiceLine lMissServLine = new MissionServiceLine();
        return lMissServLine;
    }
}
