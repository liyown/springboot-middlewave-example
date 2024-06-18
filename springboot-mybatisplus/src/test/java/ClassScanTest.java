import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.lang.annotation.Annotation;

/**
 * @author: liuyaowen
 * @poject: springboot
 * @create: 2024-06-11 20:26
 * @Description:
 */
public class ClassScanTest {

    @Test
    public void testClassScan() {
        SimpleBeanDefinitionRegistry simpleBeanDefinitionRegistry = new SimpleBeanDefinitionRegistry();
        ClassPathMapperScanner classPathBeanDefinitionScanner = new ClassPathMapperScanner(simpleBeanDefinitionRegistry);
        // classPathBeanDefinitionScanner.addIncludeFilter(new AnnotationTypeFilter(Annotation.class));
        // classPathBeanDefinitionScanner.addIncludeFilter(new AssignableTypeFilter(Class.class));
        classPathBeanDefinitionScanner.registerFilters();
        classPathBeanDefinitionScanner.scan("com.lyw.mapper");

        System.out.println();

    }
}
