# taylorsuniversity
AEM implementation for Taylors University

This repository contains Adobe Experience Manager 6.3 based implemenation code for Taylors University
 
## Modules

The project is divided into 2 modules:

* taylorsuniversity-project.core: holds `java` code
* taylorsuniversity-project.ui.apps: holds `ui` code 

## Requirements

* Oracle JDK 1.8
* Adobe Experience Manager 6.3
* Apache Maven 3.x

## Downloads

* Oracle JDK 8 - http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
* AEM 6.3 - https://wiki.corp.adobe.com/display/WEM/AEM or https://wiki.corp.adobe.com/display/WEM/Internal+Downloads (pick up AEM 6.3.0)

## Building

To build the bundle and content package and install to a CQ instance, in the root directory execute:

```
$ mvn clean install -PautoInstallPackage
```

To build just the bundle and install to an AEM instance, in the bundle folder, execute:

```
$ mvn clean install -PautoInstallBundle
```

### Documents Share

```
https://adobe.sharepoint.com/sites/APACConsulting/Shared%20Documents/Forms/AllItems.aspx?viewpath=%2Fsites%2FAPACConsulting%2FShared%20Documents&id=%2Fsites%2FAPACConsulting%2FShared%20Documents%2FProjects%2FTaylors%20%28KL%29%2FAEM%2F01%20Requirements
```
 
### Development Methodology/Process

* Below section outlines the development method/process that will be followed for UPS Brand portal implementation.

* Developers to create story branch from develop
* Developers to keep wip in story branch
* When done developers to raise PR to develop.
* Dev Lead to code review and merge if ok.
* Developers to validate checkstyle and integration issues. Fix and again raise PR.
* Developers to write authoring guide. During cyclone, QA to validate authoring guide. 
* When done developer to raise PR. 
* Dev Lead to merge the PR.
* QA to validate and raise defects. When fixed, QA to record demo. Send recording to PO and move story to Complete.
* PO to review demo and mark story to accepted or move to backlog or create a new refactoring story.

#### Branch Terminology

* 'repository': the main git repsitory currently located at https://git.corp.adobe.com/bsinghal/taylorsuniversity
* 'master' branch - branch that will be used to build for UPS stage environment.
* 'develop' branch - branch that will be used to build for GDC development environment. 
* 'feature' - branch prefixed by US will be denote the rally user story number and will be used for development of that particular story. Convention will be ex: US1234

#### Branching and Merging

* A feature branch can only be created from develop branch.
* A feature can only be merged into develop branch through a PR.
* Code can be merged to master branch only through a PR. Usually this will be from develop branch.

#### Basic commands for branching and merging

* Clone:

```
git clone git@git.corp.adobe.com:bsinghal/taylorsuniversity.git
```

* Switch to develop branch:

```
git checkout develop
```

* Create a local feature branch:

```
git checkout -b US1234 develop
```

* Create remote branch and point local to remote:

```
git push --set-upstream origin US1234
```

* Pull changes from develop:

```
git pull
```

* Merge changes from develop:

```	
git merge develop
```

* Commit to local feature changes to HEAD:

```
git commit -a -m "Feature US1234"
```

* Commit feature HEAD changes to remote origin:

```
git push origin
```

#### Assumptions/Bindings/Guidelines

* No feature commit can be done directly to 'develop' branche. Feature will only go in this branch via pull requests (PR). However non-code artifacts (such as this README) can be checked in to develop by a Team Lead.
* No commit can be done directly to 'master' branch. Only exception to master is the initial bare metal code base as this is the default branch and starting place. Revisions to these branches can only be done via PR.
* As far as possible, one PR per feature or bug.
* Meaningful description to be provided on all PR.
* Meaningful description to be provided on all commits.
* Code review will be done on PR and review comments will be provided on PR itself.
* PR for release will only be accepted after checkstyle issues are resolved or marked deferred.
* Peer developers shall review an open PR and provide inputs within the Git interface
* All developers who are done reviewing the PR - must post in a comment that they are done with review
* Developer will address the review comments by pushing more commits to the pull request
* Once all commits are addressed, the pull-request shall be merged
* Each PR must be reviewed - and a comment for the same must be entered

#### Code Review focus

Focus on reviews will be more on the 
* algorithm
* coding best practices
* performance
* resource-leak 
* null-pointers

#### General Coding Guidelines:-
* All constants must be added to existing TaylorsConstants.java. 
* All Utility methods to be used in services, servlets, etc must be written in existing CoreUtils.java
* All Sightly utility methods must be written in existing SightlyUtils.java
* All methods must be preceded by proper Javadoc. 
* All constants must have a proper and detailed Javadoc
* Proper description for rach parameter and return type in Javadoc must be added.
* Method names and Parameter names in methods must be self explanatory. Arguments such as param1, param2 must not be given.
* Logger variable must be decalred as LOGGER. So in code it should appear as LOGGER.debug
* Each catch block must have LOGGER.error
* Each Resource obtained from Repo must be null checked, especially after adaptTo, etc
* Code must not have any checkstyle defects, vulnerabilities, code smells, etc
* Servlets must return proper HTTP codes in case of error, or success with appropriate messages
* Ajax requests must have handling for success and error blocks
* Minimum Java classes should be created as far as possible. Make use of Sightly features that allow you to use implicit objects, SlingModels, etc. 
* No exception consummation (sink) in middle of code. Proper response or exception must be floated to caller for proper handling.
* Every if must have an else. If no action required in else then at minimum LOGGER.debug must be present.
* For common files like UPSConstants and DAMUtils, do no correct indentation so that the PR should only tell the change that is actually done.
* Use i18N dictionary for display labels on html
* No global variables to be defined in servlet, especially if it is not final.
* Please use proper javadocs. Validate the same using mvn javadocs:javadocs command before raising PR.
* ERROR log level should be used in exception blocks and critical checks after which code cannot execute and has to return abruptly. Rest everywhere use DEBUG log level.
* Do not print PII (Personally Identifiable Information) in logs. This also includes passwords, email id, etc.
* In case you are extending/customizing any OOTB java code, JSP, etc, please remove the ADOBE CONFIDENTIAL comment header.
* Please discuss with dev lead before creating a new package.
* Connect with senior members if you do not know how to achieve any of above
* Any deviations from above needs to be approved by Dev Lead

#### Coding guidelines for package structuring and what goes where
* All servlets should be in com.taylorsuniversity.servlet package
* All service interfaces should be in com.taylorsuniversity.services package
* All service implementations should be in com.taylorsuniversity.services.impl package
* All servlets names must suffix "Servlet", services suffix "Service", service impl suffix "ServiceImpl", etc
* All constants must be in "TaylorsConstants.java"
* All utility methods must be in "CoreUtils.java"
* All sightly utils must be in "SightlyUtils.java"
* Do not create a POJO (WCMUse) if the objective is achievable by SightlyUtils
* All workflow steps should be in com.taylorsuniversity.dam.workflow package
* All use Models should be in com.taylorsuniversity.models package
* If a constant is already present, use that instead of creating a new constant
* Demarcate your constants with java comment block //Start - Asset metadata constants and //End - Asset metadata constants
* Keep constant name as generic as possible and not tie them to your code/class/component name - unless absolutely necessary
* If required add a constant in middle of a file. Its not required to add a constant only at the end
* CoreUtils should not have any injections or dependencies of a service.
* Each method in CoreUtils will only work on parameter passed and return a mutated response.
* No class level variables in a servlet
* Methods in CoreUtils should be very generic and really reusable. Example methods in CoreUtils could be createZip, calculateChecksum, etc. 
* A method in CoreUtils cannot call a service method. Vice versa will be true.
* Each service must have an interface that will be exposed to other packages
* Each service must have an Impl which will be hidden
* Internal methods within an Impl will be protected access modifier
* Refrain from creating private methods anywhere in code. Take explicit approval of Dev Lead in case required.
* Before creating a new method in CoreUtils, search and see if any such method already exists in CoreUtils
* Proper and elaborate javadoc comments are must for methods in CoreUtils
* Services can be injected in Models, Servlets
* Never call a servlet method from any other class. This is abuse and violation of basic coding practices.
* Never overload a servlet method such as doGet or doPost. Create a separate servlet in such a requirement scenario.
* A servlet should conform to REST principles - Use doGet for query, doPost for Save, etc. 
* Use resource type & selector based approach for creating a servlet. Path based approach only where directed.
* Consider caching and non caching requirement in a servlet. 

For path based 
```
/bin/taylorsuniversity/cache/* and '/bin/taylorsuniversity/nocache/*
```

For selector based 
```
*.taylorsuniversity.cache.*.json and *.taylorsuniversity.nocache.*.json 
```


#### Release process

* Tagging will be done on develop branch at end of every sprint or last PR to release at sprint end. 

Tagging will be done as per below convention:

* SNAPSHOT:

```
v0.0.1-SNAPSHOT
```

* SPRINT RELEASE:

```
v0.1.0-SPRINT-1
```

* Final RELEASE/GoLive:

```
v1.0.0-GM
```
