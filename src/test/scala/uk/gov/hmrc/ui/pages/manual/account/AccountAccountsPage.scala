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
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.pages.BasePage
import uk.gov.hmrc.ui.utils.TestData

import scala.jdk.CollectionConverters.*

object AccountAccountsPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/accounts"

  private val addAnotherNoRadio: By  = By.cssSelector("input[name='value'][value='false']")
  private val addAnotherContinue: By = By.cssSelector("form button[type='submit']")

  private val incompleteTags: By =
    By.xpath(
      "//dl[contains(@class,'accounts-summary-list')]//strong[contains(@class,'govuk-tag')][normalize-space()='Incomplete']"
    )

  private val accountsStatus: By = By.id("accounts-information-1-status")

  def checkPage(): this.type = {
    onPage(pageUrl)
    checkH1("You have added 1 account")
    this
  }

  def checkPageForNoAccounts(): this.type = {
    onPage(pageUrl)
    checkH1("You have not added any accounts")
    this
  }

  def notificationBanner(): this.type = {
    onPage(pageUrl)
    checkH3(s"Account ${TestData.iBANAccount} removed")
    this
  }

  def changeAccount(account: String): Unit = {
    onPage(pageUrl)
    click(changeAccountLink(account))
  }

  def removeAccount(account: String): Unit = {
    onPage(pageUrl)
    click(removeAccountLink(account))
  }

  def goToAccountsList(): this.type = {
    Driver.instance.get(pageUrl)
    onPage(pageUrl)
    this
  }

  def selectNoToAddAnotherAndContinue(): this.type = {
    onPage(pageUrl)
    click(addAnotherNoRadio)
    click(addAnotherContinue)
    this
  }

  def checkIncompleteTagCount(expected: Int): this.type = {
    onPage(pageUrl)
    val actual = Driver.instance.findElements(incompleteTags).asScala.size
    assert(actual == expected, s"Expected $expected Incomplete tag(s) on the accounts list but found $actual")
    this
  }

  def checkAccountsStatus(expected: String): this.type = {
    onPageContaining("/manual/send-a-report")
    val actual = getText(accountsStatus).trim
    assert(actual == expected, s"Expected the Accounts status '$expected' but found '$actual'")
    this
  }

}
