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

object ManageElectionsPage extends BasePage with DateUtil {

  override val pageUrl: String = baseUrlManualSub + s"/elections/manage-elections-for-$reportingYear"

  val crsElectionsHeading: By   = By.xpath("//h2[contains(text(), 'CRS elections')]")
  val fatcaElectionsHeading: By = By.xpath("//h2[contains(text(), 'FATCA elections')]")

  val makeCrsElectionsLink: By    = By.linkText("Make CRS elections")
  val makeFatcaElectionsLink: By  = By.linkText("Make FATCA elections")
  val backToManageReportsLink: By = By.partialLinkText("Back to manage reports for")
  val backToManageFinInstLink: By = By.linkText("Back to manage your financial institutions")

  val previousPaginationLink: By = By.cssSelector("a.govuk-pagination__link[rel='prev']")
  val nextPaginationLink: By     = By.cssSelector("a.govuk-pagination__link[rel='next']")

  def yearPaginationLink(year: String): By =
    By.xpath(s"//nav[@aria-label='Pagination']//a[normalize-space()='$year']")

  def activeYearTab(year: String): By =
    By.xpath(s"//nav[@aria-label='Pagination']//*[@aria-current='page' and normalize-space()='$year']")

  def onManageElectionsPage(): this.type = {
    onPageContaining(s"manage-elections-for-")
    this
  }

  def checkPageHeading(fiName: String, year: String): this.type = {
    checkH1(s"Manage elections for $fiName for $year")
    this
  }

  def checkCrsElectionsSection(): this.type = {
    waitUntilVisible(crsElectionsHeading)
    this
  }

  def checkFatcaElectionsSection(): this.type = {
    waitUntilVisible(fatcaElectionsHeading)
    this
  }

  def checkActiveYear(year: String): this.type = {
    waitUntilVisible(activeYearTab(year))
    this
  }

  def checkDefaultYear(): this.type = {
    checkActiveYear(reportingYear.toString)
    this
  }

  def checkEarliestYear(): this.type = {
    checkActiveYear((currentYear - 12).toString)
    this
  }

  def checkCurrentYear(): this.type = {
    checkActiveYear(currentYear.toString)
    this
  }

  def clickMakeCrsElections(): this.type = {
    click(makeCrsElectionsLink)
    this
  }

  def clickMakeFatcaElections(): this.type = {
    click(makeFatcaElectionsLink)
    this
  }

  def clickBackToManageReports(): this.type = {
    click(backToManageReportsLink)
    this
  }

  def clickBackToManageFinancialInstitutions(): this.type = {
    click(backToManageFinInstLink)
    this
  }

  def clickPrevious(): this.type = {
    click(previousPaginationLink)
    this
  }

  def clickNext(): this.type = {
    click(nextPaginationLink)
    this
  }

  def selectElectionsYear(year: String): this.type = {
    while (driver.findElements(yearPaginationLink(year)).isEmpty)
      click(previousPaginationLink)
    click(yearPaginationLink(year))
    this
  }

  def navigateThroughAllPreviousYears(): this.type = {
    (reportingYear to (currentYear - 12) by -1).foreach { year =>
      checkActiveYear(year.toString)
      if (year > currentYear - 12) click(previousPaginationLink)
    }
    this
  }

  def navigateToCurrentYear(): this.type = {
    while (driver.findElements(yearPaginationLink(currentYear.toString)).isEmpty)
      click(nextPaginationLink)
    click(yearPaginationLink(currentYear.toString))
    this
  }

}
