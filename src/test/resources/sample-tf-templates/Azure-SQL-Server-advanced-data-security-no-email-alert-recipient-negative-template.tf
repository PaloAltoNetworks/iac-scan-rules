resource "azurerm_resource_group" "example" {
  name     = "acceptanceTestResourceGroup1"
  location = "West US"
}

resource "azurerm_sql_server" "example" {
  name                         = "mysqlserver"
  resource_group_name          = "${azurerm_resource_group.example.name}"
  location                     = "West US"
  version                      = "12.0"
  administrator_login          = "4dm1n157r470r"
  administrator_login_password = "4-v3ry-53cr37-p455w0rd"
}

resource "azurerm_sql_database" "example" {
  name                = "mysqldatabase"
  resource_group_name = "${azurerm_resource_group.example.name}"
  location            = "West US"
  server_name         = "${azurerm_sql_server.example.name}"

  threat_detection_policy {
    email_addresses = ["secops@thecompany.com"]
  }
  tags = {
    environment = "production"
  }

}

resource "azurerm_sql_database" "example2" {
  name                = "mysqldatabase2"
  resource_group_name = "${azurerm_resource_group.example.name}"
  location            = "West US"
  server_name         = "${azurerm_sql_server.example.name}"

  threat_detection_policy {
    email_addresses = ["secops@thecompany.com"]
  }
  tags = {
    environment = "production2"
  }

}