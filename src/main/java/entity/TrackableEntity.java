package entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.time.Instant;

public class TrackableEntity {

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    protected void sanitizeTimestamps() {
        if (createdAt == null) {
            createdAt = Timestamp.from(Instant.now());
        }
        if (updatedAt == null) {
            updatedAt = Timestamp.from(Instant.now());
        }
    }

}
