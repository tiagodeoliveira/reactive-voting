package io.tiagodeoliveira.controller

import groovy.util.logging.Log
import io.tiagodeoliveira.repository.VoteRepository
import spock.lang.Specification

/**
 * Created by tiago on 01/07/15.
 */
@Log
class VoteControllerTest extends Specification {
    def controller
    VoteRepository voteRepository

    def setup() {
        this.controller = new VoteController()
        this.voteRepository = Mock(VoteRepository)
        controller.voteRepository = this.voteRepository
    }

    def "listPercentTotalByParticipant"() {
        setup:
            this.voteRepository.findTotalByParticipant() >> {
                return [["p1", 4], ["p2", 2]]
            }
        when:
            def result = controller.listPercentTotalByParticipant()
        then:
            result.p1 == 67
            result.p2 == 33
    }

    def "listPercentTotalByParticipant fifty fifty"() {
        setup:
            this.voteRepository.findTotalByParticipant() >> {
                return [["p1", 4], ["p2", 4]]
            }
        when:
            def result = controller.listPercentTotalByParticipant()
        then:
            result.p1 == 50
            result.p2 == 50
    }

    def "listPercentTotalByParticipant with no results from database"() {
        when:
            def result = controller.listPercentTotalByParticipant()
        then:
            result == [:]
    }

    def "getTotalByHour with an empty return"() {
        setup:
            this.voteRepository.findTotalByHour() >> {
                return []
            }
        when:
            def result = controller.getTotalByHour()
        then:
            result == 0
    }

    def "getTotalByHour with a return"() {
        setup:
        this.voteRepository.findTotalByHour() >> {
            return [
                    [2015, 10, 10, 11, 3],
                    [2015, 10, 10, 11, 4],
                    [2015, 10, 10, 11, 1],
            ]
        }
        when:
            def result = controller.getTotalByHour()
        then:
            result == 3
    }

    def "getTotalByHour with only one vote"() {
        setup:
            this.voteRepository.findTotalByHour() >> {
                return [
                        [2015, 10, 10, 11, 1],
                ]
            }
        when:
            def result = controller.getTotalByHour()
        then:
            result == 1
    }

}
