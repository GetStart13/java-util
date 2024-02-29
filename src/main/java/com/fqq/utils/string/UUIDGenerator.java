package com.fqq.utils.string;

import java.util.UUID;

/**
 * <p> 2023/5/23 </p>
 * UUID 生成器
 *
 * @author Fqq
 */
public class UUIDGenerator {
    private UUIDGenerator() {
    }

    public static String generateUnique16Chars() {
        UUID uuid = UUID.randomUUID();
        // 获取 uuid 最高有效 64 位数字
        long mostSignificantBits = uuid.getMostSignificantBits();
        // 获取 uuid 最低有效 64 位数字
        long leastSignificantBits = uuid.getLeastSignificantBits();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            // 通过 与 运算，截取比特数据，0xFF：1111 1111
            int mostSignificantIndex = (int) ((mostSignificantBits >> (i * 8)) & 0xFF);
            builder.append(Integer.toHexString(mostSignificantIndex));

            int leastSignificantIndex = (int) ((leastSignificantBits >> (i * 8)) & 0xFF);
            builder.append(Integer.toHexString(leastSignificantIndex));

            if (builder.length() >= 16) {
                break;
            }
        }
        return builder.substring(0, 16);
    }
}
