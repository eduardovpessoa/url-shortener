package br.com.eduardovpessoa.repository

import br.com.eduardovpessoa.model.Link
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class LinkRepository : PanacheRepository<Link> {
    fun deleteAllLinks() = deleteAll()
    fun findAllLinks() = findAll()
    fun findUrlByLink(link: String): Link? = find("link", link).firstResult()
    fun updateAccess(link: Link) = update(
        "accessCount = ?1 where id = ?2",
        link.accessCount,
        link.id!!
    )

    fun saveNewUrl(link: Link): Link {
        persistAndFlush(link)
        return link
    }
}