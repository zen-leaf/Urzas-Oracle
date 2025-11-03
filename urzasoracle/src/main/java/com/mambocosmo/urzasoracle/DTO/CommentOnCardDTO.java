package com.mambocosmo.urzasoracle.DTO;

import java.sql.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class CommentOnCardDTO implements GenericDTO{
    
    private UUID id;
    private UUID cardId;
    private UUID userId;
    private String userName;
    private Date timeOfComment;
    private String comment;

}
