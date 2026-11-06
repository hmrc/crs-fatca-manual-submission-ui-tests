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
import uk.gov.hmrc.ui.pages.manual.accountholder.*

import scala.jdk.CollectionConverters.*

object CpSoAccountHolderPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/cp-so/account-holder"

  val sendAReportUrl: String = baseUrlManualSub + "/manual/send-a-report"

  private val checkboxInputs: By       = By.cssSelector(".govuk-checkboxes__input")
  private val firstCheckboxLabel: By   = By.xpath("(//label[contains(@class,'govuk-checkboxes__label')])[1]")
  private val addAccountHolderLink: By = By.linkText("add an account holder")
  private val backToReportLink: By     = By.partialLinkText("Back to send a")
  private val continueButton: By       = By.cssSelector("form button[type='submit']")

  private val taskListLink: By = By.cssSelector("a[href*='/manual/cp-so/check-cpso-add-or-allowed']")

  private def role(regime: String): String =
    if (regime == "CRS") "controlling person" else "substantial owner"

  def goToPage(): this.type = {
    Driver.instance.get(pageUrl)
    this
  }

  def openFromTaskList(): this.type = {
    click(taskListLink)
    this
  }

  def checkPage(regime: String): this.type = {
    onPage(pageUrl)
    checkH1(s"Account holders linked to this ${role(regime)}")
    this
  }

  def addEligibleAccountHolder(regime: String): this.type = {
    AccountHolderAccountPage.reachIndividualOrOrganisationPageByNumber()
    AccountHolderIndividualOrOrganisationPage.selectOrganisationAndContinue()
    if (regime == "CRS") {
      AccountHolderOrganisationNamePage.enterCrsAccountHolderName()
      AccountHolderOrgCrsTypePage.checkPage()
      AccountHolderOrgCrsTypePage.selectAccountHolderType("passiveWithMultiplePersons")
    } else {
      AccountHolderOrganisationNamePage.enterFatcaAccountHolderName()
      AccountHolderOrgFatcaTypePage.checkPage()
      AccountHolderOrgFatcaTypePage.selectAccountHolderType("ownerDocumented")
    }
    AccountHolderWhereAreTheyBasedPage.checkPage()
    AccountHolderAccountPage.goToTaskList()
    this
  }

  def reachCpSoJourney(regime: String): this.type = {
    addEligibleAccountHolder(regime)
    openFromTaskList()
    checkAccountHoldersListed(regime)
    if (regime == "CRS") checkCrsGuidanceText()
    checkCheckboxCount(1)
    selectFirstAccountHolderAndContinue()
  }

  def checkEmptyState(regime: String): this.type = {
    checkPage(regime)
    assert(isElementPresent(addAccountHolderLink), "Expected the 'add an account holder' link")
    assert(isElementPresent(backToReportLink), "Expected the 'Back to send a report' link")
    val linkText = Driver.instance.findElement(backToReportLink).getText
    assert(linkText.contains(s"send a $regime report for"), s"Unexpected back link text: $linkText")
    assert(!linkText.contains("[FI name]"), s"FI name placeholder not populated: $linkText")
    assert(Driver.instance.findElements(checkboxInputs).isEmpty, "No checkboxes should show in the empty state")
    assert(Driver.instance.findElements(continueButton).isEmpty, "No Continue button should show in the empty state")
    this
  }

  def checkAccountHoldersListed(regime: String): this.type = {
    checkPage(regime)
    val page = Driver.instance.getPageSource
    assert(page.contains(s"Which account holders are linked to this ${role(regime)}?"), "Expected the question")
    assert(page.contains("Select at least one account holder."), "Expected the hint")
    assert(isElementPresent(checkboxInputs), "Expected at least one account holder checkbox")
    assert(isElementPresent(continueButton), "Expected the Continue button")
    this
  }

  def checkCrsGuidanceText(): this.type = {
    assert(
      Driver.instance.getPageSource.contains(
        "You must add at least one controlling person for the account holder if their organisation is a passive non-financial entity."
      ),
      "Expected the CRS guidance text"
    )
    this
  }

  def checkCheckboxCount(expected: Int): this.type = {
    val actual = Driver.instance.findElements(checkboxInputs).asScala.size
    assert(actual == expected, s"Expected $expected account holder checkboxes but found $actual")
    this
  }

  def selectFirstAccountHolderAndContinue(): this.type = {
    onPage(pageUrl)
    click(firstCheckboxLabel)
    click(continueButton)
    this
  }

  def clickAddAnAccountHolder(): this.type = {
    click(addAccountHolderLink)
    this
  }

  def clickBackToSendAReport(): this.type = {
    click(backToReportLink)
    onPageContaining("/manual/send-a-report")
    this
  }
}
