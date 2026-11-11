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

package uk.gov.hmrc.ui.pages.manual

import org.openqa.selenium.By
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.pages.BasePage
import uk.gov.hmrc.ui.utils.TestData

object ReportCheckAnswersPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/report-details/check-answers"

  val changeCrsOrFatcaLink: By   = By.cssSelector("a[href*='change-crs-or-fatca']")
  val changeYearLink: By         = By.cssSelector("a[href*='change-year']")
  val changeTypeOfReportLink: By = By.cssSelector("a[href*='change-type-of-report']")
  val warningText: By            = By.cssSelector(".govuk-warning-text__text")

  val warningLine1 = "You will not be able to change these details after creating the draft."
  val warningLine2 = "To use different details later, you must create a new draft."

  def checkPage(): this.type = {
    checkDynamicPage()
    checkH1(s"Check your report details for ${TestData.firstFi}")
    this
  }

  def checkWarningText(): this.type = {
    onPage(pageUrl)
    val text = Driver.instance.findElement(warningText).getText
    assert(text.contains(warningLine1))
    assert(text.contains(warningLine2))
    this
  }

  def checkButtonText(): this.type = {
    onPage(pageUrl)
    assert(Driver.instance.findElement(submitButtonId).getText.trim == "Create draft")
    this
  }

  def clickCreateDraft(): Unit = {
    onPage(pageUrl)
    click(submitButtonId)
  }

  def clickChangeCrsOrFatca(): this.type = {
    onPage(pageUrl)
    click(changeCrsOrFatcaLink)
    this
  }

  def clickChangeYear(): this.type = {
    onPage(pageUrl)
    click(changeYearLink)
    this
  }

  def clickChangeTypeOfReport(): this.type = {
    onPage(pageUrl)
    click(changeTypeOfReportLink)
    this
  }

}
