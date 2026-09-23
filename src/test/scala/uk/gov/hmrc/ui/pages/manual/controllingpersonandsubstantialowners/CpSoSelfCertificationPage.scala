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
import uk.gov.hmrc.ui.pages.BasePage

object CpSoSelfCertificationPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/cp-so/self-certification"

  val yesRadio: By         = By.cssSelector("input[value='CRS1001']")
  val noRadio: By          = By.cssSelector("input[value='CRS1002']")
  val notReportedRadio: By = By.cssSelector("input[value='CRS1000']")

  def checkPage(): this.type                                           = {
    onPage(pageUrl)
    checkH1("provided a valid self-certification?")
    this
  }
  def selectSelfCertificationAndContinue(selection: String): this.type = {
    onPage(pageUrl)
    click(selection match {
      case "Yes"          => yesRadio
      case "No"           => noRadio
      case "Not reported" => notReportedRadio
      case other          => throw new IllegalArgumentException(s"Unknown self-certification selection: $other")
    })
    click(submitButtonId)
    this
  }

  def isNotReportedOptionPresent: Boolean = isElementPresent(notReportedRadio)

  def checkNotReportedOptionPresent(): this.type = {
    isElementPresent(notReportedRadio) shouldBe true
    this
  }

}
