package quick.boot.tools;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import quick.boot.Api;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

/**
 * @title: GenerateRequestMappingInfo
 * @projectName quick-boot
 * @author 南迷
 * @date 2021/6/5 22:58
 * @description RequestMappingInfo 解析生成工具
 */
public final class GenerateRequestMappingInfo {

	public static Boolean isApi(Class<?> handlerType){
		return handlerType.getAnnotation(Api.class) != null;
	}

	public static RequestMappingInfo generateRequestMappingInfo(Method method, Class<?> handlerType){
		return RequestMappingInfo
				.paths(analyzePath(method,handlerType))
				.methods(RequestMethod.values())
				.build();
	}

	public static String analyzePath(Method method, Class<?> handlerType){

		List<String> paths = new ArrayList<>();
		Api classApi = handlerType.getAnnotation(Api.class);
		if ( classApi != null) {
			if (StringUtils.hasText(classApi.prefix())) paths.add(classApi.prefix());
			if (StringUtils.hasText(classApi.mapping())) {
				paths.add(classApi.mapping());
			} else paths.add(analyzePath(handlerType.getSimpleName()));
		}

		Api methodApi = method.getAnnotation(Api.class);
		if (methodApi != null ) {
			// 前缀
			if (StringUtils.hasText(methodApi.prefix())) paths.add(methodApi.prefix());
			if (StringUtils.hasText(methodApi.mapping())) paths.add(methodApi.mapping());
		} else paths.add(analyzePath(method.getName()));



		StringBuilder analyze = new StringBuilder();
		for (String path : paths) {
			if (path.startsWith("/")) {
				analyze.append(path);
			} else {
				analyze.append("/").append(path);
			}
		}
		return analyze.toString();
	}

	private static String analyzePath(String name){
		String prefix = name.substring(0,1);
		String suffix = name.substring(1);
		return prefix.toLowerCase(Locale.ROOT) + suffix;
	}

}
