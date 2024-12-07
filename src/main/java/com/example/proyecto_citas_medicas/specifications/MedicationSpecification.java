package com.example.proyecto_citas_medicas.specifications;

import jakarta.persistence.criteria.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import com.example.proyecto_citas_medicas.entities.Medication;
import java.util.ArrayList;
import java.util.List;

public class MedicationSpecification {
    private static final Logger logger = LoggerFactory.getLogger(MedicationSpecification.class);

    public static Specification<Medication> getMedicationByName(String medication) {
        return (Root<Medication> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro opcional por nombre
            if (medication != null && !medication.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("name"), medication));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

