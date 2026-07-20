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

package uk.gov.hmrc.ui.pages.elections

import org.openqa.selenium.By
import uk.gov.hmrc.ui.pages.BasePage

object ElectionsCheckAnswersPage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/elections/check-answers"

  val changeContractsLink: By    = By.cssSelector("a[href*='change-contracts']")
  val changeDormantLink: By      = By.cssSelector("a[href*='change-dormant-accounts']")
  val changeThresholdsLink: By   = By.cssSelector("a[href*='change-thresholds']")
  val changeCarfLink: By         = By.cssSelector("a[href*='change-carf-gross-proceeds']")
  val changeCarfUnderCrsLink: By = By.cssSelector("a[href*='change-gross-proceeds']")

  val changeFatcaUsTreasuryLink: By = By.cssSelector("a[href*='change-us-treasury-regulations']")
  val changeFatcaThresholdsLink: By = By.cssSelector("a[href*='fatca/change-thresholds']")

  def confirmAndSend(): Unit = {
    checkDynamicPage()
    click(submitButtonId)
  }

  def clickChangeContracts(): this.type = {
    checkDynamicPage()
    click(changeContractsLink)
    this
  }

  def clickChangeDormant(): this.type = {
    checkDynamicPage()
    click(changeDormantLink)
    this
  }

  def clickChangeThresholds(): this.type = {
    checkDynamicPage()
    click(changeThresholdsLink)
    this
  }

  def clickChangeCarf(): this.type = {
    checkDynamicPage()
    click(changeCarfLink)
    this
  }

  def clickChangeCarfUnderCrs(): this.type = {
    checkDynamicPage()
    click(changeCarfUnderCrsLink)
    this
  }

  def clickChangeFatcaUsTreasury(): this.type = {
    checkDynamicPage()
    click(changeFatcaUsTreasuryLink)
    this
  }

  def clickChangeFatcaThresholds(): this.type = {
    checkDynamicPage()
    click(changeFatcaThresholdsLink)
    this
  }
}
