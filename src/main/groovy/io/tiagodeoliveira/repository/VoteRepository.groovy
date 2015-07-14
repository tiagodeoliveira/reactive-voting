package io.tiagodeoliveira.repository

import io.tiagodeoliveira.entity.Vote
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

/**
 * Created by tiago on 01/07/15.
 */
interface VoteRepository extends CrudRepository<Vote, Long>, PagingAndSortingRepository<Vote, Long> {
    Long countByParticipant(String participant)

    @Query(value = "SELECT v.participant, sum(1) FROM vote v GROUP BY V.participant order by 1", nativeQuery = true)
    List<String> findTotalByParticipant()

    @Query("select YEAR(timestamp), MONTH(timestamp), DAY(timestamp), HOUR(timestamp), sum(1) from Vote v group by YEAR(timestamp), MONTH(timestamp), DAY(timestamp), HOUR(timestamp)")
    List<String> findTotalByHour()
}
