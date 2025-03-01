package com.product.affiliation;

import com.product.affiliation.data.ProductDataClient;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "product-affiliate", variant = Lumo.DARK)
public class Application implements AppShellConfigurator {

    @Value("${product.request.kafka.topic}")
    String productRequestTopic;

    @Value("${kafka.bootstrap-servers}")
    String brokerServer;

    @Value("${kafka.bootstrap-port}")
    String brokerPort;

    @Value("${kafka.producer-consumer.id}")
    String clientGroupName;

    @Value("${product.kafka.consumer-group.id}")
    String clientConsumerGroupId;

    @Value("${product.response.kafka.topic}")
    String productResponseTopic;

    @Value("${product.http.service.base.url}")
    String productHttpServiceURL;

    @Value("product.hazelcast.distributed.client.config.host")
    String clusterIP;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean(name = "productDataClient")
    public ProductDataClient productDataClient() {
      return new ProductDataClient(productHttpServiceURL);
    }

    //@Bean
    /*public HazelcastInstance hazelcastInstance() throws Exception {
      InputStream configStream = getClass().getClassLoader().getResourceAsStream("hazelcast-cluster-client.xml");

      if (configStream == null) {
        throw new RuntimeException("hazelcast-cluster-client.xml not found in classpath");
      }

      return HazelcastClient.newHazelcastClient(new XmlClientConfigBuilder(configStream).build());

    }*/
}
