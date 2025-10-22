package com.library.accounts.stream;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class InteractiveQueryServiceImpl {

    private final InteractiveQueryService interactiveQueryService;

    @Value("${windowstore.name}")
    private String WINDOWSTORE_NAME;

    @Value("${window.size.ms}")
    private long WINDOW_SIZE_MS;

    public InteractiveQueryServiceImpl(InteractiveQueryService interactiveQueryService) {
        this.interactiveQueryService = interactiveQueryService;
    }

    public List<String> getRecentUserOrders() {
        List<String> userOrders = new ArrayList<>();
        long now = Instant.now().toEpochMilli();

        long windowStart = (now / WINDOW_SIZE_MS) * WINDOW_SIZE_MS - WINDOW_SIZE_MS;
        Instant from = Instant.ofEpochMilli(windowStart);
        Instant to = from.plus(Duration.ofMillis(WINDOW_SIZE_MS));

        try (KeyValueIterator<Windowed<String>, Long> iterator = getStore().fetchAll(from, to)) {
            while (iterator.hasNext()) {
                KeyValue<Windowed<String>, Long> record = iterator.next();
                userOrders.add("UserID " + record.key.key() +
                        " Orders: " + record.value +
                        " Window: " + record.key.window().startTime());
            }
        }
        return userOrders;
    }

    private ReadOnlyWindowStore<String, Long> getStore() {
        return interactiveQueryService.getQueryableStore(WINDOWSTORE_NAME,
                QueryableStoreTypes.windowStore());
    }
}
