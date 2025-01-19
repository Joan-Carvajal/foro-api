package com.api.foro.controller;

import com.api.foro.domain.topico.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoRepository repository;

    @PostMapping
    public ResponseEntity<DatosRespuestaTopico> registrarTopico(@Valid @RequestBody DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder){
        Topico topico = repository.save( new Topico(datosRegistroTopico));
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getTitulo(),topico.getMensaje(),
                topico.getFecha(),topico.getEstado());
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopico(@PageableDefault(size = 10,sort = {"fecha"}) Pageable paginacion){
    return  ResponseEntity.ok(repository.findByEstadoTrue(paginacion).map(DatosListadoTopico::new));
//        return repository.findAll(paginacion).map(DatosListadoTopico::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaTopico> mostrarTopico(@PathVariable Long id){
        Topico topico= repository.getReferenceById(id);
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getTitulo(),topico.getMensaje(),
                topico.getFecha(),topico.getEstado());
        return ResponseEntity.ok(datosRespuestaTopico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopico> actualizarTopico(@PathVariable Long id , @RequestBody DatosActualizarTopico datosActualizarTopico){
        Optional<Topico> optionalTopico = repository.findById(id);
        if (!optionalTopico.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Topico topico = optionalTopico.get();
        topico.actualizarTopico(datosActualizarTopico);
        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getTitulo(), topico.getMensaje(),
                topico.getFecha(), topico.getEstado()
        );
        return ResponseEntity.ok(datosRespuestaTopico);
//        Topico topico = repository.getReferenceById(id);
//        topico.actualizarTopico(datosActualizarTopico);
//        DatosRespuestaTopico datosRespuestaTopico = new DatosRespuestaTopico(topico.getTitulo(),topico.getMensaje(),
//                topico.getFecha(),topico.getEstado());
//        return ResponseEntity.ok(datosRespuestaTopico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id){
        Optional<Topico> optionalTopico= repository.findById(id);
        if (!optionalTopico.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Topico topico= optionalTopico.get();
        repository.deleteById(topico.getId());
        return ResponseEntity.noContent().build();
    }

//    @DeleteMapping("/{id}")
//    @Transactional
//    public ResponseEntity eliminarTopico(@PathVariable Long id) {
//        return repository.findById(id)
//                .map(topico -> {
//                    repository.deleteById(topico.getId());
//                    return ResponseEntity.noContent().build();
//                })
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }




}
