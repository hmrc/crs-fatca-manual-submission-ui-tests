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

object CpSoListPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/controlling-persons-or-substantial-owners"

  private val continueButton: By   = By.cssSelector("form button[type='submit']")
  private val successBanner: By    = By.cssSelector(".govuk-notification-banner--success")
  private val backToReportLink: By = By.partialLinkText("Back to send a")

  private def role(regime: String): String =
    if (regime == "CRS") "controlling person" else "substantial owner"

  private def rowFor(name: String): String =
    s"//div[contains(@class,'govuk-summary-list__row')][.//span[contains(normalize-space(.),'$name')]]"

  def goToPage(): this.type = {
    Driver.instance.get(pageUrl)
    this
  }

  def checkAddedCount(regime: String, count: Int): this.type = {
    onPage(pageUrl)
    val plural = if (count == 1) role(regime) else s"${role(regime)}s"
    checkH1(s"You have added $count $plural")
    this
  }

  def checkNoneAdded(regime: String): this.type = {
    onPage(pageUrl)
    checkH1(s"You have not added any ${role(regime)}s")
    this
  }

  def checkMarkedIncomplete(name: String): this.type = {
    onPage(pageUrl)
    val tag = By.xpath(rowFor(name) + "//strong[contains(@class,'govuk-tag')]")
    assert(isElementPresent(tag), s"Expected an Incomplete tag for $name")
    getText(tag) should include("Incomplete")
    this
  }

  def checkNotMarkedIncomplete(name: String): this.type = {
    onPage(pageUrl)
    assert(
      Driver.instance.findElements(By.xpath(rowFor(name) + "//strong[contains(@class,'govuk-tag')]")).isEmpty,
      s"$name should not be marked Incomplete"
    )
    this
  }

  def clickRemove(name: String): this.type = {
    onPage(pageUrl)
    click(By.xpath(rowFor(name) + "//a[contains(normalize-space(.),'Remove')]"))
    this
  }

  def checkRemovedBanner(regime: String, name: String): this.type = {
    onPage(pageUrl)
    getText(successBanner) should include(s"${role(regime).capitalize} $name removed.")
    this
  }

  def checkAccountHolderMissingControllingPerson(): this.type = {
    onPage(pageUrl)
    val page = Driver.instance.getPageSource
    assert(page.contains("You must add at least one controlling person."), "Expected the must-add text")
    assert(page.contains("is missing a controlling person."), "Expected the missing controlling person text")
    this
  }

  def chooseNoAnotherAndContinue(): this.type = {
    onPage(pageUrl)
    click(noRadioId)
    click(continueButton)
    this
  }

  def clickBackToSendAReport(): this.type = {
    onPage(pageUrl)
    click(backToReportLink)
    onPageContaining("/manual/send-a-report")
    this
  }

  def checkAccountHolderIsMissingControllingPerson(): this.type = {
    onPage(pageUrl)
    assert(
      Driver.instance.getPageSource.contains("is missing a controlling person."),
      "Expected the 'is missing a controlling person.' text"
    )
    this
  }

  def checkNoAccountHolderIsMissingControllingPerson(): this.type = {
    onPage(pageUrl)
    assert(
      !Driver.instance.getPageSource.contains("is missing a controlling person."),
      "Did not expect the 'is missing a controlling person.' text"
    )
    this
  }

  def checkAddAnotherQuestionShown(regime: String): this.type = {
    onPage(pageUrl)
    assert(
      Driver.instance.getPageSource.contains(s"Do you need to add another ${role(regime)}?"),
      s"Expected the add another ${role(regime)} question"
    )
    this
  }

  def checkAddAnotherQuestionNotShown(regime: String): this.type = {
    onPage(pageUrl)
    assert(
      !Driver.instance.getPageSource.contains(s"Do you need to add another ${role(regime)}?"),
      s"Did not expect the add another ${role(regime)} question"
    )
    this
  }

  def clickAddButton(regime: String): this.type = {
    onPage(pageUrl)
    click(By.linkText(s"Add a ${role(regime)}"))
    this
  }
}
