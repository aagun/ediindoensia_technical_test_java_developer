package dev.aagun.ediindonesia.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HttpStatusCommon {
    SUCCESS("success"),
    FAILED("failed");

    private final String name;
}
