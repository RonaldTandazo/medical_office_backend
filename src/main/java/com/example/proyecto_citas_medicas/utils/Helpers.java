package com.example.proyecto_citas_medicas.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class Helpers {

    public String capitalize(String text) {
        if (text == null || text.isBlank()) return text;

        text = text.trim().toLowerCase();
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    public List<Map<String, Object>> normalizeMonths(List<Object[]> rawData) {
        Map<Integer, Long> map = new HashMap<>();

        for (Object[] row : rawData) {
            Integer month = ((Number) row[0]).intValue();
            Long total = row[1] != null ? ((Number) row[1]).longValue() : 0L;
            map.put(month, total);
        }

        List<Map<String, Object>> result = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            Map<String, Object> item = new HashMap<>();
            item.put("month", month);
            item.put("total", map.getOrDefault(month, 0L));
            result.add(item);
        }

        return result;
    }
}
