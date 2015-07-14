package io.tiagodeoliveira.controller

import groovy.util.logging.Log
import io.tiagodeoliveira.repository.VoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*
import reactor.bus.Event
import reactor.bus.EventBus

/**
 * Created by tiago on 30/06/15.
 */
@Log
@RestController
@RequestMapping('/vote')
class VoteController {

    private static final Integer PAGE_SIZE = 50

    @Autowired VoteRepository voteRepository
    @Autowired EventBus eventBus

    @RequestMapping(value = '/', method = RequestMethod.POST)
    def vote(@RequestBody payload) {
        def participant = payload.participant
        def captcha = payload.captcha

        log.info("Voting on participant $participant with captcha hash [$captcha]")

        eventBus.notify('vote', Event.wrap([participant: participant, captchaKey: captcha]))

        return "Success"
    }

    @RequestMapping('/list/{page}')
    def list(@PathVariable('page') Integer page) {
        return voteRepository.findAll(new PageRequest(page, PAGE_SIZE))
    }

    @RequestMapping('/total')
    def getTotal() {
        return voteRepository.count()
    }

    @RequestMapping('/total/{id}')
    def getTotalByParticipant(@PathVariable('id') String id) {
        return voteRepository.countByParticipant(id)
    }

    @RequestMapping('/total/byhour')
    def getTotalByHour() {
        def totalByHour = voteRepository.findTotalByHour()
        log.info("Time frame votes extracted: [${totalByHour}]")

        def grandTotal = 0
        totalByHour.each {
            grandTotal += it[4]
        }

        if (grandTotal) {
            grandTotal = Math.round(grandTotal / totalByHour.size())
        }

        return grandTotal
    }

    @RequestMapping('/total/byparticipant')
    def listTotalByParticipant() {
        return voteRepository.findTotalByParticipant()
    }

    @RequestMapping('/percent/byparticipant')
    def listPercentTotalByParticipant() {
        def result = [:]

        def totals = voteRepository.findTotalByParticipant()
        if (totals) {
            def total = 0
            totals.each {
                total += it[1]
            }
            totals.each {
                result[it[0]] = Math.round((it[1] / total) * 100)
            }
        }
        return result
    }


}
