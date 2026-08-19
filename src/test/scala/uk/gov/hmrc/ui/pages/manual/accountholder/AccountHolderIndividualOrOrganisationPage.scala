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
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.pages.BasePage

object AccountHolderIndividualOrOrganisationPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/account-holder/individual-or-organisation"

  val individualRadioId: By   = By.id("value_0")
  val organisationRadioId: By = By.id("value_1")

  def checkPage(): this.type = {
    onPage(pageUrl)
    checkH1("Is the account holder an individual or organisation?")
    this
  }

  // TODO: remove once ManualSendAReportIndexPage.clickAccountHolder() is implemented — direct nav is a stand-in for the click-through
  def goToPage(): this.type = {
    Driver.instance.get(pageUrl)
    this
  }

  def selectIndividualAndContinue(): this.type = {
    onPage(pageUrl)
    click(individualRadioId)
    click(submitButtonId)
    this
  }

  def selectOrganisationAndContinue(): this.type = {
    onPage(pageUrl)
    click(organisationRadioId)
    click(submitButtonId)
    this
  }
}
