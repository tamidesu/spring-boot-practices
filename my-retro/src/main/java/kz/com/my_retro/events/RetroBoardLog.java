package kz.com.my_retro.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class RetroBoardLog {

    private static final Logger LOG =
            LoggerFactory.getLogger(RetroBoardLog.class);

    @Async
    @EventListener
    public void retroBoardEventHandler(RetroBoardEvent event) {
        LOG.info("EVENT:::RetroBoard[{}] Action[{}] happen at: {}",
                event.getRetroBoardId(),
                event.getAction(),
                event.getHappenAt());
    }
}
