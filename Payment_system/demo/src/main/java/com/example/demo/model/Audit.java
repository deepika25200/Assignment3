package com.example.demo.model;
import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String methodName;

    private String parameters;

    private LocalDateTime timestamp;

    public Audit() {
    }

    public Audit(Long id, String methodName, String parameters, LocalDateTime timestamp) {
        this.id = id;
        this.methodName = methodName;
        this.parameters = parameters;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "AuditEntry{" +
                "id=" + id +
                ", methodName='" + methodName + '\'' +
                ", parameters='" + parameters + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}