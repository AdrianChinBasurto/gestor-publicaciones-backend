package com.gestor.publicaciones_service;
import com.gestor.publicaciones_service.dto.PublicacionRequest;

public class TestLombok {
    public static void main(String[] args) {
        PublicacionRequest dto = new PublicacionRequest();
        dto.setTitulo("Test");
        System.out.println(dto.getTitulo());
    }
}
