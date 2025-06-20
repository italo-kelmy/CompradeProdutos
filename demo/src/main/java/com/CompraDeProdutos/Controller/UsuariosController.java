package com.CompraDeProdutos.Controller;

import com.CompraDeProdutos.Entity.Usuarios;
import com.CompraDeProdutos.Service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuariosController {

    private final UsuariosService service;

    @Autowired
    public UsuariosController(UsuariosService service) {
        this.service = service;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody Usuarios usuarios) {
        return service.cadastrar(usuarios);
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Usuarios usuarios) throws Exception {
        return service.login(usuarios);
    }


}
