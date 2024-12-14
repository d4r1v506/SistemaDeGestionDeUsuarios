package ec.edu.ups.gestor_usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.ups.gestor_usuarios.models.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long>{
	Optional<Estado> findByNombre(String nombre);	
}
