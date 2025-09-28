package com.icsdetec.aprendendospring.controller;

import com.icsdetec.aprendendospring.business.UsuarioService;
import com.icsdetec.aprendendospring.controller.Dtos.UsuarioDTO;
import com.icsdetec.aprendendospring.infrastruture.entity.Usuario;
import com.icsdetec.aprendendospring.infrastruture.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {


   private final UsuarioService usuarioService;
   private final AuthenticationManager authenticationManager;
   private final JwtUtil jwtUtil;

   // Longin do Usuario
   @PostMapping("/login")
   public String loginUsuario(@RequestBody UsuarioDTO usuarioDTO) {
      Authentication authentication = authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
      );
      return "Bearer" + jwtUtil.generateToken(authentication.getName());
   }

   // Criando Usuario no banco
   @PostMapping
   public ResponseEntity<Usuario> salvarUsuario(@RequestBody Usuario usuario) {
      return ResponseEntity.ok(usuarioService.salvarUsuario(usuario));
   }

   // Consulta usuario por Id
   @GetMapping("/id/{id}")
   public ResponseEntity<Usuario> obterUsuarioId(@PathVariable Long id) {
      return ResponseEntity.ok(usuarioService.obterUsuarioId(id));
   }

   // Consulta usuario por Email
   @GetMapping
   public ResponseEntity<Usuario> obterUsuarioEmail(@RequestParam("email") String email) {
      return ResponseEntity.ok(usuarioService.obterUsuarioEmail(email));
   }

   //Consulta todos os usurias
   @GetMapping("{todos}")
   public ResponseEntity<List<Usuario>> todosUsuario() {
      return ResponseEntity.ok(usuarioService.todosUsuario());
   }

   //Excluir usuario por Id
   @DeleteMapping("/id/{id}")
   public ResponseEntity<Void> excluirUsuarioId(@PathVariable Long id) {
      usuarioService.excluirUsuarioId(id);
      return ResponseEntity.ok().build();
   }

   // Excluir usuario por email
   @DeleteMapping("{email}")
   public ResponseEntity<Void> excluirPorEmail(@PathVariable String email) {
      usuarioService.excluirPorEmail(email);
      return ResponseEntity.ok().build();
   }

   //Atualiza o usuario
   @PutMapping
   public ResponseEntity<Usuario> atualizarUsuarioId(@RequestBody Usuario usuario) {
      return ResponseEntity.ok(usuarioService.atualizarUsuarioId(usuario));
   }
}
