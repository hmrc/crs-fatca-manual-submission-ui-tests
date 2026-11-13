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

package uk.gov.hmrc.ui.pages.manual.accountholder

import uk.gov.hmrc.ui.pages.manual.account.*
import uk.gov.hmrc.ui.utils.*

object AccountHolderJourneyHelper {

  private def completeAccountAsDebtOrEquity(): Unit = {
    AccountClosedPage.selectNoAndContinue()
    AccountBalancePage.selectCurrencyAndAmount("100")
    AccountUndocumentedPage.selectNoAndContinue()
    AccountDormantPage.selectNoAndContinue()
    AccountYearOpenedPage.selectYesAndContinue()
    AccountJointAccountPage.selectNoAndContinue()
    AccountTypePage.selectAccountTypeAndContinue("Debt or equity")
  }

  private def completeFatcaPaymentsAndCheckAnswers(): Unit = {
    AccountClosedPage.selectNoAndContinue()
    AccountBalancePage.selectCurrencyAndAmount("100.50")
    HavePaymentsPage.checkPageFATCA()
    HavePaymentsPage.selectYesAndContinue()
    PaymentsTypePage.checkPage()
    PaymentsTypePage.selectTypeOfPaymentsAndContinue("DividendsFATCA")
    PaymentsAmountPage.checkPage("dividends")
    PaymentsAmountPage.selectOtherCurrencyAmount("596.89")
    AccountPaymentsPage.checkPageFatca()
    AccountPaymentsPage.selectNoAndContinue()
    AccountCheckAnswersPage.checkPage()
    AccountCheckAnswersPage.submitPage()
    AccountAccountsPage.checkPage()
  }

  private def completeCrsPaymentsAndCheckAnswers(): Unit = {
    HavePaymentsPage.checkPageCRS()
    HavePaymentsPage.selectYesAndContinue()
    PaymentsTypePage.selectTypeOfPaymentsAndContinue("GrossProceedsOrRedemptionsCRS")
    PaymentsAmountPage.checkPage("gross proceeds or redemptions")
    PaymentsAmountPage.selectOtherCurrencyAmount("500")
    AccountPaymentsPage.checkPage()
    AccountPaymentsPage.selectNoAndContinue()
    AccountCheckAnswersPage.checkPage()
    AccountCheckAnswersPage.submitPage()
    AccountAccountsPage.checkPage()
  }

  def completeAccountByNumberCrs(): Unit = {
    AccountHaveNumberPage.selectYesAndContinue()
    AccountNumberTypePage.checkPage()
    AccountNumberTypePage.selectAccountNumberType(TestData.iban)
    AccountNumberPage.checkPage()
    AccountNumberPage.enterNumberAndContinue(AccountHolderAccountPage.defaultIbanNumber)
    AccountClosedPage.checkPage()
    AccountClosedPage.selectYesAndContinue()
    AccountCurrencyPage.selectCurrencyAndContinue()
    AccountUndocumentedPage.selectNoAndContinue()
    AccountDormantPage.selectNoAndContinue()
    AccountYearOpenedPage.selectYesAndContinue()
    AccountJointAccountPage.selectNoAndContinue()
    AccountTypePage.checkPageIsBypassed()
    HavePaymentsPage.checkPageCRS()
    HavePaymentsPage.selectYesAndContinue()
    PaymentsAmountPage.checkPage("interest")
    PaymentsAmountPage.selectCurrencyAndAmount("450")
    AccountPaymentsPage.checkPage()
    AccountPaymentsPage.selectNoAndContinue()
    AccountCheckAnswersPage.checkPage()
    AccountCheckAnswersPage.submitPage()
    AccountAccountsPage.checkPage()
  }

  def completeAccountByDebtOrEquityIdentifierCrs(): Unit = {
    AccountHaveNumberPage.selectNoAndContinue()
    AccountIdentifierPage.enterIdentifierAndContinue()
    completeAccountAsDebtOrEquity()
    completeCrsPaymentsAndCheckAnswers()
  }

  def completeAccountByNumberFatca(): Unit = {
    AccountHaveNumberPage.selectYesAndContinue()
    AccountNumberTypePage.selectAccountNumberType(TestData.iban)
    AccountNumberPage.enterNumberAndContinue(AccountHolderAccountPage.defaultIbanNumber)
    completeFatcaPaymentsAndCheckAnswers()
  }

  def completeAccountByIdentifierFatca(): Unit = {
    AccountHaveNumberPage.selectNoAndContinue()
    AccountIdentifierPage.checkPage()
    AccountIdentifierPage.enterIdentifierAndContinue()
    completeFatcaPaymentsAndCheckAnswers()
  }

  def openAccountHoldersAndSelectFirstAccount(): Unit = {
    AccountHolderAccountPage.goToTaskList()
    AccountHolderAccountPage.clickAccountHolders()
    AccountHolderAccountPage.checkAccountsListed()
    AccountHolderAccountPage.selectFirstAccountAndContinue()
  }
}
