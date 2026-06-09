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

import org.openqa.selenium.By
import uk.gov.hmrc.ui.utils.DateUtil

object ManageReportsPage extends BasePage with DateUtil {

  override val pageUrl: String = baseUrlManualSub + s"/manage-reports-for-$reportingYear"

  val pageHeading: By         = By.tagName("h1")
  val crsReportsHeading: By   = By.xpath("//h2[contains(text(), 'CRS reports')]")
  val fatcaReportsHeading: By = By.xpath("//h2[contains(text(), 'FATCA reports')]")

  val uploadXmlLink: By           = By.linkText("uploading an XML file")
  val manualReportingLink: By     = By.linkText("filling in an online form for manual reporting")
  val backToManageReportsLink: By = By.linkText("Back to manage your CRS and FATCA reports")
  val voidThisInformationLink: By = By.linkText("Void this information")

  val viewOrMakeElectionsLink: By = By.partialLinkText("view or make elections for")
  val previousPaginationLink: By  = By.cssSelector("a.govuk-pagination__link[rel='prev']")
  val nextPaginationLink: By      = By.cssSelector("a.govuk-pagination__link[rel='next']")

  def yearPaginationLink(year: String): By =
    By.xpath(s"//nav[@aria-label='Pagination']//a[normalize-space()='$year']")

  def onManageReportsPage(): this.type = {
    onPageContaining(s"manage-reports-for-$reportingYear")
    this
  }

  def checkPageHeading(fiName: String): this.type = {
    checkH1(s"Submitted reports for $fiName for $reportingYear")
    this
  }

  def checkCrsReportsSection(): this.type = {
    waitUntilVisible(crsReportsHeading)
    this
  }

  def checkFatcaReportsSection(): this.type = {
    waitUntilVisible(fatcaReportsHeading)
    this
  }

  def clickUploadXmlLink(): this.type = {
    click(uploadXmlLink)
    this
  }

  def clickBackToManageReports(): this.type = {
    click(backToManageReportsLink)
    this
  }

  def clickVoidThisInformation(): this.type = {
    click(voidThisInformationLink)
    this
  }

  def clickViewOrMakeElectionsForFI(): this.type = {
    click(viewOrMakeElectionsLink)
    this
  }

//  def selectReportingYear(year: String): this.type = {
//    while (driver.findElements(yearPaginationLink(year)).isEmpty) {
//      click(previousPaginationLink)
//    }
//
//    click(yearPaginationLink(year))
//    this
//  }

  def selectReportingYear(year: String): this.type = {
    while (driver.findElements(yearPaginationLink(year)).isEmpty)
      if (year.toInt < reportingYear.toInt) click(previousPaginationLink)
      else click(nextPaginationLink)
    click(yearPaginationLink(year))
    this
  }
}
