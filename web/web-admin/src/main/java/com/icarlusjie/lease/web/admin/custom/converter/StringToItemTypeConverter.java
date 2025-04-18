package com.icarlusjie.lease.web.admin.custom.converter;

import com.icarlusjie.lease.model.enums.ItemType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @ClassName : StringToItemTypeConverter
 * @Description : 自定义Converter,将String转换成ItemType
 * @Author : 吴煌杰
 */
@Component
public class StringToItemTypeConverter implements Converter<String, ItemType> {

    @Override
    public ItemType convert(String code) {
        for (ItemType type:ItemType.values()){
            if (Integer.valueOf(code).equals(type.getCode())){
                return type;
            }
        }

        throw new IllegalArgumentException(code+"code,前端传入参数非法");
    }
}
