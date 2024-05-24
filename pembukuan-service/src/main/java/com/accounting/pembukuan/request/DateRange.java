package com.accounting.pembukuan.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRange {
    @JsonFormat(pattern = "yy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yy-MM-dd")
    private LocalDate endDate;
}
