package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Status;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.StatusRepository;

@RestController
@RequestMapping(value = "api/status")
//@CrossOrigin
public class StatusController {
    @Autowired
    StatusRepository statusRepository ;

    @GetMapping("getAll/{filtro}")
public List<Status> getAll(@PathVariable (value = "filtro") boolean filtro){
    List<Status> retorno = statusRepository.findAll();
    if (filtro) {
        retorno.removeIf(p -> !p.isAtivo()); 
    }
    return retorno;
}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable (value = "id") Integer id ) {
    	if(id != 1 && id != 2) {
    		statusRepository.deleteById(id);
    		return ResponseEntity.ok("Status deletado com sucesso!");
    	}
    	else {
    		return ResponseEntity.ok("Esse status não pode ser deletado");
    	}
    	
    	
    	
    	
    }
    
    
    @PostMapping("/cadastrar")
    public ResponseEntity<Status> cadastrar(@RequestBody Status Status) {
        Status statusSalvo;
        
        try {
            statusSalvo = statusRepository.save(Status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(statusSalvo);
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Status> atualizar(@RequestBody Status status) {
        Status statusSalvo;
        try {
           
            statusSalvo = statusRepository.save(status);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(statusSalvo);
    }

    @GetMapping("/buscarTodos")
    public List<Status> buscarTodos() {
        return statusRepository.buscarTodos();
    }
    
   
   

}
