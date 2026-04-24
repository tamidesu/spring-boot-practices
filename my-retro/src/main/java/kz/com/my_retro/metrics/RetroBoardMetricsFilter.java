package kz.com.my_retro.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class RetroBoardMetricsFilter implements WebFilter {

    private static final Logger LOG =
            LoggerFactory.getLogger(RetroBoardMetricsFilter.class);

    private final MeterRegistry meterRegistry;

    public RetroBoardMetricsFilter(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             WebFilterChain chain) {
        String uri = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();

        return chain.filter(exchange).doOnSuccess(v -> {
            if (!uri.contains("prometheus") && !uri.contains("actuator")) {
                LOG.info("URI: {} METHOD: {}", uri, method);
                meterRegistry.counter("retro_board_api",
                        "URI", uri,
                        "METHOD", method).increment();
            }
        });
    }
}
