package br.com.gabriel_pereira.educational.controller;

import br.com.gabriel_pereira.educational.dto.MediaTurmaDto;
import br.com.gabriel_pereira.educational.dto.MediaDisciplinaDto;
import br.com.gabriel_pereira.educational.dto.NotaRelatoriosDto;
import br.com.gabriel_pereira.educational.service.NotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("api/v1/relatorios")
public class RelatorioController {

    private final NotaService notaService;

    public RelatorioController(NotaService notaService) {
        this.notaService = notaService;
    }

    @GetMapping(value = "/notaPorAluno/{id}")
    public ResponseEntity<Set<NotaRelatoriosDto>> notesByStudent(@PathVariable Integer id) {
        Set<NotaRelatoriosDto> notes = notaService.getNotesByAlunoId(id);
        return ResponseEntity.ok(notes);
    }

    @GetMapping(value = "/mediaPorTurma/{id}")
    public ResponseEntity<MediaTurmaDto> averageDtoResponseEntity(@PathVariable Integer id) {
        MediaTurmaDto averageDto = notaService.averageNotesByClass(id);
        return ResponseEntity.ok(averageDto);
    }

    @GetMapping(value = "/mediaPorDisciplina/{id}")
    public ResponseEntity<MediaDisciplinaDto> MediaDisciplinaDtoResponseEntity(@PathVariable Integer id) {
        MediaDisciplinaDto averageDto = notaService.averageNotesByDiscipline(id);
        return ResponseEntity.ok(averageDto);
    }
}
