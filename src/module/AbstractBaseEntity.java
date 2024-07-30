package module;

import java.time.LocalDateTime;

public abstract class AbstractBaseEntity implements BaseEntity {
    private int id;
    private LocalDateTime createdDate;

    public AbstractBaseEntity(int id) {
        this.id = id;
        this.createdDate = LocalDateTime.now();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }
}
