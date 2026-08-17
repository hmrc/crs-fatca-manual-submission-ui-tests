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

import org.openqa.selenium.support.ui.{ExpectedConditions, FluentWait, Select, Wait}
import org.openqa.selenium.{By, JavascriptExecutor, WebDriver}
import org.scalatest.Assertion
import org.scalatest.matchers.should.Matchers
import uk.gov.hmrc.selenium.component.PageObject
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.conf.TestConfiguration
import uk.gov.hmrc.ui.driver.BrowserDriver
import uk.gov.hmrc.ui.utils.{IdGenerators, TestData}

import java.time.Duration

trait BasePage extends BrowserDriver with Matchers with IdGenerators with PageObject {

  val pageUrl: String
  val baseUrlFi: String                    = TestConfiguration.url("crs-fatca-fi-management-frontend")
  val baseUrlManualSub: String             = TestConfiguration.url("crs-fatca-manual-submission-frontend")
  val submitButtonId: By                   = By.id("submit")
  val backLinkText: By                     = By.linkText("Back")
  val pageHeader: By                       = By.tagName("h1")
  val yesRadioId: By                       = By.id("value")
  val noRadioId: By                        = By.id("value-no")
  def removeCountryId(country: String): By = By.id(s"remove-country-$country")

  def clickOnBackLink(): Unit = {
    onPage()
    click(backLinkText)
  }

  def submitPage(): this.type = {
    onPage(pageUrl)
    click(submitButtonId)
    this
  }

  def onPage(url: String = this.pageUrl): this.type = {
    fluentWait.until(ExpectedConditions.urlToBe(url))
    this
  }

  def selectYesAndContinue(): Unit = {
    checkDynamicPage()
    click(yesRadioId)
    click(submitButtonId)
  }

  def checkDynamicPage(): this.type = {
    onPageContaining(pageUrl)
    this
  }

  def onPageContaining(urlPart: String): this.type = {
    fluentWait.until(ExpectedConditions.urlContains(urlPart))
    this
  }

  private def fluentWait: Wait[WebDriver] = new FluentWait[WebDriver](Driver.instance)
    .withTimeout(Duration.ofSeconds(15))
    .pollingEvery(Duration.ofMillis(200))

  def selectNoAndContinue(): Unit = {
    checkDynamicPage()
    click(noRadioId)
    click(submitButtonId)
  }

  def checkH1(h1: String): Assertion =
    getText(pageHeader) should include(h1)

  def waitUntilVisible(locator: By): Unit =
    fluentWait.until(ExpectedConditions.visibilityOfElementLocated(locator))

  def waitWith(timeoutSeconds: Int): FluentWait[WebDriver] =
    new FluentWait[WebDriver](Driver.instance)
      .withTimeout(Duration.ofSeconds(timeoutSeconds))
      .pollingEvery(Duration.ofMillis(200))

  def selectNoAndContinueFromChange(): Unit = {
    checkChangeDynamicPage()
    click(noRadioId)
    click(submitButtonId)
  }

  def checkChangeDynamicPage(): this.type = {
    onPageContaining(
      pageUrl
        .replace("/elections/crs/", "/elections/crs/change-")
        .replace("/elections/fatca/", "/elections/fatca/change-")
    )
    this
  }

  def selectYesAndContinueFromChange(): Unit = {
    checkChangeDynamicPage()
    click(yesRadioId)
    click(submitButtonId)
  }

  def isElementPresent(locator: By): Boolean =
    !Driver.instance.findElements(locator).isEmpty

  def waitForStableText(locator: By): String = {
    fluentWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(locator)))
    getText(locator)
  }

  def selectCurrency(): Unit =
    selectFromAutocomplete("currency-select", TestData.currencyGbp)

  def selectFromAutocomplete(selectId: String, visibleText: String): Unit = {
    val js = Driver.instance.asInstanceOf[JavascriptExecutor]
    js.executeScript(s"document.getElementById('$selectId').style.display = 'block';")
    new Select(Driver.instance.findElement(By.id(selectId))).selectByVisibleText(visibleText)
  }

  case class PageNotFoundException(message: String) extends Exception(message)
}
