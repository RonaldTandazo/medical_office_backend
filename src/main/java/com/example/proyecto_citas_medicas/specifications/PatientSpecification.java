package com.example.proyecto_citas_medicas.specifications;

import jakarta.persistence.criteria.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import com.example.proyecto_citas_medicas.controller.PatientController;
import com.example.proyecto_citas_medicas.entities.Patient;
import java.util.ArrayList;
import java.util.List;

public class PatientSpecification {
    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    public static Specification<Patient> getPacientesByDoctor(Long user_id, String identification, String fullName, Character gender) {
        return (Root<Patient> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por doctor_id
            predicates.add(criteriaBuilder.equal(root.get("doctorId"), user_id));

            // Filtro opcional por identificación
            if (identification != null && !identification.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("identification"), identification));
            }

            // Filtro opcional por nombre completo
            if (fullName != null && !fullName.isEmpty()) {
                // Dividimos el nombre completo en palabras
                String[] nameParts = fullName.trim().split("\\s+");
                // Preparamos los predicados para name y lastname
                Predicate namePredicate = criteriaBuilder.disjunction();
                Predicate lastnamePredicate = criteriaBuilder.disjunction();

                // Recorremos las partes del nombre y aplicamos el lower() para una búsqueda insensible a mayúsculas
                for (String namePart : nameParts) {
                    String lowerNamePart = "%" + namePart.toLowerCase() + "%";
                    namePredicate = criteriaBuilder.or(namePredicate,
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), lowerNamePart));
                    lastnamePredicate = criteriaBuilder.or(lastnamePredicate,
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("lastname")), lowerNamePart));
                }

                // Añadimos las condiciones de búsqueda a la lista de predicados
                predicates.add(criteriaBuilder.or(namePredicate, lastnamePredicate));
            }

            // Filtro opcional por género
            if (gender != null) {
                predicates.add(criteriaBuilder.equal(root.get("gender"), gender));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

