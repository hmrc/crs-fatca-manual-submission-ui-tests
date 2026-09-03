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
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.pages.BasePage

object AccountTypePage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/account/type"

  val depositoryRadio: By         = By.cssSelector("input[value='CRS1101']")
  val custodialRadio: By          = By.cssSelector("input[value='CRS1102']")
  val cashValueInsuranceRadio: By = By.cssSelector("input[value='CRS1103']")
  val debtOrEquityRadio: By       = By.cssSelector("input[value='CRS1104']")
  val notReportedRadio: By        = By.cssSelector("input[value='CRS1100']")
  val radioItems: By              = By.className("govuk-radios__item")

  def checkPage(): this.type = {
    onPage(pageUrl)
    checkH1("What type of account is this?")
    this
  }

  def checkCashValueInsuranceOptionPresent(): this.type = {
    isElementPresent(cashValueInsuranceRadio) shouldBe true
    this
  }

  def countAccountTypeOptions(): Int = {
    onPage(pageUrl)
    Driver.instance.findElements(radioItems).size()
  }

  def checkAccountTypeOptionsCount(expected: Int): this.type = {
    countAccountTypeOptions() shouldBe expected
    this
  }

  def checkPageIsBypassed(): Unit =
    Driver.instance.getCurrentUrl should not include "/manual/account/type"

  def selectAccountTypeAndContinue(accountType: String): this.type = {
    onPage(pageUrl)
    click(accountType match {
      case "Depository"           => depositoryRadio
      case "Custodial"            => custodialRadio
      case "Cash value insurance" => cashValueInsuranceRadio
      case "Debt or equity"       => debtOrEquityRadio
      case "Not reported"         => notReportedRadio
      case other                  => throw new IllegalArgumentException(s"Unknown account type: $other")
    })
    click(submitButtonId)
    this
  }

}
