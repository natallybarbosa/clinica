package br.ufms.clinicamedica.service;

import br.ufms.clinicamedica.model.Secretario;
import br.ufms.clinicamedica.repository.SecretarioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SecretarioService implements SecretarioServiceInterface {

    private final SecretarioRepository secretarioRepository;

    public SecretarioService(SecretarioRepository secretarioRepository) {
        this.secretarioRepository = secretarioRepository;
    }

    @Override
    public void salvarSecretario(Secretario secretario) {
        try {
            secretarioRepository.save(secretario);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar secretário: " + e.getMessage());
        }
    }

    @Override
    public void excluirSecretario(Secretario secretario){
        try {
            secretarioRepository.delete(secretario.getCpf());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao excluir secretário: " + e.getMessage());
        }
    }

    @Override
    public Optional<Secretario> buscarSecretario(String cpf) {
        List<Secretario> todosSecretarios = new ArrayList<>();
        try {
            todosSecretarios = (List<Secretario>) secretarioRepository.getAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar secretários: " + e.getMessage());
        }

        for (Secretario secretario : todosSecretarios) {
            if (secretario.getCpf().equals(cpf)) {
                return Optional.of(secretario);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Secretario> listarSecretarios() {
        try {
            return (List<Secretario>) secretarioRepository.getAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar secretários: " + e.getMessage());
        }
    }
}


