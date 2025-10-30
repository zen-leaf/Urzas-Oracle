package com.mambocosmo.urzasoracle.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.CardCollectionDTO;
import com.mambocosmo.urzasoracle.converters.CardCollectionConverter;
import com.mambocosmo.urzasoracle.entities.CardCollection;
import com.mambocosmo.urzasoracle.repositories.CardCollectionRepository;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Service
@Data
@EqualsAndHashCode(callSuper=true)
public class CardCollectionService
        extends GenericService<CardCollection, CardCollectionDTO, CardCollectionConverter, CardCollectionRepository> {

    @Override
    public CardCollection construct(Map<String, String> fromData) {
        CardCollection cc = new CardCollection();
        try{
            cc = getCONTEXT().getBean(CardCollection.class, fromData);
        }
        catch(Exception e){
            System.out.println("Error generating CardCollection from map!");
            e.printStackTrace();
        }
        return cc;
    }

    public List<CardCollection> getByName(String name){
        return getREPOSITORY().findByName(name);
    }


    //////// RENDUNDANT INNER CONDITIONS, WIP
    @Override
    public boolean save(CardCollection fromEntity){
        try{
            if(fromEntity.getId() != null){
                UUID id = fromEntity.getId();
                CardCollection existing = getREPOSITORY().findById(id).orElse(null);
                if(existing == null){
                    getREPOSITORY().save(fromEntity);
                }
                // } else {
                //     // ??
                //     // existing.setOwner(fromEntity.getOwner());
                //     // existing.setName(fromEntity.getName());
                //     // existing.setDescription(fromEntity.getDescription());
                //     // existing.setMainDeckFormat(fromEntity.getMainDeckFormat());
                //     // existing.setLegalIn(fromEntity.getLegalIn());
                //     // existing.setCardList(fromEntity.getCardList());
                //     getREPOSITORY().save(existing);
                // }
            } else {
                getREPOSITORY().save(fromEntity);
            }
            return true;
        } catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Error saving CardCollection!");
            return false;
        }
    }

    // public boolean mapContentCheck(Map<String,String> toCheck){
    //     if(!toCheck.containsKey("id") || toCheck.get("id") == null || !toCheck.get("id").trim().isEmpty()
    //      || !toCheck.containsKey("user") || toCheck.get("user") == null || !toCheck.get("user").trim().isEmpty())
    //         return false;
    //     if(toCheck.get("name") == null || toCheck.get("name").strip().isEmpty())
    //         toCheck.replace("name", "place_holder_collection_name");
    //     if(toCheck.get("name") == null || toCheck.get("name").strip().isEmpty())
    //         toCheck.replace("name", "place_holder_collection_name");
        
    //     return true;
    // }

}
