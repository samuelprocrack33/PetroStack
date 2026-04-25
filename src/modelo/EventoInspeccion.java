package modelo;

public class EventoInspeccion {

    private int id;
    private String codigoPozo;
    private String municipio;
    private TipoEvento tipoEvento;
    private String descripcion;
    private String fechaHora;

    public EventoInspeccion(int id, String codigoPozo, String municipio,
                            TipoEvento tipoEvento, String descripcion, String fechaHora) {
        this.id = id;
        this.codigoPozo = codigoPozo;
        this.municipio = municipio;
        this.tipoEvento = tipoEvento;
        this.descripcion = descripcion;
        this.fechaHora = fechaHora;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCodigoPozo() { return codigoPozo; }
    public void setCodigoPozo(String codigoPozo) { this.codigoPozo = codigoPozo; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public TipoEvento getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(TipoEvento tipoEvento) { this.tipoEvento = tipoEvento; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    @Override
    public String toString() {
        return "=== Evento #" + id + " ===" +
               "\n  Pozo      : " + codigoPozo +
               "\n  Municipio : " + municipio +
               "\n  Tipo      : " + tipoEvento +
               "\n  Descripcion: " + descripcion +
               "\n  Fecha/Hora: " + fechaHora;
    }
}
