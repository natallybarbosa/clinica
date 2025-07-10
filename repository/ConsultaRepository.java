package br.ufms.clinicamedica.repository;

import br.ufms.clinicamedica.model.Consulta;
import br.ufms.clinicamedica.model.Medico;
import br.ufms.clinicamedica.model.Paciente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;


public class ConsultaRepository extends FileRepository<Consulta, String> {
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public ConsultaRepository(MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        super("consulta");
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;

    }

    @Override
    protected String getId(Consulta consulta) {
        return String.valueOf(consulta.getCodigo());
    }

    @Override
    protected String entityToText(Consulta consulta) {
   return String.format(
                "%s|%s|%s|%s|%s|%.2f|%s|%s",
                consulta.getCodigo(),
                consulta.getMedico().getCpf(),
                consulta.getPaciente().getCpf(),
                consulta.getSintomas(),
                consulta.getDataHora(),
                consulta.getValor(),
                consulta.getReceita() == null ? "null" : consulta.getReceita(),
                consulta.getExames() == null ? "null" : consulta.getExames()
        );
    }

    @Override
    protected Consulta textToEntity(String line) {
        String[] parts = line.split("\\|");

        // Busca o mEdico pelo CPF
        Medico medico = null;
        try {
            for (Medico medico1 : medicoRepository.getAll()) {
                if (medicoRepository.getId(medico1).equals(parts[1])) {
                    medico = medico1;
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar médico: " + e.getMessage());
        }

        Paciente paciente = null;
        try {
            for (Paciente paciente1 : pacienteRepository.getAll()) {
                if (pacienteRepository.getId(paciente1).equals(parts[2])) {
                    paciente = paciente1;
                    break;
                }
            }
            if (paciente == null) {
                throw new RuntimeException("Paciente não encontrado: " + parts[2]);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar paciente: " + e.getMessage());
        }

        Consulta consulta = new Consulta(
                medico,
                paciente,
                parts[3], // sintomas
                LocalDateTime.parse(parts[4]),
                Double.parseDouble(parts[5])
        );

        // checa se não é nula
        if (!parts[6].equals("null")) {
            consulta.setReceita(parts[6]);
        }

        if (!parts[7].equals("null")) {
            consulta.setExames(Arrays.asList(parts[7].split(";")));
        } else {
            consulta.setExames(new ArrayList<>());
        }
        return consulta;
    }
}