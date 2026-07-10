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

package uk.gov.hmrc.ui.pages

import org.openqa.selenium.By
import uk.gov.hmrc.ui.utils.TestData

object SponsorGiinPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/sponsor/giin"

  private val giinInput: By = By.id("value")
  private val label: By     = By.cssSelector("label[for='value']")

  def checkPage(): this.type = {
    checkDynamicPage()
    checkH1("Global Intermediary Identification Number (GIIN)")
    this
  }

  def checkLabelForSponsor(): this.type = {
    getText(label) should include(s"What is the GIIN for ${TestData.sponsorName}?")
    this
  }

  def enterGiinAndContinue(): this.type = {
    checkDynamicPage()
    sendKeys(giinInput, TestData.sponsorGiin)
    click(submitButtonId)
    this
  }
}
