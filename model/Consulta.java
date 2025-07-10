package br.ufms.clinicamedica.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Consulta {

    private static int NEXT_ID = 1;
    private final int codigo;
    private final Medico medico;
    private final Paciente paciente;
    private String sintomas;
    private LocalDateTime dataHora;
    private double valor;
    private String receita;
    private List<String> exames;

    public Consulta(Medico medico, Paciente paciente, String sintomas, LocalDateTime dataHora, double valor) {
        this.codigo = NEXT_ID++;
        this.medico = medico;
        this.paciente = paciente;
        this.sintomas = sintomas;
        this.dataHora = dataHora;
        this.valor = valor;
        this.exames = new ArrayList<>();
    }

    public int getCodigo() {
        return codigo;
    }

    public Medico getMedico() {
        return medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getSintomas() {
        return sintomas;
    }

    public void setSintomas(String sintomas) {
        this.sintomas = sintomas;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        if (dataHora == null) {
            throw new IllegalArgumentException("Data e hora não pode ser nula");
        } else if (dataHora.isBefore(LocalDateTime.now().minusYears(10))) {
            throw new IllegalArgumentException("Data e hora muito antiga");
        }
        this.dataHora = dataHora;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Valor não pode ser negativo");
        }
        this.valor = valor;
    }

    public String getReceita() {
        return receita;
    }

    public void setReceita(String receita) {
        if (receita == null || receita.isBlank()) {
            throw new IllegalArgumentException("Receita não pode ser nula ou vazia");
        }
        this.receita = receita.trim();
    }

    public void pedirExame(String exame) {
        if (exame == null || exame.isBlank()) {
            throw new IllegalArgumentException("Exame não pode ser nulo ou vazio");
        }
        this.exames.add(exame.trim());
    }

    public void pedirExames(String... exames) {
        for (String e : exames) {
            pedirExame(e);
        }
    }

    public List<String> getExames() {
        if (this.exames == null) {
            this.exames = new ArrayList<>();
        }
        return Collections.unmodifiableList(this.exames);
    }

    public void setExames(List<String> exames) {
        this.exames = (exames == null) ? new ArrayList<>() : exames;
    }

    public boolean isAtendida() {
        return (receita != null && !receita.isBlank()) || !exames.isEmpty();
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Consulta consulta)) return false;
        return codigo == consulta.codigo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(codigo);
    }

    @Override
    public String toString() {
        return "Consulta\n" +
                "---------------------------\n" +
                "Código: " + codigo + "\n" +
                "Médico: " + medico.getNome() + "\n" +
                "Paciente: " + paciente.getNome() + "\n" +
                "Sintomas: " + sintomas + "\n" +
                "Data e Hora: " + dataHora + "\n" +
                "Valor: R$" + valor + "\n" +
                "Receita: " + (receita == null ? "Nenhuma" : receita) + "\n" +
                "Exames: " + (exames.isEmpty() ? "Nenhum" : String.join(", ", exames)) + "\n";
    }
}


