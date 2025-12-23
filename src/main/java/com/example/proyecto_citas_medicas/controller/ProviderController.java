package com.example.proyecto_citas_medicas.controller;

import com.example.proyecto_citas_medicas.entities.ApiResponse;
import com.example.proyecto_citas_medicas.entities.Medicamentos;
import com.example.proyecto_citas_medicas.entities.UploadDetail;
import com.example.proyecto_citas_medicas.entities.Uploads;
import com.example.proyecto_citas_medicas.entities.Variantes;
import com.example.proyecto_citas_medicas.repository.MedicamentosRepository;
import com.example.proyecto_citas_medicas.repository.UploadDetailRepository;
import com.example.proyecto_citas_medicas.repository.UploadsRepository;
import com.example.proyecto_citas_medicas.repository.VariantesRepository;
import com.example.proyecto_citas_medicas.utils.Helpers;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/provider")
public class ProviderController {
    private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);
    private final MedicamentosRepository medicamentosRepository;
    private final VariantesRepository variantesRepository;
    private final UploadsRepository uploadsRepository;
    private final UploadDetailRepository uploadDeatilRepository;
    private final Helpers helpers;

    public ProviderController(MedicamentosRepository medicamentosRepository, UploadsRepository uploadsRepository, VariantesRepository variantesRepository, UploadDetailRepository uploadDeatilRepository, Helpers helpers) {
        this.medicamentosRepository = medicamentosRepository;
        this.variantesRepository = variantesRepository;
        this.uploadsRepository = uploadsRepository;
        this.uploadDeatilRepository = uploadDeatilRepository;
        this.helpers = helpers;
    }

    @GetMapping("/uploads")
    public ResponseEntity<ApiResponse> getUploads() {
        try {
            List<Uploads> uploads = uploadsRepository.findAllActiveWithDetails();

            return ResponseEntity.ok(
                new ApiResponse(true, "Cargas Realizadas Obtenidas", uploads, HttpStatus.OK.value())
            );

        } catch (Exception e) {
            logger.error("ERROR en getUploads", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Error al obtener cargas", null, 500));
        }
    }

    @GetMapping("/download_template")
    public ResponseEntity<Resource> downloadTemplate() {
        try {
            ClassPathResource file = new ClassPathResource("files/Plantilla Medicamentos.xlsx");

            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }

            InputStreamResource resource = new InputStreamResource(file.getInputStream());

            return ResponseEntity.ok()
                .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"Plantilla Medicamentos.xlsx\""
                )
                .contentType(
                    MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                    )
                )
                .contentLength(file.contentLength())
                .body(resource);

        } catch (Exception e) {
            logger.error("Error descargando plantilla", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("upload_medication")
    @Transactional
    public ResponseEntity<ApiResponse> store(@RequestBody Map<String, Object> request) {
        try {
            Map<String, Object> general = (Map<String, Object>) request.get("general");
            List<Map<String, Object>> medications = (List<Map<String, Object>>) request.get("medicamentos");

            Uploads upload = new Uploads();
            upload.setRuc(String.valueOf(general.get("ruc")));
            upload.setProveedor(String.valueOf(general.get("proveedor")));
            upload.setFecha(LocalDateTime.now());
            upload.setStatus('A');
            upload = uploadsRepository.save(upload);

            Map<String, Medicamentos> medicamentosCache = new HashMap<>();
            Map<String, Variantes> variantesCache = new HashMap<>();

            List<Variantes> variantesToSave = new ArrayList<>();
            List<UploadDetail> uploadDetailsToSave = new ArrayList<>();

            for (Map<String, Object> data : medications) {
                String nombre = helpers.capitalize(String.valueOf(data.get("medicamento")));
                String descripcion = String.valueOf(data.get("descripcion"));
                String presentacion = helpers.capitalize(String.valueOf(data.get("presentacion")));

                Integer cantidad = Integer.parseInt(data.get("cantidad").toString());
                Double precio = Double.parseDouble(data.get("precio_unitario").toString());
                Double total = Double.parseDouble(data.get("valor_total").toString());

                Medicamentos medicamento = medicamentosCache.computeIfAbsent(nombre, n ->
                    medicamentosRepository.findByNombreIgnoreCase(n)
                        .orElseGet(() -> {
                            Medicamentos nuevo = new Medicamentos();
                            nuevo.setNombre(n);
                            nuevo.setStatus('A');
                            return medicamentosRepository.save(nuevo);
                        })
                );

                Long medicamentoId = medicamento.getId();

                String varianteKey = medicamentoId + "|" + descripcion.toLowerCase() + "|" + presentacion.toLowerCase();

                Variantes variante = variantesCache.get(varianteKey);

                if (variante == null) {
                    variante = variantesRepository
                        .findByMedicamentoIdAndDescripcionIgnoreCaseAndPresentacionIgnoreCase(
                            medicamentoId, descripcion, presentacion
                        )
                        .orElseGet(() -> {
                            Variantes v = new Variantes();
                            v.setMedicamentoId(medicamentoId);
                            v.setDescripcion(descripcion);
                            v.setPresentacion(presentacion);
                            v.setCantidad(0);
                            v.setLote(String.valueOf(data.get("lote")));
                            v.setElaboracion(String.valueOf(data.get("fecha_elaboracion")));
                            v.setCaducidad(String.valueOf(data.get("fecha_caducidad")));
                            v.setSanitario(String.valueOf(data.get("registro_sanitario")));
                            v.setPrecio(precio);
                            v.setStatus('A');
                            return v;
                        });

                    variantesCache.put(varianteKey, variante);
                }

                variante.setCantidad(variante.getCantidad() + cantidad);
                variante.setTotal(variante.getCantidad() * variante.getPrecio());

                variantesToSave.add(variante);

                UploadDetail detail = new UploadDetail();
                detail.setUpload(upload);
                detail.setMedicamento(nombre);
                detail.setDescripcion(descripcion);
                detail.setPresentacion(presentacion);
                detail.setCantidad(cantidad);
                detail.setPrecio(precio);
                detail.setTotal(total);
                detail.setLote(String.valueOf(data.get("lote")));
                detail.setElaboracion(String.valueOf(data.get("fecha_elaboracion")));
                detail.setCaducidad(String.valueOf(data.get("fecha_caducidad")));
                detail.setSanitario(String.valueOf(data.get("registro_sanitario")));
                detail.setStatus('A');

                uploadDetailsToSave.add(detail);
            }

            variantesRepository.saveAll(variantesToSave);
            uploadDeatilRepository.saveAll(uploadDetailsToSave);

            return ResponseEntity.ok(
                new ApiResponse(true, "Medicamentos y Variantes Guardados", null, HttpStatus.OK.value())
            );

        } catch (Exception e) {
            logger.error("ERROR en upload_medication", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(false, "Error al guardar información", null, 500));
        }
    }
}
