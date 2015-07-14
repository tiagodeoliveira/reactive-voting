package io.tiagodeoliveira

import groovy.util.logging.Log
import io.tiagodeoliveira.service.VoteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import reactor.Environment
import reactor.bus.EventBus
import reactor.bus.selector.Selectors

/**
 * Created by tiago on 29/06/15.
 */

@Log
@SpringBootApplication
class Application extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired EventBus eventBus
    @Autowired VoteService voteService

    @Bean Environment env() {
        return Environment.initializeIfEmpty().assignErrorJournal()
    }

    @Bean EventBus createEventBus(Environment env) {
        return EventBus.create(env, Environment.THREAD_POOL)
    }

    @Override
    def SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class)
    }

    static main(args) {
        SpringApplication.run Application, args
    }

    @Override
    public void run(String... args) throws Exception {
        eventBus.on(Selectors.object('vote')) { ev ->
            voteService.saveVote(ev.data.participant, ev.data.captchaKey)
        }
    }
}
