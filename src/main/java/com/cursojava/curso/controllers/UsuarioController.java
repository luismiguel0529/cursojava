package com.cursojava.curso.controllers;

import com.cursojava.curso.config.AppConfig;
import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao dao;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private JWTUtil jwtUtil;

    @GetMapping("/usuarios/{id}")
    public Usuario getUsuario(@PathVariable String id) {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("MIguel");
        usuario.setApellido("Rodriguez");
        usuario.setEmail("l.miguel@hotmail.com");
        usuario.setTelefono("123456");
        usuario.setPassword("1234");
        return usuario;
    }

    @GetMapping("/api/usuarios")
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization") String token) {

        if (!validarToken(token)){
            return null;
        }
        return dao.getUsuario();
    }

    @PostMapping("/api/usuarios")
    public void saveUsuarios(@RequestBody Usuario usuario) {
        usuario.setPassword(appConfig.argon2().hash(1, 1024, 1, usuario.getPassword()));
        dao.saveUsuario(usuario);
    }

    @DeleteMapping("/api/usuarios/{id}")
    public void deleteUser(@PathVariable Long id, @RequestHeader(value = "Authorization") String token) {
        if (!validarToken(token)){
            return;
        }
        dao.deleteUser(id);
    }

    @GetMapping("/api/usuarios/{id}")
    public Usuario getUsuario(@PathVariable Long id, @RequestHeader(value = "Authorization") String token) {
        if (!validarToken(token)){
            return null;
        }
        return dao.findById(id);
    }

    private boolean validarToken(String token) {
        String key = jwtUtil.getKey(token);
        return key != null;
    }
}
