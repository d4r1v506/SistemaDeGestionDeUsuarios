package ec.edu.ups.gestor_usuarios.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.ups.gestor_usuarios.models.Estado;
import ec.edu.ups.gestor_usuarios.models.Usuario;
import ec.edu.ups.gestor_usuarios.repository.EstadoRepository;
import ec.edu.ups.gestor_usuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	public Usuario crearUsuario(Usuario usuario, String estadoNombre) throws Exception{

		Optional<Usuario> usuarioExiste = usuarioRepository.findByIdentificacion(usuario.getIdentificacion());
		if (usuarioExiste.isPresent()) {
			throw new Exception("No se puede crear, identificación ya existe");
		}

		Estado estado = estadoRepository.findByNombre(estadoNombre.toUpperCase())
				.orElseThrow(() -> new IllegalArgumentException("Estado invalido: " + estadoNombre.toUpperCase()));
		usuario.setEstado(estado);
		return usuarioRepository.save(usuario);
	}

	public Usuario actualizarUsuario(String identificacion, Usuario usuario) throws Exception {

		Optional<Usuario> usuarioExistente = usuarioRepository.findByIdentificacion(identificacion);

		if (!usuarioExistente.isPresent()) {
			throw new Exception("Usuario con identificación " + identificacion + " no encontrado.");
		}

		String estadoNombre = usuario.getEstado().getNombre();
		Optional<Estado> estadoValido = estadoRepository.findByNombre(estadoNombre.toUpperCase());
		if (!estadoValido.isPresent()) {
			throw new Exception("El estado '" + estadoNombre + "' no es válido.");
		}

		Usuario usuarioActualizado = usuarioExistente.get();
		usuarioActualizado.setIdentificacion(usuario.getIdentificacion());
		usuarioActualizado.setNombres(usuario.getNombres());
		usuarioActualizado.setApellidos(usuario.getApellidos());
		usuarioActualizado.setEdad(usuario.getEdad());
		usuarioActualizado.setCargo(usuario.getCargo());
		usuarioActualizado.setEstado(estadoValido.get());

		return usuarioRepository.save(usuarioActualizado);
	}

	public boolean borrarUsuario(String identificacion) {
		Optional<Usuario> usuarioExiste = usuarioRepository.findByIdentificacion(identificacion);
		if(usuarioExiste.isPresent()) {
			usuarioRepository.delete(usuarioExiste.get());
			return true;
		}
		return false;
	}

	public Usuario obtenerUsuario(String identificacion) throws Exception {		
		Optional<Usuario> usuarioExiste = usuarioRepository.findByIdentificacion(identificacion);
		if(!usuarioExiste.isPresent()) {
			throw new Exception("Usuario con identificación " + identificacion + " no encontrado.");					
		}
		return usuarioExiste.get();
	}
	
	

}
