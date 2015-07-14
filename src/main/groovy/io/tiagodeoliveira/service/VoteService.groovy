package io.tiagodeoliveira.service

import groovy.util.logging.Log
import io.tiagodeoliveira.entity.Vote
import io.tiagodeoliveira.repository.VoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by tiago on 05/07/15.
 */
@Log
@Service
class VoteService {

    @Autowired VoteRepository voteRepository

    def saveVote(participant, captchaKey) {
        log.info("Saving vote to database $participant")
        def vote = new Vote(participant: participant, timestamp: new Date())
        voteRepository.save(vote)
    }

}
