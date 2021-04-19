package br.com.eduardovpessoa.resource

import br.com.eduardovpessoa.model.Link
import br.com.eduardovpessoa.model.dto.LinkDTO
import br.com.eduardovpessoa.repository.LinkRepository
import javax.enterprise.context.RequestScoped
import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/url")
@RequestScoped
class LinkResource {

    @Inject
    @field:Default
    lateinit var linkRepository: LinkRepository

    @GET
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun list(): Response = Response.ok(linkRepository.findAllLinks()).build()

    @Transactional
    @POST
    @Path("/check")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun check(dto: LinkDTO): Response {
        if (dto.url.isNullOrEmpty())
            return Response.status(Response.Status.BAD_REQUEST).entity("The URL cannot be empty!").build()
        val link: Link? = linkRepository.findUrlByLink(dto.url)
        return if (link == null) {
            Response.status(Response.Status.NOT_FOUND).build()
        } else {
            link.accessCount += 1
            linkRepository.updateAccess(link)
            Response.ok(link).build()
        }
    }


    @Transactional
    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun new(dto: LinkDTO): Response {
        if (dto.url.isNullOrEmpty())
            return Response.status(Response.Status.BAD_REQUEST).entity("The URL cannot be empty!").build()
        val link: Link? = linkRepository.findUrlByLink(dto.url)
        return if (link == null) {
            Response.status(Response.Status.CREATED).entity(linkRepository.saveNewUrl(Link().newLink(dto.url))).build()
        } else {
            Response.ok(link).build()
        }
    }

    @Transactional
    @DELETE
    @Path("/remove")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun remove(): Response {
        linkRepository.deleteAllLinks()
        return Response.ok().build()
    }

}