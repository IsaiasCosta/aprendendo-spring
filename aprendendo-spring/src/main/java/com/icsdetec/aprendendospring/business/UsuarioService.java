package com.icsdetec.aprendendospring.business;


import com.icsdetec.aprendendospring.infrastruture.entity.Usuario;
import com.icsdetec.aprendendospring.infrastruture.exceptions.ConflictException;
import com.icsdetec.aprendendospring.infrastruture.exceptions.ResourceNotFoundExecption;
import com.icsdetec.aprendendospring.infrastruture.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UsuarioService {

   private final UsuarioRepository usuarioRepository;
   private final PasswordEncoder passwordEncoder;

   // Regra de negócio: antes de salvar o usuário, verificar se o e-mail já está registrado no sistema.

   public Usuario salvarUsuario(Usuario usuario) {
      try {
         emailExiste(usuario.getEmail());
         usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
         return usuarioRepository.save(usuario);
      } catch (ConflictException e) {
         throw new ConflictException("Esse email já Cadastrato" + e.getCause());
      }
   }


   // Regra de negócio: verificar se o e-mail já existe. Caso exista, lançar uma exceção informando a duplicidade.

   public void emailExiste(String email) {
      try {
         boolean existe = verificaEmailExistente(email);
         if (existe) {
            throw new ClassCastException("Esse email já Cadastrato" + email);
         }
      } catch (ClassCastException e) {
         throw new ClassCastException("Esse email já Cadastrato" + e.getCause());
      }
   }

   // Consulta o repositório para verificar a existência do e-mail.

   public boolean verificaEmailExistente(String email) {
      return usuarioRepository.existsByEmail(email);
   }

   //Consulta usuario por ID
   public Usuario obterUsuarioId(Long id) {
      return usuarioRepository.findById(id).orElse(null);
   }

   //Usuario Por Email
   public Usuario obterUsuarioEmail(String email) {
      return usuarioRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundExecption("Usuario não encontrado  " + email));
   }

   //Todos usuarios
   public List<Usuario> todosUsuario() {
      return usuarioRepository.findAll();

   }

   //Excluir Usuario por ID
   public void excluirUsuarioId(Long id) {
      if (!usuarioRepository.existsById(id)) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
      }
      usuarioRepository.deleteById(id);
   }

   //Usuario excluir por Email
   public void excluirPorEmail(String email) {
      if (!usuarioRepository.existsByEmail(email)) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado!  ");
      }
      usuarioRepository.deleteByEmail(email);
   }

   //Atualizar usuario
   public Usuario atualizarUsuarioId(Usuario usuario) {
      return usuarioRepository.save(usuario);
   }
}
