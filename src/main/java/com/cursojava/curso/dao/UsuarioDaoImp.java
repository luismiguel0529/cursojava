package com.cursojava.curso.dao;

import com.cursojava.curso.config.AppConfig;
import com.cursojava.curso.exception.UserNotFoundException;
import com.cursojava.curso.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UsuarioDaoImp implements UsuarioDao {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private AppConfig appConfig;

    @Override
    public List<Usuario> getUsuario() {
        String query = "FROM Usuario";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void deleteUser(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        entityManager.remove(usuario);
    }

    @Override
    public Usuario findById(Long id) {
        return Optional.ofNullable(entityManager.find(Usuario.class, id))
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public void saveUsuario(Usuario usuario) {
        entityManager.merge(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email";
        List<Usuario> u = entityManager.createQuery(query)
                .setParameter("email", usuario.getEmail())
                .getResultList();

        if (u.isEmpty()) {
            return null;
        }
        //Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        //return argon2.verify(u.get(0).getPassword(),usuario.getPassword());
        if (appConfig.argon2().verify(u.get(0).getPassword(), usuario.getPassword())){
            return u.get(0);
        }
        return null;

    }
}
