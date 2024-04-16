package org.zhouhao.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZhServiceImpl implements ZhService {
//    private static final Logger logger = new LoggerFactory.
    private static final  Logger logger = LoggerFactory.getLogger(ZhServiceImpl.class);
    @Override
    public String hello(ZhObject object) {
        logger.info("Server recv messg {}", object.getMessage());
        return "return value: " + object.getId();
    }
}
