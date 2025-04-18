package com.icarlusjie.lease.common.exception;

import com.icarlusjie.lease.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * @ClassName : LeaseException
 * @Description : 自定义异常
 * @Author : 吴煌杰
 */
@Data
public class LeaseException extends RuntimeException{
    private Integer code;

    /**
     * 根据响应结果枚举对象创建异常对象
     * @param resultCodeEnum
     */
    public LeaseException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "LeaseException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}

