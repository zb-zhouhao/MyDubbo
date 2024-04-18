package org.zhouhao.status;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SerializerCode {
    KRYO(0),
    JSON(1);

    private final int code;
}
