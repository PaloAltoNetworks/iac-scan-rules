# Public Cloud Infrastructure as Code(IaC) Policies
This is a bare-bones Infrastructure as Code Policies project where policies are open to public.

The IaC policies will be verified by Palo Alto Networks before they are available to world.

Just add/update the IaC policies and create PR to contribute to this project.

## How to build and run

It's  a maven Java project. Go to iac-policies project directory and run ```$ mvn clean install```. 


### It doesn't work, do I need to install something first?
Yes, you need

- [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or any other Java 8 version. 
- [Apache Maven 3](https://maven.apache.org/)


## Where are the policies?

    src/main/resouces/content
  

## Different Public Cloud IaC specific template policies go in different files?

    Yes. Each Infrastructure as Code Template Policies go in template specific file. e:g; CFT template related rules are added/edited to file ```src/main/resources/content/cft.json```

## Do I follow any practice/format to write the new rule?

    Yes. Only Json format is supported. The policies are Json arraylist of Rule Object where each Rule consists of severity, resourceType, policy(rule name/what is the rule for), rule(based on json path), is(UUID), enabled(default false)   

## How can I know I am ready to check in rules?

When I have added/updated rules in one of the files and full build with unit tests working on my local machine, i am ready to push changes.

Create Pull Request with new changes in new branch to be merged to master. Once approved by PANW, it will be merged for the world to be used. 

### For full documentation on rules and supported rules
[Prisma Public Cloud IaC Scan Policies](https://iacscanapidoc.redlock.io/content)

## Where can I read more about the Prisma IaC scan api and how these rules are used?
See [Prisma Public Cloud IaC Scan API](https://iacscanapidoc.redlock.io/)
