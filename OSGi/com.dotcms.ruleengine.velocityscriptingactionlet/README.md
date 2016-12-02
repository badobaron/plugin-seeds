
# README
------

This plugin allows to add an Rules Engine Actionlet (VelocityScriptingActionlet) that allow users to execute 
arbitrary velocity code in a rule.  Using this, an admin can leverage the power of Velocity and work directly with the request, response and session objects.

## How to build this example
-------------------------

To install all you need to do is build the JAR. to do this run
```./gradlew jar```
This will build a jar in the build/libs directory

1. To install this bundle:

Copy the bundle jar file inside the Felix OSGI container (dotCMS/felix/load).
        OR
Upload the bundle jar file using the dotCMS UI (CMS Admin->Dynamic Plugins->Upload Plugin).

2. To uninstall this bundle:

Remove the bundle jar file from the Felix OSGI container (dotCMS/felix/load).
        OR
Undeploy the bundle using the dotCMS UI (CMS Admin->Dynamic Plugins->Undeploy).

## How to add a Actionlet OSGI plugin
---------------------------------

--
In order to create this OSGI plugin, you must write the META-INF/MANIFEST
to be inserted into OSGI jar.

This file is being created for you by Gradle. If you need you can alter our config for this but in general our out of the box config should work.
The Gradle plugin uses BND to generate the Manifest. The main reason you need to alter the config is when you need to exclude a package you are including on your Bundle-ClassPath

If you are building the MANIFEST on your own or desire more info on it below is a descrition of what is required
In this MANIFEST you must specify (see template plugin):

Bundle-Name: The name of your bundle

Bundle-SymbolicName: A short an unique name for the bundle

Bundle-Activator: Package and name of your Activator class (example: com.dotmarketing.osgi.ruleengine.Actionlet.Activator)

DynamicImport-Package: *
    Dynamically add required imports the plugin may need without add them explicitly

Import-Package: This is a comma separated list of package's name.
                In this list there must be the packages that you are using inside
                the bundle plugin and that are exported by the dotCMS runtime.

Beware!!!
---------

In order to work inside the Apache Felix OSGI runtime, the import
and export directive must be bidirectional.

The DotCMS must declare the set of packages that will be available to
the OSGI plugins by changing the file: dotCMS/WEB-INF/felix/osgi-extra.conf.
This is possible also using the dotCMS UI (CMS Admin->Dynamic Plugins->Exported Packages).

Only after that exported packages are defined in this list,
a plugin can Import the packages to use them inside the OSGI blundle.

--
--
--
com.dotmarketing.osgi.ruleengine.actionlet.VelocityScriptingActionlet
-----------------------------------------------

Implementation of a Actionlet object.

--
Activator
---------

This bundle activator extends from com.dotmarketing.osgi.GenericBundleActivator and implements BundleActivator.start().
This activator will allow you to register the Actionlet object using the GenericBundleActivator.registerActionlet method

———————————
How to Use
———————————

Once the plugin is installed, then :

1) Go to the Rule Engine portlet
2) Add or modify a rule
3) Add this VelocityScriptingActionlet actionlet passing the following parameter:
    3.1) request attribute key value
    3.2) and the velocity script code to execute.

Note: If The velocity script uses quotes or single quotes, those should be escaped to avoid a validation error on the rule engine. The quotes can be replaced with this values:

1) For single quotes (‘) replace with ${singleQuote}
2) For double quotes (“) replace with ${quote}


for example if you velocity code is:
<ul> #foreach($con in $dotcontent.pull(“+contentType:News”, 2, “random”)) <li>$con.title</li> #end </ul>

then should pass this code:

<ul> #foreach($con in $dotcontent.pull(${quote}+contentType:News${quote}, 2, ${quote}random${quote})) <li>$con.title</li> #end </ul>