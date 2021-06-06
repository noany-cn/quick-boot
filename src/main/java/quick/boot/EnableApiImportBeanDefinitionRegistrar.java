package quick.boot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import quick.boot.handler.ApiImportBeanDefinitionRegistrar;

import org.springframework.context.annotation.Import;

/**
 * @author 南迷
 * @title: EnableGlobalImportBeanDefinitionRegistrar
 * @projectName quick-develop-test
 * @date 2021-06-03 15:42
 * @description
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ApiImportBeanDefinitionRegistrar.class})
public @interface EnableApiImportBeanDefinitionRegistrar {

    String[] basePackages() default {};
}
