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
            // System.out.println("parsing equals");
            if (CRITERIA.getKey().equals("nonplayable")) {
                // System.out.println("parsing nonplayable " + CRITERIA);
                if (!CRITERIA.getValue().equals("include") || !CRITERIA.getValue().equals("true")) {
                    return criteriaBuilder.notLike(criteriaBuilder.lower(root.get("layout")), "art_series");
                }
                return criteriaBuilder.notLike(criteriaBuilder.lower(root.get("layout")), "art_series");

                // return criteriaBuilder.isNotEmpty(root.get("layout"));
                // return criteriaBuilder.isNotNull(criteriaBuilder.lower(root.get("layout")));
            }

            if (CRITERIA.getKey().equals("name") || CRITERIA.getKey().equals("oracle")) {

                return criteriaBuilder.like(criteriaBuilder.lower(root.get(CRITERIA.getKey())),
                        ("%" + CRITERIA.getValue() + "%"));
            }
            if (CRITERIA.getKey().equals("set")) {
                return criteriaBuilder.equal(root.get("expansion").get("code"), CRITERIA.getValue());
            }
            return criteriaBuilder.equal(root.get(CRITERIA.getKey()), CRITERIA.getValue());
        }
        if (CRITERIA.getLogic().equalsIgnoreCase(">")) {
            // System.out.println("parsing " + root.get(CRITERIA.getKey()) + " greater than "
            //         + CRITERIA.getParsedValue());
            return criteriaBuilder.greaterThan(root.get(CRITERIA.getKey()).as(Double.class), CRITERIA.getParsedValue());
        }
        if (CRITERIA.getLogic().equalsIgnoreCase("<")) {
            return criteriaBuilder.lessThan(root.get(CRITERIA.getKey()).as(Double.class), CRITERIA.getParsedValue());
        }

        return null;
    }

}
