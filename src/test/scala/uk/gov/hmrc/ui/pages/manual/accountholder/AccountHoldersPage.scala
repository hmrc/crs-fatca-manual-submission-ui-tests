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
import uk.gov.hmrc.ui.pages.manual.account.*
import uk.gov.hmrc.ui.utils.TestData

import scala.jdk.CollectionConverters.*

object AccountHoldersPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/account-holders"

  val rows: By                   = By.cssSelector("dl.hmrc-list-with-actions .govuk-summary-list__row")
  val rowNames: By               = By.cssSelector("dl.hmrc-list-with-actions .govuk-summary-list__key")
  val changeLinks: By            = By.cssSelector("dl.hmrc-list-with-actions a[href*='change-account-holder']")
  val removeLinks: By            = By.cssSelector("dl.hmrc-list-with-actions a[href*='remove-account-holder']")
  val incompleteTag: By          = By.xpath("//strong[contains(@class,'govuk-tag')][normalize-space()='Incomplete']")
  val notificationBanner: By     = By.cssSelector(".govuk-notification-banner")
  val successBanner: By          = By.cssSelector(".govuk-notification-banner--success")
  val addAccountHolderButton: By = By.partialLinkText("Add an account holder")
  val addAnotherYes: By          = By.cssSelector("input[name='value'][value='true']")
  val addAnotherNo: By           = By.cssSelector("input[name='value'][value='false']")
  val continueButton: By         = By.cssSelector("form button[type='submit']")
  val missingAccountItems: By    = By.xpath("//p[contains(.,'missing an account holder')]/following-sibling::ul[1]/li")

  val addAnotherQuestion: String = "Do you need to add another account holder?"

  def goToPage(): this.type = {
    Driver.instance.get(pageUrl)
    this
  }

  def checkPage(): this.type = {
    onPage(pageUrl)
    this
  }

  def addAnotherAccountByNumber(number: String): this.type = {
    Driver.instance.get(baseUrlManualSub + "/manual/accounts")
    click(addAnotherYes)
    click(continueButton)
    AccountHaveNumberPage.selectYesAndContinue()
    AccountNumberTypePage.selectAccountNumberType(TestData.iban)
    AccountNumberPage.enterNumberAndContinue(number)
    AccountClosedPage.checkPage()
    AccountHolderAccountPage.goToTaskList()
    this
  }

  def addOrganisationAccountHolder(regime: String, accountNumber: String): this.type = {
    AccountHolderAccountPage.goToPage()
    AccountHolderAccountPage.selectAccountAndContinue(accountNumber)
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

  def secondAccountHolderName: String =
    s"${TestData.secondAccountHolderFirstName} ${TestData.secondAccountHolderLastName}"

  def addIndividualAccountHolderToFirstAccount(): this.type = {
    AccountHolderAccountPage.selectFirstAccountAndContinue()
    AccountHolderIndividualOrOrganisationPage.selectIndividualAndContinue()
    AccountHolderIndividualNamePage.enterNameAndContinue(
      TestData.secondAccountHolderFirstName,
      TestData.secondAccountHolderLastName
    )
    AccountHolderIndividualHaveDoB.checkPage()
    AccountHolderAccountPage.goToTaskList()
    this
  }

  def fiName(regime: String): String = if (regime == "CRS") TestData.firstFi else TestData.fourthFi

  def accountHolderCount: Int = Driver.instance.findElements(rows).size

  def accountHolderNames: Seq[String] =
    Driver.instance
      .findElements(rowNames)
      .asScala
      .map(_.getText.replace("Incomplete", "").trim)
      .toSeq

  def firstAccountHolderName: String = {
    onPage(pageUrl)
    accountHolderNames.headOption.getOrElse(
      throw new AssertionError("Expected at least one account holder in the list")
    )
  }

  def expectedHeading(count: Int): String = count match {
    case 0 => "You have not added any account holders"
    case 1 => "You have added 1 account holder"
    case n => s"You have added $n account holders"
  }

  def checkHeading(count: Int): this.type = {
    val actual = getText(pageHeader).trim
    assert(actual == expectedHeading(count), s"Expected H1 '${expectedHeading(count)}' but found '$actual'")
    assert(accountHolderCount == count, s"Expected $count account holder rows but found $accountHolderCount")
    this
  }

  def checkAccountHolderNotListed(name: String): this.type = {
    assert(!accountHolderNames.contains(name), s"Did not expect '$name' in the list: $accountHolderNames")
    this
  }

  def checkChangeAndRemoveLinks(): this.type = {
    val count   = accountHolderCount
    val changes = Driver.instance.findElements(changeLinks).size
    val removes = Driver.instance.findElements(removeLinks).size
    assert(changes == count, s"Expected $count Change links but found $changes")
    assert(removes == count, s"Expected $count Remove links but found $removes")
    this
  }

  private def rowLink(name: String, hrefPart: String): By =
    By.xpath(
      s"//dl[contains(@class,'hmrc-list-with-actions')]//div[contains(@class,'govuk-summary-list__row')]" +
        s"[.//dt[contains(normalize-space(.),'$name')]]//a[contains(@href,'$hrefPart')]"
    )

  private var selectedName: Option[String] = None

  def selectedAccountHolderName: String =
    selectedName.getOrElse(throw new AssertionError("No account holder has been selected with Change or Remove yet"))

  def clickChange(name: String = firstAccountHolderName): this.type = {
    selectedName = Some(name)
    click(rowLink(name, "change-account-holder"))
    this
  }

  def clickRemove(name: String = firstAccountHolderName): this.type = {
    selectedName = Some(name)
    click(rowLink(name, "remove-account-holder"))
    this
  }

  def continueFromCheckAnswers(): this.type = {
    onPage(baseUrlManualSub + "/manual/account-holder/check-answers")
    click(continueButton)
    this
  }

  def incompleteTagCount: Int = Driver.instance.findElements(incompleteTag).size

  def checkIncompleteTagCount(expected: Int): this.type = {
    assert(incompleteTagCount == expected, s"Expected $expected 'Incomplete' tags but found $incompleteTagCount")
    this
  }

  def checkIncompleteTagShown(): this.type = {
    assert(incompleteTagCount > 0, "Expected at least one 'Incomplete' tag")
    this
  }

  def checkRemovedBanner(name: String = selectedAccountHolderName): this.type = {
    assert(isElementPresent(successBanner), "Expected the success notification banner")
    val text = Driver.instance.findElement(successBanner).getText
    assert(text.contains(s"Account holder $name removed."), s"Unexpected banner text: $text")
    this
  }

  def checkNoBanner(): this.type = {
    assert(!isElementPresent(notificationBanner), "Did not expect a notification banner")
    this
  }

  def checkNoAccountHolders(name: String = selectedAccountHolderName): this.type = {
    checkHeading(0)
    assert(
      Driver.instance.getPageSource.contains("You must add at least one account holder for each account."),
      "Expected 'You must add at least one account holder for each account.'"
    )
    assert(!accountHolderNames.contains(name), s"Did not expect '$name' in the list: $accountHolderNames")
    checkAddAnAccountHolderButtonShown()
    this
  }

  def checkAddAnAccountHolderButtonShown(): this.type = {
    assert(isElementPresent(addAccountHolderButton), "Expected the 'Add an account holder' button")
    this
  }

  def clickAddAnAccountHolder(): this.type = {
    click(addAccountHolderButton)
    this
  }

  def missingAccounts: Seq[String] =
    Driver.instance.findElements(missingAccountItems).asScala.map(_.getText.trim).toSeq

  def checkOneAccountMissing(account: String): this.type = {
    val page = Driver.instance.getPageSource
    assert(
      page.contains(s"The account $account is missing an account holder."),
      s"Expected the single missing-account text for $account"
    )
    assert(!page.contains("These accounts are missing an account holder"), "Did not expect the multiple-accounts text")
    assert(page.contains("You must either:"), "Expected 'You must either:'")
    assert(
      page.contains("change or add at least one account holder to link to the account"),
      "Expected the change/add bullet"
    )
    assert(page.contains("remove the account"), "Expected the remove bullet")
    assert(!page.contains("link to the accounts"), "Did not expect the plural bullets")
    checkAddAnAccountHolderButtonShown()
    checkAddAnotherQuestionNotShown()
    this
  }

  def checkAccountsMissing(accounts: Seq[String]): this.type = {
    val page = Driver.instance.getPageSource
    assert(
      page.contains("These accounts are missing an account holder:"),
      "Expected the multiple missing-accounts text"
    )
    assert(
      missingAccounts.sorted == accounts.sorted,
      s"Expected missing accounts ${accounts.sorted} but found ${missingAccounts.sorted}"
    )
    assert(page.contains("You must either:"), "Expected 'You must either:'")
    assert(
      page.contains("change or add at least one account holder to link to the accounts"),
      "Expected the change/add bullet"
    )
    assert(page.contains("remove the accounts"), "Expected the remove bullet")
    checkAddAnAccountHolderButtonShown()
    checkAddAnotherQuestionNotShown()
    this
  }

  def checkAddAnotherQuestionShown(): this.type = {
    assert(Driver.instance.getPageSource.contains(addAnotherQuestion), s"Expected '$addAnotherQuestion'")
    assert(isElementPresent(addAnotherYes) && isElementPresent(addAnotherNo), "Expected Yes and No radios")
    this
  }

  def checkAddAnotherQuestionNotShown(): this.type = {
    assert(!Driver.instance.getPageSource.contains(addAnotherQuestion), s"Did not expect '$addAnotherQuestion'")
    this
  }

  def selectYesToAddAnotherAndContinue(): this.type = {
    click(addAnotherYes)
    click(continueButton)
    this
  }

  def selectNoToAddAnotherAndContinue(): this.type = {
    click(addAnotherNo)
    click(continueButton)
    this
  }

  def checkUnderConstruction(): this.type = {
    onPageContaining("/under-construction")
    checkH1("Under construction")
    this
  }

  def checkBackLink(regime: String, fiName: String): this.type = {
    val link = By.partialLinkText("Back to send a")
    assert(isElementPresent(link), "Expected the 'Back to send a report' link")
    val text = Driver.instance.findElement(link).getText
    assert(text.trim == s"Back to send a $regime report for $fiName", s"Unexpected back link text: $text")
    this
  }

  def clickBackToSendAReport(): this.type = {
    click(By.partialLinkText("Back to send a"))
    onPage(baseUrlManualSub + "/manual/send-a-report")
    this
  }
}
