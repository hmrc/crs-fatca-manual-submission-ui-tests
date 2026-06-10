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

object YourFisPage extends BasePage {

  override val pageUrl: String    = baseUrlFi + "/your-fis"
  val manageReportsLink: By       = By.xpath("//a[contains(@href, 'manage-reports-for')]")
  val secondManageReportsLink: By = By.xpath("//a[contains(@href, 'manage-reports-for-2025?fiId=683373339')]")

  def checkPage(): Unit =
    onPage(pageUrl)

  def clickOnManageReports(): Unit = {
    onPage(pageUrl)
    click(manageReportsLink)
  }

  def clickOnSecondManageReports(): Unit = {
    onPage(pageUrl)
    click(secondManageReportsLink)
  }

}
