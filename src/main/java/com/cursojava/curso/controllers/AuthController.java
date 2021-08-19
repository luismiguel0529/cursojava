package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UsuarioDao dao;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/api/login")
    public String login(@RequestBody Usuario usuario) {

        Usuario user = dao.obtenerUsuarioPorCredenciales(usuario);
        if (user != null) {

            String tokenJwt = jwtUtil.create(String.valueOf(user.getId()), user.getEmail());
            return tokenJwt;
        }
        return "FAIL";
    }
}
