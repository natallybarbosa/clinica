package br.ufms.clinicamedica.service;

import br.ufms.clinicamedica.model.Consulta;
import br.ufms.clinicamedica.model.Medico;
import br.ufms.clinicamedica.model.Paciente;
import br.ufms.clinicamedica.repository.ConsultaRepository;
import br.ufms.clinicamedica.repository.MedicoRepository;
import br.ufms.clinicamedica.repository.PacienteRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultaService implements ConsultaServiceInterface {

    private final ConsultaRepository consultaRepo;
    private final MedicoRepository medicoRepo;
    private final PacienteRepository pacienteRepo;

    public ConsultaService(ConsultaRepository consultaRepo,
                           MedicoRepository medicoRepo,
                           PacienteRepository pacienteRepo) {
        this.consultaRepo = consultaRepo;
        this.medicoRepo = medicoRepo;
        this.pacienteRepo = pacienteRepo;
    }

    @Override
    public Consulta agendarConsulta(String cpfMedico, String cpfPaciente,
                                    String sintomas, LocalDateTime dataHora, double valor) {
        try {
            Optional<Medico> envelopem = medicoRepo.get(cpfMedico);
            if (!envelopem.isPresent()) {
                throw new IllegalArgumentException("Médico não encontrado : " + cpfMedico);
            }
            Medico medico = envelopem.get();

            Optional<Paciente> envelopep = pacienteRepo.get(cpfPaciente);
            if (!envelopep.isPresent()) {
                throw new IllegalArgumentException("Paciente não encontrado : " + cpfPaciente);
            }
            Paciente paciente = envelopep.get();

            List<Consulta> consultasDoMedico = listarConsultasPorMedico(cpfMedico);
            for (Consulta cm : consultasDoMedico) {
                if (cm.getDataHora().equals(dataHora)) {//verifica se o medico ja não tem consulta agendada nesse horario
                    throw new IllegalArgumentException("Médico já possui consulta agendada para este horário.");
                }
            }

            Consulta consulta = new Consulta(medico, paciente, sintomas, dataHora, valor);
            consultaRepo.save(consulta); //instancia new consulta e salvo com consultarepo
            return consulta;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao agendar consulta: " + e.getMessage(), e);
        }
    }

    @Override
    public void cancelarConsulta(String codigoConsulta) {
        try {//pega a chave que é o codigo e trato opitional e mando exception se não achar
            Optional<Consulta> consultaC = consultaRepo.get(codigoConsulta);
            if (!consultaC.isPresent()) {
                throw new IllegalArgumentException("Consulta não encontrada com código: " + codigoConsulta);
            }
            consultaRepo.delete(codigoConsulta);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cancelar consulta: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Consulta> buscarConsulta(String codigoConsulta) {
        try {
            return consultaRepo.get(codigoConsulta);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar consulta: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Consulta> listarConsultasPorMedico(String cpfMedico) { // filtra pra ver se é null e igualdade de cpf
        List<Consulta> consultasFiltradas = new ArrayList<>();
        try {
            List<Consulta> todasConsultas = new ArrayList<>(consultaRepo.getAll());
            for (Consulta consulta : todasConsultas) {
                if (consulta.getMedico() != null && consulta.getMedico().getCpf().equals(cpfMedico)) {
                    consultasFiltradas.add(consulta);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar consultas por médico: " + e.getMessage(), e);
        }
        return consultasFiltradas;
    }

    @Override
    public List<Consulta> listarConsultasPorPaciente(String cpfPaciente) {
        List<Consulta> consultasFiltradas = new ArrayList<>();
        try {
            List<Consulta> todasConsultas = new ArrayList<>(consultaRepo.getAll());
            for (Consulta consulta : todasConsultas) {
                if (consulta.getPaciente() != null && consulta.getPaciente().getCpf().equals(cpfPaciente)) {
                    consultasFiltradas.add(consulta);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar consultas por paciente: " + e.getMessage(), e);
        }
        return consultasFiltradas;
    }

    @Override
    public void atualizarConsulta(Consulta consulta) {
        try {
            consultaRepo.save(consulta);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar consulta: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Consulta> listarTodasConsultas() {
        try {
            return new ArrayList<>(consultaRepo.getAll());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar todas as consultas: " + e.getMessage(), e);
        }
    }
}


