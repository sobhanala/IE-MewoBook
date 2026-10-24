package application.cache;

import java.util.concurrent.CompletableFuture;

public interface ICacheService {
    <T> CompletableFuture<T> getCacheValueAsync(String key, Class<T> clazz);
    <T> CompletableFuture<Void> setCacheValueAsync(String key, T value, boolean throwExceptionOnFail);
    CompletableFuture<Void> removeCacheValueAsync(String key);

    <T> T getCacheValue(String key, Class<T> clazz);
    <T> void setCacheValue(String key, T value, boolean throwExceptionOnFail);
}