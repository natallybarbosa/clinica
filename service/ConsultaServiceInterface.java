package br.ufms.clinicamedica.service;

import br.ufms.clinicamedica.model.Consulta;
import br.ufms.clinicamedica.model.Medico;
import br.ufms.clinicamedica.model.Paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConsultaServiceInterface {
    Consulta agendarConsulta(String cpfMedico, String cpfPaciente,
                             String sintomas, LocalDateTime dataHora, double valor);
    void cancelarConsulta(String codigoConsulta);
    Optional<Consulta> buscarConsulta(String codigoConsulta);
    List<Consulta> listarConsultasPorMedico(String cpfMedico);
    List<Consulta> listarConsultasPorPaciente(String cpfPaciente);
    void atualizarConsulta(Consulta consulta);

    List<Consulta> listarTodasConsultas();
}