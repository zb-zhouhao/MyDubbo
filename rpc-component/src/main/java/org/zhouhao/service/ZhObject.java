package org.zhouhao.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZhObject implements Serializable {
    private Integer id;
    private String message;
}
