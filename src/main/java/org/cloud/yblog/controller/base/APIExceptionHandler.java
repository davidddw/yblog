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

package org.cloud.yblog.controller.base;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.cloud.yblog.controller.exception.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

@RestController
public class APIExceptionHandler extends AbstractErrorController {
    private static final String ERROR_PATH = "/error";
    private final ErrorAttributes errorAttributes;

    @Autowired
    public APIExceptionHandler(ErrorAttributes errorAttributes) {
        super(errorAttributes);
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(path = ERROR_PATH)
    public ResponseEntity<?> handleError(HttpServletRequest request) {
        HttpStatus status = getStatus(request);

        Map<String, Object> errors = getErrorAttributes(request, false);
        getApiException(request).ifPresent(apiError -> {
            errors.put("message" , apiError.message());
            errors.put("code", apiError.code());
        });
        // If you don't want to expose exception!
        errors.remove("exception");


        return ResponseEntity.status(status).body(errors);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    private Optional<APIException> getApiException(HttpServletRequest request) {
//        RequestAttributes attributes = new ServletRequestAttributes(request);
//        WebRequest webRequest = 
//        Throwable throwable = errorAttributes.getError(attributes);
//        if (throwable instanceof APIException) {
//            APIException exception = (APIException) throwable;
//            return Optional.of(exception);
//        }

        return Optional.empty();
    }
}