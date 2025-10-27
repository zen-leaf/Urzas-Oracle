package com.mambocosmo.urzasoracle.converters;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.ArtistDTO;
import com.mambocosmo.urzasoracle.entities.Artist;

@Service
public class ArtistConverter implements GenericConverter<Artist, ArtistDTO> {

    @Override
    public Artist fromDToE(ArtistDTO dto) {
        // Artist a = new 
        throw new UnsupportedOperationException("Unimplemented method 'fromEToD'");
    }

    @Override
    public ArtistDTO fromEToD(Artist e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromEToD'");
    }

}
