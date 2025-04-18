package com.icarlusjie.lease.web.admin.custom.converter;

import com.icarlusjie.lease.model.enums.BaseEnum;
import com.icarlusjie.lease.model.enums.ItemType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName : StringToBaseEnumConverterFactory
 * @Description : 创建一个工厂用于将String类型的数据转换成基础枚举类
 * @Author : 吴煌杰
 */
@Component
public class StringToBaseEnumConverterFactory implements ConverterFactory<String, BaseEnum> {


    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        return new Converter<String, T>() {
            @Override
            public T convert(String code) {
                for (T  enumConstant:targetType.getEnumConstants()){
                    if (Integer.valueOf(code).equals(enumConstant.getCode())){
                        return enumConstant;
                    }
                }

                throw new IllegalArgumentException(code+"code,前端传入参数非法");
            }
        };
    }
}
