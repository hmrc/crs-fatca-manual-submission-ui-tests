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

package uk.gov.hmrc.ui.pages.manual.account

import org.openqa.selenium.By
import uk.gov.hmrc.ui.pages.BasePage

object AccountPaymentsPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/account/payments"

  val paymentSummaryList: By = By.cssSelector(".govuk-summary-list")
  val changeLink: By         = By.linkText("Change")
  val removeLink: By         = By.linkText("Remove")

  def checkPage(): this.type = {
    checkH1("You have added 1 payment made to this account")
    this
  }

  def checkPageFatca(): this.type = {
    checkH1("You have added 1 payment made to this account, a payee or an owner")
    this
  }

  def checkPageWithTwoPayments(): this.type = {
    checkH1("You have added 2 payments made to this account")
    this
  }

  def checkRemovedPaymentText(): this.type = {
    onPage(pageUrl)
    checkH3("250.75 GBP interest payment removed.")
    this
  }

  def checkPaymentSummaryListContains(text: String): this.type = {
    getText(paymentSummaryList) should include(text)
    this
  }

  def changeThePayment(payment: String): Unit = {
    onPage(pageUrl)
    click(changePaymentLink(payment))
  }

  def removeThePayment(removePayment: String): Unit = {
    onPage(pageUrl)
    click(removePaymentLink(removePayment))
  }

}
