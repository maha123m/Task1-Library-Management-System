package module;

import java.time.LocalDateTime;

public interface BaseEntity {
    int getId();
    LocalDateTime getCreatedDate();
}
