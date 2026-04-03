package kz.com.my_retro.advice;

import kz.com.my_retro.board.RetroBoard;
import kz.com.my_retro.exception.RetroBoardNotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Aspect
public class RetroBoardAdvice {

    private static final Logger LOG = LoggerFactory.getLogger(RetroBoardAdvice.class);

    @Around("execution(* kz.com.my_retro.persistence.RetroBoardRepository.findById(..))")
    public Object checkFindRetroBoard(ProceedingJoinPoint pjp) throws Throwable {
        LOG.info("[ADVICE] {}", pjp.getSignature().getName());
        try {
            return (Mono<RetroBoard>) pjp.proceed(
                    new Object[]{ UUID.fromString(pjp.getArgs()[0].toString()) }
            );
        } catch (NullPointerException e) {
            throw new RetroBoardNotFoundException();
        }
    }
}
