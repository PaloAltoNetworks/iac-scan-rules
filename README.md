# Public Cloud 'Infrastructure as Code(IaC)' Policies
This is a bare-bones 'Infrastructure as Code' Policies project where policies are open to public.

The IaC policies will be verified by Palo Alto Networks(PANW) before they are available publicly.

To contribute, just add/update the IaC policies and create a PR.

## How to build and run?

It's a Java maven project. Go to iac-policies project directory and run ```$ mvn clean install```. 


### It doesn't work, do I need to install something first?

Yes, you need

- [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or any other Java 8 version. 
- [Apache Maven 3](https://maven.apache.org/)


## Where are the policies in repo structure?
There are respective folders for each template type(cft, tf & k8s) under:
    src/main/resouces/content/
  
## How can i contribute to repo?
Please create PR against development branch. Development branch is used for development and testing. By default PRs are raised against development branch.

## Is there any guideline on how to write policy?
Yes, Please go [here](src/main/resources/content/) for step wise documentation on how to write a policy with rule.

## How can I know I am ready to check in rules?
Once the rules are written and added to the respective directory & the build is successful on the local machine, then you can go ahead and push your changes to create PR.

## When would my changes be in development branch?
Once PR is generated, one non-author review approval + health check needs to passed to be merged to development branch. Add reviewer for your changes to be reviewed. Before merging to development branch, it will be reviewed by PANW team as well.

## What if health check didn't pass?
Its likely because rebase is required with development branch or build failure due to compilation/unit tests.
Please see the details in health check and take appropriate action.    

## When my rules are available in production? 
Master branch is treated as production branch. If your changes are merged on any day by 11pm, they are available to be used publicly by end of that day. Please check the doc in next question: If the policy is available in that doc, its already in use in production.  

### For full documentation supported rules
[Prisma Public Cloud IaC Scan Policies](https://iacscanapidoc.redlock.io/content)

## Where can I read more about the Prisma IaC scan api and how these rules are used?
See [Prisma Public Cloud IaC Scan API](https://iacscanapidoc.redlock.io/)
