package kr.co._39cm.homework.support.domain;

import kr.co._39cm.homework.support.ui.ValueObject;

public class LongTypeIdentifier extends ValueObject<LongTypeIdentifier> {
    private Long id;
    protected LongTypeIdentifier(Long id) {
        this.id = id;
    }

    public Long longValue() {
        return id;
    }

    public Long nextValue() {
        return id + 1;
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[] { id };
    }
}
