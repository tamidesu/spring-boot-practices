package kz.com.my_retro.events;

import java.time.LocalDateTime;
import java.util.UUID;

public class RetroBoardEvent {

    private UUID retroBoardId;
    private RetroBoardEventAction action;
    private LocalDateTime happenAt;

    public RetroBoardEvent() {}

    public RetroBoardEvent(UUID retroBoardId,
                           RetroBoardEventAction action,
                           LocalDateTime happenAt) {
        this.retroBoardId = retroBoardId;
        this.action = action;
        this.happenAt = happenAt;
    }

    public UUID getRetroBoardId() { return retroBoardId; }
    public void setRetroBoardId(UUID retroBoardId) { this.retroBoardId = retroBoardId; }

    public RetroBoardEventAction getAction() { return action; }
    public void setAction(RetroBoardEventAction action) { this.action = action; }

    public LocalDateTime getHappenAt() { return happenAt; }
    public void setHappenAt(LocalDateTime happenAt) { this.happenAt = happenAt; }
}
