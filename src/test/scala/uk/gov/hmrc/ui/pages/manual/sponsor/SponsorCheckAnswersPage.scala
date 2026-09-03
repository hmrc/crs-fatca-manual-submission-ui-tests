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

package uk.gov.hmrc.ui.pages.manual.sponsor

import org.openqa.selenium.By
import uk.gov.hmrc.ui.pages.BasePage

object SponsorCheckAnswersPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/sponsor/check-answers"

  val summaryList: By     = By.cssSelector(".govuk-summary-list")
  val taxResidentCard: By = By.cssSelector(".govuk-summary-card")
//
//  val changeHaveSponsor: By = By.cssSelector("a[href*='change-have-sponsor']")
//  val changeName: By = By.cssSelector("a[href*='change-name']")
//  val changeGiin: By = By.cssSelector("a[href*='change-giin']")
//  val changeAddress: By = By.cssSelector("a[href*='change-where-are-they-based']")
//  val changeTaxCountries: By = By.cssSelector("a[href*='change-tax-resident-countries']")

  def checkPage(): this.type = {
    checkDynamicPage()
    checkH1("Check your answers for the sponsor")
    this
  }

  def checkSummaryContains(text: String): this.type = {
    getText(summaryList) should include(text)
    this
  }

  def checkTaxResidentContains(text: String): this.type = {
    getText(taxResidentCard) should include(text)
    this
  }

//  def clickChangeHaveSponsor(): this.type = {
//    click(changeHaveSponsor)
//    this
//  }
//
//  def clickChangeName(): this.type = {
//    click(changeName)
//    this
//  }
//
//  def clickChangeGiin(): this.type = {
//    click(changeGiin)
//    this
//  }
//
//  def clickChangeAddress(): this.type = {
//    click(changeAddress)
//    this
//  }
//
//  def clickChangeTaxCountries(): this.type = {
//    click(changeTaxCountries)
//    this
//  }

  def continueFromCheckAnswers(): this.type = {
    click(submitButtonId)
    this
  }
}
