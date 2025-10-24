package com.mambocosmo.urzasoracle.entities;

import java.util.List;

import com.mambocosmo.urzasoracle.misc.Format;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class StructuredCardCollection extends CardCollection{

    private List<Format> legalIn;
}
