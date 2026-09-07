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

object PaymentsTypePage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/account/payments-type"

  private val dividendsFatcaRadio: By                   = By.cssSelector("input[value='FATCA501']")
  private val dividendsCRSRadio: By                     = By.cssSelector("input[value='CRS501']")
  private val interestFATCARadio: By                    = By.cssSelector("input[value='FATCA502']")
  private val interestCRSRadio: By                      = By.cssSelector("input[value='CRS502']")
  private val grossProceedsOrRedemptionssFATCARadio: By = By.cssSelector("input[value='FATCA503']")
  private val grossProceedsOrRedemptionsCrsRadio: By    = By.cssSelector("input[value='CRS503']")
  private val otherRadio: By                            = By.cssSelector("input[value='FATCA504']")

  def checkPage(): this.type = {
    onPage(pageUrl)
    checkH1("What type of payments were these for this account?")
    this
  }

  def selectTypeOfPaymentsAndContinue(typeOfPayment: String): this.type = {
    onPage(pageUrl)
    click(typeOfPayment match {
      case "DividendsFATCA"                  => dividendsFatcaRadio
      case "DividendsCRS"                    => dividendsCRSRadio
      case "InterestFATCA"                   => interestFATCARadio
      case "InterestCRS"                     => interestCRSRadio
      case "GrossProceedsOrRedemptionsFATCA" => grossProceedsOrRedemptionssFATCARadio
      case "GrossProceedsOrRedemptionsCRS"   => grossProceedsOrRedemptionsCrsRadio
      case "Other"                           => otherRadio
      case other                             => throw new IllegalArgumentException(s"Unknown type of payments")
    })
    click(submitButtonId)
    this
  }
}
