package com.edward.tareas.model;

public record Tarea(
        int id,
        String description,
        String status,
        String createAt,
        String updateAt
) {
}
