package br.ufms.clinicamedica.service;

import br.ufms.clinicamedica.model.Medico;

import java.util.*;

public interface MedicoServiceInterface {

    void salvarMedico(Medico medico);

    void excluirMedico(Medico medico);

    Optional<Medico> buscarMedico(String cpf);

    Optional<Medico> buscarMedicoPorCRM(String crm);

    Collection<Medico> listarMedicos();
    void prescreverReceita(String crm, String codigoConsulta, String receita);

    void pedirExames(String crm, String codigoConsulta, List<String> exames);
}
