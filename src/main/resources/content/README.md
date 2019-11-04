This file tells you how to write policy and policy rules.
If you are new to writing policy rules, this is for you.
If you are proficient with writing rules, go through this ones to understand the structure.

## Where are the policies?
The policy rules are present at following location: iac-scan-rules/src/main/resources/content/. 
Supported Infrastructure as Code templates are: <b>cft, tf and k8s</b>. There are folders for each type respectively.

## Different Public Cloud IaC specific template policies go in different files?
Yes. Each Infrastructure as Code Template Policies go in template specific directories. Each rule is added as a file to their respective directories e.g. CFT template related rule files are added/edited to directory: ```src/main/resources/content/cft```

## How do i write the policy?
Please see 'Policy Writting in Details' section.
At higher level please create a policy file under the respective folder(tf, cft or k8) with a '.json' extension. 

## What are cft.json, tf.json and k8s.json files in /src/main/resources/content/?
Please do not edit these files as they are auto-generated files by CI/CD pipeline and will be overwritten on next PR merge.
They are list of policies per template types at higher level. All the policy files are merged in X.json file aa an Arraylist of json objects where X is template folder.  

## Do I follow any practice/format to write the new policy?
1. Go to the respective folder for the type for which policy is to be created.
2. Create a new file with the same name as the policy(see next section) with hyphens in between instead of spaces.
3. Write your policy as per structure defined in next section.
Only Json format is supported. The policy is Json Object where each Rule consists of severity, resourceType, policy(rule name/what is the rule for), rule(based on json path), is(UUID), enabled(default false):
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
<h3> Policy Writting in Details</h3>
<h6>Policy Structure example</h6>

```
{ 
   "severity":"medium",
   "resourceType":"AWS_iam_account_password",
   "policy":"AWS IAM password policy does not have a minimum of 14 characters",
   "rule":"$.resource[*].aws_iam_account_password_policy[*].strict[*].minimum_password_length less than 14",
   "id":"7228106b-f82f-4d2e-a1a0-73fd15f70637",
   "enabled":true
}
```

* severity: low, medium or high.
* resourceType: The resource on which this policy is applicable. It is good to start the resourceType with cloud name i.e. AWS, GCP etc.
* policy: One liner on what this policy is about. This should start with the cloud type i.e. AWS, GCP etc. <b>This should be used as name of the policy.</b>
* rule: The actual rules which runs against the input template. These are written in JSONPath format. Next section have steps on how to write rules.
* id: uuid.
* enabled: true/false. If you want your policy not available when in doubt, make it enabled=false. By default it will be treated as false anyway. 


<h6>JSON Rules</h6>

The rules are written in JSON Path format. A quick tutorial guide: [A guide to Json Path](https://restfulapi.net/json-jsonpath/)

Since the rules should return a boolean, they should end with a relational statement. 

The allowed relational operators check: [List of allowed relational operators](https://bitbucket.org/redlockio/redlock-platform/src/4961bf69fd2a86dfd7a8fd6c2a0b692767cf4560/redlock-java/redlock-maven-agg/redlock-rules-eval/src/main/antlr4/io/redlock/rules/generated/RuleSet.g4)

Steps/Guide for rule creation and validation:

1. Make sure you have a valid tf(or cft or k8) template.
2. Convert your template to Json using the following link: https://scan.api.redlock.io/v1/iac/hclToJson
3. Copy the resultant Json into the following API: https://jsonpath.com/ and write the rule here. 
   This link can help you for writing rules.
4. Once the rule is ready, use this API : https://scan.api.redlock.io/v1/iac to check if its working fine. Copy your Json in the body and rule in the header : rl-json-rule as key and your rule as value.
5. Once your have tested couple of positive and negative test cases, commit and push your changes.

## Do i need to do anything for testing?
Yes, please include positive and negative scenario as described below. The policy will not be approved/merged without these cases covered. Please see existing files for one of the policy to get the idea.

Put the Json payload with negative and positive scenarios under 
https://github.com/PaloAltoNetworks/iac-scan-rules/src/test/resources/tf/xx. where XX is folder for specific rule file. e:g; src/main/test/resources/tf/AWS-passoword

The file name should be same as the policy file name with a suffix of -positive.json and -negative.json respectively in AWS-password dir. Test folder name has to match rule file name. 

Positive case when rule will be matched(wrong template with fault in it) and it's a negative case rule match won't happen for that specific rule(right template).