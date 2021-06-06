package quick.boot.handler;

import java.lang.annotation.Annotation;
import java.util.Set;

import quick.boot.Api;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * @author 南迷
 * @title: GlobalClassPathBeanDefinitionScanner
 * @projectName quick-develop-test
 * @date 2021-06-04 14:49
 * @description 包扫描器 扫描所有quick-boot自定义的注解
 */
public class ApiClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public static final AnnotationTypeFilter[] annotations = {new AnnotationTypeFilter(Api.class)};

    public ApiClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        return super.doScan(basePackages);
    }

    public void registerTypeFilter(){
        for (AnnotationTypeFilter annotation : annotations) addIncludeFilter(annotation);
    }
}