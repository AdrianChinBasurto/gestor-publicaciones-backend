package com.gestor.catalogo_service.controller;

import com.gestor.catalogo_service.model.CatalogoItem;
import com.gestor.catalogo_service.service.CatalogoService;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalogo")
@CrossOrigin(origins = "*")
public class CatalogoController {

    private final CatalogoService svc;
    public CatalogoController(CatalogoService svc){ this.svc = svc; }

    @GetMapping
    public Page<CatalogoItem> listar(
            @RequestParam(required=false) String q,
            @RequestParam(required=false) String tipo,
            @PageableDefault(size=10, sort="fechaPublicacion", direction=Sort.Direction.DESC) Pageable pageable
    ){
        return svc.buscar(q, tipo, pageable);
    }
}
