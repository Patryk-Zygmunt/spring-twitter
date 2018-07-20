package com.example.demo.doku;

import com.example.demo.controller.UserController;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class DokuService {
    
    
  public String getDoku() throws ClassNotFoundException {
      final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
// add include filters which matches all the classes (or use your own)
provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));

// get matching classes defined in the package
final Set<BeanDefinition> classes = provider.findCandidateComponents("my.package.name");

// this is how you can load the class type from BeanDefinition instance
for (BeanDefinition bean: classes) {
    Class<?> clazz = Class.forName(bean.getBeanClassName());
    // ... do your magic with the class ...
}
      StringBuilder sb =new StringBuilder();
        List<Method>  methods =  Arrays.asList(UserController.class.getMethods())
                .stream().filter(this::filterAnnotations).collect(Collectors.toList());
     methods.stream().forEach((Method m)->{
          sb.append(m.getAnnotations()).append("</br>");
         sb.append(m.getReturnType()+" ").append("<h2> "+m.getName()+" </h2>").append(m.getTypeParameters()+" ").append("</br>"+System.lineSeparator());        
     });   
     return sb.toString();
        
    }
    
    
  public Boolean filterAnnotations(Method m){
     
      return Arrays.asList(m.getAnnotations())
              .stream().anyMatch(a->a.annotationType().equals(TwitterDoku.class));

  }   
    
    
}
