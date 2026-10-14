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
import uk.gov.hmrc.ui.pages.manual.*
import uk.gov.hmrc.ui.pages.manual.account.*
import uk.gov.hmrc.ui.utils.*

import scala.jdk.CollectionConverters.*

object AccountHolderAccountPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/account-holder/account"

  val sendAReportUrl: String = baseUrlManualSub + "/manual/send-a-report"

  val addAccountLink: By     = By.linkText("add an account")
  val backToReportLink: By   = By.partialLinkText("Back to send a")
  val checkboxInputs: By     = By.cssSelector(".govuk-checkboxes__input")
  val accountHoldersLink: By = By.linkText("Account holders")
  val firstAccountLabel: By  = By.xpath("(//label[contains(@class,'govuk-checkboxes__label')])[1]")

  def labelFor(text: String): By =
    By.xpath(s"//label[contains(@class,'govuk-checkboxes__label')][normalize-space()='$text']")

  def inputFor(text: String): By =
    By.xpath(s"//label[contains(@class,'govuk-checkboxes__label')][normalize-space()='$text']/preceding-sibling::input")

  val defaultIbanNumber: String = "GB33BUKB20201555555555"

  /** journey helpers: an account must exist before this page (and anything after it) can be reached --- Adds one
    * account via the account-number route (have number = Yes, IBAN), then returns to the task list Adds one account via
    * the identifier route (have number = No), then returns to the task list
    */

  def addAccountByNumber(number: String = defaultIbanNumber): this.type = {
    ManualSendAReportIndexPage.clickAccounts()
    AccountHaveNumberPage.selectYesAndContinue()
    AccountNumberTypePage.selectAccountNumberType(TestData.iban)
    AccountNumberPage.enterNumberAndContinue(number)
    goToTaskList()
  }

  def addAccountByIdentifier(): this.type = {
    ManualSendAReportIndexPage.clickAccounts()
    AccountHaveNumberPage.selectNoAndContinue()
    AccountIdentifierPage.enterIdentifierAndContinue()
    goToTaskList()
  }

  /** Account number route: add an account by number, go to /account-holder/account, verify it is listed, tick it and
    * continue -> /individual-or-organisation
    */

  def reachIndividualOrOrganisationPageByNumber(): this.type = {
    addAccountByNumber()
    goToPage()
    checkAccountsListed()
    checkAccountLabelPresent(defaultIbanNumber)
    selectAccountAndContinue(defaultIbanNumber)
  }

  /** Account identifier route: same journey, but the account is added via have number = No -> identifier. The checkbox
    * label is whatever identifier the identifier page enters, so the first checkbox is ticked.
    */

  def reachIndividualOrOrganisationPageByIdentifier(): this.type = {
    addAccountByIdentifier()
    goToPage()
    checkAccountsListed()
    selectFirstAccountAndContinue()
  }

  /* Direct navigation to /manual/account-holder/account (stand-in for clicking the Account Holders link) */

  def goToPage(): this.type = {
    Driver.instance.get(pageUrl)
    this
  }

  def goToTaskList(): this.type = {
    Driver.instance.get(sendAReportUrl)
    this
  }

  def clickAccountHolders(): this.type = {
    click(accountHoldersLink)
    this
  }

  def checkPage(): this.type = {
    onPage(pageUrl)
    checkH1("Accounts held by this account holder")
    this
  }

  def checkAccountsListed(): this.type = {
    checkPage()
    assert(isElementPresent(checkboxInputs), "Expected at least one account checkbox")
    this
  }

  def checkCheckboxCount(expected: Int): this.type = {
    val actual = Driver.instance.findElements(checkboxInputs).asScala.size
    assert(actual == expected, s"Expected $expected account checkboxes but found $actual")
    this
  }

  def checkAccountLabelPresent(label: String): this.type = {
    assert(isElementPresent(labelFor(label)), s"Expected a checkbox labelled '$label'")
    this
  }

  def selectAccount(label: String): this.type = {
    click(labelFor(label))
    this
  }

  def checkAccountSelected(label: String, expected: Boolean): this.type = {
    val actual = Driver.instance.findElement(inputFor(label)).isSelected
    assert(actual == expected, s"Checkbox '$label' selected = $actual but expected $expected")
    this
  }

  def saveAndContinue(): this.type = {
    onPage(pageUrl)
    click(submitButtonId)
    this
  }

  def selectFirstAccountAndContinue(): this.type = {
    click(firstAccountLabel)
    saveAndContinue()
  }

  def selectAccountAndContinue(label: String): this.type = {
    selectAccount(label)
    saveAndContinue()
  }

  def checkNoAccountsState(regime: String): this.type = {
    checkPage()
    assert(isElementPresent(addAccountLink), "Expected the 'add an account' link")
    assert(isElementPresent(backToReportLink), "Expected the 'Back to send a report' link")
    val linkText = Driver.instance.findElement(backToReportLink).getText
    assert(linkText.contains(s"send a $regime report for"), s"Unexpected back link text: $linkText")
    assert(!linkText.contains("[FI name]"), s"FI name placeholder not populated: $linkText")
    assert(Driver.instance.findElements(checkboxInputs).isEmpty, "No checkboxes should show when there are no accounts")
    assert(
      Driver.instance.findElements(submitButtonId).isEmpty,
      "No Save and continue button should show when there are no accounts"
    )
    this
  }

  def clickAddAnAccount(): this.type = {
    click(addAccountLink)
    this
  }

  def clickBackToSendAReport(): this.type = {
    click(backToReportLink)
    onPageContaining("/manual/send-a-report")
    this
  }
}
