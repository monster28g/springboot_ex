package com.hhi.connected.platform.Entities;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.annotation.Id;

@EntityScan
public class GreetingEntity {

    @Id
    private final long id;
    private final String content;

    public GreetingEntity(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}

