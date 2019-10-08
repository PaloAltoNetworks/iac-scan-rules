This file tells you how to write policy and policy rules.
If you are new to writing policy rules, this is for you.
If you are proficient with writing rules, go through this ones to understand the structure.

<b>Let's Begin:</b>

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
   "resourceType":"iam_account_password",
   "policy":"AWS IAM password policy does not have a minimum of 14 characters",
   "rule":"$.resource[*].aws_iam_account_password_policy[*].strict[*].minimum_password_length==14",
   "id":"7228106b-f82f-4d2e-a1a0-73fd15f70637",
   "enabled":true
}
```

* severity: low, medium or high.
* resourceType: 
