package y.cloud.java.models_utils;

import java.util.UUID;

public enum NotStated {
    REL {
        @Override public Object value() { return null; }
    },
    PRIMITIVE {
        @Override public Object value() { return -1; }
    },
    BOOL {
        @Override public Object value() { return true; }
    },
    ID {
        @Override public Object value() { return new UUID(0, 0); }
    };



    public abstract Object value();
}
