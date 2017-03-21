package conkApp

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
/*
 The @Transactional annotation configures the transactional behavior of the
 controller or method. Transactions are used to manage persistence and other
 complicated operations that should be completed together (and potentially
 rolled-back if any one of the steps fails). For more information on
 transactions, see the Grails documentation
 */

class VehicleController {

    static namespace = 'staticscaffolding'

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def valueEstimateService

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        /*
         The PARAMS object is available to all controllers, and contains a
         map of any URL parameters on the request. You can refer to any
         parameter by name to retrieve the value: params.myCustomParameter
         will match this URL parameter: [url]?myCustomParameter=hello.
         */
        respond Vehicle.list(params), model:[vehicleCount: Vehicle.count()]
        /*
         The RESPOND method takes an object to return to the requestor,
         using content negotiation to choose the correct type (for example,
         a request’s Accept header might specify JSON or XML). respond also
         can accept a map of arguments, such as model (which defines the
         way the data is loaded on a page).
         */
    }

    def show(Vehicle vehicle) {
        respond vehicle, model: [estimatedValue: valueEstimateService.getEstimate(vehicle)]
    }

    def create() {
        respond new Vehicle(params)
    }

    @Transactional
    def save(Vehicle vehicle) {
        if (vehicle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (vehicle.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond vehicle.errors, view:'create'
            return
        }

        vehicle.save flush:true

        /*
         REQUEST is available on all controllers, and is an instance
         of the Servlet API’s HttpServletRequest class. You can access
         request headers, store properties in the request scope, and
         get information about the requestor using this object.
         */
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'vehicle.label', default: 'Vehicle'), vehicle.id])
                redirect vehicle
            }
            '*' { respond vehicle, [status: CREATED] }
        }
    }

    def edit(Vehicle vehicle) {
        respond vehicle
    }

    @Transactional
    def update(Vehicle vehicle) {
        if (vehicle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (vehicle.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond vehicle.errors, view:'edit'
            return
        }

        vehicle.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'vehicle.label', default: 'Vehicle'), vehicle.id])
                redirect vehicle
            }
            '*'{ respond vehicle, [status: OK] }
        }
    }
    /*
     FLASH is a map that stores objects within the session for the
     next request, automatically clearing them after that next
     request completes. This is useful for passing error messages
     or other data that you want the next request to access.

     The REDIRECT method is a simple one - it allows the action to
     redirect the request to another action, controller, or a URI.
     You can also pass along parameters with the redirect.
     */

    @Transactional
    def delete(Vehicle vehicle) {

        if (vehicle == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        vehicle.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'vehicle.label', default: 'Vehicle'), vehicle.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    /*
     The RENDER method is a less sophisticated version of respond - it
     doesn’t perform content negotiation, so you have to specify
     exactly what you want to render. You can render plain text, a
     view or template, an HTTP response code, or any object that has a
     String representation.
     */

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'vehicle.label', default: 'Vehicle'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
