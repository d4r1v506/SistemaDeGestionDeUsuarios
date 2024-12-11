package ec.edu.ups.gestor_usuarios.models;

public class AuthResponse {
	 private final String token;
     private final String type;
     
     public AuthResponse(String token, String type) {
         this.token = token;
         this.type = type;
     }

     public String getToken() {
         return token;
     }

     public String getType() {
         return type;
     }
}
