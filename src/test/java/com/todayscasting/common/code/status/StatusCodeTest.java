package com.todayscasting.common.code.status;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class StatusCodeTest {

    @Test
    void statusCodesAreUnique() {
        long distinctCodeCount = Stream.concat(
                        Arrays.stream(SuccessStatus.values()).map(SuccessStatus::getCode),
                        Arrays.stream(ErrorStatus.values()).map(ErrorStatus::getCode)
                )
                .distinct()
                .count();

        int totalCodeCount = SuccessStatus.values().length + ErrorStatus.values().length;
        assertThat(distinctCodeCount).isEqualTo(totalCodeCount);
    }
}
