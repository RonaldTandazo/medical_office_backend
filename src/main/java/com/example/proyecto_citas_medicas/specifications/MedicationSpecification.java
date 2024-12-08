package com.example.proyecto_citas_medicas.specifications;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import com.example.proyecto_citas_medicas.entities.Medication;
import jakarta.persistence.criteria.*;

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
