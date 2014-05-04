package info.hexin.mongo.example.chapter03.idgenerate;

import java.util.concurrent.atomic.AtomicLong;

import info.hexin.mongo.client.core.mapper.id.IdGenerate;

public class MyIdGenerate implements IdGenerate<Long> {

    AtomicLong atomicLong = new AtomicLong(0);

    @Override
    public Long id() {
        return atomicLong.incrementAndGet();
    }
}
