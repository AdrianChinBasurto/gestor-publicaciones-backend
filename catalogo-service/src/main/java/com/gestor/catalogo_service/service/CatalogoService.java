package com.gestor.catalogo_service.service;

import com.gestor.catalogo_service.model.CatalogoItem;
import com.gestor.catalogo_service.respository.CatalogoRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

@Service
public class CatalogoService {
    private final CatalogoRepository repo;
    public CatalogoService(CatalogoRepository repo){ this.repo = repo; }

    public Page<CatalogoItem> buscar(String q, String tipo, Pageable pageable){
        if (q != null && !q.isBlank()) return repo.findByTituloContainingIgnoreCase(q, pageable);
        if (tipo != null && !tipo.isBlank()) return repo.findByTipo(tipo, pageable);
        return repo.findAll(pageable);
    }

    public CatalogoItem guardar(CatalogoItem it){ return repo.save(it); }
}
