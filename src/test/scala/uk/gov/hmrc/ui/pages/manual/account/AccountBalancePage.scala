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
import uk.gov.hmrc.ui.utils.TestData

object AccountBalancePage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/account/balance"

  private val amountInput: By = By.id("amount")

  def checkPage(): this.type = {
    onPage(pageUrl)
    checkH1("What was the account balance?")
    this
  }

  def selectCurrencyAndAmount(amount: String): this.type = {
    onPage(pageUrl)
    selectCurrency()
    sendKeys(amountInput, amount)
    click(submitButtonId)
    this
  }
}
