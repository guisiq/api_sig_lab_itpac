package br.com.itpacpalmas.api_sig_lab_itpac.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.*;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
