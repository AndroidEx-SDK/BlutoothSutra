package com.androidex.log.parser;

import com.androidex.log.common.LogConstant;

/**
 * @Description: 解析器接口
 */
public interface Parser<T> {
    String LINE_SEPARATOR = LogConstant.BR;

    Class<T> parseClassType();

    String parseString(T t);
}
