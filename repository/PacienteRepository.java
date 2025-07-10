package br.ufms.clinicamedica.repository;
import br.ufms.clinicamedica.model.Endereco;
import br.ufms.clinicamedica.model.Paciente;

import java.time.LocalDate;


public class PacienteRepository extends FileRepository<Paciente, String> {

    public PacienteRepository() {
        super("paciente");
    }

    @Override
    protected String getId(Paciente paciente) {
        return paciente.getCpf(); // CPF como identificador único
    }


    @Override
    protected String entityToText(Paciente paciente) {
        return String.format("%s|%s|%s|%s|%s|%s",
                paciente.getCpf(),
                paciente.getNome(),
                paciente.getEmail(),
                paciente.getEndereco().toText(),  // vai pagar tuDo do endereço de uma vvez
                paciente.getTelefone(),
                paciente.getDataNascimento()
        );
    }

    @Override
    protected Paciente textToEntity(String line) {
        String[] parts = line.split("\\|");
        Endereco endereco = Endereco.fromText(parts[3]); // recria o endereço a partir do texto
        return new Paciente(
                parts[1],                         // nome
                parts[0],                         // cpf
                parts[2],                         // email
                endereco,                         // endereço
                parts[4],                         // telefone
                LocalDate.parse(parts[5])         // data de nascimento
        );
    }
}


