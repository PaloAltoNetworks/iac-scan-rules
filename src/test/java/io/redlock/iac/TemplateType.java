package io.redlock.iac;

public enum TemplateType {
  TF("tf"),
  CFT("cft"),
  K8S("k8s"),
  K8WH("k8wh");

  String templateType;

  TemplateType(String templateType) {
    this.templateType = templateType;
  }

  public String getTemplateType() {
    return this.templateType;
  }
}
