package br.com.itpacpalmas.api_sig_lab_itpac.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Usuario;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.PessoaRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.UsuarioRepository;

import br.com.itpacpalmas.api_sig_lab_itpac.security.jwt.JwtTokenProvider;
import net.bytebuddy.implementation.bytecode.Throw;

@RestController
@RequestMapping(value = "api/usuario")
@CrossOrigin
public class UsuarioController {
	@Autowired
	UsuarioRepository repository;
	@Autowired
	PessoaRepository pessoaRepository;

	@PostMapping
	public ResponseEntity cadastro(@RequestBody Usuario usuario) {
		usuario.setId(null);
		usuario.setAccountNonExpired(true);
		usuario.setAccountNonLocked(true);
		usuario.setCredentialsNonExpired(true);
		usuario.setEnabled(true);

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
		usuario.setPassword( bCryptPasswordEncoder.encode( usuario.getPassword() ) ) ;

		Usuario usu = repository.save(usuario);
			HashMap<String,Object> retorno = new HashMap<String,Object>();
			retorno.put("login", usu.getUserName());
			retorno.put("id", usu.getId());
			retorno.put("pesoaid", usu.getPessoa().getId());
			retorno.put("pesoaCpf", usu.getPessoa().getCpf());
			retorno.put("pesoaNome", usu.getPessoa().getNome());
			
		return ResponseEntity.ok(retorno);

	}

	@PutMapping
	public ResponseEntity editar(@RequestBody Usuario usuario) {
			
			usuario.setPessoa(pessoaRepository.findById(usuario.getPessoa().getId()).get() );
			
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(16);
			usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
			Usuario usu = repository.save(usuario);
			HashMap<String,Object> retorno = new HashMap<String,Object>();
			retorno.put("login", usu.getUserName());
			retorno.put("id", usu.getId());
			retorno.put("pesoaid", usu.getPessoa().getId());
			retorno.put("pesoaCpf", usu.getPessoa().getCpf());
			retorno.put("pesoaNome", usu.getPessoa().getNome());
			
			return ResponseEntity.ok(retorno);

		
	}

	@GetMapping(value = "/listar")
	public ResponseEntity listar() {
		List<Usuario> list =repository.findAll(); 
		List<HashMap<String,Object>> retorno = new ArrayList<>(); 
		list.forEach(u -> {
			HashMap<String,Object> aux = new HashMap<String,Object>();
			aux.put("login", u.getUserName());
			aux.put("id", u.getId());
			aux.put("pesoaid", u.getPessoa().getId());
			aux.put("pesoaCpf", u.getPessoa().getCpf());
			aux.put("pesoaNome", u.getPessoa().getNome());
			retorno.add(aux);
		});
		return ResponseEntity.ok(retorno);
	}
	@GetMapping(value = "/listar2")
	public ResponseEntity listar2() {
		List<Usuario> list =repository.findAll(); 
		List<HashMap<String,Object>> retorno = new ArrayList<>(); 
		list.forEach(u -> {
			HashMap<String,Object> aux = new HashMap<String,Object>();
			aux.put("login", u.getUserName());
			aux.put("id", u.getId());
			aux.put("pesoa", u.getPessoa());
			retorno.add(aux);
		});
		return ResponseEntity.ok(retorno);
	}

}
