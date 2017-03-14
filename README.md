# conkApp
Trial run on grails app based on Grails guides [tutorial](http://guides.grails.org/creating-your-first-grails-app/guide/index.html)

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
