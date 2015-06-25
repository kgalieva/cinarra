package com.cinarra.auction.config;

import com.cinarra.auction.common.profiles.Embedded;
import kafka.admin.TopicCommand;
import kafka.server.KafkaConfig;
import kafka.server.KafkaServerStartable;
import org.apache.zookeeper.server.NIOServerCnxnFactory;
import org.apache.zookeeper.server.ServerCnxnFactory;
import org.apache.zookeeper.server.ZooKeeperServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.Properties;

@Configuration
@Embedded
@PropertySource("classpath:kafkaEmbedded.properties")
@EnableAutoConfiguration
public class KafkaEmbeddedConfig implements KafkaConfiguration {
    @Value("${kafka.topic}")
    private String topicName;
    @Value("${kafka.port}")
    private String brokerPort;
    @Value("${kafka.host}")
    private String brokerHost;
    @Value("#{'${kafka.host}' + ':' + '${kafka.port}'}")
    private String brokerAddress;
    @Value("${zookeeper.port}")
    private int zookeeperPort;
    @Value("${zookeeper.host}")
    private String zookeeperHost;
    @Value("#{'${zookeeper.host}' + ':' + '${zookeeper.port}'}")
    private String zookeeperAddress;

    @Bean(destroyMethod = "shutdown")
    public ServerCnxnFactory zooKeeperServer() throws Exception {
        File snapshotDir = Files.createTempDirectory("zookeeper-snapshot").toFile();
        File logDir = Files.createTempDirectory("zookeeper-logs").toFile();
        snapshotDir.deleteOnExit();
        logDir.deleteOnExit();

        int tickTime = 500;
        ZooKeeperServer zkServer = new ZooKeeperServer(snapshotDir, logDir, tickTime);
        ServerCnxnFactory factory = NIOServerCnxnFactory.createFactory();
        factory.configure(new InetSocketAddress(zookeeperHost, zookeeperPort), 16);
        factory.startup(zkServer);
        return factory;
    }

    @Bean(initMethod = "startup", destroyMethod = "shutdown")
    @DependsOn("zooKeeperServer")
    public KafkaServerStartable kafkaServerStartable() throws Exception {
        File logDir = Files.createTempDirectory("kafka").toFile();
        logDir.deleteOnExit();
        Properties properties = new Properties();
        properties.setProperty("zookeeper.connect", zookeeperAddress);
        properties.setProperty("broker.id", "1");
        properties.setProperty("host.name", brokerHost);
        properties.setProperty("port", brokerPort);
        properties.setProperty("log.dir", logDir.getAbsolutePath());
        properties.setProperty("log.flush.interval.messages", String.valueOf(1));

        return new KafkaServerStartable(new KafkaConfig(properties));
    }

    @Bean(name = "topic")
    @DependsOn("kafkaServerStartable")
    public String crateTopic() {
        String[] arguments = new String[9];
        arguments[0] = "--create";
        arguments[1] = "--zookeeper";
        arguments[2] = zookeeperAddress;
        arguments[3] = "--replication-factor";
        arguments[4] = "1";
        arguments[5] = "--partitions";
        arguments[6] = "1";
        arguments[7] = "--topic";
        arguments[8] = topicName;
        TopicCommand.main(arguments);
        return topicName;
    }

    public String getTopic() {
        return topicName;
    }

    public String getBrokerAddress() {
        return brokerAddress;
    }

    public String getZookeeperAddress() {
        return zookeeperAddress;
    }

}