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

package uk.gov.hmrc.ui.pages.manual.filercategory

import org.openqa.selenium.By
import uk.gov.hmrc.ui.pages.BasePage
import uk.gov.hmrc.selenium.webdriver.Driver

object SponsorCompletionPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/sponsor/have-sponsor"

  val checkAnswersUrl: String = baseUrlManualSub + "/manual/sponsor/check-answers"

  private val continueButton: By = By.cssSelector("main .govuk-button")

  private def answerFor(question: String): By =
    By.xpath(s"//dt[normalize-space()='$question']/following-sibling::dd[1]")

  def checkHaveSponsorPage(fiName: String): this.type = {
    onPage(pageUrl)
    checkH1(s"Does $fiName have a sponsor?")
    this
  }

  def checkSponsorNamePage(): this.type = {
    onPageContaining("/manual/sponsor/name")
    this
  }

  def checkCheckAnswersPage(fiName: String): this.type = {
    onPage(checkAnswersUrl)
    checkH1(s"Check your answers for the sponsor for $fiName")
    this
  }

  def checkHaveSponsorAnswer(expected: String): this.type = {
    val question = "Does the financial institution have a sponsor?"
    assert(isElementPresent(answerFor(question)), s"Expected the '$question' row")
    val actual   = getText(answerFor(question)).trim
    assert(actual == expected, s"Expected '$question' to be '$expected' but found '$actual'")
    this
  }

  def continueFromCheckAnswers(): this.type = {
    onPage(checkAnswersUrl)
    click(continueButton)
    this
  }

  def goToCheckAnswers(): this.type = {
    Driver.instance.get(checkAnswersUrl)
    this
  }
}
