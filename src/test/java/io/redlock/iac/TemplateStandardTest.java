package io.redlock.iac;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TemplateStandardTest {

  private static final String rulesDirPath = "./src/main/resources/content";
  private static final String resourceExtension = "json";
  private static final String templateDirPath = "./src/test/resources";
  private static final String POSITIVE_TEST_FILE_NAME_CONTAINS = "positive";
  private static final String NEGATIVE_TEST_FILE_NAME_CONTAINS = "negative";

  @Test
  public void ensureTestTemplateStructureConventionAndStandardsForTF() throws Exception {
    //execute in this sequence: folder verification->json file extension->Name convention-> must pos/neg templates
    ensureTestTemplateFolderExists(TemplateType.TF);
    ensureTemplateFileExtensionIsJson(getTemplatesPathByTemplateType(TemplateType.TF));
    ensureTestTemplateFilesFollowNameConvention(TemplateType.TF);
    ensureAtLeastOnePositiveAndAtLeastOneNegativeTestTemplateExists(TemplateType.TF);
  }

  @Test
  public void ensureTestTemplateStructureConventionAndStandardsForCFT() throws Exception {
    ensureTestTemplateFolderExists(TemplateType.CFT);
    ensureTemplateFileExtensionIsJson(getTemplatesPathByTemplateType(TemplateType.CFT));
    ensureTestTemplateFilesFollowNameConvention(TemplateType.CFT);
    ensureAtLeastOnePositiveAndAtLeastOneNegativeTestTemplateExists(TemplateType.CFT);
  }

  @Test (enabled = false)
  public void ensureTestTemplateStructureConventionAndStandardsForK8S() throws Exception {
    ensureTestTemplateFolderExists(TemplateType.K8S);
    ensureTemplateFileExtensionIsJson(getTemplatesPathByTemplateType(TemplateType.K8S));
    ensureTestTemplateFilesFollowNameConvention(TemplateType.K8S);
    ensureAtLeastOnePositiveAndAtLeastOneNegativeTestTemplateExists(TemplateType.K8S);
  }


  private String getResourcePathByTemplateType(TemplateType templateType) throws Exception {
    String resourcePath = "";
    if (templateType != null) {
      switch (templateType) {
        case CFT:
          resourcePath = rulesDirPath + "/" + TemplateType.CFT.getTemplateType();
          break;
        case K8S:
          resourcePath = rulesDirPath + "/" + TemplateType.K8S.getTemplateType();
          break;
        case TF:
          resourcePath = rulesDirPath + "/" + TemplateType.TF.getTemplateType();
          break;
      }
    }
    return resourcePath;
  }

  private String getTemplatesPathByTemplateType(TemplateType templateType) throws Exception {
    String resourcePath = "";
    if (templateType != null) {
      switch (templateType) {
        case CFT:
          resourcePath = templateDirPath + "/" + TemplateType.CFT.getTemplateType();
          break;
        case K8S:
          resourcePath = templateDirPath + "/" + TemplateType.K8S.getTemplateType();
          break;
        case TF:
          resourcePath = templateDirPath + "/" + TemplateType.TF.getTemplateType();
          break;
      }
    }
    return resourcePath;
  }

  private void ensureTemplateFileExtensionIsJson(String templatePath) throws IOException {
    File templateDir = new File(templatePath);
    File[] dirs = templateDir.listFiles();
    for (File dir : dirs) {
      Collection<File> files = FileUtils.listFiles(dir, null, Boolean.TRUE);
      for (File file : files) {
        if (file.getCanonicalPath().endsWith(".json") == false) {
          Assert.fail("File extension should be json for file: " + file.getName());
        }
      }
    }
  }

  private void ensureTestTemplateFolderExists(TemplateType templateType) throws Exception {
    File contentFolder = new File(getResourcePathByTemplateType(templateType));
    File templateFolder = new File(getTemplatesPathByTemplateType(templateType));
    File[] templateTestFolders = templateFolder.listFiles();

    Collection<File> ruleFiles = FileUtils.listFiles(contentFolder, new String[] {resourceExtension}, Boolean.TRUE);
    for (File ruleFile : ruleFiles) {
      String ruleFileNameWithoutExtension = ruleFile.getName().substring(0,
          ruleFile.getName().length() - getResourceExtension().length() - 1);
      if (getTemplateFolderForRuleFolder(ruleFileNameWithoutExtension, templateTestFolders) == null) {
        Assert.fail("No test template folder for Rule : " + ruleFile.getName());
      }
    }
  }

  private void ensureTestTemplateFilesFollowNameConvention(TemplateType templateType) throws Exception {
    {
      File contentFolder = new File(getResourcePathByTemplateType(templateType));
      File templateFolder = new File(getTemplatesPathByTemplateType(templateType));

      Collection<File> ruleFiles = FileUtils.listFiles(contentFolder, new String[] {resourceExtension}, Boolean.TRUE);
      for (File ruleFile : ruleFiles) {

        String ruleFileNameWithoutExtension = ruleFile.getName().substring(0,
            ruleFile.getName().length() - getResourceExtension().length() - 1);

        File testFolder = new File(templateFolder, ruleFileNameWithoutExtension);
        File[] testFiles = testFolder.listFiles();
        for (File testFile : testFiles) {
          if (!testFile.getName().contains(NEGATIVE_TEST_FILE_NAME_CONTAINS) && !testFile.getName().contains(
              POSITIVE_TEST_FILE_NAME_CONTAINS)) {
            Assert.fail("Test template folder: " + testFolder.getName() + " doesn't follow positive and negative test "
                + "template file name convention for file: " + testFile);
          }
          if (testFile.getName().contains(NEGATIVE_TEST_FILE_NAME_CONTAINS) && testFile.getName().contains(
              POSITIVE_TEST_FILE_NAME_CONTAINS)) {
            Assert.fail("Test File: " + testFile + " can't be for both positive & negative test case. pos/beg both "
                + "naming "
                + "conventions exists.");
          }
        }
      }
    }
  }

  private File getTemplateFolderForRuleFolder(String ruleFileName, File[] templateFolders) {
    for (File templateFolder : templateFolders) {
      if (templateFolder.getName().equals(ruleFileName)) {
        return templateFolder;
      }
    }
    return null;
  }

  private static String getResourceExtension() {
    return "json";
  }

  private void ensureAtLeastOnePositiveAndAtLeastOneNegativeTestTemplateExists(TemplateType templateType)
      throws Exception {
    {
      File contentFolder = new File(getResourcePathByTemplateType(templateType));
      File templateFolder = new File(getTemplatesPathByTemplateType(templateType));

      Collection<File> ruleFiles = FileUtils.listFiles(contentFolder, new String[] {resourceExtension}, Boolean.TRUE);
      for (File ruleFile : ruleFiles) {

        String ruleFileNameWithoutExtension = ruleFile.getName().substring(0,
            ruleFile.getName().length() - getResourceExtension().length() - 1);

        File testFolder = new File(templateFolder.getAbsolutePath(), ruleFileNameWithoutExtension);
        Collection<File> testFiles = FileUtils.listFiles(testFolder,
            new String[] {getResourceExtension()},
            Boolean.TRUE);
        int negCount = 0;
        int posCount = 0;
        for (File testFile : testFiles) {
          if (testFile.getName().contains(NEGATIVE_TEST_FILE_NAME_CONTAINS)) {
            negCount++;
          }
          if (testFile.getName().contains(POSITIVE_TEST_FILE_NAME_CONTAINS)) {
            posCount++;
          }
        }
        if (negCount < 1 && posCount < 1) {
          Assert.fail("Test Folder : " + testFolder.getName() + " doesn;t have at least one positive & atleast one "
              + "negative test template");
        }
      }
    }
  }

}
