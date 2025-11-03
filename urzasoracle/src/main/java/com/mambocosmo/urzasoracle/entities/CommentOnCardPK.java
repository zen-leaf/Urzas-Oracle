// package com.mambocosmo.urzasoracle.entities;

// import java.io.Serializable;

// import jakarta.persistence.CascadeType;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.EqualsAndHashCode;
// import lombok.NoArgsConstructor;
// import lombok.ToString;

// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// public class CommentOnCardPK implements Serializable{
    
//     @EqualsAndHashCode.Exclude
//     @ToString.Exclude
//     @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
//     @JoinColumn(name = "card_id")
//     private Card card;

//     @EqualsAndHashCode.Exclude
//     @ToString.Exclude
//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "user_id")
//     private UrzaUser user;


//     @Override
//     public String toString() {
//         return "card name : " + getUser().getUsername() + " card name" + getCard().getName();
//     }

//     // removing this breaks cardcollection list population
//     @Override
//     public boolean equals(Object obj) {
//         if (this == obj) {
//             return true;
//         }
//         if (obj == null || getClass() != obj.getClass()) {
//             return false;
//         }
//         CommentOnCardPK other = (CommentOnCardPK) obj;
//         return card.getId() == other.card.getId() &&
//                 user.getId() == other.user.getId();
//     }

// }
