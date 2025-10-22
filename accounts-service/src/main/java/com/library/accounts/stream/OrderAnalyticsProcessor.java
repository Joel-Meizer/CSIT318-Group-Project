package com.library.accounts.stream;

import com.library.accounts.events.OrderPlacedEvent;
import com.library.accounts.model.UserOrderSummary;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.function.Function;

@Configuration
public class OrderAnalyticsProcessor {

    @Bean
    public Function<KStream<String, OrderPlacedEvent>, KStream<String, UserOrderSummary>> processOrders() {
        return input -> input
                .groupBy((String key, OrderPlacedEvent event) -> event.getCustomerId(),
                        Grouped.with(null, null))
                .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMinutes(5)))
                .count(Materialized.as("user-order-counts"))
                .toStream()
                .map((Windowed<String> windowedKey, Long count) -> {
                    String customerId = windowedKey.key();
                    UserOrderSummary summary = new UserOrderSummary(
                            Long.parseLong(customerId),
                            count
                    );
                    return new KeyValue<>(customerId, summary);
                });
    }
}
