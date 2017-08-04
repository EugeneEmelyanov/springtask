package beans.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Created by Yauhen_Yemelyanau on 7/3/2017.
 */
public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {

        useRootContext(container);

        useDispatcherContext(container);

        addFilter(container);


    }

    private void useDispatcherContext(ServletContext container) {
        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(MvcConfig.class); // <-- Use DispatcherConfig.java

        // Define mapping between <servlet> and <servlet-mapping>
        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(
                dispatcherContext));
        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);

        dispatcherContext.register(SOAPAppConfig.class);
        dispatcherContext.setServletContext(container);

        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(dispatcherContext);
        servlet.setTransformWsdlLocations(true);
        ServletRegistration.Dynamic dynamic = container.addServlet("wsdispatcher",servlet);
        dynamic.addMapping("/ws/*");
        dynamic.setLoadOnStartup(1);
    }

    private void addFilter(ServletContext container) {
//        String filterName = "springSecurityFilterChain";
//        String filterBeanName = "springSecurityFilterChain  ";
//        container.addFilter(filterName, new DelegatingFilterProxy(filterBeanName)).addMappingForUrlPatterns(null, true, "/*");
    }

    private void useRootContext(ServletContext container) {
        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(MvcConfig.class); // <-- Use RootConfig.java

        // Manage the lifecycle of the root application context
        container.addListener(new ContextLoaderListener(rootContext));
    }
}
