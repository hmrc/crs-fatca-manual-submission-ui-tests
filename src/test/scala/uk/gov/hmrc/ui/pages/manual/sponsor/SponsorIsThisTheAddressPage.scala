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
import uk.gov.hmrc.ui.utils.TestData

object SponsorIsThisTheAddressPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/sponsor/is-this-the-address"

  val addressBlock: By = By.cssSelector(".govuk-body")

  def checkPage(): this.type = {
    onPage(pageUrl)
    checkH1(s"Is this the address for ${TestData.sponsorName}?")
    this
  }

  def checkCountryDisplayed(country: String): this.type = {
    getText(addressBlock) should include(country)
    this
  }

  def checkCountryNotDisplayed(country: String): this.type = {
    getText(addressBlock) should not include country
    this
  }
}
