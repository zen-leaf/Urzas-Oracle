package com.mambocosmo.urzasoracle.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeckLegalityReportDTO {

    private String status;

    private String suspect;
    private String reason;

    public DeckLegalityReportDTO(String status) {
        this.status = status;
    }
}
