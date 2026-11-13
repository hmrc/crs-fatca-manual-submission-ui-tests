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

import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.pages.BasePage

object AccountHolderRemovePage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/account-holder/remove-account-holder"

  private def role(regime: String): String =
    if (regime == "CRS") "controlling person" else "substantial owner"

  def checkPage(regime: String): this.type = {
    val accountHolderName = AccountHoldersPage.selectedAccountHolderName
    onPage(pageUrl)
    checkH1("Remove this account holder")
    val page              = Driver.instance.getPageSource
    assert(
      page.contains("Before you can send your report, you must make sure that each:"),
      "Expected the 'Before you can send your report...' text"
    )
    assert(page.contains("account has at least one account holder"), "Expected the account bullet")
    assert(
      page.contains(s"${role(regime)} links to at least one account holder"),
      s"Expected the '${role(regime)} links to at least one account holder' bullet"
    )
    assert(
      page.contains(s"Are you sure you want to remove the account holder $accountHolderName?"),
      s"Expected 'Are you sure you want to remove the account holder $accountHolderName?'"
    )
    assert(isElementPresent(yesRadioId), "Expected the Yes radio")
    assert(isElementPresent(noRadioId), "Expected the No radio")
    assert(isElementPresent(submitButtonId), "Expected the Continue button")
    this
  }
}
