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

object FatcaInformationVoidedPage extends BasePage {

  override val pageUrl: String  = "/fatca-void/information-voided"
  override val backLinkText: By = By.linkText("Back")

  val backToManageReportsLink: By     = By.id("submitted-reports-link")
  val backToManageCrsAndFatcaLink: By = By.linkText("Back to manage your CRS and FATCA reports")

  def checkPageHeading(): Unit =
    checkH1("FATCA information voided")

  def clickBackToManageSubmittedReports(): Unit = {
    checkDynamicPage()
    click(backToManageReportsLink)
    click(backLinkText)
  }

  def clickBackToManageCrsAndFatca(): Unit = {
    checkDynamicPage()
    click(backToManageCrsAndFatcaLink)
  }

}
