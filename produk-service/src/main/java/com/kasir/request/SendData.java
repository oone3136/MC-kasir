package com.kasir.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendData {
    private String deskripsi;
    private Long kredit;

}
