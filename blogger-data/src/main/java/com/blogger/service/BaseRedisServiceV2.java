package com.blogger.service;

public interface BaseRedisServiceV2<K, F, V> {
    void set(K key, V value);

    void setTimeToLive(K key, long timeOutInDays);

    void hashSet(K key, F field, V value);
}
