package br.ufms.clinicamedica.model;

import java.time.LocalDate;

public class Secretario extends Usuario {

    private LocalDate dataIngresso;
    private double salario;

    /**
     * Cria um objeto Secretario informando apenas os atributos obrigatórios.
     *
     * @param nome    nome
     * @param cpf     cpf
     * @param salario salário
     * @param endereco endereço
     */
    public Secretario(String nome, String cpf, double salario, Endereco endereco) {
        this(nome, cpf, endereco, null, null, null, null, salario);
    }

    /**
     * Cria um objeto Secretário especificando todos os campos.
     *
     * @param nome           nome
     * @param cpf            CPF
     * @param email          email
     * @param telefone       telefone
     * @param dataNascimento data de nascimento
     * @param dataIngresso   data de ingresso na clínica
     * @param salario        salário
     */
    public Secretario(String nome, String cpf, Endereco endereco, String email, String telefone,
                      LocalDate dataNascimento, LocalDate dataIngresso, double salario) {
        super(nome, cpf, email, telefone, dataNascimento, endereco);

        this.setDataIngresso(dataIngresso);
        this.setSalario(salario);
    }

    public LocalDate getDataIngresso() {
        return dataIngresso;
    }

    public void setDataIngresso(LocalDate dataIngresso) {
        if (dataIngresso != null && dataIngresso.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de ingresso inválida");
        }
        this.dataIngresso = dataIngresso;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        if (salario < 0) {
            throw new IllegalArgumentException("O salário não pode ser negativo");
        }
        this.salario = salario;
    }

    @Override
    public String toString() {
        return "Secretario{nome='" + getNome() + '\'' +
                ", cpf='" + getCpf() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                ", dataNascimento='" + getDataNascimento() + '\'' +
                ", dataIngresso='" + dataIngresso + '\'' +
                ", salario='" + salario +
                "'}";
    }
}

