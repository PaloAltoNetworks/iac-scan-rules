package io.redlock.iac;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ContentTest {

  @Test
  public void ensureRequiredFieldExistsCFTRules() throws Exception {
    ensureRequiredFieldExistsForTemplateType(TemplateType.CFT);
  }

  @Test
  public void ensureRequiredFieldExistsTFRules() throws Exception {
    ensureRequiredFieldExistsForTemplateType(TemplateType.TF);
  }

  @Test
  public void ensureRequiredFieldExistsK8SRules() throws Exception {
    ensureRequiredFieldExistsForTemplateType(TemplateType.K8S);
  }

  @Test
  public void validateIdUniqueness() throws Exception {
    for (TemplateType templateType : TemplateType.values()) {
      validateIdUniquenessForTemplateType(templateType);
    }
  }

  private void validateIdUniquenessForTemplateType(TemplateType templateType) throws Exception {
    Set<Rule> rules = getRulesByTemplate(templateType);
    List<String> idList = new ArrayList<>();
    rules.forEach(rule -> idList.add(rule.getId()));
    Set<String> duplicates = findDuplicates(idList);
    if (duplicates.size() != 0) {
      Assert.fail("Found duplicates : " + duplicates);
    }
  }

  private void ensureRequiredFieldExistsForTemplateType(TemplateType templateType) throws Exception {

    Set<Rule> rules = getRulesByTemplate(templateType);

    rules.forEach(rule -> {
      if (isEmpty(rule.getId())) {
        Assert.fail("id not defined in rule ".concat(rule.toString()));
      }

      if (isEmpty(rule.getPolicy())) {
        Assert.fail("policy not defined in rule ".concat(rule.toString()));
      }

      if (isEmpty(rule.getSeverity())) {
        Assert.fail("Severity not defined in rule ".concat(rule.toString()));
      }

      if (isEmpty(rule.getRule())) {
        Assert.fail("rule not defined in rule ".concat(rule.toString()));
      }
    });
  }

  private Set<Rule> getRulesByTemplate(TemplateType templateType) throws Exception {
    InputStream inputStream = null;
    if (templateType != null) {
      switch (templateType) {
        case CFT:
          inputStream =
              Thread.currentThread().getContextClassLoader()
                  .getResourceAsStream("content/" + TemplateFileNameConstants.CFT_RULES_FILE_NAME);
          break;
        case K8S:
          inputStream =
              Thread.currentThread().getContextClassLoader()
                  .getResourceAsStream("content/" + TemplateFileNameConstants.KUBERNETES_RULES_FILE_NAME);
          break;
        case TF:
          inputStream =
              Thread.currentThread().getContextClassLoader()
                  .getResourceAsStream("content/" + TemplateFileNameConstants.TERRAFORM_RULES_FILE_NAME);
          break;
      }
    }
    return getRulesFromInputStreams(inputStream);
  }

  private Set<Rule> getRulesFromInputStreams(InputStream inputStream) throws Exception {
    Set<Rule> rules = new HashSet<Rule>();

    if (inputStream != null) {
      String result = "";
      try {

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
          result = br.lines().collect(Collectors.joining(System.lineSeparator()));
        }

        JSONArray jsonRules = new JSONArray(result);
        if (jsonRules != null) {
          for (Object eachJson : jsonRules) {
            JSONObject jsonObject = (JSONObject) eachJson;
            rules.add(Rule.toRule(jsonObject));
          }
        }
      } catch (Exception any) {
        throw new Exception("Exception parsing json from inputstream" + any.getMessage() +result);
      }
    }
    return rules;
  }

  private boolean isEmpty(String str) {
    return (str == null || str.isEmpty());
  }

  private <T> Set<T> findDuplicates(Collection<T> collection) {

    Set<T> duplicates = new LinkedHashSet<>();
    Set<T> uniques = new HashSet<>();

    for (T t : collection) {
      if (!uniques.add(t)) {
        duplicates.add(t);
      }
    }
    return duplicates;
  }

}