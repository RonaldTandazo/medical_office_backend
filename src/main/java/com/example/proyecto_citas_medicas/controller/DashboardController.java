package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.ApiResponse;
import com.example.proyecto_citas_medicas.entities.Uploads;
import com.example.proyecto_citas_medicas.repository.MedicamentosRepository;
import com.example.proyecto_citas_medicas.repository.UploadDetailRepository;
import com.example.proyecto_citas_medicas.repository.UploadsRepository;
import com.example.proyecto_citas_medicas.repository.VariantesRepository;
import com.example.proyecto_citas_medicas.utils.Helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
    private final MedicamentosRepository medicamentosRepository;
    private final VariantesRepository variantesRepository;
    private final UploadsRepository uploadsRepository;
    private final UploadDetailRepository uploadDeatilRepository;
    private final Helpers helpers;

    public DashboardController(MedicamentosRepository medicamentosRepository, VariantesRepository variantesRepository, UploadsRepository uploadsRepository, UploadDetailRepository uploadDeatilRepository, Helpers helpers) {
        this.medicamentosRepository = medicamentosRepository;
        this.variantesRepository = variantesRepository;
        this.uploadsRepository = uploadsRepository;
        this.uploadDeatilRepository = uploadDeatilRepository;
        this.helpers = helpers;
    }

    @GetMapping("/info")
    public ResponseEntity<ApiResponse> getDashboardInfo() {
        try {
            Long totalUploads = uploadsRepository.totalUploads();
            Long totalUploadsCurrency = uploadDeatilRepository.totalUploadsCurrency();
            Long totalMedications = medicamentosRepository.totalMedications();
            Long totalInvMedications = uploadDeatilRepository.totalInvMedications();

            List<Map<String, Object>> uploadsByMonth = helpers.normalizeMonths(uploadsRepository.uploadsByMonth());
            List<Map<String, Object>> amountByMonth = helpers.normalizeMonths(uploadDeatilRepository.totalAmountByMonth());
            List<Map<String, Object>> alerts = variantesRepository.lowStockAlerts();

            Map<String, Object> data = new HashMap<>();
            data.put("totalUploads", totalUploads);
            data.put("totalUploadsCurrency", totalUploadsCurrency);
            data.put("totalMedications", totalMedications);
            data.put("totalInvMedications", totalInvMedications);
            data.put("uploadsByMonth", uploadsByMonth);
            data.put("amountByMonth", amountByMonth);
            data.put("alerts", alerts);

            return ResponseEntity.ok(
                new ApiResponse(true, "Información Obtenida", data, 200)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Error dashboard", null, 500));
        }
    }
}
