package br.com.itpacpalmas.api_sig_lab_itpac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Pessoa;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Usuario;

@Repository
public interface PesoaRepository extends JpaRepository<Pessoa, Integer> {
}
