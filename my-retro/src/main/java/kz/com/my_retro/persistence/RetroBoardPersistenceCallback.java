package kz.com.my_retro.persistence;

import kz.com.my_retro.board.RetroBoard;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.event.ReactiveBeforeConvertCallback;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class RetroBoardPersistenceCallback implements ReactiveBeforeConvertCallback<RetroBoard> {

    private static final Logger LOG = LoggerFactory.getLogger(RetroBoardPersistenceCallback.class);

    @Override
    public Publisher<RetroBoard> onBeforeConvert(RetroBoard entity, String collection) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        if (entity.getCards() == null) {
            entity.setCards(new ArrayList<>());
        }
        LOG.info("[CALLBACK] onBeforeConvert {}", entity);
        return Mono.just(entity);
    }
}
