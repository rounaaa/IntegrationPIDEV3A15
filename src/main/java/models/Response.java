package models;

import java.time.LocalDateTime;

public class Response {
    private int id;
    private int idReclamation;
    private String responseText;
    private LocalDateTime dateResponse;

    public Response(int idReclamation, String responseText) {
        this.idReclamation = idReclamation;
        this.responseText = responseText;
        this.dateResponse = LocalDateTime.now();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdReclamation() { return idReclamation; }
    public void setIdReclamation(int idReclamation) { this.idReclamation = idReclamation; }

    public String getResponseText() { return responseText; }
    public void setResponseText(String responseText) { this.responseText = responseText; }

    public LocalDateTime getDateResponse() { return dateResponse; }
    public void setDateResponse(LocalDateTime dateResponse) { this.dateResponse = dateResponse; }

    @Override
    public String toString() {
        return "Response{" +
                "id=" + id +
                ", idReclamation=" + idReclamation +
                ", responseText='" + responseText + '\'' +
                ", dateResponse=" + dateResponse +
                '}';
    }
}
