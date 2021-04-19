package br.com.eduardovpessoa.model

import org.hibernate.annotations.CreationTimestamp
import java.util.Date
import javax.persistence.Basic
import javax.persistence.Cacheable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType
import kotlin.random.Random

@Entity
@Cacheable
@Table(name = "link")
class Link {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Basic(optional = false)
    @Column(name = "link", length = 100, unique = true, nullable = false)
    lateinit var link: String

    @Basic(optional = false)
    @Column(name = "url_shortened", length = 100, unique = true, nullable = false)
    lateinit var urlShortened: String

    @Column(name = "dt", nullable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    lateinit var dt: Date

    @Basic
    @Column(name = "access_count", nullable = false)
    var accessCount: Int = 0

    internal fun newLink(url: String): Link {
        val obj = Link()
        obj.link = url
        obj.urlShortened = generateNewUrl(5)
        return obj
    }

    private fun generateNewUrl(difficult: Int): String {
        val characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        val randomNumbers = List(difficult) { Random.nextInt(0, 62) }
        val shortenedUrl = StringBuilder("https://edu.io/")
        randomNumbers.forEach {
            shortenedUrl.append(characters[it])
        }
        return shortenedUrl.toString()
    }

}