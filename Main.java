package br.ufms.clinicamedica;

import br.ufms.clinicamedica.model.*;
import br.ufms.clinicamedica.repository.*;
import br.ufms.clinicamedica.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Inicializa repositórios
        MedicoRepository medicoRepo = new MedicoRepository();
        PacienteRepository pacienteRepo = new PacienteRepository();
        SecretarioRepository secretarioRepo = new SecretarioRepository();
        ConsultaRepository consultaRepo = new ConsultaRepository(medicoRepo,pacienteRepo);

        // Inicializa serviços
        MedicoService medicoService = new MedicoService(medicoRepo, consultaRepo);
        PacienteService pacienteService = new PacienteService(pacienteRepo, consultaRepo);
        SecretarioService secretarioService = new SecretarioService(secretarioRepo);
        ConsultaService consultaService = new ConsultaService(consultaRepo, medicoRepo, pacienteRepo);

        try {
            // Cadastro de médicos
            System.out.println("\n--- Cadastrando Médicos ---");
            Medico medico1 = new Medico("Dr João Silva", "39053344705", "123456SP", "joao@clinica.com", "11987654321", LocalDate.of(1980, 5, 15));
            Medico medico2 = new Medico("Dra Maria Santos", "74695231420", "654321RJ", "maria@clinica.com", "11912345678", LocalDate.of(1975, 8, 22));

            medicoService.salvarMedico(medico1);
            medicoService.salvarMedico(medico2);

            // Cadastro de pacientes
            System.out.println("\n--- Cadastrando Pacientes ---");
            Endereco enderecoPac1 = new Endereco();
            enderecoPac1.setLogradouro("Rua das Flores");
            enderecoPac1.setNumero(123);
            enderecoPac1.setComplemento(null);
            enderecoPac1.setBairro("Centro");
            enderecoPac1.setCep("12345-678");
            enderecoPac1.setCidade("São Paulo");
            enderecoPac1.setEstado(Estado.SP);

            Paciente paciente1 = new Paciente("Carlos Oliveira", "52998224725", "CarlosO@gmail.com", enderecoPac1, "11956588888", LocalDate.of(1990, 3, 10));

            Endereco enderecoPac2 = new Endereco();
            enderecoPac2.setLogradouro("Av. Brasil");
            enderecoPac2.setNumero(456);
            enderecoPac2.setComplemento("Apto 101");
            enderecoPac2.setBairro("Jardim Paulista");
            enderecoPac2.setCep("98765-432");
            enderecoPac2.setCidade("São Paulo");
            enderecoPac2.setEstado(Estado.SP);

            Paciente paciente2 = new Paciente("Ana Pereira", "34565498700", "AnaPereira@gmail.com", enderecoPac2, "11988887777", LocalDate.of(1985, 7, 20));

            pacienteService.salvarPaciente(paciente1);
            pacienteService.salvarPaciente(paciente2);

            // Cadastro de secretários
            System.out.println("\n--- Cadastrando Secretários ---");
            Endereco enderecoSec = new Endereco();
            enderecoSec.setLogradouro("Rua Principal");
            enderecoSec.setNumero(789);
            enderecoSec.setComplemento(null);
            enderecoSec.setBairro("Centro");
            enderecoSec.setCep("12345-000");
            enderecoSec.setCidade("São Paulo");
            enderecoSec.setEstado(Estado.SP);

            Secretario secretario = new Secretario("Fernanda Almeida", "98765432100", enderecoSec, "fernanda@clinica.com", "11977665544", LocalDate.of(1988, 11, 5), LocalDate.now(), 3500.0);

            secretarioService.salvarSecretario(secretario);

            // Agendamento de consultas
            System.out.println("\n--- Agendando Consultas ---");
            LocalDateTime amanha10h = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
            LocalDateTime amanha14h = LocalDateTime.now().plusDays(1).withHour(14).withMinute(0);

            Consulta consulta1 = consultaService.agendarConsulta("39053344705", "52998224725", "Dor de cabeça persistente", amanha10h, 250.0);
            System.out.println("Consulta 1 agendada: " + consulta1.getCodigo() + " - Médico: " + consulta1.getMedico().getNome());

            Consulta consulta2 = consultaService.agendarConsulta("74695231420", "34565498700", "Check-up anual", amanha14h, 300.0);
            System.out.println("Consulta 2 agendada: " + consulta2.getCodigo() + " - Médico: " + consulta2.getMedico().getNome());

            System.out.println("\n--- Suas Consultas Agendadas ---");
            consultaService.listarTodasConsultas()
                    .forEach(System.out::println);

            // Testando listagens
            System.out.println("\n--- Listando Médicos ---");
            List<Medico> medicos = medicoService.listarMedicos();
            medicos.forEach(m -> System.out.println(m.getNome() + " - CRM: " + m.getCrm()));

            System.out.println("\n--- Listando Pacientes ---");
            List<Paciente> pacientes = pacienteService.listarPacientes();
            pacientes.forEach(p -> System.out.println(p.getNome() + " - CPF: " + p.getCpf()));

            System.out.println("\n--- Listando Consultas do Dr. João Silva ---");
            List<Consulta> consultasMedico = consultaService.listarConsultasPorMedico("39053344705");
            consultasMedico.forEach(c -> System.out.println(
                    "Paciente: " + c.getPaciente().getNome() +
                            ", Data: " + c.getDataHora() +
                            ", Valor: R$" + c.getValor()
            ));

            System.out.println("\n--- Listando Consultas de Carlos Oliveira ---");
            List<Consulta> consultasPaciente = consultaService.listarConsultasPorPaciente("52998224725");
            consultasPaciente.forEach(c -> System.out.println(
                    "Médico: " + c.getMedico().getNome() +
                            ", Data: " + c.getDataHora()
            ));

            // Testando exclusão
            System.out.println("\n--- Cancelando uma Consulta ---");
            consultaService.cancelarConsulta(String.valueOf(consulta1.getCodigo()));
            System.out.println("Consulta cancelada: " + consulta1.getCodigo());

            // Verificando consultas após cancelamento
            System.out.println("\n--- Consultas Ativas do Dr. João Silva ---");
            consultasMedico = consultaService.listarConsultasPorMedico("39053344705");
            if (consultasMedico.isEmpty()) {
                System.out.println("Nenhuma consulta agendada para este médico.");
            } else {
                consultasMedico.forEach(c -> System.out.println(
                        "Paciente: " + c.getPaciente().getNome() +
                                ", Data: " + c.getDataHora()
                ));
            }

        } catch (Exception e) {
            System.err.println("Erro durante a execução: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
