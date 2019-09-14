package io.redlock.iac;
import com.grack.nanojson.JsonObject;

public class Rule {

  private String id;
  private String rule;
  private String severity;
  private String policy;
  private String resourceType;
  private boolean enabled;

  public Rule(String id, String rule, String severity, String policy, String resourceType, boolean enabled) {
    this.id = id;
    this.rule = rule;
    this.severity = severity;
    this.policy = policy;
    this.resourceType = resourceType;
    this.enabled = enabled;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRule() {
    return rule;
  }

  public void setRule(String rule) {
    this.rule = rule;
  }

  public String getSeverity() {
    return severity;
  }

  void setSeverity(String severity) {
    this.severity = severity;
  }

  public String getPolicy() {
    return policy;
  }

  public void setPolicy(String policy) {
    this.policy = policy;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getResourceType() { return resourceType; }

  public void setResourceType(String resourceType) { this.resourceType = resourceType; }

  @Override public String toString() {
    return "{" +
        "id='" + id + '\'' +
        ", rule='" + rule + '\'' +
        ", severity='" + severity + '\'' +
        ", resourceType='" + resourceType + '\'' +
        ", policy='" + policy + '\'' +
        ", enabled=" + enabled +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Rule jsonRule = (Rule) o;

    if (enabled != jsonRule.enabled) {
      return false;
    }
    if (!id.equals(jsonRule.id)) {
      return false;
    }
    if (!rule.equals(jsonRule.rule)) {
      return false;
    }
    if (!severity.equals(jsonRule.severity)) {
      return false;
    }
    return policy.equals(jsonRule.policy);
  }

  @Override public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + rule.hashCode();
    result = 31 * result + severity.hashCode();
    result = 31 * result + policy.hashCode();
    result = 31 * result + (enabled ? 1 : 0);
    return result;
  }

  public static Rule toRule(JsonObject jsonObject) {
    Rule jsonRule = new Rule(
        jsonObject.getString("id"),
        jsonObject.getString("rule"),
        jsonObject.getString("severity"),
        jsonObject.getString("policy"),
        jsonObject.getString("resourceType"),
        jsonObject.getBoolean("enabled"));

    return jsonRule;
  }
}