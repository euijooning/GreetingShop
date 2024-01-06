package store.greeting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    String currentWorkingDir = System.getProperty("user.dir");

    registry.addResourceHandler("/images/**")
            .addResourceLocations("file:///" + currentWorkingDir + "/");
  }

}
