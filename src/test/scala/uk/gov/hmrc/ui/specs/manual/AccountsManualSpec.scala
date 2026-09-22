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
      journeyToYourFis()
      selectTheSuitableFI("FourthFI")
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
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
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
      journeyToYourFis()
      selectTheSuitableFI("FourthFI")
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

      Then("They land in have payments page")
      HavePaymentsPage.checkPageFATCA()

      And("They need to mention if any payments made to this account, select Yes and continue")
      HavePaymentsPage.selectYesAndContinue()

      And("The User is on Payments Type page")
      PaymentsTypePage.checkPage()

      And("They also need to specify the payments type for this account, select Dividends and proceed")
      PaymentsTypePage.selectTypeOfPaymentsAndContinue("DividendsFATCA")

      Then("They are on the payments amount page")
      PaymentsAmountPage.checkPage("dividends")

      When("They select a currency and enter a valid amount and continue")
      PaymentsAmountPage.selectOtherCurrencyAmount("596.89")

      Then("They land on account payments page")
      AccountPaymentsPage.checkPage()

      And("The account payments page should contains the list of payments made to this account")
      AccountPaymentsPage.checkPaymentSummaryListContains("596.89 USD dividends")

      When("They don't want to add any more payments they can select no and continue")
      AccountPaymentsPage.selectNoAndContinue()

    }

    Scenario(
      "Accounts - year opened, joint account and holders (CRS)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
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

      Then("They lands on have payments page")
      HavePaymentsPage.checkPageCRS()

      And("They need to select Yes or No and Continue")
      HavePaymentsPage.selectYesAndContinue()

      Then("They land directly on the payments amount page, skipping payments type")
      PaymentsAmountPage.checkPage("interest")

      When("They select a currency and enter a valid amount and continue")
      PaymentsAmountPage.selectCurrencyAndAmount("250.75")

      Then("They land on account payments page")
      AccountPaymentsPage.checkPage()

      And("The account payments page should contains the list of payments made to this account")
      AccountPaymentsPage.checkPaymentSummaryListContains("250.75 GBP interest")

      When("They want to remove the payment made on that account")
      AccountPaymentsPage.removeThePayment("250.75_GBP_interest")

      Then("They land on account remove payment page")
      AccountRemovePaymentPage.checkPage("250.75 GBP interest")

      And("Select Yes to remove payment type")
      AccountRemovePaymentPage.selectYesAndContinue()

      Then("They will get back to account payments page with no list of payments")
      AccountPaymentsPage.checkRemovedPaymentText()

    }

    Scenario(
      "Accounts - account type reached via joint account No. (CRS)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
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

      Then("They lands on have payments page")
      HavePaymentsPage.checkPageCRS()

      And("They need to select Yes or No and Continue")
      HavePaymentsPage.selectYesAndContinue()

      Then("Then have to choose the what type of payments were these for this account")
      PaymentsTypePage.selectTypeOfPaymentsAndContinue("InterestCRS")

      Then("They are on the payments amount page")
      PaymentsAmountPage.checkPage("interest")

      When("They select a currency and enter a valid amount and continue")
      PaymentsAmountPage.selectOtherCurrencyAmount("500")

      Then("They land on account payments page")
      AccountPaymentsPage.checkPage()

      And("They want to add more payments, select yes and continue")
      AccountPaymentsPage.selectYesAndContinue()

      Then("They are on payment-type page again and can select payment type")
      PaymentsTypePage.selectTypeOfPaymentsAndContinue("GrossProceedsOrRedemptionsCRS")

      Then("They are on the payments amount page")
      PaymentsAmountPage.checkPage("gross proceeds or redemptions")

      When("They select a currency and enter a valid amount and continue")
      PaymentsAmountPage.selectCurrencyAndAmount("120.56")

      Then("They land on account payments page")
      AccountPaymentsPage.checkPageWithTwoPayments()

      When("They don't want to add any more payments they can select no and continue")
      AccountPaymentsPage.selectNoAndContinue()

    }

    Scenario(
      "Accounts - account type via have-number Yes shows Cash value insurance when AcctNumberType is OECD605 (CRS)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("FifthFI")
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

      And("5 account type options are shown (3 base plus Cash value insurance and Reporting period)")
      AccountTypePage.checkAccountTypeOptionsCount(5)

      When("They select Cash value insurance contract or annuity contract and continue")
      AccountTypePage.selectAccountTypeAndContinue("Cash value insurance")

      Then("They lands on have payments page")
      HavePaymentsPage.checkPageCRS()

      And("They need to select Yes or No and Continue")
      HavePaymentsPage.selectYesAndContinue()

      Then("Then have to choose the what type of payments were these for this account")
      PaymentsTypePage.selectTypeOfPaymentsAndContinue("OtherCrs")

      Then("They are on the payments amount page")
      PaymentsAmountPage.checkPage("other")

      When("They select a currency and enter a valid amount and continue")
      PaymentsAmountPage.selectCurrencyAndAmount("1000")

      Then("They lands on account payments page")
      AccountPaymentsPage.checkPage()

      When("They don't want to add any more payments they can select no and continue")
      AccountPaymentsPage.selectNoAndContinue()
    }

    Scenario(
      "Accounts - account type page bypassed when AcctNumberType is IBAN (OECD601) (CRS)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("ThirdFI")
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

      Then("They lands on have payments page")
      HavePaymentsPage.checkPageCRS()

      And("They need to select Yes or No and Continue")
      HavePaymentsPage.selectYesAndContinue()

      Then("They land directly on the payments amount page, skipping payments type")
      PaymentsAmountPage.checkPage("interest")

      When("They select a currency and enter a valid amount and continue")
      PaymentsAmountPage.selectCurrencyAndAmount("300")

      Then("They lands on account payments page")
      AccountPaymentsPage.checkPage()

      And("They would like to change the payment made to this account")
      AccountPaymentsPage.changeThePayment("300_GBP_interest")

      Then("They lands on change payments amount page")
      AccountChangePaymentsAmountPage.checkPage()

      And("They want to continue without making any change")
      AccountChangePaymentsAmountPage.submitPage()

      Then("They lands on account change payments page and continue")
      AccountChangePaymentsPage.checkAndContine()

    }

    Scenario(
      "Accounts - account type page bypassed via joint account holders when AcctNumberType is IBAN (OECD601) (CRS)",
      ManualSubmissionTests,
      SoloTests
    ) {
      Given("The user has reached the manual task list as CRS")
      journeyToYourFis()
      selectTheSuitableFI("FirstFI")
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

      Then("They lands on have payments page")
      HavePaymentsPage.checkPageCRS()

      And("They need to select Yes or No and Continue")
      HavePaymentsPage.selectYesAndContinue()

      Then("They land directly on the payments amount page, skipping payments type")
      PaymentsAmountPage.checkPage("interest")

      When("They select a currency and enter a valid amount and continue")
      PaymentsAmountPage.selectCurrencyAndAmount("450")

      Then("They lands on account payments page")
      AccountPaymentsPage.checkPage()

      And("They can continue journey without adding any more payments")
      AccountPaymentsPage.selectNoAndContinue()
    }

  }
}
