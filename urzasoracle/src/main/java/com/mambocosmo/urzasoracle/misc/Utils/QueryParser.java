package com.mambocosmo.urzasoracle.misc.Utils;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import com.mambocosmo.urzasoracle.entities.Card;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

@AllArgsConstructor
public class QueryParser implements Specification<Card> {
    private final SearchCriteria CRITERIA;

    @Override
    @Nullable
    public Predicate toPredicate(@NonNull Root<Card> root, @Nullable CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder) {
        if (CRITERIA.getLogic().equalsIgnoreCase(":")) {
            System.out.println("parsing equals");
            if (CRITERIA.getKey().equals("name") || CRITERIA.getKey().equals("oracle")) {
                return criteriaBuilder.like(criteriaBuilder.lower(root.get(CRITERIA.getKey())),
                        ("%" + CRITERIA.getValue() + "%"));
            }
            return criteriaBuilder.equal(root.get(CRITERIA.getKey()), CRITERIA.getValue());
        }
        if (CRITERIA.getLogic().equalsIgnoreCase(">")) {
            System.out.println("parsing " + root.get(CRITERIA.getKey()) + " greater than "
                    + CRITERIA.getParsedValue());
            return criteriaBuilder.greaterThan(root.get(CRITERIA.getKey()).as(Double.class), CRITERIA.getParsedValue());
        }
        if (CRITERIA.getLogic().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThan(root.get(CRITERIA.getKey()).as(Double.class), CRITERIA.getParsedValue());
        }

        return null;
    }

}
