package com.example.proyecto_citas_medicas.specifications;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.example.proyecto_citas_medicas.entities.User;
import jakarta.persistence.criteria.*;

@Component
public class UserSpecification {
    public static Specification<User> getAllUsers(String identification) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro opcional por nombre
            if (identification != null && !identification.isEmpty()) {
                String lowerIdentification = "%" + identification.trim().toLowerCase() + "%";
                Predicate identificationPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("identification")), lowerIdentification);
                predicates.add(identificationPredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
