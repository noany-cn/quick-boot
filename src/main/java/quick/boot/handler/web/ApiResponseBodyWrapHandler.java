package quick.boot.handler.web;

import quick.boot.tools.GenerateRequestMappingInfo;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
/**
 * @title: Api映射处理器
 * @projectName quick-boot
 * @author 南迷
 * @date 2021/6/5 22:50
 * @description 将所有@Api标识的类加入@ResponseBody返回
 */
public class ApiResponseBodyWrapHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public ApiResponseBodyWrapHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        return GenerateRequestMappingInfo.isApi(methodParameter.getDeclaringClass()) || delegate.supportsReturnType(methodParameter);
    }



    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        delegate.handleReturnValue(o, methodParameter, modelAndViewContainer, nativeWebRequest);
    }
}