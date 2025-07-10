package br.ufms.clinicamedica.repository;

import br.ufms.clinicamedica.model.Medico;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MedicoRepository extends FileRepository<Medico, String> {

    public MedicoRepository() {
        super("medico");
    }


    @Override
    protected String getId(Medico medico) {
        return medico.getCpf();
    }

    @Override
    protected String entityToText(Medico medico) {
        return String.format("%s|%s|%s|%s|%s|%s",
                medico.getNome(),
                medico.getCpf(),
                medico.getEmail(),
                medico.getTelefone(),
                medico.getDataNascimento(),
                medico.getCrm());
    }

    @Override
    protected Medico textToEntity(String line) {
        String[] parts = line.split("\\|");
        return new Medico(
                parts[0], // nome
                parts[1], // cpf
                parts[5], // crm
                parts[2], // email
                parts[3] ,// telefone
                LocalDate.parse(parts[4]) // dataNascimentO
        );
    }
}

