package net.chrisrichardson.ftgo.consumerservice.domain;

import io.eventuate.tram.commands.common.ChannelMapping;
import io.eventuate.tram.commands.common.DefaultChannelMapping;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.events.publisher.TramEventsPublisherConfiguration;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaParticipantConfiguration;
import net.chrisrichardson.ftgo.common.CommonConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories
@EnableAutoConfiguration
@Import({SagaParticipantConfiguration.class, TramEventsPublisherConfiguration.class, CommonConfiguration.class})
@EnableTransactionManagement
@ComponentScan
public class ConsumerServiceConfiguration {

  @Bean
  public ConsumerServiceCommandHandlers consumerServiceCommandHandlers() {
    return new ConsumerServiceCommandHandlers();
  }

  @Bean
  public ConsumerService consumerService() {
    return new ConsumerService();
  }

  @Bean
  public CommandDispatcher commandDispatcher(ConsumerServiceCommandHandlers consumerServiceCommandHandlers) {
    return new SagaCommandDispatcher("consumerServiceDispatcher", consumerServiceCommandHandlers.commandHandlers());
  }

  @Bean
  public ChannelMapping channelMapping() {
    return new DefaultChannelMapping.DefaultChannelMappingBuilder().build();
  }

}
