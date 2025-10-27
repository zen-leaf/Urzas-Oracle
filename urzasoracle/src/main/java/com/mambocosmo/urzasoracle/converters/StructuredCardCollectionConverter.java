package com.mambocosmo.urzasoracle.converters;



import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.StructuredCardCollectionDTO;
import com.mambocosmo.urzasoracle.entities.StructuredCardCollection;

@Service
public class StructuredCardCollectionConverter implements GenericConverter<StructuredCardCollection,StructuredCardCollectionDTO>{

    @Override
    public StructuredCardCollection fromDToE(StructuredCardCollectionDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromDToE'");
    }

    @Override
    public StructuredCardCollectionDTO fromEToD(StructuredCardCollection e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromEToD'");
    }


}
