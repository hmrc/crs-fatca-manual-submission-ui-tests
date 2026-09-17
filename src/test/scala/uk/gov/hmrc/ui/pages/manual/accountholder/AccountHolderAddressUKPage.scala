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

import org.openqa.selenium.By
import uk.gov.hmrc.ui.pages.BasePage

object AccountHolderAddressUKPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/account-holder/address-uk"

  val addressLineOne: By = By.id("addressLine1")
  val addressLineTwo: By = By.id("addressLine2")
  val city: By           = By.id("city")
  val region: By         = By.id("county")
  val postcode: By       = By.id("postCode")

  def checkPage(): this.type = {
    onPage(pageUrl)
    checkH1("What is the registered address for")
    this
  }

  def enterAddressUK(): Unit = {
    onPage(pageUrl)
    sendKeys(addressLineOne, "42 Test Street")
    sendKeys(addressLineTwo, "Test Town")
    sendKeys(city, "Test City")
    sendKeys(region, "Test Region")
    sendKeys(postcode, "TE57 1PC")
    selectCountry("United Kingdom")
    click(submitButtonId)
  }

  def selectCountry(countryName: String): Unit =
    selectFromAutocomplete("country-select", countryName)
}
