package com.onlineordersystem.domain.util;

import com.fasterxml.uuid.Generators;
import java.util.UUID;

public class SequentialUUID {

    private static final int TIMESTAMP_PART_1_START_INDEX = 0;
    private static final int TIMESTAMP_PART_1_END_INDEX = 5;
    private static final int TIMESTAMP_PART_2_START_INDEX = 5;
    private static final int TIMESTAMP_PART_2_END_INDEX = 8;
    private static final int TIMESTAMP_PART_3_START_INDEX = 8;
    private static final int TIMESTAMP_PART_3_END_INDEX = 12;
    private static final int TIMESTAMP_PART_4_START_INDEX = 13;
    private static final int TIMESTAMP_PART_4_END_INDEX = 16;
    private static final int TIMESTAMP_PART_5_START_INDEX = 16;
    private static final int TIMESTAMP_PART_5_END_INDEX = 20;

    private final UUID orderedUUID;

    public SequentialUUID() {
        UUID original = Generators.timeBasedGenerator().generate();
        String ordered = orderTimestampParts(original);
        this.orderedUUID = UUID.fromString(format(ordered));
    }

    public static UUID generate() {
        return new SequentialUUID().orderedUUID;
    }

    private static String orderTimestampParts(UUID uuid) {
        String tmp = uuid.toString().replace("-", "");
        String part1 = tmp.substring(TIMESTAMP_PART_4_START_INDEX, TIMESTAMP_PART_4_END_INDEX);
        String part2 = tmp.substring(TIMESTAMP_PART_3_START_INDEX, TIMESTAMP_PART_3_END_INDEX);
        String part3 = tmp.substring(TIMESTAMP_PART_1_START_INDEX, TIMESTAMP_PART_1_END_INDEX);
        String part4 = tmp.substring(TIMESTAMP_PART_2_START_INDEX, TIMESTAMP_PART_2_END_INDEX);

        return part1 + part2 + part3 + part4 + tmp.substring(TIMESTAMP_PART_4_END_INDEX);
    }

    private static String format(String arrangedUUID) {
        String part1 = arrangedUUID.substring(TIMESTAMP_PART_1_START_INDEX, TIMESTAMP_PART_2_END_INDEX);
        String part2 = arrangedUUID.substring(TIMESTAMP_PART_3_START_INDEX, TIMESTAMP_PART_3_END_INDEX);
        String part3 = arrangedUUID.substring(TIMESTAMP_PART_3_END_INDEX, TIMESTAMP_PART_4_END_INDEX);
        String part4 = arrangedUUID.substring(TIMESTAMP_PART_5_START_INDEX, TIMESTAMP_PART_5_END_INDEX);
        String part5 = arrangedUUID.substring(TIMESTAMP_PART_5_END_INDEX);

        String format = "%s-%s-%s-%s-%s";
        return String.format(format, part1, part2, part3, part4, part5);
    }
}
