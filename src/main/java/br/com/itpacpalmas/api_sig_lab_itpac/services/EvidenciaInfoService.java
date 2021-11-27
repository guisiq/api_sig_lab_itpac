package br.com.itpacpalmas.api_sig_lab_itpac.services;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.itpacpalmas.api_sig_lab_itpac.entities.Agendamento;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Aula;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.Disciplina;
import br.com.itpacpalmas.api_sig_lab_itpac.entities.VO.EvidenciaInfo;
import br.com.itpacpalmas.api_sig_lab_itpac.exception.*;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.AulaRepository;
import br.com.itpacpalmas.api_sig_lab_itpac.repository.DisciplinaRepository;


@Service
public class EvidenciaInfoService {
    @Autowired
    private AulaRepository repo;

    public EvidenciaInfo find(Integer id){
        // EvidenciaInfo info ;
        Aula aula = repo.findById(id).get();
        
        return convertToInfo(aula);
    }
    public EvidenciaInfo convertToInfo(Aula aula) {
        System.out.println("");
        System.out.println("convertToInfo");
        System.out.println("");

        EvidenciaInfo info = new EvidenciaInfo();
        System.out.println("EvidenciaInfo info = new EvidenciaInfo()");
        info.setId(aula.getId());
        System.out.println("info.setId(aula.getId())");
        info.setData(aula.getAgendamento().getData());
        System.out.println("info.setData(aula.getAgendamento().getData()");
        System.out.println("aula:"+ aula);
        System.out.println("aula.getAgendamento():"+ aula.getAgendamento());
        System.out.println("aula.getAgendamento().getSala():"+ aula.getAgendamento().getSala());
        System.out.println("aula.getAgendamento().getSala().getNome():"+ aula.getAgendamento().getSala().getNome());
        //da erro aqui 
        info.setSala(aula.getAgendamento().getSala().getNome());
        System.out.println("info.setSala(aula.getAgendamento().getSala().getNome()");
        info.setSubgrupo(aula.getAgendamento().getSubgrupo().getNome());
        System.out.println("info.setSubgrupo(aula.getAgendamento().getSubgrupo().getNome()");
        info.setIdagendamento(aula.getId());
        System.out.println("info.setIdagendamento(aula.getId()");
        info.setNomeAtividade(aula.getNomeAtividade());
        System.out.println("info.setNomeAtividade(aula.getNomeAtividade()");
        info.setHorasAprendizagem(aula.getHorasAprendizagem());
        System.out.println("info.setHorasAprendizagem(aula.getHorasAprendizagem()");
        info.setCodigo(aula.getCodigo());
        System.out.println("info.setCodigo(aula.getCodigo()");
        info.setArquivosUrl("");
        System.out.println("info.setArquivosUrl()");
        info.setPresencaUrl("");
        System.out.println("info.setPresencaUrl()");
        
        System.out.println("");
        System.out.println("convertToInfo final");
        System.out.println("");
        return info;
    }
    public Aula convertToAula(EvidenciaInfo info) {
        Aula aula = new Aula();
        //DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        //info.setId(aula.getId());
        Agendamento agendamento = new Agendamento();
        agendamento.setId(info.getIdagendamento());
        aula.setAgendamento(agendamento);
        aula.setNomeAtividade(info.getNomeAtividade());
        aula.setHorasAprendizagem(info.getHorasAprendizagem());
        aula.setCodigo(info.getCodigo());
        aula.setId(info.getId());
        return aula;
    }
    public List<EvidenciaInfo> ConvertList(List<Aula> aulas) {
        List<EvidenciaInfo> infos = new ArrayList<EvidenciaInfo>();
        /*
        aulas.forEach(aula ->{
            EvidenciaInfo info = convertToInfo(aula);
            infos.add(info);
        });
        */
        System.out.println("");
        System.out.println("ConvertList");
        System.out.println("");
        for (Aula aula : aulas) {
            EvidenciaInfo info = convertToInfo(aula);
            infos.add(info);
        }
        System.out.println("");
        System.out.println("depois do for");
        System.out.println("");
        return infos;
    }
    public List<EvidenciaInfo> findAll(){
        System.out.println("");
        System.out.println("findAll()");
        System.out.println("");
        return ConvertList(repo.findAll());
    }
    public EvidenciaInfo creat(EvidenciaInfo evidencia) {
        Aula aulaSalva = repo.save(convertToAula(evidencia));
        return convertToInfo(aulaSalva);
    }
    public EvidenciaInfo Delete(Integer id) {
        Aula aula = repo.findById(id).get();
        repo.delete(aula);
        return convertToInfo(aula);
    }
}

