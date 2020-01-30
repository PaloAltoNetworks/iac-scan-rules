resource "azurerm_sql_database" "test2" {
  name                = "sqldbtf01"
  resource_group_name = "dummyresourcename"
  location            = "North Central US"
  server_name         = "dummyservername"

  threat_detection_policy {
    state                      = "Enabled"
    email_addresses            = ["dbgrl93@gmail.com"]
    retention_days             = "30"
    storage_account_access_key = "dummystorageaccountaccesskey"
    storage_endpoint           = "dummystorageenpoint"
    use_server_default         = "Enabled"
  }

  provisioner "local-exec" {
    command     = "Set-AzureRmSqlDatabaseBackupLongTermRetentionPolicy -ResourceGroupName dummyresourcename  -ServerName dummyservername -DatabaseName 'sqldbsrvrtf01' -WeeklyRetention P12W -YearlyRetention P5Y -WeekOfYear 16 "
    interpreter = ["PowerShell", "-Command"]
  }
}
