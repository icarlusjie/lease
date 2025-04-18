package com.icarlusjie.lease.web.admin.custom.config;

import com.icarlusjie.lease.web.admin.custom.converter.StringToBaseEnumConverterFactory;
import com.icarlusjie.lease.web.admin.custom.converter.StringToItemTypeConverter;
import com.icarlusjie.lease.web.admin.custom.interceptor.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName : WebMvcConfiguration
 * @Description : 配置MVC
 * @Author : 吴煌杰
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private StringToItemTypeConverter stringToItemTypeConverter;

    @Autowired
    private StringToBaseEnumConverterFactory stringToBaseEnumConverterFactory;
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(this.stringToItemTypeConverter);
        registry.addConverterFactory(stringToBaseEnumConverterFactory);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(this.authenticationInterceptor).
                addPathPatterns("/admin/**").
                excludePathPatterns("/admin/login/**");
    }
}
