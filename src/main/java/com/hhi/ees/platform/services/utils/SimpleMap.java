package com.hhi.ees.platform.services.utils;

import java.util.AbstractMap;
import java.util.Set;

public class SimpleMap<K, V> extends AbstractMap<K, V> {

    @Override
    public Set<Entry<K, V>> entrySet() {
        // return set
        return null;
    }

    public SimpleMap<K, V> puts(K key, V value) {
        this.put(key, value);
        return this;
    };

}
