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
      AccountBalancePage.selectCurrencyAndAmount("100.50")
    }

    Scenario(
      "Accounts - year opened, joint account and holders (CRS)",
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

      And("They are on the account undocumented page and select yes or no and continue")
      AccountUndocumentedPage.selectNoAndContinue()

      And("They are on the account dormant page and select yes and continue")
      AccountDormantPage.selectYesAndContinue()

      When("They are on the account year opened page")
      AccountYearOpenedPage.checkPage()

      And("They confirm the account was opened in the year and continue")
      AccountYearOpenedPage.selectYesAndContinue()

      And("They are on the joint account page and confirm it is a joint account and continue")
      AccountJointAccountPage.selectYesAndContinue()

      Then("They are on the joint account holders page and enter a valid number of holders and continue")
      AccountJointHoldersPage.enterNumberAndContinue("2")

      When("They select Depository account and continue")
      AccountTypePage.selectAccountTypeAndContinue("Depository")
    }

    Scenario(
      "Accounts - account type reached via joint account No. (CRS)",
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

      When("They select no on the account closed page and continue")
      AccountClosedPage.selectNoAndContinue()

      Then("They are on the currency page")
      AccountBalancePage.checkPage()

      When("They select a currency, amount and continue")
      AccountBalancePage.selectCurrencyAndAmount("100")

      And("They are on the account undocumented page and select no and continue")
      AccountUndocumentedPage.selectNoAndContinue()

      And("They are on the account dormant page and select no and continue")
      AccountDormantPage.selectNoAndContinue()

      When("They are on the account year opened page")
      AccountYearOpenedPage.checkPage()

      And("They confirm the account was opened in the year and continue")
      AccountYearOpenedPage.selectYesAndContinue()

      And("They are on the joint account page and confirm it is not a joint account and continue")
      AccountJointAccountPage.selectNoAndContinue()

      Then("They see the account type page")
      AccountTypePage.checkPage()

      When("They select Custodial account and continue")
      AccountTypePage.selectAccountTypeAndContinue("Custodial")
    }

    Scenario(
      "Accounts - account type via have-number Yes shows Cash value insurance when AcctNumberType is OECD605 (CRS)",
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

      // /manual/account/number is not yet built, so selecting a non-IBAN/SEMP number type
      // here skips straight to /manual/account/closed.
      // TODO: confirm TestData holds a constant for OECD605 ("Any other type of account
      // number or identification number") — using the literal label text as a placeholder.

      When("They select 'Any other type of account number or identification number' and continue")
      AccountNumberTypePage.selectAccountNumberType("Any other")

      Then("They land directly on the account closed page, skipping the unbuilt account number page")
      AccountClosedPage.checkPage()

      When("They select no on the account closed page and continue")
      AccountClosedPage.selectNoAndContinue()

      And("They select a currency, amount and continue")
      AccountBalancePage.selectCurrencyAndAmount("100")

      And("They select no on the account undocumented page and continue")
      AccountUndocumentedPage.selectNoAndContinue()

      And("They select no on the account dormant page and continue")
      AccountDormantPage.selectNoAndContinue()

      And("They confirm the account was opened in the year and continue")
      AccountYearOpenedPage.selectYesAndContinue()

      And("They select no on the joint account page and continue")
      AccountJointAccountPage.selectNoAndContinue()

      Then("They see the account type page")
      AccountTypePage.checkPage()

      And("The Cash value insurance option is present")
      AccountTypePage.checkCashValueInsuranceOptionPresent()

      // TODO: assumes default ReportingPeriod is before 2025-12-31 - Not reported radial present
      // so 5 options = 3 base + Cash value insurance + ReportingPeriod earlier than 2025-12-31,
      // this should be 5 instead (Not reported also shown) — needs a way to control

      And("4 account type options are shown (3 base plus Cash value insurance)")
      AccountTypePage.checkAccountTypeOptionsCount(5)

      When("They select Cash value insurance contract or annuity contract and continue")
      AccountTypePage.selectAccountTypeAndContinue("Cash value insurance")
    }

    Scenario(
      "Accounts - account type page bypassed when AcctNumberType is IBAN (OECD601) (CRS)",
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

      When("They select IBAN and continue")
      AccountNumberTypePage.selectAccountNumberType(TestData.iban)

      Then("They land directly on the account closed page, skipping the unbuilt account number page")
      AccountClosedPage.checkPage()

      When("They select no on the account closed page and continue")
      AccountClosedPage.selectYesAndContinue()

      And("They select a currency and continue")
      AccountCurrencyPage.selectCurrencyAndContinue()

      And("They select no on the account undocumented page and continue")
      AccountUndocumentedPage.selectNoAndContinue()

      And("They select no on the account dormant page and continue")
      AccountDormantPage.selectNoAndContinue()

      And("They confirm the account was opened in the year and continue")
      AccountYearOpenedPage.selectYesAndContinue()

      And("They select no on the joint account page and continue")
      AccountJointAccountPage.selectNoAndContinue()

      Then("The account type page is bypassed, as AcctNumberType is auto-set to CRS1101")
      AccountTypePage.checkPageIsBypassed()
    }

    Scenario(
      "Accounts - account type page bypassed via joint account holders when AcctNumberType is IBAN (OECD601) (CRS)",
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

      When("They select IBAN and continue")
      AccountNumberTypePage.selectAccountNumberType(TestData.iban)

      Then("They land directly on the account closed page, skipping the unbuilt account number page")
      AccountClosedPage.checkPage()

      When("They select no on the account closed page and continue")
      AccountClosedPage.selectYesAndContinue()

      And("They select a currency and continue")
      AccountCurrencyPage.selectCurrencyAndContinue()

      And("They select no on the account undocumented page and continue")
      AccountUndocumentedPage.selectNoAndContinue()

      And("They select no on the account dormant page and continue")
      AccountDormantPage.selectNoAndContinue()

      And("They confirm the account was opened in the year and continue")
      AccountYearOpenedPage.selectYesAndContinue()

      And("They select yes on the joint account page and continue")
      AccountJointAccountPage.selectYesAndContinue()

      And("They enter a valid number of joint holders and continue")
      AccountJointHoldersPage.enterNumberAndContinue("2")

      Then("The account type page is bypassed, as AcctNumberType is auto-set to CRS1101")
      AccountTypePage.checkPageIsBypassed()
    }

  }
}
