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
  val manageReportsLink: By       = By.xpath("//a[contains(@href, 'manage-reports-for-2025?fiId=TES683373304')]")
  val secondManageReportsLink: By = By.xpath("//a[contains(@href, 'manage-reports-for-2025?fiId=TES683373339')]")
  val thirdManageReportsLink: By  = By.xpath("//a[contains(@href, 'manage-reports-for-2025?fiId=TES683373303')]")
  val fourthManageReportsLink: By = By.xpath("//a[contains(@href, 'manage-reports-for-2025?fiId=TES683373300')]")
  val fifthManageReportsLink: By  = By.xpath("//a[contains(@href, 'manage-reports-for-2025?fiId=TES683373301')]")

  def checkPage(): Unit =
    onPage(pageUrl)

  def clickOnManageReports(yourFi: String): Unit =
    yourFi match {
      case "FifthFI" =>
        click(manageReportsLink)

      case "FirstFI" =>
        click(secondManageReportsLink)

      case "FourthFI" =>
        click(thirdManageReportsLink)

      case "SecondFI" =>
        click(fourthManageReportsLink)

      case "ThirdFI" =>
        click(fifthManageReportsLink)

      case "other" =>
        throw new IllegalArgumentException(s"Invalid Fi : $yourFi")

    }

}
