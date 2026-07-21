/*
 * Copyright 2026 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ui.specs.manual

import uk.gov.hmrc.ui.pages.*
import uk.gov.hmrc.ui.pages.manual.*
import uk.gov.hmrc.ui.pages.manual.account.{AccountHaveNumberPage, AccountNumberTypePage}
import uk.gov.hmrc.ui.specs.BaseSpec
import uk.gov.hmrc.ui.specs.tags.*
import uk.gov.hmrc.ui.utils.TestData

class AccountsManualSpec extends BaseSpec {

  Feature("CRS/FATCA Manual - Accounts journey") {

    Scenario(
      "Accounts - FATCA report does not show SEMP and IBAN can be selected",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user logs in as a standard User")
      AuthLoginPage.loginAsBasic()

      And("The user is on the manage your financial institutions page")
      FiManagementPage.clickManageYourFinancialInstitutions()

      When("The user clicks manage reports for an FI")
      YourFisPage.clickOnSecondManageReports()

      And("The user clicks 'filling in an online form for manual reporting'")
      ManageReportsPage.clickFillInOnlineManualReport()

      And("They select FATCA and continue")
      CrsOrFatcaPage.selectFatcaAndContinue()

      And("They enter a valid year and continue")
      ReportingDetailsYearPage.enterYearAndContinue()

      And("They select a report with information and continue")
      TypeOfReportPage.selectReportWithInformationAndContinue()

      And("They save and continue on the check answers page")
      ReportCheckAnswersPage.confirmAndSend()

      Then("They are on the manual send a report task list page")
      ManualSendAReportIndexPage.checkDynamicPage()

      When("They open the accounts task")
      ManualSendAReportIndexPage.clickAccounts()

      When("They confirm the account has a number and continue")
      AccountHaveNumberPage.selectYesAndContinue()

      Then("They are on the account number type page")
      AccountNumberTypePage.checkPage()

      And("SEMP is not shown for a FATCA report")
      AccountNumberTypePage.checkSempIsNotPresent()

      When("They select IBAN and continue")
      AccountNumberTypePage.selectAccountNumberType(TestData.iban)
    }

    Scenario(
      "Accounts - CRS report shows SEMP and IBAN can be selected",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user logs in as a standard User")
      AuthLoginPage.loginAsBasic()

      And("The user is on the manage your financial institutions page")
      FiManagementPage.clickManageYourFinancialInstitutions()

      When("The user clicks manage reports for an FI")
      YourFisPage.clickOnSecondManageReports()

      And("The user clicks 'filling in an online form for manual reporting'")
      ManageReportsPage.clickFillInOnlineManualReport()

      And("They select CRS and continue")
      CrsOrFatcaPage.selectCrsAndContinue()

      And("They enter a valid year and continue")
      ReportingDetailsYearPage.enterYearAndContinue()

      And("They select a report with information and continue")
      TypeOfReportPage.selectReportWithInformationAndContinue()

      And("They save and continue on the check answers page")
      ReportCheckAnswersPage.confirmAndSend()

      Then("They are on the manual send a report task list page")
      ManualSendAReportIndexPage.checkDynamicPage()

      When("They open the accounts task")
      ManualSendAReportIndexPage.clickAccounts()

      When("They confirm the account has a number and continue")
      AccountHaveNumberPage.selectYesAndContinue()

      Then("They are on the account number type page")
      AccountNumberTypePage.checkPage()

      And("SEMP is shown for a CRS report")
      AccountNumberTypePage.checkSempIsPresent()

      When("They select IBAN and continue")
      AccountNumberTypePage.selectAccountNumberType(TestData.semp)
    }
  }
}
