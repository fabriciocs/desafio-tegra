package br.com.tegra.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import br.com.tegra.domain.enumeration.ImportStatus;

/**
 * A AirplaneTripImport.
 */
@Entity
@Table(name = "airplane_trip_import")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AirplaneTripImport implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_file")
    private String file;

    @Column(name = "date_time")
    private ZonedDateTime dateTime;

    @Column(name = "mime_type")
    private String mimeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ImportStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public AirplaneTripImport file(String file) {
        this.file = file;
        return this;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public AirplaneTripImport dateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMimeType() {
        return mimeType;
    }

    public AirplaneTripImport mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public ImportStatus getStatus() {
        return status;
    }

    public AirplaneTripImport status(ImportStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(ImportStatus status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AirplaneTripImport airplaneTripImport = (AirplaneTripImport) o;
        if (airplaneTripImport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), airplaneTripImport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AirplaneTripImport{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", dateTime='" + getDateTime() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
