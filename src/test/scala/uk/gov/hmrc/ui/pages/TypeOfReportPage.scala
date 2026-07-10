/*
 * Copyright 2023 HM Revenue & Customs
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
import org.scalatest.Assertion
import uk.gov.hmrc.ui.utils.TestData

object TypeOfReportPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/report-details/type-of-report"

  val reportWithInformationRadio: By = By.id("value_0")
  val nilReportRadio: By             = By.id("value_1")
  val legend: By                     = By.className("govuk-fieldset__legend")

  def checkPage(): this.type = {
    onPage(pageUrl)
    checkH1("Type of report")
    this
  }

  def checkLegend(): Assertion =
    getText(legend) should include(
      s"What type of report would you like to send for ${TestData.firstFi} for ${TestData.reportingYear}?"
    )

  def selectReportWithInformationAndContinue(): this.type = {
    onPage(pageUrl)
    click(reportWithInformationRadio)
    click(submitButtonId)
    this
  }

  def selectNilReportAndContinue(): this.type = {
    onPage(pageUrl)
    click(nilReportRadio)
    click(submitButtonId)
    this
  }

}
