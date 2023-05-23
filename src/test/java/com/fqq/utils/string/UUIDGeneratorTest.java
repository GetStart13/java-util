package com.fqq.utils.string;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;
// 导入所有断言静态方法
import static org.junit.jupiter.api.Assertions.*;

class UUIDGeneratorTest {
    private static final Logger log = Logger.getLogger("UUID 测试类");

    @Test
    void generateUnique16Chars() {
        String uuid = UUIDGenerator.generateUnique16Chars();
        log.info(() -> "uuid: " + uuid);
        assertEquals(16, uuid.length());
    }
}