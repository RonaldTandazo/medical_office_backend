package com.example.proyecto_citas_medicas.specifications;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.example.proyecto_citas_medicas.entities.DoctorPatient;
import com.example.proyecto_citas_medicas.entities.Patient;
import java.util.ArrayList;
import java.util.List;

@Component
public class PatientSpecification {
    public static Specification<Patient> getPacientesByDoctor(Long doctorId, String identification, String fullName, Character gender) {
        return (Root<Patient> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Patient, DoctorPatient> doctorPatientJoin = root.join("doctorPatients", JoinType.INNER);
            predicates.add(criteriaBuilder.equal(doctorPatientJoin.get("doctor").get("doctorId"), doctorId));
            predicates.add(criteriaBuilder.equal(doctorPatientJoin.get("status"), 'A'));

            if (identification != null && !identification.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("identification"), identification));
            }

            if (fullName != null && !fullName.isEmpty()) {
                String[] nameParts = fullName.trim().split("\\s+");
                Predicate namePredicate = criteriaBuilder.disjunction();
                Predicate lastnamePredicate = criteriaBuilder.disjunction();

                for (String namePart : nameParts) {
                    String lowerNamePart = "%" + namePart.toLowerCase() + "%";
                    namePredicate = criteriaBuilder.or(namePredicate,
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), lowerNamePart));
                    lastnamePredicate = criteriaBuilder.or(lastnamePredicate,
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("lastname")), lowerNamePart));
                }

                predicates.add(criteriaBuilder.or(namePredicate, lastnamePredicate));
            }

            if (gender != null) {
                predicates.add(criteriaBuilder.equal(root.get("gender"), gender));
            }

            predicates.add(criteriaBuilder.equal(root.get("status"), 'A'));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

