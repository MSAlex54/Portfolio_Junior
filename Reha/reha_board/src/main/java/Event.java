import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Event {

    @Id
    @Column
    private long id;

    @Column
    private String patientName;

    @Column
    private LocalDateTime timeStamp;

    @Column
    private String treatmentName;

    @Column
    private String dose;

    @Column
    private String status;

    @Column
    private String note;

    @Column
    private boolean active;

    public Event() {
    }

    public Event(long id, String patientName, LocalDateTime timeStamp, String treatmentName, String dose, String status, String note, boolean active) {
        this.id = id;
        this.patientName = patientName;
        this.timeStamp = timeStamp;
        this.treatmentName = treatmentName;
        this.dose = dose;
        this.status = status;
        this.note = note;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getTimeStamp() {
        return timeStamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTreatmentName() {
        return treatmentName;
    }

    public void setTreatmentName(String treatmentName) {
        this.treatmentName = treatmentName;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", patientName='" + patientName + '\'' +
                ", timeStamp=" + timeStamp +
                ", treatmentName='" + treatmentName + '\'' +
                ", dose='" + dose + '\'' +
                ", status='" + status + '\'' +
                ", note='" + note + '\'' +
                ", active=" + active +
                '}';
    }
}
