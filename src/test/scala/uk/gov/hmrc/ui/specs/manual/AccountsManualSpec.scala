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
import uk.gov.hmrc.ui.pages.manual.account.*
import uk.gov.hmrc.ui.specs.BaseSpec
import uk.gov.hmrc.ui.specs.tags.*
import uk.gov.hmrc.ui.utils.TestData

class AccountsManualSpec extends BaseSpec with ManualJourneyHelper {

  Feature("CRS/FATCA Manual - Accounts journey") {

    Scenario(
      "Accounts - FATCA report does not show SEMP and IBAN can be selected",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as FATCA")
      navigateToTaskList("FATCA")

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
      Given("The user has reached the manual task list as CRS")
      navigateToTaskList("CRS")

      When("They open the accounts task")
      ManualSendAReportIndexPage.clickAccounts()

      When("They confirm the account has a number and continue")
      AccountHaveNumberPage.selectYesAndContinue()

      Then("They are on the account number type page")
      AccountNumberTypePage.checkPage()

      And("SEMP is shown for a CRS report")
      AccountNumberTypePage.checkSempIsPresent()

      When("They select SEMP and continue")
      AccountNumberTypePage.selectAccountNumberType(TestData.semp)
    }

    Scenario(
      "Accounts - balance page valid currency and amount (FATCA)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as FATCA")
      navigateToTaskList("FATCA")

      When("They open the accounts task")
      ManualSendAReportIndexPage.clickAccounts()

      When("They confirm the account has no number and continue")
      AccountHaveNumberPage.selectNoAndContinue()

      Then("They are on the account identifier page")
      AccountIdentifierPage.checkPage()

      When("They enter a valid identifier and continue")
      AccountIdentifierPage.enterIdentifierAndContinue()

      Then("They are on the account closed page and select no and continue")
      AccountClosedPage.selectNoAndContinue()

      When("They select a currency and enter a valid amount and continue")
      AccountBalancePage.selectCurrencyAndContinue("100.50")
    }

    Scenario(
      "Accounts - balance page valid currency (CRS)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS")
      navigateToTaskList("CRS")

      When("They open the accounts task")
      ManualSendAReportIndexPage.clickAccounts()

      When("They confirm the account has no number and continue")
      AccountHaveNumberPage.selectNoAndContinue()

      Then("They are on the account identifier page")
      AccountIdentifierPage.checkPage()

      When("They enter a valid identifier and continue")
      AccountIdentifierPage.enterIdentifierAndContinue()

      When("They select yes on the account closed page and continue")
      AccountClosedPage.selectYesAndContinue()

      Then("They are on the currency page")
      AccountCurrencyPage.checkPage()

      When("They select a currency and continue")
      AccountCurrencyPage.selectCurrencyAndContinue()
    }
  }
}
