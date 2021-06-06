package quick.boot.handler.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
/**
 * @title: Api映射处理器
 * @projectName quick-boot
 * @author 南迷
 * @date 2021/6/5 22:50
 * @description 将所有@Api标识的类加入@ResponseBody返回
 */
public class ApiResponseBodyWrapBean implements InitializingBean {

    private final RequestMappingHandlerAdapter adapter;

    public ApiResponseBodyWrapBean(RequestMappingHandlerAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void afterPropertiesSet() {
        List<HandlerMethodReturnValueHandler> returnValueHandlers = adapter.getReturnValueHandlers();
        if (returnValueHandlers == null || returnValueHandlers.isEmpty()) returnValueHandlers = new ArrayList<>();
        List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>(returnValueHandlers);
        decorateHandlers(handlers);
        adapter.setReturnValueHandlers(handlers);
    }


    private void decorateHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        for (HandlerMethodReturnValueHandler handler : handlers) {
            if (handler instanceof RequestResponseBodyMethodProcessor) {
                ApiResponseBodyWrapHandler decorator = new ApiResponseBodyWrapHandler(handler);
                handlers.set(handlers.indexOf(handler), decorator);
                break;
            }
        }
    }
}