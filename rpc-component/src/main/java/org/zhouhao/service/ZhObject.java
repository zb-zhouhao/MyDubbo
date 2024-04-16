package org.zhouhao.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@Data
@AllArgsConstructor
public class ZhObject implements Serializable {
    private Integer id;
    private String message;
}
