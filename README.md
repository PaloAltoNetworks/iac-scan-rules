# Public Cloud 'Infrastructure as Code(IaC)' Policies
This is a bare-bones 'Infrastructure as Code' Policies project where policies are open to public.

The IaC policies will be verified by Palo Alto Networks(PANW) before they are available publicaly.

To contribute, just add/update the IaC policies and create a PR.

## How to build and run

It's a Java maven project. Go to iac-policies project directory and run ```$ mvn clean install```. 


### It doesn't work, do I need to install something first?

Yes, you need

- [Java JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or any other Java 8 version. 
- [Apache Maven 3](https://maven.apache.org/)


## Where are the policies?
There are respective folders for each template type(cft, tf & k8s) under:
    src/main/resouces/content/
  

## Different Public Cloud IaC specific template policies go in different files?

Yes. Each Infrastructure as Code Template Policies go in template specific directories. Each rule is added as a file to their respective directories e.g. CFT template related rule files are added/edited to directory: ```src/main/resources/content/cft```
Once the rule PR is approved and rule is merged, then it is added to the respective rules file(cft.json etc).

## Do I follow any practice/format to write the new rule?

Yes. Only Json format is supported. The policies are Json arraylist of Rule Object where each Rule consists of severity, resourceType, policy(rule name/what is the rule for), rule(based on json path), is(UUID), enabled(default false):
```
{
   "severity": "<string>",
   "resourceType": "<string>",
   "policy": "<string>",
   "rule": "<json rule>",
   "id": "<uuid>",
   "enabled": <boolean>
} 
```

## Where should I write rules?
Create a file under the respective folder(tf, cft or k8) with a '.json' extension. When you changes are meged, then your rule will be added to the respective rules file ,for e.g. for CFT ```src/main/resources/content/cft.json```

## How can I know I am ready to check in rules?

Once the rules are written and added to the repective directory & the build is successful on the local machine, then you can go ahead and push your changes.

Create Pull Request with the respective changes in new branch to be merged with master. Once approved by PANW, it will be available for use publicaly. 

### Is there any guide on how to write policy?

Yes, Please go [here](src/main/resources/content/) for step wise documentation on how to write a policy with rule and merge it.

### For full documentation on rules and supported rules
[Prisma Public Cloud IaC Scan Policies](https://iacscanapidoc.redlock.io/content)

## Where can I read more about the Prisma IaC scan api and how these rules are used?
See [Prisma Public Cloud IaC Scan API](https://iacscanapidoc.redlock.io/)
