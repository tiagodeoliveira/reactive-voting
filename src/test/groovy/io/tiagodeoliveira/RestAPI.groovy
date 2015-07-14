package io.tiagodeoliveira

import groovy.util.logging.Log
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

/**
 * Created by tiago on 29/06/15.
 */
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=0")
@DirtiesContext
@Log
class RestAPI extends Specification {

    @Value('${local.server.port}')
    int port = 0

    def 'test if the css is on the expected format'() {
        when:
            def entity = new TestRestTemplate().getForEntity("http://localhost:" + this.port+ "/webjars/bootstrap/3.3.4/css/bootstrap.min.css", String.class)
        then:
            HttpStatus.OK == entity.getStatusCode()
            entity.getBody().contains("body")
            MediaType.valueOf("text/css;charset=UTF-8") == entity.getHeaders().getContentType()
    }

}
