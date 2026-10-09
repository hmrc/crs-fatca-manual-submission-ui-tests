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

package uk.gov.hmrc.ui.pages.manual.accountholder

import org.openqa.selenium.By
import uk.gov.hmrc.ui.pages.BasePage
import uk.gov.hmrc.ui.utils.TestData

object AccountHolderOrgFatcaTypePage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/account-holder/organisation-fatca-type"

  private val ownerDocumentedRadio: By  = By.cssSelector("input[name='value'][value='ownerDocumented']")
  private val passiveRadio: By          = By.cssSelector("input[name='value'][value='passive']")
  private val nonParticipatingRadio: By = By.cssSelector("input[name='value'][value='nonParticipating']")
  private val specifiedPersonRadio: By  = By.cssSelector("input[name='value'][value='specifiedPerson']")

  def checkPage(): this.type = {
    onPage(pageUrl)
    checkH1(s"What type of account holder is ${TestData.fatcaAccountHolderName}?")
    this
  }

  def selectAccountHolderType(accountHolderType: String): this.type = {
    onPage(pageUrl)
    click(accountHolderType match {
      case "ownerDocumented"  => ownerDocumentedRadio
      case "passive"          => passiveRadio
      case "nonParticipating" => nonParticipatingRadio
      case "specifiedPerson"  => specifiedPersonRadio
      case other              => throw new IllegalArgumentException(s"Unknown Account Holder Type: $other")
    })
    click(submitButtonId)
    this
  }
}
