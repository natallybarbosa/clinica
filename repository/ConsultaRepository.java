package br.ufms.clinicamedica.repository;

import br.ufms.clinicamedica.model.Consulta;
import br.ufms.clinicamedica.model.Medico;
import br.ufms.clinicamedica.model.Paciente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class ConsultaRepository extends FileRepository<Consulta, String> {

    private MedicoRepository medicoRepository;
    private PacienteRepository pacienteRepository;

    // deixar ele vazio até o momento
    public ConsultaRepository() {
        super("consulta");
    }

    // metodo para injetar os repositórios depois que o super já rodou
    public void inicializarRepositorios(MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;

        // tam que recarregar os dados com os repositórios prontos pra não dar null
        this.repository.clear();// limpa o map
        this.repository.putAll(super.load());// vai chamar o load novamente, agr com os dados
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
                consulta.getExames() == null ? "null" : String.join(";", consulta.getExames())
        );
    }

    @Override
    protected Consulta textToEntity(String line) {
        String[] parts = line.split("\\|");
        String cpfMed = parts[1];
        String cpfPac = parts[2];

        Medico medico = null;
        Paciente paciente = null;

        if (medicoRepository != null && pacienteRepository != null) {
            try {
                Optional<Medico> medicoEnvelope = medicoRepository.get(cpfMed);
                if (medicoEnvelope.isPresent()) {
                    medico = medicoEnvelope.get();
                } else {
                    throw new RuntimeException("Médico não encontrado: " + cpfMed);
                }

                Optional<Paciente> pacienteEnvelope = pacienteRepository.get(cpfPac);
                if (pacienteEnvelope.isPresent()) {
                    paciente = pacienteEnvelope.get();
                } else {
                    throw new RuntimeException("Paciente não encontrado: " + cpfPac);
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro ao buscar médico ou paciente: " + e.getMessage());
            }
        } else {

        }

        Consulta consulta = new Consulta(
                medico,
                paciente,
                parts[3],
                LocalDateTime.parse(parts[4]),
                Double.parseDouble(parts[5].replace(",", "."))// pra aceitar virgula
        );

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
