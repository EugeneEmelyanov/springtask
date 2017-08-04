package beans.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class SOAPAppConfig extends WsConfigurerAdapter {


    @Bean(name = "events")
    public DefaultWsdl11Definition eventsWsdl11Definition(XsdSchema eventsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("EventPort");
        wsdl11Definition.setLocationUri("/events");
        wsdl11Definition.setTargetNamespace("http://epam.com/springcourse/");
        wsdl11Definition.setSchema(eventsSchema);
        return wsdl11Definition;
    }

    @Bean(name="users")
    public DefaultWsdl11Definition userstWsdl11Definition(XsdSchema usersSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("UserPort");
        wsdl11Definition.setLocationUri("/users");
        wsdl11Definition.setTargetNamespace("http://epam.com/springcourse/");
        wsdl11Definition.setSchema(usersSchema);
        return wsdl11Definition;
    }


    @Bean
    public XsdSchema eventsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("schemas/Event.xsd"));
    }

    @Bean
    public XsdSchema usersSchema() {
        return new SimpleXsdSchema(new ClassPathResource("schemas/User.xsd"));
    }
}
