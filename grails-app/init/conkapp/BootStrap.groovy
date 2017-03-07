package conkapp

import conkApp.Make
import conkApp.Model
import conkApp.Vehicle

class BootStrap {

    def init = { servletContext ->
        def toyota = new Make(name: "Toyota").save()
        def ford = new Make(name: "Ford").save()

        def prius = new Model(name: "Prius", make: toyota).save()
        def camry = new Model(name: "Camry", make: toyota).save()
        def windstar = new Model(name: "Windstar", make: ford).save()

        new Vehicle(name: "sedan", make: toyota, model: camry, year: 2012).save()
        new Vehicle(name: "hybrid", make: toyota, model: prius, year: 2009).save()
        new Vehicle(name: "minivan", make: ford, model: windstar, year: 1990).save()
    }

    def destroy = {
    }
}
