package org.yaml.schema.parser.internal.utils;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ProviderUtils {

    /**
     * Returns an instance of {@code clazz} interface.
     *
     * @param clazz interface for which the instance is searched for.
     * @return the instance of {@code clazz} interface. Cannot be {@code null}.
     * @throws IllegalStateException if instance of {@code clazz} interface not found.
     */
    public static <T> T provider(Class<T> clazz) {
        T provider = loadProvider(clazz, Thread.currentThread().getContextClassLoader());
        if (provider == null) {
            provider = loadProvider(clazz, ProviderUtils.class.getClassLoader());
            if (provider == null) {
                throw new IllegalStateException(
                        String.format("Instance of the %s interface not found.", clazz.getCanonicalName()));
            }
        }
        return provider;
    }

    private static <T> T loadProvider(Class<T> clazz, ClassLoader classLoader) {
        ServiceLoader<T> loader = ServiceLoader.load(clazz, classLoader);
        Iterator<T> iterator = loader.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

}
