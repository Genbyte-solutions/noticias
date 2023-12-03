package com.mendozanews.apinews.controller;

import com.mendozanews.apinews.exception.ResourceBadRequestException;
import com.mendozanews.apinews.exception.ResourceNotFoundException;
import com.mendozanews.apinews.mapper.SeccionMapper;
import com.mendozanews.apinews.model.dto.request.SeccionDto;
import com.mendozanews.apinews.model.dto.response.SeccionResDto;
import com.mendozanews.apinews.model.entity.Seccion;
import com.mendozanews.apinews.model.payload.ResponseMessage;
import com.mendozanews.apinews.service.interfaces.ISeccion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Validated
@RequestMapping("api/v1")
@Tag(name = "Seccion")
public class SeccionControlador {

    private final ISeccion seccionService;
    private final SeccionMapper seccionMapper;

    public SeccionControlador(ISeccion seccionService, SeccionMapper seccionMapper) {
        this.seccionService = seccionService;
        this.seccionMapper = seccionMapper;
    }

    @Operation(
            summary = "Crea una seccion",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "La solicitud ha tenido éxito y se ha creado un nuevo recurso como resultado de ello"),
                    @ApiResponse(responseCode = "400",
                            description = "El servidor no pudo interpretar la solicitud dada una sintaxis inválida"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @PostMapping("/seccion")
    public ResponseEntity<?> crearSeccion(
            @ModelAttribute @Valid SeccionDto seccionDto,
            @RequestPart(value = "icono", required = false) MultipartFile icono) throws IOException {

        Seccion seccion = seccionService.crearSeccion(seccionDto, icono);
        SeccionResDto seccionResDto = seccionMapper.toDTO(seccion);

        return new ResponseEntity<>(ResponseMessage.builder()
                .mensaje("Sección añadida")
                .recurso(seccionResDto)
                .build(), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Edita una seccion por ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "400",
                            description = "El servidor no pudo interpretar la solicitud dada una sintaxis inválida"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @PutMapping("/seccion/{seccionId}")
    public ResponseEntity<?> editarSeccion(@PathVariable("seccionId") String seccionId,
                                           @ModelAttribute @Valid SeccionDto seccionDto,
                                           @RequestPart(value = "icono", required = false) MultipartFile icono) {

        try {
            Seccion seccion = seccionService.buscarSeccionPorId(seccionId);
            if (seccion == null) throw new ResourceNotFoundException("seccion", "id", seccionId);

            Seccion seccionEditada = seccionService.editarSeccion(seccion, seccionDto, icono);
            SeccionResDto seccionResDto = seccionMapper.toDTO(seccionEditada);

            return new ResponseEntity<>(ResponseMessage.builder()
                    .mensaje("Sección actualizada")
                    .recurso(seccionResDto)
                    .build(), HttpStatus.OK);
        } catch (IOException ioException) {
            throw new ResourceBadRequestException(ioException.getMessage());
        }
    }


    @Operation(
            summary = "Busca una seccion por ID o Nombre",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )

    @GetMapping("/seccion/{buscar}")
    public ResponseEntity<?> buscarSeccion(@PathVariable(value = "buscar") String buscar) {

        Seccion seccion = null;
        if (buscar.length() == 36) {
            seccion = seccionService.buscarSeccionPorId(buscar);
            if (seccion == null) throw new ResourceNotFoundException("seccion", "id", buscar);
        } else {
            seccion = seccionService.buscarSeccionPorNombre(buscar);
            if (seccion == null) throw new ResourceNotFoundException("seccion", "nombre", buscar);
        }

        SeccionResDto seccionResDto = seccionMapper.toDTO(seccion);
        return new ResponseEntity<>(seccionResDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Lista de secciones",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "404",
                            description = "El servidor no pudo encontrar el contenido solicitado"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @GetMapping("/secciones")
    public ResponseEntity<?> listarSecciones() {

        List<Seccion> secciones = seccionService.listarSecciones();
        if (secciones.isEmpty()) throw new ResourceNotFoundException("secciones");

        List<SeccionResDto> seccionesResDto = seccionMapper.toDTOs(secciones);
        return new ResponseEntity<>(seccionesResDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Elimina una seccion por ID",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "La solicitud ha tenido éxito"),
                    @ApiResponse(responseCode = "500",
                            description = "El servidor ha encontrado una situación que no sabe cómo manejarla")
            }
    )
    @DeleteMapping("/seccion/{seccionId}")
    public ResponseEntity<?> eliminarSeccionPorId(@PathVariable("seccionId") String seccionId) {
        seccionService.eliminarSeccionPorId(seccionId);
        return new ResponseEntity<>(ResponseMessage.builder()
                .mensaje("Seccion eliminada")
                .build(), HttpStatus.OK);
    }
}
