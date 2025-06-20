package com.CompraDeProdutos.Service;

import com.CompraDeProdutos.Entity.Usuarios;
import com.CompraDeProdutos.Repository.UsuariosRepository;
import com.CompraDeProdutos.Security.JwtConfig;
import com.CompraDeProdutos.Security.JwtUtil;
import com.CompraDeProdutos.Security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuariosService implements UserDetailsService {

    private UsuariosRepository repository;
    private JwtConfig jwtConfig;
    private SecurityConfig config;
    private JwtUtil jwtUtil;

    @Autowired
    public UsuariosService(UsuariosRepository repository, JwtUtil jwtUtil, SecurityConfig config) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
        this.config = config;
    }

    public ResponseEntity<?> cadastrar(Usuarios usuarios) {

        if (repository.findByUsuario(usuarios.getUsuario()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já cadastrado");
        } else if (repository.findByEmail(usuarios.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado");
        }


        usuarios.setSenha(config.bCryptPasswordEncoder().encode(usuarios.getSenha()));
repository.save(usuarios);
        return ResponseEntity.status(HttpStatus.OK).body("Usuário cadastrado com sucesso");
    }


    public ResponseEntity<?> login(Usuarios usuarios) throws Exception {
        config.manager(new AuthenticationConfiguration()).authenticate(
                new UsernamePasswordAuthenticationToken(usuarios.getUsuario(), usuarios.getSenha())
        );


        Usuarios usuario = repository.findByUsuario(usuarios.getUsuario()).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        UserDetails user = User.builder()
                .username(usuario.getUsuario())
                .password(usuario.getSenha())
                .roles(usuario.getRole())
                .build();

        return ResponseEntity.ok(jwtUtil.generatKey(user));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuarios = repository.findByUsuario(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return User.builder()
                .username(usuarios.getUsuario())
                .password(usuarios.getSenha())
                .roles(usuarios.getRole())
                .build();
    }
}
