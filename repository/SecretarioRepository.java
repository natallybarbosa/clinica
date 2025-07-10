package br.ufms.clinicamedica.repository;

import br.ufms.clinicamedica.model.Endereco;
import br.ufms.clinicamedica.model.Secretario;
import java.time.LocalDate;

public class SecretarioRepository extends FileRepository<Secretario, String> {

    public SecretarioRepository() {
        super("secretario");
    }

    @Override
    protected String getId(Secretario secretario) {
        return secretario.getCpf(); // funciona como um identificador único
    }

    @Override
    protected String entityToText(Secretario secretario) {
        return String.format("%s|%s|%s|%s|%s|%s|%s|%.2f",
                secretario.getNome(),              // 0
                secretario.getCpf(),               // 1
                secretario.getEmail(),             // 2
                secretario.getTelefone(),          // 3
                secretario.getDataNascimento(),    // 4
                secretario.getDataIngresso(),      // 5
                secretario.getEndereco().toText(), // 6
                secretario.getSalario()            // 7
        );
    }

    @Override
    protected Secretario textToEntity(String line) {
        String[] parts = line.split("\\|");
        Endereco endereco = Endereco.fromText(parts[6]);
        return new Secretario(
                parts[0],                        // nome
                parts[1],                        // cpf
                endereco,                        // endereço
                parts[2],                        // email
                parts[3],                        // telefone
                LocalDate.parse(parts[4]),       // dataNascimento
                LocalDate.parse(parts[5]),       // dataIngresso
                Double.parseDouble(parts[7].replace(",", "."))     // pra passar certnho o salario
        );
    }
}


