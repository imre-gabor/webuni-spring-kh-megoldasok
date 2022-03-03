package hu.webuni.eduservice.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hu.webuni.eduservice.xmlws.StudentXmlWs;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebServiceConfig {
	
	private final Bus bus;
	private final StudentXmlWs studentXmlWs;

	@Bean
	public Endpoint endPoint() {
		EndpointImpl endpointImpl = new EndpointImpl(bus, studentXmlWs);
		endpointImpl.publish("/student");
		return endpointImpl;
	}
	
}
