package io.tiagodeoliveira.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

/**
 * Created by tiago on 01/07/15.
 */
@Entity
class Vote implements Serializable {
    @Id
    @GeneratedValue
    Long id
    String participant
    Date timestamp
}
