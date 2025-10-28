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
        ArtistDTO dto = new ArtistDTO();
        dto.setId(e.getId());
        dto.setArtist_name(e.getName());
        return dto;
    }

}
