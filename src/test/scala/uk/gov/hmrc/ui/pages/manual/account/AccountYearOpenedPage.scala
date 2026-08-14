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
import uk.gov.hmrc.ui.utils.TestData

object AccountYearOpenedPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/account/year-opened"

  val yesRadio: By         = By.cssSelector("input[name='value'][value='yes']")
  val noRadio: By          = By.cssSelector("input[name='value'][value='no']")
  val notReportedRadio: By = By.cssSelector("input[name='value'][value='notReported']")

  def checkPage(): this.type = {
    onPage(pageUrl)
    checkH1(s"Was the account opened in ${TestData.reportingYear}?")
    this
  }

  override def selectYesAndContinue(): Unit = {
    checkDynamicPage()
    click(yesRadio)
    click(submitButtonId)
  }

  override def selectNoAndContinue(): Unit = {
    checkDynamicPage()
    click(noRadio)
    click(submitButtonId)
  }

  def selectNotReportedAndContinue(): this.type = {
    checkDynamicPage()
    click(notReportedRadio)
    click(submitButtonId)
    this
  }

  def checkNotReportedIsPresent(): this.type = {
    isElementPresent(notReportedRadio) shouldBe true
    this
  }

  def checkNotReportedIsNotPresent(): this.type = {
    isElementPresent(notReportedRadio) shouldBe false
    this
  }
}
