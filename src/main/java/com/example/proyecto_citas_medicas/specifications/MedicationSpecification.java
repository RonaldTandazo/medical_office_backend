package com.example.proyecto_citas_medicas.specifications;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import com.example.proyecto_citas_medicas.entities.Medication;
import jakarta.persistence.criteria.*;

@Component
public class MedicationSpecification {
    public static Specification<Medication> medicationByNameAndStatus(String name) {
        return (Root<Medication> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro opcional por nombre
            if (name != null && !name.isEmpty()) {
                String lowerName = "%" + name.trim().toLowerCase() + "%";
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), lowerName);
                predicates.add(namePredicate);
            }

            // Filtro por estado 'A' (activo)
            predicates.add(criteriaBuilder.equal(root.get("status"), 'A'));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
