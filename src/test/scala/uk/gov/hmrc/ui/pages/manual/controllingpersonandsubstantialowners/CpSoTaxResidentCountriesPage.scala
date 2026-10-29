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

package uk.gov.hmrc.ui.pages.manual.controllingpersonandsubstantialowners

import uk.gov.hmrc.ui.pages.BasePage
import uk.gov.hmrc.ui.utils.TestData

object CpSoTaxResidentCountriesPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/cp-so/tax-resident-countries"

  def checkPage(): this.type = {
    onPage(pageUrl)
    checkH1(s"You have added 1 country where ${TestData.firstName} ${TestData.lastName} is resident for tax")
    this
  }

  def checkPageForOrganisation(): this.type = {
    onPage(pageUrl)
    checkH1("You have added 1 country where ABC Corp is resident for tax")
    this
  }

  def changeCountry(country: String): Unit = {
    onPage(pageUrl)
    click(changeCountryId(country))
  }

  def removeCountry(country: String): Unit = {
    onPage(pageUrl)
    click(removeCountryId(country))
  }
}
