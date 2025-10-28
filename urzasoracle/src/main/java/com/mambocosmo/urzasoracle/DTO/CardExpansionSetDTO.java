package com.mambocosmo.urzasoracle.DTO;

import lombok.Data;

@Data
public class CardExpansionSetDTO implements GenericDTO {

    private String code;
    private String name;
    private String release_date;
    private String icon_svg_uri;

}
