package br.ufms.clinicamedica.model;

public class Endereco {

    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String cidade;
    private Estado estado;

    public Endereco() {} // construtor vazio pra encher e puxar dps

    public Endereco(String logradouro, Integer numero, String complemento, String bairro, String cep, String cidade, Estado estado) {
        setLogradouro(logradouro);
        setNumero(numero);
        setComplemento(complemento);
        setBairro(bairro);
        setCep(cep);
        setCidade(cidade);
        setEstado(estado);
    }

    public Endereco(String logradouro, String numero, String bairro, String cidade, String estadoSigla, String cep) {
        this(logradouro, Integer.parseInt(numero), "", bairro, cep, cidade, Estado.valueOf(estadoSigla));
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        if (logradouro == null || logradouro.isEmpty()) {
            throw new IllegalArgumentException("Logradouro nulo ou vazio");
        }
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        if (numero != null && numero < 0) {
            throw new IllegalArgumentException("Numero inválido");
        }
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        if (complemento != null && complemento.length() > 50) {
            throw new IllegalArgumentException("O complemento deve ter no máximo 50 caracteres");
        }
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String toText() {
        return String.join(",",
                logradouro,
                String.valueOf(numero),
                complemento,
                bairro,
                cep,
                cidade,
                estado.name());
    }

    public static Endereco fromText(String text) {
        String[] parts = text.split(",", -1); // -1 pra manter campos vazios
        return new Endereco( // pra usar chma endereco.frotext e passa tudo de uma vez la
                parts[0],
                Integer.parseInt(parts[1]),
                parts[2],
                parts[3],
                parts[4],
                parts[5],
                Estado.valueOf(parts[6])
        );
    }
}

