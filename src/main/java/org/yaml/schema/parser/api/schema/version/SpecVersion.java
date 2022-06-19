package org.yaml.schema.parser.api.schema.version;

public enum SpecVersion {
    DRAFT_01("1.0");

    private final String id;

    SpecVersion(String id) {
        this.id = id;
    }

    /**
     * Returns the current stable version.
     *
     * @return the current stable version.
     */
    public static SpecVersion current() {
        return DRAFT_01;
    }

    /**
     * Returns the version mapped from string.
     *
     * @return the mapped version.
     */
    public static SpecVersion of(String value) {
        for (SpecVersion specVersion : SpecVersion.values()) {
            if (specVersion.id().equals(value)) {
                return specVersion;
            }
        }
        throw new IllegalStateException(String.format("Specification version with id %s not exists.", value));
    }

    /**
     * Returns the identifier of this version, such as {@code "1.0"}.
     *
     * @return the identifier of this version, never be {@code null}.
     */
    public String id() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
