package com.vacapp.ganado.internal.infrastructure.controllers.mobile;

import com.vacapp.core.TenantContext;
import com.vacapp.ganado.internal.application.usecases.ActualizarAnimalUseCase;
import com.vacapp.ganado.internal.application.usecases.ListarAnimalesUseCase;
import com.vacapp.ganado.internal.application.usecases.RegistrarAnimalUseCase;
import com.vacapp.ganado.internal.domain.model.Animal;
import com.vacapp.ganado.internal.infrastructure.controllers.mobile.dtos.AnimalRequest;
import com.vacapp.ganado.internal.infrastructure.controllers.mobile.dtos.AnimalResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST del inventario de ganado.
 * Implementa la interfaz generada desde openapi-ganado.yaml (Design-First).
 * Sin anotaciones Swagger propias — el contrato vive en el YAML.
 */
@RestController
@RequiredArgsConstructor
public class GanadoRestController implements GanadoApi {

    private final RegistrarAnimalUseCase registrarAnimalUseCase;
    private final ListarAnimalesUseCase listarAnimalesUseCase;
    private final ActualizarAnimalUseCase actualizarAnimalUseCase;

    @Override
    public ResponseEntity<AnimalResponse> registrarAnimal(@Valid @RequestBody AnimalRequest animalRequest) {
        String tenantId = TenantContext.obtenerTenant();
        Animal animal = registrarAnimalUseCase.ejecutar(new RegistrarAnimalUseCase.Comando(
                animalRequest.numeroIdentificador(), animalRequest.estatus(), animalRequest.sexo(), animalRequest.raza(),
                animalRequest.fechaNacimiento(), animalRequest.meses(), animalRequest.fechaAretado(), animalRequest.tipo(),
                animalRequest.areteAnterior(), animalRequest.folioReemo(), animalRequest.nota(), tenantId
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(AnimalResponse.desde(animal));
    }

    @Override
    public ResponseEntity<List<AnimalResponse>> listarAnimales() {
        String tenantId = TenantContext.obtenerTenant();
        List<AnimalResponse> lista = listarAnimalesUseCase.ejecutar(tenantId)
                .stream().map(AnimalResponse::desde).toList();
        return ResponseEntity.ok(lista);
    }

    @Override
    public ResponseEntity<AnimalResponse> actualizarAnimal(@PathVariable("id") UUID id,
                                                            @Valid @RequestBody AnimalRequest animalRequest) {
        String tenantId = TenantContext.obtenerTenant();
        Animal animal = actualizarAnimalUseCase.ejecutar(new ActualizarAnimalUseCase.Comando(
                id, animalRequest.numeroIdentificador(), animalRequest.estatus(), animalRequest.sexo(),
                animalRequest.raza(), animalRequest.fechaNacimiento(), animalRequest.meses(),
                animalRequest.fechaAretado(), animalRequest.tipo(), animalRequest.areteAnterior(),
                animalRequest.folioReemo(), animalRequest.nota(), tenantId
        ));
        return ResponseEntity.ok(AnimalResponse.desde(animal));
    }
}
