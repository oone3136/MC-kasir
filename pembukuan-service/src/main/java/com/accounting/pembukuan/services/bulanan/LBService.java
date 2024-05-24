package com.accounting.pembukuan.services.bulanan;

import com.accounting.pembukuan.entity.LB;
import com.accounting.pembukuan.entity.LabaRugi;
import com.accounting.pembukuan.entity.Status;
import com.accounting.pembukuan.repository.LBRepository;
import com.accounting.pembukuan.request.DateRange;
import com.accounting.pembukuan.request.LBRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LBService {

    private final LBRepository repository;
    private static final DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("yy-MM");
    private static final SecureRandom rendom = new SecureRandom();
    private static final int randomNumber = 4;

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    public String generateId(){
        String datePart = LocalDate.now().format(dateFormater);
        String randomPart = String.format("%04d",rendom.nextInt(randomNumber));
        return datePart + "-" +randomPart;
    }

    public ResponseEntity<String> createLaporanHarian(LBRequest request) {
        log.info("start creat laporan");
        log.info("request " +  request);
        try {
            String desc = request.getDeskripsi();
            if (desc == null || desc.isEmpty()){
                throw new RuntimeException("deskripsi tidak boleh kosong");
            }
            log.info("deskripsi" + desc);
            LB lh1 = new LB();
            lh1.setId(generateId());
            lh1.setTglTrx(LocalDate.now());
            lh1.setKeterangan(request.getKeterangan());
            lh1.setKredit(request.getKredit());
            lh1.setPending(request.getPending());

            if (request.getDebit() != null && request.getDebit() > 0) {
                lh1.setStatusPembayaran(Status.debit);
                lh1.setDebit(request.getDebit());
                lh1.setDeskripsi(desc);

            } else if (request.getKredit() != null && request.getKredit() > 0) {
                lh1.setStatusPembayaran(Status.kredit);
                lh1.setDebit(request.getDebit());
                lh1.setDeskripsi(desc);
            } else if (request.getPending() != null && request.getPending() > 0) {
                lh1.setStatusPembayaran(Status.ngutang);

            }
            log.info("save" +lh1);
            repository.save(lh1);
            return ResponseEntity.ok("berhasil mencatat laporan");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    public ResponseEntity<Map<String, Object>>aruskasDebit(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> items = new HashMap<>();
        List<LB> lbList = repository.findByStatusDebit(startDate, endDate);
        if (lbList.isEmpty()) {
            throw new RuntimeException("data kosong");
        }else {
            items.put("items", lbList);
            items.put("Total Data", lbList.size());
            return ResponseEntity.ok(items);
        }
    }
    public ResponseEntity<Map<String, Object>>aruskasKredit(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> items = new HashMap<>();
        List<LB> lbList = repository.findByStatusKredit(startDate, endDate);
        if (lbList.isEmpty()) {
            throw new RuntimeException("data kosong");
        }else {
            items.put("items", lbList);
            items.put("Total Data", lbList.size());
            return ResponseEntity.ok(items);
        }
    }

}
