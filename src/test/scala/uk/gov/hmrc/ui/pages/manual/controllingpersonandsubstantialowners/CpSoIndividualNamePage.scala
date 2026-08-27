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

import org.openqa.selenium.By
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.pages.BasePage

object CpSoIndividualNamePage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/cp-so/individual-name"

  val firstNameInput: By = By.id("firstName")
  val lastNameInput: By  = By.id("lastName")

  def checkHeadingForCrs(): this.type = {
    onPage(pageUrl)
    checkH1("What is the name of the controlling person?")
    this
  }

  def checkHeadingForFatca(): this.type = {
    onPage(pageUrl)
    checkH1("What is the name of the substantial owner?")
    this
  }

  // TODO: remove once /manual/cp-so/account-holder is implemented — CRS currently has
  // no click-through to this page (individual-or-organisation is FATCA-only), so this is a stand-in.
  def goToPage(): this.type = {
    Driver.instance.get(pageUrl)
    this
  }

  def enterNameAndContinue(firstName: String, lastName: String): this.type = {
    onPage(pageUrl)
    sendKeys(firstNameInput, firstName)
    sendKeys(lastNameInput, lastName)
    click(submitButtonId)
    this
  }
}
