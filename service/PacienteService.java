package br.ufms.clinicamedica.service;

import br.ufms.clinicamedica.model.Consulta;
import br.ufms.clinicamedica.model.Paciente;
import br.ufms.clinicamedica.repository.PacienteRepository;
import br.ufms.clinicamedica.repository.ConsultaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PacienteService implements PacienteServiceInterface {

    private final PacienteRepository pacienteRepository;
    private final ConsultaRepository consultaRepository;

    public PacienteService(PacienteRepository pacienteRepository,
                           ConsultaRepository consultaRepository) {
        this.pacienteRepository = pacienteRepository;
        this.consultaRepository = consultaRepository;
    }

    @Override
    public void salvarPaciente(Paciente paciente) {
        try {
            pacienteRepository.save(paciente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public void excluirPaciente(String cpf) {
        try {
            pacienteRepository.delete(cpf);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Paciente> buscarPaciente(String cpf) {
        try {
            return pacienteRepository.get(cpf);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar paciente: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Paciente> listarPacientes() {
        try {
            return new ArrayList<>(pacienteRepository.getAll());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar pacientes: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Consulta> listarConsultasDoPaciente(String cpf) {
        try {
            List<Consulta> todas = new ArrayList<>(consultaRepository.getAll());
            List<Consulta> filtradas = new ArrayList<>();
            for (Consulta c : todas) {
                if (c.getPaciente() != null && c.getPaciente().getCpf().equals(cpf)) {
                    filtradas.add(c);
                }
            }
            return filtradas;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar consultas do paciente: " + e.getMessage(), e);
        }
    }


}





