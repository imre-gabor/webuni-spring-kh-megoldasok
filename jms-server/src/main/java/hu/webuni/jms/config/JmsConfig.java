package hu.webuni.jms.config;

import org.apache.activemq.broker.BrokerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsConfig {

	@Bean
	public BrokerService broker() throws Exception {
		BrokerService brokerService = new BrokerService();
		brokerService.addConnector("tcp://localhost:9998");
		return brokerService;
	}
}

