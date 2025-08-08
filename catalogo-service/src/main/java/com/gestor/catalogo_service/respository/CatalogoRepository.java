package com.gestor.catalogo_service.respository;

import com.gestor.catalogo_service.model.CatalogoItem;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CatalogoRepository extends JpaRepository<CatalogoItem, UUID> {
    Page<CatalogoItem> findByTituloContainingIgnoreCase(String q, Pageable p);
    Page<CatalogoItem> findByPalabrasContainingIgnoreCase(String q, Pageable p);
    Page<CatalogoItem> findByTipo(String tipo, Pageable p);
}
