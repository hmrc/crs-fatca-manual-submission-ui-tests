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

package uk.gov.hmrc.ui.pages.manual.controllingpersonandsubstantialowners

import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.pages.BasePage

object CpSoRemovePage extends BasePage {

  override val pageUrl: String = baseUrlManualSub + "/manual/cp-so/remove-cp-so"

  private def role(regime: String): String =
    if (regime == "CRS") "controlling person" else "substantial owner"

  def checkPage(regime: String, name: String): this.type = {
    onPage(pageUrl)
    checkH1(s"Are you sure you want to remove the ${role(regime)} $name?")
    val page = Driver.instance.getPageSource
    assert(
      page.contains(
        s"Before you can send your report, you must make sure that account holders link to a ${role(regime)} if needed."
      ),
      s"Expected the 'Before you can send your report...' text for ${role(regime)}"
    )
    this
  }
}
