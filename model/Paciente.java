package br.ufms.clinicamedica.model;
import java.time.LocalDate;

public class Paciente extends Usuario {

    /**
     * Cria um objeto Paciente informando apenas os atributos obrigatórios.
     *
     * @param nome nome
     * @param cpf  cpf
     */
    public Paciente(String nome, String cpf) {
        this(nome, cpf, null, null, null, null);
    }

    /**
     * Cria um objeto Paciente informando todos os atributos.
     *
     * @param nome           nome
     * @param cpf            cpf
     * @param endereco       endereço
     * @param telefone       telefone
     * @param dataNascimento data de nascimento
     */
    public Paciente(String nome, String cpf, String email, Endereco endereco, String telefone, LocalDate dataNascimento) {
        super(nome, cpf, email, telefone, dataNascimento, endereco);
    }

    @Override
    public String toString() {
        return "Paciente{nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", endereco=" + getEndereco() +
                ", telefone='" + getTelefone() + '\'' +
                ", dataNascimento=" + getDataNascimento() +
                '}';
    }
}
