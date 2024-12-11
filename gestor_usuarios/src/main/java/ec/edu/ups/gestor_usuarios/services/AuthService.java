package ec.edu.ups.gestor_usuarios.services;

import org.springframework.stereotype.Service;

@Service
public interface AuthService {

	public String generateToken(String username);
}
