package com.mambocosmo.urzasoracle.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.mambocosmo.urzasoracle.DTO.ArtistDTO;
import com.mambocosmo.urzasoracle.converters.ArtistConverter;
import com.mambocosmo.urzasoracle.entities.Artist;
import com.mambocosmo.urzasoracle.repositories.ArtistRepository;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Service
@Data
@EqualsAndHashCode(callSuper=true)
public class ArtistService extends GenericService<Artist, ArtistDTO, ArtistConverter, ArtistRepository> {

    @Override
    public Artist construct(Map<String, String> fromData) {
        return getCONTEXT().getBean(Artist.class, fromData);
    }

    public Object getByName(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByName'");
    }

}
