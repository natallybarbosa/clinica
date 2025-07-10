package br.ufms.clinicamedica.service;

import br.ufms.clinicamedica.model.Medico;
import br.ufms.clinicamedica.model.Consulta;
import br.ufms.clinicamedica.repository.MedicoRepository;
import br.ufms.clinicamedica.repository.ConsultaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicoService implements MedicoServiceInterface {

    private final MedicoRepository medicoRepository;
    private final ConsultaRepository consultaRepository;

    public MedicoService(MedicoRepository medicoRepository, ConsultaRepository consultaRepository) {
        this.medicoRepository = medicoRepository;
        this.consultaRepository = consultaRepository;
    }

    @Override
    public void salvarMedico(Medico medico) {
        try {
            medicoRepository.save(medico);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar médico: " + e.getMessage());
        }
    }

    @Override
    public void excluirMedico(Medico medico) {
        List<Consulta> todasConsultas = new ArrayList<>();
        try {
            todasConsultas = new ArrayList<>(consultaRepository.getAll());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar consultas: " + e.getMessage());
        }

        boolean temConsultas = false;
        for (Consulta consulta : todasConsultas) {
            if (consulta.getMedico() != null && consulta.getMedico().getCpf().equals(medico.getCpf())) {
                temConsultas = true;
                break;
            }
        }

        if (temConsultas) {
            throw new RuntimeException("Médico tem consultas agendadas!");
        }

        try {
            medicoRepository.delete(medico.getCpf());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir médico: " + e.getMessage());
        }
    }

    @Override
    public Optional<Medico> buscarMedico(String cpf) {
        List<Medico> todosMedicos = new ArrayList<>();
        try {
            todosMedicos = new ArrayList<>(medicoRepository.getAll());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar médicos: " + e.getMessage());
        }

        for (Medico medico : todosMedicos) {
            if (medico.getCpf().equals(cpf)) {
                return Optional.of(medico);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Medico> buscarMedicoPorCRM(String crm) {
        List<Medico> todosMedicos = new ArrayList<>();
        try {
            todosMedicos = new ArrayList<>(medicoRepository.getAll());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar médicos: " + e.getMessage());
        }

        for (Medico medico : todosMedicos) {
            if (medico.getCrm().equals(crm)) {
                return Optional.of(medico);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Medico> listarMedicos() {
        try {
            return new ArrayList<>(medicoRepository.getAll());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar médicos: " + e.getMessage());
        }
    }

    @Override
    public void prescreverReceita(String crm, String codigoConsulta, String receita) {
        Optional<Medico> envelopem = buscarMedicoPorCRM(crm);
        if (!envelopem.isPresent()) {
            throw new RuntimeException("Médico não encontrado!");
        }
        Medico medico = envelopem.get();

        List<Consulta> todasConsultas = new ArrayList<>();
        try {
            todasConsultas = new ArrayList<>(consultaRepository.getAll());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar consultas: " + e.getMessage());
        }

        Consulta consultaX = null;
        int codigoInt = Integer.parseInt(codigoConsulta);
        for (Consulta consulta : todasConsultas) {
            if (consulta.getCodigo() == codigoInt) {
                consultaX = consulta;
                break;
            }
        }

        if (consultaX == null) {
            throw new RuntimeException("Consulta não encontrada!");
        }

        consultaX.setReceita(receita);
        try {
            consultaRepository.save(consultaX);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar consulta: " + e.getMessage());
        }
    }

    @Override
    public void pedirExames(String crm, String codigoConsulta, List<String> exames) {
        Optional<Medico> envelopem = buscarMedicoPorCRM(crm);
        if (!envelopem.isPresent()) {
            throw new RuntimeException("Médico não encontrado!");
        }
        Medico medico = envelopem.get();

        List<Consulta> todasConsultas = new ArrayList<>();
        try {
            todasConsultas = new ArrayList<>(consultaRepository.getAll());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar consultas: " + e.getMessage());
        }
        Consulta consultaEncontrada = null;
        int codigoInt = Integer.parseInt(codigoConsulta); //converte a pora pra inteiro
        for (Consulta consulta : todasConsultas) {
            if (consulta.getCodigo() == codigoInt) {
                consultaEncontrada = consulta;
                break;
            }
        }

        if (consultaEncontrada == null) {
            throw new RuntimeException("Consulta não encontrada!");
        }
        consultaEncontrada.setExames(exames);
        try {
            consultaRepository.save(consultaEncontrada);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar consulta: " + e.getMessage());
        }
    }
}






