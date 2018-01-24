package com.josecullen.reactor.service;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.rabbitmq.client.Delivery;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.NodeClientFactoryBean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import reactor.core.publisher.Flux;
import reactor.rabbitmq.ReactorRabbitMq;
import reactor.rabbitmq.Receiver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
@SpringBootApplication()
@EnableAutoConfiguration
@EnableReactiveMongoRepositories("com.josecullen.reactor.domain.repository")
@EnableMongoRepositories
@EnableElasticsearchRepositories("com.josecullen.reactor.domain.repository")
@ComponentScan(basePackages = "com.josecullen.reactor")
@EntityScan(basePackages = "com.josecullen.reactor")
public class App extends AbstractReactiveMongoConfiguration {
    private static final String QUEUE = "demo-queue";

    @Autowired
    ElasticsearchOperations operations;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create();
    }

    @Bean
    public Client client() throws UnknownHostException {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
//        NodeClientFactoryBean bean = new NodeClientFactoryBean(true);
//        bean.setClusterName(UUID.randomUUID().toString());
//        bean.setEnableHttp(false);
//        bean.setPathData("target/elasticsearchTestData");
//        bean.setPathHome("src/test/resources/test-home-dir");

        Client client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));

        return client;
    }

    @Bean
    public Receiver receiver(){
        Receiver receiver = ReactorRabbitMq.createReceiver();
        Flux<Delivery> messages = receiver.consumeAutoAck(QUEUE);
        messages
                .doOnEach(deliverySignal -> System.out.println("message - "
                        + new String(deliverySignal.get().getBody(), UTF_8)))
                .subscribe();
        return receiver;
    }

//    @Bean
//    public ElasticsearchTemplate elasticsearchTemplate(Client client) throws Exception {
//        return new ElasticsearchTemplate(client);
//    }

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(Client client) throws UnknownHostException {

        ElasticsearchTemplate template = new ElasticsearchTemplate(client);

        return template;
    }



    @Override
    protected String getDatabaseName() {
        return "reactive";
    }

    @Override
    public MongoClient reactiveMongoClient() {
        return mongoClient();
    }
}
