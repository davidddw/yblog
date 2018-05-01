/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 d05660@163.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.cloud.yblog.config;

import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.cloud.yblog.utils.DateFormatTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by d05660ddw on 2017/4/8.
 */

@EnableWebMvc
@Configuration
@ComponentScan(useDefaultFilters = false, basePackages = {"org.cloud.yblog"})
public class MvcConfiguration extends WebMvcConfigurerAdapter {
    final Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new PostAndPutCommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(500000000);
        return multipartResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Bean(name = "freemarkerConfig")
    public FreeMarkerConfigurer freeMarkerConfigurer() throws IOException, TemplateException {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        Map<String, Object> variables = new HashMap<>();
        variables.put("dateFormat", new DateFormatTag());
        configurer.setFreemarkerVariables(variables);
        configurer.setTemplateLoaderPaths("classpath:/templates/");
        freemarker.template.Configuration configuration = configurer.createConfiguration();
        // Use this for local development. When a template exception occurs,
        // it will format the error using HTML so it can be easily read
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        // Makre sure everything is UTF-8 from the beginning to avoid headaches
        configuration.setDefaultEncoding("UTF-8");
        configuration.setLocale(Locale.CHINA);
        // Apply the configuration settings to the configurer
        configurer.setConfiguration(configuration);
        return configurer;
    }

    /**
     * 浏览器form表单只支持GET与POST请求，而DELETE、PUT等method并不支持
     * 解决不支持put form数据的问题
     */
    class PostAndPutCommonsMultipartResolver extends CommonsMultipartResolver {
        @Override
        public boolean isMultipart(HttpServletRequest request) {
            String method = request.getMethod().toLowerCase();
            //By default, only POST is allowed. Since this is an 'update' we should accept PUT.
            if (!Arrays.asList("put", "post").contains(method)) { return false; }
            String contentType = request.getContentType();
            return (contentType != null &&contentType.toLowerCase().startsWith("multipart/"));
        }
    }
}
