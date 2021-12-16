package br.com.itpacpalmas.api_sig_lab_itpac.controller;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Agendamento;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Status;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.AgendamentoRepository;

@RestController
@RequestMapping(value = "/api/periodo/Agendamentos")
@CrossOrigin
public class AgendamentoController {
    @Autowired
    AgendamentoRepository agendamentoRepository;

    @GetMapping("getAll/{filtro}")
    public List<Agendamento> getAll(@PathVariable (value = "filtro") String filtro){
        List<Agendamento> retorno = agendamentoRepository.findAll();
        if (filtro != null) {
            retorno.removeIf(p -> {
                if (p.getStatus() != null) {
                    return !(p.getStatus().getDescricao().equals(filtro));
                }
                else{
                    return true;
                }
            }); 
        }
        return retorno;
    }

    @PostMapping("/professor")
    public ResponseEntity<?> cadastrarRecorenteProfessor(
        @RequestBody Agendamento agendamento,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datafim,
        @RequestParam(required = false) String dias) {
        
        if(dataInicio == null || datafim == null||dias==null){
            
            Agendamento agendamentoSalvo;

            try {
                Status auxS = new Status();
                auxS.setId(1);
                agendamento.setStatus(auxS);
                agendamentoSalvo = agendamentoRepository.save(agendamento);
                
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoSalvo);
        }else{
            
            List<DayOfWeek>lDayOfWeeks = new ArrayList<>();
            Arrays.asList(dias.split(",")).forEach(d ->{
                lDayOfWeeks.add(DayOfWeek.of(Integer.parseInt(d))); 
            });

            List<Agendamento> lAgendamentos = new ArrayList<Agendamento>();
            
            while (!(dataInicio.getDayOfYear() == datafim.getDayOfYear())) {

                   if (lDayOfWeeks.contains(dataInicio.getDayOfWeek()) ) {
                        
                        Agendamento aux = new Agendamento();
                        // criando outro objeto aparti da referencia 

                        Status auxS = new Status();
                        auxS.setId(1);
                        
                        aux.setAtivo(agendamento.getAtivo());
                        aux.setData(dataInicio);
                        aux.setHoraInicio(agendamento.getHoraInicio());
                        aux.setHoraFim(agendamento.getHoraFim());
                        aux.setManual(agendamento.getManual());
                        aux.setMotivo(agendamento.getMotivo());
                        aux.setProfessor(agendamento.getProfessor());
                        aux.setSala(agendamento.getSala());
                        aux.setStatus(auxS);
                        aux.setSubgrupo(agendamento.getSubgrupo());
                        lAgendamentos.add(agendamentoRepository.save(aux));
                       
                        // agendamento.setData(dataInicio);
                        // agendamento.setId(null);
                        // lAgendamentos.add(agendamentoRepository.save(agendamento));
                   }
                   dataInicio = dataInicio.plusDays(1);  
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(lAgendamentos);
        }
    }

    @PostMapping("/aluno")
    public ResponseEntity<Agendamento> cadastrarAluno(@RequestBody Agendamento agendamento) {
        Agendamento agendamentoSalvo;

        try {
            Status auxS = new Status();
            auxS.setId(1);
            agendamento.setStatus(auxS);
            agendamentoSalvo = agendamentoRepository.save(agendamento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoSalvo);
    }

    @PostMapping("/tecnico")
    public ResponseEntity<?> cadastrarRecorente(
        @RequestBody Agendamento agendamento,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datafim,
        @RequestParam(required = false) String dias) {


        if(dataInicio == null || datafim == null||dias==null){
            
            Agendamento agendamentoSalvo;

            try {
                agendamentoSalvo = agendamentoRepository.save(agendamento);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoSalvo);
        }else{
            
            List<DayOfWeek>lDayOfWeeks = new ArrayList<>();
            Arrays.asList(dias.split(",")).forEach(d ->{
                lDayOfWeeks.add(DayOfWeek.of(Integer.parseInt(d))); 
            });

            List<Agendamento> lAgendamentos = new ArrayList<Agendamento>();
            
            while (!(dataInicio.getDayOfYear() == datafim.getDayOfYear())) {

                   if (lDayOfWeeks.contains(dataInicio.getDayOfWeek()) ) {
                        
                        Agendamento aux = new Agendamento();
                        // criando outro objeto aparti da referencia 
                        aux.setAtivo(agendamento.getAtivo());
                        aux.setData(dataInicio);
                        aux.setHoraInicio(agendamento.getHoraInicio());
                        aux.setHoraFim(agendamento.getHoraFim());
                        aux.setManual(agendamento.getManual());
                        aux.setMotivo(agendamento.getMotivo());
                        aux.setProfessor(agendamento.getProfessor());
                        aux.setSala(agendamento.getSala());
                        aux.setStatus(agendamento.getStatus());
                        aux.setSubgrupo(agendamento.getSubgrupo());
                        lAgendamentos.add(agendamentoRepository.save(aux));
                        
                        // agendamento.setData(dataInicio);
                        // agendamento.setId(null);
                        // lAgendamentos.add(agendamentoRepository.save(agendamento));
                   }
                   dataInicio = dataInicio.plusDays(1);  
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(lAgendamentos);
        }
    }

    @PutMapping
    public ResponseEntity<Agendamento> atualizar(@RequestBody Agendamento Agendamento) {
        Agendamento AgendamentoSalvo;
        try {

            AgendamentoSalvo = agendamentoRepository.save(Agendamento);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(AgendamentoSalvo);
    }

    @GetMapping
    public List<Agendamento> buscarTodos() {
        return agendamentoRepository.buscarTodos();
    }

    @GetMapping("/{id}")
    public Agendamento listAgendamentoId(@PathVariable(value = "id") int id) {
        return agendamentoRepository.findById(id);

    }

    @PatchMapping(value = "/desativar/{id}")
    public ResponseEntity<Agendamento> disable(@PathVariable(value = "id") Integer id) {
        try {
            Agendamento agendamento = agendamentoRepository.findById(id).get();
            agendamento.setAtivo(false);
            agendamentoRepository.save(agendamento);
            return ResponseEntity.status(HttpStatus.OK).body(agendamento);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PatchMapping(value = "/ativar/{id}")
    public ResponseEntity<Agendamento> ativar(@PathVariable(value = "id") Integer id) {
        try {
            Agendamento agendamento = agendamentoRepository.findById(id).get();
            agendamento.setAtivo(true);
            agendamentoRepository.save(agendamento);
            return ResponseEntity.status(HttpStatus.OK).body(agendamento);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("buscarPorDatas/{data}")
    public List<Agendamento> findByData(@PathVariable(value = "data") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataRecebida) throws ParseException {

        // SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
        // LocalDate dataFormatada = formato.parse(dataRecebida);

        return agendamentoRepository.findByData(dataRecebida);
    }

    @GetMapping("buscarPorPeriodo")
    public List<Agendamento> findByPeriodoData(
        @RequestParam("inicio") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate inicio, 
        @RequestParam("termino") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate termino) throws ParseException {
        
        return agendamentoRepository.findByPeriod(inicio, termino);
    }
}
