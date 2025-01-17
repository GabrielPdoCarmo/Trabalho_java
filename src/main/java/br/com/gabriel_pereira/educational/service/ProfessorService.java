package br.com.gabriel_pereira.educational.service;

import br.com.gabriel_pereira.educational.dto.ProfessorDto;
import br.com.gabriel_pereira.educational.model.ProfessorModel;
import br.com.gabriel_pereira.educational.repository.ProfessorRepository;
import br.com.gabriel_pereira.educational.service.exception.ResourceEmailAlreadyExistsException;
import br.com.gabriel_pereira.educational.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public void createProfessor(ProfessorDto professorDto) {
        verifyEmail(professorDto);

        ProfessorModel professorModel = COVERT_DTO_TO_MODEL(professorDto);

        professorRepository.save(professorModel);
    }

    public void updateProfessor(ProfessorDto professorDto, Integer id) {
        ProfessorDto verifyProfessorDto = findById(id);

        if(!verifyProfessorDto.getEmail().equals(professorDto.getEmail())){
            verifyEmail(professorDto);
        }
        
        ProfessorModel professorModel = COVERT_DTO_TO_MODEL(professorDto);
        professorModel.setId(id);
        professorRepository.save(professorModel);
    }

    public ProfessorDto findById (Integer id) {
        ProfessorModel professorModel = professorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id não foi encontrado"));

        return COVERT_MODEL_TO_DTO(professorModel);
    }

    public List<ProfessorDto> findAll () {
        List<ProfessorModel> lstProfessorModel = professorRepository.findAll();
        List<ProfessorDto> lstProfessorDto = new ArrayList<>();

        for(ProfessorModel professorModel : lstProfessorModel) {
            lstProfessorDto.add(COVERT_MODEL_TO_DTO(professorModel));
        }

        return lstProfessorDto;
    }

    public void deleteProfessor(Integer id) {
        ProfessorDto verifyProfessorDto = findById(id);
        ProfessorModel professorModel = COVERT_DTO_TO_MODEL(verifyProfessorDto);
        professorModel.setId(id);
        professorRepository.delete(professorModel);
    }

    private void verifyEmail (ProfessorDto professorDto) {
        Optional<ProfessorModel> optionalProfessorModel = professorRepository.findByEmail(professorDto.getEmail());

        if(optionalProfessorModel.isPresent()){
            throw new ResourceEmailAlreadyExistsException("Já existe um aluno com teste email");
        }
    }

    public static ProfessorModel COVERT_DTO_TO_MODEL(ProfessorDto professorDto) {
        ProfessorModel professorModel = new ProfessorModel();
        professorModel.setId(professorDto.getId() != null ? professorDto.getId() : null);
        professorModel.setNome(professorDto.getNome());
        professorModel.setEmail(professorDto.getEmail());
        professorModel.setTelefone(professorDto.getTelefone());
        professorModel.setEspecialidade(professorDto.getEspecialidade());

        return professorModel;
    }

    public static ProfessorDto COVERT_MODEL_TO_DTO(ProfessorModel professorModel) {
        ProfessorDto professorDto = new ProfessorDto();
        professorDto.setId(professorModel.getId() != null ? professorModel.getId() : null);
        professorDto.setNome(professorModel.getNome());
        professorDto.setEmail(professorModel.getEmail());
        professorDto.setTelefone(professorModel.getTelefone());
        professorDto.setEspecialidade(professorModel.getEspecialidade());

        return professorDto;
    }

}
