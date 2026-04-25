package modelo;

public class Pozo {

    private String codigo;
    private String municipio;
    private String operador;

    public Pozo(String codigo, String municipio, String operador) {
        this.codigo = codigo;
        this.municipio = municipio;
        this.operador = operador;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getOperador() { return operador; }
    public void setOperador(String operador) { this.operador = operador; }

    @Override
    public String toString() {
        return "Pozo{codigo='" + codigo + "', municipio='" + municipio + "', operador='" + operador + "'}";
    }
}
