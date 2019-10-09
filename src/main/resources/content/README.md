This file tells you how to write policy and policy rules.
If you are new to writing policy rules, this is for you.
If you are proficient with writing rules, go through this ones to understand the structure.

<h2><b>Let's Begin:</b></h2>

<h3>Files, folders and naming convention</h3>

The policy rules are present at following location: iac-scan-rules/src/main/resources/content/

Supported formats are: <b>cft, tf and k8s</b>. There are folders for each type respectively.

<h4>Steps to create a policy:</h4>

1. Create a local branch for your work.
2. Go to the respective folder for the type for which policy is to be created.
3. Create a new file with the same name as the policy(see next section) with hyphens in between instead of spaces.
4. Write your policy as per structure defined in next section.
5. Run ```mvn clean build``` at iac-scan-rules/.
6. If the build is successful, commit and push your changes.
7. Create the PR for review. The checks might fail in the rebase was not done. Make sure that your branch is upto date with latest changes.
8. Once the PR is approved, the changes will be merged. Also the changes will be added to the rules file i.e. tf.json, k8s.json or cft.json respectively, and are ready to be used.

<h3>Policy Structure</h3>

```
{ 
   "severity":"medium",
   "resourceType":"AWS_iam_account_password",
   "policy":"AWS IAM password policy does not have a minimum of 14 characters",
   "rule":"$.resource[*].aws_iam_account_password_policy[*].strict[*].minimum_password_length==14",
   "id":"7228106b-f82f-4d2e-a1a0-73fd15f70637",
   "enabled":true
}
```

* severity: low, medium or high.
* resourceType: The resource on which this policy is applicable. It is good to start the resourceType with cloud name i.e. AWS, GCP etc.
* policy: One liner on what this policy is about. This should start with the cloud type i.e. AWS, GCP etc. <b>This should be used as name of the policy.</b>
* rule: The actual rules which runs against the input template. These are written in JSONPath format. Next section have steps on how to write rules.
* id: uuid.
* enabled: true/false.


<h3>JSON Rules</h3>

The rules are written in JSON Path format. A quick tutorial guide: [A guide to Json Path](https://restfulapi.net/json-jsonpath/)

Since the rules should return a boolean, they should end with a relational statement. 

The allowed relational operators check: [List of allowed relational operators](https://bitbucket.org/redlockio/redlock-platform/src/4961bf69fd2a86dfd7a8fd6c2a0b692767cf4560/redlock-java/redlock-maven-agg/redlock-rules-eval/src/main/antlr4/io/redlock/rules/generated/RuleSet.g4)

Steps/Guide for rule creation and validation:

1. Make sure you have a valid tf(or cft or k8) template.
2. Convert your template to Json using the following link: https://dev.scan.api.redlock.io/v1/iac/hclToJson
3. Copy the resultant Json into the following API: https://jsonpath.com/ and write the rule here. 
   This link can help you for writing rules.
4. Once the rule is ready, use this API : https://scan.api.redlock.io/v1/iac to check if its working fine. Copy your Json in the body and rule in the header : rl-json-rule as key and your rule as value.
5. Once your have tested couple of positive and negative test cases, commit and push your changes.


<h3> Test Cases </h3>

Put the Json payload with negative and positive scenarios under 
https://github.com/PaloAltoNetworks/iac-scan-rules/tree/development/src/test/resources/tf

The file name should be same as the policy file name with a suffix of -positive and -negative respectively.
