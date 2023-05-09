package com.fqq.utils;

import java.util.Objects;

/**
 * <p> 2023/5/9 </p>
 * 异常工具类
 *
 * @author Fqq
 */
public class ThrowableUtil {
    private ThrowableUtil() {
    }

    /**
     * 获取异常原因
     *
     * @param throwable 抛出的异常
     * @return 异常原因
     */
    public static Throwable getRootCause(Throwable throwable) {
        Objects.requireNonNull(throwable);
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }
}
