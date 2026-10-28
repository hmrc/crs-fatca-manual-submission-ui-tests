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

package uk.gov.hmrc.ui.pages.manual

import org.openqa.selenium.By
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.pages.BasePage

object ReportingFinancialInstitutionPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/reporting-financial-institution"

  val h2Question: By   = By.cssSelector("h2.govuk-heading-m")
  val yesRadio: By     = By.id("value")
  val noRadio: By      = By.id("value-no")
  val taskListLink: By = By.linkText("Reporting financial institution details")

  val notinValue: String = "Not provided. We’ll use ‘NOTIN’ in your report"

  private def rowValue(label: String): By =
    By.xpath(s"//dt[contains(@class,'govuk-summary-list__key')][normalize-space()='$label']/following-sibling::dd[1]")

  private def rowKey(label: String): By =
    By.xpath(s"//dt[contains(@class,'govuk-summary-list__key')][normalize-space()='$label']")

  def openFromTaskList(): this.type = {
    click(taskListLink)
    this
  }

  def checkReturnedToSendAReport(): this.type = {
    onPageContaining("/manual/send-a-report")
    this
  }

  def checkPage(fiName: String): this.type = {
    onPage(pageUrl)
    getText(pageHeader) should include(s"Check the details for $fiName")
    this
  }

  def checkChangeLink(fiName: String): this.type = {
    val link = By.linkText(s"change these details for $fiName if needed")
    assert(isElementPresent(link), s"Expected the change link for $fiName")
    val href = Driver.instance.findElement(link).getAttribute("href")
    assert(href.contains("change-answers"), s"Unexpected change link href: $href")
    this
  }

  def checkRegisteredBusinessChangeLink(fiName: String): this.type = {
    val link = By.linkText(s"change these details for $fiName if needed")
    assert(isElementPresent(link), s"Expected the change link for $fiName")
    val href = Driver.instance.findElement(link).getAttribute("href")
    assert(href.contains("registered-business/change-answers"), s"Unexpected change link href: $href")
    this
  }

  def checkRegisteredUtrParagraph(utr: String): this.type = {
    val para = By.xpath("//p[contains(normalize-space(),'also use the Unique Taxpayer Reference')]")
    assert(isElementPresent(para), "Expected the 'also use the Unique Taxpayer Reference' paragraph")
    getText(para) should include(utr)
    this
  }

  def checkNoTinRows(): this.type = {
    checkRowAbsent("Unique Taxpayer Reference")
    checkRowAbsent("Company Registration Number")
    checkRowAbsent("Trust Unique Reference Number")
    checkRowAbsent("Identification number")
    this
  }

  def checkMustProvideGiinLink(fiName: String): this.type = {
    val link = By.linkText(
      s"provide the Global Intermediary Identification Number (GIIN) for $fiName and change other details if needed"
    )
    assert(isElementPresent(link), s"Expected the 'provide the GIIN' link for $fiName")
    assert(
      !isElementPresent(By.partialLinkText("change these details for")),
      "The standard change link should not show when the GIIN is missing"
    )
    this
  }

  def checkRow(label: String, expectedValue: String): this.type = {
    assert(isElementPresent(rowValue(label)), s"Expected a summary row '$label'")
    getText(rowValue(label)) should include(expectedValue)
    this
  }

  def checkRowAbsent(label: String): this.type = {
    assert(!isElementPresent(rowKey(label)), s"Summary row '$label' should not be shown")
    this
  }

  def checkNameAndAddress(fiName: String, postcode: String = "BA23 2AZ"): this.type = {
    checkRow("Financial institution name", fiName)
    checkRow("Main business address", "2 High Street")
    checkRow("Main business address", postcode)
    this
  }

  def checkQuestion(fiName: String): this.type = {
    getText(h2Question) should include(s"Have you finished checking these details for $fiName?")
    assert(isElementPresent(yesRadio), "Expected the Yes radio")
    assert(isElementPresent(noRadio), "Expected the No radio")
    this
  }

  def checkQuestionHidden(): this.type = {
    assert(!isElementPresent(yesRadio), "Yes radio should not show")
    assert(!isElementPresent(noRadio), "No radio should not show")
    this
  }
}
