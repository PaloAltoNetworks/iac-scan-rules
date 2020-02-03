resource "azurerm_resource_group" "example" {
  name     = "example-resources"
  location = "West Europe"
}
resource "azurerm_storage_account" "example" {
  name                     = "examplestoracc"
  resource_group_name      = "${azurerm_resource_group.example.name}"
  location                 = "${azurerm_resource_group.example.location}"
  account_tier             = "Standard"
  account_replication_type = "LRS"
}
resource "azurerm_storage_container" "example" {
  name                  = "content"
  resource_group_name   = "${azurerm_resource_group.example.name}"
  storage_account_name  = "${azurerm_storage_account.example.name}"
  container_access_type = "private"
}
resource "azurerm_storage_blob" "example" {
  name                   = "my-awesome-content.zip"
  resource_group_name    = "${azurerm_resource_group.example.name}"
  storage_account_name   = "${azurerm_storage_account.example.name}"
  storage_container_name = "${azurerm_storage_container.example.name}"
  type                   = "Block"
  source                 = "some-local-file.zip"
}
