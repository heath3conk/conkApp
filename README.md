# conkApp
Trial run on grails app based on Grails guides [tutorial](http://guides.grails.org/creating-your-first-grails-app/guide/index.html)

## Run the app
`./gradlew bootRun`

## Scaffolding
 **Dynamic scaffolding - prefab everything**

 `./grailsw create-controller com.whatever.Vehicle` Then, inside the controller, add the static scaffold: 
 ```
 package com.whatever
 class VehicleController {
    static scaffold = Vehicle
}
```
That scaffold creates all the CRUD routes in the controller as well as views and links between them. It uses the associations and relationships among tables that you've already set up. It does all this at runtime so you can't really customize much. (Yes, this seems to be called 'dynamic scaffolding' even though it does its work through the expression `static scaffold = aThing`. And even though there's this other thing that's actually called 'static scaffolding.')

 **Static scaffolding - a *bit* more DIY**

`./grailsw generate-controller com.whatever.Vehicle`

`./grailsw generate-views com.whatever.Vehicle`

`./grailsw generate-all com.whatever.Vehicle` 

For any of these, use the `-force` flag to override existing files: `./grailsw generate-all com.whatever.Vehicle -force`

Static scaffolding generates the code and files for you so you can customize as you like. It also uses the associations set up in the models to properly link tables and actions.

## Views

Grails looks for views in the `app/views` folder. It resolves controller actions by trying to match a folder under view with the name of the controller or you can give the controller more specific instructions to go to a different view.

```
class SomeController {
  def someAction() {
    render view: 'anotherView'
  }
}
```
If the file name is undecorated, Grails will look in the same matching folder for a view with that name. To render a view in another folder, specify a path relative to `app/views`, such as `render view: '/another/view'`.

## Embedded Groovy

To embed the value of a variable: `${variable}`

Include Groovy code in the view with the `<g:x>` and `</g:x>` tags, where *x* might be an *if* block, *each* loop, form, etc.
```
<g:if test="${flash.message}">
  <regular html to render ${flash.message}>
</g:if>
```

## Services

**"Controllers for web logic; services for business logic"**

By convention, Grails will configure any Groovy classes within the grails-app/services directory as services. 

Services will be "wired" as Spring beans in the Grails application context, which means you can refer to them simply by name from any other Spring bean (including controllers and domain classes).

## URL Mapping

```
static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
```
Allow users to customize their accepted formats by adding a parameter to the URL, eg: `http://localhost:8080/home/index.json` so they'll receive the response as a json object.
