package beans.configuration;

import beans.PdfMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.List;

/**
 * Created by Yauhen_Yemelyanau on 7/3/2017.
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"beans"})
@Import({SecurityConfiguration.class})
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
    }

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver bean = new FreeMarkerViewResolver();
        bean.setPrefix("");
        bean.setSuffix(".ftl");
        bean.setCache(false);//for debugging only
        return bean;
    }

    @Bean
    public FreeMarkerConfig freeMarkerConfig() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("/WEB-INF/views/");
        return configurer;
    }

    @Bean
    public ResourceBundleViewResolver resourceBundleViewResolver() {
        ResourceBundleViewResolver rbvr = new ResourceBundleViewResolver();
        rbvr.setCache(false);//for debugging only
        rbvr.setBasename("WEB-INF/views");
        return rbvr;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter());
        converters.add(pdfMessageConverter());
        super.configureMessageConverters(converters);
    }

    @Bean
    MappingJackson2HttpMessageConverter converter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    PdfMessageConverter pdfMessageConverter() {
        return new PdfMessageConverter();
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .defaultContentType(MediaType.TEXT_HTML)
                .mediaType("pdf", new MediaType("application", "pdf"))
                .mediaType("json", MediaType.APPLICATION_JSON)
                .parameterName("mediaType")
                .favorParameter(true)
                .favorPathExtension(true)
                .ignoreUnknownPathExtensions(false)
                .ignoreAcceptHeader(true)
                .useJaf(false);
    }


    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getMultipartResolver() {
        return new CommonsMultipartResolver();
    }

}
