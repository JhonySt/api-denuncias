package com.jhonyst.apidenuncias.controller;

import com.jhonyst.apidenuncias.controller.request.AsignarFiscalDenunciaDTO;
import com.jhonyst.apidenuncias.controller.request.CambiarEstadoDenunciaDTO;
import com.jhonyst.apidenuncias.controller.request.CreateDenunciaDTO;
import com.jhonyst.apidenuncias.model.Denuncia;
import com.jhonyst.apidenuncias.model.Denunciante;
import com.jhonyst.apidenuncias.model.EEstado;
import com.jhonyst.apidenuncias.model.Fiscal;
import com.jhonyst.apidenuncias.service.impl.DenunciaServiceImpl;
import com.jhonyst.apidenuncias.service.impl.DenuncianteServiceImpl;
import com.jhonyst.apidenuncias.service.impl.FiscalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/denuncias")
public class DenunciaController {

    @Autowired
    FiscalServiceImpl fiscalService;

    @Autowired
    private DenunciaServiceImpl denunciaService;

    @Autowired
    private DenuncianteServiceImpl denuncianteService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FISCAL')")
    public ResponseEntity<List<?>> getAllDenuncias(){
        List<Denuncia> listaDenuncias = denunciaService.getAllDenuncias();
        return ResponseEntity.ok(listaDenuncias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDenunciaById(@PathVariable Long id){
        if(id == null){
            return ResponseEntity.badRequest().body("El id esta vacio");
        }

        Denuncia denuncia = denunciaService.getDenunciaById(id);

        if(denuncia == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(denuncia);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveDenuncia(@RequestBody CreateDenunciaDTO denunciaDTO){

        Denunciante denunciante = denuncianteService.getDenuncianteByDocumento(denunciaDTO.getDocumentoDenunciante());

        if(denunciante == null){
            return ResponseEntity.badRequest().body("El documento del denunciante no esta registrado en el sistema, por favor registrese antes de realizar una denuncia");
        }

        Denuncia denuncia = Denuncia.builder()
                .lugar(denunciaDTO.getLugar())
                .descripcion(denunciaDTO.getDescripcion())
                .estado(EEstado.REGISTRADO)
                .denunciante(denunciante)
                .build();

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fechaJava = formato.parse(denunciaDTO.getFechaIncidente());
            denuncia.setFechaIncidente(fechaJava);
        } catch (ParseException ex) {
            Logger.getLogger(DenunciaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        denunciaService.saveDenuncia(denuncia);

        return new ResponseEntity<>(denuncia, HttpStatus.CREATED);
    }

    @PutMapping("/asignarFiscal")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> asignarFiscalDenuncia(@RequestBody AsignarFiscalDenunciaDTO asignarFiscalDenunciaDTO){

        Denuncia denuncia = denunciaService.getDenunciaById(asignarFiscalDenunciaDTO.getIdDenuncia());

        if(denuncia == null){
            return ResponseEntity.badRequest().body("No se encontro la denuncia con el id: " + asignarFiscalDenunciaDTO.getIdDenuncia());
        }

        Fiscal fiscal = fiscalService.getFiscalByDocumento(asignarFiscalDenunciaDTO.getDocumentoFiscal());

        if(fiscal == null){
            return ResponseEntity.badRequest().body("No se encontro al fiscal con el documento: " + asignarFiscalDenunciaDTO.getDocumentoFiscal());
        }

        denuncia.setFiscal(fiscal);
        denuncia.setEstado(EEstado.FISCAL_ASIGNADO);

        denunciaService.saveDenuncia(denuncia);

        return ResponseEntity.ok(denuncia);
    }

    @PutMapping("/actualizarEstado/{id}")
    @PreAuthorize("hasRole('FISCAL')")
    public ResponseEntity<?> actualizarEstadoDenuncia(@PathVariable Long id, @RequestBody CambiarEstadoDenunciaDTO estadoDenunciaDTO){

        Denuncia denunciaBD = denunciaService.getDenunciaById(id);

        if(denunciaBD == null){
            return ResponseEntity.badRequest().body("No se encontro la denuncia con el id: " + id);
        }

        denunciaBD.setEstado(EEstado.valueOf(estadoDenunciaDTO.getEstado()));
        denunciaBD.setObservacion(estadoDenunciaDTO.getObservacion());

        denunciaService.saveDenuncia(denunciaBD);

        return ResponseEntity.ok(denunciaBD);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDenunciaById(@PathVariable Long id){

        if(denunciaService.getDenunciaById(id) == null){
            return ResponseEntity.notFound().build();
        }

        denunciaService.deleteDenunciaById(id);

        return ResponseEntity.noContent().build();
    }


}
