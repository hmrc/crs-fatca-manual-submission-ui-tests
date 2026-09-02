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

object AccountNumberTypePage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/account/number-type"

  private val ibanRadio: By  = By.cssSelector("input[name='value'][value='iBAN']")
  private val obanRadio: By  = By.cssSelector("input[name='value'][value='oBAN']")
  private val isinRadio: By  = By.cssSelector("input[name='value'][value='iSIN']")
  private val osinRadio: By  = By.cssSelector("input[name='value'][value='oSIN']")
  private val sempRadio: By  = By.cssSelector("input[name='value'][value='sEMP']")
  private val otherRadio: By = By.cssSelector("input[name='value'][value='oTHER']")

  def checkPage(): this.type = {
    onPage(pageUrl)
    checkH1("What type of account number or identification number is this?")
    this
  }

  def selectAccountNumberType(accountNumberType: String): this.type = {
    onPage(pageUrl)
    click(accountNumberType match {
      case "IBAN"      => ibanRadio
      case "OBAN"      => obanRadio
      case "ISIN"      => isinRadio
      case "OSIN"      => osinRadio
      case "SEMP"      => sempRadio
      case "Any other" => otherRadio
      case other       => throw new IllegalArgumentException(s"Unknown account number type: $other")
    })
    click(submitButtonId)
    this
  }

  def checkSempIsPresent(): this.type = {
    isElementPresent(sempRadio) shouldBe true
    this
  }

  def checkSempIsNotPresent(): this.type = {
    isElementPresent(sempRadio) shouldBe false
    this
  }

}
