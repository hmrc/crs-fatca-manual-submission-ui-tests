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

object SendAReportSectionStatusPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/send-a-report"

  private val sponsorStatus: By       = By.id("sponsor-1-status")
  private val filerCategoryStatus: By = By.id("filer-category-1-status")
  private val sponsorLink: By         = By.linkText("Sponsor details")
  private val filerCategoryLink: By   = By.linkText("Filer-category")

  def goToPage(): this.type = {
    Driver.instance.get(pageUrl)
    this
  }

  def clickSponsorDetails(): this.type = {
    click(sponsorLink)
    this
  }

  def clickFilerCategory(): this.type = {
    click(filerCategoryLink)
    this
  }

  def checkSponsorStatus(expected: String): this.type = {
    onPageContaining("/manual/send-a-report")
    val actual = getText(sponsorStatus).trim
    assert(actual == expected, s"Expected the Sponsor details status '$expected' but found '$actual'")
    this
  }

  def checkFilerCategoryStatus(expected: String): this.type = {
    onPageContaining("/manual/send-a-report")
    val actual = getText(filerCategoryStatus).trim
    assert(actual == expected, s"Expected the Filer category status '$expected' but found '$actual'")
    this
  }

  def checkFilerCategoryIsNotALink(): this.type = {
    assert(!isElementPresent(filerCategoryLink), "Filer category should not be a link while it cannot be started")
    this
  }

  def checkSponsorAndFilerCategoryNotShown(): this.type = {
    onPageContaining("/manual/send-a-report")
    assert(!isElementPresent(sponsorLink), "Sponsor details should not be shown for CRS")
    assert(!isElementPresent(sponsorStatus), "Sponsor details status should not be shown for CRS")
    assert(!isElementPresent(filerCategoryLink), "Filer category should not be shown for CRS")
    assert(!isElementPresent(filerCategoryStatus), "Filer category status should not be shown for CRS")
    this
  }
}
